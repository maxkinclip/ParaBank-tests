package tests.web;

import org.junit.jupiter.api.Test;
import pages.MainPage;
import tests.BaseTest;

public class LoginTests extends BaseTest {
    MainPage ParaBankLogin = new MainPage();

    @Test
    public void loginWithDemoAccount() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
    }

    @Test
    public void loginError() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","wrong-password")
                .verifyLoginError();


    }
}



