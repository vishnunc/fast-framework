package com.qentelli.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qentelli.automation.accelerators.Actiondriver;


public class ExcelLib 
{
	public static Sheet wrkSheetObj;
    public static Workbook wrkBookObj =null;
    public static FileInputStream fis = null;
	public static FileOutputStream fileOut = null;
	public static HSSFWorkbook workbook = null;
	public static HSSFSheet sheet = null;
	public static HSSFRow row = null;
	public static HSSFCell cell = null;
	public static Property configProps = new Property("config.properties");
	public static String path = System.getProperty("user.dir") + "\\Macros\\"
			+ configProps.getProperty("MacroFile");
	public static String testDatapath = System.getProperty("user.dir") + "\\TestData\\"
			+ configProps.getProperty("NeriumWorkbook");
    
    /**
     * 
     * @param inputDataFilePath
     * @param SheetName
     * @return
     * @throws Exception
     */
    public static  Sheet getSheetObject(String inputDataFilePath, String SheetName) throws Exception
	{
		FileInputStream file = new FileInputStream(new File(inputDataFilePath));
		Workbook wrkBookObj =  Workbook.getWorkbook(file); 
		Sheet wrkSheetObj= wrkBookObj.getSheet(SheetName);		
		return wrkSheetObj;
	}
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
    public static String getSheetCellData(String sColumnName, int iRowCount) throws Exception
	{
    	String cellData=null;
		int SheetColCount=Actiondriver.objInputSheet.getColumns();
		for(int Colcnt=0;Colcnt<SheetColCount;Colcnt++)
		{
			if((Actiondriver.objInputSheet.getCell(Colcnt,0).getContents()).equals(sColumnName))
			{
				cellData=Actiondriver.objInputSheet.getCell(Colcnt, iRowCount).getContents();
				return cellData;
			}			
		}				
		return null;
	}

    public static ArrayList<String> getModules(String sheetName)
			throws IOException {
		List<String> runsuites = new ArrayList<String>();
		fis = new FileInputStream(path);
		workbook = new HSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		for (int i = 0; i <= getRowCount(sheetName) + 1; i++) {
			

			if (getCellData(sheetName, "ExecuteMode", i)
					.equalsIgnoreCase("Yes")) {
				//System.out.println(getCellData(sheetName, "Modules", i));
				runsuites.add(getCellData(sheetName, "Modules", i));
			} else {
				continue;
			}
		}

		return (ArrayList<String>) runsuites;

	}

public static ArrayList<String> getTestCases(String suiteName) {

		List<String> testCases = new ArrayList<String>();
		try {
			for (int i = 0; i <= getRowCount(suiteName) + 1; i++) {
				if (getCellData(suiteName, "ExecuteMode", i)
						.equalsIgnoreCase("Yes")) {
					testCases.add(getCellData(suiteName, "TestCase", i));
				} else {
					continue;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (ArrayList<String>) testCases;

	}

public static String getCellData(String sheetName, String colName,
			int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
				
				return cell.getStringCellValue();
			}
			else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)))
							.substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/"
							+ cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName
					+ " does not exist in xls";
		}
	}

	/*
	 * public static int getRowCount(String sheetName) {
	 * 
	 * try{ System.out.println("path:"+path); fis = new FileInputStream(path);
	 * workbook = new XSSFWorkbook(fis); sheet = workbook.getSheet(sheetName);
	 * 
	 * 
	 * } catch(Exception e){ e.printStackTrace(); } return
	 * sheet.getPhysicalNumberOfRows();
	 * 
	 * }
	 */

public static int getRowCount(String sheetName) throws IOException {
		//System.out.println("path:" + path);
		int rowCount  = 0;
		
		fis = new FileInputStream(path);
		workbook = new HSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		try{
		rowCount= sheet.getLastRowNum();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rowCount;

	}

public static int getColumnCount(String sheetName) throws IOException {
		int columnCount = 0;
	

		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName.trim());
			columnCount = sheet.getRow(0).getLastCellNum();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return columnCount;
	}
	
public static int getColumnNumber(String columnName,HSSFSheet sheet) 
throws IOException{		
	int noOfColumns = sheet.getRow(0).getLastCellNum();
	int columnNUmber = 0;
	for(int i=0;i<=noOfColumns;i++){
		String s = sheet.getRow(0).getCell(i).toString().trim();
		if(!s.equals(columnName.trim())){
			continue;
		}
		else{
			columnNUmber =i;
		}
		break;
	}
	return columnNUmber;
}




}
