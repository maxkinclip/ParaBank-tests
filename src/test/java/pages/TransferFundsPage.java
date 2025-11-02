package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferFundsPage {

    public SelenideElement amountField() {
        return $("amount");
    }

    public void transferFromAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    public void transferToAccountByIndex(int index) {
        $("#toAccountId").selectOption(index);
    }

    public SelenideElement transferButton() {
        return $(byText("amount"));
    }

    @Step("Type in the amount in the field")
    public TransferFundsPage ClickSubmitRegistrationFormButton() {
        amountField().sendKeys();

        return this;
    }

    @Step("Choose the account you're going to withdraw the money from")
    public TransferFundsPage transferFromAccount(int index) {
        transferFromAccountByIndex(index);
        return this;
    }

    @Step("Choose the account you're going to deposit money to")
    public TransferFundsPage transferToAccount(int index) {
        transferToAccountByIndex(index);
        return this;
    }

    @Step("Click transfer button")
    public TransferFundsPage clickTransferButton() {
        transferButton().click();
        return this;
    }








}
