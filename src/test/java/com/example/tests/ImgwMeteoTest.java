package com.example.tests;

import com.example.steps.MeteoImgwSteps;
import com.example.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Feature("ImgwMeteoTest")
public class ImgwMeteoTest extends BaseTest {

    @Test
    @Description("Log into console actual weather for a random city")
    public void logActualWeatherForCurrentCity() {
        MeteoImgwSteps meteoImgwSteps = new MeteoImgwSteps(getPlaywrightManager().getPage());
        meteoImgwSteps.isPageLoaded();
        meteoImgwSteps.manageCookiesAndPrivacyPolicyMessages();
        meteoImgwSteps.getWeatherForRandomCity();
    }
}
