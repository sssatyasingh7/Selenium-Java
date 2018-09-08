package com.BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

class TestEnvironment {

	private static final Properties properties = new Properties();
	private static WebDriver driver;
	private String isExtentReport = null;
	private String isExcelReport = null;
	
	
	TestEnvironment() {
		if (driver == null) {
			try {
				File file = new File(System.getProperty("user.dir") + "\\configure.properties");
				FileInputStream fileInput = new FileInputStream(file);
				properties.load(fileInput);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String browserName = properties.getProperty("browserName");
			String driverPath = properties.getProperty("driverPath");
			if (browserName != null) {
				if (browserName.equalsIgnoreCase("chrome")) {
					ChromeBrowser(driverPath);
				} else if (browserName.equalsIgnoreCase("firefox")) {
					FirefoxBrowser(driverPath);
				} else if (browserName.equalsIgnoreCase("html")) {
					HTMLUnitHeadlessBrowser();
				} else if (browserName.equalsIgnoreCase("ie")) {
					InternetExplorerBrowser(driverPath);
				}
				driver.manage().window().maximize();
				
				
			}
		}
	}

	static WebDriver getDriver() {
		return driver;
	}

	/**
	 * Initiate Chrome Browser
	 * 
	 * @param chromeDriverPath
	 * @return webDriver
	 */
	private static WebDriver ChromeBrowser(String chromeDriverPath) {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		return driver;
	}

	/**
	 * Initiate Chrome Profile Browser
	 * 
	 * @param chromeDriverPath
	 * @param chromeProfilePath
	 * @return webDriver
	 */
	private static WebDriver ChromeUserProfileBrowser(String chromeDriverPath, String chromeProfilePath) {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=" + chromeProfilePath);
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		return driver;
	}

	/**
	 * Initiate Firefox Browser
	 * 
	 * @param firefoxDriverPath
	 * @return webDriver
	 */
	private static WebDriver FirefoxBrowser(String firefoxDriverPath) {
		System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
		driver = new FirefoxDriver();
		return driver;
	}

	/**
	 * Initiate Internet Explorer Browser
	 * 
	 * @param ieDriverPath
	 * @return webDriver
	 */
	private static WebDriver InternetExplorerBrowser(String ieDriverPath) {
		System.setProperty("webdriver.ie.driver", ieDriverPath);
		driver = new InternetExplorerDriver();
		return driver;
	}

	/**
	 * Initiate HTML Headless browser
	 * 
	 * @return webDriver
	 */
	private static WebDriver HTMLUnitHeadlessBrowser() {
		driver = new HtmlUnitDriver();
		return driver;
	}

	/*
	 * protected static TestEnvironment getInstance() { try { final
	 * BrowserInitialization browser = BrowserInitialization.getInstance(); File
	 * file = new File(System.getProperty("user.dir") + "\\configure.properties");
	 * FileInputStream fileInput = new FileInputStream(file);
	 * properties.load(fileInput);
	 * 
	 * String browserName = properties.getProperty("browserName");
	 * System.out.println("Browser Name is:: " + browserName); browserName =
	 * browserName.toLowerCase();
	 * 
	 * String newBrowserInstance = properties.getProperty("newBrowserInstance");
	 * System.out.println("::>> New Browser Instance is:: "+newBrowserInstance); if
	 * (newBrowserInstance == null) { newBrowserInstance = "yes"; } else {
	 * newBrowserInstance = newBrowserInstance.toLowerCase(); }
	 * 
	 * String driverPath = properties.getProperty("driverPath",
	 * System.getProperty("user.dir") + "\\Driver\\");
	 * 
	 * if (browserName != null && newBrowserInstance.startsWith("y")) { if
	 * (browserName.contains("chrome")) { driver =
	 * browser.ChromeBrowser(driverPath); } else if
	 * (browserName.contains("firefox")) { driver =
	 * browser.FirefoxBrowser(driverPath); } else if (browserName.equals("ie") ||
	 * browserName.contains("internet")) { driver =
	 * browser.FirefoxBrowser(driverPath); } else if (browserName.contains("html")
	 * || browserName.contains("head")) { driver =
	 * browser.FirefoxBrowser(driverPath); } } else { Assert.
	 * fail("*** Browser Name is not mentioned in 'Cinfiguration' properties file ***"
	 * ); } } catch (Exception e) { e.printStackTrace(); } return testEnv; }
	 */

	public static String getPropertyValue(String key) {
		return properties.getProperty(key);
	}

}
