package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class RequestLoanPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/requestloan.htm";
    }

    @Step("Open Request Loan Page")
    public RequestLoanPage openRequestLoanPage() {
        openPage();


        if (!url().contains("requestloan.htm")) {
            open(getPageUrl());
        }


        $("#rightPanel .title")
                .should(appear)
                .shouldHave(or("title", text("Request Loan"), text("Apply for a Loan")));

        $("#amount").should(appear);
        $("#downPayment").should(appear);
        $("#fromAccountId").should(appear);

        return this;
    }

    @Step("Type in the loan amount in the field")
    public RequestLoanPage fillLoanAmountField(int amount) {
        loanAmountField().should(appear).setValue(String.valueOf(amount));
        return this;
    }

    public SelenideElement loanAmountField() {
        return Selenide.$("#amount");
    }

    @Step("Type in the down payment amount in the field")
    public RequestLoanPage fillDownPaymentField(int amount) {
        downPaymentField().should(appear).setValue(String.valueOf(amount));
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
        $("#fromAccountId").should(appear).selectOption(index);
    }

    @Step("Click Apply button")
    public RequestLoanPage clickApplyButton() {
        applyButton().should(appear).click();
        return this;
    }

    public SelenideElement applyButton() {
        return Selenide.$("input[type='button'][value='Apply Now']");
    }

    @Step("Verify loan approved message")
    public RequestLoanPage receiveLoanApprovedMessage() {
        loanApprovedMessage().should(appear);
        return this;
    }

    public SelenideElement loanApprovedMessage() {
        return Selenide.$("#loanStatus").shouldHave(text("Approved"));
    }

    @Step("Verify loan denied due to insufficient funds message")
    public RequestLoanPage receiveLoanDeniedInsufficientFundsMessage() {
        loanDeniedInsufficientFundsMessage().should(appear);
        return this;
    }

    public SelenideElement loanDeniedInsufficientFundsMessage() {
        return Selenide.$("#loanRequestDenied")
                .shouldHave(text("You do not have sufficient funds for the given down payment."));
    }
}






