package tests.api.models.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class AddressModel {

    @JacksonXmlProperty(localName = "street")
    private String street;

    @JacksonXmlProperty(localName = "city")
    private String city;

    @JacksonXmlProperty(localName = "state")
    private String state;

    @JacksonXmlProperty(localName = "zipCode")
    private String zipCode;
}