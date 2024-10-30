package com.test.automation.utils;


import java.util.Base64;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
import java.util.Map;

public class CredentialsLoaderOld {
    private Map<String, String> credentials;

    public CredentialsLoaderOld() {
        loadCredentials();
    }

    private void loadCredentials() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("credentials.yaml")) {
            if (inputStream != null) {
                credentials = yaml.load(inputStream);
                System.out.println("Credentials Map: " + credentials);
                if (credentials.get("password") != null) {
                    credentials.replace("password", new String(Base64.getDecoder().decode(credentials.get("password"))));
                } else {
                    System.out.println("Password is missing or null in the credentials map.");
                }
               // credentials.replace("password", new String(Base64.getDecoder().decode(credentials.get("password"))));

            } else {
                throw new RuntimeException("credentials.yaml not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return credentials.get("username");
    }

    public String getPassword() {
        return credentials.get("password");
    }
}
