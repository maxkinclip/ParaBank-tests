package tests.api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tests.api.base.BaseApi;

public class CustomerClient extends BaseApi {

    public Response login(String username, String password) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/login/{username}/{password}", username, password);
    }
}