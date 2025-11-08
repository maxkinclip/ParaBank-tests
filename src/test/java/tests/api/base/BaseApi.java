package tests.api.base;



import io.restassured.specification.RequestSpecification;
import tests.api.specs.Specs;

public abstract class BaseApi {
    protected final RequestSpecification requestSpec;

    public BaseApi() {
        this.requestSpec = Specs.bankRequestSpec("https://parabank.parasoft.com/parabank/services/bank");
    }
}