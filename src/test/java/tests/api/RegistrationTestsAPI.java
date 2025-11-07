package tests.api;

import data.TestDataGenerator;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.api.clients.RegistrationClient;
import tests.api.models.request.RegistrationRequestModel;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTestsAPI {

    RegistrationClient registrationClient = new RegistrationClient();

    @Test
    @Description("Verify user registration succeeds with valid data")
    public void successfulRegistrationAPI() {
        RegistrationRequestModel user = TestDataGenerator.generateValidUser();

        Response response = registrationClient.registerUser(user);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.asString())
                .contains("Your account was created successfully");
    }

    @Test
    @Description("Verify registration fails with mismatched passwords")
    public void failedRegistrationMismatchedPasswords() {
        RegistrationRequestModel user = TestDataGenerator.failedRegistrationMismatchedPasswords();

        Response response = registrationClient.registerUser(user);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.asString())
                .contains("Passwords did not match");
    }
}