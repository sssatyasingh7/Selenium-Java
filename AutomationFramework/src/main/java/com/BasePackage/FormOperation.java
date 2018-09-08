package com.BasePackage;

import org.openqa.selenium.By;

public class FormOperation extends SeleniumCode{

	//private final SeleniumCode selCode = new SeleniumCode();
	
	private static String setInputFieldValue = "";
	private static String getInputFieldValue = "";
	private static String setRefFieldValue = "";
	private static String getRefFieldValue = "";
	private static String setSelFieldValue = "";
	private static String getSelFieldValue = "";
	private static String setCheckboxValue = "";
	private static String getCheckboxValue = "";
	private static String setTextAreaFieldValue = "";
	private static String getTextAreaFieldValue = "";
	private static String setRadioButton = "";
	private static String getRadioButton = "";
	
	
	public void setTextField(By byValue, String inputText) {
		//System.out.println("::>> Selenium Code Object:: "+selCode+" <<::");
		sendKeys(byValue, inputText);
		pauseMe(10);;
	}

}
