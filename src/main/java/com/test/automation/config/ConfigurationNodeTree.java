package com.test.automation.config;


import lombok.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
public class ConfigurationNodeTree {
    //private String browserType;
    private String browserType;  // Changed to List to support multiple browsers
    private String baseUrl;
    private int implicitWaitSeconds;
    private int pageLoadTimeoutSeconds;
    private boolean headless;
    private String environment;

    // Singleton instance to hold configuration data
    private static ConfigurationNodeTree instance;

    // Private constructor to enforce singleton pattern
    private ConfigurationNodeTree() {}

    // Public method to retrieve the singleton instance
    public static ConfigurationNodeTree getInstance() {
        // Lazy initialization - loads config only if not already loaded
        if (instance == null) {
            instance = loadConfig("development"); // Default to development
        }
        return instance; // Return the singleton instance
    }

    // Method to load specific environment config
    public static void loadEnvironment(String environment) {
        instance = loadConfig(environment);
    }

    // Private method to load configuration based on the environment
    private static ConfigurationNodeTree loadConfig(String environment) {
        ObjectMapper mapper = new ObjectMapper();// Mapper to handle JSON data
        try {
            // Load the entire JSON config file into a JSON tree structure
            JsonNode rootNode = mapper.readTree(new File("src/test/resources/config/configNodeTree.json"));

            // Get environment setting (can override from command line)
            //String env = System.getProperty("env", rootNode.path("environment").asText());

            ConfigurationNodeTree configurationNodeTree = new ConfigurationNodeTree();
            configurationNodeTree.setEnvironment(environment);

            // Locate the environment node within the JSON based on provided environment name
            JsonNode envNode = rootNode.path("environments").path(environment);

            // Check if the specified environment node is present; throw error if missing
            if (envNode.isMissingNode()) {
                throw new RuntimeException("Environment '" + environment + "' not found in config");
            }

            // Map the found environment node into a Configuration instance
            ConfigurationNodeTree config = mapper.treeToValue(envNode, ConfigurationNodeTree.class);

            // Set the environment name in the config instance for reference
            config.setEnvironment(environment);
            return config;// Return the populated Configuration instance
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }


}
