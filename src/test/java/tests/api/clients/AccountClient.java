package tests.api.clients;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;

public class AccountClient extends BaseApi {

    public Response getAccounts(long customerId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/customers/{customerId}/accounts", customerId);
    }

    public Response getAccountDetails(String accountId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/accounts/{accountId}", accountId);
    }

    public Response openNewAccount(long customerId, int type, String fromAccountId) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", type)
                .queryParam("fromAccountId", fromAccountId)
                .when()
                .post("/createAccount");
    }
}