package tests.web;

import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.TransferFundsPage;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TransferFundsTests extends BaseTest {

    MainPage ParaBankLogin = new MainPage();
    TransferFundsPage ParaBankTransfer = new TransferFundsPage();

    @Test
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
    @Test
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
