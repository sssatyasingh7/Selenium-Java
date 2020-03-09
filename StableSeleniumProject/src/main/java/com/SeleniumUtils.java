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

	public final void closeWebDriverInstance(WebDriver driver) {
		if (driver != null) {
			driver.close();
		}
	}

	public final void closeWebDriverInstance() {
		closeWebDriverInstance(DRIVER);
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

	public final String getTitle(WebDriver driver) {
		return driver != null ? driver.getTitle() : null;
	}

	public final String getTitle() {
		return getTitle(DRIVER);
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

	public final WebElement findElement(By locator, long seconds) {
		return (locator == null) ? null : elementToBePresent(locator, seconds);
	}

	@Override
	public final WebElement findElement(By locator) {
		return (locator == null) ? null : elementToBePresent(locator, 3);
	}

	public final WebElement findElementWithoutWait(By locator) {
		try {
			return DRIVER.findElement(locator);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public final WebElement findNestedElement(WebElement parentElement, By childLocator) {
		return nestedElementToBePresent(parentElement, childLocator, 2);
	}

	public final WebElement findNestedElement(By parentLocator, By childLocator) {
		return nestedElementToBePresent(findElement(parentLocator), childLocator, 2);
	}

	public final String getNestedElementTextValue(WebElement parentElement, By childLocator) {
		return getText(findNestedElement(parentElement, childLocator));
	}

	public final String getNestedElementTextValue(By parentLocator, By childLocator) {
		return getNestedElementTextValue(findElement(parentLocator), childLocator);
	}

	public final boolean isElementPresent(By locator) {
		return findElementWithoutWait(locator) != null;
	}

	public final List<WebElement> findElementsWithoutWait(By locator) {
		return DRIVER.findElements(locator);
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
		return (element == null) ? null : CommonUtils.returnValue(element.getText());
	}

	public final String getText(By locator) {
		return getText(findElement(locator));
	}

	public final String getAttribute(WebElement element, String attribute) {
		return (element != null && attribute != null) ? CommonUtils.returnValue(element.getAttribute(attribute)) : null;
	}

	public final String getAttribute(By locator, String attribute) {
		return (locator != null && attribute != null) ? getAttribute(findElement(locator), attribute) : null;
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

	public final void elementWithTextToBeInvisible(By locator, String textValue, long seconds) {
		if (DRIVER != null && locator != null && textValue != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, textValue));
		}
	}

	public final void elementWithTextToBePresent(By locator, String textValue, long seconds) {
		if (DRIVER != null && locator != null && textValue != null) {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, textValue));
		}
	}

	public final void waitUntilURLContains(String urlValue, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.urlContains(urlValue));
		} catch (Exception e) {
		}
	}

	public final void pageRefresh() {
		try {
			DRIVER.navigate().refresh();
		} catch (Exception e) {
		}
	}

	public final void pageReload() {
		pageRefresh();
	}

	public final void pressEnter(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ENTER);
			} catch (Exception e) {
			}
		}
	}

	public final void pressEnter(By locator) {
		pressEnter(findElement(locator));
	}

	public final void clear(WebElement element) {
		if (element != null) {
			element.clear();
		}
	}

	public final void clear(By locator) {
		clear(findElement(locator));
	}

	public final void clearAndEnter(WebElement element) {
		if (element != null) {
			clear(element);
			pressEnter(element);
		}
	}

	public final void clearAndEnter(By locator) {
		clearAndEnter(findElement(locator));
	}

	public final void pressTab(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.TAB);
			} catch (Exception e) {
			}
		}
	}

	public final void pressTab(By locator) {
		pressTab(findElement(locator));
	}

	public final void clearAndTab(WebElement element) {
		if (element != null) {
			clear(element);
			pressTab(element);
		}
	}

	public final void clearAndTab(By locator) {
		clearAndTab(findElement(locator));
	}

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

	public final void click(By locator) {
		click(findElement(locator));
	}

	public final void clickWithoutWait(By locator) {
		click(findElementWithoutWait(locator));
	}

	public final void clickOnNestedElement(WebElement parentElement, By childLocator) {
		if (parentElement != null) {
			click(findNestedElement(parentElement, childLocator));
		}
	}

	public final void clickOnNestedElement(By parentLocator, By childLocator) {
		clickOnNestedElement(findElement(parentLocator), childLocator);
	}

	public final void clickWithoutScroll(WebElement element) {
		if (element != null) {
			try {
				element.click();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public final void clickWithoutScroll(By locator) {
		clickWithoutScroll(findElement(locator));
	}

	public final void appendText(WebElement element, String keysToSend) {
		if (element != null && keysToSend != null) {
			try {
				element.sendKeys(keysToSend);
			} catch (Exception e) {
			}
		}
	}

	public final void appendText(By locator, String keysToSend) {
		if (locator != null && keysToSend != null) {
			appendText(findElement(locator), keysToSend);
		}
	}

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

	public final void sendKeys(By locator, String keysToSend) {
		if (locator != null && keysToSend != null) {
			sendKeys(findElement(locator), keysToSend);
		}
	}

	public final void pressDownArrow(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ARROW_DOWN);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public final void pressDownArrow(By locator) {
		pressDownArrow(findElement(locator));
	}

	public final void pressUpArrow(WebElement element) {
		if (element != null) {
			try {
				element.sendKeys(Keys.ARROW_UP);
			} catch (Exception e) {
			}
		}
	}

	public final void pressUpArrow(By locator) {
		pressDownArrow(findElement(locator));
	}

	public final void switchToDefaultWindow() {
		if (DRIVER != null) {
			DRIVER.switchTo().defaultContent();
		}
	}

	public final void selectByIndex(WebElement element, int index) {
		if (element != null) {
			Select sel = new Select(element);
			sel.selectByIndex(index);
		}
	}

	public final void selectByIndex(By locator, int index) {
		if (locator != null) {
			selectByIndex(findElement(locator), index);
		}
	}

	public final void selectByValue(WebElement element, String value) {
		if (element != null && value != null) {
			Select sel = new Select(element);
			sel.selectByValue(value);
		}
	}

	public final void selectByValue(By locator, String value) {
		if (locator != null && value != null) {
			selectByValue(findElement(locator), value);
		}
	}

	public final void selectByVisibleText(WebElement element, String visibleText) {
		if (element != null && visibleText != null) {
			Select sel = new Select(element);
			sel.selectByVisibleText(visibleText);
		}
	}

	public final void selectByVisibleText(By locator, String visibleText) {
		if (locator != null && visibleText != null) {
			selectByVisibleText(findElement(locator), visibleText);
		}
	}

	public final void submit(WebElement element) {
		if (element != null) {
			element.submit();
		}
	}

	public final void submit(By locator) {
		submit(findElement(locator));
	}

	public final List<WebElement> elementsToBePresent(By locator, long seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(DRIVER, seconds);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
		}
		return DRIVER.findElements(locator);
	}

	public final List<WebElement> findElements(By locator) {
		return elementsToBePresent(locator, 3);
	}

	public final List<String> getListOfText(List<WebElement> elements) {
		return elements.stream().map(this::getText).filter(Objects::nonNull).collect(Collectors.toList());
	}

	public final List<String> getListOfText(By locator) {
		return getListOfText(findElements(locator));
	}

	public final List<String> getListOfTextWithoutWait(By locator) {
		return getListOfText(findElementsWithoutWait(locator));
	}

	public final List<String> getListOfAttributeValues(List<WebElement> elements, String attributeName) {
		return elements.stream().map(element -> getAttribute(element, attributeName)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public final List<String> getListOfAttributeValues(By locator, String attributeName) {
		return getListOfAttributeValues(findElements(locator), attributeName);
	}

	public final List<String> getListOfAttributeValuesWithoutWait(By locator, String attributeName) {
		return getListOfAttributeValues(findElementsWithoutWait(locator), attributeName);
	}

	public final boolean captureScreenshot(String destFilePath) {
		try {
			FileUtils.copyFile(((TakesScreenshot) DRIVER).getScreenshotAs(OutputType.FILE), new File(destFilePath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public final void captureFullPageScreenshot(String destFilePath) {
		try {
			ImageIO.write(new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DRIVER)
					.getImage(), "PNG", new File(destFilePath));
		} catch (Exception e) {
		}
	}

	public final Alert getAlertInstance() {
		try {
			threadSleep(200);
			return DRIVER.switchTo().alert();
		} catch (NoAlertPresentException ae) {
			return null;
		}
	}

	public final boolean isAlertExist() {
		if (getAlertInstance() != null) {
			switchToDefaultWindow();
			return true;
		} else {
			return false;
		}
	}

	public final void acceptAlert() {
		if (DRIVER != null) {
			DRIVER.switchTo().alert().accept();
		}
	}

	public final void acceptAlertIfExist() {
		Alert alert = getAlertInstance();
		if (alert != null) {
			alert.accept();
		}
	}

	public final void dismissAlert() {
		if (DRIVER != null) {
			DRIVER.switchTo().alert().dismiss();
		}
	}

	public final void dismissAlertIfExist() {
		Alert alert = getAlertInstance();
		if (alert != null) {
			alert.dismiss();
		}
	}

	public final WebDriver switchToFrame(WebElement element) {
		return (DRIVER != null && element != null) ? DRIVER.switchTo().frame(element) : null;
	}

	public final WebDriver switchToFrame(By locator) {
		return switchToFrame(findElement(locator));
	}

	public final void switchToFrameIfExist(By locator) {
		if (locator != null && isElementPresent(locator)) {
			switchToFrame(locator);
		}
	}

	public final WebDriver switchToFrame(String frameNameOrId) {
		return (DRIVER != null && frameNameOrId != null) ? DRIVER.switchTo().frame(frameNameOrId) : null;
	}

	public final WebDriver switchToFrame(int frameIndex) {
		return (DRIVER != null) ? DRIVER.switchTo().frame(frameIndex) : null;
	}

	public final WebElement switchToActiveElement() {
		return (DRIVER != null) ? DRIVER.switchTo().activeElement() : null;
	}

	public final Actions getActionsInstance() {
		return (DRIVER != null) ? new Actions(DRIVER) : null;
	}

	public final void mouseHoverToElement(WebElement element) {
		Actions act = getActionsInstance();
		if (act != null && element != null) {
			act.moveToElement(element).build().perform();
		}
	}

	public final void mouseHoverToElement(By locator) {
		mouseHoverToElement(findElement(locator));
	}

	public final void pressEnter() {
		Actions act = getActionsInstance();
		if (act != null) {
			act.sendKeys(Keys.ENTER);
		}
	}

	public final WebDriver switchToWindow(String nameOrHandle) {
		return (DRIVER != null && nameOrHandle != null) ? DRIVER.switchTo().window(nameOrHandle) : null;
	}

	public final String getWindowTitle(String nameOrHandle) {
		WebDriver driverValue = switchToWindow(nameOrHandle);
		return (driverValue != null) ? driverValue.getTitle() : null;
	}

	public final String getWindowId() {
		return (DRIVER != null) ? DRIVER.getWindowHandle() : null;
	}

	public final List<String> getWindowsId() {
		return (DRIVER != null) ? new ArrayList<>(DRIVER.getWindowHandles()) : new ArrayList<String>();
	}

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

	public final List<String> getWindowsTitle() {
		return getWindowsId().stream().map(this::switchToWindow).filter(Objects::nonNull).map(this::getTitle)
				.collect(Collectors.toList());
	}

	public final List<String> getWindowsURL() {
		return getWindowsId().stream().map(this::switchToWindow).filter(Objects::nonNull).map(this::getWindowURL)
				.collect(Collectors.toList());
	}

	public final void openNewTab() {
		if (DRIVER != null) {
			DRIVER.switchTo().newWindow(WindowType.TAB);
		}
	}

	public final String openNewTabInBrowser() {
		((JavascriptExecutor) DRIVER).executeScript("window.open('about:blank','_blank');");
		switchToWindowByURL("about:blank");
		return getWindowId();
	}

	public final int getTabCount() {
		return DRIVER.getWindowHandles().size();
	}

	public final WebDriver switchToTab(int index) {
		try {
			return switchToWindow(getWindowsId().get(index));
		} catch (Exception e) {
			return null;
		}
	}

	public final void switchToTabAndClose(int index) {
		closeWebDriverInstance(switchToTab(index));
	}

	public final void switchToTabAndClose(String windowTitle) {
		closeWebDriverInstance(switchToWindowByTitle(windowTitle));
	}

	public final String getCSSValue(WebElement element, String cssAttribute) {
		return (element != null) ? element.getCssValue(cssAttribute) : null;
	}

	public final String getCSSValue(By locator, String cssAttribute) {
		return getCSSValue(findElement(locator), cssAttribute);
	}

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

	public final void startRecording(String destFolderPath, String fileName) {
		try {
			recorder = new ATUTestRecorder(destFolderPath, fileName, false);
			recorder.start();
		} catch (ATUTestRecorderException e) {
		}
	}

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
