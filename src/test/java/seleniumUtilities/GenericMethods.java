package seleniumUtilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.anikasystems.common.reports.ReporterConstants;

/**
 * Framework level reusable selenium custom methods
 * 
 * @author CignitiTeam
 *
 */

public class GenericMethods extends BaseRunner {
	public final Logger LOG = Logger.getLogger(GenericMethods.class);
	private final String msgClickSuccess = "Successfully Clicked On ";
	private final String msgClickFailure = "Unable To Click On ";
	private final String msgTypeSuccess = "Successfully Entered value ";
	private final String msgTypeFailure = "Unable To Type On ";
	private final String msgIsElementFoundSuccess = "Successfully Found Element ";
	protected boolean reportIndicator = true;

	private static final long DEFAULT_TIMEOUT_SEC = 90;
	private static final int SLEEP_MILLI_SEC = 1000;

	/* Action Engine Methods */

	/**
	 * selectDropdownByIndex Selects the option at the given index in the dropdown
	 * specified by the given locator and logs the selection
	 * 
	 * @param locator     of (By) - the specification of the location of the
	 *                    dropdown selector element
	 * @param index       of (int) the index of the option that's being selected
	 *                    from the dropdown Here index starts with Zero(0)
	 * @param locatorName of (String) label, or short description of the dropdown
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean selectDropdownByIndex(By locator, int index, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			Select s = new Select(driver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				if (reportIndicator) {
					reporter.failureReport("Select Value from the Dropdown :: ",
							"Option at index :: " + index + " is Not Select from the DropDown :: " + locatorName,
							driver);
				} else {
					reporter.SuccessReport("Select Value from the Dropdown :: ",
							"Option at index :: " + index + "is Selected from the DropDown :: " + locatorName);
				}
			}
			reportIndicator = true;
		}
	}

	/**
	 * selectDropdownByValue Selects the option with the given value in the dropdown
	 * specified by the given locator and logs the selection
	 *
	 * @param locator     of (By)
	 * @param value       of (String) - the 'value' name of the option that's being
	 *                    selected from the dropdown
	 * @param locatorName of (String) - label or short description of the dropdown
	 * @return boolean
	 * @throws Throwable the throwable
	 */

