package tests.api.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferRequestModel {
    private int fromAccountId;
    private int toAccountId;
    private double amount;
}