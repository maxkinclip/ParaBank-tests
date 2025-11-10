package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RequestLoanPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/transfer.htm";
    }

    @Step("Open Transfer Page")
    public RequestLoanPage openRequestLoanPage() {
        openPage();
        return this.openRequestLoanPage();
    }

    @Step("Type in the loan amount in the field")
    public RequestLoanPage fillLoanAmountField(int amount) {
        loanAmountField().sendKeys(String.valueOf(amount));

        return this;
    }
    public SelenideElement loanAmountField() {
        return Selenide.$("#amount");
    }

    @Step("Type in the down payment amount in the field")
    public RequestLoanPage fillDownPaymentField(int amount) {
        downPaymentField().sendKeys(String.valueOf(amount));

        return this;
    }
    public SelenideElement downPaymentField() {
        return Selenide.$("#downPayment");
    }

    @Step("Choose the account for payments")
    public RequestLoanPage selectLoanAccount(int index) {
        selectLoanAccountByIndex(index);

        return this;
    }
    public void selectLoanAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    @Step("Click transfer button")
    public RequestLoanPage clickApplyButton() {
        applyButton().click();
        return this;
    }

    public SelenideElement applyButton() {
        return Selenide.$("input[type='button'][value='Apply Now']");
    }

    @Step("Receive loan approved message")
    public RequestLoanPage receiveLoanApprovedMessage() {
        loanApprovedMessage().click();
        return this;
    }

    public SelenideElement loanApprovedMessage() {
        return Selenide.$("#loanStatus").shouldHave(text("Approved"));
    }

    @Step("Receive loan denied due to insufficient funds message")
    public RequestLoanPage clickLoanDeniedInsufficientFundsMessage() {
        loanDeniedInsufficientFundsMessage().click();
        return this;
    }

    public SelenideElement loanDeniedInsufficientFundsMessage() {
        return Selenide.$("#loanRequestDenied").shouldHave(text("You do not have sufficient funds for the given down payment."));
    }








}
