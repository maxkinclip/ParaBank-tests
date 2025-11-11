package tests.api.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.response.Response;

public class XmlUtils {

    public static <T> T fromXml(Response response, Class<T> clazz) {
        try {
            String xml = response.getBody().asString().trim();
            if (xml.isEmpty()) throw new RuntimeException("Empty XML response");
            XmlMapper mapper = new XmlMapper();
            return mapper.readValue(xml, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML response to " + clazz.getSimpleName(), e);
        }
    }
}