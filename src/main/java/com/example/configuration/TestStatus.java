package com.example.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

public class TestStatus {

    private static final Logger logger = LoggerFactory.getLogger(TestStatus.class);

    public static void printTestStatus(ITestResult result) {
        String status = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN";
        };
        logger.info("Test ended with status {}: {}", status, result.getName());
    }
}