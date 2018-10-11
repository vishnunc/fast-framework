package com.qentelli.automation.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.qentelli.automation.accelerators.Actiondriver;
import com.qentelli.automation.accelerators.Base;

public class Reporters extends Actiondriver{

	public static Property configProps=new Property("config.properties");
	static String  timeStamp=Accessories.timeStamp().replace(":", "_").replace(".", "_");

	/**
	 * 
	 * @throws Throwable
	 */
	public static void reportCreater() throws Throwable{
		
		switch (REPORTERTYPE) {
			case 1: HtmlReporters.htmlCreateReport();
				break;
			case 2: HtmlReporters.htmlCreateReport();
					HtmlReporters.createDetailedReport();
				break;
			default: HtmlReporters.htmlCreateReport();
				break;
		}
	}
	
	/**
	 * 
	 * @param strStepName
	 * @param strStepDes
	 * @throws Throwable
	 */
	public static void iterationReport(int iLoop) throws Throwable{


		File file = new File(Base.filePath() + "/" + "Results_"	+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
		Writer writer = null;

		try {
			iStepNo = 0;
			writer = new FileWriter(file, true);
			writer.write("<tr class='content2'>");
			if(iLoop == 1)
			{
			writer.write("<td colspan='2' height='25' ><b> Iteration"+iLoop+"</b></td>");
			writer.write("<td colspan='3' height='25' ><b> Language : "+LANGUAGE+"</b></td>");
			}
			else
			{
				writer.write("<td colspan='2' height='25' ><b> Iteration"+iLoop+"</b></td>");
				writer.write("<td colspan='4' height='25' ><b> Language : "+LANGUAGE+"</b></td>");
			}
			writer.write("</tr> ");
			writer.close();
			Accessories.calculateStepStartTime();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}	

	/**
	 * 
	 * @param strStepName
	 * @param strStepDes
	 * @throws Throwable
	 */
	public static void successReport(String strStepName, String strStepDes) throws Throwable{
		blnExcFlag = true;
		java.util.Date today = new java.util.Date();
		String strTimeStamp = new java.sql.Timestamp(today.getTime()).toString();
		strTimeStamp=strTimeStamp.replace(" ","_").replace(":","_").replace(".", "_");
		String strValue = "";
		if(strStepName.length()>50)
			strValue = strStepName.substring(0, 50);
		else
			strValue = strStepName;
		if(configProps.getProperty("sucessScreenShots").equalsIgnoreCase("true"))
		{
			String testRailImage=strValue.replace("\n", "").replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+strTimeStamp+".png";
			Actiondriver.gf_ScreenShot(Base.filePath()+strValue.replace("\n", "").replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+strTimeStamp+".png");
			Base.gStrCaseResult=Base.gStrCaseResult+"\n"+strStepDes+"\n"+"***"+configProps.getProperty("testRailImagesLoc")+testRailImage+"***";
		}
		else{
			Base.gStrCaseResult=Base.gStrCaseResult+"\n"+strStepDes;
		}
		switch (REPORTERTYPE) {
			case 1: HtmlReporters.onSuccess1(strStepName, strStepDes);
				break;
			case 2: HtmlReporters.onSuccess(strStepName, strStepDes);
				break;
			default: HtmlReporters.onSuccess1(strStepName, strStepDes);
				break;
		}
	}	

	/**
	 * 
	 * @param strStepName
	 * @param strStepDes
	 * @param blnFlag TODO
	 * @throws Throwable
	 */
	public static void failureReport(String strStepName, String strStepDes, Boolean blnFlag) throws Throwable{
		blnExcFlag = false;
		java.util.Date today = new java.util.Date();
		String strTimeStamp = new java.sql.Timestamp(today.getTime()).toString();
		strTimeStamp=strTimeStamp.replace(" ","_").replace(":","_").replace(".", "_");
		String strValue = "";
		if(strStepName.length()>50)
			strValue = strStepName.substring(0, 50);
		else
			strValue = strStepName;
		String testRailImage=strValue.replace("\n", "").replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+strTimeStamp+".png";
		try{
			Actiondriver.gf_ScreenShot(Base.filePath()+strValue.replace("\n", "").replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+strTimeStamp+".png");
		}
		catch(Exception ex){
			
		}
		Base.gStrCaseResult=Base.gStrCaseResult+"\n"+"***"+strStepDes+"***"+"\n"+"***"+configProps.getProperty("testRailImagesLoc")+testRailImage+"***";
		Base.gStrCaseStatus="FAIL";
		switch (REPORTERTYPE) {
			case 1: HtmlReporters.onFailure1(strStepName, strStepDes,strTimeStamp);
				break;	
			case 2: HtmlReporters.onFailure(strStepName, strStepDes,strTimeStamp);
				break;
			default: HtmlReporters.onFailure1(strStepName, strStepDes,strTimeStamp);
				break;
		}
		/*if(blnFlag)
			throw new UserDefinedException();*/
	}
	
	/**
	 * 
	 * @param strStepName
	 * @param strStepDes
	 * @param blnFlag TODO
	 * @throws Throwable
	 */
	public static void failureReportSample(String strStepName, String strStepDes) throws Throwable{
		java.util.Date today = new java.util.Date();
		String strTimeStamp = new java.sql.Timestamp(today.getTime()).toString();
		strTimeStamp=strTimeStamp.replace(" ","_").replace(":","_").replace(".", "_");
		HtmlReporters.onFailure1(strStepName, strStepDes,strTimeStamp);
		}
	
	
	/**
	* This method is used to generate the step report for all type of reports
	* @param strStepName
	* @param strStepDes
	*/
	public static void onSuccessStepReport(String strStepName, String strStepDes) {

	File file = new File(Base.filePath() + "/" + "Results_" + Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
	Writer writer = null;
	try {
	writer = new FileWriter(file, true);
	writer.write("<tr class='content2'>");
	writer.write("<td colspan=5 class='Pass' ><b>" + strStepName + "<b/></td>");
	Accessories.calculateStepExecutionTime();
	writer.write("</tr> ");
	writer.flush();
	writer.close();
	Accessories.calculateStepStartTime();
	}catch (Exception e) {
	e.printStackTrace();
	}

	}
	
/**
 * 
 * @param strStepName
 * @param strStepDes
 * @throws Throwable
 */
public static void singlecolumnReport() throws Throwable{


	File file = new File(Base.filePath() + "/" + "Results_"	+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
	Writer writer = null;

	try {
		iStepNo = 0;
		writer = new FileWriter(file, true);
		writer.write("<tr class='content2'>");
		
		writer.write("<td colspan='4' height='25' ><b>Close the browser</b></td>");
		
		writer.write("</tr> ");
		writer.close();
		Accessories.calculateStepStartTime();
	}catch (Exception e) {
		e.printStackTrace();
	}

}	
}
