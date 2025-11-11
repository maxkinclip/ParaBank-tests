package tests.web;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import tests.BaseTest;


@Epic("ParaBank UI")
@Feature("Login")
@Story("User Authentication")
public class LoginTests extends BaseTest {
    MainPage ParaBankLogin = new MainPage();

    @Test
    @DisplayName("Successful login with valid credentials")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithDemoAccount() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
    }

    @Test
    @DisplayName("Error for invalid password")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void loginError() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","wrong-password")
                .verifyInvalidLoginError();


    }
}



