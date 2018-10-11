package com.qentelli.automation.datadriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.google.common.collect.Maps;
import com.qentelli.automation.accelerators.Base;

public class ReadResourceData {

	@SuppressWarnings("rawtypes")
	public static Map<String, List> dataMap = Maps.newHashMap();
	public static Map<String, String> IntranetCredentialsMap = Maps.newHashMap();
	public static Map<String, String> EmailDetailsMap = Maps.newHashMap();

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws BiffException 
	 */
	public static void readResourceData() throws IOException, BiffException{
		FileInputStream file = new FileInputStream("TestData\\"+Base.configProps.getProperty("ResourceFilePath"));
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet("ResourceData");
		int iRowCount =sheet.getRows();
		int iColCount =sheet.getColumns();
		int i=1,j=1;
		for(i=1;i<iRowCount;i++){
			List<String> lData = new ArrayList<String>();
			for(j=1;j<iColCount;j++)
				lData.add(sheet.getCell(j, i).getContents());			
			dataMap.put(sheet.getCell(0,i).getContents(),lData);
		}		
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void readIntranetCredentialsData() throws IOException, BiffException{

		FileInputStream file = new FileInputStream("TestData\\"+Base.configProps.getProperty("Credentials"));
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet("IntranetCredentials");
		int iRowCount =sheet.getRows();
		for(int i=1;i<iRowCount;i++){		
			IntranetCredentialsMap.put(sheet.getCell(0,i).getContents(),sheet.getCell(1,i).getContents());
		}		
	}

	/**
	 * 
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void readEmailDetailsData() throws IOException, BiffException{

		FileInputStream file = new FileInputStream("TestData\\"+Base.configProps.getProperty("Credentials"));
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet("EmailDetails");
		int iRowCount =sheet.getRows();
		for(int i=1;i<iRowCount;i++){		
			EmailDetailsMap.put(sheet.getCell(0,i).getContents(),sheet.getCell(1,i).getContents());
		}		
	}
	
	/**
	 * 
	 * @param sKey
	 * @param dynamicValue
	 * @return
	 */
	public static String getResourceData(String sKey, String sPosition, String dynamicValue){
		String resourceName="";
		if(sKey!=null && !sKey.equalsIgnoreCase("")){
			if("Desc".equalsIgnoreCase(sPosition))
			{
				resourceName=dataMap.get(sKey).get(0).toString();
				resourceName =resourceName.replaceAll(",",dynamicValue);
			}
			else if("Pass".equalsIgnoreCase(sPosition))
			{
				resourceName=dataMap.get(sKey).get(1).toString();
				resourceName =resourceName.replaceAll(",",dynamicValue);
			}
			else if("Fail".equalsIgnoreCase(sPosition))
			{
				resourceName=dataMap.get(sKey).get(2).toString();
				resourceName =resourceName.replaceAll(",",dynamicValue);
			}
		}
		return resourceName;
	}
}