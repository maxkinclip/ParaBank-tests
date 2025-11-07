package tests.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenAccountRequestModel {
    private int accountType;
    private long fromAccountId;
}