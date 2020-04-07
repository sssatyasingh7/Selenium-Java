package com;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class SeleniumUtils implements Form {

	private static WebDriver DRIVER;
	private static SeleniumUtils form;
	// private static TestEnvironment testEnv;
	private String defaultURL;
	private String userName;
	private String password;
	private ATUTestRecorder recorder;

	/**
	 * @return {@link SeleniumUtils}
	 */
	private SeleniumUtils() {
		TestEnvironment testEnv = TestEnvironment.INSTANCE;
		this.defaultURL = testEnv.getDefaultURL();
		this.userName = testEnv.getUserName();
		this.password = testEnv.getPassword();
	}

	/**
	 * 
	 * @return {@link SeleniumUtils}
	 */
	public static final SeleniumUtils getInstance() {
		form = form == null ? (form = new SeleniumUtils()) : form;
		return form;
	}

	/**
	 * 
	 * @param driver
	 */
	public static final void setWebDriverInstance(WebDriver driver) {
		DRIVER = driver;
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getDefaultURL() {
		return this.defaultURL;
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getUserName() {
		return this.userName;
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getPassword() {
		return this.password;
	}

	@Override
	public WebDriver getWebDriverInstance() {
		return DRIVER;
	}

	/**
	 * 
	 */
	public final void quitWebDriverInstance() {
		if (DRIVER != null) {
			DRIVER.quit();
		}
	}

	/**
	 * 
	 * @param driver
	 */
	public final void closeWebDriverInstance(WebDriver driver) {
		if (driver != null) {
			driver.close();
		}
	}

	/**
	 * 
	 */
	public final void closeWebDriverInstance() {
		closeWebDriverInstance(DRIVER);
	}

	/**
	 * 
	 * @param milliSeconds
	 */
	public final void threadSleep(long milliSeconds) {
		CommonUtils.threadSleep(milliSeconds);
	}

	/**
	 * 
	 * @param seconds
	 */
	private void waitForAngularLoad(long seconds) {
		try {
			if (DRIVER != null) {
				String angularScript = "return angular.element(document).injector().get('$http').pendingRequests.length===0";
				ExpectedCondition<Boolean> angularLoad = driver -> Boolean
						.valueOf(((JavascriptExecutor) driver).executeScript(angularScript).toString());
				if (!Boolean.parseBoolean(((JavascriptExecutor) DRIVER).executeScript(angularScript).toString()))
					new WebDriverWait(DRIVER, seconds).until(angularLoad);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param seconds
	 */
	private void waitUntilJSReady(long seconds) {
		try {
			if (DRIVER != null) {
				ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) DRIVER)
						.executeScript("return document.readyState").toString().equals("complete");
				if (!(boolean) ((JavascriptExecutor) DRIVER).executeScript("return document.readyState").toString()
						.equals("complete"))
					new WebDriverWait(DRIVER, seconds).until(jsLoad);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param seconds
	 */
	private void waitUntilAngularReady(long seconds) {
		try {
			JavascriptExecutor jsExec = (JavascriptExecutor) DRIVER;
			if (jsExec != null && !(boolean) jsExec.executeScript("return window.angular === undefined")
					&& !(boolean) jsExec.executeScript("return angular.element(document).injector() === undefined")) {
				waitForAngularLoad(seconds);
				waitUntilJSReady(seconds);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param seconds
	 */
	private void waitAngularJs(long seconds) {
		waitUntilAngularReady(seconds);
		waitUntilJSReady(seconds);
	}

	/**
	 * 
	 * @return {@link JavascriptExecutor}
	 */
	public JavascriptExecutor getJavaScriptExecutorInstance() {
		return (DRIVER != null) ? (JavascriptExecutor) DRIVER : null;
	}

	/**
	 * 
	 * @param locator
	 * @param inputText
	 */
	public final void sendKeysUsingJS(By locator, String inputText) {
		WebElement element = findElement(locator);
		JavascriptExecutor js = getJavaScriptExecutorInstance();
		if (element != null && js != null) {
			js.executeScript(String.format("arguments[0].value='%s'", inputText), element);
		}
	}

	/**
	 * 
	 * @param seconds
	 */
	public final void pageLoadTime(long seconds) {
		if (DRIVER != null) {
			try {
				waitAngularJs(seconds);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 */
	public final void pageLoadTime() {
		pageLoadTime(100);
	}

	/**
	 * 
	 * @param urlValue
	 */
	public final void get(String urlValue) {
		if (DRIVER != null) {
			DRIVER.get(urlValue);
		}
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getCurrentURL() {
		return DRIVER != null ? DRIVER.getCurrentUrl() : null;
	}

	/**
	 * 
	 * @param driver
	 * @return {@link String}
	 */
	public final String getWindowURL(WebDriver driver) {
		return driver != null ? driver.getCurrentUrl() : null;
	}

	/**
	 * 
	 * @param driver
	 * @return {@link String}
	 */
	public final String getTitle(WebDriver driver) {
		return driver != null ? driver.getTitle() : null;
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getTitle() {
		return getTitle(DRIVER);
	}

	/**
	 * 
	 * @param element
	 */
	public final void scrollToView(WebElement element) {
		JavascriptExecutor js = getJavaScriptExecutorInstance();
		if (js != null && element != null) {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	/**
	 * \
	 * 
	 * @param locator
	 */
	public final void scrollToView(By locator) {
		scrollToView(findElement(locator));
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 * @return {@link WebElement}
	 */
	public final WebElement elementToBePresent(By locator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return DRIVER.findElement(locator);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 * @return {@link WebElement}
	 */
	public final WebElement findElement(By locator, long seconds) {
		return (locator == null) ? null : elementToBePresent(locator, seconds);
	}

	@Override
	public final WebElement findElement(By locator) {
		return (locator == null) ? null : elementToBePresent(locator, 3);
	}

	/**
	 * 
	 * @param locator
	 * @return {@link WebElement}
	 */
	public final WebElement findElementWithoutWait(By locator) {
		try {
			return DRIVER.findElement(locator);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @return {@link WebElement}
	 */
	public final WebElement findNestedElement(WebElement parentElement, By childLocator) {
		return nestedElementToBePresent(parentElement, childLocator, 2);
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @return {@link WebElement}
	 */
	public final WebElement findNestedElement(By parentLocator, By childLocator) {
		return nestedElementToBePresent(findElement(parentLocator), childLocator, 2);
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @return {@link String}
	 */
	public final String getNestedElementTextValue(WebElement parentElement, By childLocator) {
		return getText(findNestedElement(parentElement, childLocator));
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @return {@link String}
	 */
	public final String getNestedElementTextValue(By parentLocator, By childLocator) {
		return getNestedElementTextValue(findElement(parentLocator), childLocator);
	}

	/**
	 * 
	 * @param locator
	 * @return {@link Boolean}
	 */
	public final boolean isElementPresent(By locator) {
		return findElementWithoutWait(locator) != null;
	}

	/**
	 * 
	 * @param locator
	 * @return {@link List}
	 */
	public final List<WebElement> findElementsWithoutWait(By locator) {
		return DRIVER.findElements(locator);
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @param seconds
	 */
	public final void nestedElementToBePresent(By parentLocator, By childLocator, long seconds) {
		WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentLocator, childLocator));
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @param seconds
	 * @return {@link WebElement}
	 */
	public final WebElement nestedElementToBePresent(WebElement parentElement, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, childLocator));
			return parentElement.findElement(childLocator);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @return {@link List}
	 */
	public final List<WebElement> findNestedElements(WebElement parentElement, By childLocator) {
		try {
			return parentElement.findElements(childLocator);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @return {@link List}
	 */
	public final List<WebElement> findNestedElements(By parentLocator, By childLocator) {
		return findNestedElements(findElement(parentLocator), childLocator);
	}

	/**
	 * 
	 * @param element
	 * @return {@link String}
	 */
	public final String getText(WebElement element) {
		return (element == null) ? null : CommonUtils.returnValue(element.getText());
	}

	/**
	 * 
	 * @param locator
	 * @return {@link String}
	 */
	public final String getText(By locator) {
		return getText(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 * @param attribute
	 * @return {@link String}
	 */
	public final String getAttribute(WebElement element, String attribute) {
		return (element != null && attribute != null) ? CommonUtils.returnValue(element.getAttribute(attribute)) : null;
	}

	/**
	 * 
	 * @param locator
	 * @param attribute
	 * @return {@link String}
	 */
	public final String getAttribute(By locator, String attribute) {
		return (locator != null && attribute != null) ? getAttribute(findElement(locator), attribute) : null;
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @return {@link List}
	 */
	public final List<String> getNestedElementsGetText(WebElement parentElement, By childLocator) {
		return findNestedElements(parentElement, childLocator).stream().map(this::getText).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @return {@link List}
	 */
	public final List<String> getNestedElementsGetText(By parentLocator, By childLocator) {
		return getNestedElementsGetText(findElement(parentLocator), childLocator);
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 * @param seconds
	 */
	public final void nestedElementToBeVisible(By parentLocator, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentLocator, childLocator));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 * @param seconds
	 */
	public final void nestedElementToBeVisible(WebElement parentElement, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentElement, childLocator));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param element
	 * @param seconds
	 */
	public final void elementToBeClickable(WebElement element, long seconds) {
		if (element != null) {
			scrollToView(element);
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 */
	public final void elementToBeClickable(By locator, long seconds) {
		if (locator != null) {
			scrollToView(locator);
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		}
	}

	/**
	 * 
	 * @param element
	 * @param seconds
	 */
	public final void elementToBeVisible(WebElement element, long seconds) {
		if (DRIVER != null && element != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 */
	public final void elementToBeVisible(By locator, long seconds) {
		if (DRIVER != null && locator != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
	}

	/**
	 * 
	 * @param element
	 * @param seconds
	 */
	public final void elementToBeInvisible(WebElement element, long seconds) {
		if (DRIVER != null && element != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOf(element));
		}
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 */
	public final void elementToBeInvisible(By locator, long seconds) {
		if (DRIVER != null && locator != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
	}

	/**
	 * 
	 * @param locator
	 * @param textValue
	 * @param seconds
	 */
	public final void elementWithTextToBeInvisible(By locator, String textValue, long seconds) {
		if (DRIVER != null && locator != null && textValue != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, textValue));
		}
	}

	/**
	 * 
	 * @param locator
	 * @param textValue
	 * @param seconds
	 */
	public final void elementWithTextToBePresent(By locator, String textValue, long seconds) {
		if (DRIVER != null && locator != null && textValue != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, textValue));
		}
	}

	/**
	 * 
	 * @param urlValue
	 * @param seconds
	 */
	public final void waitUntilURLContains(String urlValue, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.urlContains(urlValue));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 */
	public final void pageRefresh() {
		try {
			DRIVER.navigate().refresh();
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 */
	public final void pageReload() {
		pageRefresh();
	}

	/**
	 * 
	 * @param element
	 */
	public final void pressEnter(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ENTER);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void pressEnter(By locator) {
		pressEnter(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void clear(WebElement element) {
		if (element != null) {
			element.clear();
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void clear(By locator) {
		clear(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void clearAndEnter(WebElement element) {
		if (element != null) {
			clear(element);
			pressEnter(element);
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void clearAndEnter(By locator) {
		clearAndEnter(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void pressTab(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.TAB);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void pressTab(By locator) {
		pressTab(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void clearAndTab(WebElement element) {
		if (element != null) {
			clear(element);
			pressTab(element);
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void clearAndTab(By locator) {
		clearAndTab(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void click(WebElement element) {
		if (element != null) {
			elementToBeClickable(element, 3);
			try {
				element.click();
			} catch (Exception e) {
				// TODO: handle exception
			}
			pageLoadTime();
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void click(By locator) {
		click(findElement(locator));
	}

	/**
	 * 
	 * @param locator
	 */
	public final void clickWithoutWait(By locator) {
		click(findElementWithoutWait(locator));
	}

	/**
	 * 
	 * @param parentElement
	 * @param childLocator
	 */
	public final void clickOnNestedElement(WebElement parentElement, By childLocator) {
		if (parentElement != null) {
			click(findNestedElement(parentElement, childLocator));
		}
	}

	/**
	 * 
	 * @param parentLocator
	 * @param childLocator
	 */
	public final void clickOnNestedElement(By parentLocator, By childLocator) {
		clickOnNestedElement(findElement(parentLocator), childLocator);
	}

	/**
	 * 
	 * @param element
	 */
	public final void clickWithoutScroll(WebElement element) {
		if (element != null) {
			try {
				element.click();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void clickWithoutScroll(By locator) {
		clickWithoutScroll(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 * @param keysToSend
	 */
	public final void appendText(WebElement element, String keysToSend) {
		if (element != null && keysToSend != null) {
			try {
				element.sendKeys(keysToSend);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * @param locator
	 * @param keysToSend
	 */
	public final void appendText(By locator, String keysToSend) {
		if (locator != null && keysToSend != null) {
			appendText(findElement(locator), keysToSend);
		}
	}

	/**
	 * 
	 * @param element
	 * @param keysToSend
	 */
	public final void sendKeys(WebElement element, String keysToSend) {
		if (element != null && keysToSend != null) {
			try {
				scrollToView(element);
				elementToBeVisible(element, 3);
			} catch (Exception e) {
			}
			clear(element);
			appendText(element, keysToSend);
		}
	}

	/**
	 * 
	 * @param locator
	 * @param keysToSend
	 */
	public final void sendKeys(By locator, String keysToSend) {
		if (locator != null && keysToSend != null) {
			sendKeys(findElement(locator), keysToSend);
		}
	}

	/**
	 * 
	 * @param element
	 */
	public final void pressDownArrow(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ARROW_DOWN);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void pressDownArrow(By locator) {
		pressDownArrow(findElement(locator));
	}

	/**
	 * 
	 * @param element
	 */
	public final void pressUpArrow(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ARROW_UP);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void pressUpArrow(By locator) {
		pressDownArrow(findElement(locator));
	}

	/**
	 * 
	 */
	public final void switchToDefaultWindow() {
		if (DRIVER != null) {
			DRIVER.switchTo().defaultContent();
		}
	}

	/**
	 * 
	 * @param element
	 * @param index
	 */
	public final void selectByIndex(WebElement element, int index) {
		if (element != null) {
			Select sel = new Select(element);
			sel.selectByIndex(index);
		}
	}

	/**
	 * 
	 * @param locator
	 * @param index
	 */
	public final void selectByIndex(By locator, int index) {
		if (locator != null) {
			selectByIndex(findElement(locator), index);
		}
	}

	/**
	 * 
	 * @param element
	 * @param value
	 */
	public final void selectByValue(WebElement element, String value) {
		if (element != null && value != null) {
			Select sel = new Select(element);
			sel.selectByValue(value);
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 */
	public final void selectByValue(By locator, String value) {
		if (locator != null && value != null) {
			selectByValue(findElement(locator), value);
		}
	}

	/**
	 * 
	 * @param element
	 * @param visibleText
	 */
	public final void selectByVisibleText(WebElement element, String visibleText) {
		if (element != null && visibleText != null) {
			Select sel = new Select(element);
			sel.selectByVisibleText(visibleText);
		}
	}

	/**
	 * 
	 * @param locator
	 * @param visibleText
	 */
	public final void selectByVisibleText(By locator, String visibleText) {
		if (locator != null && visibleText != null) {
			selectByVisibleText(findElement(locator), visibleText);
		}
	}

	/**
	 * 
	 * @param element
	 */
	public final void submit(WebElement element) {
		if (element != null) {
			element.submit();
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void submit(By locator) {
		submit(findElement(locator));
	}

	/**
	 * 
	 * @param locator
	 * @param seconds
	 * @return {@link List}
	 */
	public final List<WebElement> elementsToBePresent(By locator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
		}
		return DRIVER.findElements(locator);
	}

	/**
	 * 
	 * @param locator
	 * @return {@link List}
	 */
	public final List<WebElement> findElements(By locator) {
		return elementsToBePresent(locator, 3);
	}

	/**
	 * 
	 * @param elements
	 * @return {@link List}
	 */
	public final List<String> getListOfText(List<WebElement> elements) {
		return elements.stream().map(this::getText).filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param locator
	 * @return {@link List}
	 */
	public final List<String> getListOfText(By locator) {
		return getListOfText(findElements(locator));
	}

	/**
	 * 
	 * @param locator
	 * @return {@link List}
	 */
	public final List<String> getListOfTextWithoutWait(By locator) {
		return getListOfText(findElementsWithoutWait(locator));
	}

	/**
	 * 
	 * @param elements
	 * @param attributeName
	 * @return {@link List}
	 */
	public final List<String> getListOfAttributeValues(List<WebElement> elements, String attributeName) {
		return elements.stream().map(element -> getAttribute(element, attributeName)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param locator
	 * @param attributeName
	 * @return {@link List}
	 */
	public final List<String> getListOfAttributeValues(By locator, String attributeName) {
		return getListOfAttributeValues(findElements(locator), attributeName);
	}

	/**
	 * 
	 * @param locator
	 * @param attributeName
	 * @return {@link List}
	 */
	public final List<String> getListOfAttributeValuesWithoutWait(By locator, String attributeName) {
		return getListOfAttributeValues(findElementsWithoutWait(locator), attributeName);
	}

	/**
	 * 
	 * @param destFilePath
	 * @return {@link Boolean}
	 */
	public final boolean captureScreenshot(String destFilePath) {
		try {
			FileUtils.copyFile(((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE), new File(destFilePath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param destFilePath
	 */
	public final void captureFullPageScreenshot(String destFilePath) {
		try {
			ImageIO.write(new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DRIVER)
					.getImage(), "PNG", new File(destFilePath));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @return {@link Alert}
	 */
	public final Alert getAlertInstance() {
		try {
			threadSleep(200);
			return DRIVER.switchTo().alert();
		} catch (NoAlertPresentException ae) {
			return null;
		}
	}

	/**
	 * 
	 * @return {@link Boolean}
	 */
	public final boolean isAlertExist() {
		if (getAlertInstance() != null) {
			switchToDefaultWindow();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	public final void acceptAlert() {
		if (DRIVER != null) {
			DRIVER.switchTo().alert().accept();
		}
	}

	/**
	 * 
	 */
	public final void acceptAlertIfExist() {
		Alert alert = getAlertInstance();
		if (alert != null) {
			alert.accept();
		}
	}

	/**
	 * 
	 */
	public final void dismissAlert() {
		if (DRIVER != null) {
			DRIVER.switchTo().alert().dismiss();
		}
	}

	/**
	 * 
	 */
	public final void dismissAlertIfExist() {
		Alert alert = getAlertInstance();
		if (alert != null) {
			alert.dismiss();
		}
	}

	/**
	 * 
	 * @param element
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToFrame(WebElement element) {
		return (DRIVER != null && element != null) ? DRIVER.switchTo().frame(element) : null;
	}

	/**
	 * 
	 * @param locator
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToFrame(By locator) {
		return switchToFrame(findElement(locator));
	}

	/**
	 * 
	 * @param locator
	 */
	public final void switchToFrameIfExist(By locator) {
		if (locator != null && isElementPresent(locator)) {
			switchToFrame(locator);
		}
	}

	/**
	 * 
	 * @param frameNameOrId
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToFrame(String frameNameOrId) {
		return (DRIVER != null && frameNameOrId != null) ? DRIVER.switchTo().frame(frameNameOrId) : null;
	}

	/**
	 * 
	 * @param frameIndex
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToFrame(int frameIndex) {
		return (DRIVER != null) ? DRIVER.switchTo().frame(frameIndex) : null;
	}

	/**
	 * 
	 * @return {@link WebElement}
	 */
	public final WebElement switchToActiveElement() {
		return (DRIVER != null) ? DRIVER.switchTo().activeElement() : null;
	}

	/**
	 * 
	 * @return {@link Actions}
	 */
	public final Actions getActionsInstance() {
		return (DRIVER != null) ? new Actions(DRIVER) : null;
	}

	/**
	 * 
	 * @param element
	 */
	public final void mouseHoverToElement(WebElement element) {
		Actions act = getActionsInstance();
		if (act != null && element != null) {
			act.moveToElement(element).build().perform();
		}
	}

	/**
	 * 
	 * @param locator
	 */
	public final void mouseHoverToElement(By locator) {
		mouseHoverToElement(findElement(locator));
	}

	/**
	 * 
	 */
	public final void pressEnter() {
		Actions act = getActionsInstance();
		if (act != null) {
			act.sendKeys(Keys.ENTER);
		}
	}

	/**
	 * 
	 * @param nameOrHandle
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToWindow(String nameOrHandle) {
		return (DRIVER != null && nameOrHandle != null) ? DRIVER.switchTo().window(nameOrHandle) : null;
	}

	/**
	 * 
	 * @param nameOrHandle
	 * @return {@link String}
	 */
	public final String getWindowTitle(String nameOrHandle) {
		WebDriver driverValue = switchToWindow(nameOrHandle);
		return (driverValue != null) ? driverValue.getTitle() : null;
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String getWindowId() {
		return (DRIVER != null) ? DRIVER.getWindowHandle() : null;
	}

	/**
	 * 
	 * @return {@link List}
	 */
	public final List<String> getWindowsId() {
		return (DRIVER != null) ? new ArrayList<>(DRIVER.getWindowHandles()) : new ArrayList<String>();
	}

	/**
	 * 
	 * @param windowTitle
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToWindowByTitle(String windowTitle) {
		String currentWindowId = getWindowId();
		for (String windowId : getWindowsId()) {
			if (CommonUtils.compareContains(getWindowTitle(windowId), windowTitle)) {
				currentWindowId = windowId;
				break;
			}
		}
		return switchToWindow(currentWindowId);
	}

	/**
	 * 
	 * @param windowURL
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToWindowByURL(String windowURL) {
		String currentWindowId = getWindowId();
		for (String windowId : getWindowsId()) {
			switchToWindow(windowId);
			if (CommonUtils.compareContains(getCurrentURL(), windowURL)) {
				currentWindowId = windowId;
				break;
			}
		}
		return switchToWindow(currentWindowId);
	}

	/**
	 * 
	 * @return {@link List}
	 */
	public final List<String> getWindowsTitle() {
		return getWindowsId().stream().map(this::switchToWindow).filter(Objects::nonNull).map(this::getTitle)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return {@link List}
	 */
	public final List<String> getWindowsURL() {
		return getWindowsId().stream().map(this::switchToWindow).filter(Objects::nonNull).map(this::getWindowURL)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 */
	public final void openNewTab() {
		if (DRIVER != null) {
			DRIVER.switchTo().newWindow(WindowType.TAB);
		}
	}

	/**
	 * 
	 * @return {@link String}
	 */
	public final String openNewTabInBrowser() {
		((JavascriptExecutor) DRIVER).executeScript("window.open('about:blank','_blank');");
		switchToWindowByURL("about:blank");
		return getWindowId();
	}

	/**
	 * 
	 * @return {@link Integer}
	 */
	public final int getTabCount() {
		return DRIVER.getWindowHandles().size();
	}

	/**
	 * 
	 * @param index
	 * @return {@link WebDriver}
	 */
	public final WebDriver switchToTab(int index) {
		try {
			return switchToWindow(getWindowsId().get(index));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param index
	 */
	public final void switchToTabAndClose(int index) {
		closeWebDriverInstance(switchToTab(index));
	}

	/**
	 * 
	 * @param windowTitle
	 */
	public final void switchToTabAndClose(String windowTitle) {
		closeWebDriverInstance(switchToWindowByTitle(windowTitle));
	}

	/**
	 * 
	 * @param element
	 * @param cssAttribute
	 * @return {@link String}
	 */
	public final String getCSSValue(WebElement element, String cssAttribute) {
		return (element != null) ? element.getCssValue(cssAttribute) : null;
	}

	/**
	 * 
	 * @param locator
	 * @param cssAttribute
	 * @return {@link String}
	 */
	public final String getCSSValue(By locator, String cssAttribute) {
		return getCSSValue(findElement(locator), cssAttribute);
	}

	/**
	 * 
	 * @param locator
	 * @param textValue
	 * @return {@link Integer}
	 */
	public final int getIndexBasedOnText(By locator, String textValue) {
		int count = 0;
		for (WebElement element : findElements(locator)) {
			count++;
			if (CommonUtils.compareEqualsIgnoreCase(getText(element), textValue)) {
				break;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param locator
	 * @param attribute
	 * @param attributeValue
	 * @return {@link Integer}
	 */
	public final int getIndexBasedOnAttribute(By locator, String attribute, String attributeValue) {
		int count = 0;
		for (WebElement element : findElements(locator)) {
			count++;
			if (CommonUtils.compareEqualsIgnoreCase(getAttribute(element, attribute), attributeValue)) {
				break;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param destFolderPath
	 * @param fileName
	 */
	public final void startRecording(String destFolderPath, String fileName) {
		try {
			recorder = new ATUTestRecorder(destFolderPath, fileName, false);
			recorder.start();
		} catch (ATUTestRecorderException e) {
		}
	}

	/**
	 * 
	 */
	public final void stopRecording() {
		if (CommonUtils.isNotNull(recorder)) {
			try {
				recorder.stop();
			} catch (ATUTestRecorderException e) {
			}
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		pageLoadTime();
	}

	@AfterMethod
	public void afterMethod() {
		pageLoadTime();
	}
}
