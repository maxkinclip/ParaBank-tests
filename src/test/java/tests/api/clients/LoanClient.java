package tests.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;
import tests.api.models.request.LoanRequestModel;

public class LoanClient extends BaseApi {


    public Response requestLoan(LoanRequestModel req) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("customerId", req.getCustomerId())
                .queryParam("amount", req.getAmount())
                .queryParam("downPayment", req.getDownPayment())
                .queryParam("fromAccountId", req.getFromAccountId())
                .when()
                .post("/requestLoan");
    }

}