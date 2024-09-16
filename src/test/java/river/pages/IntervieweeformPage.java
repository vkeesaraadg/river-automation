package river.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import seleniumUtilities.GenericMethods;

public class IntervieweeFormPage extends GenericMethods {

    // Locators
    private By formCardHeader = By.cssSelector(".usa-card:nth-child(2) .usa-card__header");
    private By formSelect = By.id("form-select");
    By ddnCountry = By.name("country");
    private By startFormLink = By.linkText("Start form");
    private By alienRegistrationNumberInput = By.name("alienRegistrationNumber");
    private By languageComboBox = By.id("combo-languages");
    private By button = By.xpath("//button[contains(text(),'%s')]");

    // Constructor
    public IntervieweeFormPage() {
        
    }

    

    // Open a form based on label
    public void openForm(String formLabel) throws Throwable {
        try {
            waitForVisibilityOfElement(formCardHeader, "Form Card Header");
            click(formCardHeader, "Form Card Header");

			/*
			 * WebElement formSelectElement = driver.findElement(formSelect);
			 * formSelectElement.click();
			 */
            
            By selectForm = By.xpath("//section[@id='mainSection']/form/fieldset/select");	
    		waitForVisibilityOfElement(selectForm);
    		selectDropdownByVisibleText(selectForm, formLabel, "Select the form you want to file online.");    		

			/*
			 * WebElement option = driver.findElement(By.xpath("//option[text()='" +
			 * formLabel + "']")); option.click();
			 */
        } catch (Exception e) {
            reporter.failureReport("Open Form", "Failed to open the form with label: " + formLabel, driver);
            throw e;
        }
    }

    // Start the form
    public void startForm() throws Throwable {
        try {
            waitForVisibilityOfElement(startFormLink, "Start Form Link");
            click(startFormLink, "Start Form Link");
        } catch (Exception e) {
            reporter.failureReport("Start Form", "Failed to start the form", driver);
            throw e;
        }
    }

 // Click a button with specific text
    public void clickButton(String buttonText) throws Throwable {
        try {
            
            By buttonLocator = By.xpath("//a[contains(text(),'" + buttonText + "')]");
            if (buttonLocator ==null)
            	buttonLocator = By.xpath("//button[contains(text(),'" + buttonText + "')]");
            // Use the click method with the locator
            boolean clicked = click(buttonLocator, buttonText + " Button Link");
            
            // Check if the click was successful
            if (!clicked) {
                reporter.failureReport("Click Button", "Failed to click the button with text: " + buttonText, driver);
                throw new Exception("Failed to click the button with text: " + buttonText);
            }
        } catch (Exception e) {
            reporter.failureReport("Click Button", "Exception occurred: " + e.getMessage(), driver);
            throw e;
        }
    }



    // Scroll to the top of the page
    public void scrollToTop() throws Throwable {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            reporter.failureReport("Scroll to Top", "Failed to scroll to the top of the page", driver);
            throw e;
        }
    }

    // Enter text into the Alien Registration Number field
    public void enterAlienRegistrationNumber(String registrationNumber) throws Throwable {
        try {
            waitForVisibilityOfElement(alienRegistrationNumberInput, "Alien Registration Number Input");
            WebElement input = driver.findElement(alienRegistrationNumberInput);
            input.sendKeys(registrationNumber);
        } catch (Exception e) {
            reporter.failureReport("Enter Alien Registration Number", "Failed to enter the Alien Registration Number", driver);
            throw e;
        }
    }

    // Select a language from the combo box
    public void selectLanguage(String language) throws Throwable {
        try {
            waitForVisibilityOfElement(languageComboBox, "Language Combo Box");
            WebElement comboBox = driver.findElement(languageComboBox);
            comboBox.click();

            WebElement option = driver.findElement(By.xpath("//li[contains(text(),'" + language + "')]"));
            option.click();
        } catch (Exception e) {
            reporter.failureReport("Select Language", "Failed to select the language: " + language, driver);
            throw e;
        }
    }
}
