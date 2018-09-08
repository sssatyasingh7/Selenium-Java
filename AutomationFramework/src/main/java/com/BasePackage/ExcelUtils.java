package com.BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

public final class ExcelUtils {

	public static XSSFSheet sheet;
	private static final String REGEX = "[0-9]+{3}";

	/**
	 * Get Cell Value
	 * 
	 * @param path
	 *            (Path of Spreadsheet)
	 * @param sheetNameOrNumber
	 * @param row
	 *            (Row Number)
	 * @param col
	 *            (Column Number)
	 * @return cellValue for row and col
	 * @throws IOException
	 * @throws Exception
	 */
	public Object getCellValue(String path, String sheetNameOrNumber, int row, int col) throws IOException {
		Object cellValue = null;
		File src = new File(path);
		if (src.exists()) {

			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);

			XSSFSheet xssfSheet = null;

			if (Pattern.matches(REGEX, sheetNameOrNumber)) {
				xssfSheet = wb.getSheetAt(Integer.parseInt(sheetNameOrNumber));
			} else {
				xssfSheet = wb.getSheet(sheetNameOrNumber);
			}

			if (xssfSheet != null) {
				cellValue = getCellValue(xssfSheet, row, col);
			} else {
				Assert.fail("*** '" + sheetNameOrNumber + "' Sheet/Tab not found for Spreadsheet ***");
			}
			wb.close();
		}
		return cellValue;
	}

	/**
	 * Load Excel Sheet
	 * 
	 * @param path
	 *            (Path of Spreadsheet)
	 * @param sheetNameOrNumber
	 *            (Sheet/Tab Name/Number)
	 * @return sheetObject
	 * @throws IOException
	 * @throws Exception
	 */
	public XSSFSheet loadExcelSheet(String path, String sheetNameOrNumber) throws IOException {
		File excelFile = new File(path);// Path Where Excel sheet is located
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook book = new XSSFWorkbook(fis);

		if (Pattern.matches(REGEX, sheetNameOrNumber)) {
			sheet = book.getSheetAt(Integer.parseInt(sheetNameOrNumber));
		} else {
			sheet = book.getSheet(sheetNameOrNumber);
		}
		book.close();

		return sheet;
	}

	/**
	 * Get cell Value
	 * 
	 * @param sheet
	 *            (sheet Instance)
	 * @param row
	 *            (Row Number)
	 * @param col
	 *            (Column Number)
	 * @return Cell Value at Row index (row) and column index (col)
	 */
	public String getCellValue(XSSFSheet sheet, int row, int col) {
		String dataVal = null;
		XSSFCell cellType = sheet.getRow(row).getCell(col);
		if (cellType.getCellTypeEnum() == CellType.NUMERIC)
			dataVal = String.valueOf((long) cellType.getNumericCellValue());
		else if (cellType.getCellTypeEnum() == CellType.BOOLEAN)
			dataVal = Boolean.toString(cellType.getBooleanCellValue());
		else
			dataVal = cellType.getStringCellValue();

		return dataVal;
	}

	/**
	 * Get Cell Value
	 * 
	 * @param row
	 *            (Row Number)
	 * @param col
	 *            (Column Number)
	 * @return Cell Value at Row index (row) and column index (col)
	 */
	public Object getCellValue(Row row, int col) {
		Object cellValue = null;
		Cell cellType = row.getCell(col);
		if (cellType.getCellTypeEnum() == CellType.NUMERIC)
			cellValue = Long.toString((long) cellType.getNumericCellValue());
		else if (cellType.getCellTypeEnum() == CellType.BOOLEAN)
			cellValue = Boolean.toString(cellType.getBooleanCellValue());
		else if (cellType.getCellTypeEnum() == CellType.BLANK)
			cellValue = null;
		else
			cellValue = cellType.getStringCellValue();
		return cellValue;
	}

	/**
	 * Get Cell Value (It will return the data from currently opened sheet)
	 * 
	 * @param row
	 *            (Row Number)
	 * @param col
	 *            (Column Number)
	 * @return Cell Value at Row index (row) and column index (col)
	 */
	public Object getCellValue(int row, int col) {
		Object dataVal = null;
		XSSFCell cellType = sheet.getRow(row).getCell(col);
		if (cellType.getCellTypeEnum() == CellType.NUMERIC)
			dataVal = String.valueOf((long) cellType.getNumericCellValue());
		else if (cellType.getCellTypeEnum() == CellType.BOOLEAN)
			dataVal = Boolean.toString(cellType.getBooleanCellValue());
		else
			dataVal = cellType.getStringCellValue();
		return dataVal;
	}

	/**
	 * Set Cell Value in Excel Sheet
	 * 
	 * @param path
	 *            (Path of Spreadsheet)
	 * @param sheetNameOrNumber
	 *            (pass Sheet/Tab Name/Index in the form of String)
	 * @param row
	 *            (Row Number)
	 * @param col
	 *            (Column Number)
	 * @param obj
	 *            (value to be set)
	 * @param createNewWorkbook
	 * @throws IOException
	 * @throws Exception
	 */
	public void setCellValue(String path, String sheetNameOrNumber, int row, int col, Object obj,
			boolean createNewWorkbook) throws IOException {
		String sheetList = null;
		String writeSheet = null;
		XSSFSheet xssfSheet = null;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		Row rowVal = null;
		Cell cellVal = null;
		File src = new File(path);

		if (createNewWorkbook == true && !src.exists()) {
			if (src.createNewFile()) {
				FileOutputStream fout = new FileOutputStream(src);
				xssfWorkbook.write(fout);
				xssfWorkbook.close();
			}
		} else if (createNewWorkbook == false && !src.exists()) {
			Assert.fail("*** Spreadsheet File not found ***");
		}

		FileInputStream fis = new FileInputStream(src);
		xssfWorkbook = new XSSFWorkbook(fis);

		if (xssfWorkbook != null) {
			if (Pattern.matches(REGEX, sheetNameOrNumber)) {
				writeSheet = xssfWorkbook.getSheetName(Integer.parseInt(sheetNameOrNumber));
			} else {
				writeSheet = sheetNameOrNumber;
			}

			for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
				sheetList += xssfWorkbook.getSheetName(i) + "  ";
			}

			if (sheetList == null || !sheetList.contains(writeSheet)) {
				xssfSheet = xssfWorkbook.createSheet(writeSheet);
			} else {
				xssfSheet = xssfWorkbook.getSheet(writeSheet);
			}

			if (xssfSheet != null) {
				rowVal = xssfSheet.getRow(row);
				if (rowVal == null) {
					rowVal = xssfSheet.createRow(row);
				}
				cellVal = rowVal.getCell(col);
				if (cellVal == null) {
					cellVal = rowVal.createCell(col);
				}

				if (obj instanceof Boolean) {
					cellVal.setCellValue((Boolean) obj);
				} else if (obj instanceof Integer) {
					cellVal.setCellValue((Integer) obj);
				} else if (obj instanceof Calendar) {
					cellVal.setCellValue((Calendar) obj);
				} else if (obj instanceof Date) {
					cellVal.setCellValue((Date) obj);
				} else if (obj instanceof RichTextString) {
					cellVal.setCellValue((RichTextString) obj);
				} else {
					cellVal.setCellValue((String) obj);
				}
			}
		}
		FileOutputStream fout = new FileOutputStream(src);
		xssfWorkbook.write(fout);
		xssfWorkbook.close();
	}

	/**
	 * Set Cell value in existing WorkBook and Sheet
	 * 
	 * @param path
	 * @param sheetNameOrNumber
	 * @param row
	 * @param col
	 * @param obj
	 * @throws IOException
	 */
	public void setCellValue(String path, String sheetNameOrNumber, int row, int col, Object obj) throws IOException {
		setCellValue(path, sheetNameOrNumber, row, col, obj, false);
	}

	/**
	 * Create New Workbook, Sheet and Set Cell Value
	 * 
	 * @param path
	 * @param sheetNameOrNumber
	 * @param row
	 * @param col
	 * @param obj
	 * @throws IOException
	 */
	public void setCellValueWithNewWorkbookAndSheet(String path, String sheetNameOrNumber, int row, int col, Object obj)
			throws IOException {
		setCellValue(path, sheetNameOrNumber, row, col, obj, true);
	}
}