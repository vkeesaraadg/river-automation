package seleniumUtilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.anikasystems.common.reports.CReporter;
import com.anikasystems.common.reports.ConfigFileReadWrite;
import com.anikasystems.common.reports.ReporterConstants;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Driver Script/Base Runner to load/close the configurations before/after each test Run and also configurations to execute in different browsers
 * @author CignitiTeam
 *
 */
public class BaseRunner extends AbstractTestNGCucumberTests{
	
	public static Logger LOG = Logger.getLogger(BaseRunner.class);
	public static long startTime;
	protected static CReporter reporter = null;
	public static String suiteStartTime = null;
	public static String landingURL = null;
	
	protected static WebDriver WebDriver = null;
	protected static EventFiringWebDriver driver = null;
	public final static int TIMEOUT = 30;
    public final static int PAGE_LOAD_TIMEOUT = 50;
    public static String browserName;
    public static String executionType = "";
    public static String applicationName = "";

    String SELENIUM_GRID_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "seleniumgridURL");
	String APP1_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterApp1File, "app1URL");
		
	@BeforeClass(alwaysRun = true)	
	@Parameters({ "automationName", "browser", "browserVersion", "environment", "platformName","appName"})
	public void beforeTest(String automationName, String browser, String browserVersion, String environment,
		  String platformName,String appName) throws Throwable {		  
		browserName = browser;
		executionType = environment;
		applicationName = appName;
		  startTime = System.currentTimeMillis(); Date date = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
		  String formattedDate = sdf.format(date); suiteStartTime =
		  formattedDate.replace(":", "_").replace(" ", "_");
		  System.out.println("Suite time ==============>" + suiteStartTime);
		  if (environment.equalsIgnoreCase("local")) {
			  this.setWebDriverForLocal(browser, environment);
		  }
		  else if(environment.equalsIgnoreCase("grid")) {
			  this.setWebDriverForGrid(browser, environment);			  
		  }
		  else{
			  System.out.println("Invalid option, please pass valid environment");
		  }
		  reporter = CReporter.getCReporter(browser, browserVersion, platformName, appName, true);

		  driver = new EventFiringWebDriver(this.WebDriver);
		  System.out.println("appName##" + appName);
		  switch (appName) {
		  case "River":
			  LOG.info("++++++++++++++++++++++++++++" + APP1_URL + "+++++++++++++++++++++++++++++++++++++++++++");
			  System.out.println("appName##" + appName + "##APP1_URL##" + APP1_URL);
			  driver.get(APP1_URL);		
			  landingURL = APP1_URL;
			  break;
		 
			  
			  
			  
		  }
		  Thread.sleep(5000);
		  reporter.calculateSuiteStartTime();
    }
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		try {
			driver.close();
			driver.quit();
		}catch (Exception e) {
			System.out.println("Exception "+e);
		}
		if(CReporter.detailedReportStatus == true) {
			
	
		System.out.println("**************************************Sumary Report &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		reporter.calculateSuiteExecutionTime();
		reporter.createHtmlSummaryReport(landingURL, true);
		reporter.closeSummaryReport();
		}
	}
	
	
	 /**
     * setWebDriverForLocal :: Setting webdriver based on Browser which is going to be executed
     * param :: browser
     * param :: environment
     * throws :: IOException, InterruptedException
     */
	@SuppressWarnings("deprecation")
	public void setWebDriverForLocal(String browser, String environment) throws IOException, InterruptedException {

		switch (browser) {
		case "firefox":
			System.out.println("Executing in firefox Browser");
			String downloadPath = System.getProperty("user.dir")+"\\resources\\common\\testData";
			DesiredCapabilities ffcapabilities = new DesiredCapabilities().firefox();
			 FirefoxProfile profile = new FirefoxProfile();
			 profile.setAcceptUntrustedCertificates(true);
			 profile.setAssumeUntrustedCertificateIssuer(false);
			  profile.setPreference("permissions.default.desktop-notification", 1);
			  profile.setPreference("dom.webnotifications.enabled", false);
			  profile.setPreference("dom.push.enabled", false);
			  ffcapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			 profile.setPreference("browser.download.folderList",2);  
			 profile.setPreference("browser.download.manager.showWhenStarting", false); 
			 profile.setPreference("browser.download.dir", downloadPath); 
			 profile.setPreference("browser.helperApps.neverAsk.openFile","application/excel/pdf");

			 profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/excel/pdf");

			
			 profile.setPreference("pdfjs.disabled", true);
			 profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			 profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
			// profile.set_preference("pdfjs.disabled", True)
			 profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf") ;
//			 profile.setPreference("browser.download.manager.showAlertOnComplete", false); 
//			 profile.setPreference("browser.download.manager.closeWhenDone", false);
		 
		        // Creating FirefoxOptions to set profile
		        FirefoxOptions option = new FirefoxOptions();
		        option.setProfile(profile);
		        // Launching browser with desired capabilities
		      
		        ffcapabilities.setCapability(FirefoxDriver.PROFILE, profile);
		        //capabilities.setCapability(CapabilityType.PROXY, proxy);
			//System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			 WebDriverManager.firefoxdriver().setup();

			  WebDriver = new FirefoxDriver(ffcapabilities);
			WebDriver.manage().window().maximize();
			Thread.sleep(5000);
			break;
			
		case "ie":
			Thread.sleep(10000);
			System.out.println("Executing in IE Browser");
			String iefileDownload = System.getProperty("user.dir")+"\\resources\\common\\testdata";
			DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
			//capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capab.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
			File file = new File("Drivers\\IEDriverServer.exe");
			capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capab.setJavascriptEnabled(true);
			capab.setCapability("requireWindowFocus", true);
			capab.setCapability("extractpath", iefileDownload);
			capab.setCapability("enablePersistentHover", false);
			//System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			WebDriverManager.iedriver().setup();

			WebDriver = new InternetExplorerDriver(capab);
			break;

		case "chrome":
			System.out.println("Executing in Chrome Browser");
			//System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();

			DesiredCapabilities capabilities = new DesiredCapabilities();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
			options.addArguments("test-type");
			options.setAcceptInsecureCerts(true);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);		
			options.addArguments("start-maximized"); // open Browser in maximized mode
		    options.addArguments("disable-infobars"); // disabling infobars
		    options.addArguments("--disable-extensions"); // disabling extensions
		    options.addArguments("--disable-gpu"); // applicable to windows os only
		    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		    options.addArguments("enable-automation"); 
		    options.addArguments("--disable-browser-side-navigation");
		    String downloadFilepath = System.getProperty("user.dir")+"\\resources\\common\\testData";
		    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		    chromePrefs.put("plugins.always_open_pdf_externally", true);
		    chromePrefs.put("download.default_directory", downloadFilepath);
		    options.setExperimentalOption("prefs", chromePrefs);		    
			options.merge(capabilities);
			WebDriver = new ChromeDriver(options);
			break;

		case "edge":
			System.out.println("Executing in Edge Browser ");
			//System.setProperty("webdriver.edge.driver", "Drivers\\MicrosoftWebDriver.exe");
			WebDriverManager.edgedriver().setup();
//			WebDriver = new EdgeDriver();
//			WebDriver.manage().window().maximize();
			DesiredCapabilities capability = DesiredCapabilities.edge();
	        capability.setBrowserName("MicrosoftEdge");
	        capability.setBrowserName(BrowserType.EDGE);
	        capability.setPlatform(Platform.WIN10);
	        WebDriver = new EdgeDriver();
			break;
		}
	}
	
	 private Capabilities FirefoxDriverProfile() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
     * setWebDriverForGrid :: Setting webdriver for Selenium Grid, to pass the webdriver to Hub/Node
     * param :: browser
     * param :: environment
     * throws :: IOException, InterruptedException
     */
