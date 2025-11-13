package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import config.EnvConfig;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;


import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        EnvConfig.loadEnvironment();

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

        if (!browserVer.isBlank()) Configuration.browserVersion = browserVer;

        if (!remoteUrl.isBlank()) {
            Configuration.remote = remoteUrl;
            System.out.println("REMOTE GRID: " + Configuration.remote);
            System.out.println("Browser: " + browser + " " + browserVer);

            var chrome = new org.openqa.selenium.chrome.ChromeOptions();
            chrome.addArguments(
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=" + browserSize.replace('x', ',')
            );

            var prefs = new java.util.HashMap<String, Object>();
            prefs.put("download.prompt_for_download", false);
            prefs.put("safebrowsing.enabled", true);
            chrome.setExperimentalOption("prefs", prefs);

            var selenoid = new java.util.HashMap<String, Object>();
            selenoid.put("enableVNC", true);
            selenoid.put("enableVideo", true);
            selenoid.put("name", "ParaBank Jenkins run");
            chrome.setCapability("selenoid:options", selenoid);

            Configuration.browserCapabilities = chrome;
        } else {
            System.out.println("LOCAL MODE (no -Dremote passed)");
        }

        com.codeborne.selenide.logevents.SelenideLogger.addListener("AllureSelenide",
                new io.qameta.allure.selenide.AllureSelenide()
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


        System.err.println("Couldn't ensure login panel. URL: " + WebDriverRunner.url());
        $(byName("username")).should(exist);
    }


    @AfterEach
    void addAllureAttachments() {
        Attachments.screenshot("Final screenshot");
        Attachments.pageSource();
        Attachments.browserConsoleLogs();


        if (Configuration.remote != null && !Configuration.remote.isBlank()) {
            Attachments.addVideo();
        }
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }
}
