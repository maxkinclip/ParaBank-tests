package tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.api.clients.CustomerClient;
import tests.api.models.response.CustomerModel;
import tests.api.utils.XmlUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTestsAPI {

    CustomerClient customerClient = new CustomerClient();

    @Test
    public void loginWithDemoAccount() {
        Response response = customerClient.login("john", "demo");
        CustomerModel customer = XmlUtils.fromXml(response, CustomerModel.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(customer.getFirstName()).isEqualTo("John");
        assertThat(customer.getLastName()).isEqualTo("Smith");
        assertThat(customer.getAddress().getCity()).isNotEmpty();
    }
}