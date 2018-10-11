package com.qentelli.automation.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.google.gson.JsonArray;
import com.qentelli.automation.accelerators.Base;

public class TestNGExecution2 {
	public static Property configProps = new Property("config.properties");
	public static String currentModule = null;
	public static void main(String[] args) throws Throwable {
	String FilePath = System.getProperty("user.dir") + "\\Macros\\"	+ configProps.getProperty("MacroFile");
	FileInputStream fs = new FileInputStream(FilePath);
	ArrayList<String> moduleName = new ArrayList<String>();
	LinkedHashMap<String, List<String>> map = new LinkedHashMap<String, List<String>>();
	FileInputStream updatefile = new FileInputStream(new File(FilePath));
	moduleName = ExcelLib.getModules("Modules");
	JSONArray testIDs = new JSONArray();
	Base.json.clear();
	for (String module : moduleName) {
		map.put(module.toString(), ExcelLib.getTestCases(module.toString()));
	}
	for(String module : moduleName){
		try { 
			currentModule = module;	
			//Accessories.calculateSuiteStartTime();
			Base.timeStampBeforeSutie = Accessories.timeStamp().replace(" ", "_").replace(":", "_").replace(".", "_");
			XmlSuite suite = new XmlSuite();
			suite.setName(module);
			suite.addListener("com.qentelli.automation.utilities.TestListener");
			for (int i = 0; i <= map.get(module).size() - 1; i++) {
				XmlTest test = new XmlTest(suite);
				test.setName(map.get(module).get(i).toString());
				String browser="firefox";
				if(map.get(module).get(i).toString().contains("Chrome")){
					browser="Chrome";
				}
				test.addParameter("browser", browser);
				test.setPreserveOrder("true");
				List<XmlClass> classes = new ArrayList<XmlClass>();
				XmlClass testClass = new XmlClass();
				testClass = new XmlClass();
				testClass.setName("com.aidemo."+"scripts."+map.get(module).get(i).toString());
				classes.add(testClass);
				testIDs.add(map.get(module).get(i).toString().split("_")[0].replaceFirst("C",""));
				test.setXmlClasses(classes);
		    }
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG tng = new TestNG();
			tng.setXmlSuites(suites);
			Base.json=testIDs;
			tng.run();
			Base.close();
			Base.sTestResult = "";
			Base.sBrowserType = "";
			Base.testresultPath = "";
			Base.timeStampBeforeSutie="";
			Base.timeStamp="";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		updatefile.close();
		HtmlReporters.currentTimeList.add(System.currentTimeMillis());
	}
	//HtmlReporters.currentTimeList.add(System.currentTimeMillis());
	//Base.tearDown();
  }
}
		
