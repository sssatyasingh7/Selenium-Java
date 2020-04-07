package com;

import java.util.List;
import java.util.Map;

public class AssertUtils {
	private static ReportUtils reportUtils;

	private AssertUtils() {
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void equals(String message, String actual, String expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void equals(String message, Object actual, Object expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void equals(String message, boolean actual, boolean expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param <E>
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final <E> void equals(String message, List<E> actual, List<E> expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final <K, V> void equals(String message, Map<K, V> actual, Map<K, V> expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void notEquals(String message, String actual, String expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void notEquals(String message, Object actual, Object expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void notEquals(String message, boolean actual, boolean expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param <E>
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final <E> void notEquals(String message, List<E> actual, List<E> expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final <K, V> void nonEquals(String message, Map<K, V> actual, Map<K, V> expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUAL_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUAL_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void equalsIgnoreCase(String message, String actual, String expected) {
		if (CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.EQUALS_IGNORE_CASE_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.EQUALS_IGNORE_CASE_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param actual
	 * @param expected
	 */
	public static final void notEqualsIgnoreCase(String message, String actual, String expected) {
		if (!CommonUtils.compareEquals(actual, expected))
			reportUtils.assertPass(SystemData.NOT_EQUALS_IGNORE_CASE_OPER, message, actual, expected);
		else
			reportUtils.assertFail(SystemData.NOT_EQUALS_IGNORE_CASE_OPER, message, actual, expected);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void contains(String message, String stringContains, String containsString) {
		if (CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(SystemData.CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.CONTAINS_OPER, message, stringContains, containsString);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void contains(String message, List<String> stringContains, String containsString) {
		if (CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(SystemData.CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.CONTAINS_OPER, message, stringContains, containsString);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void contains(String message, List<String> stringContains, List<String> containsString) {
		if (CommonUtils.compareContainsAny(stringContains, containsString))
			reportUtils.assertPass(SystemData.CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.CONTAINS_OPER, message, stringContains, containsString);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void notContains(String message, String stringContains, String containsString) {
		if (!CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(SystemData.NOT_CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.NOT_CONTAINS_OPER, message, stringContains, containsString);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void notContains(String message, List<String> stringContains, String containsString) {
		if (!CommonUtils.compareContains(stringContains, containsString))
			reportUtils.assertPass(SystemData.NOT_CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.NOT_CONTAINS_OPER, message, stringContains, containsString);
	}

	/**
	 * 
	 * @param message
	 * @param stringContains
	 * @param containsString
	 */
	public static final void notContains(String message, List<String> stringContains, List<String> containsString) {
		if (!CommonUtils.compareContainsAny(stringContains, containsString))
			reportUtils.assertPass(SystemData.CONTAINS_OPER, message, stringContains, containsString);
		else
			reportUtils.assertFail(SystemData.CONTAINS_OPER, message, stringContains, containsString);
	}
}
