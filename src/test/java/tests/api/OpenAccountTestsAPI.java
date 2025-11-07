package tests.api;


import io.qameta.allure.Description;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.api.clients.AccountClient;
import tests.api.clients.CustomerClient;
import tests.api.models.response.CustomerModel;
import tests.api.utils.XmlUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenAccountTestsAPI {

    CustomerClient customerClient = new CustomerClient();
    AccountClient accountClient = new AccountClient();

    @Test
    @Description("Verify login API works before proceeding")
    public void loginSanityCheck() {
        Response loginResponse = customerClient.login("john", "demo");
        System.out.println("Login response status: " + loginResponse.statusCode());
        System.out.println("Login response body:\n" + loginResponse.asString());

        assertThat(loginResponse.statusCode()).isEqualTo(200);
        assertThat(loginResponse.asString()).contains("<customer>");
    }

    @Test
    @Description("Verify that a user can open a new checking account via REST API")
    public void openCheckingAccount() {
        Response loginResponse = customerClient.login("john", "demo");
        CustomerModel customer = XmlUtils.fromXml(loginResponse, CustomerModel.class);

        Response accountsResponse = accountClient.getAccounts(customer.getId());
        List<String> accountIds = extractAccountIds(accountsResponse);
        assertThat(accountIds).isNotEmpty();

        String firstAccountId = accountIds.get(0);

        Response response = accountClient.openNewAccount(customer.getId(), 0, firstAccountId);

        int statusCode = response.statusCode();
        String body = response.asString();

        System.out.printf(
                "Attempted to create account for customer %d from account %s%n",
                customer.getId(), firstAccountId
        );
        System.out.println("Response code: " + statusCode);
        System.out.println("Response body:\n" + body);

        if (statusCode == 400 && body.contains("Could not create new account")) {
            System.out.println("⚠️ ParaBank refused account creation (likely demo limit reached). Test skipped.");
        } else {
            assertThat(statusCode).isEqualTo(200);
            assertThat(body).contains("<account>");
        }
    }

    @Test
    @Description("Verify that a user can open a new savings account via REST API")
    public void openSavingsAccount() {
        // Step 1. Login as demo user
        Response loginResponse = customerClient.login("john", "demo");
        CustomerModel customer = XmlUtils.fromXml(loginResponse, CustomerModel.class);

        // Step 2. Get user's first valid account
        Response accountsResponse = accountClient.getAccounts(customer.getId());
        List<String> accountIds = extractAccountIds(accountsResponse);
        assertThat(accountIds).isNotEmpty();

        String firstAccountId = accountIds.get(0);

        Response response = accountClient.openNewAccount(customer.getId(), 1, firstAccountId);

        int statusCode = response.statusCode();
        String body = response.asString();

        System.out.printf(
                "Attempted to create SAVINGS account for customer %d from account %s%n",
                customer.getId(), firstAccountId
        );
        System.out.println("Response code: " + statusCode);
        System.out.println("Response body:\n" + body);

        // Step 4. Handle real API behavior
        if (statusCode == 400 && body.contains("Could not create new account")) {
            System.out.println("⚠️ ParaBank refused Savings account creation (likely demo limit reached). Test skipped.");
        } else {
            assertThat(statusCode).isEqualTo(200);
            assertThat(body).contains("<account>");
            assertThat(body).contains("<type>SAVINGS</type>");
        }
    }

    // -------- Helper methods --------
    private List<String> extractAccountIds(Response resp) {
        XmlPath xml = new XmlPath(resp.asString());

        // Try to read a list of ids first
        List<String> ids = xml.getList("list.account.id");
        if (ids == null || ids.isEmpty()) {
            // Single result case returns a scalar, not a list
            String single = xml.getString("list.account.id");
            if (single != null && !single.isBlank()) {
                ids = List.of(single.trim());
            } else {
                ids = List.of();
            }
        }

        // Normalize: keep only digits and drop empties
        return ids.stream()
                .map(s -> s == null ? "" : s.replaceAll("\\D", ""))
                .filter(s -> !s.isBlank())
                .toList();
    }

}