package tests.api.models.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class TransferResponseModel {
    @JacksonXmlProperty(localName = "transferResult")
    private String transferResult;
}