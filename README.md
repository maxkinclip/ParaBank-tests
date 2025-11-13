# 🧪 Проект автоматизации тестирования ParaBank
<img src="images/parabank_logo.png" width="500" alt="Java">

## 🌐 Описание проекта
Учебный проект по автоматизации тестирования банковского сайта [ParaBank](https://parabank.parasoft.com/parabank/index.htm).

Проект демонстрирует использование современных инструментов автоматизации тестирования, CI/CD, генерацию отчётности в Allure и интеграцию с Telegram-уведомлениями через Jenkins.

---

## 🧰 Стек технологий

<p align="center">
  <img src="images/hd-java-logo.png" width="65" alt="Java">
  <img src="images/junit-5-logo.png" width="65" alt="JUnit 5">
  <img src="images/selenide-logo.png" width="65" alt="Selenide">
  <img src="images/allure-logo.png" width="65" alt="Allure">
  <img src="images/jenkins-logo.png" width="65" alt="Jenkins">
  <img src="images/gradle-logo.png" width="65" alt="Gradle">
  <img src="images/intellij-idea-logo.png" width="65" alt="IntelliJ IDEA">
  <img src="images/github-logo.png" width="65" alt="GitHub">
</p>

| Категория | Технологии                                                                                 |
|------------|--------------------------------------------------------------------------------------------|
| Язык программирования | [**Java 17**](https://www.java.com/)                                                       |
| Сборщик | [**Gradle**](https://gradle.org/)                                                          |
| Тестовый фреймворк | [**JUnit 5**](https://junit.org/junit5/)                                                   |
| UI-тестирование | [**Selenide**](https://selenide.org/)                                                      |
| API-тестирование | [**REST Assured**](https://rest-assured.io/)                                               |
| Отчётность | [**Allure Report**](https://allurereport.org/)                                             |
| CI/CD | [**Jenkins**](https://www.jenkins.io/)                                                     |
| Уведомления | [**Telegram Bot + Allure Notifications**](https://github.com/qa-guru/allure-notifications) |
| IDE | [**IntelliJ IDEA**](https://www.jetbrains.com/idea/)                                       |
| Система контроля версий | [**GitHub**](https://github.com/)                                                          |

---

## 🧩 Реализованные тесты (UI + API)

- ✅ Авторизация с валидными/невалидными данными
- ✅ Регистрация нового пользователя
- ✅ Открытие расчётного/сберегательного счёта
- ✅ Перевод средств между счетами
- ✅ Оформление заявки на займ
- ✅ Заполнение формы для связи с поддержкой
- ✅ Проверка статуса ответа при неверных запросах

---

## ⚙️ Запуск тестов

### Локальный запуск:
gradle clean test -Denv=local

### Удалённый запуск через Selenoid:
gradle clean test -Denv=remote

### Запуск в Jenkins:
clean test -Denv=jenkins




---
#  <img src="images/jenkins-logo.png" width="70" alt="Jenkins"> Запуск тестов

## [Сборка в Jenkins](https://jenkins.autotests.cloud/job/ParaBank-tests/)
<p align="center">
  <img src="images/jenkins_build.png" width="900" alt="Jenkins Build Page">
</p>

- На странице проекта доступна информация о последних сборках и график успешности прохождения тестов из Allure-отчёта

---
#  <img src="images/allure-logo.png" width="70" alt="Jenkins"> Интеграция с Allure


## Основное окно
<img src="images/allure_report.png" width="900" alt="Java">

## Тесты

- К каждой проверке прилагается скриншот страницы последнего действия в тесте, логи и видео

<img src="images/allure_test_suite.png" width="900" alt="Java">

---
#  <img src="images/telegram-logo.png" width="70" alt="Jenkins"> Allure уведомления в Telegram
<img src="images/Allure_telegram_bot.png" width="900" alt="Java">