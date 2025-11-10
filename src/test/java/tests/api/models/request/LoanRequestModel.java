package tests.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class LoanRequestModel {
    private long customerId;
    private double amount;
    private double downPayment;
    private String fromAccountId;
}