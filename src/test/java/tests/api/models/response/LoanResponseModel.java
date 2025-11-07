package tests.api.models.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // tolerate extra fields like responseDate
public class LoanResponseModel {
    @JacksonXmlProperty(localName = "approved")
    private Boolean approved;

    @JacksonXmlProperty(localName = "message")
    private String message;

    // Optional if you want it:
    @JacksonXmlProperty(localName = "responseDate")
    private String responseDate;
}