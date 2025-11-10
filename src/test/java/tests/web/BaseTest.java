package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

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

        addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(true));


        Configuration.pageLoadStrategy = "eager";

    }

    @AfterAll
    void tearDown() {
        closeWebDriver();
    }
}