//	@SuppressWarnings("deprecation")
	public void setWebDriverForGrid(String browser, String environment) throws IOException, InterruptedException {

		switch (browser) {
		case "firefox":
			System.out.println("Executing in firefox Browser using Grid");
			//System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			firefoxoptions.addPreference("network.proxy.type", 0);
			WebDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), firefoxoptions);
			WebDriver.manage().window().maximize();
			Thread.sleep(5000);
			break;
			
		case "ie":
			Thread.sleep(10000);
			System.out.println("Executing in IE Browser using Grid");
			InternetExplorerOptions ieCap = new InternetExplorerOptions();
			File file = new File("Drivers\\IEDriverServer.exe");
			//ieCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCap.setCapability("ignoreProtectedModeSettings", true);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			WebDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), ieCap);						
			break;

		case "chrome":
			System.out.println("Executing in Chrome Browser using Grid");
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
			options.addArguments("test-type");
			options.setAcceptInsecureCerts(true);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);		
			options.addArguments("start-maximized"); // open Browser in maximized mode
		    options.addArguments("disable-infobars"); // disabling infobars
		    options.addArguments("--disable-extensions"); // disabling extensions
		    options.addArguments("--disable-gpu"); // applicable to windows os only
		    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		    options.addArguments("enable-automation"); 
		    options.addArguments("--disable-browser-side-navigation");
		    String downloadFilepath = "\\\\sun\\work\\JIRA\\RIVERDeploy\\Test_Automation_PDF\\Download";
		    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		    chromePrefs.put("plugins.always_open_pdf_externally", true);
		    chromePrefs.put("download.default_directory", downloadFilepath);
		    options.setExperimentalOption("prefs", chromePrefs);
			capabilities.setBrowserName("chrome");
			options.merge(capabilities);
			 System.out.println(options.getExperimentalOption("prefs"));
			 
			WebDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
			break;
			
		case "edge":
			System.out.println("Executing in Edge Browser using Grid");
			System.setProperty("webdriver.edge.driver", "Drivers\\MicrosoftWebDriver.exe");
			WebDriver = new EdgeDriver();
			WebDriver.manage().window().maximize();
			break;
		}
	}
}
