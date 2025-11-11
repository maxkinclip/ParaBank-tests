package tests.web;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RequestLoanPage;
import tests.BaseTest;


@Epic("ParaBank UI")
@Feature("Loan Requests")
@Story("Requesting loans via UI")
public class RequestLoanTests extends BaseTest {

    MainPage ParaBankLogin = new MainPage();

    RequestLoanPage ParaBankLoanRequest = new RequestLoanPage();

    @Test
    @DisplayName("Request loan successfully with valid data")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void loanApproved() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john", "demo")
                .loginVerification();

        ParaBankLoanRequest
                .openRequestLoanPage()
                .fillLoanAmountField(1000)
                .fillDownPaymentField(50)
                .selectLoanAccount(0)
                .clickApplyButton()
                .receiveLoanApprovedMessage();
    }

    @Test
    @DisplayName("Loan request denied because of insufficient funds ")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void loanDeniedInsufficientFunds() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john", "demo")
                .loginVerification();

        ParaBankLoanRequest
                .openRequestLoanPage()
                .fillLoanAmountField(999999999)
                .fillDownPaymentField(999999999)
                .selectLoanAccount(0)
                .clickApplyButton()
                .receiveLoanDeniedInsufficientFundsMessage();
    }

}


