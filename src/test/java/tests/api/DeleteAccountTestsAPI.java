package tests.api;

import io.qameta.allure.*;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.clients.AccountClient;
import tests.api.clients.AccountDeleteClient;
import tests.api.clients.CustomerClient;
import tests.api.models.response.AccountDeleteResponseModel;
import tests.api.models.response.CustomerModel;
import tests.api.utils.XmlUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("ParaBank API")
@Feature("Account Management")
@Story("Deleting customer accounts via API")
public class DeleteAccountTestsAPI {

    CustomerClient customerClient = new CustomerClient();
    AccountClient  accountClient  = new AccountClient();
    AccountDeleteClient deleteClient = new AccountDeleteClient();

    @Issue("PARA-405")
    @Test
    @Disabled("ParaBank DELETE endpoint currently not supported")
    @DisplayName("Delete existing account successfully")
    @Description("DELETE: closing a customer account (hypothetical scenario)")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteAccountSuccess() {
        // Arrange
        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);


        Response accountsRes = accountClient.getAccounts(customer.getId());
        assertThat(accountsRes.statusCode()).isEqualTo(200);
        XmlPath xml = new XmlPath(accountsRes.asString());
        String accountId = xml.getString("list.account[0].id").replaceAll("\\D", "");
        assertThat(accountId).isNotBlank();


        Response deleteRes = deleteClient.deleteAccount(accountId);
        System.out.println("DELETE response code: " + deleteRes.statusCode());
        System.out.println(deleteRes.asString());


        assertThat(deleteRes.statusCode()).isEqualTo(200);
        AccountDeleteResponseModel result = XmlUtils.fromXml(deleteRes, AccountDeleteResponseModel.class);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getClosedAccountId()).isEqualTo(accountId);
    }
    @Issue("PARA-405")
    @Test
    @Disabled("ParaBank DELETE endpoint currently not supported")
    @DisplayName("Error when deleting non-existing account")
    @Description("DELETE: Attempt to delete a non-existent account (error 404)")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void deleteAccountNotFound() {
        Response deleteRes = deleteClient.deleteAccount("999999");
        assertThat(deleteRes.statusCode()).isEqualTo(404);
    }
}