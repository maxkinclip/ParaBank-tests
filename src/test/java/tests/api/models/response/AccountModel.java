package tests.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountModel {

    @JacksonXmlProperty(localName = "id")
    private long id;

    @JacksonXmlProperty(localName = "customerId")
    private long customerId;

    @JacksonXmlProperty(localName = "type")
    private String type;

    @JacksonXmlProperty(localName = "balance")
    private double balance;
}