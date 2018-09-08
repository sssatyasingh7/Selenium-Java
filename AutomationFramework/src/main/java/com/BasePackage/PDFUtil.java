package com.BasePackage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public final class PDFUtil {

	/**
	 * Input parameter - File Name with full path (must be a PDF file) Returns
	 * extracted text
	 */
	public static String extractTextfromAnyFile(String fileNamewithPath) throws IOException {
		File file = new File(fileNamewithPath);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		document.close();
		return text;
	}

	/**
	 * Input parameter - Resource File Name (must be a PDF file, present under
	 * resource folder) Returns extracted text
	 */
	public static String extractTextfromResourceFile(String resourceFileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL pdfFileURL = classLoader.getResource(resourceFileName);
		return extractTextfromAnyFile(pdfFileURL.getPath());
	}
}
