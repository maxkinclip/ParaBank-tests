#!/usr/bin/env bash
set +e
set -u

: "${TELEGRAM_TOKEN:?TELEGRAM_TOKEN is required}"
: "${TELEGRAM_CHAT_ID:?TELEGRAM_CHAT_ID is required}"

JOB_NAME="${JOB_NAME:-ParaBank-tests}"
BUILD_NUMBER="${BUILD_NUMBER:-N/A}"
BUILD_STATUS="${BUILD_STATUS:-SUCCESS}"
BUILD_URL="${BUILD_URL:-}"

ALLURE_CLI="/home/jenkins/tools/ru.yandex.qatools.allure.jenkins.tools.AllureCommandlineInstallation/allure_2.17.2/bin/allure"
RESULTS_DIR="${WORKSPACE}/build/allure-results"
TEMP_DIR="${WORKSPACE}/tmp-allure-${BUILD_NUMBER}"

rm -rf "${TEMP_DIR}" || true
mkdir -p "${TEMP_DIR}"


if [ -d "${RESULTS_DIR}" ] && [ "$(ls -A "${RESULTS_DIR}" 2>/dev/null || true)" ]; then
  if [ -x "${ALLURE_CLI}" ]; then
    "${ALLURE_CLI}" generate "${RESULTS_DIR}" -c -o "${TEMP_DIR}" >/dev/null 2>&1 || true
  elif command -v allure >/dev/null 2>&1; then
    allure generate "${RESULTS_DIR}" -c -o "${TEMP_DIR}" >/dev/null 2>&1 || true
  fi
fi


SUMMARY_JSON="${TEMP_DIR}/widgets/summary.json"
TOTAL="0"; PASSED="0"; FAILED="0"; BROKEN="0"; SKIPPED="0"; DURATION_MS="0"

if [ -s "${SUMMARY_JSON}" ]; then
  RAW="$(tr -d '\n' < "${SUMMARY_JSON}")"
  TOTAL=$(   echo "$RAW" | sed -nE 's/.*"total"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p'   | head -n1)
  PASSED=$(  echo "$RAW" | sed -nE 's/.*"passed"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p'  | head -n1)
  FAILED=$(  echo "$RAW" | sed -nE 's/.*"failed"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p'  | head -n1)
  BROKEN=$(  echo "$RAW" | sed -nE 's/.*"broken"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p'  | head -n1)
  SKIPPED=$( echo "$RAW" | sed -nE 's/.*"skipped"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p' | head -n1)
  DURATION_MS=$(echo "$RAW" | sed -nE 's/.*"duration"[[:space:]]*:[[:space:]]*([0-9]+).*/\1/p' | head -n1)

  TOTAL=${TOTAL:-0}; PASSED=${PASSED:-0}; FAILED=${FAILED:-0}; BROKEN=${BROKEN:-0}; SKIPPED=${SKIPPED:-0}; DURATION_MS=${DURATION_MS:-0}
else
  echo "No summary.json produced. Sending notification without stats."
fi


DUR_SEC=$(( DURATION_MS / 1000 ))
MIN=$(( DUR_SEC / 60 ))
SEC=$(( DUR_SEC % 60 ))
HUMAN_DURATION=$(printf "%02d:%02d" "${MIN}" "${SEC}")


case "${BUILD_STATUS}" in
  SUCCESS)  STATUS_ICON="🟢" ;;
  FAILURE)  STATUS_ICON="🔴" ;;
  UNSTABLE) STATUS_ICON="🟡" ;;
  *)        STATUS_ICON="⚪️" ;;
esac

ALLURE_LINK="${BUILD_URL}allure/"


TEXT="${STATUS_ICON} ParaBank build: ${JOB_NAME} #${BUILD_NUMBER}
Status: ${BUILD_STATUS}
Duration: ${HUMAN_DURATION}
Total: ${TOTAL}  |  ✅ ${PASSED}  |  ❌ ${FAILED}  |  ⚠️ ${BROKEN}  |  ⏭ ${SKIPPED}
Allure: ${ALLURE_LINK}"

ESCAPED_TEXT=$(printf '%s' "$TEXT" | awk '{printf "%s\\n",$0}' | sed 's/\\n$//')
PAYLOAD="{\"chat_id\":\"${TELEGRAM_CHAT_ID}\",\"text\":\"${ESCAPED_TEXT}\",\"disable_notification\":true}"

echo "Sending Telegram summary..."
curl -sS -X POST -H "Content-Type: application/json" \
  -d "${PAYLOAD}" \
  "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage" >/dev/null || true
echo "Text summary sent successfully."


SCREENSHOT_PATH="${WORKSPACE}/allure_screenshot_${BUILD_NUMBER}.png"

echo "Checking for Chromium or wkhtmltoimage..."
if command -v chromium >/dev/null 2>&1; then
  echo "Chromium found, taking screenshot of Allure report..."
  chromium --headless --disable-gpu \
    --window-size=1280,720 \
    --screenshot="${SCREENSHOT_PATH}" \
    "${ALLURE_LINK}" >/dev/null 2>&1 || true

elif command -v wkhtmltoimage >/dev/null 2>&1; then
  echo "wkhtmltoimage found, capturing Allure report..."
  wkhtmltoimage --quality 90 \
    "${ALLURE_LINK}" "${SCREENSHOT_PATH}" >/dev/null 2>&1 || true
else
  echo "Neither Chromium nor wkhtmltoimage installed — skipping report screenshot."
fi


if [ -s "${SCREENSHOT_PATH}" ]; then
  echo "Sending Allure report screenshot to Telegram..."
  curl -s -X POST \
    -F chat_id="${TELEGRAM_CHAT_ID}" \
    -F photo=@"${SCREENSHOT_PATH}" \
    -F caption="📊 ${STATUS_ICON} ParaBank build #${BUILD_NUMBER} — ${BUILD_STATUS}
Duration: ${HUMAN_DURATION}
Total: ${TOTAL} | ✅ ${PASSED} | ❌ ${FAILED} | ⚠️ ${BROKEN} | ⏭ ${SKIPPED}
Allure: ${ALLURE_LINK}" \
    "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendPhoto" >/dev/null 2>&1 || true
  echo "Screenshot sent successfully."
else
  echo "No screenshot captured — skipping image send."
fi

rm -rf "${TEMP_DIR}" || true