package com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {

	public static final byte[] readBinaryFile(String filePath) {
		try {
			return Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String readTextFile(String filePath) {
		return new String(readBinaryFile(filePath));
	}

	public static final List<String> readTextFileByLines(String filePath) {
		try {
			return Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static final void writeToTextFile(String filePath, String content) {
		File file = new File(filePath);
		content = content.replaceAll("\n", "\r\n");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.CREATE_NEW);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			content = " " + content;

			try {
				Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final String getRequiredTextFromTextFile(String filePath, String beforeText, int reqTextLength) {
		String fileContent = readTextFile(filePath);
		int initLen = fileContent.indexOf(beforeText) + fileContent.length();
		return fileContent.substring(initLen, initLen + reqTextLength).trim();
	}

	public static final void clearTextFile(String filePath) {
		try {
			Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
