package seleniumUtilities;

import org.openqa.selenium.By;

import com.anikasystems.common.reports.ConfigFileReadWrite;
import com.anikasystems.common.reports.ReporterConstants;

import seleniumUtilities.GenericMethods;

public class CommonEmailValidations extends GenericMethods {

	By txtUserName = By.name("loginfmt");
	By btnNext = By.xpath("//input[@type='submit' and @value='Next']");
	By txtPassword = By.name("passwd");
	By btnSignIn = By.xpath("//input[@type='submit' and @value='Sign in']");
	By outlookIcon = By.id("ShellMail_link_text");
	By btnNew = By.xpath("//button[@title='Write a new message (N)']");
	By divUseAnotherAccount = By.id("otherTileText");
	By btnYes = By.xpath("//input[@class='btn btn-block btn-primary']");
	By btnNo = By.xpath("//input[@class='btn btn-block']");

	By btnMenuItem = By.xpath("//button[contains(@id,'MeFlexPane_ButtonID') and @role='menuitem']//div[contains(@class,'mfp-circular-small')]//div");
	//By lnkSignOut = By.xpath("//a[contains(@id,'SubLink_ShellSignout') and @role='link']");
	By lnkSignOut = By.xpath("//span[text()='Sign out']");
	
	String microsoftURl = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "microsofUrl");
	String userName = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "microsoftAccUserName");
	String password = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "microsoftAccUserPassword");

	public String parentWindow;

	public void navigateToMicrosoftEmail() throws Throwable {
		driver.navigate().to(microsoftURl);
		driver.manage().deleteAllCookies();
		Thread.sleep(3000);
	}

	public void loginToMicrosoftAccount() throws Throwable {

		driver.navigate().refresh();
		parentWindow = getWindowHandle();
		if (waitForVisibilityOfElement(txtUserName)) {
			login();
		} else if (waitForVisibilityOfElement(divUseAnotherAccount)) {
			clickJS(divUseAnotherAccount, "Use another account");
			login();
		} else if(waitForVisibilityOfElement(btnMenuItem)){
			clickSignOut();
			
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			navigateToMicrosoftEmail();
			if (waitForVisibilityOfElement(txtUserName)) {
				login();
			} else if (waitForVisibilityOfElement(divUseAnotherAccount)) {
				clickJS(divUseAnotherAccount, "Use another account");
				login();
			}
		}
	}

	private void login() throws Throwable {
		Thread.sleep(3000);
		setText(txtUserName, userName, "UserName");
		click(btnNext, "Next");
		waitForVisibilityOfElement(txtPassword, "Password");
		setText(txtPassword, password, "Password");
		click(btnSignIn, "Sign in");
		if(isElementDisplayed(btnNo, "No")) {
			clickJS(btnNo, "No Button");
		}
		waitForElementToBeClickable(btnNew);
		reporter.SuccessReport("Email Login", "Sucessfully Login");

	}

	public void closeAndSwitchToparentWindow() throws Throwable {
		
		if(browserName.equals("ie") || browserName.equals("firefox")) {
			Thread.sleep(3000);
			clickSignOut();
		}
		else {
			Thread.sleep(3000);
			//driver.navigate().refresh();
			clickSignOut();
		}
		
		
	}

	public void clickSignOut() throws Throwable {
		Thread.sleep(4000);
//		if(isElementVisible(btnMenuItem,"MenuItem")) {
//			setFocusAndClick(btnMenuItem, "MenuItem");
//			Thread.sleep(3000);
//			if(isElementVisible(lnkSignOut,"Sign Out")) {
//				click(lnkSignOut, "Sign Out");
//			}
//			else {
//				Thread.sleep(5000);
//				click(btnMenuItem, "MenuItem");
//				Thread.sleep(3000);
//				click(lnkSignOut, "Sign Out");
//			}
//			
//		}
//		
//
		setFocusAndClick(btnMenuItem, "Menu Item");
		Thread.sleep(4000);
		moveToElement(lnkSignOut, "Sign Out");
		click(lnkSignOut, "Sign Out");
		
	}
}
