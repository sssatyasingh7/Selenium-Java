package com;

import java.util.List;
import java.util.Map;

public class AssertUtils {
	private static ReportUtils reportUtils;
	private static final String EQUAL_OPER = "Equals";
	private static final String NOT_EQUAL_OPER = "Not Equals";
	private static final String EQUALS_IGNORE_CASE_OPER = "EqualsIgnoreCase";
	private static final String NOT_EQUALS_IGNORE_CASE_OPER = "Not EqualsIgnoreCase";
	private static final String CONTAINS_OPER = "Contains";
	private static final String NOT_CONTAINS_OPER = "Not Contains";
	private static final String CONTAINS_ALL_OPER = "Contains All";
	private static final String NOT_CONTAINS_ALL_OPER = "Not Contains All";

	private AssertUtils() {
	}

	public static final void equals(String message, String actual, String expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUAL_OPER, message, actual, expected);
	}

	public static final void equals(String message, Object actual, Object expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUAL_OPER, message, actual, expected);
	}

	public static final void equals(String message, boolean actual, boolean expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUAL_OPER, message, actual, expected);
	}

	public static final <E> void equals(String message, List<E> actual, List<E> expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUAL_OPER, message, actual, expected);
	}

	public static final <K, V> void equals(String message, Map<K, V> actual, Map<K, V> expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUAL_OPER, message, actual, expected);
	}

	public static final void notEquals(String message, String actual, String expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUAL_OPER, message, actual, expected);
	}

	public static final void notEquals(String message, Object actual, Object expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUAL_OPER, message, actual, expected);
	}

	public static final void notEquals(String message, boolean actual, boolean expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUAL_OPER, message, actual, expected);
	}

	public static final <E> void notEquals(String message, List<E> actual, List<E> expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUAL_OPER, message, actual, expected);
	}

	public static final <K, V> void nonEquals(String message, Map<K, V> actual, Map<K, V> expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUAL_OPER, message, actual, expected);
	}

	public static final void equalsIgnoreCase(String message, String actual, String expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(EQUALS_IGNORE_CASE_OPER, message, actual, expected);
		else
			reportUtils.assertFail(EQUALS_IGNORE_CASE_OPER, message, actual, expected);
	}

	public static final void notEqualsIgnoreCase(String message, String actual, String expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(NOT_EQUALS_IGNORE_CASE_OPER, message, actual, expected);
		else
			reportUtils.assertFail(NOT_EQUALS_IGNORE_CASE_OPER, message, actual, expected);
	}

	public static final void contains(String message, String stringContains, String containsString) {
		if (CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(CONTAINS_OPER, message, stringContains, containsString);
	}
	
	public static final void contains(String message, List<String> stringContains, String containsString) {
		if (CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(CONTAINS_OPER, message, stringContains, containsString);
	}
	
	public static final void contains(String message, List<String> stringContains, List<String> containsString) {
		if (CommonUtils.compareContainsAny(stringContains, containsString))
			reportUtils.assertPass(CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(CONTAINS_OPER, message, stringContains, containsString);
	}

	public static final void notContains(String message, String stringContains, String containsString) {
		if (!CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(NOT_CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(NOT_CONTAINS_OPER, message, stringContains, containsString);
	}
	
	public static final void notContains(String message, List<String> stringContains, String containsString) {
		if (!CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(NOT_CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(NOT_CONTAINS_OPER, message, stringContains, containsString);
	}
	
	public static final void notContains(String message, List<String> stringContains, List<String> containsString) {
		if (!CommonUtils.compareContainsAny(stringContains, containsString))
			reportUtils.assertPass(CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(CONTAINS_OPER, message, stringContains, containsString);
	}
}
