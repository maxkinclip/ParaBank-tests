package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        String baseUrl       = System.getProperty("baseUrl", "https://parabank.parasoft.com");
        String browser       = System.getProperty("browser", "chrome");
        String browserSize   = System.getProperty("browserSize", "1920x1080");
        String browserVer    = System.getProperty("browserVersion", "");
        String remoteUrl     = System.getProperty("remote", ""); // if empty => local
        boolean headless     = Boolean.parseBoolean(System.getProperty("headless", "false"));

        Configuration.baseUrl       = baseUrl;
        Configuration.browser       = browser;
        Configuration.browserSize   = browserSize;
        Configuration.headless      = headless;
        if (!browserVer.isBlank()) {
            Configuration.browserVersion = browserVer;
        }
        if (!remoteUrl.isBlank()) {
            Configuration.remote = remoteUrl;
        }

        Configuration.pageLoadStrategy = "eager";


        addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .includeSelenideSteps(true));
    }

    @BeforeEach
    void openMainPage() {
        open("/");
        ensureLoginPanelVisible();
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    // ---------- helpers ----------
    private void ensureLoginPanelVisible() {
        // ParaBank sometimes shows an Error page; retry a few times
        for (int i = 0; i < 3; i++) {
            if ($(byName("username")).exists()) return; // found login form
            if ($("h1.title").has(text("Error!")) || $("p.error").has(text("internal error"))) {
                Selenide.sleep(1000);
                refresh();
            } else {
                Selenide.sleep(1000);
                refresh();
            }
        }

        $(byName("username")).should(exist).shouldBe(visible);
    }
}