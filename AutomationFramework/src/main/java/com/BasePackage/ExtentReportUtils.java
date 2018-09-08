package com.BasePackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.BasePackage.DateUtils;

public class ExtentReportUtils {
	
	private static ExtentReports report;
	private static ExtentTest logger;
	private static File reportDir;
	private static File screenDir;
	private static String reportName;
	private static WebDriver driver;
	private static XSSFWorkbook writeWorkBook;
	private static XSSFSheet writeSheet;
	private static File writeExcelDir;
	private static int row=0;
	private static int col=0;
	
	
	public ExtentReportUtils(WebDriver driverInstance) {
		reportName="Report_"+ DateUtils.getSystemDateAndTime("MMMddmmss");
		driver = driverInstance;
	}
	
	/**
	 * To Create the logger for Extent Report
	 * It will create logger of Class Name
	 * It will name first Column as "Method/Steps/Scenario"
	 * Second Column as "Message"
	 * Third Column as "ObjectData"
	 * Fourth Column as "Input/Output/Actual"
	 * Fifth Column as "Expected"
	 * Sixth COlumn as "Result"
	 * @param testName
	 * @author satyam.kumar
	 */
	void createExtentTest(String releaseName, String testName) {
		
		createExcelSheet(testName);
		
		if(report==null) {
			if(reportDir==null) {
				reportDir=new File(System.getProperty("user.dir")+"\\Reports");
				if(!reportDir.exists())
					reportDir.mkdirs();
			}
			String path=reportDir+"\\"+reportName+".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			htmlReporter.config().setReportName(releaseName);
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setTheme(Theme.DARK);
		}
		logger=report.createTest(testName);
		report.flush();
	}
		
