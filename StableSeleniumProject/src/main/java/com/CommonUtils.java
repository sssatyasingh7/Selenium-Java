package com;

import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public final class CommonUtils {
	private CommonUtils() {
	}

	/**
	 * 
	 * @param milliSeconds
	 */
	public static final void threadSleep(long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 * @param min
	 * @param max
	 * @return {@link String}
	 */
	public static final String generateRandomNumber(long min, long max) {
		return String.valueOf((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * 
	 * @param digitCount
	 * @return {@link String}
	 */
	public static final String generateRandomNumber(int digitCount) {
		long min = (long) Math.pow(10, digitCount - 1);
		return String.valueOf(ThreadLocalRandom.current().nextLong(min, min * 10));
	}

	/**
	 * 
	 * @param <E>
	 * @param lst
	 * @return
	 */
	public static final <E> boolean isListNotEmpty(List<E> lst) {
		return !isListEmpty(lst);
	}

	/**
	 * 
	 * @param <E>
	 * @param lst
	 * @return
	 */
	public static <E> boolean isListEmpty(List<E> lst) {
		return (lst == null || lst.isEmpty());
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static final <K, V> boolean isMapEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static final <K, V> boolean isMapNotEmpty(Map<K, V> map) {
		return !isMapEmpty(map);
	}

	/**
	 * 
	 * @param stringValue
	 * @return
	 */
	public static final String returnValue(String stringValue) {
		return stringValue == null ? null : stringValue.trim().isEmpty() ? null : stringValue.trim();
	}

	/**
	 * 
	 * @param objectValue
	 * @return
	 */
	public static final Object returnValue(Object objectValue) {
		return (objectValue == null || objectValue == "") ? null : objectValue;
	}

	/**
	 * 
	 * @param objectValue
	 * @return
	 */
	public static final boolean isNotNull(Object objectValue) {
		return returnValue(objectValue) != null;
	}

	/**
	 * 
	 * @param objectValue
	 * @return
	 */
	public static final boolean isNull(Object objectValue) {
		return !isNotNull(objectValue);
	}

	public static final boolean isNotNull(String stringValue) {
		return returnValue(stringValue) != null;
	}

	/**
	 * 
	 * @param stringValue
	 * @return
	 */
	public static final boolean isNull(String stringValue) {
		return !isNotNull(stringValue);
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static final <E> boolean compareEquals(List<E> actual, List<E> expected) {
		return isListNotEmpty(actual) && isListNotEmpty(expected) && actual.equals(expected);
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return
	 */
	public static final <E> boolean compareContainsAll(List<E> actual, List<E> expected) {
		return isListNotEmpty(actual) && isListNotEmpty(expected) && actual.containsAll(expected);
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static final <E> boolean compareContainsAny(List<E> stringContains, List<E> containsString) {
		return isListNotEmpty(stringContains) && isListNotEmpty(containsString)
				&& containsString.stream().anyMatch(stringContains::contains);
	}

	/**
	 * 
	 * @param stringContains
	 * @param containsString
	 * @return
	 */
	public static final boolean compareContains(String stringContains, String containsString) {
		return isNotNull(stringContains) && isNotNull(containsString) && stringContains.contains(containsString);
	}

	/**
	 * 
	 * @param stringContains
	 * @param containsString
	 * @return
	 */
	public static final boolean compareContains(List<String> stringContains, String containsString) {
		return isListNotEmpty(stringContains) && isNotNull(containsString) && stringContains.contains(containsString);
	}

	/**
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static final boolean compareEquals(Object object1, Object object2) {
		return isNotNull(object1) && isNotNull(object2) && object1 == object2;
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static final <K, V> boolean compareEquals(Map<K, V> map1, Map<K, V> map2) {
		return isMapNotEmpty(map1) && isMapNotEmpty(map2) && map1.equals(map2);
	}

	/**
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static final boolean compareEqualsIgnoreCase(String string1, String string2) {
		return isNotNull(string1) && isNotNull(string2) && string1.equalsIgnoreCase(string2);
	}

	/**
	 * 
	 * @param filePath
	 * @return byte[]
	 */
	public static final byte[] convertScreenshotTo64Bits(String filePath) {
		InputStream ins = null;
		int byteRead = 0;
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ins = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
		}
		try {
			while ((byteRead = ins.read(buffer)) != 1) {
				baos.write(buffer, 0, byteRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	/**
	 * 
	 * @param filePath1
	 * @param filePath2
	 * @return {@link Boolean}
	 */
	public static final boolean compareImages(String filePath1, String filePath2) {
		try {
			DataBuffer dbA = ImageIO.read(new File(filePath1)).getData().getDataBuffer();
			DataBuffer dbB = ImageIO.read(new File(filePath2)).getData().getDataBuffer();
			int size1 = dbA.getSize();
			if (size1 == dbB.getSize()) {
				for (int i = 0; i < size1; i++) {
					if (dbA.getElem(i) != dbB.getElem(i)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static final List<String> getListOfCommaSaperatedWords(String string) {
		return isNotNull(string) ? Arrays.asList(string.split(",")) : SystemData.EMPTY_LIST_OF_TYPE_STRING;
	}

	/**
	 * 
	 * @param inputString
	 * @param specialChar
	 * @return {@link List}
	 */
	public static final List<String> getListOfSpecialCharacterSaperatedWords(String inputString, String specialChar) {
		return isNotNull(inputString) ? Arrays.asList(inputString.split(specialChar))
				: SystemData.EMPTY_LIST_OF_TYPE_STRING;
	}

	/**
	 * 
	 * @param inputString
	 * @return List<String>
	 */
	public static final List<String> getListOfBlankSpaceSaperatedWords(String inputString) {
		return getListOfSpecialCharacterSaperatedWords(inputString, "\\s+");
	}

	/**
	 * 
	 * @param inputString
	 * @param replacableChar
	 * @param replacedChar
	 * @return String
	 */
	public static final String replaceAllCharWithOtherChar(String inputString, String replacableChar,
			String replacedChar) {
		return isNotNull(inputString) && inputString.contains(replacableChar)
				? inputString.replaceAll(replacableChar, replacedChar)
				: inputString;
	}

	/**
	 * 
	 * @param inputString
	 * @param charValue
	 * @return
	 */
	public static final String replaceBlankSpaceWithChar(String inputString, String charValue) {
		return replaceAllCharWithOtherChar(inputString, "\\s", charValue);
	}

	/**
	 * Contains X-Path Operation
	 * 
	 * @param inputString
	 * @return {@link String}
	 */
	public static final String containsTextXpath(String inputString) {
		return getListOfBlankSpaceSaperatedWords(inputString).stream().filter(Objects::nonNull)
				.map(textVal -> String.format("contains(.,'%s')", textVal))
				.collect(Collectors.joining(" and ", "[", "]"));
	}

	/**
	 * 
	 * @param inputString
	 * @param map
	 * @return String
	 */
	public static final String stringToJsonString(String inputString, final Map<String, String> map) {
		return map.keySet().stream()
				.map(key -> replaceAllCharWithOtherChar(inputString, key, String.format("\"%s\"", map.get(key))))
				.collect(Collectors.joining("", "{\"", "\"}"));
	}

	/**
	 * 
	 * @param map
	 * @return String
	 */
	public static final String mapToJsonString(final Map<String, String> map) {
		return map.keySet().stream().map(key -> String.format("\"%s\":\"%s\"", key, map.get(key)))
				.collect(Collectors.joining(",", "{", "}"));
	}

	/**
	 * 
	 * @param jsonString
	 * @return {@link Map}
	 */
	public static final Map<String, String> jsonStringToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		if (jsonString.startsWith("{")) {
			jsonString = jsonString.substring(1);
		}
		if (jsonString.endsWith("}")) {
			jsonString = jsonString.substring(0, jsonString.length() - 1).trim();
		}
		if (jsonString != null) {
			getListOfCommaSaperatedWords(jsonString).stream().forEachOrdered(str -> {
				str = str.trim();
				String key = str.substring(1, str.indexOf(":") - 1);
				String value = str.substring(str.indexOf(":") + 2, str.length() - 1);
				map.put(key, value);
			});
		}
		return map;
	}

	/**
	 * Merge Two Map (Merger Map will be merged into Merger Map, if key already
	 * exist in merger map, then it will update the value for that key)
	 * 
	 * @param <K>
	 * @param <V>
	 * @param mergerMap
	 * @param mergedMap
	 * @return
	 */
	public static final <K, V> Map<K, V> mergeMap(Map<K, V> mergerMap, Map<K, V> mergedMap) {
		Set<K> jsonKeySet = mergerMap.keySet();
		mergedMap.keySet().parallelStream().forEachOrdered(key -> {
			if (jsonKeySet.contains(key)) {
				mergerMap.replace(key, mergedMap.get(key));
			} else {
				mergerMap.put(key, mergedMap.get(key));
			}
		});
		return mergerMap;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static final List<String> splitStringBasedOnDot(String string) {
		return Arrays.asList(string.split("\\."));
	}

	/**
	 * 
	 * @param encodedQuery
	 * @param key
	 * @param operator
	 * @param values
	 * @return {@link String}
	 */
	public static final String formAndEncodedQuery(String encodedQuery, String key, String operator,
			List<String> values) {
		String encQuery = returnValue(values.stream().filter(Objects::nonNull)
				.map(value -> String.format("%s%s%s", key, operator, value)).collect(Collectors.joining("^")));
		return isNotNull(encodedQuery) ? encodedQuery + "^" + encQuery : encQuery;
	}

	/**
	 * 
	 * @param key
	 * @param operator
	 * @param values
	 * @return {@link String}
	 */
	public static final String formAndEncodedQuery(String key, String operator, List<String> values) {
		return formAndEncodedQuery(null, key, operator, values);
	}

	/**
	 * 
	 * @param encodedQuery
	 * @param operator
	 * @param mapVal
	 * @return
	 */
	public static final String formAndEncodedQuery(String encodedQuery, String operator, Map<String, String> mapVal) {
		String encQuery = returnValue(mapVal.keySet().stream().filter(Objects::nonNull)
				.map(key -> String.format("%s%s%s", key, operator, mapVal.get(key))).collect(Collectors.joining("^")));
		return isNotNull(encodedQuery) ? encodedQuery + "^" + encQuery : encQuery;
	}

	/**
	 * 
	 * @param operator
	 * @param mapVal
	 * @return
	 */
	public static final String formAndEncodedQuery(String operator, Map<String, String> mapVal) {
		return formAndEncodedQuery(null, operator, mapVal);
	}
}