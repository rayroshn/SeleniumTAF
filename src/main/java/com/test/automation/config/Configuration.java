package com.test.automation.config;

import lombok.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

@Data
public class Configuration {
    private String browserType;
    private String baseUrl;
    private int implicitWaitSeconds;
    private int pageLoadTimeoutSeconds;
    private boolean headless;
    private String environment;

    private static Configuration instance;

    private Configuration() {}

    public static Configuration getInstance() {
        if (instance == null) {
            instance = loadConfig();
        }
        return instance;
    }

    private static Configuration loadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                    new File("src/test/resources/config/config.json"),
                    Configuration.class
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
}