	/**
	 * Capture Screenshot
	 * @param message
	 * @return screenshot Destination
	 * @author satyam.kumar
	 */
	private static String captureScreenshot(String message) {
		if(screenDir==null) {
			screenDir=new File(System.getProperty("user.dir")+"\\Reports");
			if(!screenDir.exists())
				screenDir.mkdirs();
		}
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String dest=screenDir+"\\screenshot.png";
			FileUtils.copyFile(scrFile, new File(dest));
			return dest;
		}catch(Exception e) {System.out.println("\n::>>Capture Screenshots Error is::\n"+e);return e.getMessage();}
	}	

	/**
	 * Read the screenshot in byte format
	 * @param InputStream
	 * @return ByteArray
	 * @throws IOException
	 * @author satyam.kumar
	 */
	private static byte[] readFully(){
		InputStream ins=null;
		int bytesRead=0;
		try {
			ins=new FileInputStream(captureScreenshot("Screenshot"));
		}catch(Exception e) {}
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			while ((bytesRead = ins.read(buffer)) != -1) 
				baos.write(buffer, 0, bytesRead);
			
		}catch(Exception e) {System.out.println(e.getMessage());}
		return baos.toByteArray();
	}
	
	/**
	 * Perform test Result operation in Extent report
	 * @param message
	 * @param actual
	 * @param expected
	 * @param status
	 * @throws IOException
	 * @author satyam.kumar
	 */
	private void setTestResult(String message, String actual, String expected, String status) {
		String fileInBase64=null;
		status=status.toLowerCase();
		
		fileInBase64 = DatatypeConverter.printBase64Binary(readFully());
				
		try {
			if(status=="fail"){
				if((actual==null && expected==null) || (actual=="" && expected==""))
					logger.log(Status.FAIL,"<b><u><font color='red '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				else {
					logger.log(Status.FAIL, "<section class='left'><b><u><font color='red'>Operation:- '"+message+"'  is failed</u></b></font><br><b>Act Value: </b>"+actual+"<br><b>Exp Value: </b>"+expected+"</section>");
					logger.log(Status.INFO,"<b><u><font color='Chocolate '>Screenshot for above result is:</u></b></font>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				}
			}
			else if (status=="pass"){
				if((actual==null && expected==null) || (actual=="" && expected==""))
					logger.log(Status.PASS,"<b><u><font color='green '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
				else 
					logger.log(Status.PASS, "<section class='left'><b><u><font color='green'>Operation:- '"+message+"'  is working as expected</u></b></font><br><b>Act Value: </b>"+actual+"<br><b>Exp Value: </b>"+expected+"</section>");
			}
			else 
				logger.log(Status.INFO,"<b><u><font color='Chocolate '>"+message+"</font></u></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(fileInBase64).build());
		}catch(Exception e) {System.out.println("\nScreenshot attachment error message::\n"+e.getMessage());}
		
		report.flush();
	}

	/**
	 * Pass Information to Extent report
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusInfo(String message) {
		logger.log(Status.INFO,"<font color='Chocolate '>"+message+"</font>");
		report.flush();
	}
	
	/**
	 * Pass Info along with Screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusInfoWithScreenshot(String message) {
		setTestResult(message, null, null, "info");
	}
	
	/**
	 * Failed Scenario along with screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusFailForScreenshot(String message) {
		setTestResult(message, null, null, "fail");
	}
	
	/**
	 * Passes scenario along with Screenshot
	 * @param message
	 * @author satyam.kumar
	 */
	public void StatusPassForScreenshot(String message) {
		setTestResult(message, null, null, "pass");
	}
	
	/**********************************************************************************************************************/

	/**
	 * Create Sheet in WorkBook for Write Operations
	 * @param sheetName
	 * @return XSSFSheet
	 * @author satyam.kumar
	 */
	private XSSFSheet createExcelSheet(String sheetName) {
		if(writeWorkBook==null) {
			if(writeExcelDir==null) {
				String path=System.getProperty("user.dir")+"\\ExcelSheetReports\\"+reportName+".xlsx";
				writeExcelDir=new File(path);
				if(!writeExcelDir.exists()) {
					File parntDir=writeExcelDir.getParentFile();
					if(!parntDir.exists())
						parntDir.mkdirs();
				}	
			}
			writeWorkBook=new XSSFWorkbook();
			try {
				writeExcelDir.createNewFile();
				FileOutputStream fout = new FileOutputStream(writeExcelDir);
				writeWorkBook.write(fout);
				writeWorkBook.close();
			}catch(Exception e) {}
		}
		
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(writeExcelDir);
			writeWorkBook=new XSSFWorkbook(fis);
			writeSheet=writeWorkBook.createSheet(sheetName);
		
			FileOutputStream fout = new FileOutputStream(writeExcelDir);
			writeWorkBook.write(fout);
		}catch(Exception e) {}
		row=0;
		
		Font font = writeWorkBook.createFont();
	    font.setFontHeightInPoints((short)12);
	   // font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    CellStyle style = writeWorkBook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	   // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style.setFont(font);
	   //style.setAlignment(CellStyle.ALIGN_CENTER);
	 
	    writeSheet.setColumnWidth(0, 6500);
	    writeSheet.setColumnWidth(1, 10000);
	    writeSheet.setColumnWidth(2, 6000);
	    writeSheet.setColumnWidth(3, 6500);
	    writeSheet.setColumnWidth(4, 6500);
	    writeSheet.setColumnWidth(5, 4000);
	    
		Row headerRow=writeSheet.createRow(row);
		
		Cell cell0=	headerRow.createCell(col);
			cell0.setCellValue("Method/Steps/Scenario");
			cell0.setCellStyle(style);
		
		Cell cell1=headerRow.createCell(col+1);
			cell1.setCellValue("Message");
			cell1.setCellStyle(style);
			
		Cell cell2=headerRow.createCell(col+2);
			cell2.setCellValue("ObjectData");
			cell2.setCellStyle(style);
			
		Cell cell3=headerRow.createCell(col+3);
			cell3.setCellValue("Input/Output/Actual");
			cell3.setCellStyle(style);
			
		Cell cell4=headerRow.createCell(col+4);
			cell4.setCellValue("Expected");
			cell4.setCellStyle(style);
			
		Cell cell5=headerRow.createCell(col+5);
			cell5.setCellValue("Result");
			cell5.setCellStyle(style);	
			
		return writeSheet;
	}
	
	/**
	 * Write Data in Excel Sheet
	 * @param step
	 * @param objectData
	 * @param data
	 * @param expected
	 * @param result
	 */
	private void WriteToExcelSheet(String step, String message, String objectData, String data, String expected, String result) {
		Row rowVal=writeSheet.createRow(++row);
		if(step!=null || step!="")
			rowVal.createCell(col).setCellValue(step);
		if(message!=null || message!="")
			rowVal.createCell(col+1).setCellValue(message);
		if(objectData!=null || objectData!="")
			rowVal.createCell(col+2).setCellValue(objectData);
		if(data!=null || data!="")
			rowVal.createCell(col+3).setCellValue(data);
		if(expected!=null || expected!="")
			rowVal.createCell(col+4).setCellValue(expected);
		if(result!=null || result!="")
			rowVal.createCell(col+5).setCellValue(result);
		
		try {
			FileOutputStream fout=new FileOutputStream(writeExcelDir);
			writeWorkBook.write(fout);
		}catch(Exception e) {}
	}
	
	/**
	 * Get Total (till last entered data) row count
	 * @return Total Row Count
	 */
	public int TotalRowCount(XSSFSheet sheet) {
		return sheet.getPhysicalNumberOfRows();
	}

}
