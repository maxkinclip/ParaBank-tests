package tests.api.specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Specs {

    private static AllureRestAssured allureFilter(String reqTpl, String respTpl) {
        return new AllureRestAssured()
                .setRequestTemplate(reqTpl)
                .setResponseTemplate(respTpl);
    }

    // Банк (XML)
    public static RequestSpecification bankRequestSpec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri) // https://parabank.parasoft.com/parabank/services/bank
                .addFilter(new AllureRestAssured()
                        .setRequestTemplate("request.ftl")
                        .setResponseTemplate("response.ftl"))
                .log(LogDetail.URI)
                .build();
    }

    // JSON-сервисы (для DELETE-примера)
    public static RequestSpecification jsonRequestSpec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri) // например https://reqres.in/api
                .addFilter(new AllureRestAssured()
                        .setRequestTemplate("request.ftl")
                        .setResponseTemplate("response.ftl"))
                .log(LogDetail.URI)
                .build();
    }

    // Базовые response-спеки
    public static ResponseSpecification ok200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification notFound404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification badRequest400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }
}