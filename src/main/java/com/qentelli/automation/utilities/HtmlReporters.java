package com.qentelli.automation.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.qentelli.automation.accelerators.Base;

public class HtmlReporters {

	public static long iStepStartTime = 0;
	public static long iStepEndTime = 0;
	public static long iStepExecutionTime = 0;
	public static long iTestStartTime = 0;
	public static long iTestEndTime = 0;
	public static long iTestExecutionTime = 0;
	public static long iSuiteStartTime = 0;
	public static long iSuiteEndTime = 0;
	public static double iSuiteExecutionTime = 0;
	public static ArrayList<Double> list = new ArrayList<Double>();
	public static long startStepTime = 0;
	public static long endStepTime = 0;
	public static double stepExecutionTime = 0;

	static String startedAt = "";
	public static String strTestName = "";
	public static String tc_name = "";
	public static String packageName = "";
	public static Map<String, String> map = new LinkedHashMap<String, String>();
	public static Map<String, Long> executionTime = new LinkedHashMap<String, Long>();
	static Property config = new Property("config.properties");
	public static String currentSuit = "";
	public static int pCount = 0;
	public static int fCount = 0;
	public static int intPassNum;
	public static int intFailNum;
	public static String[] key;

	public static long tcStartTime = 0;// Code Change
	public static long tcEndTime = 0;// Code Change
	public static double tcExecutionTime = 0;
	public static List<Long> currentTimeList = Lists.newArrayList();
	static String workingDir = System.getProperty("user.dir").replace(File.separator, "/");

	public static void startTime() {
		tcStartTime = System.currentTimeMillis(); // For Step End time
	}

	public static void createDetailedReport() throws Exception {
		String[] s = Accessories.timeStamp().split(":");
		for (int i = 0; i < s.length - 1; i++) {
			startedAt = startedAt + "-" + s[i];
		}

		startedAt = startedAt.substring(1, startedAt.length());
		startedAt = startedAt.replace(" ", "_");
		workingDir = System.getProperty("user.dir")
				.replace(File.separator, "/");
		File file = new File(Base.filePath() + "/" + "Results_"
				+ Base.timeStampBeforeSutie + ".html");

		Writer writer = new FileWriter(file, true);
		writer.write("<HTML>");
		writer.write("<BODY>");
		writer.write("<script> document.addEventListener('DOMContentLoaded', function() {"
				+ "var url = document.URL; var id = url.split('#'); document.getElementById(id[1]).style.display='inline';"
				+ "}, false); </script>");




		writer.write("<TABLE border=0 cellSpacing=1 cellPadding=1 width='100%'>");
		writer.write("<TR><TD align='right' height = '100' width='20%'>");
		writer.write("<img src='file:///" + workingDir + "/Logos/"
				+ config.getProperty("Client_logo") + ".png' height='70%'></td><td align='center' height ='100' width='60%'>");
		writer.write("<TD align='left' height = '100' width='20%'><img src='file:///" + workingDir + "/Logos/qentelli.png' height='70%' ></td>");
		writer.write("</td></tr>");

		writer.write("<TABLE border=0 cellSpacing=1 cellPadding=1 width='95%'>");
		writer.write("<tr><TD align='center'><H4 align='center'><font color='#000000' face='arial' color='#000000' size=6.5><b>Detailed report</b></H4></TD>");
		writer.write("<TD align='right'> </TD></TR>");

		writer.write("</TABLE>");
		writer.write("</BODY>");
		writer.write("</HTML>");
		writer.flush();
		writer.close();
	}

