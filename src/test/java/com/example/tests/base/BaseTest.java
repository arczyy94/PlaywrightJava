package com.example.tests.base;

import com.example.allure.AllureStepListener;
import com.example.configuration.ConfigurationManager;
import com.example.configuration.PlaywrightManager;
import com.example.configuration.TestStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;

public abstract class BaseTest {

    private static final Properties config = ConfigurationManager.loadConfiguration();
    private final PlaywrightManager playwrightManager = new PlaywrightManager();

    public PlaywrightManager getPlaywrightManager() {
        return playwrightManager;
    }

    @BeforeMethod
    public void setUp() {
        playwrightManager.setUp(config);
        AllureStepListener.setCurrentPage(playwrightManager.getPage());
        playwrightManager.getPage().navigate(config.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        TestStatus.printTestStatus(result);
        AllureStepListener.clearCurrentPage();
        playwrightManager.cleanUp(config, result.getName());
    }

}
