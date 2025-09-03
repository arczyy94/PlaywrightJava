package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeteoTopBarUtil {

    public String getDate(String text) {
        String date = extractBetween(text, "Current weather:", ")");
        if (date != null)
            date = date.trim() + ")";
        return date;
    }

    public String getTemperature(String text) {
        String temperature = extractBetween(text, ")", "Feels");
        if (temperature != null)
            temperature = temperature.trim();
        return temperature;
    }

    public String getPerceivedTemperature(String text) {
        String perceivedTemperature = extractBetween(text, "Feels like temperature:", "Weather for your place");
        if (perceivedTemperature != null)
            perceivedTemperature = "Feels like temperature: " + perceivedTemperature.trim();
        return perceivedTemperature;
    }

    public String getSunrise(String text) {
        Pattern sunrisePattern = Pattern.compile("Sunrise: (\\d{2}:\\d{2})");
        Matcher sunriseMatcher = sunrisePattern.matcher(text);
        return sunriseMatcher.find() ? sunriseMatcher.group(1) : null;
    }

    public String getSunset(String text) {
        Pattern sunsetPattern = Pattern.compile("Sunset: (\\d{2}:\\d{2})");
        Matcher sunsetMatcher = sunsetPattern.matcher(text);
        return sunsetMatcher.find() ? sunsetMatcher.group(1) : null;
    }

    private String extractBetween(String text, String start, String end) {
        int startIdx = text.indexOf(start);
        if (startIdx == -1) return null;
        startIdx += start.length();
        int endIdx = text.indexOf(end, startIdx);
        if (endIdx == -1) return null;
        return text.substring(startIdx, endIdx);
    }
}
