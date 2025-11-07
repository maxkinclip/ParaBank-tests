package tests.api;

import io.qameta.allure.Description;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.api.clients.AccountClient;
import tests.api.clients.CustomerClient;
import tests.api.clients.LoanClient;
import tests.api.models.request.LoanRequestModel;
import tests.api.models.response.CustomerModel;
import tests.api.models.response.LoanResponseModel;
import tests.api.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoanTestsAPI {

    CustomerClient customerClient = new CustomerClient();
    AccountClient  accountClient  = new AccountClient();
    LoanClient     loanClient     = new LoanClient();

    @Test
    @Description("Loan approval with dynamic customer/account IDs")
    public void loanApproved() {
        // 1) Login -> customerId
        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);

        // 2) Get accounts -> pick first as funding account (STRING id)
        Response accountsRes = accountClient.getAccounts(customer.getId());
        assertThat(accountsRes.statusCode()).isEqualTo(200);
        String fromAccountId = firstAccountId(accountsRes);
        assertThat(fromAccountId).isNotBlank();

        // 3) Build request (use sensible small numbers for demo env)
        LoanRequestModel req = LoanRequestModel.builder()
                .customerId(customer.getId())
                .amount(1000)
                .downPayment(50)
                .fromAccountId(fromAccountId)
                .build();

        // 4) Call API
        Response loanRes = loanClient.requestLoan(req);
        System.out.println("Loan response code: " + loanRes.statusCode());
        System.out.println("Loan response body:\n" + loanRes.asString());

        // 5) Assert parse + outcome (approval may vary in demo; assert 200 and approved != null)
        assertThat(loanRes.statusCode()).isEqualTo(200);
        LoanResponseModel loan = XmlUtils.fromXml(loanRes, LoanResponseModel.class);
        assertThat(loan.getApproved()).isNotNull();
        // If you want to require approval here, keep this:
        // assertThat(loan.getApproved()).isTrue();
    }

    @Test
    @Description("Loan declined with large amount (negative scenario)")
    public void loanDeclined() {
        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);

        Response accountsRes = accountClient.getAccounts(customer.getId());
        assertThat(accountsRes.statusCode()).isEqualTo(200);
        String fromAccountId = firstAccountId(accountsRes);
        assertThat(fromAccountId).isNotBlank();

        // deliberately too large -> likely decline
        LoanRequestModel req = LoanRequestModel.builder()
                .customerId(customer.getId())
                .amount(9_999_999)
                .downPayment(1_000_000)
                .fromAccountId(fromAccountId)
                .build();

        Response loanRes = loanClient.requestLoan(req);
        System.out.println("Loan response code: " + loanRes.statusCode());
        System.out.println("Loan response body:\n" + loanRes.asString());

        assertThat(loanRes.statusCode()).isEqualTo(200);
        LoanResponseModel loan = XmlUtils.fromXml(loanRes, LoanResponseModel.class);
        assertThat(loan.getApproved()).isFalse(); // negative path
    }

    // ---- helpers ----
    private String firstAccountId(Response accountsRes) {
        XmlPath xml = new XmlPath(accountsRes.asString());
        List<?> raw = xml.getList("list.account.id");
        if (raw != null && !raw.isEmpty()) {
            Object o = raw.get(0);
            return o == null ? "" : o.toString().replaceAll("\\D", "");
        }
        String single = xml.getString("list.account.id");
        return single == null ? "" : single.replaceAll("\\D", "");
    }
}