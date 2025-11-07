package tests.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LoanRequestModel {
    private long customerId;     // from login
    private double amount;       // loan amount
    private double downPayment;  // down payment
    private String fromAccountId; // STRING account id (funding account)
}