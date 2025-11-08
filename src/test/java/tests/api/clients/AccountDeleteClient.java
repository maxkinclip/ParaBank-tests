package tests.api.clients;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;

public class AccountDeleteClient extends BaseApi {

    // DELETE /accounts/{accountId}
    public Response deleteAccount(String accountId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/accounts/{accountId}", accountId);
    }
}