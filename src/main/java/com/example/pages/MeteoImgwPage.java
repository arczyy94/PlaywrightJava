package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MeteoImgwPage {

    private final Page page;
    private final Locator meteoTopBar;
    private final Locator copyrightMsgBtn;
    private final Locator dismissCookieMsgBtn;
    private final Locator enterPlaceInput;

    public MeteoImgwPage(Page page) {
        this.page = page;
        this.meteoTopBar = page.locator("meteo-top-numeric");
        this.copyrightMsgBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("dismiss copyright message"));
        this.dismissCookieMsgBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("dismiss cookie message"));
        this.enterPlaceInput = page.locator("#top-widgets").getByRole(AriaRole.SEARCHBOX, new Locator.GetByRoleOptions().setName("Enter place name"));
    }

    public void isMeteoTopBarVisible() {
        PlaywrightAssertions.assertThat(meteoTopBar).isVisible();
    }

    public void clickOnDismissCopyrightMsgBtn() {
        copyrightMsgBtn.click();
    }

    public void clickOnDismissCookieMsgBtn() {
        dismissCookieMsgBtn.click();
    }

    public void enterPlace(String place) {
        enterPlaceInput.fill(place);
        String cityLocatorOnList = String.format("%s, municipality: %s, county: %s", place, place, place);
        page.getByText(cityLocatorOnList, new Page.GetByTextOptions().setExact(true)).click();

        page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(place)
        ).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
    }

    public String getTheWeatherForPlace() {
        return meteoTopBar.textContent();
    }

}

