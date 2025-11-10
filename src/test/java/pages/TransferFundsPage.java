package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferFundsPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/transfer.htm";
    }

    @Step("Open Transfer Page")
    public TransferFundsPage openTransferPage() {
        openPage();
        return this.openTransferPage();
    }

    private SelenideElement rightPanelTitle() {
        return $("#rightPanel .title");
    }

    @Step("Type in the amount in the field")
    public TransferFundsPage writeTheAmountToSend(int amount) {
        amountField().sendKeys(String.valueOf(amount));
        return this;
    }

    public SelenideElement amountField() {
        return $("#amount");
    }

    @Step("Choose the account for withdraw")
    public TransferFundsPage transferFromAccount(int index) {
        transferFromAccountByIndex(index);
        return this;
    }

    public void transferFromAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    @Step("Choose the account for deposit")
    public TransferFundsPage transferToAccount(int index) {
        transferToAccountByIndex(index);
        return this;
    }

    public void transferToAccountByIndex(int index) {
        $("#toAccountId").selectOption(index);
    }

    @Step("Click transfer button")
    public TransferFundsPage clickTransferButton() {
        transferButton().should(appear, enabled).click();
        return this;
    }

    public SelenideElement transferButton() {
        return $("input[type='submit'][value='Transfer']");
    }

    @Step("Receive successful transfer message")
    public TransferFundsPage receiveSuccessfulTransferMessage() {
        SuccessfulTransferMessage().click();
        return this;
    }

    public SelenideElement SuccessfulTransferMessage() {
        return $("#showResult").$(byText("Transfer Complete!"));
    }

    @Step("Receive successful transfer message")
    public TransferFundsPage receiveTransferErrorMessage() {
        TransferErrorMessage().click();
        return this;
    }


    public SelenideElement TransferErrorMessage() {
        return $("#showError").$(withText("An internal error has occurred and has been logged"));
    }









}
