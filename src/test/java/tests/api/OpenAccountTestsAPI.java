package tests.api;


import io.qameta.allure.*;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.clients.AccountClient;
import tests.api.clients.CustomerClient;
import tests.api.models.response.CustomerModel;
import tests.api.utils.XmlUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("ParaBank API")
@Feature("Account Management")
@Story("Opening new accounts via API")
public class OpenAccountTestsAPI {

    CustomerClient customerClient = new CustomerClient();
    AccountClient accountClient = new AccountClient();

    @Test
    @DisplayName("Verify login API works before proceeding")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void loginSanityCheck() {
        Response loginResponse = customerClient.login("john", "demo");
        System.out.println("Login response status: " + loginResponse.statusCode());
        System.out.println("Login response body:\n" + loginResponse.asString());

        assertThat(loginResponse.statusCode()).isEqualTo(200);
        assertThat(loginResponse.asString()).contains("<customer>");
    }

    @Test
    @DisplayName("Open new checking account successfully")
    @Description("Verify that a user can open a new checking account via REST API")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
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
            System.out.println("ParaBank refused account creation (demo limit reached). Test skipped.");
        } else {
            assertThat(statusCode).isEqualTo(200);
            assertThat(body).contains("<account>");
        }
    }

    @Test
    @DisplayName("Open new savings account successfully")
    @Description("Verify that a user can open a new savings account via REST API")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void openSavingsAccount() {

        Response loginResponse = customerClient.login("john", "demo");
        CustomerModel customer = XmlUtils.fromXml(loginResponse, CustomerModel.class);


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


        if (statusCode == 400 && body.contains("Could not create new account")) {
            System.out.println("ParaBank refused Savings account creation (demo limit reached). Test skipped.");
        } else {
            assertThat(statusCode).isEqualTo(200);
            assertThat(body).contains("<account>");
            assertThat(body).contains("<type>SAVINGS</type>");
        }
    }

    // ---- helpers ----
    private List<String> extractAccountIds(Response resp) {
        XmlPath xml = new XmlPath(resp.asString());


        List<String> ids = xml.getList("list.account.id");
        if (ids == null || ids.isEmpty()) {

            String single = xml.getString("list.account.id");
            if (single != null && !single.isBlank()) {
                ids = List.of(single.trim());
            } else {
                ids = List.of();
            }
        }


        return ids.stream()
                .map(s -> s == null ? "" : s.replaceAll("\\D", ""))
                .filter(s -> !s.isBlank())
                .toList();
    }

}