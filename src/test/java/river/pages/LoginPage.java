package river.pages;

import org.openqa.selenium.By;

import com.anikasystems.common.reports.ConfigFileReadWrite;
import com.anikasystems.common.reports.ReporterConstants;
import com.anikasystems.common.support.DecryptData;

import seleniumUtilities.GenericMethods;


public class LoginPage extends GenericMethods {

	protected String APP1_USERNAME = ConfigFileReadWrite.read(ReporterConstants.configReporterApp1File,
			"app1_username");
	protected String APP1_PASSWORD = ConfigFileReadWrite.read(ReporterConstants.configReporterApp1File,
			"app1_password");
	
	protected String app1URL = ConfigFileReadWrite.read(ReporterConstants.configReporterApp1File,
			"app1URL");
	
	DecryptData decryptdata = new DecryptData();

	By iframeRiver = By.id("loginFrame");
	By txtLoginName = By.id("input81");
	By txtLoginPassword = By.id("input89");
	By btnLogin = By.xpath("//input[@title='Sign in']");
	By btnLogout = By.id("river_toolbar_logout");
	By riverLogo = By.id("River_header_nciLogo");
	//By lnkProvisionalAccess = By.linkText("Request Provisional Access");
    By imgHelpLogo = By.xpath("//a[@title='Help']");
    
  	
    /**
  	 * return ::void throws :: throwable methodName :: Login description :: This is
  	 * for Login into the application
  	 */
  	public void loginIntoRiverApp() throws Throwable {
  		moveToElement(imgHelpLogo, "Help Logo");
		if(isElementDisplayed(btnLogout, "Logout")) {
			clickLogout();
			driver.manage().deleteAllCookies();
			Thread.sleep(2000);
			driver.navigate().refresh();
			login();
		}
		else {
			//driver.manage().deleteAllCookies();
			//Thread.sleep(2000);
			//driver.navigate().refresh();
			//Thread.sleep(2000);
			//driver.get(app1URL);
			waitForVisibilityOfElement(riverLogo);
			login();
		}
	}

	
	public void login() throws Throwable {
		clearGlobalVariables();
		//if(isElementDisplayed(riverLogo, "Logout")) {
			switchToFrameByIndex(0);
			enterUserName(APP1_USERNAME);
			enterPassword(APP1_PASSWORD);
			clickOnLoginButton();
			//waitForHomePage();
			comeOutFromFrame();
			waitForElementToBeClickable(btnLogout);
			if(!isElementPresent(btnLogout, "Log Out")) {
				reporter.failureReport("Login", "Failed to login,Please check user details", driver);
				assertElementPresent(btnLogout, "Log Out");
			}
			else {
				reporter.SuccessReport("Login", "Scessfully login into the application");
			}
			/*
			 * } else { reporter.failureReport("Application Down",
			 * "Login page issue or application down Please check", driver); }
			 */
	}
	/**
	 * param :: String inputs return ::void throws :: throwable methodName ::
	 * enterUserName description :: Provide LoginName in UserName Field
	 */
	public void enterUserName(String userName) throws Throwable {
		waitForVisibilityOfElement(txtLoginName, "User Name Text Field");
		setText(txtLoginName, userName, "User Name");
	}

	/**
	 * param :: String inputs return ::void throws :: throwable methodName ::
	 * enterPassword description :: Provide text in Password field
	 */
	public void enterPassword(String password) throws Throwable {
		try {
		String decryptedPassword = decryptdata.DecryptDataValue(password);
		waitForVisibilityOfElement(txtLoginPassword, "Password Text Field");
		driver.findElement(txtLoginPassword).sendKeys(decryptedPassword);
		}catch (Exception e) {
			reporter.failureReport("Password", "While entering the password it failed to decrypt", driver);
		}
	}

	/**
	 * param :: String inputs return ::void throws :: throwable methodName ::
	 * clickOnLoginButton description :: Clicks on Login Button
	 */
	public void clickOnLoginButton() throws Throwable {
		waitForVisibilityOfElement(btnLogin, "Login button");
		click(btnLogin, "Login Button");
	}

	private void waitForHomePage() throws Throwable {
		waitForElementToBeClickable(btnLogout, "Home Page is displayed");
	}
	
	public void clickLogout() throws Throwable {
		waitForVisibilityOfElement(btnLogout, "Logout");
		click(btnLogout, "Logout");
	}
	
	
	public void clearGlobalVariables() {
		
		
	}


	public void loginIntoRiverApp(String role) throws Throwable {
		moveToElement(imgHelpLogo, "Help Logo");
		if(isElementDisplayed(btnLogout, "Logout")) {
			clickLogout();
			driver.manage().deleteAllCookies();
			Thread.sleep(2000);
			driver.navigate().refresh();
			loginWithRole(role);
		}
		else {
			
			waitForVisibilityOfElement(riverLogo);
			loginWithRole(role);
		}
		
	}
	
	/**
	 *Login to River based on role
	 * @param role
	 * @throws Throwable
	 */
	public void loginWithRole(String role) throws Throwable {
		clearGlobalVariables();
		if(isElementDisplayed(riverLogo, "Logout")) {
			switchToFrameByIndex(0);
			enterUserName(APP1_USERNAME);
			enterPassword(APP1_PASSWORD);
	
			clickOnLoginButton();
			
			comeOutFromFrame();
			waitForElementToBeClickable(btnLogout);
			if(!isElementPresent(btnLogout, "Log Out")) {
				reporter.failureReport("Login", "Failed to login,Please check user details", driver);
				assertElementPresent(btnLogout, "Log Out");
			}
			else {
				reporter.SuccessReport("Login", "Scessfully login into the application");
			}
		}
		else {
			reporter.failureReport("Application Down", "Login page issue or application down Please check", driver);
		}
	}
}
