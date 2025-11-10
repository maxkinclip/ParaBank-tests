package tests.web;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.OpenAccountPage;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.open;

@Epic("ParaBank UI")
@Feature("Account Management")
@Story("Opening new accounts")
public class OpeningAccountTests extends BaseTest {
    MainPage ParaBankLogin = new MainPage();
    OpenAccountPage ParaBankOpenAccount = new OpenAccountPage();

    @Test
    @DisplayName("Open new checking account successfully")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void openCheckingAccount() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/openaccount.htm");
        ParaBankOpenAccount.selectTypeByIndex(0);
        ParaBankOpenAccount.selectAccountByIndex(1);
        ParaBankOpenAccount.clickOpenNewAccountButton();
        ParaBankOpenAccount.accountResult();
    }

    @Test
    @DisplayName("Open new savings account successfully")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void openSavingsAccount() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/openaccount.htm");
        ParaBankOpenAccount.selectTypeByIndex(1);
        ParaBankOpenAccount.selectAccountByIndex(1);
        ParaBankOpenAccount.clickOpenNewAccountButton();
        ParaBankOpenAccount.accountResult();
    }
}
