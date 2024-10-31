package com.test.automation.Listener;

import lombok.extern.log4j.Log4j2;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);
    private static final int MAX_RETRY_COUNT = 2;

    // Using ConcurrentHashMap to handle parallel test execution
    private static final ConcurrentHashMap<String, Integer> retryMap = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String testName = getTestMethodName(result);
        int retryCount = retryMap.getOrDefault(testName, 0);

        if (retryCount < MAX_RETRY_COUNT) {
            logger.info("Retrying test '{}' for the {} time", testName, (retryCount + 1));
            log.info("Retrying test '{}' for the {} time", testName, (retryCount + 1));
            retryMap.put(testName, retryCount + 1);
            return true;
        }

        // Clear the retry count after max retries reached
        retryMap.remove(testName);
        logger.info("Test '{}' has reached maximum retry attempts", testName);
        log.info("Test '{}' has reached maximum retry attempts", testName);
        return false;
    }

    private String getTestMethodName(ITestResult result) {
        return result.getMethod().getMethodName() + "_" + result.getTestClass().getName();
    }
}


