package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface Form {

	public WebDriver getWebDriverInstance();
	
	public WebElement findElement(By locator);
}
