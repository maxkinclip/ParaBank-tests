package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class RegistrationPage {

    public SelenideElement CustomerFirstNameField() {
        return Selenide.$("customer.firstName");
    }

    public SelenideElement CustomerLastNameField() {
        return Selenide.$("customer.lastName");
    }

    public SelenideElement CustomerCityField() {
        return Selenide.$("customer.address.city");
    }

    public SelenideElement CustomerStateField() {
        return Selenide.$("customer.address.state");
    }

    public SelenideElement CustomerZipCodeField() {
        return Selenide.$("customer.address.zipCode");
    }

    public SelenideElement CustomerPhoneNumberField() {
        return Selenide.$("customer.phoneNumber");
    }

    public SelenideElement CustomerSsnField() {
        return Selenide.$("customer.ssn");
    }

    public SelenideElement CustomerUsernameField() {
        return Selenide.$("customer.username");
    }

    public SelenideElement CustomerPasswordField() {
        return Selenide.$("customer.password");
    }

    public SelenideElement CustomerPasswordValidationField() {
        return Selenide.$("repeatedPassword");
    }

    public SelenideElement submitRegistrationFormButton() {
        return Selenide.$("submit");
    }

    @Step("Fill first name field with '{firstName}'")
    public RegistrationPage fillFirstName(String firstName) {
        CustomerFirstNameField().sendKeys(firstName);
        return this;
    }

    @Step("Fill last name field with '{lastName}'")
    public RegistrationPage fillLastName(String lastName) {
        CustomerLastNameField().sendKeys(lastName);
        return this;
    }

    @Step("Fill city field with '{city}'")
    public RegistrationPage fillCityField(String city) {
        CustomerCityField().sendKeys(city);
        return this;
    }

    @Step("Fill state field with '{state}'")
    public RegistrationPage fillStateField(String state) {
        CustomerStateField().sendKeys(state);
        return this;
    }

    @Step("Fill zip code field with '{zipCode}'")
    public RegistrationPage fillZipCodeField(String zipCode) {
        CustomerZipCodeField().sendKeys(zipCode);
        return this;
    }

    @Step("Fill phoneNumber field with '{phoneNumber}'")
    public RegistrationPage fillPhoneNumberField(String phoneNumber) {
        CustomerPhoneNumberField().sendKeys(phoneNumber);
        return this;
    }

    @Step("Fill SSN number field with '{ssn}'")
    public RegistrationPage fillSsnNumberField(String ssn) {
        CustomerSsnField().sendKeys(ssn);
        return this;
    }

    @Step("Fill username field with '{username}'")
    public RegistrationPage fillCustomerUsernameField(String username) {
        CustomerUsernameField().sendKeys(username);
        return this;
    }

    @Step("Fill State field with '{password}'")
    public RegistrationPage fillCustomerPasswordField(String password) {
        CustomerPasswordField().sendKeys(password);
        return this;
    }

    @Step("Fill password validation field with '{repeatedPassword}'")
    public RegistrationPage fillCustomerPasswordValidationField(String repeatedPassword) {
        CustomerPasswordValidationField().sendKeys(repeatedPassword);
        return this;
    }

    @Step("Click Submit Registration Form button")
    public RegistrationPage ClickSubmitRegistrationFormButton() {
        submitRegistrationFormButton().click();

        return this;
    }



}





