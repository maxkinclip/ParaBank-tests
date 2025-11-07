package tests.api.base;



import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {
    protected final RequestSpecification requestSpec;

    public BaseApi() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://parabank.parasoft.com/parabank/services/bank")
                .build();
    }
}
