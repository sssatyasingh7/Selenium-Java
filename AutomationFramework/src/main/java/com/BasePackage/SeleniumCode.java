package com.BasePackage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

class SeleniumCode {

	private static TestEnvironment testEnv;
	protected SeleniumCode() {
		if(testEnv == null) {
			testEnv = new TestEnvironment();
			driver = TestEnvironment.getDriver();
		}
	}
	private static WebDriver driver;// = TestEnvironment.getDriver();

	/**
	 * findElement
	 * 
	 * @param byValue
	 * @return webElement
	 */
	protected WebElement findElement(By byValue) {
		WebElement element = null;
		try {
			element = driver.findElement(byValue);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return element;
	}

	/**
	 * findElement
	 * 
	 * @param byValue
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement findElement(By byValue, long seconds) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(byValue));
			element = findElement(byValue);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return element;
	}

	/**
	 * Find Element Operation
	 * 
	 * @param parentElement
	 * @param byValue
	 * @return webElement
	 */
	protected WebElement findElement(WebElement parentElement, By byValue) {
		WebElement element = null;
		try {
			element = parentElement.findElement(byValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

	/**
	 * Find Element Operation
	 * 
	 * @param parentbyValue
	 * @param byValue
	 * @return webElement
	 */
	protected WebElement findElement(By parentbyValue, By byValue) {
		WebElement element = findElement(parentbyValue);
		if (element != null) {
			return findElement(element, byValue);
		} else {
			Assert.fail();
			return null;
		}
	}

	/**
	 * findElements
	 * 
	 * @param byValue
	 * @return List<WebElement>
	 */
	protected List<WebElement> findElements(By byValue) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(byValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elements;
	}

	/**
	 * findElements
	 * 
	 * @param byValue
	 * @param seconds
	 * @return List<WebElement>
	 */
	protected List<WebElement> findElements(By byValue, long seconds) {
		List<WebElement> elements = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byValue));
			elements = driver.findElements(byValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elements;
	}

	/**
	 * locator identifier
	 * 
	 * @param locator
	 * @param locatorValue
	 * @return
	 */
	@Deprecated
	private By locatorIdentifier(String locator, String locatorValue) {
		locator = locator.toLowerCase();
		if (locator.equalsIgnoreCase("id")) {
			return By.id(locatorValue);
		} else if (locator.equalsIgnoreCase("name")) {
			return By.name(locatorValue);
		} else if (locator.contains("class")) {
			return By.className(locatorValue);
		} else if (locator.contains("css")) {
			return By.cssSelector(locatorValue);
		} else if (locator.contains("xpath")) {
			return By.xpath(locatorValue);
		} else if (locator.contains("partial")) {
			return By.partialLinkText(locatorValue);
		} else if (locator.contains("link")) {
			return By.linkText(locatorValue);
		} else if (locator.contains("tag")) {
			return By.tagName(locatorValue);
		} else {
			return null;
		}
	}

	/**
	 * locator identifier
	 * 
	 * @param objectData
	 * @return By
	 */
	private By locatorIdentifier(String objectData) {
		String locator = objectData.substring(0, objectData.indexOf('[')).toLowerCase();
		String locatorValue = objectData.substring(objectData.indexOf('[') + 1, objectData.lastIndexOf(']'));

		locator = locator.toLowerCase();
		if (locator.equalsIgnoreCase("id")) {
			return By.id(locatorValue);
		} else if (locator.equalsIgnoreCase("name")) {
			return By.name(locatorValue);
		} else if (locator.contains("class")) {
			return By.className(locatorValue);
		} else if (locator.contains("css")) {
			return By.cssSelector(locatorValue);
		} else if (locator.contains("xpath")) {
			return By.xpath(locatorValue);
		} else if (locator.contains("partial")) {
			return By.partialLinkText(locatorValue);
		} else if (locator.contains("link")) {
			return By.linkText(locatorValue);
		} else if (locator.contains("tag")) {
			return By.tagName(locatorValue);
		} else {
			return null;
		}
	}

//	public WebElement fluentWait(By byValue) {
//		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
//		wait.withTimeout(30, TimeUnit.SECONDS);
//		wait.pollingEvery(10, TimeUnit.MILLISECONDS);
//		wait.ignoring(Exception);
//		
//		
//	}
	
	/**
	 * findElement
	 * 
	 * @param objectData
	 * @return webElement
	 */
	protected WebElement findElement(String objectData) {
		return findElement(locatorIdentifier(objectData));
	}

	/**
	 * findElement
	 * 
	 * @param objectData
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement findElement(String objectData, long seconds) {
		return findElement(locatorIdentifier(objectData), seconds);
	}

	/**
	 * findElements
	 * 
	 * @param objectData
	 * @return List<WebElement>
	 */
	protected List<WebElement> findElements(String objectData) {
		return findElements(locatorIdentifier(objectData));
	}

	/**
	 * findElements
	 * 
	 * @param objectData
	 * @param seconds
	 * @return List<WebElement>
	 */
	protected List<WebElement> findElements(String objectData, long seconds) {
		return findElements(locatorIdentifier(objectData), seconds);
	}

	/**
	 * Find Element Operation (Maximum Wait Time 30 Seconds)
	 * 
	 * @param byValue
	 * @return webElement
	 */
	protected WebElement findElementOper(By byValue) {
		return findElement(byValue, 30);
	}

	/**
	 * Find Element Operation (Maximum Wait Time 30 Seconds)
	 * 
	 * @param objectData
	 * @return webElement
	 */
	protected WebElement findElementOper(String objectData) {
		return findElement(objectData, 30);
	}

	/**
	 * Element to be Visible
	 * 
	 * @param byValue
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBeVisible(By byValue, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byValue));
		return findElement(byValue);
	}

	/**
	 * Element to be Visible
	 * 
	 * @param objectData
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBeVisible(String objectData, long seconds) {
		return elementToBeVisible(locatorIdentifier(objectData), seconds);
	}

	/**
	 * Element to be Visible
	 * 
	 * @param element
	 * @param seconds
	 */
	protected void elementToBeVisible(WebElement element, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Element to be Clickable
	 * 
	 * @param byValue
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBeClickable(By byValue, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(byValue));
		return findElement(byValue);
	}

	/**
	 * Element to be Clickable
	 * 
	 * @param objectData
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBeClickable(String objectData, long seconds) {
		return elementToBeClickable(locatorIdentifier(objectData), seconds);
	}

	/**
	 * Element to be Clickable
	 * 
	 * @param element
	 * @param seconds
	 */
	protected void elementToBeClickable(WebElement element, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Element to be Present
	 * 
	 * @param byValue
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBePresent(By byValue, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(byValue));
		return findElement(byValue);
	}

	/**
	 * Element to be Present
	 * 
	 * @param objectData
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement elementToBePresent(String objectData, long seconds) {
		return elementToBePresent(locatorIdentifier(objectData), seconds);
	}

	/**
	 * Presence of Nested Element
	 * 
	 * @param parentElement
	 * @param byValue
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement nestedElementToBePresent(WebElement parentElement, By byValue, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, byValue));
		return findElement(parentElement, byValue);
	}

	/**
	 * Presence of Nested Element
	 * 
	 * @param parentElement
	 * @param objectData
	 * @param seconds
	 * @return webElement
	 */
	protected WebElement nestedElementToBePresent(WebElement parentElement, String objectData, long seconds) {
		By byValue = locatorIdentifier(objectData);
		return nestedElementToBePresent(parentElement, byValue, seconds);
	}

	/**
	 * Wait until Alert is Present
	 * 
	 * @param seconds
	 */
	protected void alertIsPresent(long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Staleness Of Element
	 * 
	 * @param element
	 * @param seconds
	 */
	protected void stalenessOfElement(WebElement element, long seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.stalenessOf(element));
	}

	/**
	 * Page Load Time
	 * 
	 * @param seconds
	 */
	protected void pageLoadTimeOut(long seconds) {
		driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Open URL driver.get() operation
	 * 
	 * @param url
	 */
	protected void getURL(String url) {
		driver.get(url);
		pageLoadTimeOut(100);
	}

	/**
	 * Click Operation
	 * 
	 * @param byValue
	 */
	protected void click(By byValue) {
		WebElement element = findElement(byValue, 5);
		if (element != null) {
			scrollToView(element);
			element.click();
		}
	}

	/**
	 * SendKeys Operation
	 * 
	 * @param byValue
	 * @param inputText
	 */
	protected void sendKeys(By byValue, String inputText) {
		WebElement element = findElement(byValue, 5);
		if (element != null) {
			element.clear();
			element.sendKeys(inputText);
		}
	}

	/**
	 * Clear Operation
	 * 
	 * @param byValue
	 */
	protected void clear(By byValue) {
		WebElement element = findElement(byValue, 5);
		if (element != null) {
			element.clear();
		}
	}

	protected void closeBrowser() {
		driver.close();
	}

	/**
	 * Quit Browser
	 */
	protected void quitBrowser() {
		driver.quit();
	}

	/**
	 * Scroll To View Element
	 * 
	 * @param element
	 */
	protected void scrollToView(WebElement element) {
		if (element != null) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	/**
	 * PauseMe Thread.sleep()
	 * 
	 * @param seconds
	 */
	protected void pauseMe(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
		}
	}
}
