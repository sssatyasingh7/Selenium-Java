package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils implements Form {

	private static WebDriver DRIVER;
	private static SeleniumUtils form;
	// private static TestEnvironment testEnv;
	private String defaultURL;
	private String userName;
	private String password;

	private SeleniumUtils() {
		TestEnvironment testEnv = TestEnvironment.INSTANCE;
		this.defaultURL = testEnv.getDefaultURL();
		this.userName = testEnv.getUserName();
		this.password = testEnv.getPassword();
	}

	public static final SeleniumUtils getInstance() {
		form = form == null ? (form = new SeleniumUtils()) : form;
		return form;
	}

	public static final void setWebDriverInstance(WebDriver driver) {
		DRIVER = driver;
	}

	public final String getDefaultURL() {
		return this.defaultURL;
	}

	public final String getUserName() {
		return this.userName;
	}

	public final String getPassword() {
		return this.password;
	}

	@Override
	public WebDriver getWebDriverInstance() {
		return DRIVER;
	}

	public final void quitWebDriverInstance() {
		if (DRIVER != null) {
			DRIVER.quit();
		}
	}

	public final void closeWebDriverInstance() {
		if (DRIVER != null) {
			DRIVER.close();
		}
	}

	public final void threadSleep(long milliSeconds) {
		CommonUtils.threadSleep(milliSeconds);
	}

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

	private void waitAngularJs(long seconds) {
		waitUntilAngularReady(seconds);
		waitUntilJSReady(seconds);
	}

	public JavascriptExecutor getJavaScriptExecutorInstance() {
		return (DRIVER != null) ? (JavascriptExecutor) DRIVER : null;
	}

	public final void sendKeysUsingJS(By locator, String inputText) {
		WebElement element = findElement(locator);
		JavascriptExecutor js = getJavaScriptExecutorInstance();
		if (element != null && js != null) {
			js.executeScript(String.format("arguments[0].value='%s'", inputText), element);
		}
	}

	public final void pageLoadTime(long seconds) {
		if (DRIVER != null) {
			try {
				waitAngularJs(seconds);
			} catch (Exception e) {
			}
		}
	}

	public final void pageLoadTime() {
		pageLoadTime(100);
	}

	public final void get(String urlValue) {
		if (DRIVER != null) {
			DRIVER.get(urlValue);
		}
	}

	public final String getCurrentURL() {
		return DRIVER != null ? DRIVER.getCurrentUrl() : null;
	}

	public final String getWindowURL(WebDriver driver) {
		return driver != null ? driver.getCurrentUrl() : null;
	}

	public final String getTitle() {
		return DRIVER != null ? DRIVER.getTitle() : null;
	}

	public final void scrollToView(WebElement element) {
		JavascriptExecutor js = getJavaScriptExecutorInstance();
		if (js != null && element != null) {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	public final void scrollToView(By locator) {
		scrollToView(findElement(locator));
	}

	public final WebElement elementToBePresent(By locator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return DRIVER.findElement(locator);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public WebElement findElement(By locator) {
		return elementToBePresent(locator, 3);
	}

	public final void nestedElementToBePresent(By parentLocator, By childLocator, long seconds) {
		WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentLocator, childLocator));
	}

	public final WebElement nestedElementToBePresent(WebElement parentElement, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, childLocator));
			return parentElement.findElement(childLocator);
		} catch (Exception e) {
			return null;
		}
	}

	public final List<WebElement> findNestedElements(WebElement parentElement, By childLocator) {
		try {
			return parentElement.findElements(childLocator);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public final List<WebElement> findNestedElements(By parentLocator, By childLocator) {
		return findNestedElements(findElement(parentLocator), childLocator);
	}

	public final String getText(WebElement element) {
		return element == null ? null : CommonUtils.returnValue(element.getText());
	}

	public final String getText(By locator) {
		return getText(findElement(locator));
	}

	public final List<String> getNestedElementsGetText(WebElement parentElement, By childLocator) {
		return findNestedElements(parentElement, childLocator).stream().map(this::getText).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public final List<String> getNestedElementsGetText(By parentLocator, By childLocator) {
		return getNestedElementsGetText(findElement(parentLocator), childLocator);
	}

	public final void nestedElementToBeVisible(By parentLocator, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentLocator, childLocator));
		} catch (Exception e) {
		}
	}

	public final void nestedElementToBeVisible(WebElement parentElement, By childLocator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentElement, childLocator));
		} catch (Exception e) {
		}
	}

	public final void elementToBeClickable(WebElement element, long seconds) {
		if (element != null) {
			scrollToView(element);
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
	}

	public final void elementToBeClickable(By locator, long seconds) {
		if (locator != null) {
			scrollToView(locator);
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		}
	}

	public final void elementToBeVisible(WebElement element, long seconds) {
		if (DRIVER != null && element != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	}

	public final void elementToBeVisible(By locator, long seconds) {
		if (DRIVER != null && locator != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
	}

	public final void elementToBeInvisible(WebElement element, long seconds) {
		if (DRIVER != null && element != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOf(element));
		}
	}

	public final void elementToBeInvisible(By locator, long seconds) {
		if (DRIVER != null && locator != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
	}
	
	

}
