package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.codeborne.selenide.WebDriverRunner.url;

public class MainPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/index.htm";
    }

    @Step("Open Main Page")
    public MainPage openMainPage() {
        openPage();
        return this;
    }

    @Step("Fill username field with '{username}'")
    public MainPage fillUsername(String username) {
        usernameField().sendKeys(username);
        return this;
    }

    public SelenideElement usernameField() {
        return $("[name='username']");
    }

    @Step("Fill password field with '{password}'")
    public MainPage fillPassword(String password) {
        passwordField().sendKeys(password);
        return this;
    }

    public SelenideElement passwordField() {
        return $("[name='password']");
    }

    @Step("Click login button")
    public MainPage clickLoginButton() {
        loginButton().click();
        return this;
    }

    public SelenideElement loginButton() {
        return $("[value='Log In']");
    }

    @Step("Login verification")
    public MainPage loginVerification() {
        String currentUrl = url();
        assertTrue(
                currentUrl.contains("overview.htm") || currentUrl.contains("index.htm"),
                "Expected user to be logged in (overview or index page), but was: " + currentUrl
        );
        return null;
    }

    @Step("Receive login error")
    public MainPage verifyLoginError() {
        String expectedUrl = "https://parabank.parasoft.com/parabank/login.htm";
        String actualUrl = WebDriverRunner.url().split(";")[0];
        assertEquals(expectedUrl, actualUrl,
                "User should be on the login page after failed main page login");
        loginError().should(appear);
        return this;
    }

    public SelenideElement loginError() {
        return $("#rightPanel .title")
                .should(appear)
                .shouldHave(or("title", text("Error!"), text("The username and password could not be verified.")));
    }

    @Step("Fill Login Form")
    public MainPage fillLoginForm(String userName, String password) {
        return fillUsername(userName)
                .fillPassword(password)
                .clickLoginButton();

    }

    public SelenideElement forgotLoginLink() {
        return $(byText("Forgot login info?"));
    }

    public SelenideElement registerLink() {
        return $(byText ("Register"));
    }

    public SelenideElement contactButton() {
        return $(".contact");
    }



    @Step("Click forgot login link")
    public MainPage clickForgotLoginLink() {
        forgotLoginLink().click();
        return this;
    }

    @Step("Click register link")
    public MainPage clickRegisterLink() {
        registerLink().click();
        return this;
    }

    @Step("Click contact button")
    public MainPage clickContactButton() {
        contactButton().click();
        return this;
    }









}

