package com.qentelli.automation.datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qentelli.automation.accelerators.Actiondriver;
import com.qentelli.automation.utilities.Property;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelLib 
{
	public static Sheet wrkSheetObj;
    public static Workbook wrkBookObj =null;
    static WritableWorkbook workbookWrite;
	static WritableSheet sheetWrite;
	public static Property configProps=new Property("config.properties");
	
    
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
    
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
    public static String getExigoSheetCellData(String sColumnName, int iRowCount) throws Exception
	{
    	String cellData=null;
		int SheetColCount=Actiondriver.objExigoInputSheet.getColumns();
		for(int Colcnt=0;Colcnt<SheetColCount;Colcnt++)
		{
			if((Actiondriver.objExigoInputSheet.getCell(Colcnt,0).getContents()).equals(sColumnName))
			{
				cellData=Actiondriver.objExigoInputSheet.getCell(Colcnt, iRowCount).getContents();
				return cellData;
			}			
		}				
		return null;
	}
    
    
    
    
    public static void writeXLSXFile(String sOrderID , String sStatus) throws IOException {
        try {
        	
        	String filePath=configProps.getProperty("orderLog").toString();
        	FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            XSSFCell cell = null;
            XSSFCell cell1 = null;
            
            int rowCnt1;
            rowCnt1=findRow(sheet, sOrderID);
            
            if(rowCnt1==-1)
            {
            	//Update the value of cell
	            rowCnt1=sheet.getLastRowNum();
	            XSSFRow lastRow = sheet.createRow(++rowCnt1);
	            
	            cell = lastRow.createCell(0);
	           // cell.setCellType(0);
	        	cell.setCellValue("!"+sOrderID);
	        	
	        	cell1 = lastRow.createCell(1);
	        	cell1.setCellValue("NEW");
            }
            else
            {
                //Update the value of cell
	          //  rowCnt=sheet.getLastRowNum();
	            XSSFRow lastRow = sheet.getRow(rowCnt1-1);
	          	cell1 = lastRow.getCell(1);
	        	cell1.setCellValue("CANCELLED");
            }
     
        	file.close();

            FileOutputStream  outFile = new FileOutputStream (new File(filePath));
            workbook.write(outFile);
            outFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
    public static void writeOrderDetailsToExcel(String sOrderID, String sOrderStats) throws Exception
	{
    	
    	String filePath=configProps.getProperty("orderLog").toString();
    	FileInputStream file = new FileInputStream(new File(filePath));
		Workbook wrkBookObj =  Workbook.getWorkbook(file); 
		Sheet wrkSheetObj= wrkBookObj.getSheet("Orders");
    	int rwCnt=wrkSheetObj.getRows();
    	System.out.println("Number of Rows in Excel :"+rwCnt);
    	
    	workbookWrite = Workbook.createWorkbook(new File(filePath), wrkBookObj);
		try{
			
    	sheetWrite = workbookWrite.getSheet("Orders");
		sheetWrite.addCell(new jxl.write.Label(0,rwCnt,sOrderID));
		sheetWrite.addCell(new jxl.write.Label(1,rwCnt,"New"));
		workbookWrite.write();	
		workbookWrite.close();
		}
		catch(Exception Ex)
		{
			workbookWrite.write();	
			workbookWrite.close();
		}
    	
  }
    
    
    /**
     * 
     * @param sSheet
     * @param sColumnName
     * @param iRowCount
     * @return
     * @throws Exception
     */
/*    public static void updateOrderDetailsInExcel(String sOrderID, String sOrderStats) throws Exception
	{
    	String filePath=configProps.getProperty("orderLog").toString();
    	FileInputStream file = new FileInputStream(new File(filePath));
		Workbook wrkBookObj =  Workbook.getWorkbook(file); 
	   	try
    	{
	    	Sheet wrkSheetObj= wrkBookObj.getSheet("Orders");
	    	int rwCnt=wrkSheetObj.getRows();
	    	int desiredRow=findRow(wrkSheetObj,rwCnt ,sOrderID);
	    	System.out.println("The Order is available at the Row"+desiredRow);
	       	workbookWrite = Workbook.createWorkbook(new File(filePath), wrkBookObj);
			sheetWrite = workbookWrite.getSheet("Orders");
			sheetWrite.addCell(new jxl.write.Label(1,desiredRow,sOrderStats));
			workbookWrite.write();	
			workbookWrite.close();
    	}
    	catch(Exception ex)
    	{
    		workbookWrite.write();	
    		workbookWrite.close();
    	}
    		
		
	}*/
    
   
  /*  private static int findRow(Sheet sheet, int ttlRows ,String cellContent) {
       
    	int numCols = sheet.getColumns();        // getColumns() returns an int
    	  for( int i = 0; i <ttlRows; i++) {
    	     Cell column = sheet.getCell(0, i);
    	     if(column.getContents().equalsIgnoreCase(cellContent))
    	    	 return i;
    	  }  
    	  return -1;
    }  */
    
    
    
  public static int findRow(XSSFSheet orders, String cellContent)
  {
        /*
         *  This is the method to find the row number
        */
    	XSSFRow row; 
		XSSFCell cell;
		int rwCnt=0; 
		Iterator rows = orders.rowIterator();
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			XSSFCell crntCell=row.getCell(0);
			rwCnt=rwCnt+1;
			String readCntnt = null;
			readCntnt=crntCell.getStringCellValue();
		   /* switch(crntCell.getCellType()) {
                case 2:
                    System.out.println(crntCell.getBooleanCellValue() + "\t\t");
                    crntCell.getRawValue();
                    break;
                case 0:
                	System.out.println("raw Data :"+crntCell.getRawValue() + "\t\t");
                	System.out.println(crntCell.getRawValue() + "\t\t");
                    break;
                case 1:
                	System.out.println("raw Data :"+crntCell.getRawValue() + "\t\t");
                	System.out.println(crntCell.getStringCellValue() + "\t\t");
                	readCntnt=crntCell.getStringCellValue();
                    break;
            }*/
			if (readCntnt.contains(cellContent))
			{
				System.out.println("The Row Number matched is :"+rwCnt);	
				return rwCnt;
			}
		}
		return -1;
    }
  
  
}
    
 
