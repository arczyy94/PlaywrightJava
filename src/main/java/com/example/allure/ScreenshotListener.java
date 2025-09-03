package com.example.allure;

import com.example.configuration.PlaywrightManager;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestSkipped(ITestResult result) {
        takeScreenshot(result.getName());
    }

    private void takeScreenshot(String name) {
        PlaywrightManager playwrightManager = new PlaywrightManager();
        if (playwrightManager.getPage() != null) {
            byte[] screenshotBytes = playwrightManager.getPage().screenshot();
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshotBytes), "png");
        }
    }

}