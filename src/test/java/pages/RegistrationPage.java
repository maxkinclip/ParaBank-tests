package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class RegistrationPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/register.htm";
    }

    @Step("Open Registration Page")
    public RegistrationPage openRegistrationPage() {
        openPage();
        return this;
    }

    @Step("Fill first name field with '{firstName}'")
    public RegistrationPage fillFirstName(String firstName) {
        customerFirstNameField().clear();
        customerFirstNameField().sendKeys(firstName);
        return this;
    }

    public SelenideElement customerFirstNameField() {
        return $(byId("customer.firstName"))
                .shouldBe(visible,enabled)
                .shouldHave(attribute("name", "customer.firstName"))
                .shouldBe(visible,enabled);
    }

    @Step("Fill last name field with '{lastName}'")
    public RegistrationPage fillLastName(String lastName) {
        CustomerLastNameField().clear();
        CustomerLastNameField().sendKeys(lastName);
        return this;
    }

    public SelenideElement CustomerLastNameField() {
        return Selenide.$("form[action*='register']").$(byId("customer.lastName"));
    }

    @Step("Fill address field with '{address}'")
    public RegistrationPage fillAddressField(String address) {
        CustomerAddressField().clear();
        CustomerAddressField().sendKeys(address);
        return this;
    }

    public SelenideElement CustomerAddressField() {
        return $("form[action*='register']").$(byId("customer.address.street"));
    }

    @Step("Fill city field with '{city}'")
    public RegistrationPage fillCityField(String city) {
        CustomerCityField().clear();
        CustomerCityField().sendKeys(city);
        return this;
    }

    public SelenideElement CustomerCityField() {
        return $("form[action*='register']").$(byId("customer.address.city"));
    }

    @Step("Fill state field with '{state}'")
    public RegistrationPage fillStateField(String state) {
        CustomerStateField().clear();
        CustomerStateField().sendKeys(state);
        return this;
    }

    public SelenideElement CustomerStateField() {
        return Selenide.$("form[action*='register']").$(byId("customer.address.state"));
    }
    @Step("Fill zip code field with '{zipCode}'")
    public RegistrationPage fillZipCodeField(String zipCode) {
        CustomerZipCodeField().clear();
        CustomerZipCodeField().sendKeys(zipCode);
        return this;
    }

    public SelenideElement CustomerZipCodeField() {
        return Selenide.$("form[action*='register']").$(byId("customer.address.zipCode"));
    }

    @Step("Fill phoneNumber field with '{phoneNumber}'")
    public RegistrationPage fillPhoneNumberField(String phoneNumber) {
        CustomerPhoneNumberField().clear();
        CustomerPhoneNumberField().sendKeys(phoneNumber);
        return this;
    }

    public SelenideElement CustomerPhoneNumberField() {
        return Selenide.$("form[action*='register']").$(byId("customer.phoneNumber"));
    }

    @Step("Fill SSN number field with '{ssn}'")
    public RegistrationPage fillCustomerSsnField(String ssn) {
        CustomerSsnField().clear();
        CustomerSsnField().sendKeys(ssn);
        return this;
    }

    public SelenideElement CustomerSsnField() {
        return Selenide.$("form[action*='register']").$(byId("customer.ssn"));
    }

    @Step("Fill username field with '{username}'")
    public RegistrationPage fillCustomerUsernameField(String username) {
        CustomerUsernameField().clear();
        CustomerUsernameField().sendKeys(username);
        return this;
    }

    public SelenideElement CustomerUsernameField() {
        return Selenide.$("form[action*='register']").$(byId("customer.username"));
    }

    @Step("Fill State field with '{password}'")
    public RegistrationPage fillCustomerPasswordField(String password) {
        CustomerPasswordField().clear();
        CustomerPasswordField().sendKeys(password);
        return this;
    }

    public SelenideElement CustomerPasswordField() {
        return Selenide.$("form[action*='register']").$(byId("customer.password")) ;
    }

    @Step("Fill password validation field with '{repeatedPassword}'")
    public RegistrationPage fillCustomerPasswordValidationField(String repeatedPassword) {
        CustomerPasswordValidationField().clear();
        CustomerPasswordValidationField().sendKeys(repeatedPassword);;
        return this;
    }
    public SelenideElement CustomerPasswordValidationField() {
        return Selenide.$("form[action*='register']").$(byId("repeatedPassword"));
    }

    @Step("Click Submit Registration Form button")
    public RegistrationPage clickSubmitRegistrationFormButton() {
        submitRegistrationFormButton().click();

        return this;
    }

    public SelenideElement submitRegistrationFormButton() {
        return Selenide.$("form[action*='register'] input.button[value='Register']");
    }


    @Step("Fill Registration Form")
    public RegistrationPage fillRegistrationForm(String firstName, String lastName, String address, String city, String state, String zipCode, String phoneNumber, String ssn, String userName, String password, String passwordValidation){
        return fillFirstName(firstName)
                .fillLastName(lastName)
                .fillAddressField(address)
                .fillCityField(city)
                .fillStateField(state)
                .fillZipCodeField(zipCode)
                .fillPhoneNumberField(phoneNumber)
                .fillCustomerSsnField(ssn)
                .fillCustomerUsernameField(userName)
                .fillCustomerPasswordField(password)
                .fillCustomerPasswordValidationField(passwordValidation);

    }

    @Step("Verify getting a successful registration message")
    public RegistrationPage verifySuccessfulRegistrationMessage() {
        $(byText("Your account was created successfully. You are now logged in.")).click();
        return this;
    }

    @Step("Verify error message about passwords not matching is displayed")
    public RegistrationPage verifyPasswordsDoNotMatchError() {
        $("form[action*='register']").$(byId("repeatedPassword.errors")).click();
        return this;
    }



    @Step("Submit registration with automatic retry on duplicate username")
    public RegistrationPage submitWithRetry(String baseUsername, String password, int retries) {
        for (int attempt = 1; attempt <= retries; attempt++) {
            clickSubmitRegistrationFormButton();


            long deadline = System.currentTimeMillis() + 6000;
            boolean handled = false;

            while (System.currentTimeMillis() < deadline) {

                if ($(byText("Your account was created successfully. You are now logged in.")).exists()
                        || com.codeborne.selenide.WebDriverRunner.url().contains("/overview.htm")
                        || $("#rightPanel").has(text("Welcome"))) {

                    return this;
                }


                if ($("#customer\\.username\\.errors").exists()
                        && $("#customer\\.username\\.errors").has(text("already exists"))) {
                    System.out.println("Username already exists. Retrying with a new one...");
                    String newUsername = baseUsername + "_" + attempt + "_" + (System.currentTimeMillis() % 10000);

                    CustomerUsernameField().clear();
                    CustomerUsernameField().sendKeys(newUsername);


                    CustomerPasswordField().clear();
                    CustomerPasswordField().sendKeys(password);
                    CustomerPasswordValidationField().clear();
                    CustomerPasswordValidationField().sendKeys(password);

                    handled = true;
                    break;
                }


                if ($("h1.title").exists() && $("h1.title").has(text("Error!"))) {
                    System.out.println("ParaBank internal error page detected, re-opening registration...");
                    openRegistrationPage();
                    handled = true;
                    break;
                }

                sleep(200);
            }

            if (handled) {
                continue;
            }


            if (com.codeborne.selenide.WebDriverRunner.url().contains("/overview.htm")
                    || $("#rightPanel").has(text("Welcome"))
                    || $(byText("Your account was created successfully. You are now logged in.")).exists()) {
                return this;
            }


            System.out.println("No definitive outcome detected, retrying submit (attempt " + attempt + " of " + retries + ")...");
        }


        throw new AssertionError("Registration failed after retries");
    }
}






