package com.example.steps;

import com.example.pages.MeteoImgwPage;
import com.example.utils.InputFileUtil;
import com.example.utils.MeteoTopBarUtil;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeteoImgwSteps {

    private static final Logger logger = LoggerFactory.getLogger(MeteoImgwSteps.class);
    private final Page page;
    private final MeteoImgwPage meteoImgwPage;

    public MeteoImgwSteps(Page page) {
        this.page = page;
        this.meteoImgwPage = new MeteoImgwPage(page);
    }

    @Step("Page is loaded")
    public void isPageLoaded() {
        page.waitForLoadState();
        meteoImgwPage.isMeteoTopBarVisible();
    }

    @Step("Get rid of cookies and privacy policies messages")
    public void manageCookiesAndPrivacyPolicyMessages() {
        meteoImgwPage.clickOnDismissCookieMsgBtn();
        meteoImgwPage.clickOnDismissCopyrightMsgBtn();
        page.evaluate("window.scrollTo(0, 0)");
    }

    @Step("Look for todays weather for a random city")
    public void getWeatherForRandomCity() {
        String randomCity = new InputFileUtil().getRandomCityFromInputFile();
        if (!randomCity.isEmpty()) {
            meteoImgwPage.enterPlace(randomCity);
            String weatherDetails = meteoImgwPage.getTheWeatherForPlace();
            MeteoTopBarUtil meteoTopBarUtil = new MeteoTopBarUtil();

            if (meteoTopBarUtil.getTemperature(weatherDetails) == null)
                throw new AssertionError("Temperature for city " + randomCity + " was not found!");
            else {
                logger.info("Weather for " + randomCity);
                logger.info("Today is : " + meteoTopBarUtil.getDate(weatherDetails));
                logger.info("Current temperature : " + meteoTopBarUtil.getTemperature(weatherDetails));
                logger.info(meteoTopBarUtil.getPerceivedTemperature(weatherDetails));
                logger.info("Sunrise: " + meteoTopBarUtil.getSunrise(weatherDetails));
                logger.info("Sunset: " + meteoTopBarUtil.getSunset(weatherDetails));
            }
        } else {
            throw new AssertionError("Input-file did not contains any cities!");
        }
    }

}
