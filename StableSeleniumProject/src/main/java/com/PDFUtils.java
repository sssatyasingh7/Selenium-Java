package com;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFUtils {
	private PDFUtils() {
	}

	public static final PDDocument getPDFDocInstance(String filePath) {
		try {
			return PDDocument.load(new File(filePath));
		} catch (Exception e) {
			return null;
		}
	}

	public static final String getPDFContent(String filePath) {
		try (PDDocument pdf = getPDFDocInstance(filePath)) {
			return CommonUtils.isNotNull(pdf) ? CommonUtils.returnValue(new PDFTextStripper().getText(pdf)) : null;
		} catch (IOException e) {
			return null;
		}
	}

}
