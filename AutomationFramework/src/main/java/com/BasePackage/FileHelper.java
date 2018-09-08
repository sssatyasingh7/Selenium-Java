package com.BasePackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileHelper {

	private FileHelper() {
	}

	public static File[] getAllFilesUnderFoler(String fullFolderPath) {
		File folder = new File(fullFolderPath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return listOfFiles;
	}

	public static File getFileObject(URL fileURL) {
		String rawPath = fileURL.getPath();
		return new File(rawPath);
	}

	public static int getFileLineCount(String fullFilePathWithName)
			throws IOException {
		File tempFile = new File(fullFilePathWithName);
		String safePath = tempFile.getAbsolutePath();
		return Files.readAllLines(Paths.get(safePath)).size();
	}

	public static List<String> getFileContentAsListOfString(File tempFile)
			throws IOException {
		String safePath = tempFile.getAbsolutePath();
		return Files.readAllLines(Paths.get(safePath));
	}

	public static List<String> getFileContentAsListOfString(
			String fullFilePathWithName) throws IOException {
		File tempFile = new File(fullFilePathWithName);
		String safePath = tempFile.getAbsolutePath();
		return Files.readAllLines(Paths.get(safePath));
	}

	public static String getFileContentAsString(String fullFilePathWithName)
			throws IOException {
		File tempFile = new File(fullFilePathWithName);
		String safePath = tempFile.getAbsolutePath();
		return String.join("\n", Files.readAllLines(Paths.get(safePath)));
	}

	public static String getFileContentAsString(File tempFile)
			throws IOException {
		String safePath = tempFile.getAbsolutePath();
		return String.join("\n", Files.readAllLines(Paths.get(safePath)));
	}

	public static void overWriteFileContent(File fileObject, String text)
			throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileObject);
			fileWriter.write(text);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fileWriter != null) {
				fileWriter.flush();
				fileWriter.close();
			}
		}
	}
	
	public static void replaceTextInFile(File fileObject,  String regex, String replacement) throws IOException{
		String data = getFileContentAsString(fileObject);
		data = data.replaceAll(regex, replacement);
		overWriteFileContent(fileObject, data);
	}
	
}
