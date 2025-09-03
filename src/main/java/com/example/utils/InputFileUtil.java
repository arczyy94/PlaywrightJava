package com.example.utils;

import com.example.steps.MeteoImgwSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class InputFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(MeteoImgwSteps.class);
    String pathToFile = "src/test/resources/cities_list.txt";

    public String getRandomCityFromInputFile() {
        Random random = new Random();
        List<String> cities = readInputFile();

        if (cities.isEmpty()) {
            logger.error("Input-file is empty!");
            return "";
        }

        return cities.get(random.nextInt(cities.size()));
    }

    private List<String> readInputFile() {
        List<String> inputFileContent = new ArrayList<>();

        try {
            inputFileContent = Files.readAllLines(Paths.get(pathToFile));
        } catch (IOException e) {
            logger.error("Exception during reading from input-file: " + e.getMessage());
        }

        return inputFileContent;
    }

}