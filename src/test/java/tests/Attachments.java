package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Attachments {
    public static void screenshot(String name) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            byte[] bytes = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(name, "image/png", "png", bytes);
        }
    }

    public static void pageSource() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Allure.addAttachment("Page source", "text/html",
                    WebDriverRunner.source(), ".html");
        }
    }

    public static void browserConsoleLogs() {
        if (!WebDriverRunner.hasWebDriverStarted()) return;
        try {
            var logs = WebDriverRunner.getWebDriver().manage().logs().get("browser")
                    .getAll().stream().map(l -> l.getLevel() + " " + l.getMessage())
                    .collect(Collectors.joining("\n"));
            if (!logs.isBlank()) {
                Allure.addAttachment("Browser console logs", "text/plain",
                        logs, ".log");
            }
        } catch (Throwable ignored) {

        }
    }
}