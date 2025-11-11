package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class OpenAccountPage extends BasePage {

    private final SelenideElement title         = $("#rightPanel .title");
    private final SelenideElement typeSelect    = $("#type");
    private final SelenideElement fromAccount   = $("#fromAccountId");
    private final SelenideElement openBtn       = Selenide.$("input[type='button'][value='Open New Account']");

    @Override
    protected String getPageUrl() {
        return "/parabank/openaccount.htm";
    }

    @Step("Open 'Open New Account' page")
    public OpenAccountPage openAccountPage() {
        openPage();

        if (!url().contains("openaccount.htm")) {
            open(getPageUrl());
        }

        title.should(appear)
                .shouldHave(or("title", text("Open New Account"), text("What type of Account would you like to open?")));

        $("#type").should(appear);
        $("#fromAccountId").should(appear);
        Selenide.$("input[type='button'][value='Open New Account']").should(appear);

        return this;
    }



    @Step("Choose the type of account to open (index={index})")
    public OpenAccountPage selectTypeByIndex(int index) {
        typeSelect.should(appear).selectOption(index);
        return this;
    }

    @Step("Choose source account by index {index}")
    public OpenAccountPage selectAccountByIndex(int index) {
        fromAccount.should(appear).selectOption(index);
        return this;
    }

    @Step("Click 'Open New Account'")
    public OpenAccountPage submitOpen() {
        openBtn.should(appear).click();
        return this;
    }

    @Step("Verify seeing the message about opening new account")
    public OpenAccountPage accountResult() {
        openAccountResult().should(appear);
        return this;
    }
    public SelenideElement openAccountResult() {
        return $("#openAccountResult");
    }


}


