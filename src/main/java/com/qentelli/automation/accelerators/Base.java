package com.qentelli.automation.accelerators;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.google.common.collect.Maps;
import com.qentelli.automation.utilities.APIClient;
import com.qentelli.automation.utilities.APIException;
import com.qentelli.automation.utilities.Accessories;
import com.qentelli.automation.utilities.HtmlReporters;
import com.qentelli.automation.utilities.MyListener;
import com.qentelli.automation.utilities.Property;
import com.qentelli.automation.utilities.Reporters;
import com.qentelli.automation.utilities.SendMail;

public class Base {
	public static Property configProps = new Property("config.properties");

	public static String method = "";
	public static WebDriver driver;;
	//public static EventFiringWebDriver driver;
	public static int count = 1;
	public static int x = 0;
	public static String sBrowserType = "";
	public static String sTestResult;
	public static String testresultPath;
	public static int iStepNo = 0;
	public static String sBrowser;
	public static ITestContext itc;
	public static long timeStampBeforeExecution;
	public static Map<String, String[]> ResourceData = Maps.newHashMap();

	public static Logger logger = Logger.getLogger("Static_Log");
	public static FileHandler fh;
	public static String currentTestname; 
	public static String timeStamp = Accessories.timeStamp().replace(" ", "_").replace(":", "_").replace(".", "_");
	public static String timeStampBeforeSutie = Accessories.timeStamp().replace(" ", "_").replace(":", "_").replace(".", "_");

	public static APIClient client = new APIClient(configProps.getProperty("TRURL"));
	public static boolean gAccessTestRail = Boolean.valueOf(configProps.getProperty("UpdateTestRail"));
	public static boolean gCloseTestRun = Boolean.valueOf(configProps.getProperty("CloseTestRun"));
	public static String RunID = null;
	public static JSONArray json = new JSONArray();
	public static String gStrCaseResult = null;
	public static String gStrCaseStatus = "PASS";
	public static String gCurrentTestRun = null;
	public static String gTestCaseId = null;
	public static Exception aiException=null;
	public static ResultSet rs=null;

	@BeforeSuite(alwaysRun = true)
	public void setupSuite(ITestContext ctx) throws Throwable {
		itc = ctx;
		if (sBrowserType.equalsIgnoreCase("") || sBrowserType == null)
		 Accessories.calculateSuiteStartTime();
		timeStampBeforeExecution = System.currentTimeMillis();
		Reporters.reportCreater();
		HtmlReporters.currentSuit = ctx.getCurrentXmlTest().getSuite().getName();
		if (gAccessTestRail) {
			CreateTestRunInTestRail();
			System.out.println("Created Test Run in Rail");
		}
		// getTestRailData();
		// ReadResourceData.readResourceData();
		// Actiondriver.gf_IntranetCredentials();

	}

	// @AfterTest
	public static void close() {
		if (gAccessTestRail && gCloseTestRun) {
			CloseTestRun();
		}
		// driver.close();
		// driver.quit();

	}

