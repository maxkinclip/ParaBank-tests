package tests.api.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tests.api.models.request.RegistrationRequestModel;

public class RegistrationClient {

    private static final String BASE = "https://parabank.parasoft.com/parabank";
    private static final String REGISTER_PAGE = BASE + "/register.htm";


    private String getSessionCookie() {
        Response open = RestAssured
                .given()
                .when()
                .get(REGISTER_PAGE);

        return open.getCookie("JSESSIONID");
    }


    public Response registerUser(RegistrationRequestModel user) {
        String jsessionId = getSessionCookie();

        return RestAssured
                .given()
                .contentType(ContentType.URLENC)
                .cookie("JSESSIONID", jsessionId)
                .formParam("customer.firstName", user.getFirstName())
                .formParam("customer.lastName", user.getLastName())
                .formParam("customer.address.street", user.getAddress())
                .formParam("customer.address.city", user.getCity())
                .formParam("customer.address.state", user.getState())
                .formParam("customer.address.zipCode", user.getZipCode())
                .formParam("customer.phoneNumber", user.getPhoneNumber())
                .formParam("customer.ssn", user.getSsnNumber())
                .formParam("customer.username", user.getUserName())
                .formParam("customer.password", user.getPassword())
                .formParam("repeatedPassword", user.getPasswordValidation())
                .when()
                .post(REGISTER_PAGE);
    }
}