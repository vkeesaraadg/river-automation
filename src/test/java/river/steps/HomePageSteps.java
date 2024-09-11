package river.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class HomePageSteps {
	WebDriver driver;

	@Given("I navigate to the homepage")
	public void iNavigateToTheHomepage() {
		// Set up the ChromeDriver (you'll need to adjust this path)
		System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

		// Initialize the WebDriver
		driver = new ChromeDriver();

		// Navigate to the specified URL
		driver.get("https://davguylytwn4k.cloudfront.net/");
	}

	@Then("I should see the website logo")
	public void iShouldSeeTheWebsiteLogo() {
		// Locate the logo element by its selector (e.g., id, class, xpath, etc.)
		WebElement logo = driver.findElement(By.id("logo")); // Adjust the selector as needed

		// Assert that the logo is displayed
		Assert.assertTrue("Logo is not displayed", logo.isDisplayed());
	}

	@Then("I should see the main heading")
	public void iShouldSeeTheMainHeading() {
		// Locate the main heading element by its selector
		WebElement heading = driver.findElement(By.tagName("h1")); // Adjust the selector as needed

		// Assert that the main heading is displayed
		Assert.assertTrue("Main heading is not displayed", heading.isDisplayed());
	}

	@Then("I close the browser")
	public void iCloseTheBrowser() {
		// Close the browser
		if (driver != null) {
			driver.quit();
		}
	}
}
