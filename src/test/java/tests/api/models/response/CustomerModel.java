package tests.api.models.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerModel {

    @JacksonXmlProperty(localName = "id")
    private long id;

    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    @JacksonXmlProperty(localName = "address")
    private AddressModel address;

    @JacksonXmlProperty(localName = "phoneNumber")
    private String phoneNumber;

    @JacksonXmlProperty(localName = "ssn")
    private String ssn;
}