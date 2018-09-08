package com.BasePackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.winium.DesktopOptions;
//import org.openqa.selenium.winium.WiniumDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SeleniumUtils {
	
	public static WebDriver driver, winDriver;
	private static ExtentReports report;
	private static ExtentTest logger;
	private static XSSFWorkbook writeWorkBook;
	private static XSSFSheet writeSheet;
	private static File reportDir;
	private static File writeExcelDir;
	private static File screenDir;
	private static int row=0;
	private static int col=0;
	private static boolean readStatus;
	public static final String releaseName="Test Automation Testing";
		
	static DateFormat dateFormat = new SimpleDateFormat("MMMdd_HHmm");
	static Date date = new Date();
	private static String ReportName="Report_"+dateFormat.format(date);
		
	/********************************************Selenium Operations**************************************************/
	/*****************************************************************************************************************/
	/**
	 * Initialize Chrome Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver ChromeBrowser() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		driver=new ChromeDriver();
		BrowserOper("ChromeBrowser", "");
		return driver;
	}
	
	/**
	 * Initialize Chrome Browser
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver ChromeBrowser(String message) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		driver=new ChromeDriver();
		BrowserOper("ChromeBrowser",message);
		return driver;
	}
	
	/**
	 * Initialize Chrome User Profile Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver ChromeUserProfileBrowser() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir="+System.getProperty("user.dir")+"\\Chrome\\ChromeProfile");
		driver = new ChromeDriver(options);
		BrowserOper("ChromeUserProfileBrowser", "");
		return driver;
	}
	
	/**
	 * Initialize Chrome User Profile Browser
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver ChromeUserProfileBrowser(String message) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir="+System.getProperty("user.dir")+"\\Chrome\\ChromeProfile");
		//options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		BrowserOper("ChromeUserProfileBrowser", message);
		return driver;
	}
	
	/**
	 * Initialize Firefox Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver FirefoxBrowser() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Driver\\geckodriver.exe");
		driver = new FirefoxDriver();
		BrowserOper("FirefoxBrowser", "");
		return driver;
	}
	
	/**
	 * Initialize Firefox Browser
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver FirefoxBrowser(String message) {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Driver\\geckodriver.exe");
		driver = new FirefoxDriver();
		BrowserOper("FirefoxBrowser", message);
		return driver;
	}
	
	/**
	 * Initialize InternetExplorer Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver InternetExplorerBrowser() {
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Driver\\MicrosoftWebDriver.exe");
		driver=new InternetExplorerDriver();
		BrowserOper("InternetExplorerBrowser", "");
		return driver;
	}
	
	/**
	 * Initialize InternetExplorer Browser
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver InternetExplorerBrowser(String message) {
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Driver\\MicrosoftWebDriver.exe");
		driver=new InternetExplorerDriver();
		BrowserOper("InternetExplorerBrowser", message);
		return driver;
	}
	
	/**
	 * Initialize HTML Headless Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver HTMLHeadlessBrowser() {
		driver=new HtmlUnitDriver();
		BrowserOper("HTMLHeadlessBrowser", "");
		return driver;
	}
	
	/**
	 * Initialize HTML Headless Browser
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver HTMLHeadlessBrowser(String message) {
		driver=new HtmlUnitDriver();
		BrowserOper("HTMLHeadlessBrowser", message);
		return driver;
	}
	
	/**
	 * Initiate Opera Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver OperaBrowser() {
		System.setProperty("webdriver.opera.driver", System.getProperty("user.dir")+"\\Driver\\operadriver.exe");
		driver = new OperaDriver();
		BrowserOper("OperaBrowser", "");
		return driver;
	}
	
	/**
	 * Initiate Opera Browser 
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver OperaBrowser(String message) {
		System.setProperty("webdriver.opera.driver", System.getProperty("user.dir")+"\\Driver\\operadriver.exe");
		driver = new OperaDriver();
		BrowserOper("OperaBrowser", message);
		return driver;
	}
	
	/**
	 * Initiate Safari Browser
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver SafariBrowser() {
		System.setProperty("webdriver.safari.driver", System.getProperty("user.dir")+"\\Driver\\safaridriver.exe");
		driver = new SafariDriver();
		BrowserOper("SafariBrowser", "");
		return driver;
	}
	
	/**
	 * Initiate Safari Browser 
	 * @param message
	 * @return WebDriver
	 * @author satyam.kumar
	 */
	public WebDriver SafariBrowser(String message) {
		System.setProperty("webdriver.safari.driver", System.getProperty("user.dir")+"\\Driver\\safaridriver.exe");
		driver = new SafariDriver();
		BrowserOper("SafariBrowser", message);
		return driver;
	}
	
	/**
	 * Finishing of browser
	 * @param message
	 * @author satyam.kumar
	 */
	private void BrowserOper(String method, String message) {
		driver.manage().window().maximize();
		String testName=this.getClass().getName();
		testName=testName.substring(testName.lastIndexOf('.')+1);
		CreateTest(releaseName, testName);
		WriteToExcelSheet(method, message, "", "", "", "");
	}

	/**
	 * Locator Identification at RunTime
	 * @param objectData
	 * @return ByValue
	 * @author satyam.kumar
	 */
	private By locatorIdentifiers(String objectData) {
		
		String locator=objectData.substring(0, objectData.indexOf('['));
		String locatorVal=objectData.substring(objectData.indexOf('[')+1, objectData.lastIndexOf(']'));
		
		By ByVal=null;
		String string=locator.toLowerCase();
		
		if(string.equals("id"))
			ByVal = By.id(locatorVal);
		else if(string.equals("name"))
			ByVal = By.name(locatorVal);
		else if(string.contains("xpath"))
			ByVal=By.xpath(locatorVal);
		else if(string.contains("part"))
			ByVal=By.partialLinkText(locatorVal);
		else if(string.contains("link"))
			ByVal=By.linkText(locatorVal);
		else if(string.contains("css"))
			ByVal=By.cssSelector(locatorVal);
		else if(string.contains("class"))
			ByVal=By.className(locatorVal);
		else if(string.contains("tag"))
			ByVal=By.tagName(locatorVal);
		
		return ByVal;
	}
	
	/**
	 * Find WebElement using driver.findElement Method
	 * if no element found, it will return null
	 * @param ByValue
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement findElement(By ByValue) {
		WebElement element=null;
		try {
			element=findElement(ByValue);
		}catch(Exception e) {
			System.out.println("::>>WebElement not found for ByValue:: '"+ByValue+"' <<::");
		}
		return element;
	}
	
	/**
	 * Find WebElement using driver.findElement Method
	 * if no element found, it will return null
	 * @param objectData
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement findElement(String objectData) {
		WebElement element=null;
		try {
			element =driver.findElement(locatorIdentifiers(objectData));
		}catch(Exception e) {
			System.out.println("::>>WebElement not found for objectData:: '"+objectData+"' <<::");
			WriteToExcelSheet("findElement", "", objectData, "webElement not found", "", "");
		}
		return element;
	}
	
	/**
	 * Find WebElement using driver.findElement Method
	 * if no element found, it will return null
	 * @param message
	 * @param objectData
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement findElement(String message, String objectData) {
		WebElement element=null;
		try {
			element =driver.findElement(locatorIdentifiers(objectData));
		}catch(Exception e) {
			System.out.println("::>>WebElement not found for objectData:: '"+objectData+"' <<::");
			WriteToExcelSheet("findElement", message, objectData, "webElement not found", "", "");
		}
		return element;
	}
	
	/**
	 * To perform driver.findElements method
	 * @param ByValue
	 * @return List<WebElement>
	 * @author satyam.kumar
	 */
	public List<WebElement> findElements(By ByValue) {
		List<WebElement> lst=driver.findElements(ByValue);
		if(lst==null) 
			System.out.println("::>>WebElements not found for ByValue:: '"+ByValue+"' <<::");
		return lst;
	}
	
	/**
	 * To perform driver.findElements method
	 * @param objectData
	 * @return List<WebElement>
	 * @author satyam.kumar
	 */
	public List<WebElement> findElements(String objectData) {
		List<WebElement> lst=driver.findElements(locatorIdentifiers(objectData));
		if(lst==null) {
			System.out.println("::>>WebElements not found for objectData:: '"+objectData+"' <<::");
			WriteToExcelSheet("findElements", "", "", "webElements not found", "", "");
		}
		return lst;
	}
	
	/**
	 * FluentWait Operation
	 * Total wait time is 30 seconds 
	 * and polling frequency is 50 milliseconds
	 * @param ByVal
	 * @return webElement
	 * @author satyam.kumar
	 */
	public WebElement Fluentwait(By ByValue){
		WebElement element=null;
		FluentWait<WebDriver> fwait =new FluentWait<WebDriver>(driver);
		  fwait.withTimeout(30, TimeUnit.SECONDS);
		  fwait.pollingEvery(50,TimeUnit.MILLISECONDS);
		   fwait.ignoring(NoSuchElementException.class);
		 fwait.until(ExpectedConditions.visibilityOfElementLocated(ByValue));
		 try{
			 element=driver.findElement(ByValue);
		 }catch(Exception e) {System.out.println("::>>WebElements not found for ByValue:: '"+ByValue+"' <<::");}
		 return element;
	}
	
	/**
	 * FluentWait Operation
	 * Total wait time is 30 seconds 
	 * and polling frequency is 50 milliseconds
	 * @param objectData
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement Fluentwait(String objectData){
		WebElement element=null;
		FluentWait<WebDriver> fwait =new FluentWait<WebDriver>(driver);
		  fwait.withTimeout(30, TimeUnit.SECONDS);
		  fwait.pollingEvery(50,TimeUnit.MILLISECONDS);
		   fwait.ignoring(NoSuchElementException.class);
		 fwait.until(ExpectedConditions.visibilityOfElementLocated(locatorIdentifiers(objectData)));
		 try{
			 element=driver.findElement(locatorIdentifiers(objectData));
		 }catch(Exception e) {System.out.println("::>>WebElements not found for objectData:: '"+objectData+"' <<::");}
		 return element;
	}
	
	/**
	 * FluentWait Operation
	 * Total wait time is 30 seconds 
	 * and polling frequency is 50 milliseconds
	 * @param message
	 * @param objectData
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement Fluentwait(String message,String objectData){
		WebElement element=null;
		FluentWait<WebDriver> fwait =new FluentWait<WebDriver>(driver);
		  fwait.withTimeout(30, TimeUnit.SECONDS);
		  fwait.pollingEvery(50,TimeUnit.MILLISECONDS);
		   fwait.ignoring(NoSuchElementException.class);
		 fwait.until(ExpectedConditions.visibilityOfElementLocated(locatorIdentifiers(objectData)));
		 try{
			 element=driver.findElement(locatorIdentifiers(objectData));
		 }catch(Exception e) {System.out.println("::>>WebElements not found for objectData:: '"+objectData+"' <<::");}
		 return element;
	}
		
	/**
	 * External Wait 
	 * for Click Operation
	 * @param ByValue
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeClickable(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(ByValue));
	}
	
	/**
	 * External Wait 
	 * for Click Operation
	 * @param objectData
	 * @param seconds
	 * @author satyam.kumar
	 */
	public void WaitUntilElementToBeClickable(String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeClickable", "", "", "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Click Operation
	 * @param message
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeClickable(String message, String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeClickable", message, "", "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Click Operation
	 * @param element
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeClickable(WebElement element, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		WriteToExcelSheet("WaitUntilElementToBeClickable", "", "", "", "", "");
	}
		
	/**
	 * External Wait 
	 * for Select Operation
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeSelected(String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeSelected", "", objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Select Operation
	 * Maximum Wait time allocated is 300 Seconds
	 * @param message
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeSelected(String message, String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeSelected", message, objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Select Operation
	 * @param element
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeSelected(WebElement element, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(element));
		WriteToExcelSheet("WaitUntilElementToBeSelected", "", "", "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Select Operation
	 * @param ByVal
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeSelected(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(ByValue));
	}
	
	/**
	 * External Wait 
	 * for Visibility Operation
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeVisible(String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeVisible", "", objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Visibility Operation
	 * @param message
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeVisible(String message, String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeVisible", message, objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Visibility Operation
	 * @param element
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeVisible(WebElement element, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		WriteToExcelSheet("WaitUntilElementToBeVisible", "", "", "", "", "");
	}
	
	/**
	 * External Wait 
	 * for Visibility Operation
	 * @param ByVal
	 * @param seconds
	 * @author satyam.kumar
	 */
	public void WaitUntilElementToBeVisible(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ByValue));
	}
	
	/**
	 * External Wait 
	 * for InVisibility Operation
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeInVisible(String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeInVisible", "", objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for InVisibility Operation
	 * @param message
	 * @param objectData
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeInVisible(String message, String objectData, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorIdentifiers(objectData)));
		WriteToExcelSheet("WaitUntilElementToBeInVisible", message, objectData, "", "", "");
	}
	
	/**
	 * External Wait 
	 * for InVisibility Operation
	 * @param element
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeInVisible(WebElement element, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOf(element));
		WriteToExcelSheet("WaitUntilElementToBeInVisible", "", "", "", "", "");
	}
	
	/**
	 * External Wait 
	 * for InVisibility Operation
	 * @param ByVal
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeInVisible(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ByValue));
	}
	
	/**
	 * External Wait 
	 * for InVisibility Operation
	 * Till specified Text is disappeared
	 * @param ByValue
	 * @param text
	 * @param seconds
	 * @author satyam kumar
	 */
	public void WaitUntilElementToBeInVisible(By ByValue, String text, int seconds) {
		WebDriverWait wait=new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(ByValue, text));
	}
	
	/**
	 * SendKeys Operation
	 * @param ByValue
	 * @param text
	 * @author satyam.kumar
	 */
	public void SetTextValue(By ByValue, String text) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			ele.sendKeys(text);
	}
	
	/**
	 * SendKeys Operation
	 * @param objectData
	 * @param text
	 * @author satyam.kumar
	 */
	public void SetTextValue(String objectData, String text) {
		WebElement ele=Fluentwait("",objectData);
		if(ele!=null) {
			ele.sendKeys(text);
			WriteToExcelSheet("SetTextValue", "", objectData, text, "", "");
		}
	}
	
	/**
	 * SendKeys Operation
	 * @param message
	 * @param objectData
	 * @param text
	 * @author satyam.kumar
	 */
	public void SetTextValue(String message, String objectData, String text) {
		WebElement ele=Fluentwait(message,objectData);
		if(ele!=null) {
			ele.sendKeys(text);
			WriteToExcelSheet("SetTextValue", message, objectData, text, "", "");
		}
	}
	
	/**
	 * Enter data in textfield/textarea and press enter
	 * @param ByValue
	 * @param inputData
	 * @author satyam.kumar
	 */
	public void SetTextAndPressEnter(By ByValue, String inputData) {
		WebElement element=Fluentwait(ByValue);
		if(element!=null) {
			element.clear();
			element.sendKeys(inputData);
			Sleep(200);
			element.sendKeys(Keys.ENTER);
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Enter data in textfield/textarea and press enter
	 * @param ByValue
	 * @param inputData
	 * @author satyam.kumar
	 */
	public void SetTextAndPressEnter(String objectData, String inputData) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			element.clear();
			element.sendKeys(inputData);
			Sleep(200);
			element.sendKeys(Keys.ENTER);
		}
		else
			System.out.println("::>> Element value is null <<::");
		WriteToExcelSheet("EnterDataAndPressEnter", "", objectData, inputData, "", "");
	}
	
	/**
	 * Enter data in textfield/textarea and press enter
	 * @param message
	 * @param ByValue
	 * @param inputData
	 * @author satyam.kumar
	 */
	public void SetTextAndPressEnter(String message, String objectData, String inputData) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			element.clear();
			element.sendKeys(inputData);
			Sleep(200);
			element.sendKeys(Keys.ENTER);
		}
		else
			System.out.println("::>> Element value is null <<::");
		WriteToExcelSheet("EnterDataAndPressEnter", message, objectData, inputData, "", "");
	}
	
	/**
	 * Get Text using getText method
	 * @param ByValue
	 * @return getText
	 * @author satyam.kumar
	 */
	public String GetTextValue(By ByValue) {
		String textValue=null;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) {
			textValue=ele.getText();
			System.out.println("::>>Get Text value for '"+ByValue+"' is:: "+textValue+" <<::");
		}
		return textValue;
	}
	
	/**
	 * Get Text using getText method
	 * @param objectData
	 * @return getText
	 * @author satyam.kumar
	 */
	public String GetTextValue(String objectData) {
		String textValue=null;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			textValue=ele.getText();
			System.out.println("::>>Get Text value for '"+objectData+"' is:: "+textValue+" <<::");
			WriteToExcelSheet("GetTextValue", "", objectData, textValue, "", "");
		}
		return textValue;
	}
	
	/**
	 * Get Text using getText method
	 * @param message
	 * @param objectData
	 * @return getText
	 * @author satyam.kumar
	 */
	public String GetTextValue(String message, String objectData) {
		String textValue=null;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			textValue=ele.getText();
			System.out.println("::>>Get Text value for '"+objectData+"' is:: "+textValue+" <<::");
			WriteToExcelSheet("GetTextValue", message, objectData, textValue, "", "");
		}
		return textValue;
	}
	
	/**
	 * Get Attribute value using getAttribute method
	 * @param ByValue
	 * @param attribute
	 * @return attribute Value
	 * @author satyam.kumar
	 */
	public String GetAttributeValue(By ByValue, String attribute) {
		String textValue=null;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) {
			textValue=ele.getAttribute(attribute);
			System.out.println("::>>Attribute '"+attribute+"' value for '"+ByValue+"' is:: "+textValue+" <<::");
		}
		return textValue;
	}
	
	/**
	 * Get Attribute value using getAttribute method
	 * @param objectData
	 * @param attribute
	 * @return attribute Value
	 * @author satyam.kumar
	 */
	public String GetAttributeValue(String objectData, String attribute) {
		String textValue=null;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			textValue=ele.getAttribute(attribute);
			System.out.println("::>>Attribute '"+attribute+"' value for '"+objectData+"' is:: "+textValue+" <<::");
			WriteToExcelSheet("GetAttributeValue", "", objectData, textValue, "", "");
		}
		return textValue;
	}
	
	/**
	 * Get Attribute value using getAttribute method
	 * @param message
	 * @param objectData
	 * @param attribute
	 * @return attribute Value
	 * @author satyam.kumar
	 */
	public String GetAttributeValue(String message, String objectData, String attribute) {
		String textValue=null;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			textValue=ele.getAttribute(attribute);
			System.out.println("::>>Attribute '"+attribute+"' value for '"+objectData+"' is:: "+textValue+" <<::");
			WriteToExcelSheet("GetAttributeValue", message, objectData, textValue, "", "");
		}
		return textValue;
	}
	
	/**
	 * Verify Element isEnable
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isEnabled(By ByValue) {
		boolean status=false;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) {
			status=ele.isEnabled();
		}
		return status;
	}
		
	/**
	 * Verify Element isEnable
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isEnabled(String objectData) {
		boolean status=false;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			status=ele.isEnabled();
			WriteToExcelSheet("isEnabled", "", objectData, Boolean.toString(status), "", "");
		}
		return status;
	}
	
	/**
	 * Verify Element isEnable
	 * @param message
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isEnabled(String message, String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			stat=ele.isEnabled();
			WriteToExcelSheet("isEnabled", message, objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isSelected
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isSelected(By ByValue) {
		boolean stat=false;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			stat=ele.isSelected();
		return stat;
	}
	
	/**
	 * Verify Element isSelected
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isSelected(String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			stat=ele.isSelected();
			WriteToExcelSheet("isSelected", "", objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isSelected
	 * @param message
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isSelected(String message, String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			stat=ele.isSelected();
			WriteToExcelSheet("isSelected", message, objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isClickable
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isClickable(By ByValue) {
		boolean stat=false;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null && ele.isDisplayed()) 
			stat=ele.isEnabled();
		return stat;
	}
	
	/**
	 * Verify Element isClickable
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isClickable(String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null && ele.isDisplayed()) {
			stat=ele.isEnabled();
			WriteToExcelSheet("isClickable", "", objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isClickable
	 * @param message
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isClickable(String message, String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null && ele.isDisplayed()) {
			stat=ele.isEnabled();
			WriteToExcelSheet("isClickable", message, objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isDisplayed
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isDisplayed(By ByValue) {
		boolean stat=false;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			stat=ele.isDisplayed();
		return stat;
	}
	
	/**
	 * Verify Element isDisplayed
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isDisplayed(String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			stat=ele.isDisplayed();
			WriteToExcelSheet("isDisplayed", "", objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Verify Element isDisplayed
	 * @param message
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isDisplayed(String message, String objectData) {
		boolean stat=false;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			stat=ele.isDisplayed();
			WriteToExcelSheet("isDisplayed", message, objectData, Boolean.toString(stat), "", "");
		}
		return stat;
	}
	
	/**
	 * Perform Submit operation on WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void Submit(By ByValue) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			ele.submit();
	}
	
	/**
	 * Perform Submit operation on WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Submit(String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			ele.submit();
			WriteToExcelSheet("Submit", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Perform Submit operation on WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Submit(String message, String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			ele.submit();
			WriteToExcelSheet("Submit", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Implicit wait operation
	 * Maximum wait time is 60 seconds
	 * @author satyam.kumar
	 */
	public void ImplicitWait() {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	/**
	 * Quit Browser Instance
	 * @throws InterruptedException
	 * @author satyam.kumar
	 */
	public void QuitBrowser(){
		Sleep(2000);
		try {
			driver.quit();
		}catch(Exception e) {}
		WriteToExcelSheet("QuitBrowser", "", "", "", "", "");
		try {
			writeWorkBook.close();
			report.flush();
		}catch(Exception e) {}
	}
	
	/**
	 * Quit Browser Instance
	 * @param message
	 * @throws InterruptedException
	 * @author satyam.kumar
	 */
	public void QuitBrowser(String message){
		Sleep(2000);
		try {
			driver.quit();
		}catch(Exception e) {}
		WriteToExcelSheet("QuitBrowser", message, "", "", "", "");
		try {
			writeWorkBook.close();
			report.flush();
		}catch(Exception e) {}
	}
	
	/**
	 * Close Browser Instance
	 * @throws InterruptedException
	 * @author satyam.kumar
	 */
	public void CloseBrowser(){
		Sleep(1000);
		driver.close();
		WriteToExcelSheet("CloseBrowser", "", "", "", "", "");
	}
	
	/**
	 * Close Browser Instance
	 * @param message
	 * @throws InterruptedException
	 * @author satyam.kumar
	 */
	public void CloseBrowser(String message){
		Sleep(1000);
		driver.close();
		WriteToExcelSheet("CloseBrowser", message, "", "", "", "");
	}
	
	/**
	 * Open Url and wait for Page Load till 1 minute
	 * @param url
	 * @author satyam.kumar
	 */
	public void Get(String url) {
		driver.get(url);
		WriteToExcelSheet("Get", "", "", url, "", "");
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
	}
	
	/**
	 * Open Url and wait for Page Load till 1 minute
	 * @param message
	 * @param url
	 * @author satyam.kumar
	 */
	public void Get(String message, String url) {
		driver.get(url);
		WriteToExcelSheet("Get", message, "", url, "", "");
		driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
	}
	
	/**
	 * Click on objectData WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void Click(By ByValue) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			ele.click();
	}
	
	/**
	 * Click on objectData WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Click(String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			ele.click();
			WriteToExcelSheet("Click", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Click on objectData WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Click(String message, String objectData) {
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			ele.click();
			WriteToExcelSheet("Click", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Get Title of the current window
	 * @return WebPage Title
	 * @author satyam.kumar
	 */
	public String GetTitle() {
		String str=driver.getTitle();
		WriteToExcelSheet("GetTitle", "", "", str, "", "");
		return str;
	}
	
	/**
	 * Get Title of the current window
	 * @param message
	 * @return WebPage Title
	 * @author satyam.kumar
	 */
	public String GetTitle(String message) {
		String str=driver.getTitle();
		WriteToExcelSheet("GetTitle", message, "", str, "", "");
		return str;
	}
	
	/**
	 * Get Current window Url
	 * @return current URL
	 * @author satyam.kumar
	 */
	public String GetCurrentUrl() {
		String Url=driver.getCurrentUrl();
		WriteToExcelSheet("GetCurrentUrl", "", "", Url, "", "");
		return Url;
	}
	
	/**
	 * Get Current window URL
	 * @param message
	 * @return current URL
	 * @author satyam.kumar
	 */
	public String GetCurrentUrl(String message) {
		String Url=driver.getCurrentUrl();
		WriteToExcelSheet("GetCurrentUrl", message, "", Url, "", "");
		return Url;
	}
	
	/**
	 * Move to WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void MouseHover(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
		}
	}
	
	/**
	 * Move to WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void MouseHover(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait("",objectData);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
			WriteToExcelSheet("MouseHover", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Move to WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void MouseHover(String message, String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(message,objectData);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
			WriteToExcelSheet("MouseHover", message, objectData, "", "", "");
		}
	}
	
	/**
	 * MouseHover to WebElement and Click
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void MouseHoverAndClick(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
			Sleep(500);
			ele.click();
		}
	}
	
	/**
	 * MouseHover to WebElement and Click
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void MouseHoverAndClick(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
			Sleep(500);
			ele.click();
			WriteToExcelSheet("MouseHoverAndClick", "", objectData, "", "", "");
		}
	}
	
	/**
	 * MouseHover to WebElement and Click
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void MouseHoverAndClick(String message, String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			act.moveToElement(ele).build().perform();
			Sleep(500);
			ele.click();
			WriteToExcelSheet("MouseHoverAndClick", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Perform Drag and Drop Operation
	 * @param dragByValue
	 * @param dropByValue
	 * @author satyam.kumar
	 */
	public void DragAndDrop(By dragByValue, By dropByValue) {
		Actions act=new Actions(driver);
		WebElement dragEle=Fluentwait(dragByValue);
		WebElement dropEle=Fluentwait(dropByValue);
		if(dragEle!=null && dropEle!=null) 
			act.dragAndDrop(dragEle, dropEle);
	}
	
	/**
	 * Perform Drag and Drop Operation
	 * @param dragObjectData
	 * @param dropObjectData
	 * @author satyam.kumar
	 */
	public void DragAndDrop(String dragObjectData, String dropObjectData) {
		Actions act=new Actions(driver);
		WebElement dragEle=Fluentwait(dragObjectData);
		WebElement dropEle=Fluentwait(dropObjectData);
		if(dragEle!=null && dropEle!=null) {
			act.dragAndDrop(dragEle, dropEle);
			WriteToExcelSheet("DragAndDrop", "", "'"+dragObjectData+"' and '"+dropObjectData+"'", "", "", "");
		}
	}
	
	/**
	 * Perform Drag and Drop Operation
	 * @param message
	 * @param dragObjectData
	 * @param dropObjectData
	 * @author satyam.kumar
	 */
	public void DragAndDrop(String message, String dragObjectData, String dropObjectData) {
		Actions act=new Actions(driver);
		WebElement dragEle=Fluentwait(message, dragObjectData);
		WebElement dropEle=Fluentwait(message, dropObjectData);
		if(dragEle!=null && dropEle!=null) {
			act.dragAndDrop(dragEle, dropEle);
			WriteToExcelSheet("DragAndDrop", message, "'"+dragObjectData+"' and '"+dropObjectData+"'", "", "", "");
		}
	}
	
	/**
	 * Pause the execution
	 * Enter parameters in Seconds
	 * @param Seconds
	 * @author satyam.kumar
	 */
	public void PauseMe(int Seconds) {
		try {
			Thread.sleep(Seconds*1000);
			WriteToExcelSheet("PauseMe", "", "", Seconds+" Seconds", "", "");
		} catch (Exception e) {}
	}
		
	/**
	 * Pause the execution
	 * Enter parameters in miliSeconds
	 * @param message
	 * @param miliSeconds
	 * @author satyam.kumar
	 */
	public void PauseMe(String message, int miliSeconds) {
		try {
			Thread.sleep(miliSeconds);
			WriteToExcelSheet("PauseMe", message, "", "", "", "");
		} catch (InterruptedException e) {}
	}

	/**
	 * Pause the execution
	 * Enter parameters in miliSeconds
	 * @param miliSeconds
	 * @author satyam.kumar
	 */
	public void Sleep(int miliSeconds) {
		try {
			Thread.sleep(miliSeconds);
		} catch (Exception e) {}
	}
	
	/**
	 * Clear field for objectData
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void Clear(By ByValue) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			ele.clear();
	}
	
	/**
	 * Clear field for objectData
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Clear(String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			ele.clear();
			WriteToExcelSheet("Clear", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Clear field for objectData
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void Clear(String message, String objectData) {
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			ele.clear();
			WriteToExcelSheet("Clear", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Get tagName value for objectData 
	 * @param ByValue
	 * @return TagName
	 * @author satyam.kumar
	 */
	public String GetTagName(By ByValue) {
		String textValue=null;
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			textValue=ele.getTagName();
		System.out.println("::>>TagName value for '"+ByValue+"' is:: "+textValue+" <<::");
		return textValue;
	}
	
	/**
	 * Get tagName value for objectData 
	 * @param objectData
	 * @return TagName
	 * @author satyam.kumar
	 */
	public String GetTagName(String objectData) {
		String textValue=null;
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			textValue=ele.getTagName();
			WriteToExcelSheet("GetTagName", "", objectData, textValue, "", "");
		}
		System.out.println("::>>TagName value for '"+objectData+"' is:: "+textValue+" <<::");
		return textValue;
	}
	
	/**
	 * Get tagName value for objectData 
	 * @param message 
	 * @param objectData
	 * @return tagName
	 * @author satyam.kumar
	 */
	public String GetTagName(String message, String objectData) {
		String textValue=null;
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			textValue=ele.getTagName();
			WriteToExcelSheet("GetTagName", message, objectData, textValue, "", "");
		}
		System.out.println("::>>TagName value for '"+objectData+"' is:: "+textValue+" <<::");
		return textValue;
	}
	
	/**
	 * Pop Up Operation
	 * Move to 'windowName' PopUp
	 * Pass action as 'close' if want to close the 'windowName' PopUp
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToPopUpsAndClose(String windowName){
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
			if(driver.getTitle().toLowerCase().contains(windowName)){
				System.out.println("::>>Current window is:: "+driver.getTitle());
				driver.close();
			}
		}
		driver.switchTo().defaultContent();
		WriteToExcelSheet("SwitchToPopUpsAndClose", ""+" operation on '"+windowName+"' and Close the window", "", "", "", "");
	}
	
	/**
	 * Pop Up Operation
	 * Move to 'windowName' PopUp
	 * Pass action as 'close' if want to close the 'windowName' PopUp
	 * @param message
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToPopUpsAndClose(String message, String windowName){
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
			if(driver.getTitle().toLowerCase().contains(windowName)){
				System.out.println("::>>Current window is:: "+driver.getTitle());
				driver.close();
			}
		}
		driver.switchTo().defaultContent();
		WriteToExcelSheet("SwitchToPopUpsAndClose", message+" operation on '"+windowName+"' and Close the window", "", "", "", "");
	}
	
	/**
	 * Pop Up Operation
	 * Move to 'windowName' PopUp
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToPopUps(String windowName){
		String subWindow=null;
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
			if(driver.getTitle().toLowerCase().contains(windowName)){
				System.out.println("::>>Current window is:: "+driver.getTitle());
				subWindow=winHandle;
			}
		}
		driver.switchTo().window(subWindow);
		WriteToExcelSheet("SwitchToPopUps", " operation on '"+windowName+"'", "", "", "", "");
	}
	
	/**
	 * Pop Up Operation
	 * Move to 'windowName' PopUp
	 * @param message
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToPopUps(String message, String windowName){
		String subWindow=null;
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
			if(driver.getTitle().toLowerCase().contains(windowName)){
				System.out.println("::>>Current window is:: "+driver.getTitle());
				subWindow=winHandle;
			}
		}
		driver.switchTo().window(subWindow);
		WriteToExcelSheet("SwitchToPopUps", message+" operation on '"+windowName+"'", "", "", "", "");
	}
	
	/**
	 *Print all possible windows PopUps
	 * @param message
	 * @author satyam.kumar
	 */
	public void GetPopUpsName(String message){
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
		}
		WriteToExcelSheet("GetPopUpsName", message, "", "", "", "");
	}
	
	/**
	 * Print all possible windows PopUps
	 * @author satyam.kumar
	 */
	public void GetPopUpsName() {
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
			System.out.println(driver.getTitle());
		}
		WriteToExcelSheet("GetPopUpsName", "", "", "", "", "");
	}
	
	/**
	 * Switch To Alert
	 * @author satyam.kumar
	 */
	public void SwitchToAlert() {
		driver.switchTo().alert();
		WriteToExcelSheet("SwitchToAlert", "", "", "", "", "");
	}
	
	/**
	 * Switch To Alert
	 * @param message
	 * @author satyam.kumar
	 */
	public void SwitchToAlert(String message) {
		driver.switchTo().alert();
		WriteToExcelSheet("SwitchToAlert", message, "", "", "", "");
	}
	
	/**
	 * Switch To Window
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToWindow(String windowName) {
		driver.switchTo().window(windowName);
		WriteToExcelSheet("SwitchToWindow", ""+" '"+windowName+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Window
	 * @param message
	 * @param windowName
	 * @author satyam.kumar
	 */
	public void SwitchToWindow(String message, String windowName) {
		driver.switchTo().window(windowName);
		WriteToExcelSheet("SwitchToWindow", message+" '"+windowName+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Frame
	 * @param frameName
	 * @author satyam.kumar
	 */
	public void SwitchToFrame(String frameName) {
		driver.switchTo().frame(frameName);
		WriteToExcelSheet("SwitchToFrame", ""+" '"+frameName+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Frame
	 * @param message
	 * @param frameName
	 * @author satyam.kumar
	 */
	public void SwitchToFrame(String message, String frameName) {
		driver.switchTo().frame(frameName);
		WriteToExcelSheet("SwitchToFrame", message+" '"+frameName+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Frame
	 * @param frameNumber
	 * @author satyam.kumar
	 */
	public void SwitchToFrame(int frameNumber) {
		driver.switchTo().frame(frameNumber);
		WriteToExcelSheet("SwitchToFrame", ""+" '"+String.valueOf(frameNumber)+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Frame
	 * @param message
	 * @param frameNumber
	 * @author satyam.kumar
	 */
	public void SwitchToFrame(String message, int frameNumber) {
		driver.switchTo().frame(frameNumber);
		WriteToExcelSheet("SwitchToFrame", message+" '"+String.valueOf(frameNumber)+"'", "", "", "", "");
	}
	
	/**
	 * Switch To Frame
	 * @param frameElement
	 * @author satyam.kumar
	 */
	public void SwitchToFrame(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
	}
	
	/**
	 * Switch To Frame
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void SwitchToFrameByElement(By ByValue) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			driver.switchTo().frame(ele);
	}
	
	/**
	 * Switch To Frame
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void SwitchToFrameByElement(String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			driver.switchTo().frame(ele);
			WriteToExcelSheet("SwitchToFrameByElement", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Switch To Frame
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void SwitchToFrameByElement(String message, String objectData) {
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			driver.switchTo().frame(ele);
			WriteToExcelSheet("SwitchToFrameByElement", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Switch To Parent/Default Frame
	 * @author satyam.kumar
	 */
	public void SwitchToParentFrame() {
		driver.switchTo().parentFrame();
		WriteToExcelSheet("SwitchToParentFrame", "", "", "", "", "");
	}
	
	/**
	 * Switch To Parent/Default Frame
	 * @param message
	 * @author satyam.kumar
	 */
	public void SwitchToParentFrame(String message) {
		driver.switchTo().parentFrame();
		WriteToExcelSheet("SwitchToParentFrame", message, "", "", "", "");
	}
	
	/**
	 * Switch To Default
	 * @author satyam.kumar
	 */
	public void SwitchToDefault() {
		driver.switchTo().defaultContent();
		WriteToExcelSheet("SwitchToDefault", "", "", "", "", "");
	}
	
	/**
	 * Switch To Default
	 * @param message
	 * @author satyam.kumar
	 */
	public void SwitchToDefault(String message) {
		driver.switchTo().defaultContent();
		WriteToExcelSheet("SwitchToDefault", message, "", "", "", "");
	}
	
	/**
	 * Navigate To URL
	 * @param url
	 * @author satyam.kumar
	 */
	public void NavigateToUrl(String url){
		driver.navigate().to(url);
		WriteToExcelSheet("NavigateToUrl", "", "", url,"", "");
	}
	
	/**
	 * Navigate To URL
	 * @param message
	 * @param url
	 * @author satyam.kumar
	 */
	public void NavigateToUrl(String message, String url){
		driver.navigate().to(url);
		WriteToExcelSheet("NavigateToUrl", message, "", url,"", "");
	}

	/**
	 * Refresh the Current WebPage
	 * @author satyam.kumar
	 */
	public void Refresh() {
		driver.navigate().refresh();
		WriteToExcelSheet("Refresh", "", "", "", "", "");
	}
	
	/**
	 * Refresh the Current WebPage
	 * @param message
	 * @author satyam.kumar
	 */
	public void Refresh(String message) {
		driver.navigate().refresh();
		WriteToExcelSheet("Refresh", message, "", "", "", "");
	}
	
	/**
	 * Move Forward in WebPage
	 * if applicable
	 * @author satyam.kumar
	 */
	public void MoveForward() {
		driver.navigate().forward();
		WriteToExcelSheet("MoveForward", "", "", "", "", "");
	}
	
	/**
	 * Move Forward in WebPage
	 * if applicable
	 * @param message
	 * @author satyam.kumar
	 */
	public void MoveForward(String message) {
		driver.navigate().forward();
		WriteToExcelSheet("MoveForward", message, "", "", "", "");
	}

	/**
	 * Move Backward in WebPage
	 * if applicable
	 * @author satyam.kumar
	 */
	public void MoveBackFromCurrentWebPage() {
		driver.navigate().back();
		WriteToExcelSheet("MoveBackFromCurrentWebPage", "", "", "", "", "");
	}
	
	/**
	 * Move Backward in WebPage
	 * if applicable
	 * @param message
	 * @author satyam.kumar
	 */
	public void MoveBackFromCurrentWebPage(String message) {
		driver.navigate().back();
		WriteToExcelSheet("MoveBackFromCurrentWebPage", message, "", "", "", "");
	}
	
	/**
	 * Delete Cookies
	 * @author satyam.kumar
	 */
	public void DeleteCookies() {
		driver.manage().deleteAllCookies();
		WriteToExcelSheet("DeleteCookies", "", "", "", "", "");
	}
		
	/**
	 * Select DropDown Value
	 * By Index Value
	 * @param element
	 * @param indexVal
	 * @author satyam.kumar
	 */
	public void SelectByIndex(WebElement element, int indexVal) {
		Select sel=new Select(element);
		sel.selectByIndex(indexVal);
	}
	
	/**
	 * Select DropDown Value
	 * By Index Value
	 * @param message
	 * @param objectData
	 * @param indexVal
	 * @author satyam.kumar
	 */
	public void SelectByIndex(String message, String objectData, int indexVal) {
		WebElement element=Fluentwait(message, objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByIndex(indexVal);
			WriteToExcelSheet("SelectByIndex", message+" at indexValue "+indexVal, objectData, "", "", "");
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Index Value
	 * @param objectData
	 * @param indexVal
	 * @author satyam.kumar
	 */
	public void SelectByIndex(String objectData, int indexVal) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByIndex(indexVal);
			WriteToExcelSheet("SelectByIndex", "at indexValue "+indexVal, objectData, "", "", "");
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Index Value
	 * @param ByValue
	 * @param indexVal
	 * @author satyam.kumar
	 */
	public void SelectByIndex(By ByValue, int indexVal) {
		WebElement element=Fluentwait(ByValue);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByIndex(indexVal);
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Value
	 * @param element
	 * @param Value
	 * @author satyam.kumar
	 */
	public void SelectByValue(WebElement element, String Value) {
		Select sel=new Select(element);
		sel.selectByValue(Value);
	}
	
	/**
	 * Select DropDown Value
	 * By Value
	 * @param objectData
	 * @param Value
	 * @author satyam.kumar
	 */
	public void SelectByValue(String objectData, String value) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByValue(value);
			WriteToExcelSheet("SelectByValue", "for Value "+value, objectData, "", "", "");
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Value
	 * @param message
	 * @param objectData
	 * @param Value
	 * @author satyam.kumar
	 */
	public void SelectByValue(String message, String objectData, String value) {
		WebElement element=Fluentwait(message, objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByValue(value);
			WriteToExcelSheet("SelectByValue", message+" for Value "+value, objectData, "", "", "");
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Value
	 * @param message
	 * @param ByValue
	 * @param Value
	 * @author satyam.kumar
	 */
	public void SelectByValue(By ByValue, String value) {
		WebElement element=Fluentwait(ByValue);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByValue(value);
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Visible Text
	 * @param element
	 * @param Text
	 * @author satyam.kumar
	 */
	public void SelectByVisibleText(WebElement element, String text) {
		Select sel=new Select(element);
		sel.selectByVisibleText(text);
	}
	
	/**
	 * Select DropDown Value
	 * By Visible Text
	 * @param ByValue
	 * @param Text
	 * @author satyam.kumar
	 */
	public void SelectByVisibleText(By ByValue, String text) {
		WebElement element=Fluentwait(ByValue);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByVisibleText(text);
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Visible Text
	 * @param objectData
	 * @param Text
	 * @author satyam.kumar
	 */
	public void SelectByVisibleText(String objectData, String text) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByVisibleText(text);
			WriteToExcelSheet("SelectByVisibleText", "by visible text "+text, objectData, "", "", "");
		}
	}
	
	/**
	 * Select DropDown Value
	 * By Visible Text
	 * @param message
	 * @param objectData
	 * @param Text
	 * @author satyam.kumar
	 */
	public void SelectByVisibleText(String message, String objectData, String text) {
		WebElement element=Fluentwait(message, objectData);
		if(element!=null) {
			Select sel=new Select(element);
			sel.selectByVisibleText(text);
			WriteToExcelSheet("SelectByVisibleText", message+" by visible text "+text, objectData, "", "", "");
		}
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is Check, it will be Checked
	 * else, Check operation will be performed
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void CheckBoxCheck(By ByValue) {
		WebElement element=Fluentwait(ByValue);
		if(!element.isSelected()) 
			element.click();
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is Check, it will be Checked
	 * else, Check operation will be performed
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void CheckBoxCheck(String objectData) {
		WebElement element=Fluentwait(objectData);
		if(!element.isSelected()) {
			element.click();
			WriteToExcelSheet("CheckBoxCheck", "", objectData, "", "", "");
		}
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is Check, it will be Checked
	 * else, Check operation will be performed
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void CheckBoxCheck(String message, String objectData) {
		WebElement element=Fluentwait(message, objectData);
		if(!element.isSelected()) {
			element.click();
			WriteToExcelSheet("CheckBoxCheck", message, objectData, "", "", "");
		}
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is Check, it will be Checked
	 * else, Check operation will be performed
	 * @param element
	 * @author satyam.kumar
	 */
	public void CheckBoxCheck(WebElement element) {
		if(!element.isSelected())
			element.click();
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is UnCheck, it will be UnChecked
	 * else, UnCheck operation will be performed
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void CheckBoxUnCheck(By ByValue) {
		WebElement element=Fluentwait(ByValue);
		if(element.isSelected()) 
			element.click();
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is UnCheck, it will be UnChecked
	 * else, UnCheck operation will be performed
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void CheckBoxUnCheck(String objectData) {
		WebElement element=Fluentwait(objectData);
		if(element.isSelected()) {
			element.click();
			WriteToExcelSheet("CheckBoxUnCheck", "", objectData, "", "", "");
		}
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is UnCheck, it will be UnChecked
	 * else, UnCheck operation will be performed
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void CheckBoxUnCheck(String message, String objectData) {
		WebElement element=Fluentwait(message, objectData);
		if(element.isSelected()) {
			element.click();
			WriteToExcelSheet("CheckBoxUnCheck", message, objectData, "", "", "");
		}
	}
	
	/**
	 * CheckBox Check Operation
	 * If CheckBox is UnCheck, it will be UnChecked
	 * else, UnCheck operation will be performed
	 * @param element
	 * @author satyam.kumar
	 */
	public void CheckBoxUnCheck(WebElement element) {
		if(element.isSelected())
			element.click();
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is Selected, it will be Selected
	 * Else, Select operation will be performed
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void RadioButtonSelect(By ByValue) {
		WebElement element=Fluentwait(ByValue);
		if(!element.isSelected()) 
			element.click();
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is Selected, it will be Selected
	 * Else, Select operation will be performed
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RadioButtonSelect(String objectData) {
		WebElement element=Fluentwait(objectData);
		if(!element.isSelected()) {
			element.click();
			WriteToExcelSheet("RadioButtonSelect","" , objectData, "", "", "");
		}
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is Selected, it will be Selected
	 * Else, Select operation will be performed
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RadioButtonSelect(String message, String objectData) {
		WebElement element=Fluentwait(message, objectData);
		if(!element.isSelected()) {
			element.click();
			WriteToExcelSheet("RadioButtonSelect", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is Selected, it will be Selected
	 * Else, Select operation will be performed
	 * @param element
	 * @author satyam.kumar
	 */
	public void RadioButtonSelect(WebElement element) {
		if(!element.isSelected())
			element.click();
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is DeSelected, it will be DeSelected
	 * Else, DeSelect operation will be performed
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void RadioButtonDeSelect(By ByValue) {
		WebElement element=Fluentwait(ByValue);
		if(element.isSelected()) 
			element.click();
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is DeSelected, it will be DeSelected
	 * Else, DeSelect operation will be performed
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RadioButtonDeSelect(String objectData) {
		WebElement element=Fluentwait(objectData);
		if(element.isSelected()) {
			element.click();
			WriteToExcelSheet("RadioButtonDeSelect", "", objectData, "", "", "");
		}
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is DeSelected, it will be DeSelected
	 * Else, DeSelect operation will be performed
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RadioButtonDeSelect(String message, String objectData) {
		WebElement element=Fluentwait(message, objectData);
		if(element.isSelected()) {
			element.click();
			WriteToExcelSheet("RadioButtonDeSelect", message, objectData, "", "", "");
		}
	}
	
	/**
	 * Radio Button Select Operation
	 * If Radio Button is DeSelected, it will be DeSelected
	 * Else, DeSelect operation will be performed
	 * @param element
	 * @author satyam.kumar
	 */
	public void RadioButtonDeSelect(WebElement element) {
		if(element.isSelected())
			element.click();
	}
	
	/**
	 * Verify Alert Present 'or' Not
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isAlertPresent() {
		boolean alrt=false;
		try {
			driver.switchTo().alert();
			alrt=true;
			driver.switchTo().defaultContent();
		}catch(Exception e) {}
		WriteToExcelSheet("isAlertPresent", "", "", "", "", "");
		return alrt;
	}
	
	/**
	 * Verify Alert Present 'or' Not
	 * @param message
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isAlertPresent(String message) {
		boolean alrt=false;
		try {
			driver.switchTo().alert();
			alrt=true;
			driver.switchTo().defaultContent();
		}catch(Exception e) {}
		WriteToExcelSheet("isAlertPresent", message, "", "", "", "");
		return alrt;
	}
	
	/**
	 * Accept Alert Operation
	 * @author satyam.kumar
	 */
	public void AlertAccept() {
		if(isAlertPresent()==true) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			WriteToExcelSheet("AlertAccept", "", "", "", "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
	}
	
	/**
	 * Accept Alert Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void AlertAccept(String message) {
		if(isAlertPresent()==true) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			WriteToExcelSheet("AlertAccept", message, "", "", "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
	}
	
	/**
	 * Dismiss Alert Operation
	 * @author satyam.kumar
	 */
	public void AlertDismiss() {
		if(isAlertPresent()==true) {
			driver.switchTo().alert().dismiss();
			driver.switchTo().defaultContent();
			WriteToExcelSheet("AlertDismiss", "", "", "", "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
	}
	
	/**
	 * Dismiss Alert Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void AlertDismiss(String message) {
		if(isAlertPresent()==true) {
			driver.switchTo().alert().dismiss();
			driver.switchTo().defaultContent();
			WriteToExcelSheet("AlertDismiss", message, "", "", "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
	}
	
	/**
	 * Alert GetText Operation
	 * @return Alert Text
	 * @author satyam.kumar
	 */
	public String AlertGetText() {
		String Text=null;
		if(isAlertPresent()==true) {
			Text= driver.switchTo().alert().getText();
			System.out.println("::>>Alert Text is:: "+Text+" <<::");
			WriteToExcelSheet("AlertGetText", "", "", Text, "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
		return Text;
	}
	
	/**
	 * Alert GetText Operation
	 * @param message
	 * @return Alert Text
	 * @author satyam.kumar
	 */
	public String AlertGetText(String message) {
		String Text=null;
		if(isAlertPresent()==true) {
			Text= driver.switchTo().alert().getText();
			System.out.println("::>>Alert Text is:: "+Text+" <<::");
			WriteToExcelSheet("AlertGetText", message, "", Text, "", "");
		}
		else
			System.out.println("::>>Alert Not Found<<::");
		return Text;
	}
	
	/**
	 * File Upload Using SendKeys	
	 * Path is the path of file
	 * @param ByValue
	 * @param path
	 * @author satyam.kumar
	 */
	public void FileUpload(By ByValue, String path) {
		WebElement element=Fluentwait(ByValue);
		if(element!=null) {
			element.sendKeys(path);
			WriteToExcelSheet("FileUpload", "", "", path, "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * File Upload Using SendKeys	
	 * Path is the path of file
	 * @param objectData
	 * @param path
	 * @author satyam.kumar
	 */
	public void FileUpload(String objectData, String path) {
		WebElement element=Fluentwait(objectData);
		if(element!=null) {
			element.sendKeys(path);
			WriteToExcelSheet("FileUpload", "", "", path, "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * File Upload Using SendKeys	
	 * Path is the path of file
	 * @param message
	 * @param objectData
	 * @param path
	 * @author satyam.kumar
	 */
	public void FileUpload(String message, String objectData, String path) {
		WebElement element=Fluentwait(message, objectData);
		if(element!=null) {
			element.sendKeys(path);
			WriteToExcelSheet("FileUpload", message, "", path, "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * File upload using SendKeys
	 * Path is the path of file
	 * @param element
	 * @param path
	 * @author satyam.kumar
	 */
	public void FileUpload(WebElement element, String path) {
		element.sendKeys(path);
	}
	
	/**
	 * RightClick Operation
	 * @author satyam.kumar
	 */
	public void RightClick() {
		Actions act=new Actions(driver);
		act.contextClick().build().perform();
		WriteToExcelSheet("MouseRightClick", "", "", "", "", "");
	}
	
	/**
	 * RightClick Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void RightClick(String message) {
		Actions act=new Actions(driver);
		act.contextClick().build().perform();
		WriteToExcelSheet("MouseRightClick", message, "", "", "", "");
	}
	
	/**
	 * RightClick Operation
	 * On target WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void RightClickOnElement(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			act.contextClick(ele).build().perform();
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * RightClick Operation
	 * On target WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RightClickOnElement(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			act.contextClick(ele).build().perform();
			WriteToExcelSheet("MouseRightClickOnElement", "", objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * RightClick Operation
	 * On target WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void RightClickOnElement(String message, String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(message,objectData);
		if(ele!=null) {
			act.contextClick(ele).build().perform();
			WriteToExcelSheet("MouseRightClickOnElement", message, objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Double Click Operation
	 * @author satyam.kumar
	 */
	public void DoubleClick() {
		Actions act=new Actions(driver);
		act.doubleClick().build().perform();
		WriteToExcelSheet("MouseDoubleClick", "", "", "", "", "");
	}
	
	/**
	 * Double Click Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void DoubleClick(String message) {
		Actions act=new Actions(driver);
		act.doubleClick().build().perform();
		WriteToExcelSheet("MouseDoubleClick", message, "", "", "", "");
	}
	
	/**
	 * Double Click Operation
	 * On target WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void DoubleClickOnElement(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			act.doubleClick(ele).build().perform();
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Double Click Operation
	 * On target WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void DoubleClickOnElement(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			act.doubleClick(ele).build().perform();
			WriteToExcelSheet("MouseDoubleClickOnElement", "", objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Double Click Operation
	 * On target WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void DoubleClickOnElement(String message, String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			act.doubleClick(ele).build().perform();
			WriteToExcelSheet("MouseDoubleClickOnElement", message, objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Enter
	 * @author satyam.kumar
	 */
	public void KeyBoardEnter() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ENTER).build().perform();
		WriteToExcelSheet("KeyBoardEnter", "", "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Enter
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardEnter(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ENTER).build().perform();
		WriteToExcelSheet("KeyBoardEnter", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Operation
	 * Press Enter on a WebElement
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void PressEnterOnElement(By ByValue) {
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			ele.sendKeys(Keys.ENTER);
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Keyboard Operation
	 * Press Enter on a WebElement
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void PressEnterOnElement(String objectData) {
		WebElement ele=Fluentwait(objectData);
		if(ele!=null) {
			ele.sendKeys(Keys.ENTER);
			WriteToExcelSheet("PressEnterOnElement", "", objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Keyboard Operation
	 * Press Enter on a WebElement
	 * @param message
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void PressEnterOnElement(String message, String objectData) {
		WebElement ele=Fluentwait(message, objectData);
		if(ele!=null) {
			ele.sendKeys(Keys.ENTER);
			WriteToExcelSheet("PressEnterOnElement", message, objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Control button and Hold
	 * @author satyam.kumar
	 */
	public void KeyBoardControlHold() {
		Actions act=new Actions(driver);
		act.keyDown(Keys.CONTROL).build().perform();
		WriteToExcelSheet("KeyBoardControlHold", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press Control button and Hold
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardControlHold(String message) {
		Actions act=new Actions(driver);
		act.keyDown(Keys.CONTROL).build().perform();
		WriteToExcelSheet("KeyBoardControlHold", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Control button and Release
	 * @author satyam.kumar
	 */
	public void KeyBoardControlRelease() {
		Actions act=new Actions(driver);
		act.keyUp(Keys.CONTROL).build().perform();
		WriteToExcelSheet("KeyBoardControlRelease", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Control Button Release
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardControlRelease(String message) {
		Actions act=new Actions(driver);
		act.keyUp(Keys.CONTROL).build().perform();
		WriteToExcelSheet("KeyBoardControlRelease", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Alert button and Hold
	 * @author satyam.kumar
	 */
	public void KeyBoardAlertHold() {
		Actions act=new Actions(driver);
		act.keyDown(Keys.ALT).build().perform();
		WriteToExcelSheet("KeyBoardAlertHold", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press Alert Button and Hold
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardAlertHold(String message) {
		Actions act=new Actions(driver);
		act.keyDown(Keys.ALT).build().perform();
		WriteToExcelSheet("KeyBoardAlertHold", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Alert button and Release
	 * @author satyam.kumar
	 */
	public void KeyBoardAlertRelease() {
		Actions act=new Actions(driver);
		act.keyUp(Keys.ALT).build().perform();
		WriteToExcelSheet("KeyBoardAlertRelease", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Alert Button and Release
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardAlertRelease(String message) {
		Actions act=new Actions(driver);
		act.keyUp(Keys.ALT).build().perform();
		WriteToExcelSheet("KeyBoardAlertRelease", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Shift button and Hold
	 * @author satyam.kumar
	 */
	public void KeyBoardShiftHold() {
		Actions act=new Actions(driver);
		act.keyDown(Keys.SHIFT).build().perform();
		WriteToExcelSheet("KeyBoardShiftHold", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press Shift button and Hold
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardShiftHold(String message) {
		Actions act=new Actions(driver);
		act.keyDown(Keys.SHIFT).build().perform();
		WriteToExcelSheet("KeyBoardShiftHold", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard Operation
	 * Press Shift button and Release
	 * @author satyam.kumar
	 */
	public void KeyBoardShiftRelease() {
		Actions act=new Actions(driver);
		act.keyUp(Keys.SHIFT).build().perform();
		WriteToExcelSheet("KeyBoardShiftRelease", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Shift Release
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardShiftRelease(String message) {
		Actions act=new Actions(driver);
		act.keyUp(Keys.SHIFT).build().perform();
		WriteToExcelSheet("KeyBoardShiftRelease", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Up Arrow
	 * @author satyam.kumar
	 */
	public void KeyBoardUpArrow() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_UP).build().perform();
		WriteToExcelSheet("KeyBoardUpArrow", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Up Arrow
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardUpArrow(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_UP).build().perform();
		WriteToExcelSheet("KeyBoardUpArrow", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Down Arrow
	 * @author satyam.kumar
	 */
	public void KeyBoardDownArrow() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		WriteToExcelSheet("KeyBoardDownArrow", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Down Arrow
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardDownArrow(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		WriteToExcelSheet("KeyBoardDownArrow", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Left Arrow
	 * @author satyam.kumar
	 */
	public void KeyBoardLeftArrow() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_LEFT).build().perform();
		WriteToExcelSheet("KeyBoardLeftArrow", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Left Arrow
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardLeftArrow(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_LEFT).build().perform();
		WriteToExcelSheet("KeyBoardLeftArrow", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Right Arrow
	 * @author satyam.kumar
	 */
	public void KeyBoardRightArrow() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_RIGHT).build().perform();
		WriteToExcelSheet("KeyBoardRightArrow", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Right Arrow
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardRightArrow(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ARROW_RIGHT).build().perform();
		WriteToExcelSheet("KeyBoardRightArrow", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Backspace
	 * @author satyam.kumar
	 */
	public void KeyBoardBackspace() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.BACK_SPACE).build().perform();
		WriteToExcelSheet("KeyBoardBackspace", "", "", "", "", "");
	}
	
	/**
	 * Keyboard BackSpace
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardBackspace(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.BACK_SPACE).build().perform();
		WriteToExcelSheet("KeyBoardBackspace", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Escape
	 * @author satyam.kumar
	 */
	public void KeyBoardEscape() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ESCAPE).build().perform();
		WriteToExcelSheet("KeyBoardEscape", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Escape Button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardEscape(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ESCAPE).build().perform();
		WriteToExcelSheet("KeyBoardEscape", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Tab
	 * @author satyam.kumar
	 */
	public void KeyBoardTab() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		WriteToExcelSheet("KeyBoardTab", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Tab Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardTab(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		WriteToExcelSheet("KeyBoardTab", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Space
	 * @author satyam.kumar
	 */
	public void KeyBoardSpace() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SPACE).build().perform();
		WriteToExcelSheet("KeyBoardSpace", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Space Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardSpace(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SPACE).build().perform();
		WriteToExcelSheet("KeyBoardSpace", message, "", "", "", "");
	}
	
	/**
	 * KeyBoard operation
	 * Press Delete
	 * @author satyam.kumar
	 */
	public void KeyBoardDelete() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DELETE).build().perform();
		WriteToExcelSheet("KeyBoardDelete", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Delete Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardDelete(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DELETE).build().perform();
		WriteToExcelSheet("KeyBoardDelete", message, "", "", "", "");
	}
		
	/**
	 * KeyBoard operation
	 * Press Decimal
	 * @author satyam.kumar
	 */
	public void KeyBoardDecimal() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DECIMAL).build().perform();
		WriteToExcelSheet("KeyBoardDecimal", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press Decimal button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyBoardDecimal(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DECIMAL).build().perform();
		WriteToExcelSheet("KeyBoardDecimal", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Page Up Operation
	 * @author satyam.kumar
	 */
	public void KeyboardPageUp() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAGE_UP).build().perform();
		WriteToExcelSheet("KeyboardPageUp", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Page Up Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardPageUp(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAGE_UP).build().perform();
		WriteToExcelSheet("KeyboardPageUp", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Page Down Operation
	 * @author satyam.kumar
	 */
	public void KeyboardPageDown() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAGE_DOWN).build().perform();
		WriteToExcelSheet("KeyboardPageUp", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Page Down Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardPageDown(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAGE_DOWN).build().perform();
		WriteToExcelSheet("KeyboardPageDown", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Pause Operation
	 * @author satyam.kumar
	 */
	public void KeyboardPause() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAUSE).build().perform();
		WriteToExcelSheet("KeyboardPageDown", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Pause Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardPause(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.PAUSE).build().perform();
		WriteToExcelSheet("KeyboardPageDown", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Clear Operation
	 * @author satyam.kumar
	 */
	public void KeyboardClear() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.CLEAR).build().perform();
		WriteToExcelSheet("KeyboardClear", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Clear Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardClear(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.CLEAR).build().perform();
		WriteToExcelSheet("KeyboardClear", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Cancel Operation
	 * @author satyam.kumar
	 */
	public void KeyboardCancel() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.CANCEL).build().perform();
		WriteToExcelSheet("KeyboardCancel", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Cancel Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardCancel(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.CANCEL).build().perform();
		WriteToExcelSheet("KeyboardCancel", message, "", "", "", "");
	}
	
	/**
	 * Keyboard End Operation
	 * @author satyam.kumar
	 */
	public void KeyboardEnd() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.END).build().perform();
		WriteToExcelSheet("KeyboardEnd", "", "", "", "", "");
	}
	
	/**
	 * Keyboard End Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardEnd(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.END).build().perform();
		WriteToExcelSheet("KeyboardEnd", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Equals Operation
	 * @author satyam.kumar
	 */
	public void KeyboardEquals() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.EQUALS).build().perform();
		WriteToExcelSheet("KeyboardEquals", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Equals Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardEquals(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.EQUALS).build().perform();
		WriteToExcelSheet("KeyboardEquals", message, "", "", "", "");
	}
	
	/**
	 * Keyboard UP Operation
	 * @author satyam.kumar
	 */
	public void KeyboardUp() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.UP).build().perform();
		WriteToExcelSheet("KeyboardUp", "", "", "", "", "");
	}
	
	/**
	 * Keyboard UP Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardUp(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.UP).build().perform();
		WriteToExcelSheet("KeyboardUp", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Down Operation
	 * @author satyam.kumar
	 */
	public void KeyboardDown() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DOWN).build().perform();
		WriteToExcelSheet("KeyboardDown", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Down Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardDown(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DOWN).build().perform();
		WriteToExcelSheet("KeyboardDown", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Command Operation
	 * @author satyam.kumar
	 */
	public void KeyboardCommand() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.COMMAND).build().perform();
		WriteToExcelSheet("KeyboardCommand", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Command Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardCommand(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.COMMAND).build().perform();
		WriteToExcelSheet("KeyboardCommand", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Home Operation
	 * @author satyam.kumar
	 */
	public void KeyboardHome() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.HOME).build().perform();
		WriteToExcelSheet("KeyboardHome", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Home Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardHome(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.HOME).build().perform();
		WriteToExcelSheet("KeyboardHome", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Insert Operation
	 * @author satyam.kumar
	 */
	public void KeyboardInsert() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.INSERT).build().perform();
		WriteToExcelSheet("KeyboardInsert", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Insert Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardInsert(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.INSERT).build().perform();
		WriteToExcelSheet("KeyboardInsert", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Return Operation
	 * @author satyam.kumar
	 */
	public void KeyboardReturn() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.RETURN).build().perform();
		WriteToExcelSheet("KeyboardReturn", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Return Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardReturn(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.RETURN).build().perform();
		WriteToExcelSheet("KeyboardReturn", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Semicolon Operation
	 * @author satyam.kumar
	 */
	public void KeyboardSemicolon() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SEMICOLON).build().perform();
		WriteToExcelSheet("KeyboardSemicolon", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Semicolon Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardSemicolon(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SEMICOLON).build().perform();
		WriteToExcelSheet("KeyboardSemicolon", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Separator Operation
	 * @author satyam.kumar
	 */
	public void KeyboardSeparator() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SEPARATOR).build().perform();
		WriteToExcelSheet("KeyboardSeparator", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Separator Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardSeparator(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SEPARATOR).build().perform();
		WriteToExcelSheet("KeyboardSeparator", message, "", "", "", "");
	}
	
	/**
	 * Keyboard ADD Operation
	 * @author satyam.kumar
	 */
	public void KeyboardAdd() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ADD).build().perform();
		WriteToExcelSheet("KeyboardAdd", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Add Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardAdd(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.ADD).build().perform();
		WriteToExcelSheet("KeyboardAdd", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Subtract Operation
	 * @author satyam.kumar
	 */
	public void KeyboardSubtract() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SUBTRACT).build().perform();
		WriteToExcelSheet("KeyboardSubtract", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Subtract Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardSubtract(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.SUBTRACT).build().perform();
		WriteToExcelSheet("KeyboardSubtract", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Multiply Operation
	 * @author satyam.kumar
	 */
	public void KeyboardMultiply() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.MULTIPLY).build().perform();
		WriteToExcelSheet("KeyboardMultiply", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Multiply Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardMultiply(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.MULTIPLY).build().perform();
		WriteToExcelSheet("KeyboardMultiply", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Divide Operation
	 * @author satyam.kumar
	 */
	public void KeyboardDivide() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DIVIDE).build().perform();
		WriteToExcelSheet("KeyboardDivide", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Divide Operation
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardDivide(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.DIVIDE).build().perform();
		WriteToExcelSheet("KeyboardDivide", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 0 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad0() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD0).build().perform();
		WriteToExcelSheet("KeyboardNumPad0", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 0 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad0(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD0).build().perform();
		WriteToExcelSheet("KeyboardNumPad0", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 1 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad1() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD1).build().perform();
		WriteToExcelSheet("KeyboardNumPad1", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 1 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad1(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD1).build().perform();
		WriteToExcelSheet("KeyboardNumPad1", message, "", "", "", "");
	}

	/**
	 * Keyboard Press NumberPad 2 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad2() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD2).build().perform();
		WriteToExcelSheet("KeyboardNumPad2", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 2 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad2(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD2).build().perform();
		WriteToExcelSheet("KeyboardNumPad2", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 3 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad3() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD3).build().perform();
		WriteToExcelSheet("KeyboardNumPad3", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 3 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad3(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD3).build().perform();
		WriteToExcelSheet("KeyboardNumPad3", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 4 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad4() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD4).build().perform();
		WriteToExcelSheet("KeyboardNumPad4", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 4 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad4(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD4).build().perform();
		WriteToExcelSheet("KeyboardNumPad4", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 5 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad5() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD5).build().perform();
		WriteToExcelSheet("KeyboardNumPad5", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 5 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad5(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD5).build().perform();
		WriteToExcelSheet("KeyboardNumPad5", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 6 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad6() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD6).build().perform();
		WriteToExcelSheet("KeyboardNumPad6", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 6 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad6(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD6).build().perform();
		WriteToExcelSheet("KeyboardNumPad6", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 7 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad7() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD7).build().perform();
		WriteToExcelSheet("KeyboardNumPad7", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 7 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad7(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD7).build().perform();
		WriteToExcelSheet("KeyboardNumPad7", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 8 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad8() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD8).build().perform();
		WriteToExcelSheet("KeyboardNumPad8", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 8 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad8(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD8).build().perform();
		WriteToExcelSheet("KeyboardNumPad8", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 9 button
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad9() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD9).build().perform();
		WriteToExcelSheet("KeyboardNumPad9", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press NumberPad 9 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardNumPad9(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.NUMPAD9).build().perform();
		WriteToExcelSheet("KeyboardNumPad9", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F1 button
	 * @author satyam.kumar
	 */
	public void KeyboardF1() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F1).build().perform();
		WriteToExcelSheet("KeyboardF1", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F1 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF1(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F1).build().perform();
		WriteToExcelSheet("KeyboardF1", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F2 button
	 * @author satyam.kumar
	 */
	public void KeyboardF2() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F2).build().perform();
		WriteToExcelSheet("KeyboardF2", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F2 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF2(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F2).build().perform();
		WriteToExcelSheet("KeyboardF2", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F3 button
	 * @author satyam.kumar
	 */
	public void KeyboardF3() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F3).build().perform();
		WriteToExcelSheet("KeyboardF3", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F3 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF3(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F3).build().perform();
		WriteToExcelSheet("KeyboardF3", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F4 button
	 * @author satyam.kumar
	 */
	public void KeyboardF4() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F4).build().perform();
		WriteToExcelSheet("KeyboardF4", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F4 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF4(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F4).build().perform();
		WriteToExcelSheet("KeyboardF4", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F5 button
	 * @author satyam.kumar
	 */
	public void KeyboardF5() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F5).build().perform();
		WriteToExcelSheet("KeyboardF5", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F5 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF5(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F5).build().perform();
		WriteToExcelSheet("KeyboardF5", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F6 button
	 * @author satyam.kumar
	 */
	public void KeyboardF6() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F6).build().perform();
		WriteToExcelSheet("KeyboardF6", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F6 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF6(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F6).build().perform();
		WriteToExcelSheet("KeyboardF6", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F7 button
	 * @author satyam.kumar
	 */
	public void KeyboardF7() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F7).build().perform();
		WriteToExcelSheet("KeyboardF7", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F7 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF7(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F7).build().perform();
		WriteToExcelSheet("KeyboardF7", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F8 button
	 * @author satyam.kumar
	 */
	public void KeyboardF8() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F8).build().perform();
		WriteToExcelSheet("KeyboardF8", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F8 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF8(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F8).build().perform();
		WriteToExcelSheet("KeyboardF8", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F9 button
	 * @author satyam.kumar
	 */
	public void KeyboardF9() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F9).build().perform();
		WriteToExcelSheet("KeyboardF9", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F9 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF9(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F9).build().perform();
		WriteToExcelSheet("KeyboardF9", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F10 button
	 * @author satyam.kumar
	 */
	public void KeyboardF10() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F10).build().perform();
		WriteToExcelSheet("KeyboardF10", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F10 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF10(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F10).build().perform();
		WriteToExcelSheet("KeyboardF10", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F11 button
	 * @author satyam.kumar
	 */
	public void KeyboardF11() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F11).build().perform();
		WriteToExcelSheet("KeyboardF11", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F11 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF11(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F11).build().perform();
		WriteToExcelSheet("KeyboardF11", message, "", "", "", "");
	}
	
	/**
	 * Keyboard Press F12 button
	 * @author satyam.kumar
	 */
	public void KeyboardF12() {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F12).build().perform();
		WriteToExcelSheet("KeyboardF12", "", "", "", "", "");
	}
	
	/**
	 * Keyboard Press F12 button
	 * @param message
	 * @author satyam.kumar
	 */
	public void KeyboardF12(String message) {
		Actions act=new Actions(driver);
		act.sendKeys(Keys.F12).build().perform();
		WriteToExcelSheet("KeyboardF12", message, "", "", "", "");
	}
	
	/**
	 * Click on ByValue and Open in New Tab
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void OpenLinkInNewTab(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			act.keyDown(Keys.CONTROL).click(ele).keyUp(Keys.CONTROL).build().perform();
		else
			System.out.println("::>> Element value is null <<::");
	}
		
	/**
	 * Click on WebElement and Open in New Tab
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void OpenLinkInNewTab(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait("",objectData);
		if(ele!=null) {
			act.keyDown(Keys.CONTROL).click(ele).keyUp(Keys.CONTROL).build().perform();
			WriteToExcelSheet("OpenLinkInNewTab", "", objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Click on WebElement and Open in New Window
	 * @param ByValue
	 * @author satyam.kumar
	 */
	public void OpenLinkInNewWindow(By ByValue) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait(ByValue);
		if(ele!=null) 
			act.keyDown(Keys.SHIFT).click(ele).keyUp(Keys.SHIFT).build().perform();
		else
			System.out.println("::>> Element value is null <<::");
	}
	
	/**
	 * Click on WebElement and Open in New Window
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void OpenLinkInNewWindow(String objectData) {
		Actions act=new Actions(driver);
		WebElement ele=Fluentwait("",objectData);
		if(ele!=null) {
			act.keyDown(Keys.SHIFT).click(ele).keyUp(Keys.SHIFT).build().perform();
			WriteToExcelSheet("OpenLinkInNewWindow", "", objectData, "", "", "");
		}
		else
			System.out.println("::>> Element value is null <<::");
	}

	/**
	 * To verify CheckBox is Checked 'or' not	
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isCheckBoxChecked(By ByValue) {
		return isSelected(ByValue);
	}
	
	/**
	 * To verify CheckBox is Checked 'or' not	
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isCheckBoxChecked(String objectData) {
		return isSelected(objectData);
	}
	
	/**
	 * To verify CheckBox is Checked 'or' not	
	 * @param element
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isCheckBoxChecked(WebElement element) {
		boolean Status=false;
		if(element.isSelected())
			Status=true;
		return Status;
	}
	
	/**
	 * To verify Radio Button is selected 'or' not	
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isRadioButtonSelected(By ByValue) {
		return isSelected(ByValue);
	}
	
	/**
	 * To verify Radio Button is selected 'or' not	
	 * @param objectData
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isRadioButtonSelected(String objectData) {
		return isSelected(objectData);
	}
	
	/**
	 * To verify Radio Button is selected 'or' not	
	 * @param element
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isRadioButtonSelected(WebElement element) {
		boolean Status=false;
		if(element.isSelected())
			Status=true;
		return Status;
	}
	
	/**
	 * Get Frame Count on any WebPage
	 * @return frameCount
	 * @author satyam.kumar
	 */
	public int GetFrameCount() {
		int frameCount=0;
		boolean Status=false;
		do {
			try {
				driver.switchTo().frame(frameCount);
				frameCount++;
			}catch(Exception e) {Status=true;}
		}while(Status==true);
		driver.switchTo().defaultContent();
		frameCount++;
		System.out.println("::>> Total Frame Count is:: "+frameCount+" <<::");
		return frameCount;
	}
	
	/**
	 * Maximize the window
	 * @author satyam.kumar
	 */
	public void WindowMaximize() {
		driver.manage().window().maximize();
	}
	
	/**
	 * Make the Window FullScreen
	 * @author satyam.kumar
	 */
	public void WindowFullscreen() {
		driver.manage().window().fullscreen();
	}
	
	/**
	 * Scroll Operation in a WebPage to the Element
	 * @param objectData
	 * @author satyam.kumar
	 */
	public void PageScrollToElement(String objectData) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);",Fluentwait(objectData));
	}
	
	/**********************************************Validation Operation*************************************************/
	/********************************************************************************************************************/
	
	/**
	 * Comparison isEquals
	 * @param actual
	 * @param expected
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isEquals(String actual, String expected) {
		boolean stat=false;
		if(actual==null && expected==null)
			stat=true;
		else if(actual!=null && expected!=null && actual.equals(expected))
			stat=true;
		else if(actual==expected)
			stat=true;
		return stat;
	}
	
	/**
	 * To Verify that Actual Contains Expected
	 * @param actual
	 * @param expected
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean isContains(String actual, String expected) {
		boolean stat=false;
		if(actual!=null && expected!=null && actual.contains(expected))
			stat=true;
		return stat;
	}
	
	/**
	 * Verify actual contains expected
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void VerifyContains(String message, String actual, String expected){
		if(isContains(actual, expected)==true) {
			setTestResult("VerifyNotContains "+message, actual, expected, "pass");
			WriteToExcelSheet("VerifyNotContains", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("VerifyNotContains "+message, actual, expected, "fail");
			WriteToExcelSheet("VerifyNotContains", message, "", actual, expected, "Fail");
		}
	}
	
	/**
	 * Verify actual don't contains expected
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void VerifyNotContains(String message, String actual, String expected) {
		if(isContains(actual, expected)==false) {
			setTestResult("VerifyNotContains "+message, actual, expected, "pass");
			WriteToExcelSheet("VerifyNotContains", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("VerifyNotContains "+message, actual, expected, "pass");
			WriteToExcelSheet("VerifyNotContains", message, "", actual, expected, "Fail");
		}
	}
		
	/**
	 * Verify actual and expected are equals
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void VerifyEquals(String message, String actual, String expected) {
		if(isEquals(actual, expected)==true){
			setTestResult("VerifyEquals "+message, actual, expected, "pass");
			WriteToExcelSheet("VerifyEquals", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("VerifyEquals "+message, actual, expected, "fail");
			WriteToExcelSheet("VerifyEquals", message, "", actual, expected, "Fail");
		}
	}
	
	/**
	 * Verify actual and expected are not equals
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void VerifyNotEquals(String message, String actual, String expected) {
		if(isEquals(actual, expected)==false) {
			setTestResult("VerifyNotEquals "+message, actual, expected, "pass");
			WriteToExcelSheet("VerifyNotEquals", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("VerifyNotEquals "+message, actual, expected, "fail");
			WriteToExcelSheet("VerifyNotEquals", message, "", actual, expected, "Fail");
		}
	}
		
	/**
	 * Verify Contains is null
	 * @param message
	 * @param Contens
	 * @author satyam.kumar
	 */
	public void VerifyNull(String message, String Contens) {
		if(Contens==null){
			setTestResult("VerifyNull "+message, null, null, "pass");
			WriteToExcelSheet("VerifyNull", message, Contens, "", "", "Pass");
			System.out.println(">'"+Contens+"' is null<");
		}
		else {
			setTestResult("VerifyNull "+message, null, null, "fail");
			WriteToExcelSheet("VerifyNull", message, Contens, "", "", "Fail");
			System.out.println(">'"+Contens+"' is null<");
		}
	}

	/**
	 * Verify Content is not null
	 * @param message
	 * @param Contens
	 * @author satyam.kumar
	 */
	public void VerifyNotNull(String message, String contents) {
		if(contents!=null){
			setTestResult("VerifyNotNull "+message, null, null, "pass");
			WriteToExcelSheet("VerifyNotNull", message, "", "", "", "Pass");
			System.out.println(">'"+contents+"' is not null<");
		}
		else {
			setTestResult("VerifyNotNull "+message, null, null, "fail");
			WriteToExcelSheet("VerifyNotNull", message, "", "", "", "Fail");
			System.out.println(">'"+contents+"' is not null<");
		}
	}
	
	/**
	 * Assert actual contains expected
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void AssertContains(String message, String actual, String expected) {
		if(isContains(actual, expected)==true) {
			setTestResult("AssertContains "+message, actual, expected, "pass");
			WriteToExcelSheet("AssertContains", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("AssertContains "+message, actual, expected, "fail");
			WriteToExcelSheet("AssertContains", message, "", actual, expected, "Fail");
		}
	}
	
	/**
	 * Assert actual don't contains expected
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void AssertNotContains(String message, String actual, String expected) {
		if(isContains(actual, expected)==false) {
			setTestResult("AssertNotContains "+message, actual, expected, "pass");
			WriteToExcelSheet("AssertNotContains", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("AssertNotContains "+message, actual, expected, "fail");
			WriteToExcelSheet("AssertNotContains", message, "", actual, expected, "Fail");
		}
	}
		
	/**
	 * Assert actual and expected are equals
	 * @param message
	 * @param actual
	 * @param expected
	 * @throws IOException 
	 * @author satyam.kumar
	 */
	public void AssertEquals(String message, String actual, String expected) {
		if(isEquals(actual, expected)==true) {
			setTestResult("AssertEquals "+message, actual, expected, "pass");
			WriteToExcelSheet("AssertEquals", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("AssertEquals "+message, actual, expected, "fail");
			WriteToExcelSheet("AssertEquals", message, "", actual, expected, "Fail");
			Terminate();
		}
	}
	
	/**
	 * Assert actual and expected are not equals
	 * @param message
	 * @param actual
	 * @param expected
	 * @author satyam.kumar
	 */
	public void AssertNotEquals(String message, String actual, String expected) {
		if(isEquals(message, expected)==false) {
			setTestResult("AssertNotEquals "+message, actual, expected, "pass");
			WriteToExcelSheet("AssertNotEquals", message, "", actual, expected, "Pass");
		}
		else {
			setTestResult("AssertNotEquals "+message, actual, expected, "fail");
			WriteToExcelSheet("AssertNotEquals", message, "", actual, expected, "Fail");
			Terminate();
		}
	}
	/**********************************************Extent Report*****************************************************/
	/*****************************************************************************************************************/
	
	/**
	 * Extent Report Name 
	 * @param reportName
	 * @author satyam.kumar
	 */
	public void extentReportName(String reportName) {
		ReportName=reportName;
	}
	
	/**
	 * To Create the logger for Extent Report
	 * It will create logger of Class Name
	 * It will name first Column as "Method/Steps/Scenario"
	 * Second Column as "Message"
	 * Third Column as "ObjectData"
	 * Fourth Column as "Input/Output/Actual"
	 * Fifth Column as "Expected"
	 * Sixth COlumn as "Result"
	 * @param testName
	 * @author satyam.kumar
	 */
	private void CreateTest(String releaseName, String testName) {
		
		CreateSheet(testName);
		
		if(report==null) {
			if(reportDir==null) {
				reportDir=new File(System.getProperty("user.dir")+"\\Reports");
				if(!reportDir.exists())
					reportDir.mkdirs();
			}
			String path=reportDir+"\\"+ReportName+".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			htmlReporter.config().setReportName(releaseName);
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setTheme(Theme.DARK);
		}
		logger=report.createTest(testName);
		report.flush();
	}
		
	/**
	 * Capture Screenshot
	 * @param message
	 * @return screenshot Destination
	 * @author satyam.kumar
	 */
	private static String captureScreenshot(String message) {
		if(screenDir==null) {
			screenDir=new File(System.getProperty("user.dir")+"\\Reports");
			if(!screenDir.exists())
				screenDir.mkdirs();
		}
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String dest=screenDir+"\\screenshot.png";
			FileUtils.copyFile(scrFile, new File(dest));
			return dest;
		}catch(Exception e) {System.out.println("\n::>>Capture Screenshots Error is::\n"+e);return e.getMessage();}
	}	

	/**
	 * Read the screenshot in byte format
	 * @param InputStream
	 * @return ByteArray
	 * @throws IOException
	 * @author satyam.kumar
	 */
	private static byte[] readFully(){
		InputStream ins=null;
		int bytesRead=0;
		try {
			ins=new FileInputStream(captureScreenshot("Screenshot"));
		}catch(Exception e) {}
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			while ((bytesRead = ins.read(buffer)) != -1) 
				baos.write(buffer, 0, bytesRead);
			
		}catch(Exception e) {System.out.println(e.getMessage());}
		return baos.toByteArray();
	}
	
	/**
	 * Perform test Result operation in Extent report
	 * @param message
	 * @param actual
	 * @param expected
	 * @param status
	 * @throws IOException
	 * @author satyam.kumar
	 */
	private void setTestResult(String message, String actual, String expected, String status) {
		String fileInBase64=null;
		status=status.toLowerCase();
		
		fileInBase64 = DatatypeConverter.printBase64Binary(readFully());
				
		try {
			if(status=="fail"){
				if((actual==null && expected==null) || (actual=="" && expected==""))
					logger.log(Status.FAIL,"<b><u><font color='red '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				else {
					logger.log(Status.FAIL, "<section class='left'><b><u><font color='red'>Operation:- '"+message+"'  is failed</u></b></font><br><b>Act Value: </b>"+actual+"<br><b>Exp Value: </b>"+expected+"</section>");
					logger.log(Status.INFO,"<b><u><font color='Chocolate '>Screenshot for above result is:</u></b></font>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				}
			}
			else if (status=="pass"){
				if((actual==null && expected==null) || (actual=="" && expected==""))
					logger.log(Status.PASS,"<b><u><font color='green '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				else 
					logger.log(Status.PASS, "<section class='left'><b><u><font color='green'>Operation:- '"+message+"'  is working as expected</u></b></font><br><b>Act Value: </b>"+actual+"<br><b>Exp Value: </b>"+expected+"</section>");
			}
			else 
				logger.log(Status.INFO,"<b><u><font color='Chocolate '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
		}catch(Exception e) {System.out.println("\nScreenshot attachment error message::\n"+e.getMessage());}
		
		report.flush();
	}

	/**
	 * Pass Information to Extent report
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusInfo(String message) {
		logger.log(Status.INFO,"<font color='Chocolate '>"+message+"</font>");
		report.flush();
	}
	
	/**
	 * Pass Info along with Screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusInfoWithScreenshot(String message) {
		setTestResult(message, null, null, "info");
	}
	
	/**
	 * Failed Scenario along with screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusFailForScreenshot(String message) {
		setTestResult(message, null, null, "fail");
	}
	
	/**
	 * Passes scenario along with Screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusPassForScreenshot(String message) {
		setTestResult(message, null, null, "pass");
	}
	
	/*****************************************************Excel Sheet*************************************************/
	/*****************************************************************************************************************/	
	/**
	 * Load ExcelSheet of Name 'tabName' for WorkBook 'workbook'
	 * @param workbook
	 * @param tabName
	 * @return XSSFSheet
	 * @author satyam.kumar
	 */
	public XSSFSheet LoadExcelSheet(XSSFWorkbook workbook, String tabName) {
		XSSFSheet sheet=null;
		if(workbook!=null) 
			sheet=workbook.getSheet(tabName);
		else
			System.out.println(" ::>> WorkBook value is null <<::");
		return sheet;
	}
	
	/**
	 * Load ExcelSheet At TabNumber 'tabNumber' for WorkBook 'workbook'
	 * @param workbook
	 * @param tabNumber
	 * @return XSSFSheet
	 * @author satyam.kumar
	 */
	public XSSFSheet LoadExcelSheet(XSSFWorkbook workbook, int tabNumber) {
		XSSFSheet sheet=null;
		if(workbook!=null) 
			sheet=workbook.getSheetAt(tabNumber);
		else
			System.out.println(" ::>> WorkBook value is null <<::");
		return sheet;
	}
	
	/**
	 * Read Excel Sheet Data
	 * @param row
	 * @param col
	 * @return Cell Value at Row index (row) and column index (col)
	 * @author satyam.kumar
	 */
	public String GetCellValue(XSSFSheet sheet,int row, int col) {
		String dataVal=null;
		XSSFCell cellType=sheet.getRow(row).getCell(col);
		if(cellType.getCellTypeEnum()==CellType.NUMERIC)
			dataVal=String.valueOf((long) cellType.getNumericCellValue());
		else if(cellType.getCellTypeEnum()==CellType.BOOLEAN)
			dataVal=Boolean.toString(cellType.getBooleanCellValue());
		else
			dataVal=cellType.getStringCellValue();	
		
		return dataVal;
	}
	
	/**
	 * Get Cell Value
	 * @param row
	 * @param col
	 * @return Cell Value
	 * @author satyam.kumar
	 */
	public String GetCellValue(Row row, int col) {
		String cellValue=null;
		Cell cellType=row.getCell(col);
		if(cellType.getCellTypeEnum()==CellType.NUMERIC)
			cellValue=Long.toString((long) cellType.getNumericCellValue());
		else if(cellType.getCellTypeEnum()==CellType.BOOLEAN)
			cellValue=Boolean.toString(cellType.getBooleanCellValue());
		else if(cellType.getCellTypeEnum()==CellType.BLANK)
			cellValue=null;
		else
			cellValue=cellType.getStringCellValue();
		
		return cellValue;
	}

	/**
	 * Create Sheet in WorkBook for Write Operations
	 * @param sheetName
	 * @return XSSFSheet
	 * @author satyam.kumar
	 */
	private XSSFSheet CreateSheet(String sheetName) {
		if(writeWorkBook==null) {
			if(writeExcelDir==null) {
				String path=System.getProperty("user.dir")+"\\ExcelSheetReports\\"+ReportName+".xlsx";
				writeExcelDir=new File(path);
				if(!writeExcelDir.exists()) {
					File parntDir=writeExcelDir.getParentFile();
					if(!parntDir.exists())
						parntDir.mkdirs();
				}	
			}
			writeWorkBook=new XSSFWorkbook();
			try {
				writeExcelDir.createNewFile();
				FileOutputStream fout = new FileOutputStream(writeExcelDir);
				writeWorkBook.write(fout);
				writeWorkBook.close();
			}catch(Exception e) {}
		}
		
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(writeExcelDir);
			writeWorkBook=new XSSFWorkbook(fis);
			writeSheet=writeWorkBook.createSheet(sheetName);
		
			FileOutputStream fout = new FileOutputStream(writeExcelDir);
			writeWorkBook.write(fout);
		}catch(Exception e) {}
		row=0;
		
		Font font = writeWorkBook.createFont();
	    font.setFontHeightInPoints((short)12);
	   // font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    CellStyle style = writeWorkBook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	   // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style.setFont(font);
	   // style.setAlignment(CellStyle.ALIGN_CENTER);
	 
	    writeSheet.setColumnWidth(0, 6500);
	    writeSheet.setColumnWidth(1, 10000);
	    writeSheet.setColumnWidth(2, 6000);
	    writeSheet.setColumnWidth(3, 6500);
	    writeSheet.setColumnWidth(4, 6500);
	    writeSheet.setColumnWidth(5, 4000);
	    
		Row headerRow=writeSheet.createRow(row);
		
		Cell cell0=	headerRow.createCell(col);
			cell0.setCellValue("Method/Steps/Scenario");
			cell0.setCellStyle(style);
		
		Cell cell1=headerRow.createCell(col+1);
			cell1.setCellValue("Message");
			cell1.setCellStyle(style);
			
		Cell cell2=headerRow.createCell(col+2);
			cell2.setCellValue("ObjectData");
			cell2.setCellStyle(style);
			
		Cell cell3=headerRow.createCell(col+3);
			cell3.setCellValue("Input/Output/Actual");
			cell3.setCellStyle(style);
			
		Cell cell4=headerRow.createCell(col+4);
			cell4.setCellValue("Expected");
			cell4.setCellStyle(style);
			
		Cell cell5=headerRow.createCell(col+5);
			cell5.setCellValue("Result");
			cell5.setCellStyle(style);	
			
		return writeSheet;
	}
	
	/**
	 * Write Data in Excel Sheet
	 * @param step
	 * @param objectData
	 * @param data
	 * @param expected
	 * @param result
	 */
	private void WriteToExcelSheet(String step, String message, String objectData, String data, String expected, String result) {
		Row rowVal=writeSheet.createRow(++row);
		if(step!=null || step!="")
			rowVal.createCell(col).setCellValue(step);
		if(message!=null || message!="")
			rowVal.createCell(col+1).setCellValue(message);
		if(objectData!=null || objectData!="")
			rowVal.createCell(col+2).setCellValue(objectData);
		if(data!=null || data!="")
			rowVal.createCell(col+3).setCellValue(data);
		if(expected!=null || expected!="")
			rowVal.createCell(col+4).setCellValue(expected);
		if(result!=null || result!="")
			rowVal.createCell(col+5).setCellValue(result);
		
		try {
			FileOutputStream fout=new FileOutputStream(writeExcelDir);
			writeWorkBook.write(fout);
		}catch(Exception e) {}
	}
	
	/**
	 * Get Total (till last entered data) row count
	 * @return Total Row Count
	 */
	public int TotalRowCount(XSSFSheet sheet) {
		return sheet.getPhysicalNumberOfRows();
	}
		
	/**
	 * Run Script using Excel Sheet
	 * @param path
	 * @param tabName
	 */
	public void RunScriptUsingExcelSheet(String path, String tabName) {
		Workbook rdWD=null;
		Sheet sheet=null;
		try {
			File readExcelDir=new File(path);//Path Where Excel sheet is located
			FileInputStream fis=new FileInputStream(readExcelDir);
			if(readStatus==false) {
				rdWD=new XSSFWorkbook(fis);//Loading .xlsx Workbook
				readStatus=true;
			}
		}catch (IOException e) {}
		
		if(rdWD!=null) {
			sheet=rdWD.getSheet(tabName);
			if(sheet!=null)
				ExcelActionExecutor(sheet);
			else
				System.out.println("No sheet found of the name '"+tabName+"'");
		}
		else
			System.out.println("Workbook not found at:: "+path);		
	}
	
	/**
	 * Run Script using Excel Sheet
	 * @param path
	 * @param tabName
	 */
	public void RunScriptUsingExcelSheet(String path, int tabNumber) {
		Workbook rdWD=null;
		Sheet sheet=null;
		try {
			File readExcelDir=new File(path);//Path Where Excel sheet is located
			FileInputStream fis=new FileInputStream(readExcelDir);
			if(readStatus==false) {
				rdWD=new XSSFWorkbook(fis);//Loading .xlsx Workbook
				readStatus=true;
			}
		}catch (IOException e) {}
		
		if(rdWD!=null) {
			sheet=rdWD.getSheetAt(tabNumber);
			if(sheet!=null)
				ExcelActionExecutor(sheet);
			else
				System.out.println("No sheet found at number '"+tabNumber+"'");
		}
		else
			System.out.println("Workbook not found at:: "+path);	
	}
	
	/**
	 * To call function based on Excel Sheet data
	 * @param rdSheet
	 */
	private void ExcelActionExecutor(Sheet sheet) {
		int colCount=0;
		System.out.println("total row count is:: "+sheet.getPhysicalNumberOfRows());
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
			System.out.println("Value of i is:: "+i);
			Row rowVal=sheet.getRow(i);
			String action=GetCellValue(rowVal, colCount);
			System.out.println("::>>Action value is:: "+action);
			
			String message=GetCellValue(rowVal, colCount+1);
			System.out.println("::>>Message value is:: "+message);
			
			if(action!=null) {
				action=action.toLowerCase();
				if(action.contains("click")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					Click(message, objectData);
				}
				else if(action.contains("send") || action.contains("input") || (action.contains("set") && action.contains("text")) || action.contains("upload")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					String inputTextVal=GetCellValue(rowVal, colCount+3);
					SetTextValue(message, objectData, inputTextVal);
				}
				else if(action.contains("mouse")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("double") && action.contains("click") && objectData!=null)
						DoubleClickOnElement(message, objectData);
					else if(action.contains("click") && objectData!=null)
						MouseHoverAndClick(message, objectData);
					else if(action.contains("double"))
						DoubleClick(message);
					else if((action.contains("right") || action.contains("context")) && objectData!=null)
						RightClickOnElement(message, objectData);
					else if(action.contains("right") || action.contains("context"))
						RightClick(message);
					else
						MouseHover(message, objectData);
				}
				else if(action.contains("gettext")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					GetTextValue(message, objectData);
				}
				else if(action.contains("select") || action.contains("drop")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					String inputVal=GetCellValue(rowVal, colCount+3);
					if(inputVal!=null && action.contains("text"))
						SelectByVisibleText(message, objectData, inputVal);
					else if(inputVal!=null && action.contains("val"))
						SelectByValue(message, objectData, inputVal);
					else if(inputVal!=null && action.contains("ind"))
						SelectByIndex(message, objectData, Integer.valueOf(inputVal));			
				}
				else if(action.contains("alert") || action.contains("alrt")) {
					if(action.contains("no") || action.contains("canc") || action.contains("dis"))
						AlertDismiss();
					else if(action.contains("text"))
						AlertGetText(message);
					else
						AlertAccept();
				}
				else if(action.contains("check")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("un"))
						CheckBoxUnCheck(message, objectData);
					else
						CheckBoxCheck(message, objectData);
				}
				else if(action.contains("radio")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("un") || action.contains("de"))
						RadioButtonDeSelect(message, objectData);
					else
						RadioButtonSelect(message, objectData);
				}
				else if(action.contains("wait") && action.contains("ext")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					int seconds=150;
					if(action.contains("click"))
						WaitUntilElementToBeClickable(message, objectData,seconds);
					else if(action.contains("sel"))
						WaitUntilElementToBeSelected(message, objectData,seconds);
					else if(action.contains("invisible"))
						WaitUntilElementToBeInVisible(message, objectData,seconds);
					else
						WaitUntilElementToBeVisible(message, objectData, seconds);
				}
				else if(action.contains("switch")) {
					if(action.contains("window")) {
						String windowName=GetCellValue(rowVal, colCount+3);
						SwitchToWindow(windowName);
					}
					else if(action.contains("alert") || action.contains("alrt"))
						SwitchToAlert();
					else if(action.contains("parent"))
						SwitchToParentFrame();
					else if(action.contains("frame")) {
						String frame=GetCellValue(rowVal, colCount+3);
						if(frame.equals("1") || frame.equals("2") || frame.equals("3") || frame.equals("4") || frame.equals("5"))
							SwitchToFrame(message, Integer.parseInt(frame));
						else
							SwitchToFrame(message, frame);
					}
					else
						SwitchToDefault();
				}
				else if(action.contains("mouse")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("click"))
						MouseHoverAndClick(message,objectData);
					else
						MouseHover(message, objectData);
					
				}
				else if(action.contains("submit")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					Submit(message, objectData);
				}
				else if(action.contains("clear")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					Clear(message, objectData);
				}
				else if(action.contains("navigate")) {
					if(action.contains("url")) {
						String url=GetCellValue(rowVal, colCount+3);
						NavigateToUrl(message, url);
					}
					else if(action.contains("back"))
						MoveBackFromCurrentWebPage(message);
					else if(action.contains("forward") || action.contains("next"))
						MoveForward(message);
				}
				else if(action.contains("popup")) {
					String winName=GetCellValue(rowVal, colCount+3);
					if(winName==null)
						GetPopUpsName(message);
					else if(winName!=null && action.contains("close"))
						SwitchToPopUpsAndClose(message, winName);
					else
						SwitchToPopUps(message, winName);				
				}
				else if(action.contains("attribute")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("class"))
						GetAttributeValue(message, objectData, "class");
					else if(action.contains("value"))
						GetAttributeValue(message, objectData, "value");
					else if(action.contains("alt"))
						GetAttributeValue(message, objectData, "alt");
					else if(action.contains("src"))
						GetAttributeValue(message, objectData, "src");
					else if(action.contains("id"))
						GetAttributeValue(message, objectData, "id");
					else if(action.contains("href"))
						GetAttributeValue(message, objectData, "href");
					else if(action.contains("title"))
						GetAttributeValue(message, objectData, "title");
					else if(action.contains("role"))
						GetAttributeValue(message, objectData, "role");
				}
				else if(action.contains("get") && action.contains("title"))
					GetTitle(message);
				else if(action.contains("current") && action.contains("url"))
					GetCurrentUrl(message);
				else if(action.contains("key") && action.contains("board")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					if(action.contains("enter")) 
						KeyBoardEnter(message);
					else if(action.contains("space"))
						KeyBoardSpace(message);
					else if((action.contains("alert") || action.contains("alrt")) && action.contains("hold"))
						KeyBoardAlertHold(message);
					else if((action.contains("alert") || action.contains("alrt")) && action.contains("rel"))
						KeyBoardAlertRelease(message);
					else if((action.contains("control") || action.contains("ctrl")) && action.contains("hold"))
						KeyBoardControlHold(message);
					else if((action.contains("control") || action.contains("ctrl")) && action.contains("rel"))
						KeyBoardControlRelease(message);
					else if(action.contains("back"))
						KeyBoardBackspace(message);
					else if(action.contains("shift") && action.contains("hold"))
						KeyBoardShiftHold(message);
					else if(action.contains("shift") && action.contains("rel"))
						KeyBoardShiftRelease(message);
					else if(action.contains("del"))
						KeyBoardDelete(message);
					else if(action.contains("down"))
						KeyBoardDownArrow(message);
					else if(action.contains("up"))
						KeyBoardUpArrow(message);
					else if(action.contains("left"))
						KeyBoardLeftArrow(message);
					else if(action.contains("right"))
						KeyBoardRightArrow(message);
					else if(action.contains("tab"))
						KeyBoardTab(message);
					else if(action.contains("decimal"))
						KeyBoardDecimal(message);
				}
				else if(action.contains("enter")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					PressEnterOnElement(message, objectData);
				}
				else if(action.contains("tag") && action.contains("name")) {
					String objectData=GetCellValue(rowVal, colCount+2);
					GetTagName(message, objectData);
				}
				else if(action.contains("url") || action.contains("get")) {
					String url=GetCellValue(rowVal, colCount+3);
					Get(message, url);
				}
					
				else if(action.contains("chrome")) {
					if(action.contains("us") || action.contains("pro"))
						ChromeUserProfileBrowser(message);
					else
						ChromeBrowser(message);
				}
				else if(action.contains("html") || action.contains("head"))
					HTMLHeadlessBrowser(message);
				else if(action.contains("firefox"))
					FirefoxBrowser(message);
				else if(action.contains("internet") || (action.contains("ie") && action.contains("brow")))
					InternetExplorerBrowser(message);
				else if(action.contains("quit"))
					QuitBrowser();
				else if(action.contains("close"))
					CloseBrowser(message);
			}
		}
	}
	/************************************************Java Operations************************************************/
	/******************************************************************************************************************/
	
	/**
	 * Terminate the execution and quit the browser
	 */
	private void Terminate() {
		if(winDriver!=null)
			winDriver.quit();
		if(driver!=null)
			driver.quit();
		System.exit(1);
	}
	
	/**
	 * Get Duplicate Records,
	 * Comparison happens in respect with first record
	 * @param objectData
	 * @return Total Number of Duplicate record respect to First Record
	 */
	public int DuplicateRecords(String objectData){
		int count=0;
		List<WebElement> list=findElements(objectData);
		String str=list.get(0).getText();
		for(WebElement ele:list){
			if(ele.getText().equals(str))
				count++;
		}
		System.out.println("::>>Duplicate Count is: "+count+" <<::");
		return count;
	}
		
	/**
	 * Get Current Date
	 * @param format
	 * @return Date in String format
	 */
	public String GetCurrentDate(String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		return df.format(date);
	}
	
	/**
	 * Get Before After Date
	 * @param format
	 * @param beforeAfterDays
	 * @return date
	 */
	public String GetBeforeAfterDate(String format, int beforeAfterDays) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.add(Calendar.DATE, beforeAfterDays);
		date = cal.getTime();
		return df.format(date);
	}
	/**********************************************Winium Operation************************************************/
	/**************************************************************************************************************/
	/**
	 * Initiate Winium Driver for Existing Open Application
	 * @param winAppPath
	 * @param appStatus :- If want to perform action on OpenApp, pass true else false
	 * @return WiniumDriver
	 * @author satyam.kumar
	 */
	public WebDriver WiniumSetup(String winAppPath, boolean appStatus) {
		boolean status=false;
		try {
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Driver\\Winium.Desktop.Driver.exe");
			Sleep(1000);
			status=true;
		} catch (IOException e) {e.printStackTrace();}
		
//		if(status==true) {
//			DesktopOptions option=new DesktopOptions();
//			option.setApplicationPath(winAppPath);
//			option.setDebugConnectToRunningApp(appStatus);
//			Sleep(3000);
//			try {
//				winDriver=new WiniumDriver(new URL("http://localhost:9999"), option);
//			} catch (Exception e) {e.printStackTrace();}
//		}
//		else
//			System.out.println("::>> Winium.Desktop.Driver not found <<::");
//		System.out.println("winDriver Valus is:: "+winDriver);
		if(winDriver==null)
			Terminate();
		Sleep(3000);
		return winDriver;
	}	
	
	/**
	 * Quit Winium Driver
	 * @author satyam.kumar
	 */
	public void QuitWinium() {
		try {
			winDriver.quit();
		}catch(Exception e) {}
	}
	
	/**
	 * Attach File (in gmail) by window PopUp 
	 * @param driverVal
	 * @param filePath
	 * @author satyam.kumar
	 */
	public void FileUploadInWindowPopUp(WebDriver driverVal, String filePath) {
		winDriver.findElement(By.name("File name:")).sendKeys(filePath);
		winDriver.findElement(By.name("Open")).submit();
	}
	
	/**
	 * FindElement method
	 * @param ByValue
	 * @return WebElement
	 * @author satyam.kumar
	 */
	public WebElement winFindElement(By ByValue) {
		WebElement element=null;
		try {
			element=winDriver.findElement(ByValue);
		}catch(Exception e) {System.out.println("::>> WebElement not found for ByValue '"+ByValue+"' <<::");}
		return element;
	}
	
	/**
	 * Fluent Wait Operation
	 * Maximum allocated time is 5 seconds
	 * Pooling frequency is 30 miliSeconds
	 * @param ByValue
	 * @return webElement
	 * @author satyam.kumar
	 */
	public WebElement winFluentwait(By ByValue){
		FluentWait<WebDriver> fwait =new FluentWait<WebDriver>(winDriver);
		  fwait.withTimeout(5, TimeUnit.SECONDS);
		  fwait.pollingEvery(50,TimeUnit.MILLISECONDS);
		   fwait.ignoring(NoSuchElementException.class);
		 fwait.until(ExpectedConditions.visibilityOfElementLocated(ByValue));
		 WebElement element=winFindElement(ByValue);
		 return element;
	}
	
	/**
	 * Wait until Element to be Clickable
	 * @param ByValue
	 * @param seconds
	 * @author satyam.kumar
	 */
	public void winWaitUntilElementToBeClickable(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(winDriver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(ByValue));
	}
	
	/**
	 * Wait until Element to be Selected
	 * @param ByValue
	 * @param seconds
	 * @author satyam.kumar
	 */
	public void winWaitUntilElementToBeSelected(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(winDriver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(ByValue));
	}
	
	/**
	 * Wait until Element to be Visible
	 * @param ByValue
	 * @param seconds
	 * @author satyam.kumar
	 */
	public void winWaitUntilElementToBeVisible(By ByValue, int seconds) {
		WebDriverWait wait=new WebDriverWait(winDriver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ByValue));
	}
	
	/**
	 * Element is Displayed
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean winIsDisplayed(By ByValue) {
		boolean status=false;
		WebElement element=winFluentwait(ByValue);
		if(element!=null) {
			element.isDisplayed();
			status=true;
		}
		else
			System.out.println("::>> Element value is null <<::");
		return status;
	}
	
	/**
	 * Element is Enabled
	 * @param ByValue
	 * @return boolean
	 * @author satyam.kumar
	 */
	public boolean winIsEnabled(By ByValue) {
		boolean status=false;
		WebElement element=winFluentwait(ByValue);
		if(element!=null) {
			element.isEnabled();
			status=true;
		}
		else
			System.out.println("::>> Element value is null <<::");
		return status;		
	}
}
