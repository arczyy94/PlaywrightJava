package com.example.configuration;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import java.nio.file.Paths;
import java.util.Properties;

public class PlaywrightManager {

    protected static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    protected static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    protected static final ThreadLocal<Page> page = new ThreadLocal<>();
    protected static final ThreadLocal<Browser> browser = new ThreadLocal<>();

    public Playwright getPlaywright() {
        return playwright.get();
    }

    public Browser getBrowser() {
        return browser.get();
    }

    public BrowserContext getBrowserContext() {
        return context.get();
    }

    public Page getPage() {
        return page.get();
    }

    public void setUp(Properties config) {
        String browser = System.getProperty("browser", config.getProperty("browser", "chromium"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", config.getProperty("headless", "true")));
        String viewport = System.getProperty("viewport", config.getProperty("viewport", "1920x1080"));
        int timeout = Integer.parseInt(System.getProperty("timeout", config.getProperty("timeout", "30000")));
        int slowMo = Integer.parseInt(System.getProperty("slowMo", config.getProperty("slowMo", "100")));
        String timezone = System.getProperty("timezone", config.getProperty("timezone", "UTC"));
        String locale = System.getProperty("locale", config.getProperty("locale", "en-US"));
        boolean video = Boolean.parseBoolean(System.getProperty("video", config.getProperty("video", "false")));
        boolean trace = Boolean.parseBoolean(System.getProperty("trace", config.getProperty("trace", "false")));

        playwright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        switch (browser.toLowerCase()) {
            case "edge" ->
                    PlaywrightManager.browser.set(playwright.get().chromium().launch(launchOptions.setChannel("msedge")));
            case "chrome" ->
                    PlaywrightManager.browser.set(playwright.get().chromium().launch(launchOptions.setChannel("chrome")));
            case "firefox" -> PlaywrightManager.browser.set(playwright.get().firefox().launch(launchOptions.setChannel("firefox")));
            default -> PlaywrightManager.browser.set(playwright.get().chromium().launch(launchOptions));
        }

        String[] viewportDims = viewport.split("x");
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setTimezoneId(timezone)
                .setLocale(locale)
                .setViewportSize(Integer.parseInt(viewportDims[0]), Integer.parseInt(viewportDims[1]));

        if (video)
            contextOptions.setRecordVideoDir(Paths.get("target/videos/"));

        context.set(PlaywrightManager.browser.get().newContext(contextOptions));
        context.get().setDefaultTimeout(timeout);
        context.get().setDefaultNavigationTimeout(timeout);

        if (trace)
            getBrowserContext().tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));

        page.set(context.get().newPage());
        PlaywrightAssertions.setDefaultAssertionTimeout(timeout);
    }

    public void cleanUp(Properties config, String name) {
        boolean trace = Boolean.parseBoolean(System.getProperty("trace", config.getProperty("trace", "false")));
        if (trace)
            getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("target/trace/" + name + ".zip")));

        getPage().close();
        getBrowser().close();
        getPlaywright().close();
    }

}
