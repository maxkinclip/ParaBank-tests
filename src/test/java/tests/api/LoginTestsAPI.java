package tests.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.clients.CustomerClient;
import tests.api.models.response.CustomerModel;
import tests.api.utils.XmlUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("ParaBank API")
@Feature("Authentication")
@Story("Customer login via API")
public class LoginTestsAPI {

    CustomerClient customerClient = new CustomerClient();

    @Test
    @DisplayName("Login successful with demo account")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithDemoAccount() {
        Response response = customerClient.login("john", "demo");
        CustomerModel customer = XmlUtils.fromXml(response, CustomerModel.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(customer.getFirstName()).isEqualTo("John");
        assertThat(customer.getLastName()).isEqualTo("Smith");
        assertThat(customer.getAddress().getCity()).isNotEmpty();
    }
}