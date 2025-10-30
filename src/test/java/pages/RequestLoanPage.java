package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;

public class RequestLoanPage {
    public SelenideElement loanAmountField() {
        return Selenide.$("amount");
    }

    public SelenideElement downPaymentField() {
        return Selenide.$("downPayment");
    }

    public SelenideElement transferButton() {
        return Selenide.$(byText("Transfer"));
    }




}
