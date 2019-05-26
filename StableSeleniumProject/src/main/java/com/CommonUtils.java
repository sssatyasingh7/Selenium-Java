package com;

import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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
	 * @return String
	 */
	public static final String generateRandomNumber(long min, long max) {
		return String.valueOf((Math.random() * ((max - min) + 1)) + min);
	}

	/**
	 * 
	 * @param digitCount
	 * @return String
	 */
	public static final String generateRandomNumber(int digitCount) {
		long min = (long) Math.pow(10, digitCount - 1);
		return String.valueOf(ThreadLocalRandom.current().nextLong(min, min * 10));
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static final <E> boolean compareEquals(List<E> actual, List<E> expected) {
		return new HashSet<E>(actual).equals(new HashSet<E>(expected));
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static final <E> boolean compareContains(List<E> actual, List<E> expected) {
		return actual.contains(expected);
	}

	/**
	 * 
	 * @param <E>
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static final <E> boolean compareContainsAll(List<E> actual, List<E> expected) {
		return actual.containsAll(expected);
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
	 * @return boolean
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
	 * @param inputString
	 * @param specialChar
	 * @return List<String>
	 */
	public static final List<String> getListOfSpecialCharacterSaperatedWords(String inputString, String specialChar) {
		return inputString != null ? Arrays.asList(inputString.split(specialChar)) : new ArrayList<String>();
	}

	/**
	 * 
	 * @param inputString
	 * @return
	 */
	public static final List<String> getListOfCommaSaperatedWords(String inputString) {
		return getListOfSpecialCharacterSaperatedWords(inputString, ",");
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
		return inputString.replaceAll(replacableChar, replacedChar);
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
	 * 
	 * @param inputString
	 * @return
	 */
	public static final String containsTextXpath(String inputString) {
		String xPath = "[";
		List<String> words = getListOfBlankSpaceSaperatedWords(inputString);
		int listSize = words.size();
		for (int i = 0; i < listSize; i++) {
			xPath = xPath + String.format("contains(.,'%s')", words.get(i));
			xPath = i < (listSize - 1) ? xPath + " and " : xPath + "]";
		}
		return xPath;
	}

	/**
	 * 
	 * @param inputString
	 * @param map
	 * @return String
	 */
	public static final String stringToJsonString(String inputString, final Map<String, String> map) {
		for (String key : map.keySet()) {
			inputString = replaceAllCharWithOtherChar(inputString, key, String.format("\"%s\"", map.get(key)));
		}
		return "{\"" + inputString + "\"}";
	}

	/**
	 * 
	 * @param map
	 * @return String
	 */
	public static final String mapToJsonString(final Map<String, String> map) {
		String jsonString = "{";
		List<String> lst = new ArrayList<String>(map.keySet());
		int lstSize = lst.size();
		for (int i = 0; i < lstSize; i++) {
			jsonString = jsonString + String.format("\"%s\":\"%s\"", lst.get(i), map.get(lst.get(i)));
			jsonString = i < (lstSize - 1) ? jsonString + "," : jsonString + "}";
		}
		return jsonString;
	}

	/**
	 * 
	 * @param inputString
	 * @return String
	 */
	public static final String returnStringValue(String inputString) {
		return inputString == null ? null : inputString.trim().length() == 0 ? null : inputString.trim();
	}

	public static final Map<String, String> jsonStringToMap(String jsonString) {
		Map<String, String> map = new HashMap<String, String>();
		if (jsonString.startsWith("{")) {
			jsonString = jsonString.substring(1);
		}
		if (jsonString.endsWith("}")) {
			jsonString = jsonString.substring(0, jsonString.length() - 1).trim();
		}
		if (jsonString != null) {
			getListOfCommaSaperatedWords(jsonString).parallelStream().forEachOrdered(str -> {
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
}
