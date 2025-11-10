package tests.api;

import data.TestDataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.clients.RegistrationClient;
import tests.api.models.request.RegistrationRequestModel;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("ParaBank API")
@Feature("Registration")
@Story("Customer registration via API")
public class RegistrationTestsAPI {

    RegistrationClient registrationClient = new RegistrationClient();

    @Test
    @DisplayName("Register new customer successfully")
    @Description("Verify user registration succeeds with valid data")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void successfulRegistrationAPI() {
        RegistrationRequestModel user = TestDataGenerator.generateValidUser();

        Response response = registrationClient.registerUser(user);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.asString())
                .contains("Your account was created successfully");
    }

    @Test
    @DisplayName("Registration fails because of mismatched passwords")
    @Description("Verify registration fails with mismatched passwords")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void failedRegistrationMismatchedPasswords() {
        RegistrationRequestModel user = TestDataGenerator.failedRegistrationMismatchedPasswords();

        Response response = registrationClient.registerUser(user);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.asString())
                .contains("Passwords did not match");
    }
}