package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class OpenAccountPage extends BasePage {

    @Override
    protected String getPageUrl() {
        return "/parabank/openaccount.htm";
    }

    @Step("Open Account Page")
    public OpenAccountPage openAccountPage() {
        openPage();
        return this.openAccountPage();
    }

    @Step("Choose the type of account to open")
    public OpenAccountPage selectType(int index) {
        selectTypeByIndex(index);
        return this;
    }

    public void selectTypeByIndex(int index) {
        $("#type").selectOption(index);
    }

    @Step("Select account for the first deposit")
    public OpenAccountPage selectAccount(int index) {
        selectAccountByIndex(index);
        return this;
    }

    public void selectAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    @Step("Click the button to open new account")
    public OpenAccountPage clickOpenNewAccountButton() {
        openNewAccountButton().click();
        return this;
    }
    public SelenideElement openNewAccountButton () {
        return Selenide.$("input[type='button'][value='Open New Account']");
    }

    @Step("Verify seeing the message about opening new account")
    public OpenAccountPage accountResult() {
        openAccountResult().click();
        return this;
    }
    public SelenideElement openAccountResult() {
        return $("#openAccountResult");
    }


}


