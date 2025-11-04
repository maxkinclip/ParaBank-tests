package tests.web;

import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RequestLoanPage;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.open;

public class RequestLoanTests extends BaseTest {

    MainPage ParaBankLogin = new MainPage();

    RequestLoanPage ParaBankLoanRequest = new RequestLoanPage();

    @Test
    public void loanApproved() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/requestloan.htm");
        ParaBankLoanRequest
                .fillLoanAmountField(1000)
                .fillDownPaymentField(50)
                .selectLoanAccount(0)
                .clickApplyButton()
                .receiveLoanApprovedMessage();
    }

    @Test
    public void loanDeniedInsufficientFunds() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/requestloan.htm");
        ParaBankLoanRequest
                .fillLoanAmountField(999999999)
                .fillDownPaymentField(999999999)
                .selectLoanAccount(0)
                .clickApplyButton()
                .loanDeniedInsufficientFundsMessage();
    }

}