	protected boolean selectDropdownByValue(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			Select s = new Select(driver.findElement(locator));
			s.selectByValue(value);
			flag = true;
			LOG.info("Successfully selected the value" + locatorName);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("Select",
						"Option with value attribute : " + value + " is Not Select from the DropDown : " + locatorName,
						driver);
			} else {
				reporter.SuccessReport("Select",
						"Option with value attribute : " + value + " is  Selected from the DropDown : " + locatorName);
			}
		}
	}

	/**
	 * selectDropdownByVisibleText - Selects the option with the given displayed
	 * name in the dropdown specified by the given locator
	 *
	 * @param locator     of (By)
	 * @param visibleText of (String) - the displayed name of the option that's
	 *                    being selected from the dropdown
	 * @param locatorName of (String) - label or short description of the dropdown
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean selectDropdownByVisibleText(By locator, String visibleText, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByVisibleText(visibleText.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			// return false;

			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("Select", visibleText + " is Not Select from the DropDown" + locatorName,
						driver);
			} else {
				reporter.SuccessReport("Select", visibleText + "  is Selected from the DropDown" + locatorName);
			}
		}
	}

	/**
	 * elementEnabled verifies whether the condition matches to the given String
	 * method using if else statement Method will return true or false depending on
	 * the outcome
	 *
	 * @param condition of (boolean)
	 * @param message   of (String) - Short description of the message
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean elementEnabled(boolean condition, String message) throws Throwable {
		try {
			if (condition == true) {
				reporter.SuccessReport("Check Point", message);
			}
			reporter.SuccessReport("Check Point", message);
		} catch (Exception e) {
			reporter.failureReport("Check Point", message, driver);
		}
		return condition;
	}

	/**
	 * elementDisabled verifies whether the condition does not match to the given
	 * String method using if else statement Method will return true or false
	 * depending on the outcome
	 *
	 * @param condition of (boolean)
	 * @param message   of (String) - Short description of the message
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean elementDisabled(boolean condition, String message) throws Throwable {
		try {
			if (condition == false) {
				reporter.SuccessReport("Check Point", message);
			}
		} catch (Exception e) {
			reporter.failureReport("Check Point", message, driver);
		}
		return condition;
	}

	/**
	 * dynamicWaitByLocator (here wait time for the element to load is 'default
	 * timeout' in seconds)
	 *
	 * @param locator of (By)
	 * @throws InterruptedException the throwable
	 */
	protected void dynamicWaitByLocator(By locator) throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT_SEC, SLEEP_MILLI_SEC);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
	}

	/**
	 * assertElementPresent checks the presence of an element on the page will
	 * assert that the given condition is true if the element is present on the page
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean assertElementPresent(By by, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Assert.assertTrue(isElementPresent(by, locatorName));
			flag = true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
		} finally {
			if (!flag) {
				reporter.failureReport("CheckPoint :: ", locatorName + " is not present in the Page", driver);
			} else {
				reporter.SuccessReport("CheckPoint :: ", locatorName + " is present in the application");
			}
		}
		return flag;
	}

	/**
	 * waitForVisibilityOfElement provides an explicit wait of 60 seconds until the
	 * element is visible before throwing an exception
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the Locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForVisibilityOfElement(By by, String locatorName) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("Visible of element is false :: ",
						"Element :: " + locatorName + " is not visible", driver);
			} else {
				reporter.SuccessReport("Visible of element is true :: ", "Element :: " + locatorName + "  is visible");
			}
		}
	}

	/**
	 * waitForInVisibilityOfElement provides an explicit wait of 25 seconds until
	 * the element is visible before throwing an exception
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the Locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForInVisibilityOfElement(By by, String locatorName) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("InVisible of element is false :: ", "Element :: " + locatorName + " is visible",
						driver);
			} else {
				reporter.SuccessReport("InVisible of element is true :: ",
						"Element :: " + locatorName + "  is not visible");
			}
		}
	}

	/**
	 * waitForInVisibilityOfElement provides an explicit wait of 25 seconds until
	 * the element is visible before throwing an exception
	 *
	 * @param by of (By)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForInVisibilityOfElement(By by) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			return false;
		}
	}

	/**
	 * waitForVisibilityOfElement provides an explicit wait of 60 seconds until the
	 * element is visible before throwing an exception
	 *
	 * @param by of (By)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForVisibilityOfElement(By by) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * waitTime - waits the execution time
	 *
	 * @throws Throwable the throwable
	 */
	// TODO
	private void waitTime() throws Throwable {
		String time = ReporterConstants.Timeout;
		long timeValue = Long.parseLong(time);
		Thread.sleep(timeValue);
		LOG.info("Time out value is : " + timeValue);
	}

	/**
	 * getElementsSize returns the size of the list of elements
	 *
	 * @param locator of (By)
	 * @return int
	 */
	protected int getElementsSize(By locator) {
		int a = 0;
		try {
			List<WebElement> rows = driver.findElements(locator);
			a = rows.size();
		} catch (Exception e) {
			e.getMessage();
		}
		return a;
	}

	/**
	 * assertTextMatching verifies whether actual text using locator and getText
	 * method matches to the text provided
	 *
	 * @param by          of (By)
	 * @param text        of (String)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean verifyTextMatching(By by, String text, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			String ActualText = getText(by, locatorName).trim();
			LOG.info("ActualText is : " + ActualText);

			if (ActualText.contains(text.trim())) {
				flag = true;
				LOG.info("String comparison with actual text :: " + "actual text is : " + ActualText
						+ "And expected text is : " + text);
				return true;
			} else {
				LOG.info("String comparison with actual text :: " + "actual text is : " + ActualText
						+ "And expected text is : " + text);
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("CheckPoint :: ", text + " is not present  : " + locatorName, driver);
			} else {
				reporter.SuccessReport("CheckPoint :: ", text + " is  present  : " + locatorName);
			}
		}
	}

	/**
	 * verifyTextEquals - Comparing both actual and expected strings
	 *
	 * @param actText of (String)
	 * @param expText of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean verifyTextEquals(String actText, String expText) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			String ActualText = actText.trim();
			LOG.info("act - " + ActualText);
			LOG.info("exp - " + expText);
			if (ActualText.equalsIgnoreCase(expText.trim())) {

				LOG.info("in if condition");
				flag = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {

				reporter.failureReport("CheckPoint:: ",
						"Expected Text: " + expText + "Actual Text: " + actText + " are not Maching", driver);
			} else {

				reporter.SuccessReport("CheckPoint:: ",
						"Expected Text: " + expText + "Actual Text: " + actText + " are Maching");
			}
		}
	}

	/**
	 * click - Perform click event on locator with an explicit wait of 30 seconds
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean click(By locator, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : click  ::  Locator : " + locatorName);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			LOG.info("Locator is Visible :: " + locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			LOG.info("Clicked on the Locator");
			driver.findElement(locator).click();
			LOG.info("identified the element :: " + locator);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			reporter.failureReport("Click : ", msgClickFailure + locatorName, driver);
		} finally {
			if (!status) {
				reporter.failureReport("Click : ", msgClickFailure + locatorName, driver);
			} else {
				reporter.SuccessReport("Click : " + locatorName, msgClickSuccess + locatorName);
			}
		}
		return status;
	}

	/**
	 * setFocusAndClick using actions class method move to the specific element and
	 * then click on that element
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean setFocusAndClick(By locator, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : click  ::  Locator : " + locatorName);
			WebElement element = driver.findElement(locator);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
			LOG.info("identified the element to focus :: " + locator);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			reporter.failureReport("Click : ", msgClickFailure + locatorName, driver);
		} finally {
			if (!status) {
				reporter.failureReport("Click : ", msgClickFailure + locatorName, driver);
			} else {
				reporter.SuccessReport("Click : ", msgClickSuccess + locatorName);
			}
		}
		return status;
	}

	/**
	 * isElementPresent to determine if an element is present on the page without if
	 * else statement
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the element
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean isElementPresent(By by, String locatorName) throws Throwable {
		boolean status;
		try {
			waitTime();
			driver.findElement(by);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		}
		return status;
	}

	/**
	 * isElementNotPresent
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the element
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean isElementNotPresent(By by, String locatorName) throws Throwable {
		boolean status;
		try {
			String time = ReporterConstants.MIN_TIMEOUT;
			int timevalue = Integer.parseInt(time);
			WebDriverWait wait = new WebDriverWait(driver, timevalue);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			driver.findElement(by);
			status = false;
			reporter.SuccessReport("isElementNotPresent : " + locatorName, locatorName + " is not visible");
		} catch (Exception e) {
			status = true;
			LOG.info(e.getMessage());
		}
		return status;
	}

	/**
	 * moveToElement using actions class method move to the specific element
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean moveToElement(By by, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName());
			WebElement element = driver.findElement(by);
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.build().perform();
			LOG.info("Scroll is performed : " + locatorName);
			status = true;
		} catch (Exception e) {
			e.getMessage();
		}
		return status;
	}

	/**
	 * verifyElementDisplayed - Verify whether the element is present or not
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the Element
	 * @param expected    true
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean verifyElementDisplayed(By by, String locatorName, boolean expected) throws Throwable {
		boolean status = expected;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			if (expected == true) {
				if (driver.findElement(by).isDisplayed()) {
					LOG.info("Element is available :: " + locatorName);
					status = true;
				} else {
					status = false;
				}
			}
			if (expected == false) {
				if (driver.findElement(by).isDisplayed()) {
					LOG.info("Element is available :: " + locatorName);
					status = false;
				} else {
					status = true;
				}
			}

		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		} finally {
			if (expected == true) {
				if (!status) {
					reporter.failureReport("CheckPoint :: ", "Element is not displayed: " + locatorName + " : false",
							driver);
				} else {
					reporter.SuccessReport("CheckPoint :: ", "Element is displayed: " + locatorName + " : true");
				}
			}
			if (expected == false) {
				if (!status) {
					reporter.SuccessReport("CheckPoint :: ", "Element is displayed: " + locatorName + " : true");
				} else {
					reporter.failureReport("CheckPoint :: ", "Element is not displayed: " + locatorName + " : false",
							driver);
				}
			}

		}
		return status;
	}

	/**
	 * setText - Clear the value from element and Enter the text
	 *
	 * @param locator     of (By)
	 * @param testData    of (String) - Test Data to be entered in element
	 * @param locatorName of (String) -- Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected void setText(By locator, String testData, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : Type  ::  Locator : " + locatorName + " :: Data :" + testData);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			LOG.info("Locator is Visible :: " + locator);
			driver.findElement(locator).clear();
			LOG.info("Cleared the existing Locator data : ");
			driver.findElement(locator).sendKeys(testData);
			LOG.info("Set Data in Locator :: " + testData);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		} finally {
			if (!status) {
				if (reportIndicator) {
					reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + testData, driver);
				}
			} else {
				reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + testData);
			}
			reportIndicator = true;
		}
	}

	/**
	 * setTextWithoutClear -- Enter the text in element without clearing the
	 * previous value
	 *
	 * @param locator     of (By)
	 * @param testData    of (String) - Test Data to be entered
	 * @param locatorName of (String) - Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean setTextWithoutClear(By locator, String testData, String locatorName) throws Throwable {
		boolean status;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : Type  ::  Locator : " + locatorName + " :: Data :" + testData);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			LOG.info("Locator is Visible :: " + locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).sendKeys(testData);
			LOG.info("Typed the Locator data :: " + testData);
			reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + "'" + testData + "'");
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + "'" + testData + "'", driver);
		}
		return status;
	}

	/**
	 * setTextUsingJSE -- Set Text Using Java Script Executor when send keys doesn't
	 * work
	 *
	 * @param locator     of (By)
	 * @param testData    of (String)
	 * @param locatorName of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean setTextUsingJSE(By locator, String testData, String locatorName) throws Throwable {
		boolean status;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + locatorName);
			waitTime();
			WebElement searchbox = driver.findElement(locator);
			JavascriptExecutor myExecutor = ((JavascriptExecutor) WebDriver);
			myExecutor.executeScript("arguments[0].value=' " + testData + "'; ", searchbox);
			reporter.SuccessReport("Enter text in :: " + locatorName, msgTypeSuccess + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			reporter.failureReport("Enter text in :: " + locatorName, msgTypeFailure + locatorName, driver);
		}
		return status;
	}

	/**
	 * setTextWithTab - after entering the text it will click on tab to select value
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) -- Name of the locator
	 * @throws Throwable the throwable
	 */
	protected void setTextWithTab(By locator, String locatorName) throws Throwable {
		boolean status = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			WebDriverWait wait = new WebDriverWait(driver, 15);
			LOG.info("Waiting for element :");
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			LOG.info("Locator is Visible :: " + locator);
			driver.findElement(locator).sendKeys(Keys.TAB);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		}
	}

	/**
	 * getPageTitle - Get title of the page
	 *
	 * @return String
	 * @throws Throwable the throwable
	 */
	protected String getPageTitle() throws Throwable {
		String text = driver.getTitle();
		{
			reporter.SuccessReport("Title :: ", "Title of the page is :: " + text);
		}
		return text;
	}

	/**
	 * getText -- Get text of the given locator
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) - Name of the locator
	 * @return String - Returns text from given locator
	 * @throws Throwable the throwable
	 */
	protected String getText(By locator, String locatorName) throws Throwable {
		String text = "";
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		try {
			waitTime();
			boolean value = isElementDisplayed(locator, locatorName);
			if (value) {
				text = driver.findElement(locator).getText();
				LOG.info("Locator is Visible and text is retrieved :: " + text);
				flag = true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("GetText :: ", "Unable to get Text from :: " + locatorName, driver);
			} else {
				reporter.SuccessReport("GetText :: " + locatorName, "" + locatorName + " is :" + text);
				LOG.info("Locator is Visible and text is retrieved :: " + text);
			}
		}
		return text;
	}

	/**
	 * getText -- Get text of the given locator
	 *
	 * @param locator of (By)
	 * @return String - Returns text from given locator
	 * @throws Throwable the throwable
	 */
	protected String getText(By locator) throws Throwable {
		String text = "";
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		try {
			boolean value = isElementDisplayed(locator, "");
			if (value) {
				text = driver.findElement(locator).getText();
				LOG.info("Locator is Visible and text is retrieved :: " + text);
				flag = true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return text;
	}

	/**
	 * getTextAttributeByValue -- Get the value of the given locator
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) -- Name of the locator/element
	 * @return String
	 * @throws Throwable the throwable
	 */

	protected String getTextAttributeByValue(By locator, String locatorName) throws Throwable {
		String text = null;
		boolean flag = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName());
			waitTime();
			text = driver.findElement(locator).getAttribute("value");
			flag = true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("GetAttribute :: " + locatorName,
						"Locator is Visible and attribute value of:: " + locatorName + " is Null", driver);
				LOG.info("GetAttribute :: Unable to get Attribute value from :: " + locatorName);
			} else {
				reporter.SuccessReport("GetAttribute :: " + locatorName, "" + locatorName + " is" + text);
				LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
			}
		}
		return text;
	}

	/**
	 * getAttributeByClass - Get the class of the given locator
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) - Name of the element
	 * @return String
	 * @throws Throwable the throwable
	 */
	protected String getAttributeByClass(By locator, String locatorName) throws Throwable {
		String text = "";
		boolean flag = false;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName());
			waitTime();
			text = driver.findElement(locator).getAttribute("class");
			LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
			flag = true;

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("GetAttribute :: ", "Unable to get Attribute value from :: " + locatorName,
						driver);
				LOG.info("GetAttribute :: Unable to get Attribute value from :: " + locatorName);
			} else {
				reporter.SuccessReport("GetAttribute :: ", "" + locatorName + " is" + text);
				LOG.info("Locator is Visible and attribute value is retrieved :: " + text);
			}
		}
		return text;
	}

	/**
	 * Moves the mouse to the middle of the element. The element is scrolled into
	 * view and its location is calculated using getBoundingClientRect.
	 *
	 * @param locator     : Action to be performed on element
	 * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
	 */
	protected boolean mouseHover(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Mouse over start :: " + locatorName);
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).build().perform();

			flag = true;
			LOG.info("Mouse over End :: " + locatorName);
			return true;
		} catch (Exception e) {
			// return false;
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("MouseOver ::", "MouseOver action is not perform on ::" + locatorName, driver);
			} else {
				reporter.SuccessReport("MouseOver :: ", "MouserOver Action is Done on  :: " + locatorName);
			}
		}
	}

	/**
	 * MouseHoverJS - Hover the mouse using javascript if actions class is not
	 * working
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) - Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean mouseHoverJS(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
			WebElement HoverElement = driver.findElement(locator);
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) this.WebDriver).executeScript(mouseOverScript, HoverElement);
			LOG.info("JSmousehover is performed  on :: " + locatorName);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (!flag) {
				reporter.failureReport("MouseOver : ", "MouseOver action is not perform on : " + locatorName, driver);
			} else {
				reporter.SuccessReport("MouseOver : ", "MouserOver Action is Done on" + locatorName);
			}
		}
		return flag;
	}

	/**
	 * clickJS - Wait for the Element and Perform click operation using JavaScript
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) -- Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean clickJS(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
//			WebDriverWait wait = new WebDriverWait(driver, 30);
//			LOG.info("Locator is Visible :: " + locator);
//			wait.until(ExpectedConditions.elementToBeClickable(locator));
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) WebDriver).executeScript("arguments[0].click();", element);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			if (!flag) {
				reporter.failureReport("Click : ", "Click is not performed on : " + locatorName, driver);
			} else {
				reporter.SuccessReport("Click : ", "Successfully click on  : " + locatorName);
			}
		}
		return flag;
	}

	/**
	 * getWebElementList - Get list of elements presented on webpage and return all
	 * elements
	 *
	 * @param by          of (By)
	 * @param locatorName of (String)
	 * @return List<WebElement>
	 * @throws Throwable the throwable
	 */
	protected List<WebElement> getWebElementList(By by, String locatorName) throws Throwable {
		List<WebElement> elements = null;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method :" + getCallerMethodName() + "  ::  Locator : " + locatorName);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			elements = driver.findElements(by);
			LOG.info("Size of List ::" + elements.size());
			for (WebElement element : elements) {
				LOG.info("List value are :: " + element.getText());
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return elements;
	}

	/**
	 * elementVisibleTime - Time taken to load the element on a page
	 *
	 * @param locator of (By)
	 * @throws Throwable the throwable
	 */
	protected void elementVisibleTime(By locator) throws Throwable {
		float timeTaken;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			long start = System.currentTimeMillis();
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			long stop = System.currentTimeMillis();
			timeTaken = (stop - start) / 1000;
			LOG.info("Took : " + timeTaken + " secs to display the results : ");
			reporter.SuccessReport("Total time taken for element visible :: ",
					"Time taken load the element :: " + timeTaken + " seconds");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * dragAndDrop -- Moves the mouse to the middle of the element. The element is
	 * scrolled into view and its location is calculated using
	 * getBoundingClientRect.
	 *
	 * @param destinationLocator : Action to be performed on element
	 * @param locatorName        : Meaningful name to the element (Ex:link,menus
	 *                           etc..)
	 */
	protected boolean dragAndDrop(By souceLocator, By destinationLocator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			Actions builder = new Actions(driver);
			WebElement souceElement = driver.findElement(souceLocator);
			WebElement destinationElement = driver.findElement(destinationLocator);
			builder.dragAndDrop(souceElement, destinationElement).build().perform();
			flag = true;
			LOG.info("drag and drop performed ");
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("DragDrop : ", "Drag and Drop action is not performed on : " + locatorName,
						driver);
			} else {
				reporter.SuccessReport("DragDrop : ", "Drag and Drop Action is Done on : " + locatorName);
			}
		}
	}

	/**
	 * getCurrentUrl - Get the current url of the page working on
	 * 
	 * @return url
	 *
	 */
	protected String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}

	/**
	 * navigateToURL - Open or redirect to new URL
	 *
	 * @param Url of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean navigateToURL(String Url) throws Throwable {
		boolean flag = false;
		try {
			waitTime();
			WebDriver.navigate().to(Url);
			LOG.info("Navigated URL is : " + Url);
			flag = true;
		} catch (Exception e) {
			flag = false;
			LOG.info(e.getMessage());
		} finally {
			if (!flag) {
				reporter.failureReport("Navigate to ", "Unable to Navigate " + Url, driver);
			} else {
				reporter.SuccessReport("Navigate to", "Successfully Navigate to " + Url);
			}
		}
		return flag;
	}

	/**
	 * rightClick - Perform action on element using right click through actions
	 * class
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String) - Name of the element
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean rightClick(By locator, String locatorName) throws Throwable {
		boolean status;
		String msgRightClickSuccess = "Successfully Mouse Right Clicked On ";
		String msgRightClickFailure = "Unable To Right Click On ";
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			Actions action = new Actions(driver);
			action.contextClick(driver.findElement(locator)).build().perform();
			reporter.SuccessReport("Right Click : " + locatorName, msgRightClickSuccess + locatorName);
			LOG.info("Right click performed  on :: " + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
			reporter.failureReport("Right Click : ", msgRightClickFailure + locatorName, driver);
		}
		return status;
	}

	/**
	 * getCallerClassName
	 *
	 * @return String
	 */
	protected static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getClassName();
	}

	/**
	 * getCallerMethodName
	 *
	 * @return String
	 */
	protected static String getCallerMethodName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getMethodName();
	}

	/**
	 * doubleClick -- Double click the mouse to the middle of the element. The
	 * element is scrolled into view and its location is calculated using
	 * getBoundingClientRect.
	 *
	 * @param locator     : Action to be performed on element
	 * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
	 */
	protected boolean doubleClick(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Mouse Double Click start :: " + locatorName);
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).doubleClick(mo).build().perform();
			flag = true;
			LOG.info("Mouse Double Click :: " + locatorName);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("double Click :: ", "double Click action is not perform on ::" + locatorName,
						driver);
			} else {
				reporter.SuccessReport("double Click :: ", "double Click Action is Done on  :: " + locatorName);
			}
		}
	}

	/**
	 * doubleClickJS -- Double click the mouse to the middle of the element. The
	 * element is scrolled into view and its location is calculated using
	 * getBoundingClientRect.
	 *
	 * @param locator     : Action to be performed on element (Get it from Object
	 *                    repository)
	 * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
	 */
	protected boolean doubleClickJS(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Mouse Double Click start :: " + locatorName);
			WebElement mo = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript(
					"var evt = document.createEvent('MouseEvents'); evt.initMouseEvent('dblclick',true, "
							+ "true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null); arguments[0].dispatchEvent(evt);",
					mo);
			flag = true;
			LOG.info("Mouse Double Click :: " + locatorName);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("double Click :: ", "double Click action is not perform on ::" + locatorName,
						driver);
			} else {
				reporter.SuccessReport("double Click :: ", "double Click Action is Done on  :: " + locatorName);
			}
		}
	}

	/**
	 * moveToElementAndClick -- click the mouse to the middle of the element. The
	 * element is scrolled into view and its location is calculated using
	 * getBoundingClientRect.
	 *
	 * @param locator     : Action to be performed on element
	 * @param locatorName : Meaningful name to the element (Ex:link,menus etc..)
	 */
	protected boolean moveToElementAndClick(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Mouse Double Click start :: " + locatorName);
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).click().build().perform();
			flag = true;
			LOG.info("Mouse Double Click :: " + locatorName);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("Move to Element And Click :: ",
						"Click action is not perform on ::" + locatorName, driver);
			} else {
				reporter.SuccessReport("Move to Element And Click :: ", " Click Action is Done on  :: " + locatorName);
			}
		}
	}

	/**
	 * replaceAll, Function to replace the regular expression values with client
	 * required values
	 *
	 * @param text        of (String)
	 * @param pattern     of (String), regular expression of actual value
	 * @param replaceWith of (String), value to replace the actual
	 * @return : String
	 */
	protected String replaceAll(String text, String pattern, String replaceWith) {
		String flag = null;
		try {
			flag = text.replaceAll(pattern, replaceWith);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * subString, Function to get sub string of given actual string text
	 *
	 * @param text       of (String), Actual text
	 * @param startIndex of (int), Start index of sub string
	 * @param endIndex   of (int), end index of sub string
	 * @return : String
	 */
	protected String subString(String text, int startIndex, int endIndex) {
		String flag = null;
		try {
			flag = text.substring(startIndex, endIndex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * getCssValue, Function to get the value of a given CSS property (e.g. width)
	 *
	 * @param locator  of (By)
	 * @param cssValue of (String), CSS property
	 * @return : String
	 */
	protected String getCssValue(By locator, String cssValue) {
		String result = "";
		try {
			result = driver.findElement(locator).getCssValue(cssValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * getBackGroundColor -- Function to get the background color of a given web
	 * element (e.g. background-color)
	 *
	 * @param locator  of (By)
	 * @param cssValue of (String), CSS property (e.g. background-color)
	 * @return : String
	 */
	protected String getBackGroundColor(By locator, String cssValue) {
		String hexColor = "";
		try {
			String bColor = driver.findElement(locator).getCssValue(cssValue);
			hexColor = Color.fromString(bColor).asHex();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hexColor;
	}

	/**
	 * getDateTimeStamp -- Get Date and Time in specific format
	 *
	 * @return String
	 */
	protected String getDateTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		String formattedDate = sdf.format(date);
		suiteStartTime = formattedDate.replace(":", "_").replace(" ", "_");
		return suiteStartTime;
	}

	/**
	 * getCurrentDateTime, Function to get current time in client required format
	 *
	 * @param dateTimeFormat of (String), format to get date and time (e.g: h:mm)
	 * @return : String
	 */
	protected String getCurrentDate(String dateTimeFormat) throws Throwable {
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * getDateTimeFromNow :: Get future time after adding x
	 * minutes/days/minutes/year to current time param :: timeFromNow -- minutes to
	 * be added return ::date throws :: Exception
	 */
	public static String getDateTimeFromNow(String type, int dateTimeFromNow, String format) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		switch (type.toLowerCase()) {
		case "year":
			cal.add(Calendar.YEAR, dateTimeFromNow);
			break;
		case "month":
			cal.add(Calendar.MONTH, dateTimeFromNow);
			break;
		case "date":
			cal.add(Calendar.DATE, dateTimeFromNow);
			break;
		case "minute":
			cal.add(Calendar.MINUTE, dateTimeFromNow);
			break;
		}
		Date desiredDateTime = cal.getTime();
		DateFormat newDate = new SimpleDateFormat(format);
		return newDate.format(desiredDateTime);
	}

	/**
	 * getFutureDateTime, Function to get future date in required format
	 * 
	 * @param dateTimeFormat of (String), format to get date and time (e.g:
	 *                       MM/dd/yyyy)
	 * @param days           of (int), number to get date E.g. 1:Tomorrow date, -1:
	 *                       Yesterday date
	 * @return : String
	 */
	protected String getFutureDateTime(String dateTimeFormat, int days) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date tomorrow = calendar.getTime();
		return sdf.format(tomorrow);
	}

	/**
	 * getCurrentSystemTime :: Get Current System Time based on timezone param ::
	 * TimeFormat,TimeZone return ::date
	 */
	public static String getCurrentSystemTimeAsPerTimeZone(String format, String timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		sdf.format(new Date()).toString();
		return sdf.format(new Date());
	}

	/**
	 * getCountryDateTime, Function to get future or past date in client required
	 * format
	 *
	 * @param dateTimeFormat of (String), format to get date and time (e.g:
	 *                       MM/dd/yyyy)
	 * @param days           of (int), number to get date E.g. 1:Tomorrow date, -1:
	 *                       Yesterday date
	 * @param timeZone       of (String), time format to get date E.g.
	 *                       :America/New_York
	 * @return : String
	 */
	protected String getCountryDateTime(String dateTimeFormat, int days, String timeZone) throws Throwable {
		Calendar calNewYork = Calendar.getInstance();
		calNewYork.add(Calendar.DAY_OF_YEAR, 0);
		Date date = calNewYork.getTime();
		DateFormat formatter = new SimpleDateFormat(dateTimeFormat);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * �* param :: Hashtable with string inputs �* return ::String �* throws ::
	 * throwable �* methodName :: generateRandomNumber �
	 */
	protected static String generateRandomNumber(int length) {
		String randomNumber = "1";
		int retryCount = 1;
		while (retryCount > 0) {
			String number = Double.toString(Math.random());
			number = number.replace(".", "");
			if (number.length() > length) {
				randomNumber = number.substring(0, length);
			} else {
				int remainingLength = length - number.length() + 1;
				randomNumber = generateRandomNumber(remainingLength);
			}
			if (randomNumber.length() < length) {
				retryCount++;
			} else {
				retryCount = 0;
			}
		}
		return randomNumber;
	}

	/**
	 * generateRandomNumber description :: This is used to generate random number
	 * with 8 digit as current Year(YY)and system time
	 * 
	 * @return int
	 * @throws Throwable the throwable
	 */
	protected String generateRandomNumberBasedOnLength(int length) throws Throwable {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSdSMSs");
		String currentdate = sdf.format(date);
		return new StringBuffer().append(currentdate).reverse().substring(0, length);
	}

	/**
	 * getRandomString, Get random String
	 * 
	 * @param noOfCharacters of (int), Number of characters to get randomly
	 * @return String
	 */

	protected String getRandomString(int noOfCharacters) throws IOException {
		return RandomStringUtils.randomAlphabetic(noOfCharacters);
	}

	/**
	 * getAttribute, Function to get the value of a given attribute (e.g. class)
	 *
	 * @param locator       of (By)
	 * @param attributeName of (String)
	 * @return : String
	 */
	protected String getAttribute(By locator, String attributeName) {
		String result = "";
		try {
			result = driver.findElement(locator).getAttribute(attributeName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * refreshPage -- Refreshing webpage working on currently
	 */
	protected void refreshPage() throws Throwable {
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName());
			driver.navigate().refresh();
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		}
	}

	/**
	 * clearText -- clearData or Clear value from textBox
	 *
	 * @param locator of (By)
	 */
	protected void clearText(By locator) throws Throwable {
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName());
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
			element.clear();
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		}
	}

	/**
	 * waitForAlertPresent -- Wait for an alert to be present within the specified
	 * wait time in seconds Note that this does not actually handle the alert just
	 * confirms if it appears or not
	 *
	 * @param waitTime the amount of time to wait for the alert, in seconds
	 *
	 * @return boolean indicating whether the alert was found
	 */
	protected boolean waitForAlertPresent(int waitTime) {
		boolean foundAlert = false;
		try {
			final WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (final RuntimeException e) {
			LOG.info("Alert Is Not Present");
		}
		return foundAlert;
	}

	/**
	 * acceptAlert -- > To Accept the Alert, click on OK button from alert with the
	 * help of alerts class
	 */
	protected void acceptAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * dismissAlert -- > To Dismiss the Alert, click on cancel button from alert
	 * with the help of alerts class
	 */
	protected void dismissAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().dismiss();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * findWebElement on the webpage using an explicit wait
	 * 
	 * @param locator     of (By)
	 * @param locatorName of (String)
	 * @return WebElement
	 */
	protected WebElement findWebElement(By locator, String locatorName) throws Throwable {
		WebElement element;
		try {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : click  ::  Locator : " + locatorName);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			LOG.info("Locator is Visible :: " + locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			element = driver.findElement(locator);
		} catch (Exception e) {
			LOG.info(e.getMessage());
			throw new RuntimeException(e);
		}
		return element;
	}

	/**
	 * checkBoxIsChecked -- Validate Whether Check box is checked or not will return
	 * true or false
	 *
	 * @param by          of (By)
	 * @param locatorName of (String)
	 * @param expected    of (boolean)
	 * @return boolean
	 */
	protected boolean checkBoxIsChecked(By by, String locatorName, boolean expected) throws Throwable {
		boolean status = expected;
		String msgCheckboxisnotChecked = "Checkbox is not Selected";
		try {
			waitTime();
			boolean actualStatus = driver.findElement(by).isSelected();
			status = actualStatus;
		} catch (Exception e) {
			status = false;
			LOG.info(e.getMessage());
		} finally {
			if (!status) {
				if (reportIndicator) {
					reporter.failureReport("checkBoxIsChecked : ", msgCheckboxisnotChecked + locatorName, driver);
				}
			} else {
				reporter.SuccessReport("checkBoxIsChecked : ", locatorName + ", is checked : true");
			}
			reportIndicator = true;
		}
		return status;
	}

	/**
	 * checkBoxIsUnChecked -- Validate Whether Check box is un checked or not will
	 * return true or false
	 *
	 * @param by          of (By)
	 * @param locatorName of (String)
	 * @param expected    of (boolean)
	 * @return boolean
	 */
	protected boolean checkBoxIsUnChecked(By by, String locatorName, boolean expected) throws Throwable {
		boolean status = expected;
		try {
			waitTime();
			boolean actualStatus = driver.findElement(by).isSelected();
			status = actualStatus;
		} catch (Exception e) {
			status = true;
			LOG.info(e.getMessage());
		} finally {
			if (status) {
				reporter.failureReport("checkBoxIsUnChecked : ", "Checkbox is in selected state" + locatorName, driver);
			} else {
				reporter.SuccessReport("checkBoxIsUnChecked : ", locatorName + ", checkbox is unselected state : true");
			}
		}
		return status;
	}

	/**
	 * switchToFrame -- Function to switch to a different frame using locator and an
	 * explicit wait
	 * 
	 * @param locator of (By)
	 */
	protected void switchToFrame(By locator) {
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		LOG.info("Locator is Visible :: " + locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		driver.switchTo().frame(driver.findElement(locator));
	}

	/**
	 * Function to switch to a different frame using index value
	 * 
	 * @param index of (int), frame number to switch
	 */
	protected void switchToFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	/**
	 * comeOutFromFrame - Get come out of frame
	 */
	protected void comeOutFromFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * switchToWindow, Function to switch to latest window
	 */
	protected void switchToWindow() {
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
	}

	/**
	 * switchToWindow, Function to switch to latest window
	 */
	protected void switchToWindow(String mainWindow) {
		for (String handle : driver.getWindowHandles()) {
			if (handle != mainWindow)
				driver.switchTo().window(handle);
		}
	}

	/**
	 * switchToParentWindow, Function to switch to parent window
	 *
	 * @param handle of (String), window handle to switch
	 */
	protected void switchToParentWindow(String handle) {
		driver.switchTo().window(handle);
	}

	/**
	 * closeWindow, Function to close the currently focused window only
	 */
	protected void closeWindow() {
		driver.close();
	}

	/**
	 * getWindowHandle, Function to get the current window handle of the webpage
	 * working on
	 *
	 * @return : String
	 */
	protected String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * scrollToWebElement, Function to scroll to a particular element until in view
	 * using java script executor
	 *
	 * @param element of (By)
	 */
	protected void scrollToWebElement(By element) throws Throwable {
		boolean status = false;
		try {
			if (isElementDisplayed(element, "element")) {
				((JavascriptExecutor) WebDriver).executeScript("arguments[0].scrollIntoView(true);",
						driver.findElement(element));
				status = true;
			}
		} catch (Exception e) {
			status = false;
		} finally {
			if (!status) {
				if (reportIndicator) {
					reporter.failureReport("scroll to element : ", "Unable to scroll to " + element, driver);
				}
			} else {
				reporter.SuccessReport("scroll to element : ", "Able to scroll to " + element);
			}
			reportIndicator = true;
		}
	}

	/**
	 * scrollBottom : Function to move the scroll to bottom of the page using java
	 * script executor
	 * 
	 */
	protected void scrollBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1500)");
	}

	/**
	 * scrollBottom : Function to move the scroll to bottom of the page using java
	 * script executor
	 * 
	 */
	protected void scrollMiddle() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,650)");
	}

	/**
	 * scrollBottom : Function to move the scroll to bottom of the page using java
	 * script executor
	 * 
	 */
	protected void scrollUP() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)");
	}

	/**
	 * isElementVisibleOnly
	 *
	 * @param locator     of (By)
	 * @param locatorName of (String)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean isElementDisplayed(By locator, String locatorName) throws Throwable {
		boolean flag;
		try {
			LOG.info("Class name :: " + getCallerClassName() + " Method name :: " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			flag = driver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * getYear, Function to get required year e.g: 0-Current year, 1-Next year,
	 *
	 * @param number of (int) Number to get year (e.g: -1,0,1 etc)
	 * @return int
	 * @throws Throwable the throwable
	 */
	protected int getYear(int number) throws Throwable {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) + number;
		LOG.info("Year is : " + year);
		return year;
	}

	/**
	 * dateFormatVerification, Function to verify date format by giving actual date
	 *
	 * @param actualDate     of (String) actual date e.g: 21-11-2015
	 * @param formatToVerify of (String) format type e.g: dd-MM-yyyy
	 * @return boolean
	 */
	protected boolean dateFormatVerification(String actualDate, String formatToVerify) {
		boolean flag = false;
		if (actualDate.toLowerCase().contains("am")) {
			flag = formatVerify(actualDate, formatToVerify);
		} else if (actualDate.toLowerCase().contains("pm")) {
			flag = formatVerify(actualDate, formatToVerify);
		} else if (!actualDate.toLowerCase().contains("am") || !actualDate.toLowerCase().contains("pm")) {
			flag = formatVerify(actualDate, formatToVerify);
		}
		return flag;
	}

	/**
	 * formatVerify, Reusable Function to verify date format by giving actual date
	 *
	 * @param actualDate     of (String)e.g: 21-11-2015
	 * @param formatToVerify of (String) type e.g: dd-MM-yyyy
	 * @return : boolean
	 */
	protected boolean formatVerify(String actualDate, String formatToVerify) {
		boolean flag;
		try {
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat(formatToVerify);
			Date date = sdf.parse(actualDate);
			String formattedDate = sdf.format(date);
			flag = actualDate.equals(formattedDate);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * differenceBetweenTwoDates, Find the difference between two dates
	 *
	 * @param date1
	 * @param date2
	 * @param dateFormat
	 */
	protected long differenceBetweenTwoDates(String date1, String date2, String dateFormat) throws Throwable {
		long diffDays = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);
			long diff = d2.getTime() - d1.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		} catch (Exception e) {
			e.getMessage();
		}
		return diffDays;
	}

	/**
	 * convertDateFormatToAnotherDateFormat, Function to convert one date format to
	 * another date format
	 *
	 * @param actualDate        of (String), Actual date (e.g: Dec 5, 2017)
	 * @param sourceFormat      of (String), format of actualDate (e.g: MMM dd,
	 *                          yyyy)
	 * @param destinationFormat of (String), Format what we required (e.g:
	 *                          dd/MM/yyyy)
	 * @return : String
	 */
	protected String convertDateFormatToAnotherDateFormat(String actualDate, String sourceFormat,
			String destinationFormat) throws Throwable {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceFormat);
		SimpleDateFormat sdf = new SimpleDateFormat(destinationFormat);
		Date date = simpleDateFormat.parse(actualDate);
		return sdf.format(date);
	}

	/**
	 * Get text from PDF file.
	 *
	 * @param pdfFilePath (String) path of the PDF file
	 * @return (String) text from PDF file
	 * @throws Throwable the throwable
	 */
	

	/**
	 * Function to get attribute name from focused element If we use this method it
	 * will return required attribute value of focused element
	 *
	 * @throws Throwable the throwable
	 */
	protected String getAttributeFromFocusedElement(String attributeName) throws Throwable {
		WebElement activeElement = driver.switchTo().activeElement();
		return activeElement.getAttribute(attributeName);
	}

	/**
	 * Function to get value from XML file If we use this method it will return
	 * required value based on tag name
	 *
	 * @param xmlFileName (String) path of the XML file
	 * @param parentTag   (String) Parent tag name
	 * @param childTag    (String) Child tag name
	 */
	protected String readXML(String xmlFileName, String parentTag, String childTag) {
		String tagName = "";
		try {
			File fXmlFile = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(parentTag);
			Node nNode;
			Element eElement;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					tagName = eElement.getElementsByTagName(childTag).item(0).getTextContent();
				}
			}
		} catch (Exception e) {
		}
		return tagName;
	}

	/**
	 * Function to get check the sessions in multiple tabs If we use this method it
	 * will return required text of highlighted element
	 *
	 * @throws Throwable the throwable
	 */
	protected void openURLInNewTab(String URL) throws Throwable {
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, "t");
		driver.findElement(By.tagName("body")).sendKeys(selectLinkOpeninNewTab);
		driver.get(URL);
	}

	/**
	 * Function to get check the session in a new window
	 *
	 * @throws Throwable the throwable
	 */
	protected void openURLInNewWindow(String URL) throws Throwable {

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		String OpenNewtab = tabs.get(1);
		if (OpenNewtab != null) {
			reporter.SuccessReport("OpenNewtab", "New tab opened");
		}
		driver.get(URL);
	}

	/**
	 * sendKeysActionsEsc - Select Escape key to perform action
	 * 
	 * @param locator
	 */
	protected void sendKeysActionsEsc(By locator) throws Throwable {
		try {
			Thread.sleep(3000);
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ESCAPE);
		} catch (Exception e) {

		}
	}

	/**
	 * isEnabled - Check whether element is enabled or not
	 * 
	 * @param locator     of (By)
	 * @param locatorName of (String) - Name of the locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean isEnabled(By locator, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name :: " + getCallerClassName() + " Method name :: " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			flag = driver.findElement(locator).isEnabled();
		} catch (Exception e) {
			flag = false;
		} finally {
			if (!flag) {
				reporter.failureReport("IsEnabled : ", locatorName + " Element is Not Enable", driver);
			} else {
				reporter.SuccessReport("IsEnabled : ", locatorName + " Element is Enabled");
			}
		}
		return flag;
	}

	/**
	 * isEnabled - Check whether element is enabled or not
	 * 
	 * @param locator of (By)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean isEnabled(By locator) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name :: " + getCallerClassName() + " Method name :: " + getCallerMethodName());
			flag = driver.findElement(locator).isEnabled();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * getAttributeValue of the element it fetch the value that containing one of
	 * any attribute in the HTML tag
	 * 
	 * @param element       of (WebElement)
	 * @param attributeName (String) - Name of the attribute
	 * @throws Throwable the throwable
	 */

	protected String getAttributeValue(WebElement element, String attributeName) throws Throwable {

		String text = "";
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		try {
			text = element.getAttribute(attributeName);
			flag = true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (!flag) {
				reporter.failureReport("GetAttributeValue :: ", "Unable to get attribute value", driver);
				LOG.info("GetAttributeValue :: Unable to get Attribute");
			} else {
				reporter.SuccessReport("GetAttributeValue :: ", "Attribute value" + text);
				LOG.info("GetAttributeValue :: Attribute is retrieved :: " + text);
			}
		}
		return text;
	}

	/**
	 * returnCheckBoxStatus -- Validate Whether Check box is selected or not
	 *
	 * @param by of (By)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean returnCheckBoxStatus(By by) throws Throwable {
		boolean actualStatus;
		try {
			waitTime();
			actualStatus = driver.findElement(by).isSelected();

		} catch (Exception e) {
			actualStatus = false;
			LOG.info(e.getMessage());
		}
		return actualStatus;
	}

	/**
	 * getDropDownData -- Get all values from the dropdown
	 * 
	 * @param locator  of (By)
	 * @param dataType (String) - Datatype of dropdown values
	 * @return complete list of dropdown values
	 * @throws Throwable the throwable
	 */

	protected List<String> getDropDownData(By locator, String dataType) {
		boolean flag = false;
		List<String> dropdownData = new ArrayList<String>();
		try {
			Select s = new Select(driver.findElement(locator));
			List<WebElement> allOptions = s.getOptions();
			for (WebElement option : allOptions) {
				String data;
				if (dataType.equalsIgnoreCase("value")) {
					data = option.getAttribute("value");
				} else {
					data = option.getText();
				}
				dropdownData.add(data);
			}

		} catch (Exception e) {
			reporter.failureReport("Get data from drop down", "Failed to read data from dropdown " + e.getMessage(),
					driver);
			throw new RuntimeException(e);
		} finally {
			return dropdownData;
		}
	}

	/**
	 * getSelectedDropdownOption -- Get "default selected value" from dropdown
	 * 
	 * @param locator of (By)
	 * @return selected item from dropdown
	 */
	protected String getSelectedDropdownOption(By locator) {
		String selectedItem = null;
		try {
			Select select = new Select(driver.findElement(locator));
			WebElement option = select.getFirstSelectedOption();
			selectedItem = option.getText();
		} catch (Exception ex) {
			reporter.failureReport("Get Selected Drop down Option",
					"Failed to get selected Drop down Option" + ex.getMessage(), driver);
			throw new RuntimeException(ex);
		} finally {
			return selectedItem;
		}
	}

	/**
	 * methodName :: javaScriptOpenNewTab return ::void throws :: throwable
	 * description :: to open new tab in chrome
	 */
	protected void javaScriptOpenNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}

	/***
	 * getCurrentClassAndMethodNames - To get current class and method names on
	 * report
	 * 
	 * @return get current class and method names
	 */
	protected static String getCurrentClassAndMethodNames() {
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
	}

	/**
	 * methodName :: getWebElement return ::element description :: to get webelement
	 */
	protected WebElement getWebElement(final By by) {

		WebElement element = null;

		if (by != null) {
			element = driver.findElement(by);
		} else {
			LOG.info("By instance is null");
		}
		return element;
	}

	/**
	 * Wrapper around thread.sleep
	 *
	 * @param milliseconds the number of milliseconds to sleep
	 */
	public static void sleep(Integer milliseconds) {
		long secondsLong = (long) milliseconds;
		try {
			Thread.sleep(secondsLong);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * waitForElementToBeClickable
	 *
	 * @param by          of (By)
	 * @param locatorName of (String) - Name of the Locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForElementToBeClickable(By by, String locatorName) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			return false;
		} finally {
			if (!flag) {
				reporter.failureReport("Wait for element to be clickable is false :: ",
						"Element :: " + locatorName + " is not clickable", driver);
			} else {
				reporter.SuccessReport("Wait for element to be clickable is true :: ",
						"Element :: " + locatorName + "  is clickable");
			}
		}
	}

	/**
	 * waitForElementToBeClickable
	 *
	 * @param by of (By)
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected boolean waitForElementToBeClickable(By by) throws Throwable {
		boolean flag = false;
		LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
		WebDriverWait wait = new WebDriverWait(driver, 15);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			flag = true;
			return true;
		} catch (Exception e) {
			LOG.info("Class name" + getCallerClassName() + "Method name : " + getCallerMethodName());
			return false;
		}
	}

	/**
	 * click on enter
	 *
	 * @param locator of (By)
	 */
	protected void clickEnter(By locator) throws Throwable {

		try {
			driver.findElement(locator).sendKeys(Keys.ENTER);

		} catch (Exception e) {

			LOG.info(e.getMessage());
		}
	}

	/**
	 * kendoSelectValueFormDropdown
	 *
	 * @param by of (By),locatorName,value
	 * @throws Throwable the throwable
	 */
	public void kendoSelectValueFormDropdown(By locator, String locatorName, String value) throws Throwable {
		kendoClickValueFormDropdown(locator, locatorName, value);
		/*
		 * try { click(locator, locatorName); driver.getKeyboard().sendKeys(value);
		 * driver.getKeyboard().sendKeys(Keys.ENTER);
		 * reporter.SuccessReport("Kendo DropDown",
		 * "Successfully selected value from dropdown " + value); } catch (Exception e)
		 * { reporter.failureReport("Kendo DropDown",
		 * "Failed to select value from dropdown " + value, driver); }
		 */

	}

	/**
	 * kendoSelectValueFormDropdown
	 *
	 * @param by of (By),locatorName,value
	 * @throws Throwable the throwable
	 */
	public void kendoClickValueFormDropdown(By locator, String locatorName, String value) throws Throwable {
		try {
			click(locator, locatorName);
			Thread.sleep(2000);
			By byTypeLocator = By.xpath("//li[contains(.,'" + value + "')]");
			click(byTypeLocator, value);
			reporter.SuccessReport("Kendo DropDown", "Successfully selected value from dropdown " + value);
		} catch (Exception e) {
			reporter.failureReport("Kendo DropDown", "Failed to select value from dropdown " + value, driver);
		}

	}

	/**
	 * assertTextMatching verifies whether actual text using locator and getText
	 * method matches to the text provided
	 *
	 * @param by          of (By)
	 * @param text        of (String)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected void validateCheckPointMatch(By by, String expected, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			String ActualText = getText(by, locatorName).trim();
			LOG.info("ActualText is : " + ActualText);
			Assert.assertEquals(ActualText, expected);
			flag = true;
		} catch (Exception e) {
			flag = false;

		} finally {
			if (flag) {
				reporter.SuccessReport("CheckPoint :: ", expected + " is  present  : " + locatorName);
			} else {
				reporter.failureReport("CheckPoint :: ", expected + " is not present  : " + locatorName, driver);
			}
		}
	}

	/**
	 * validateCheckPointContains verifies whether actual text using locator and
	 * getText method matches to the text provided
	 *
	 * @param by          of (By)
	 * @param text        of (String)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected void validateCheckPointContains(By by, String expected, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			LOG.info("Method : " + getCallerMethodName() + "  ::  Locator : " + locatorName);
			String ActualText = getText(by, locatorName).trim();
			LOG.info("ActualText is : " + ActualText);
			if (ActualText.contains(expected)) {
				reporter.SuccessReport("CheckPoint :: ", expected + " is  present  : " + locatorName);
				Assert.assertTrue(true);
			} else {
				reporter.failureReport("CheckPoint :: ", expected + " is not present  : " + locatorName, driver);
				Assert.assertTrue(false);

			}

		} catch (Exception e) {
			reporter.failureReport("CheckPoint :: ", expected + " is not present  : " + locatorName, driver);
		}

	}

	/**
	 * validateCheckPointEquals verifies actual text and expected text method
	 * matches to the text provided
	 *
	 * @param by          of (String)
	 * @param text        of (String)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	protected void validateCheckPointEquals(String actual, String expected) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			Assert.assertEquals(actual, expected);
			flag = true;

		} catch (Exception e) {
			flag = false;

		} finally {
			if (flag) {
				reporter.SuccessReport("CheckPoint :: ", actual + "," + expected + " are same");
			} else {
				reporter.failureReport("CheckPoint :: ", actual + "," + expected + " are not same", driver);
			}

		}
	}

	protected void validateCheckPointTrue(boolean status, String message) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			Assert.assertTrue(status, message);
			flag = true;
		} catch (Exception e) {
			flag = false;

		} finally {
			if (flag) {
				reporter.SuccessReport("CheckPoint :: ", message);
			} else {
				reporter.failureReport("CheckPoint :: ", message, driver);
			}

		}
	}

	protected void validateCheckPointFalse(boolean status, String message) throws Throwable {
		boolean flag = false;
		try {
			LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
			Assert.assertFalse(status, message);
			flag = true;
		} catch (Exception e) {
			flag = false;

		} finally {
			if (flag) {
				reporter.SuccessReport("CheckPoint :: ", message);
			} else {
				reporter.failureReport("CheckPoint :: ", message, driver);
			}

		}
	}

	protected String getYearFullName(String monthShortCut) {
		String YearString;

		switch (monthShortCut) {
		case "Jan":
			YearString = "January";
			break;
		case "Feb":
			YearString = "February";
			break;
		case "Mar":
			YearString = "March";
			break;
		case "Apr":
			YearString = "April";
			break;
		case "May":
			YearString = "May";
			break;
		case "Jun":
			YearString = "June";
			break;
		case "Jul":
			YearString = "July";
			break;
		case "Aug":
			YearString = "August";
			break;
		case "Sep":
			YearString = "September";
			break;
		case "Oct":
			YearString = "October";
			break;
		case "Nov":
			YearString = "November";
			break;
		case "Dec":
			YearString = "December";
			break;
		default:
			YearString = "Invalid Year";
			break;
		}
		return YearString;
	}

	public void scrollToElement(By locator) throws Throwable {
		try {
			WebElement element = driver.findElement(locator);
			JavascriptExecutor myExecutor = ((JavascriptExecutor) WebDriver);
			myExecutor.executeScript("arguments[0].scrollIntoView()", element);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			reporter.failureReport("scroll to view :: ", "failed to scroll" + e.getMessage(), driver);
		}

	}
	/**
	 * verifyKendoDropDownValueNOTEXIST verifies drop down values existence
		 *
	 * @param by          of (String)
	 * @param text        of (String)
	 * @param locatorName of (String) - Name of the element/locator
	 * @return boolean
	 * @throws Throwable the throwable
	 */
	public void verifyKendoDropDownValueNOTEXIST(By locator, String locatorName, String value) throws Throwable {
		try {

			click(locator, locatorName);
			Thread.sleep(2000);
			By byTypeLocator = By.xpath("//li[contains(.,'" + value + "')]");

			if (isElementDisplayed(byTypeLocator, "Dropdown value " + value)) {
				reporter.failureReport("Kendo DropDown", "Dropdown value should not appear:- " + value, driver);

			} else {

				reporter.SuccessReport("Kendo DropDown", "As Expected Dropdown value does not exist"+value);
			}

		} catch (Exception e) {
			reporter.failureReport("Kendo DropDown", "Failed to Find the list of values from dropdown " + value,
					driver);
		}

	}

}
