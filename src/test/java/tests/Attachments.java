package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

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

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        if (WebDriverRunner.getWebDriver() instanceof org.openqa.selenium.remote.RemoteWebDriver driver) {
            String sessionId = driver.getSessionId().toString();
            String videoUrl = "https://selenoid.autotests.cloud/video/" + sessionId + ".mp4";
            return "<html><body><video width='100%' controls autoplay><source src='" + videoUrl + "' type='video/mp4'></video></body></html>";
        }
        return "";
    }
}