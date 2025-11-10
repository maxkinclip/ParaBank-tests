package tests.web;

import data.TestDataGenerator;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import tests.BaseTest;
import tests.api.models.request.RegistrationRequestModel;

@Epic("ParaBank UI")
@Feature("Registration")
@Story("User Registration Flow")
public class RegistrationTests extends BaseTest {
    RegistrationPage ParaBankRegistration = new RegistrationPage();

    @Test
    @DisplayName("User registers successfully with valid data")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
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
    @DisplayName("Error message appears for mismatched passwords")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
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
