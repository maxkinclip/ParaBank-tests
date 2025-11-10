package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

import org.junit.jupiter.api.Assumptions;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        String baseUrl       = System.getProperty("baseUrl", "https://parabank.parasoft.com");
        String browser       = System.getProperty("browser", "chrome");
        String browserSize   = System.getProperty("browserSize", "1920x1080");
        String browserVer    = System.getProperty("browserVersion", "");
        String remoteUrl     = System.getProperty("remote", "");
        boolean headless     = Boolean.parseBoolean(System.getProperty("headless", "false"));

        Configuration.baseUrl       = baseUrl;
        Configuration.browser       = browser;
        Configuration.browserSize   = browserSize;
        Configuration.headless      = headless;
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 8000;

        if (!browserVer.isBlank()) {
            Configuration.browserVersion = browserVer;
        }
        if (!remoteUrl.isBlank()) {
            Configuration.remote = remoteUrl;
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .includeSelenideSteps(true));
    }


    @BeforeEach
    void startCleanOnLogin() {
        resetToLogin();
        ensureLoginPanelVisible();
    }

    @Step("Reset to login page (clear cookies/storage and open home)")
    protected void resetToLogin() {
        open("/");
        clearBrowserCookies();
        clearBrowserLocalStorage();
        open("/");
    }

    @Step("Ensure login panel is visible")
    protected void ensureLoginPanelVisible() {

        int tries = 4;
        for (int i = 1; i <= tries; i++) {
            if ($(byName("username")).exists()) {
                $(byName("username")).should(appear);
                return;
            }

            sleep(800);
            refresh();
        }


        System.err.println("Could not ensure login panel. URL: " + WebDriverRunner.url());
        $(byName("username")).should(exist);
    }



    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    // ---------- helpers ----------

    protected void openMainPageAndEnsureLogin() {
        int maxAttempts = 5;
        for (int i = 1; i <= maxAttempts; i++) {
            open("/");
            if ($(byName("username")).exists()) {
                $(byName("username")).shouldBe(visible);
                return;
            }
            if ($("h1.title").has(text("Error!")) || $("p.error").has(text("internal error"))) {
                System.out.println("Attempt " + i + ": Error page; retry...");
            } else {
                System.out.println("Attempt " + i + ": login form not found; retry...");
            }
            sleep(1500);
        }


        System.err.println("Could not load login panel after retries. URL: " + WebDriverRunner.url());
        System.err.println("Page title: " + title());
        String html = WebDriverRunner.source();
        if (html != null && html.contains("Error!")) {
            System.err.println("Detected ParaBank internal error page.");
        }
        $(byName("username")).should(exist);
    }

    protected boolean isHomeRedirect() {

        return WebDriverRunner.url().contains("/index.htm") || $(byName("username")).exists();
    }


    protected void expectRightPanelTitle(String expectedText) {
        boolean strict = Boolean.parseBoolean(System.getProperty("STRICT_UI", "true"));

        try {
            $("#rightPanel").should(appear);
            $("#rightPanel .title").should(appear).shouldHave(text(expectedText));
        } catch (Throwable t) {
            if (!strict && isHomeRedirect()) {
                System.out.println("ParaBank redirected to home during assertion. Skipping as env flake.");
                Assumptions.abort("Skipped due to ParaBank home redirect (environment instability).");
            }
            throw t; // rethrow real failure
        }
    }

    protected void ensureOn(String path, String expectedRightPanelTitle) {
        int maxWaitMs = 8000;
        int intervalMs = 500;
        long start = System.currentTimeMillis();


        while (System.currentTimeMillis() - start < maxWaitMs) {
            String currentUrl = WebDriverRunner.url();
            if (currentUrl.contains(path)) {
                break;
            }

            if (currentUrl.contains("/index.htm") || $("h1.title").has(text("Error!"))) {
                System.out.println("⚠️ Waiting for redirect to settle... (" + (System.currentTimeMillis() - start) / 1000.0 + "s)");
            }
            sleep(intervalMs);
        }


        if (!WebDriverRunner.url().contains(path)) {
            System.out.println("🔄 Forcing navigation to " + path);
            open(path);
        }


        sleep(1000);
        $("#rightPanel .title").should(appear).shouldHave(text(expectedRightPanelTitle));
    }

    @AfterEach
    void addAllureAttachments() {
        Attachments.screenshot("Final screenshot");
        Attachments.pageSource();
        Attachments.browserConsoleLogs();
    }

}
