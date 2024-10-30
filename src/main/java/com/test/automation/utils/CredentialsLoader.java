package com.test.automation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.log4j.Log4j2;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
@Log4j2
public class CredentialsLoader {
    private Map<String, Object> credentials;

    public CredentialsLoader() {
        loadCredentials();
        decodePassword();
    }

    // Load credentials from the YAML file
    private void loadCredentials() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("credentials.yaml")) {
            if (inputStream == null) {
                throw new RuntimeException("credentials.yaml file not found in resources.");
            }
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            // Read YAML/JSON data from the input stream and convert it to a raw Map
           // credentials = mapper.readValue(inputStream, Map.class);

            // Read YAML/JSON data from the input stream and convert it to a Map with type safety
            // Here, the keys are Strings and the values are Objects, allowing for nested structures.
            credentials = mapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});

        } catch (Exception e) {
            throw new RuntimeException("Failed to load credentials.yaml", e);
        }
    }

    // Decode the Base64 password
    private void decodePassword() {
        Map<String, String> nestedCredentials = (Map<String, String>) credentials.get("credentials");
        if (nestedCredentials != null && nestedCredentials.get("password") != null) {
            log.info("Retrieved Username... : {}",nestedCredentials.get("username"));
            log.info("Retrieved Password... : {}",nestedCredentials.get("password"));
            String decodedPassword = new String(Base64.getDecoder().decode(nestedCredentials.get("password")));
            nestedCredentials.replace("password", decodedPassword);
        } else {
            System.out.println("Password is missing or null in the credentials map.");
        }
    }

    // Get the username
    public String getUsername() {
        Map<String, String> nestedCredentials = (Map<String, String>) credentials.get("credentials");
        return nestedCredentials != null ? nestedCredentials.get("username") : null;
    }

    // Get the decoded password
    public String getPassword() {
        Map<String, String> nestedCredentials = (Map<String, String>) credentials.get("credentials");
        return nestedCredentials != null ? nestedCredentials.get("password") : null;
    }

    public static void main(String[] args) {
        CredentialsLoader loader = new CredentialsLoader();
        System.out.println("Username: " + loader.getUsername());
        System.out.println("Password: " + loader.getPassword());  // Decoded password
    }
}
