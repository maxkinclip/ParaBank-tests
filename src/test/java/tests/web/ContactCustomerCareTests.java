package tests.web;

import data.TestDataGenerator;
import org.junit.jupiter.api.Test;
import pages.ContactPage;
import tests.BaseTest;
import tests.api.models.request.ContactRequestModel;

public class ContactCustomerCareTests extends BaseTest {
    ContactPage ParaBankCustomerCare = new ContactPage();

    @Test
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
