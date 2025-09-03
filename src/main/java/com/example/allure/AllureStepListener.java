package com.example.allure;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;

import java.io.ByteArrayInputStream;

public class AllureStepListener implements StepLifecycleListener {
    private static final ThreadLocal<Page> currentPage = new ThreadLocal<>();

    public static void setCurrentPage(Page page) {
        currentPage.set(page);
    }

    public static void clearCurrentPage() {
        currentPage.remove();
    }
    
    @Override
    public void beforeStepStop(StepResult result) {
        if (result.getStatus() != null &&
                (result.getStatus().name().equals("BROKEN") ||
                        result.getStatus().name().equals("FAILED"))) {

            Page page = currentPage.get();
            if (page != null) {
                byte[] screenshot = page.screenshot();
                Allure.addAttachment(
                        "Failed step: " + result.getName(),
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );
            }
        }
    }
}
