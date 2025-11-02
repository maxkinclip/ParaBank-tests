package tests.web;

import data.TestDataGenerator;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import tests.BaseTest;
import tests.api.models.RegistrationRequestModel;

public class RegistrationTests extends BaseTest {
    RegistrationPage ParaBankRegistration = new RegistrationPage();

    @Test
    public void RegistrationSuccess(){
        RegistrationRequestModel userData = TestDataGenerator.generateValidUser();
        ParaBankRegistration.openRegistrationPage().fillRegistrationForm(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getAddress(),
                userData.getCity(),
                userData.getState(),
                userData.getZipCode(),
                userData.getPhoneNumber(),
                userData.getSsnNumber(),
                userData.getUserName(),
                userData.getPassword(),
                userData.getPasswordValidation()

        ).clickSubmitRegistrationFormButton().verifySuccessfulRegistrationMessage();
    }



    @Test
    public void RegistrationFailed(){
        RegistrationRequestModel userData = TestDataGenerator.failedRegistrationMismatchedPasswords();
        ParaBankRegistration.openRegistrationPage().fillRegistrationForm(
                userData.getFirstName(),
                userData.getLastName(),
                userData.getAddress(),
                userData.getCity(),
                userData.getState(),
                userData.getZipCode(),
                userData.getPhoneNumber(),
                userData.getSsnNumber(),
                userData.getUserName(),
                userData.getPassword(),
                userData.getPasswordValidation()

        ).clickSubmitRegistrationFormButton().verifyPasswordsDoNotMatchError();
    }

}
