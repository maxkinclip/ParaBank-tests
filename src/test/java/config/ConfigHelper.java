package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {

    private static Properties properties = new Properties();

    public static void load(String fileName) {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/" + fileName);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file: " + fileName);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}