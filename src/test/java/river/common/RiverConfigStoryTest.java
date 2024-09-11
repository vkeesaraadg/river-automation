package river.common;

import io.cucumber.testng.CucumberOptions;
import seleniumUtilities.BaseRunner;

/**
 * Runner for river Application. This Runner file can be configured to execute/locate "river Feature files based on their Tags and river Step Definition files"
 * @author CignitiTeam
 *
 */

@CucumberOptions(
        plugin = {"json:reports/cucumberreports/river/cucumber.json", "pretty", "usage:reports/cucumberreports/river/cucumber-usage.json", 
        		"html:reports/cucumberreports/river/cucumber.html", "junit:reports/cucumberreports/river/cucumber.xml"},
        features = "src/test/resources/riverfeatures/riverstory",
        glue = {"river.steps"},
         monochrome = true)


public class RiverConfigStoryTest  extends BaseRunner{

}


