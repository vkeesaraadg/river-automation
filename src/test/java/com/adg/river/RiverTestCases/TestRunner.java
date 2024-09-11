package com.adg.river.RiverTestCases;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    //features = "src/test/resources/features", // Path to your feature files
    features = "src/test/resources/features/RiverTestCases2.feature", // Path to a specific feature file
    glue = {"com.adg.river.RiverTestCases.StepDefinitions"}, // Package containing step definitions
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports.json"},
    monochrome = true
)
public class TestRunner {
    // This class remains empty, it is used only as a holder for the above annotations
}
