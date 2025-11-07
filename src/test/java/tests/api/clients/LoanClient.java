package tests.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;
import tests.api.models.request.LoanRequestModel;

public class LoanClient extends BaseApi {

    // Request using a model (preferred)
    public Response requestLoan(LoanRequestModel req) {
        return RestAssured.given()
                .spec(requestSpec) // base: https://parabank.parasoft.com/parabank/services/bank
                .queryParam("customerId",   req.getCustomerId())
                .queryParam("amount",       req.getAmount())
                .queryParam("downPayment",  req.getDownPayment())
                .queryParam("fromAccountId", req.getFromAccountId())
                .when()
                .post("/requestLoan");
    }

    // Convenience overload (optional)
    public Response requestLoan(long customerId, double amount, double downPayment, String fromAccountId) {
        return requestLoan(LoanRequestModel.builder()
                .customerId(customerId)
                .amount(amount)
                .downPayment(downPayment)
                .fromAccountId(fromAccountId)
                .build());
    }
}