package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;

import java.nio.charset.StandardCharsets;

public class BaseTest {

    @BeforeAll
    static void setUp() {

        Configuration.baseUrl = System.getProperty("baseUrl", "https://parabank.parasoft.com");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = "eager";


        if ("true".equalsIgnoreCase(System.getProperty("headless", "false"))) {
            System.setProperty("selenide.headless", "true");
        }


        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true));
    }

    @AfterEach
    void tearDown() {
        attachScreenshot();
        attachPageSource();
        attachBrowserLogs();
        attachCurrentUrl();
        Selenide.closeWebDriver();
    }




    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        try {
            return Selenide.screenshot(OutputType.BYTES);
        } catch (Throwable e) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page Source", type = "text/html", fileExtension = ".html")
    private byte[] attachPageSource() {
        try {
            return WebDriverRunner.source().getBytes(StandardCharsets.UTF_8);
        } catch (Throwable e) {
            return "No page source".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Attachment(value = "Browser Console Logs", type = "text/plain")
    private String attachBrowserLogs() {
        try {
            return String.join("\n", Selenide.getWebDriverLogs("browser"));
        } catch (Throwable e) {
            return "No logs available";
        }
    }

    @Attachment(value = "Current URL", type = "text/plain")
    private String attachCurrentUrl() {
        try {
            return WebDriverRunner.url();
        } catch (Throwable e) {
            return "No URL";
        }
    }
}