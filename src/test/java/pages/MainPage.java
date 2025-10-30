package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;

public class MainPage {

    public SelenideElement usernameField() {
        return Selenide.$("username");
    }

    public SelenideElement passwordField() {
        return Selenide.$("password");
    }

    public SelenideElement loginButton() {
        return Selenide.$("Log In");
    }

    public SelenideElement forgotLoginLink() {
        return Selenide.$(byText("Forgot login info?"));
    }

    public SelenideElement registerLink() {
        return Selenide.$(byText ("Register"));
    }

    public SelenideElement contactButton() {
        return Selenide.$(".contact");
    }




    @Step("Fill username field with '{username}'")
    public MainPage fillUsername(String username) {
        usernameField().sendKeys(username);
        return this;
    }

    @Step("Fill password field with '{password}'")
    public MainPage fillPassword(String password) {
        passwordField().sendKeys(password);
        return this;
    }

    @Step("Click login button")
    public MainPage clickLoginButton() {
        loginButton().click();
        return this;
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

