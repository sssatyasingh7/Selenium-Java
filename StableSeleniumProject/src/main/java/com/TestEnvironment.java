package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

enum TestEnvironment {
INSTANCE;
	//private static TestEnvironment testEnv;
	private final Properties properties = new Properties();

	private TestEnvironment() {
		try (FileInputStream fis = new FileInputStream(new File("configure.properties"))) {
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("***'Configure.properties' file not found in prohect structure***");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static final TestEnvironment getInstance() { if (testEnv == null) {
	 * testEnv = new TestEnvironment(); } return testEnv; }
	 */

	public final String getPropertiesValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public final String getPropertiesValue(String key) {
		return getPropertiesValue(key, null);
	}

	public final String getUserName() {
		return getPropertiesValue("USER_NAME");
	}

	public final String getPassword() {
		return getPropertiesValue("PASSWORD");
	}

	public final String getBrowserName() {
		return getPropertiesValue("BROWSER_NAME", "ie").toLowerCase();
	}

	public final String getDefaultURL() {
		String defaultUrl = getPropertiesValue("PRIMARY_URL_VALUE", "https://google.co.in");
		if (defaultUrl.endsWith("/")) {
			defaultUrl = defaultUrl.substring(0, defaultUrl.length() - 1);
		}
		return defaultUrl;
	}
}
