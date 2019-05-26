package com;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class FileHelperUtils {
	private FileHelperUtils() {
	}

	/**
	 * 
	 * @param pathName
	 * @return File
	 */
	public static final File createFile(String pathName) {
		File file = new File(pathName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 
	 * @param pathName
	 * @return File
	 */
	public static final File createFolder(String pathName) {
		File file = new File(pathName);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * 
	 * @param pathName
	 */
	public static final void deleteExistingFile(String pathName) {
		File file = new File(pathName);
		file.deleteOnExit();
	}

	/**
	 * 
	 * @param pathName
	 * @return List<File>
	 */
	public static final List<File> getListOfFiles(String pathName) {
		return Arrays.asList(new File(pathName).listFiles());
	}

	/**
	 * 
	 * @param folderPath
	 * @param fileName
	 * @return String
	 */
	public static final String filePathFromFolder(String folderPath, String fileName) {
		String pathName = null;
		List<File> lst = getListOfFiles(pathName);
		for (File file : lst) {
			if (file.toString().contains(fileName)) {
				pathName = file.toString();
				break;
			}
		}
		return pathName;
	}

	/**
	 * 
	 * @return String
	 */
	public static final String getResourcesFolderPath() {
		return String.format(System.getProperty("user.dir") + "\\Resources");
	}

	/**
	 * 
	 * @param pathName
	 * @return String
	 */
	public static final String getResourcesFilePath(String pathName) {
		return getResourcesFolderPath() + "\\" + pathName;
	}

	/**
	 * 
	 * @return String
	 */
	public static final String getReportFolderPath() {
		return String.format(System.getProperty("user.dir") + "\\Report");
	}

	/**
	 * 
	 * @param pathName
	 * @return String
	 */
	public static final String getReportFilePath(String pathName) {
		return getReportFolderPath() + "\\" + pathName;
	}

	/**
	 * 
	 * @param pathName
	 * @return String
	 */
	public static final String readFileAsString(String pathName) {
		try {
			return FileUtils.readFileToString(new File(pathName), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param pathName
	 */
	public static final void clearFile(String pathName) {
		deleteExistingFile(pathName);
		createFile(pathName);
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 */
	public static final void copyFile(String sourceFilePath, String destFilePath) {
		try {
			FileUtils.copyFile(new File(sourceFilePath), new File(destFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 */
	public static final void copyFolder(String sourceFilePath, String destFilePath) {
		try {
			FileUtils.copyDirectory(new File(sourceFilePath), new File(destFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param pathName
	 */
	public static final void cleanFolder(String pathName) {
		try {
			FileUtils.cleanDirectory(new File(pathName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 */
	public static final void moveFile(String sourceFilePath, String destFilePath) {
		try {
			FileUtils.moveFile(new File(sourceFilePath), new File(destFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return String
	 */
	public static final String getFilePathFromReportOrResourcesFolder(String fileName) {
		if (fileName != null && !(fileName.contains("Report") || fileName.contains("Resources"))) {
			String filePath = null;
			try {
				filePath = getReportFilePath(fileName);
				if (!(new File(filePath).exists())) {
					filePath = getResourcesFilePath(fileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fileName = filePath;
			}
		}
		return fileName;
	}
	
	public static final <V, K> Map<K, Set<V>> getKeyValuePairsFromExcel(String filePath, String sheetName, int keyCoulmnNum, int valueColumnNum){
		return null;
		
	}
}
