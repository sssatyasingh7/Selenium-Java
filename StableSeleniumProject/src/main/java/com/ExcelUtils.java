package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static final String REGEX = "[0-9]+{1}";

	/**
	 * 
	 * @param filePath
	 * @return {@link XSSFWorkbook}
	 */
	public final XSSFWorkbook getWorkbookInstance(String filePath) {
		File src = new File(filePath);
		if (src.exists()) {
			XSSFWorkbook book = null;
			try (FileInputStream fis = new FileInputStream(src)) {
				book = new XSSFWorkbook(fis);
			} catch (IOException e) {
			}
			return book;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param workbookPath
	 * @param sheetNameOrNum
	 * @return {@link XSSFSheet}
	 */
	public final XSSFSheet getWorksheetInstance(String workbookPath, String sheetNameOrNum) {
		XSSFWorkbook book = getWorkbookInstance(workbookPath);
		if (book != null) {
			return Pattern.matches(REGEX, sheetNameOrNum) ? book.getSheetAt(Integer.parseInt(sheetNameOrNum))
					: book.getSheet(sheetNameOrNum);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param book
	 * @param sheetNameOrNum
	 * @return {@link XSSFSheet}
	 */
	public final XSSFSheet getWorksheetInstance(XSSFWorkbook book, String sheetNameOrNum) {
		return CommonUtils.isNotNull(book)
				? Pattern.matches(REGEX, sheetNameOrNum) ? book.getSheetAt(Integer.parseInt(sheetNameOrNum))
						: book.getSheet(sheetNameOrNum)
				: null;

	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return {@link Object}
	 */
	public final Object getCellValue(Row row, int col) {
		Cell cellType = row.getCell(col);
		if (cellType.getCellType() == CellType.NUMERIC)
			return cellType.getNumericCellValue();
		else if (cellType.getCellType() == CellType.BLANK || cellType.getCellType() == CellType._NONE)
			return null;
		else
			return cellType.getStringCellValue();
	}

	/**
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @return {@link Object}
	 */
	public final Object getCellValue(XSSFSheet sheet, int row, int col) {
		return CommonUtils.isNotNull(sheet) ? getCellValue(sheet.getRow(row), col) : null;
	}

	/**
	 * 
	 * @param filePath
	 * @param sheetNameOrNum
	 * @param row
	 * @param col
	 * @return {@link Object}
	 */
	public final Object getCellValue(String filePath, String sheetNameOrNum, int row, int col) {
		XSSFSheet sheet = getWorksheetInstance(filePath, sheetNameOrNum);
		if (sheet != null)
			return getCellValue(sheet, row, col);
		else
			return null;
	}
}
