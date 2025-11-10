package tests.api;

import io.qameta.allure.*;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

@Epic("ParaBank API")
@Feature("Loan Requests")
@Story("Customer requests a loan")
public class LoanTestsAPI {

    CustomerClient customerClient = new CustomerClient();
    AccountClient  accountClient  = new AccountClient();
    LoanClient     loanClient     = new LoanClient();


    @Test
    @DisplayName("Approve loan for valid customer and amount")
    @Description("Loan approval with dynamic customer/account IDs")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.CRITICAL)
    public void loanApproved() {


        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);


        Response accountsRes = accountClient.getAccounts(customer.getId());
        assertThat(accountsRes.statusCode()).isEqualTo(200);
        String fromAccountId = firstAccountId(accountsRes);
        assertThat(fromAccountId).isNotBlank();


        LoanRequestModel req = LoanRequestModel.builder()
                .customerId(customer.getId())
                .amount(1000)
                .downPayment(50)
                .fromAccountId(fromAccountId)
                .build();


        Response loanRes = loanClient.requestLoan(req);
        System.out.println("Loan response code: " + loanRes.statusCode());
        System.out.println("Loan response body:\n" + loanRes.asString());


        assertThat(loanRes.statusCode()).isEqualTo(200);
        LoanResponseModel loan = XmlUtils.fromXml(loanRes, LoanResponseModel.class);
        assertThat(loan.getApproved()).isNotNull();

    }

    @Test
    @DisplayName("Reject loan for invalid customer ID")
    @Description("Loan declined with large amount (negative scenario)")
    @Owner("maxkinclip")
    @Severity(SeverityLevel.NORMAL)
    public void loanDeclined() {
        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);

        Response accountsRes = accountClient.getAccounts(customer.getId());
        assertThat(accountsRes.statusCode()).isEqualTo(200);
        String fromAccountId = firstAccountId(accountsRes);
        assertThat(fromAccountId).isNotBlank();


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
        assertThat(loan.getApproved()).isFalse();
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

    private List<Long> getAccountIds(long customerId) {
        Response accountsRes = accountClient.getAccounts(customerId);
        assertThat(accountsRes.statusCode()).isEqualTo(200);

        XmlPath xml = new XmlPath(accountsRes.asString());


        List<String> raw = xml.getList("list.account.id");
        List<Long> ids = new ArrayList<>();
        if (raw != null && !raw.isEmpty()) {
            for (String s : raw) {
                if (s != null) {
                    s = s.replaceAll("\\D", "");
                    if (!s.isBlank()) {
                        try { ids.add(Long.parseLong(s)); } catch (Exception ignored) {}
                    }
                }
            }
        }


        if (ids.isEmpty()) {
            String single = xml.getString("list.account.id");
            if (single != null) {
                String onlyDigits = single.replaceAll("\\D", "");
                if (!onlyDigits.isBlank()) {
                    try { ids.add(Long.parseLong(onlyDigits)); } catch (Exception ignored) {}
                }
            }
        }

        System.out.println("Discovered account IDs: " + ids);
        return ids;
    }


    @ParameterizedTest(name = "Loan decision for amount={0}, downPayment={1}")
    @CsvSource({
            "1000, 50",
            "5000, 100",
            "9999999, 10"
    })
    void loanDecisionParameterized(double amount, double down) {

        Response loginRes = customerClient.login("john", "demo");
        assertThat(loginRes.statusCode()).isEqualTo(200);
        CustomerModel customer = XmlUtils.fromXml(loginRes, CustomerModel.class);


        List<Long> ids = getAccountIds(customer.getId());
        assertThat(ids).isNotEmpty();
        long fromAccountId = ids.get(0);


        LoanRequestModel req = LoanRequestModel.builder()
                .customerId(customer.getId())
                .amount(amount)
                .downPayment(down)
                .fromAccountId(String.valueOf(fromAccountId))
                .build();

        Response loanRes = loanClient.requestLoan(req);

        int code = loanRes.statusCode();
        String body = loanRes.asString();
        System.out.println("Loan request → code: " + code + "\nbody:\n" + body);


        assertThat(code).isEqualTo(200);

        LoanResponseModel loan = XmlUtils.fromXml(loanRes, LoanResponseModel.class);
        assertThat(loan.getApproved()).isNotNull();
    }

}
