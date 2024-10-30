package com.test.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T readJson(String filePath, Class<T> valueType) {
        try {
            return mapper.readValue(new File(filePath), valueType);
        } catch (IOException e) {
            log.error("Failed to read JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public static void writeJson(String filePath, Object value) {
        try {
            mapper.writeValue(new File(filePath), value);
            log.info("Successfully wrote to JSON file: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to write JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to write JSON file", e);
        }
    }
}
