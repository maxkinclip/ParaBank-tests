package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://parabank.parasoft.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        // Configuration.browser = "chrome";
        // Configuration.holdBrowserOpen = true;
        // Configuration.timeout = 5000;
    }

    @AfterAll
    static void tearDown() {
        //closeWebDriver();
    }
}