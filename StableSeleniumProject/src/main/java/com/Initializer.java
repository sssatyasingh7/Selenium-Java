package com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

enum Initializer {
	INSTANCE;

	/**
	 * 
	 * @return {@link WebDriver}
	 */
	static WebDriver getDriverInstance() {
		WebDriver driver = null;
		TestEnvironment testEnv = TestEnvironment.INSTANCE;
		String driverPath = testEnv.getPropertiesValue("DRIVER_PATH");
		String browser = testEnv.getBrowserName();
		if (browser.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath);
			ChromeOptions options = new ChromeOptions();
			try {
				options.setExperimentalOption("useAutomationExtension", false);
			} catch (Exception e) {
			}
			driver = new ChromeDriver(options);
		} else if (browser.contains("fire") || browser.contains("ff")) {
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver();
		} else if (browser.contains("internet") || browser.contains("ie")) {
			System.setProperty("webdriver.ie.driver", driverPath);
			driver = new InternetExplorerDriver();
		} else if (browser.contains("edge")) {
			System.setProperty("webdriver.edge.driver", driverPath);
			driver = new EdgeDriver();
		}
		if (CommonUtils.isNotNull(driver))
			driver.manage().window().maximize();
		return driver;
	}

}
