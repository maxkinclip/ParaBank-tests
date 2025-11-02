package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class OpenAccountPage {

    public void selectTypeByIndex(int index) {
        $("#type").selectOption(index);
    }

    public void selectAccountByIndex(int index) {
        $("#fromAccountId").selectOption(index);
    }

    public SelenideElement openNewAccountButton () {
        return Selenide.$(byText("Open New Account"));
    }

    public SelenideElement openAccountResult() {
        return $("openAccountResult");
    }

    @Step("Choose the type of account to open")
    public OpenAccountPage selectType(int index) {
        selectTypeByIndex(index);
        return this;
    }

    @Step("Select account for the first deposit")
    public OpenAccountPage selectAccount(int index) {
        selectAccountByIndex(index);
        return this;
    }

    @Step("Click the button to open new account")
    public OpenAccountPage selectAccount() {
        openNewAccountButton().click();
        return this;
    }

    @Step("Verify seeing the message about opening new account")
    public OpenAccountPage accountResult() {
        openAccountResult().click();
        return this;
    }


}


