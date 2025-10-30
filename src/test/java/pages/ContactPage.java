package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;

public class ContactPage {

    public SelenideElement nameField() {
        return Selenide.$("name");
    }

    public SelenideElement emailField() {
        return Selenide.$("email");
    }

    public SelenideElement phoneField() {
        return Selenide.$("phone");
    }

    public SelenideElement messageField() {
        return Selenide.$("message");
    }

    public SelenideElement submitToCustomerCareButton() {
        return Selenide.$(byText("Send to Customer Care");
    }

    @Step("Fill name field with '{name}'")
    public ContactPage fillNameField(String name) {
        nameField().sendKeys(name);
        return this;
    }

    @Step("Fill email field with '{email}'")
    public ContactPage fillEmailField(String email) {
        emailField().sendKeys(email);
        return this;
    }

    @Step("Fill phone field with '{phone}'")
    public ContactPage fillPhoneField(String phone) {
        phoneField().sendKeys(phone);
        return this;
    }

    @Step("Fill message field with '{message}'")
    public ContactPage fillMessageField(String message) {
        messageField().sendKeys(message);
        return this;
    }

    @Step("Click submit button to send the message to the Customer Care")
    public ContactPage clickSubmitToCustomerCareButton() {
        submitToCustomerCareButton().click();
        return this;
    }



}
