package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ReportUtils {

	private static ExtentReports REPORT_INSTANCE;
	private static final String REPORT_DIR_PATH = FileHelperUtils.getReportFolderPath();
	private static final String SCREENSHOT_PATH = REPORT_DIR_PATH + "\\screenshot.png";
	private static ExtentTest LOGGER;
	private static ReportUtils REPORT_UTILS;
	// private static final SeleniumUtils SELENIUM_UTILS = new SeleniumUtils();
	private static String LOGGER_TEST_FILE_PATH;
	private static final List<String> TEST_CASES = new ArrayList<String>();

	private static final String REPORT_NAME = "";

	private ReportUtils() {
		FileHelperUtils.createFolder(REPORT_DIR_PATH);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				REPORT_DIR_PATH + "\\" + CommonUtils.replaceBlankSpaceWithChar(REPORT_NAME, "_") + ".html");
		REPORT_INSTANCE = new ExtentReports();
		REPORT_INSTANCE.attachReporter(htmlReporter);
		htmlReporter.config().setReportName(REPORT_NAME);
		htmlReporter.config().setDocumentTitle("Execution Report");
		htmlReporter.config().setTimeStampFormat("yyyy-MMM-dd HH:mm:ss");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setEncoding("utf-8");
		reportFlush();
	}

	private static final void reportFlush() {
		if (REPORT_INSTANCE != null) {
			REPORT_INSTANCE.flush();
		}
	}

	public static final ReportUtils getInstance() {
		return (REPORT_UTILS == null) ? (REPORT_UTILS = new ReportUtils()) : REPORT_UTILS;
	}

	public final void createTestLogger() {
		if (REPORT_INSTANCE != null) {
			for (StackTraceElement str : Arrays.asList(Thread.currentThread().getStackTrace())) {
				String loggerName = str.getClassName();
				loggerName = loggerName.substring(loggerName.lastIndexOf(".") + 1);
				if (TEST_CASES.isEmpty() || !TEST_CASES.contains(loggerName)) {
					TEST_CASES.add(loggerName);
					LOGGER = REPORT_INSTANCE.createTest(loggerName);
					reportFlush();
					try {
						LOGGER_TEST_FILE_PATH = REPORT_DIR_PATH + "\\" + loggerName + ".txt";
						FileHelperUtils.clearFile(LOGGER_TEST_FILE_PATH);
					} catch (Exception e) {
					}
				}
				break;
			}
		}
	}

	private static final MediaEntityModelProvider getMediaEntityBuilderInstance() {
		try {
			// SELENIUM_UTILS.captureScreenshot();
			return MediaEntityBuilder
					.createScreenCaptureFromBase64String(
							DatatypeConverter.printBase64Binary(CommonUtils.convertScreenshotTo64Bits(SCREENSHOT_PATH)))
					.build();
		} catch (IOException e) {
			return null;
		}
	}

	private static final String messageFormatter(String message) {
		message = message.replaceAll("\n", "<br>&nbsp;&nbsp;&nbsp;&#10146;</b>&nbsp;&nbsp;");
		message = message.replaceAll(">>>", "<br>");
		return message.replaceAll(">>", "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10159;</b>&nbsp;&nbsp;");
	}

	private static final String messageFormatter(List<String> messages) {
		String message = messages.get(0);
		for (int i = 1; i < messages.size() - 1; i++) {
			message = message.replaceAll("\n", "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#10159;</b>&nbsp;&nbsp;");
			message = "\n" + messages.get(i);
		}
		return message.replaceAll("\n", "<br>&nbsp;&nbsp;&nbsp;&#10146;</b>&nbsp;&nbsp;");
	}

	private static final void writeLogsToLoggerFile(String message) {
		TextUtils.writeToTextFile(LOGGER_TEST_FILE_PATH, "\n \n" + message);
	}

	private final void setInfoLogs(String message, boolean isScreenshot) {
		if (LOGGER != null && message != null) {
			if (isScreenshot) {
				LOGGER.log(Status.INFO, "<font color='Sienna'> &#10158; " + messageFormatter(message) + "</font>",
						getMediaEntityBuilderInstance());
			} else {
				LOGGER.log(Status.INFO, "<font color='Sienna'> &#10158; " + messageFormatter(message) + "</font>");
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.INFO::\n" + message + "\n***");
	}

	private final void setInfoLogs(List<String> message) {
		if (LOGGER != null && !message.isEmpty()) {
			LOGGER.log(Status.INFO, "<font color='Sienna'> &#10158; " + messageFormatter(message) + "</font>");
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.INFO::\n" + message + "\n***");
	}

	private final void setPassLogs(String message, Object actual, Object expected, boolean isScreenshot) {
		if (LOGGER != null) {
			if ((actual == null || actual == "") && (expected == null || expected == "")) {
				if (isScreenshot) {
					LOGGER.log(Status.PASS, "<b><u><font color='green'>" + messageFormatter(message) + "</font></b>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.PASS, "<b><u><font color='green'>" + messageFormatter(message) + "</font></b>");
				}
			} else if (actual != null && expected == null) {
				LOGGER.log(Status.PASS,
						"<b><u><font color='green'>" + messageFormatter(message) + " ---  " + actual + "</font></b>");
			} else {
				if (isScreenshot) {
					LOGGER.log(Status.PASS,
							"<section class='left'><b><u><font color='green'> " + messageFormatter(message)
									+ " --- is working as expected </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.PASS,
							"<section class='left'><b><u><font color='green'> " + messageFormatter(message)
									+ " --- is working as expected </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>");
				}
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.PASS::\n" + message + "\n Act. Value:: " + actual + "\n Exp. Value:: "
				+ expected + "\n***\n");
	}

	private final <E> void setPassLogs(String message, List<E> actual, List<E> expected, boolean isScreenshot) {
		if (LOGGER != null) {
			if (actual.isEmpty() && expected.isEmpty()) {
				if (isScreenshot) {
					LOGGER.log(Status.PASS, "<b><u><font color='green'>" + messageFormatter(message) + "</font></b>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.PASS, "<b><u><font color='green'>" + messageFormatter(message) + "</font></b>");
				}
			} else if (!actual.isEmpty() && !expected.isEmpty()) {
				LOGGER.log(Status.PASS,
						"<b><u><font color='green'>" + messageFormatter(message) + " ---  " + actual + "</font></b>");
			} else {
				if (isScreenshot) {
					LOGGER.log(Status.PASS,
							"<section class='left'><b><u><font color='green'> " + messageFormatter(message)
									+ " --- is working as expected </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.PASS,
							"<section class='left'><b><u><font color='green'> " + messageFormatter(message)
									+ " --- is working as expected </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>");
				}
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.PASS::\n" + message + "\n Act. Value:: " + actual + "\n Exp. Value:: "
				+ expected + "\n***\n");
	}

	private final void setFailLogs(String message, Object actual, Object expected, boolean isScreenshot) {
		if (LOGGER != null) {
			if ((actual == null || actual == "") && (expected == null || expected == "")) {
				if (isScreenshot) {
					LOGGER.log(Status.FAIL, "<b><u><font color='red'>" + messageFormatter(message) + "</font></b>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.FAIL, "<b><u><font color='red'>" + messageFormatter(message) + "</font></b>");
				}
			} else if (actual != null && expected == null) {
				LOGGER.log(Status.FAIL,
						"<b><u><font color='red'>" + messageFormatter(message) + " ---  " + actual + "</font></b>");
			} else {
				if (isScreenshot) {
					LOGGER.log(Status.FAIL,
							"<section class='left'><b><u><font color='red'> " + messageFormatter(message)
									+ " --- is FAIL </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.FAIL,
							"<section class='left'><b><u><font color='red'> " + messageFormatter(message)
									+ " --- is failed </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>");
				}
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.FAIL::\n" + message + "\n Act. Value:: " + actual + "\n Exp. Value:: "
				+ expected + "\n***\n");
	}

	private final <E> void setFailLogs(String message, List<E> actual, List<E> expected, boolean isScreenshot) {
		if (LOGGER != null) {
			if (actual.isEmpty() && expected.isEmpty()) {
				if (isScreenshot) {
					LOGGER.log(Status.FAIL, "<b><u><font color='red'>" + messageFormatter(message) + "</font></b>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.FAIL, "<b><u><font color='red'>" + messageFormatter(message) + "</font></b>");
				}
			} else if (!actual.isEmpty() && !expected.isEmpty()) {
				LOGGER.log(Status.FAIL,
						"<b><u><font color='red'>" + messageFormatter(message) + " ---  " + actual + "</font></b>");
			} else {
				if (isScreenshot) {
					LOGGER.log(Status.FAIL,
							"<section class='left'><b><u><font color='red'> " + messageFormatter(message)
									+ " --- is failed </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>",
							getMediaEntityBuilderInstance());
				} else {
					LOGGER.log(Status.FAIL,
							"<section class='left'><b><u><font color='red'> " + messageFormatter(message)
									+ " --- is failed </font><br>&nbsp;&nbsp;Act. Value: </b> " + actual
									+ "<br><b>&nbsp;&nbsp;Exp. Value: </b>" + expected + "</section>");
				}
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** STATUS.FAIL::\n" + message + "\n Act. Value:: " + actual + "\n Exp. Value:: "
				+ expected + "\n***\n");
	}

	public final void setErrorLogs(List<String> errors, boolean isScreenshot) {
		if (LOGGER != null && errors.size() > 0) {
			if (isScreenshot) {
				LOGGER.log(Status.ERROR,
						"<b><u><font color='Brown'>Error(s) are:: </font></u></b><font color='#ff6347'>"
								+ messageFormatter(errors) + "</font>",
						getMediaEntityBuilderInstance());
			} else {
				LOGGER.log(Status.ERROR,
						"<b><u><font color='Brown'>Error(s) are:: </font></u></b><font color='#ff6347'>"
								+ messageFormatter(errors) + "</font>");
			}
			reportFlush();
		}
		writeLogsToLoggerFile("*** ERROR.LOGS::\n" + errors + "\n***\n");
	}

	private final String formMessage(Object methodName, Object message) {
		return methodName + " Operation " + message;
	}

	public final void assertPass(final String methodName, Object message, Object actual, Object expected) {
		setPassLogs(formMessage(methodName, message), actual, expected, false);
	}

	public final <E> void assertPass(final String methodName, Object message, List<E> actual, List<E> expected) {
		setPassLogs(formMessage(methodName, message), actual, expected, false);
	}

	public final void assertFail(final String methodName, String message, Object actual, Object expected) {
		message = formMessage(methodName, message);
		setFailLogs(message, actual, expected, true);
		if (CommonUtils.isNull(actual) && CommonUtils.isNull(expected))
			Assert.fail(message);
		else
			Assert.fail(message + "\nAct. Val. is:: " + actual + "\nExp. Val. is:: " + expected);
	}

	public final <E> void assertFail(final String methodName, String message, List<E> actual, List<E> expected) {
		message = formMessage(methodName, message);
		setFailLogs(message, actual, expected, true);
		if (CommonUtils.isListEmpty(actual) && CommonUtils.isListEmpty(expected))
			Assert.fail(message);
		else
			Assert.fail(message + "\nAct. Val. is:: " + actual + "\nExp. Val. is:: " + expected);
	}

	public final void verifyPass(final String methodName, Object message, Object actual, Object expected) {
		setPassLogs(formMessage(methodName, message), actual, expected, false);
	}

	public final <E> void verifyPass(final String methodName, Object message, List<E> actual, List<E> expected) {
		setPassLogs(formMessage(methodName, message), actual, expected, false);
	}

	public final void verifyFail(final String methodName, String message, Object actual, Object expected) {
		setFailLogs(formMessage(methodName, message), actual, expected, true);
	}

	public final <E> void verifyFail(final String methodName, String message, List<E> actual, List<E> expected) {
		setFailLogs(formMessage(methodName, message), actual, expected, true);
	}
}
