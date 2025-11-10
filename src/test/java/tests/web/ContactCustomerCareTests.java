package tests.web;

import data.TestDataGenerator;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ContactPage;
import tests.BaseTest;
import tests.api.models.request.ContactRequestModel;


@Epic("ParaBank UI")
@Feature("Customer Care")
@Story("Contact Customer Support via form")
public class ContactCustomerCareTests extends BaseTest {
    ContactPage ParaBankCustomerCare = new ContactPage();

    @Test
    @DisplayName("Send message via Contact Us form successfully")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void sendCustomerCareMessage(){
        ContactRequestModel userData = TestDataGenerator.fillCustomerCareForm();
        ParaBankCustomerCare.openContactPage()
                .fillContactForm(
                        userData.getName(),
                        userData.getEmail(),
                        userData.getPhone(),
                        userData.getMessage()
                )
                .clickSubmitToCustomerCareButton()
                .theMessageWasSentAlert();
    }
}