	/**
	 * This method void
	 */
	public static boolean WriteTestResultToTestRail() {
		Map data = new HashMap();
		int status = 0;

		switch (gStrCaseStatus) {
		case "PASS":
			status = 1;
			break;
		case "FAIL":
			status = 5;
			break;
		default:
			status = 4;
			break;
		}
		data.put("status_id", status);
		data.put("custom_environment", new Integer(1));
		data.put("custom_browser", new Integer(4));
		data.put("custom_country", new Integer(5));
		data.put("custom_language", new Integer(4));
		data.put("comment", gStrCaseResult);
		try {

			JSONObject rs = (JSONObject) client.sendPost("add_result_for_case/"
					+ gCurrentTestRun + "/" + gTestCaseId, data);
			// return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return true;

	}

	public static void CloseTestRun() {

		Map reqRunData = new HashMap();
		try {
			JSONObject rs = (JSONObject) client.sendPost("close_run/"
					+ gCurrentTestRun, reqRunData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * De-Initializing and closing all the connections
	 * 
	 * @throws Exception
	 * @throws Throwable
	 */
	
	//@BeforeTest
	@Parameters({"browser"})
	@BeforeClass
	public void openBrowser(String browser) throws Throwable {
		aiException=null;
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		try {
			sBrowserType=browser;
			if (sBrowserType.equalsIgnoreCase("") || sBrowserType == null)
				sBrowser = configProps.getProperty("browserType");
			else
				sBrowser = browser;
			if(sBrowser.equalsIgnoreCase("windows"))
			{
				 DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability("app", "C:/Retail Plus 40/Plus40.exe");
					caps.setCapability("debugConnectToRunningApp", false);
				   driver = new RemoteWebDriver(new URL("http://localhost:9999/"), caps);
					// driver=new RemoteWebDriver(new URL("http://127.0.0.1:4723/"), caps);
				   
				   System.out.println("Driver Set Up Completed...'");
					 return;
			}
			System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.Jdk14Logger");
			if (sBrowser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			} else if (sBrowser.equalsIgnoreCase("ie")) {
				File file = new File("Drivers\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
			} else if (sBrowser.equalsIgnoreCase("chrome")) {
				System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.Jdk14Logger");
				System.setProperty("webdriver.chrome.driver","asmla\\Drivers\\chromedriver.exe");
				//ChromeOptions options = new ChromeOptions();
				//options.addArguments("--test-type");
				//webDriver = new ChromeDriver(options);
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			}
			/*driver = new EventFiringWebDriver(webDriver);
			MyListener myListener = new MyListener();
			driver.register(myListener);*/
			driver.manage().deleteAllCookies();
			int implicitWaitTime = 20;
			if ("ie".equalsIgnoreCase(configProps.getProperty("browserType"))) {
				driver.manage().timeouts().implicitlyWait(implicitWaitTime * 2, TimeUnit.SECONDS);
			} else {
				driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			System.out.println("Excpetion "+e.getMessage());
			aiException=e;
		}
	}

	@AfterSuite(alwaysRun = true)
	public static void tearDown() throws Throwable {
		Accessories.calculateSuiteExecutionTime();
		if (Actiondriver.REPORTERTYPE == 1)
			HtmlReporters.createHtmlSummaryReport1();
		else
			HtmlReporters.createHtmlSummaryReport();
		// open the result file
		String dir = configProps.getProperty("openResult"); // path to your new
															// file
		File fl = new File(dir);
		File[] folders = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choise = null;
		for (File folder : folders) {
			if (folder.lastModified() > lastMod) {
				choise = folder;
				lastMod = folder.lastModified();
			}
		}
		dir = "" + choise;
		fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		lastMod = Long.MIN_VALUE;
		choise = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choise = file;
				lastMod = file.lastModified();
			}
		}
		dir = "" + choise;
		File htmlFile = new File(dir);
		Desktop.getDesktop().browse(htmlFile.toURI());
		try{
			driver.quit();
		}
		catch(Exception ex){
			
		}
		if (configProps.getProperty("MailTransfer").equalsIgnoreCase("True"))
			SendMail.attachReportsToEmail();
	}

	public static String filePath() {
		String strDirectoy = "ResultFile";
		new File(configProps.getProperty("screenShotPath") + strDirectoy + "_"
				+ timeStamp).mkdirs();
		return configProps.getProperty("screenShotPath") + strDirectoy + "_"
				+ timeStamp + "\\";
	}

	@BeforeMethod(alwaysRun = true)
	public void reportHeader(Method method) {
		gStrCaseResult = "";
		gTestCaseId = null;
		gStrCaseStatus = "PASS";
		iStepNo = 0;
		Accessories.calculateTestCaseStartTime();
		HtmlReporters.tc_name = method.getName().toString() + "," + sBrowser
				+ "," + Base.timeStampBeforeSutie;
		String[] ts_Name = this.getClass().getName().toString().split("\\.");
		HtmlReporters.packageName = ts_Name[0] + "." + ts_Name[1] + "."
				+ ts_Name[2];
		if (Actiondriver.REPORTERTYPE == 1)
			HtmlReporters.testHeader1("Script Name : "
					+ method.getName().toString());
		else
			HtmlReporters.testHeader("Script Name : "
					+ method.getName().toString());
		Accessories.calculateStepStartTime();
		gTestCaseId = method.getName().toString().split("_")[0].replaceFirst(
				"C", "");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownAfterMethod() {
		Accessories.calculateTestCaseExecutionTime();
		HtmlReporters.executionTime.put(HtmlReporters.tc_name,HtmlReporters.iTestExecutionTime);
		HtmlReporters.testCaseExecutionTime();
		if (Actiondriver.REPORTERTYPE == 1)
			HtmlReporters.closeDetailedReport1();
		if (gAccessTestRail) {
			//WriteTestResultToTestRail();
		}
		try{
		driver.quit();
		}
		catch(Exception ex){
			
		}
	}

	/**
	 * This method
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 *            void
	 */
	public static void UpdateTestResultInRail(String string, String string2,
			String string3) {
		// TODO Auto-generated method stub

	}

	/*
	 * @BeforeSuite(alwaysRun = true)
	 *//***
	 * This Method runs before suite runs
	 */
	/*
	 * public static void LogFileCreation() {
	 * logger.setUseParentHandlers(false); try { String strDateTime =
	 * Actiondriver
	 * .gf_GetCurrentDate("dd/MM/yyyy")+"_"+Actiondriver.gf_GetCurrentDate
	 * ("hh:mm:ss"); String strFile = configProps.getProperty("LogFileName");
	 * strFile = strFile.replace(".", "_"+strDateTime+"."); fh=new
	 * FileHandler(strFile.replace("/", "").replace(":", "")); // Object of the
	 * log file logger.addHandler(fh); LogFormatter formatter = new
	 * LogFormatter(); fh.setFormatter(formatter); // Format }catch
	 * (SecurityException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	public static void CreateTestRunInTestRail() {
		// Connect to Test Rail

		client.setUser(configProps.getProperty("TRUserName"));
		client.setPassword(configProps.getProperty("TRPassword"));
		String TRRunName = configProps.getProperty("TRSuiteType") + "_"
				+ configProps.getProperty("browserType") + timeStampBeforeSutie;
		// * Add A New Test Run
		Map testRun = new HashMap();
		testRun.put("include_all", false);
		testRun.put("description", "This is the New test run description");
		testRun.put("name", TRRunName);
		testRun.put("case_ids", json);
		try {
			JSONObject r = (JSONObject) client.sendPost("add_run/"
					+ configProps.getProperty("TRProject"), testRun);
			if (!verifyTestRunInRail(TRRunName)) {
				System.out
						.println("Failed to Create the Test Run " + TRRunName);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean verifyTestRunInRail(String TRRunName) {
		boolean flag = false;
		// Read All the Test Runs
		JSONArray getRuns = null;
		String projectID=configProps.getProperty("TRProject");
		try {
			getRuns = (JSONArray) client.sendGet("get_runs/"+projectID);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < getRuns.size(); i++) {
			JSONObject obj = (JSONObject) getRuns.get(i);
			System.out.println(obj.get("name"));
			if (TRRunName.equals(obj.get("name"))) {
				gCurrentTestRun = obj.get("id").toString();
				return true;
			}
		}

		return flag;
	}
}