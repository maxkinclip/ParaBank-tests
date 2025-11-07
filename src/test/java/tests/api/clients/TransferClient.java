package tests.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;

public class TransferClient extends BaseApi {

    public Response transferFunds(String fromAccountId, String toAccountId, double amount) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", amount)
                .when()
                .post("/transfer");
    }
}