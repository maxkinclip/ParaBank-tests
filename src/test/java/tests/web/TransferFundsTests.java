package tests.web;

import io.qameta.allure.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.TransferFundsPage;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Epic("ParaBank UI")
@Feature("Fund Transfers")
@Story("Transferring money between accounts")
public class TransferFundsTests extends BaseTest {

    MainPage ParaBankLogin = new MainPage();
    TransferFundsPage ParaBankTransfer = new TransferFundsPage();

    @Test
    @DisplayName("Transfer funds between valid accounts")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.BLOCKER)
    public void successfulTransfer() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/transfer.htm");
        ParaBankTransfer
                .writeTheAmountToSend(50)
                .transferFromAccount(0)
                .transferToAccount(0)
                .clickTransferButton()
                .receiveSuccessfulTransferMessage();
    }

    @Issue("PARA-405")
    @Test
    @Disabled("ParaBank currently doesn't trigger 'insufficient funds' error")
    @DisplayName("Error when transferring with insufficient balance")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void TransferError() {
        ParaBankLogin.openMainPage()
                .fillLoginForm("john","demo")
                .loginVerification();
        open("/parabank/transfer.htm");
        $("#amount").sendKeys("9999999999999999");
        ParaBankTransfer
                .transferFromAccount(0)
                .transferToAccount(0)
                .clickTransferButton().receiveTransferErrorMessage();
    }
}
