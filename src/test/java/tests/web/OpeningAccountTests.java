package tests.web;

import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.OpenAccountPage;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.open;

public class OpeningAccountTests extends BaseTest {
    MainPage ParaBankLogin = new MainPage();
    OpenAccountPage ParaBankOpenAccount = new OpenAccountPage();
    @Test
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
