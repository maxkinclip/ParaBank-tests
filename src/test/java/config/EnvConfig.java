package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {

    public static void loadEnvironment() {
        String env = System.getProperty("env", "local");
        String filePath = "src/test/resources/" + env + ".properties";

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load env file: " + filePath, e);
        }


        props.forEach((key, value) -> {
            System.setProperty(key.toString(), value.toString());
        });

        System.out.println("ENVIRONMENT LOADED: " + env);
    }
}