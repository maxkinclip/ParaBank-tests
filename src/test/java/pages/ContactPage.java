package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;

public class ContactPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/contact.htm";
    }

    @Step("Open Contact Page")
    public ContactPage openContactPage() {
        openPage();
        return this;
    }

    @Step("Fill name field with '{name}'")
    public ContactPage fillNameField(String name) {
        nameField().sendKeys(name);
        return this;
    }


    public SelenideElement nameField() {
        return Selenide.$("#name");
    }
    @Step("Fill email field with '{email}'")
    public ContactPage fillEmailField(String email) {
        emailField().sendKeys(email);
        return this;
    }

    public SelenideElement emailField() {
        return Selenide.$("#email");
    }

    @Step("Fill phone field with '{phone}'")
    public ContactPage fillPhoneField(String phone) {
        phoneField().sendKeys(phone);
        return this;
    }

    public SelenideElement phoneField() {
        return Selenide.$("#phone");
    }

    @Step("Fill message field with '{message}'")
    public ContactPage fillMessageField(String message) {
        messageField().sendKeys(message);
        return this;
    }

    public SelenideElement messageField() {
        return Selenide.$("#message");
    }

    @Step("Click submit button to send the message to the Customer Care")
    public ContactPage clickSubmitToCustomerCareButton() {
        submitToCustomerCareButton().click();
        return this;
    }

    public SelenideElement submitToCustomerCareButton() {
        return Selenide.$("input[type='submit'][value='Send to Customer Care']");
    }

    @Step("Fill Contact Form")
    public ContactPage fillContactForm(String name, String email, String phoneNumber, String message){
        return fillNameField(name)
                .fillEmailField(email)
                .fillPhoneField(phoneNumber)
                .fillMessageField(message);

    }

    @Step("Verify that the message was sent to the Customer Care")
    public ContactPage verifyMessageToCustomerCare() {
        theMessageWasSentAlert().click();
        return this;
    }

    public SelenideElement theMessageWasSentAlert() {
        return Selenide.$(byText("A Customer Care Representative will be contacting you."));
    }





}
