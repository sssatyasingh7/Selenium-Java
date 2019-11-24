package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumUtils implements Form{
	
	private static WebDriver DRIVER;
	private static SeleniumUtils form;
	private static TestEnvironment testEnv;
	
	private SeleniumUtils() {
		testEnv = TestEnvironment.INSTANCE;
	}
	
	public static final SeleniumUtils getInstance() {
		return form == null ? (form = new SeleniumUtils()) : form;
	}
	
	public  static final void setWebDriverInstance(WebDriver driver) {
		DRIVER = driver;
	}

	@Override
	public WebDriver getWebDriverInstance() {
		return DRIVER;
	}

	@Override
	public WebElement findElement(By locator) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
