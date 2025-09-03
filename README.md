# Playwright Java Testing Repository

This repository contains automated UI test using the Playwright testing framework with Java.

## Prerequisites

- Java JDK 17 or higher
- Maven
- IDE (IntelliJ IDEA recommended)

## Setup Instructions

1. Clone this repository:
   ```
   git clone <repository-url>
   cd PlaywrightJava
   ```

2. Install dependencies:
   ```
   mvn clean install -DskipTests=true
   ```

3. Install Playwright browsers:
   ```
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

## Running Tests

Execute tests using Maven:

```
mvn test -s settings.xml
```

To run specific test classes:

```
mvn test -Dtest=TestClassName
```
## Create Report

Create allure-report via Maven:

```
mvn allure:report
```

## Reporting

Test reports are generated in the `target/reports` directory after test execution, and `mvn allure:report` command.

## Configuration

Test configuration is managed through properties files and environment variables.

## GOAL

### Zadanie

### Wymagania techniczne

- projekt Maven lub Gradle
- Java 17 lub 21
- biblioteka Playwright


### Oczekiwany rezultat

Po uruchomieniu, aplikacja uruchamia przeglądarkę Chrome i przechodzi na stronę 'https://meteo.imgw.pl/'.
Z pliku tekstowego wczytuje listę miast wojewódzkich w Polsce, losuje jedno miasto.
Aplikacja wyszukuje dla wylosowanego miasta bieżącą pogodę i wyświetla w konsoli aktualną temperaturę.

Pliki wymagane do zbudowania projektu należy przesłać spakowane w archiwum .7z