	@SuppressWarnings("rawtypes")
	public static void createHtmlSummaryReport() throws Exception {

		Property configProps = new Property("config.properties");
		File file = new File(Base.filePath() + "/" + "SummaryResults_"
				+ Base.timeStamp + ".html");// "SummaryReport.html"
		// ElsevierObjects.testresultPath=file.toString();
		Writer writer = null;

		workingDir = System.getProperty("user.dir")
				.replace(File.separator, "/");

		Base.testresultPath = workingDir + "/"
				+ file.toString().replace(File.separator, "/");

		if (file.exists()) {
			file.delete();
		}
		// Create Summary report first table
		writer = new FileWriter(file, true);
		try {
			writer.write("<HTML>");
			writer.write("<HEAD>");

			writer.write("<BODY>");

			writer.write("<TABLE border=0 cellSpacing=1 cellPadding=1 width='100%'>");
			writer.write("<TR><TD align='right' height = '100' width='20%'>");
			writer.write("<img src='file:///" + workingDir + "/Logos/"
					+ config.getProperty("Client_logo") + ".png' height='70%'></td><td align='center' height ='100' width='60%'>");
			writer.write("<TD align='left' height = '100' width='20%'><img src='file:///" + workingDir + "/Logos/qentelli.png' height='70%' ></td>");
			writer.write("</td></tr>");

			writer.write("<TABLE border=0 cellSpacing=1 cellPadding=1 width='100%'>");
			writer.write("<tr><TD align='center'><H4 align='center'><font color='#000000' face='arial' color='#000000' size=6.5><b>Execution Summary Report</b></H4></TD></TR>");
						
			writer.write("<TABLE border=1 align='center' cellSpacing=1 cellPadding=1 width='50%' font='arial'>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Build Version</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ config.getProperty("BuildVersion") + "</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Run ID</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=3><b>"
					+ config.getProperty("RunID") + "</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Run Date&Time</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ Accessories.timeStamp() + "</b></td></tr>");
			writer.write("</table><hr/>");

			writer.write("<DIV style='padding:20px;margin:5px;'>"
					+ "<DIV style='float:left'>"
					+ "<DIV>"
					+ "<TABLE border=1 cellSpacing=1 cellPadding=1 width='100%' font='arial'>");
			writer.write("<tr><td colspan='2' align='center' bgcolor='FFCC99'><FONT COLOR='#000000' FACE='Arial' SIZE=3><b>Environment</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Host Name</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ Accessories.getHostName() + "</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>OS Name</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ System.getProperty("os.name") + "</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>OS Version</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ System.getProperty("os.version") + "</b></td></tr>");
			writer.write("</TABLE>" + "</DIV></br>");

			// Create Summary report Second table
			writer.write("<DIV>");
			writer.write("<TABLE border=1 cellSpacing=1 cellPadding=1 width='100%' font='arial'>");
			writer.write("<TR><TD colspan='2' align='center' bgcolor='FFCC99'><FONT COLOR='#000000' FACE='Arial' SIZE=3><b>Execution Status</b></TD></TR>");

			Iterator<Entry<String, String>> iterator = map.entrySet()
					.iterator();
			pCount = 0;
			fCount = 0;
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				String value = mapEntry.getValue().toString();
				Base.sTestResult = mapEntry.getValue().toString();
				if (value.equals("PASS")) {
					pCount += 1;
				} else {
					fCount += 1;
				}

				writer.write("</tr>");
			}

			writer.write("<tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Suite</b></td>");
			writer.write("<td width=150 align='left'><FONT FACE='Arial' SIZE=2><b>"
					+ currentSuit + "</b></td></tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Executed</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ map.size() + "</b></td></tr>");
			writer.write("<tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Passed</b></td>");
			writer.write("<td width=150 align='left' color='#000000'><FONT FACE='Arial' SIZE=2><b>"
					+ pCount + "</b></td></tr>");
			writer.write("<tr>");
			writer.write("<tr><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>No. of  Scripts Failed</b></td>");
			writer.write("<td width=150 align='left'color='red' ><FONT FACE='Arial' SIZE=2><b>"
					+ fCount + "</b></td></tr>");

			writer.write("<TR><td width=150 align='left'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Suite Execution Time</b></td>");// Newly

//			long ltimeStamp 	= (currentTimeList.get(1)-currentTimeList.get(0))/1000;																										// Report
			writer.write("<TD width=150 align='left'color='red' ><FONT FACE='Arial' SIZE=2><b>"
					+ iSuiteExecutionTime + " secs" + "</b></TD></TR>");
			
			writer.write("</TR>" + "</TABLE>" + "</DIV></br>");
			int iSno = 1;
			// Create Summary report third table
			writer.write("<DIV>");
			writer.write("<TABLE border=1 cellSpacing=1 cellPadding=1 width='170%' font='arial'>");
			writer.write("<tr><td colspan='4' align='center'bgcolor='FFCC99'><FONT COLOR='#000000' FACE='Arial' SIZE=3><b>Summary Report</b></td></tr>");
			writer.write("<td align='center'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Sno</b></td>"
					+"<td align='center'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Test Case</b></td>"
					+ "<td align='center'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Browser Selected</b></td>"
					+ "<td align='center'><FONT COLOR='#000000' FACE='Arial' SIZE=2><b>Status</b></td>");


			Iterator<Entry<String, String>> iterator1 = map.entrySet()
					.iterator();

			while (iterator1.hasNext()) {

				Map.Entry mapEntry1 = (Map.Entry) iterator1.next();
				key = mapEntry1.getKey().toString().split(":");
				String b1 = (String)key[1];
				String sBrowser = b1.toString().split(",")[1];
				String timeStamp = b1.toString().split(",")[2];
				String value = (String) mapEntry1.getValue();
				writer.write("<tr>");
				writer.write("<td><CENTER><FONT color=#000000 size=2 face=Arial><B>"
						+ (iSno++) + "</B></td>");
				writer.write("<td><FONT color=#000000 size=2 face=Arial><B>"
						+ key[1].split(",")[0] + "</B></td>");

				writer.write("<td><FONT color=#000000 size=2 face=Arial><B>"
						+ sBrowser + "</B></td>");


				if (value.equals("PASS")) {
					writer.write("<TD width='30%' bgcolor=green align=center><FONT color=white size=2 face=Arial><B><a href='Results_"
							+ timeStamp
							+ ".html#"
							+ key[1]
									+ "'>"
									+ value
									+ "</a></B></td>");
				} else {
					writer.write("<TD width='30%' bgcolor=red align=center><FONT color=white size=2 face=Arial><B><a href='Results_"
							+ timeStamp
							+ ".html#"
							+ key[1]
									+ "'>"
									+ value
									+ "</a></B></td>");
				}

				// Change
				writer.write("</tr>");
			}
			writer.write("</table bgcolor='#99CCFF'>");

			writer.write("</DIV>");
			writer.write("</DIV>");

			if (configProps.getProperty("PieChartEnable").equalsIgnoreCase(
					"True")) {
				writer.write("<script type='text/javascript' src='piecanvas.js'></script>");
				writer.write("<script type='text/javascript' src='https://www.google.com/jsapi'></script>");
				writer.write("<script type='text/javascript'>"
						+ "google.load('visualization', '1', {packages:['corechart']});"
						+ "google.setOnLoadCallback(drawChart);"
						+ "function drawChart() {"
						+ "var data = google.visualization.arrayToDataTable(["
						+ "['Status', 'Count'],"
						+ "['PASS',     "
						+ pCount
						+ "],"
						+ "['FAIL',      "
						+ fCount
						+ "]"
						+ "]);"
						+ "var options = {"
						+ "title: 'Test Cases Status Chart',"
						+ "is3D: true,"
						+ "chartArea : {left:0,top:0,width:'100%',height:'100%'},"
						+ "colors:['green','red','blue'],};"
						+ "var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
						+ "chart.draw(data, options);" + "}</script>");

				// pie chart

				writer.write("<DIV style='float:right; padding-right:50px;'>");
				writer.write("<colspan='2' align='right'><FONT COLOR='#000000' FACE='Arial' SIZE=2>");
				writer.write("<DIV id='piechart' style='padding:50px 10px 10px 80px;color:#99ccff' ></DIV>"
						+ "</DIV>");

				writer.write("<DIV style=clear:both;'></DIV>" + "</DIV>");

			}
			writer.write("</BODY></HTML>");
			writer.flush();
			writer.close();
			// map.clear();

		} catch (Exception e) {
			writer.flush();
			writer.close();
		}

	}

	// Crate a report file
	public static void htmlCreateReport() throws Exception {
		// map.clear();
		File file = new File(Base.filePath() + "Results_" + Base.timeStampBeforeSutie
				+ ".html");// "Results.html"

		if (file.exists()) {
			file.delete();
		}

	}
	public static void onSuccess(String strStepName,String strStepDes){

		Property configProps=new Property("config.properties");
		File file = new File(Base.filePath() + "/" + "Results_"	+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
		Writer writer = null;



		try{
			String strValue = "";
			if(strStepName.length()>50)
				strValue = strStepName.substring(0, 50);
			else
				strValue = strStepName;
			Base.iStepNo++;
			writer = new FileWriter(file,true); 
				if(configProps.getProperty("sucessScreenShots").equals("false")){
					writer.write("<tr><TD width='50px'><FONT color=#000000 size=2 face=Arial><B><CENTER>" + Base.iStepNo
							+ " </b></td><TD width='300px'><FONT color=#000000 size=2 face=Arial><B>" + strStepName
							+ " </b></td><TD width='600px'><FONT color=#000000 size=2 face=Arial><B>" + strStepDes + "</B></td>"//replaced strStepName instead of strStepDes 
							+ " </td><TD width='100px' bgcolor=green align=center><FONT color=white size=2 face=Arial><B>" + "Passed" + "</td></tr>");


				}
				else{
					writer.write("<tr><TD width='50px'><FONT color=#000000 size=2 face=Arial><B><CENTER>" + Base.iStepNo
							+ " </b></td><TD width='300px'><FONT color=#000000 size=2 face=Arial><B>" + strStepName
							+ " </b></td><TD width='600px'><FONT color=#000000 size=2 face=Arial><B>" + strStepDes + "</B></td>"//replaced strStepName instead of strStepDes 
							+ " </td><TD width='100px' bgcolor=green align=center><FONT color=white size=2 face=Arial><B><a href="+strValue.replace(" ", "_").replace(":", "_")+"_"+Base.timeStamp+".png>" + "Passed" + "</a></td></tr>");
				}	
			writer.flush();
			writer.close();

			if(!map.get(packageName+":"+tc_name).equals("FAIL"))
			{
				map.put(packageName+":"+tc_name, "PASS");
			}
			tcEndTime = System.currentTimeMillis();
			endStepTime = System.currentTimeMillis(); //For Step End time			
			stepExecutionTime = (endStepTime - startStepTime)/1000.000;	//For Step wise execution time	
			writer.write("<TD align=center><FONT color=#000000 size=2 face=Arial><B>" + stepExecutionTime +" secs" +"</B></FONT></TD>"+"</TR>");
		}
		catch(Exception e){

		}
	}

	public static void onFailure(String strStepName, String strStepDes, String strTimeStamp) {
		File file = new File(Base.filePath() + "/" + "Results_"
				+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
		Writer writer = null;

		try {
			String strValue = "";
			if(strStepName.length()>50)
				strValue = strStepName.substring(0, 50);
			else
				strValue = strStepName;
			
			Base.iStepNo++;
			writer = new FileWriter(file, true);
			writer.write("<tr><TD width='50px'><FONT color=#000000 size=2 face=Arial><B><CENTER>"
					+ Base.iStepNo
					+ " </b></td><TD width='300px'><FONT color=#000000 size=2 face=Arial><B>"
					+ strStepName
					+ " </b></td><TD width='600px'><FONT color=#000000 size=2 face=Arial><B>"
					+ strStepDes
					+ "</B></td>"
					+ " </td><TD width='100px' bgcolor=red align=center><FONT color=white size=2 face=Arial><B><a href="
					+ strValue.replace(" ", "_").replace(":", "_")
					.replace("</br>", "_")
					+ "_"
					+ strTimeStamp
					+ ".png>" + "Failed" + "</a></td></tr>");

			writer.flush();
			writer.close();

			map.put(packageName + ":" + tc_name, "FAIL");
			tcEndTime = System.currentTimeMillis();
			endStepTime = System.currentTimeMillis(); // For Step End time
			stepExecutionTime = (endStepTime - startStepTime) / 1000.000; // For
			// Step
			// wise
			// execution
			// time
			writer.write("<TD align=center><FONT color=#000000 size=2 face=Arial><B>"
					+ stepExecutionTime
					+ " secs"
					+ "</B></FONT></TD>"
					+ "</TR>");
		} catch (Exception e) {

		}
	}
	
	public static void onFailureSample(String strStepName, String strStepDes, String strTimeStamp) {
		File file = new File(Base.filePath() + "/" + "Results_"
				+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
		Writer writer = null;

		try {
			Base.iStepNo++;
			writer = new FileWriter(file, true);
			writer.write("<tr><TD width='50px'><FONT color=#000000 size=2 face=Arial><B><CENTER>"
					+ Base.iStepNo
					+ " </b></td><TD width='300px'><FONT color=#000000 size=2 face=Arial><B>"
					+ strStepName
					+ " </b></td><TD width='600px'><FONT color=#000000 size=2 face=Arial><B>"
					+ strStepDes
					+ "</B></td>"
					+ " </td><TD width='100px' bgcolor=red align=center><FONT color=white size=2 face=Arial></td></tr>");

			writer.flush();
			writer.close();

			map.put(packageName + ":" + tc_name, "FAIL");
			tcEndTime = System.currentTimeMillis();
			endStepTime = System.currentTimeMillis(); // For Step End time
			stepExecutionTime = (endStepTime - startStepTime) / 1000.000; // For
			// Step
			// wise
			// execution
			// time
			writer.write("<TD align=center><FONT color=#000000 size=2 face=Arial><B>"
					+ stepExecutionTime
					+ " secs"
					+ "</B></FONT></TD>"
					+ "</TR>");
		} catch (Exception e) {

		}
	}

	public static void testHeader(String strTestName) {
		Writer writer = null;
		try {
			File file = new File(Base.filePath() + "Results_" + Base.timeStampBeforeSutie
					+ ".html");// "Results.html"
			writer = new FileWriter(file, true);
			writer.write("<div name='"
					+ tc_name
					+ "'><TABLE style='margin-left:157px' id = '"
					+ tc_name
					+ "' border=2 BORDERCOLOR=BLACK cellSpacing=1 cellPadding=1 width='80%' font='arial'>");
			writer.write("<tr ><td colspan=4><H4 align=center><FONT color=black size=4 face=Arial><B>"
					+ strTestName + "</B></H4></td></tr>");
			writer.write("<tr><TD width='50px' align=middle><FONT color='#000000' size=2 face='Arial'><B>Step No.</b></td>"
					+"<TD  width='100px' align=middle><FONT color='#000000' size=2 face='Arial'><B>Step Name</b></td>"
					+ "<TD width='200px' align=middle><FONT color=#000000 size=2 face=Arial><B>"
					+ "Step Description</b></td>"
					+ "<TD align=middle><FONT color=#000000 size=2 face=Arial><B>Status</b></td></tr></div>");
			map.put(packageName + ":" + tc_name, "status");
		} catch (Exception e) {

		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	


	/**
	 * 
	 * @throws Exception
	 */
	public static void createHtmlSummaryReport1() throws Exception {
		File file = new File(Base.filePath() + "/" + "SummaryResults_"
				+ Base.timeStamp + ".html");// "SummaryReport.html"
		Writer writer = null;

		if (file.exists()) {
			file.delete();
		}
		writer = new FileWriter(file, true);
		try {
			writer.write("<!DOCTYPE html>");
			writer.write("<html> ");
			writer.write("<head> ");
			writer.write("<meta> ");
			writer.write("<title>AIDemo - Automation Execution Results Summary</title>");

			writer.write("<style type='text/css'>");
			writer.write("body {");
			writer.write("background-color: #FFFFFF; ");
			writer.write("font-family: Verdana, Geneva, sans-serif; ");
			writer.write("text-align: center; ");
			writer.write("} ");

			writer.write("small { ");
			writer.write("font-size: 0.7em; ");
			writer.write("} ");

			writer.write("table { ");
			writer.write("box-shadow: 9px 9px 10px 4px #BDBDBD;");
			writer.write("border: 0px solid #4D7C7B;");
			writer.write("border-collapse: collapse; ");
			writer.write("border-spacing: 0px; ");
			writer.write("width: 1000px; ");
			writer.write("margin-left: auto; ");
			writer.write("margin-right: auto; ");
			writer.write("} ");

			writer.write("tr.heading { ");
			writer.write("background-color: #041944;");
			writer.write("color: #FFFFFF; ");
			writer.write("font-size: 0.7em; ");
			writer.write("font-weight: bold; ");
			writer.write("background:-o-linear-gradient(bottom, #999999 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000) );");
			writer.write("background:-moz-linear-gradient( center top, #999999 5%, #000000 100% );");
			writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);	background: -o-linear-gradient(top,#999999,000000);");
			writer.write("} ");

			writer.write("tr.subheading { ");
			writer.write("background-color: #6A90B6;");
			writer.write("color: #000000; ");
			writer.write("font-weight: bold; ");
			writer.write("font-size: 0.7em; ");
			writer.write("text-align: justify; ");
			writer.write("} ");

			writer.write("tr.section { ");
			writer.write("background-color: #A4A4A4; ");
			writer.write("color: #333300; ");
			writer.write("cursor: pointer; ");
			writer.write("font-weight: bold;");
			writer.write("font-size: 0.8em; ");
			writer.write("text-align: justify;");
			writer.write("background:-o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5) );");
			writer.write("background:-moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100% );");
			writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);	background: -o-linear-gradient(top,#56aaff,e5e5e5);");

			writer.write("} ");

			writer.write("tr.subsection { ");
			writer.write("cursor: pointer; ");
			writer.write("} ");

			writer.write("tr.content { ");
			writer.write("background-color: #FFFFFF; ");
			writer.write("color: #000000; ");
			writer.write("font-size: 0.7em; ");
			writer.write("display: table-row; ");
			writer.write("} ");

			writer.write("tr.content2 { ");
			writer.write("background-color:#;E1E1E1");
			writer.write("border: 1px solid #4D7C7B;");
			writer.write("color: #000000; ");
			writer.write("font-size: 0.7em; ");
			writer.write("display: table-row; ");
			writer.write("} ");

			writer.write("td, th { ");
			writer.write("padding: 5px; ");
			writer.write("border: 1px solid #4D7C7B; ");
			writer.write("text-align: inherit\0/; ");
			writer.write("} ");

			writer.write("th.Logos { ");
			writer.write("padding: 5px; ");
			writer.write("border: 0px solid #4D7C7B; ");
			writer.write("text-align: inherit /;");
			writer.write("} ");

			writer.write("td.justified { ");
			writer.write("text-align: justify; ");
			writer.write("} ");

			writer.write("td.pass {");
			writer.write("font-weight: bold; ");
			writer.write("color: green; ");
			writer.write("} ");

			writer.write("td.fail { ");
			writer.write("font-weight: bold; ");
			writer.write("color: red; ");
			writer.write("} ");

			writer.write("td.done, td.screenshot { ");
			writer.write("font-weight: bold; ");
			writer.write("color: black; ");
			writer.write("} ");

			writer.write("td.debug { ");
			writer.write("font-weight: bold; ");
			writer.write("color: blue; ");
			writer.write("} ");

			writer.write("td.warning { ");
			writer.write("font-weight: bold; ");
			writer.write("color: orange; ");
			writer.write("} ");
			writer.write("</style> ");


			writer.write("<script> ");
			writer.write("function toggleMenu(objID) { ");
			writer.write(" if (!document.getElementById) return;");
			writer.write(" var ob = document.getElementById(objID).style; ");
			writer.write("if(ob.display === 'none') { ");
			writer.write(" try { ");
			writer.write(" ob.display='table-row-group';");
			writer.write("} catch(ex) { ");
			writer.write("	 ob.display='block'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("else { ");
			writer.write(" ob.display='none'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("function toggleSubMenu(objId) { ");
			writer.write("for(i=1; i<10000; i++) { ");
			writer.write("var ob = document.getElementById(objId.concat(i)); ");
			writer.write("if(ob === null) { ");
			writer.write("break; ");
			writer.write("} ");
			writer.write("if(ob.style.display === 'none') { ");
			writer.write("try { ");
			writer.write(" ob.style.display='table-row'; ");
			writer.write("} catch(ex) { ");
			writer.write("ob.style.display='block'; ");
			writer.write("} ");
			writer.write(" } ");
			writer.write("else { ");
			writer.write("ob.style.display='none'; ");
			writer.write("} ");
			writer.write(" } ");
			writer.write("} ");
			writer.write("</script> ");
			writer.write("</head> ");

			writer.write("<body> ");
			writer.write("</br>");

			writer.write("<table id='Logos'>");
			writer.write("<colgroup>");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("</colgroup> ");
			writer.write("<thead> ");

			writer.write("<tr class='content'>");
			writer.write("<th class ='Logos' colspan='2' align='left'>");
			writer.write("<img src='file:///" + workingDir + "/Logos/"+ config.getProperty("Client_logo") + ".png' height='70%'>");
			writer.write("</th>");
			writer.write("<th class = 'Logos' colspan='2' align='right'> ");
			writer.write("<img src='file:///" + workingDir + "/Logos/qentelli.png' height='70%' >");
			writer.write("</th> ");
			writer.write("</tr> ");

			writer.write("</thead> ");
			writer.write("</table> ");

			writer.write("<table id='header'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 40%' /> ");
			writer.write("<col style='width: 60%' /> ");
			//			writer.write("<col style='width: 30%' /> ");
			//			writer.write("<col style='width: 25%' /> ");
			writer.write("</colgroup> ");

			writer.write("<thead> ");

			writer.write("<tr class='heading'> ");
			writer.write("<th colspan='2' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> ");
			writer.write("Automation Execution Result Summary");
			writer.write("</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;Build Version&nbsp;&nbsp;</th> ");
			writer.write("<th> &nbsp;"+config.getProperty("BuildVersion")+"&nbsp;</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;Date&nbsp;&&nbsp;Time&nbsp;&nbsp;</th> ");
			writer.write("<th> &nbsp;"+Accessories.timeStamp()+"&nbsp;</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;OS NAme&nbsp;&nbsp;</th> ");
			writer.write("<th> &nbsp;"+System.getProperty("os.name")+"&nbsp;</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;Suite Executed&nbsp;&nbsp;</th> ");
			writer.write("<th> &nbsp;Regression &nbsp;</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;Suite Name&nbsp;</th> ");
			writer.write("<th> &nbsp;"+currentSuit+"&nbsp;</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th colspan='2' align='left'> ");
			writer.write("&nbsp;Environment -  AIDEMO");
			writer.write("</th> ");
			writer.write("</tr> ");
			writer.write("</thead> ");
			writer.write("</table> ");
			writer.write("<table id='main'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 5%' /> ");
			writer.write("<col style='width: 47%' /> ");
			writer.write("<col style='width: 30%' /> ");
			writer.write("<col style='width: 10%' /> ");
			writer.write("<col style='width: 8%' /> ");
			writer.write("</colgroup> ");
			writer.write("<thead> ");
			writer.write("<tr class='heading'> ");
			writer.write("<th>S.NO</th> ");
			writer.write("<th>Test Case</th> ");
			writer.write("<th>Browser</th> ");
			writer.write("<th>Time</th> ");
			writer.write("<th>Status</th> ");
			writer.write("</tr> ");
			writer.write("</thead> ");
			Iterator<Entry<String, String>> iterator1 = map.entrySet()
					.iterator();
			int serialNo = 1;
			while (iterator1.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry mapEntry1 = (Map.Entry) iterator1.next();
				key = mapEntry1.getKey().toString().split(":");
				String b1 = (String)key[1];
				String sBrowser = b1.toString().split(",")[1];
				String timeStamp = b1.toString().split(",")[2];
				String value = (String) mapEntry1.getValue();

				writer.write("<tbody style='font-size:18px'> ");
				writer.write("<tr class='content2' > ");
				writer.write("<td class='justified' align='center'>" + serialNo + "</td>");
				if (value.equalsIgnoreCase("pass")) {
					writer.write("<td class='justified'><a href='Results_"
							+ timeStamp
							+ ".html#"
							+ b1.toString().split(",")[0]
									+ "'>"
									+ b1.toString().split(",")[0]
											+ "</a>"
											+ "</td>");
				} else if (value.equalsIgnoreCase("fail")) {
					writer.write("<td class='justified'><a href='Results_"
							+ timeStamp
							+ ".html#"
							+ b1.toString().split(",")[0]
									+ "'>"
									+ b1.toString().split(",")[0]
											+ "</a>"
											+ "</td>");
				}
				else
					writer.write("<td class='justified'>"+b1.toString().split(",")[0]+"</td>");
				writer.write("<td class='justified'>" + sBrowser
						+ "</td>");
				writer.write("<td>" + executionTime.get(b1)+ " Seconds</td>");
				if(value.equals("PASS"))
				{
					writer.write("<td class='pass'>"+value+"</td> ");
					pCount++;
				}
				else if(value.equals("FAIL"))
				{
					writer.write("<td class='fail'>"+value+"</td> ");
					fCount++;
				}
				else 
				{
					writer.write("<td class='fail'>Not Executed</td> ");
					fCount++;
				}
				writer.write("</tr>");

				writer.write("</tbody> ");
				serialNo = serialNo + 1;	
			}
			/*if (config.getProperty("PieChartEnable").equalsIgnoreCase(
					"True")) {
				writer.write("<script type='text/javascript' src='piecanvas.js'></script>");
				writer.write("<script type='text/javascript' src='https://www.google.com/jsapi'></script>");
				writer.write("<script type='text/javascript'>"
						+ "google.load('visualization', '1', {packages:['corechart']});"
						+ "google.setOnLoadCallback(drawChart);"
						+ "function drawChart() {"
						+ "var data = google.visualization.arrayToDataTable(["
						+ "['Status', 'Count'],"
						+ "['PASS',     "
						+ pCount
						+ "],"
						+ "['FAIL',      "
						+ fCount
						+ "]"
						+ "]);"
						+ "var options = {"
						+ "title: 'Test Cases Status Chart',"
						+ "is3D: true,"
						+ "chartArea : {left:0,top:0,width:'100%',height:'100%'},"
						+ "colors:['green','red','blue'],};"
						+ "var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
						+ "chart.draw(data, options);" + "}</script>");

				// pie chart

				writer.write("<DIV style='float:right; padding-right:50px;'>");
				writer.write("<colspan='2' align='center'><FONT COLOR='#000000' FACE='Arial' SIZE=2>");
				writer.write("<DIV id='piechart' style='padding:50px 10px 10px 80px;color:#99ccff' ></DIV>"
						+ "</DIV>");

				writer.write("<DIV style=clear:both;'></DIV>" + "</DIV>");
			}*/

			writer.flush();
			writer.close();
			closeSummaryReport1(serialNo);
		} catch (Exception e) {
			writer.flush();
			writer.close();
		}

	}


	/**
	 * 
	 * @param testName
	 */
	public static void testHeader1(String testName) {
		Writer writer = null;
		try {
			strTestName = testName;
			File file = new File(Base.filePath() + "Results_" + Base.timeStampBeforeSutie
					+ ".html");// "Results.html"
			writer = new FileWriter(file, true);

			writer.write("<!DOCTYPE html> ");
			writer.write("<html>");
			writer.write("<head> ");
			writer.write("<meta> ");
			writer.write("<title>" + strTestName
					+ " Execution Results</title> ");

			writer.write("<style type='text/css'> ");
			writer.write("body { ");
			writer.write("background-color: #FFFFFF; ");
			writer.write("font-family: Verdana, Geneva, sans-serif; ");
			writer.write("text-align: center; ");
			writer.write("} ");

			writer.write("small { ");
			writer.write("font-size: 0.7em; ");
			writer.write("} ");

			writer.write("table { ");
			writer.write("box-shadow: 9px 9px 10px 4px #BDBDBD;");
			writer.write("border: 0px solid #4D7C7B; ");
			writer.write("border-collapse: collapse; ");
			writer.write("border-spacing: 0px; ");
			writer.write("width: 1000px; ");
			writer.write("margin-left: auto; ");
			writer.write("margin-right: auto; ");
			writer.write("} ");

			writer.write("tr.heading { ");
			writer.write("background-color: #041944; ");
			writer.write("color: #FFFFFF; ");
			writer.write("font-size: 0.7em; ");
			writer.write("font-weight: bold; ");
			writer.write("background:-o-linear-gradient(bottom, #999999 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000) );");
			writer.write("background:-moz-linear-gradient( center top, #999999 5%, #000000 100% );");
			writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);	background: -o-linear-gradient(top,#999999,000000);");
			writer.write("} ");

			writer.write("tr.subheading { ");
			writer.write("background-color: #FFFFFF; ");
			writer.write("color: #000000; ");
			writer.write("font-weight: bold; ");
			writer.write("font-size: 0.7em; ");
			writer.write("text-align: justify; ");
			writer.write("} ");

			writer.write("tr.section { ");
			writer.write("background-color: #A4A4A4; ");
			writer.write("color: #333300; ");
			writer.write("cursor: pointer; ");
			writer.write("font-weight: bold; ");
			writer.write("font-size: 0.7em; ");
			writer.write("text-align: justify; ");
			writer.write("background:-o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5) );");
			writer.write("background:-moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100% );");
			writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);	background: -o-linear-gradient(top,#56aaff,e5e5e5);");
			writer.write("} ");

			writer.write("tr.subsection { ");
			writer.write("cursor: pointer; ");
			writer.write("} ");

			writer.write("tr.content { ");
			writer.write("background-color: #FFFFFF; ");
			writer.write("color: #000000; ");
			writer.write("font-size: 0.7em; ");
			writer.write("display: table-row; ");
			writer.write("} ");

			writer.write("tr.content2 { ");
			writer.write("background-color: #E1E1E1; ");
			writer.write("border: 1px solid #4D7C7B;");
			writer.write("color: #000000; ");
			writer.write("font-size: 0.75em; ");
			writer.write("display: table-row; ");
			writer.write("} ");

			writer.write("td, th { ");
			writer.write("padding: 5px; ");
			writer.write("border: 1px solid #4D7C7B; ");
			writer.write("text-align: inherit\0/; ");
			writer.write("} ");

			writer.write("th.Logos { ");
			writer.write("padding: 5px; ");
			writer.write("border: 0px solid #4D7C7B; ");
			writer.write("text-align: inherit /;");
			writer.write("} ");

			writer.write("td.justified { ");
			writer.write("text-align: justify; ");
			writer.write("} ");

			writer.write("td.pass { ");
			writer.write("font-weight: bold; ");
			writer.write("color: green; ");
			writer.write("} ");

			writer.write("td.fail { ");
			writer.write("font-weight: bold; ");
			writer.write("color: red; ");
			writer.write("} ");

			writer.write("td.done, td.screenshot { ");
			writer.write("font-weight: bold; ");
			writer.write("color: black; ");
			writer.write("} ");

			writer.write("td.debug { ");
			writer.write("font-weight: bold;");
			writer.write("color: blue; ");
			writer.write("} ");

			writer.write("td.warning { ");
			writer.write("font-weight: bold; ");
			writer.write("color: orange; ");
			writer.write("} ");
			writer.write("</style> ");

			writer.write("<script> ");
			writer.write("function toggleMenu(objID) { ");
			writer.write("if (!document.getElementById) return; ");
			writer.write("var ob = document.getElementById(objID).style; ");
			writer.write("if(ob.display === 'none') { ");
			writer.write("try { ");
			writer.write("ob.display='table-row-group'; ");
			writer.write("} catch(ex) { ");
			writer.write("ob.display='block'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("else { ");
			writer.write("ob.display='none'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("function toggleSubMenu(objId) { ");
			writer.write("for(i=1; i<10000; i++) { ");
			writer.write("var ob = document.getElementById(objId.concat(i)); ");
			writer.write("if(ob === null) { ");
			writer.write("break; ");
			writer.write("} ");
			writer.write("if(ob.style.display === 'none') { ");
			writer.write("try { ");
			writer.write("ob.style.display='table-row'; ");
			writer.write("} catch(ex) { ");
			writer.write("ob.style.display='block'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("else { ");
			writer.write("ob.style.display='none'; ");
			writer.write("} ");
			writer.write("} ");
			writer.write("} ");
			writer.write("</script> ");
			writer.write("</head> ");

			writer.write(" <body> ");
			writer.write("</br>");

			writer.write("<table id='Logos'>");
			writer.write("<colgroup>");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("</colgroup> ");
			writer.write("<thead> ");


			writer.write("<tr class='content'>");
			writer.write("<th class ='Logos' colspan='2' align='left'>");
			writer.write("<img src='file:///" + workingDir + "/Logos/"+ config.getProperty("Client_logo") + ".png' height='70%'>");
			writer.write("</th>");
			writer.write("<th class = 'Logos' colspan='2' align='right'> ");
			writer.write("<img src='file:///" + workingDir + "/Logos/qentelli.png' height='70%' >");
			writer.write("</th> ");
			writer.write("</tr> ");
			writer.write("</thead> ");
			writer.write("</table> ");

			writer.write("<table id='header'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 25%' /> ");
			writer.write("<col style='width: 25%' /> ");
			writer.write("<col style='width: 25%' /> ");
			writer.write("<col style='width: 25%' /> ");
			writer.write("</colgroup> ");

			writer.write(" <thead> ");

			writer.write("<tr class='heading'> ");
			writer.write("<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> ");
			writer.write("**" + strTestName + " Execution Results **");
			writer.write("</th> ");
			writer.write("</tr> ");
			writer.write("<tr class='subheading'> ");
			writer.write("<th>&nbsp;Date&nbsp;&&nbsp;Time&nbsp;&nbsp;</th> ");

			writer.write("<th>" + Accessories.timeStamp()
					+ "</th> ");
			writer.write("<th>&nbsp;Browser&nbsp;&nbsp;</th> ");
			writer.write("<th>"
					+(tc_name.split(",")[1])+ "</th> ");
			writer.write("</tr> ");
			writer.write("</thead> ");
			writer.write("</table> ");

			writer.write("<table id='main'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 5%' /> ");
			writer.write("<col style='width: 26%' /> ");
			writer.write("<col style='width: 51%' /> ");
			writer.write("<col style='width: 8%' /> ");
			writer.write("<col style='width: 10%' /> ");
			writer.write("</colgroup> ");
			writer.write("<thead> ");
			writer.write("<tr class='heading'> ");
			writer.write("<th>S.NO</th> ");
			writer.write("<th>Steps</th> ");
			writer.write("<th>Description</th> ");
			writer.write("<th>Status</th> ");
			writer.write("<th>Time in sec</th> ");
			writer.write("</tr> ");
			writer.write("</thead> ");
			writer.close();
			map.put(packageName + ":" + tc_name, "status");
		} catch (Exception e) {

		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {

			}
		}
	}

	
	/**
	 * 
	 * @param strStepName
	 * @param strStepDes
	 */
	public static void onSuccess1(String strStepName, String strStepDes) {


		File file = new File(Base.filePath() + "/" + "Results_"	+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
		Writer writer = null;
		Base.iStepNo = Base.iStepNo + 1;

		try {
			String strValue = "";
			if(strStepName.length()>50)
				strValue = strStepName.substring(0, 50);
			else
				strValue = strStepName;
			//testdescrption.put(TestTitleDetails.x.toString(), TestEngine.testDescription.get(TestTitleDetails.x));
			if (!map.get(packageName + ":" + tc_name).equals("FAIL")) {
				map.put(packageName + ":" + tc_name, "PASS");
				//map.put(TestTitleDetails.x.toString(), TestEngine.testDescription.get(TestTitleDetails.x.toString()));
			}
			writer = new FileWriter(file, true);
			writer.write("<table id='main'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 5%' /> ");
			writer.write("<col style='width: 26%' /> ");
			writer.write("<col style='width: 51%' /> ");
			writer.write("<col style='width: 8%' /> ");
			writer.write("<col style='width: 10%' /> ");
			writer.write("</colgroup> ");
			writer.write("<tr class='content2' >");
			writer.write("<td>" + Base.iStepNo + "</td> ");
			writer.write("<td class='justified'>" + strStepName + "</td>");
			writer.write("<td class='justified'>" + strStepDes + "</td> ");
			if(config.getProperty("sucessScreenShots").equalsIgnoreCase("true"))
				writer.write("<td class='Pass' align='center'><a  href="
						+ strValue.replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+Base.timeStamp+".png>"
						+"<img  src='file:///" + workingDir + "/Logos/passed.ico' width='18' height='18'/></a></td> ");
			else
				writer.write("<td class='Pass' align='center'><img  src='file:///" + workingDir + "/Logos/passed.ico' width='18' height='18'/></td> ");
			intPassNum  =intPassNum + 1;
			//			String strPassTime = Actiondriver.gf_GetCurrentTime();
			Accessories.calculateStepExecutionTime();
			writer.write("<td>" + iStepExecutionTime + "</td> ");
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
	 */
	public static void onFailure1(String strStepName, String strStepDes, String strTimeStamp) {
		Writer writer = null;
		try {
			String strValue = "";
			if(strStepName.length()>50)
				strValue = strStepName.substring(0, 50);
			else
				strValue = strStepName;
			
			File file = new File(Base.filePath() + "/" + "Results_"	+ Base.timeStampBeforeSutie + ".html");// "SummaryReport.html"
			Base.iStepNo = Base.iStepNo + 1;

			writer = new FileWriter(file, true);
			writer.write("<table id='main'> ");
			writer.write("<colgroup> ");
			writer.write("<col style='width: 5%' /> ");
			writer.write("<col style='width: 26%' /> ");
			writer.write("<col style='width: 51%' /> ");
			writer.write("<col style='width: 8%' /> ");
			writer.write("<col style='width: 10%' /> ");
			writer.write("</colgroup> ");
			writer.write("<tr class='content2' >");
			writer.write("<td>" +Base.iStepNo+ "</td> ");
			writer.write("<td class='justified'>" + strStepName + "</td>");
			writer.write("<td class='justified'>" + strStepDes + "</td> ");
			intFailNum = intFailNum + 1;


			writer.write("<td class='Fail' align='center'><a  href="
					+ strValue.replace(" ", "_").replace(":", "_").replace("</br>", "_")+"_"+strTimeStamp+".png>"
					+ "<img  src='file:///" + workingDir + "/Logos/failed.ico' width='18' height='18'/></a></td>");

			//			String strFailTime = Actiondriver.gf_GetCurrentTime();
			Accessories.calculateStepExecutionTime();
			writer.write("<td>" + iStepExecutionTime + "</td> ");
			writer.write("</tr> ");
			writer.close();
			Accessories.calculateStepStartTime();
//			if (!map.get(packageName + ":" + tc_name).equalsIgnoreCase("PASS")) {
				map.put(packageName + ":" + tc_name, "FAIL");
				//map.put(TestTitleDetails.x.toString(), TestEngine.testDescription.get(TestTitleDetails.x.toString()));
//			}
		} catch (Exception e) {

		}

	}

	/**
	 * 
	 */
	public static void closeDetailedReport1() {

		File file = new File(Base.filePath() + "/" + "Results_"
				+ Base.timeStampBeforeSutie + ".html");
		Writer writer = null;

		try {
			writer = new FileWriter(file, true);
			writer.write("</table>");
			writer.write("<table id='footer'>");
			writer.write("<colgroup>");
			writer.write("<col style='width: 21%' />");
			writer.write("<col style='width: 10%' />");
			writer.write("<col style='width: 24%' />");
			writer.write("<col style='width: 10%' />");
			writer.write("<col style='width: 25%' />");
			writer.write("<col style='width: 10%' />");
			writer.write("</colgroup>");
			writer.write("<tfoot>");
			/*writer.write("<tr class='heading'> ");
			writer.write("<th colspan='6'>Execution Time In Seconds (Includes Report Creation Time) : "
					+ executionTime.get(tc_name)+ "&nbsp;</th> ");
			writer.write("</tr> ");*/
			writer.write("<tr class='content' style='font-size:12px'>");
			writer.write("<td class='pass'>&nbsp;Steps Executed&nbsp;</td>");
			writer.write("<td class='pass'> " + Base.iStepNo
					+ "</td>");
			writer.write("<td class='pass'>&nbsp;Steps Passed&nbsp;</td>");
			writer.write("<td class='pass'> " + intPassNum
					+ "</td>");
			writer.write("<td class='fail'>&nbsp;Steps Failed&nbsp;</td>");
			writer.write("<td class='fail'>" + intFailNum
					+ "</td>");
			writer.write("</tr>");
			writer.close();
			intPassNum=0;
			intFailNum=0;
		} catch (Exception e) {

		}
	}
	
	public static void testCaseExecutionTime() {

		File file = new File(Base.filePath() + "/" + "Results_"
				+ Base.timeStampBeforeSutie + ".html");
		Writer writer = null;

		try {
			writer = new FileWriter(file, true);
			writer.write("</table>");
			writer.write("<table id='footer'>");
			writer.write("<tfoot>");
			writer.write("<tr class='heading'> ");
			writer.write("<th colspan='6'>Execution Time In Seconds (Includes Report Creation Time) : "
					+ executionTime.get(tc_name)+ "&nbsp;</th> ");
			writer.write("</tr> ");
			writer.close();
			intPassNum=0;
			intFailNum=0;
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 */
	public static void closeSummaryReport1(int iSerialNo) {
		File file = new File(Base.filePath() + "/" + "SummaryResults_"
				+ Base.timeStamp + ".html");
		Writer writer = null;
		try {
			writer = new FileWriter(file, true);

			writer.write("<table id='footer'>");
			writer.write("<colgroup>");
			writer.write("<col style='width: 20%' />");
			writer.write("<col style='width: 14%' />");
			writer.write("<col style='width: 20%' />");
			writer.write("<col style='width: 13%' />");
			writer.write("<col style='width: 20%' /> ");
			writer.write("<col style='width: 13%' /> ");
			writer.write("</colgroup> ");
			writer.write("<tfoot>");
			writer.write("<tr class='heading'>");
			writer.write("<th colspan='6'>Total Duration  In Seconds (Including Report Creation) : "
					+ ((int)iSuiteExecutionTime) + "</th>");
			writer.write("</tr>");
			writer.write("<tr class='content' style='font-size:12px'>");
			writer.write("<td class='pass'>&nbsp;Total TestCases&nbsp;</td>");
			writer.write("<td class='pass'> " + (iSerialNo-1)
					+ "</td> ");
			writer.write("<td class='pass'>&nbsp;Tests Passed&nbsp;</td>");
			writer.write("<td class='pass'> " + pCount
					+ "</td> ");
			writer.write("<td class='fail'>&nbsp;Tests Failed&nbsp;</td>");
			writer.write("<td class='fail'> " + fCount
					+ "</td> ");
			writer.write("</tr>");
			writer.write("</tfoot>");
			writer.write("</table> ");
			writer.close(); 
		}catch (Exception e) {

		}
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
			intFailNum = intFailNum + 1;
			writer = new FileWriter(file, true);
			writer.write("<tr class='content2'>");
			writer.write("<td colspan=5 class='Pass' ><b>" + strStepName + "<b/></td>");
			Accessories.calculateStepExecutionTime();
			writer.write("</tr> ");
			writer.flush();
			writer.close();
			Accessories.calculateStepStartTime();
			map.put(packageName + ":" + tc_name, "FAIL");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
