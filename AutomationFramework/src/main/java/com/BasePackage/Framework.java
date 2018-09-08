package com.BasePackage;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

public class Framework extends SeleniumCode {

	protected static final FormOperation form = new FormOperation();
	protected static final WebDriver driver = TestEnvironment.getDriver();

	protected static final String baseURL = TestEnvironment.getPropertyValue("baseURL");
	protected static final String userID = TestEnvironment.getPropertyValue("userID");
	protected static final String password = TestEnvironment.getPropertyValue("password");

	
	@AfterSuite
	public void quitBrowser() {
		//quitBrowser();
		driver.quit();
	}
}
