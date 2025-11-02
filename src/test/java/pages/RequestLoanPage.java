package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RequestLoanPage {
    public SelenideElement loanAmountField() {
        return Selenide.$("amount");
    }

    public SelenideElement downPaymentField() {
        return Selenide.$("downPayment");
    }

    public void selectLoanAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    public SelenideElement transferButton() {
        return Selenide.$(byText("Transfer"));
    }

    @Step("Type in the loan amount in the field")
    public RequestLoanPage FillLoanAmountField() {
        loanAmountField().sendKeys();

        return this;
    }

    @Step("Type in the down payment amount in the field")
    public RequestLoanPage fillDownPaymentField() {
        downPaymentField().sendKeys();

        return this;
    }

    @Step("Choose the account for payments")
    public RequestLoanPage selectLoanType(int index) {
        selectLoanAccountByIndex(index);

        return this;
    }




}
