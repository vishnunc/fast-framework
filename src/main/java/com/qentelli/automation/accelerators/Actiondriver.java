package com.qentelli.automation.accelerators;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import jxl.Sheet;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.ITestContext;

import com.qentelli.automation.datadriven.ReadResourceData;
import com.qentelli.automation.utilities.HtmlReporters;
import com.qentelli.automation.utilities.MyListener;
import com.qentelli.automation.utilities.Reporters;
import com.qentelli.automation.utilities.UserDefinedException;

/**
 * All customized/ Generic functions
 * 
 * 
 */
public class Actiondriver extends Base {

	public static WebDriverWait wait;
	public static String gStrErrMsg = " ";
	public static boolean blnExcFlag = false;
	public static Logger log = Logger.getLogger(Actiondriver.class.getName());
	// Application Data
	public static final int REPORTERTYPE = Integer.parseInt(configProps.getProperty("reportsType"));
	public static String LANGUAGE;
	public static String DOWNLOADLOCATION;
	public static String INTRANET_URL_Test;
	public static String INVOICING_URL_Test;
	public static String PAYMENTS_URL_Test;
	public static String INTRANET_URL_Dev;
	public static String INVOICING_URL_Dev;
	public static String PAYMENTS_URL_Dev;
	public static String IUSER_ID;
	public static String IPASSWORD;
	public static String IUSER_NAME;
	public static String ILANGUAGE;
	// Workbook data
	public static Sheet objInputSheet = null; // Declare sheet objects to input sheet and assertion sheet
	public static Sheet objExigoInputSheet = null;
	/*public static final String NERIUMWORKBOOK = "TestData\\"+configProps.getProperty("NeriumWorkbook").toString(); // Client module input sheet file location
	public static final String EXIGOWORKBOOK = "TestData\\"+configProps.getProperty("ExigoWorkbook").toString(); // Client module input sheet file location
*/	// Synchronization data
	public static long lWait_VSlow = Long.valueOf(configProps.getProperty("Wait_VSlow").toString());
	public static long lWait_Slow = Long.valueOf(configProps.getProperty("Wait_Slow").toString());
	public static long lWait_Medium = Long.valueOf(configProps.getProperty("Wait_Medium").toString());
	public static long lWait_Fast = Long.valueOf(configProps.getProperty("Wait_Fast").toString());
	public static long lSleep_Low = Long.valueOf(configProps.getProperty("Sleep_Low").toString());
	public static long lSleep_Medium = Long.valueOf(configProps.getProperty("Sleep_Medium").toString());
	public static long lSleep_High = Long.valueOf(configProps.getProperty("Sleep_High").toString());
	public static int iSleep_Low = Integer.valueOf(configProps.getProperty("Sleep_Low").toString());
	public static int iSleep_Medium = Integer.valueOf(configProps.getProperty("Sleep_Medium").toString());
	public static int iSleep_High = Integer.valueOf(configProps.getProperty("Sleep_High").toString());
	public static long lSleep_VHigh = Long.valueOf(configProps.getProperty("Sleep_VeryHigh").toString());
	public static long lEmail_Wait = Long.valueOf(configProps.getProperty("Email_Wait").toString());
	public static String Tabname = "";
	public static String Tabletabs = "";
	public static int index = 0;
	public static boolean blnEventReport = true;
	public static String strGlobalValue = "";
	public static String url = configProps.getProperty("NeriumURL");
	public static String adourl = configProps.getProperty("neriumADOManager");
	public static String neriumhomeurl = configProps.getProperty("neriumCorporate");

	/****************************************************************************************************************************
	 * Function Name		:	gf_Click()
	 * Description			:	This method is used to launch the application
	 * @param objLocator	: Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName: Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return boolean (true or false)
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_Click(By objLocator, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try{	
			System.out.println("Click Item "+strLocatorName);
				if(driver.findElement(objLocator).isDisplayed()){
					System.out.println(driver.findElement(objLocator).getAttribute("AutoamtionId"));
					
					driver.findElement(objLocator).click();
					blnFlag = true;
			}
			return blnFlag;
		}
		catch (Exception e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click on "+strLocatorName,"Failed to click on "+strLocatorName, false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click on "+strLocatorName,"Successfully clicked on "+strLocatorName );
		}
	}


	/****************************************************************************************************************************
	 * Function Name : gf_Click() Description : This method is used to launch
	 * the application
	 * 
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * @return boolean (true or false)
	 * @throws Throwable
	 ****************************************************************************************************************************/
	public static boolean gf_Click_one(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			if (driver.findElement(objLocator).isDisplayed()) {
				driver.findElement(objLocator).click();
				blnFlag = true;
			}

			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} finally {
			if (!blnFlag) {
				Reporters.failureReport("Click on " + strLocatorName,"Failed to click on " + strLocatorName, false);
			} else {
				Reporters.successReport("Click on " + strLocatorName,"Successfully clicked on " + strLocatorName);
			}
		}
	}

	/****************************************************************************************************************************
	 * Function Name		:	gf_Type()
	 * Description			:	This method used type value in to text box or text area
	 * @param objLocator	: 	Action to be performed on element (Get it from Object repository)
	 * @param strTestdata	: 	Value wish to type in text box / text area
	 * @param strLocatorName: 	Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * @throws Throwable
	 *****************************************************************************************************************************/
	public static boolean gf_Type(By objLocator, String strTestdata, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
		//	driver.findElement(objLocator).clear();
			driver.findElement(objLocator).click();
			driver.findElement(objLocator).sendKeys(strTestdata);
			blnFlag = true;
			return blnFlag;
		}
		/*catch (Exception e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}*/
		finally
		{
			if(!(strTestdata.equalsIgnoreCase("")||strTestdata==null))
			{
				if(!blnFlag)
					Reporters.failureReport("Enter data in "+strLocatorName,"Failed to enter data. ", false );
				else if(blnEventReport && blnFlag)
					Reporters.successReport("Enter data in "+strLocatorName,"Data '"+strTestdata+"' entered successfully.");
			}
		}
	}
	
	/****************************************************************************************************************************
	 * Function Name : gf_Type() Description : This method used type value in to
	 * text box or text area
	 * 
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strTestdata
	 *            : Value wish to type in text box / text area
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * @throws Throwable
	 *****************************************************************************************************************************/
	public static boolean gf_ClearTextBox(By objLocator, String strLocatorName)	throws Throwable {
		boolean blnFlag = false;
		try {
			driver.findElement(objLocator).clear();
			//Assert.assertEquals("", driver.findElement(objLocator).getText());
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} finally {

			if (!blnFlag) {
				Reporters.failureReport("Clear Text Box","Not cleared text box:" + strLocatorName, false);
			} else {
				Reporters.successReport("Clear Text Box", "Cleared text box:" + strLocatorName + " successfully.");
			}

		}
	}


	/****************************************************************************************************************************
	 *Function Name			:	gf_TypeAndVerify()
	 * Description			:	This method used type value in to text box or text area and verify value in textbox
	 * @param objLocator	: 	Action to be performed on element (Get it from Object repository)
	 * @param strTestdata	: 	Value wish to type in text box / text area
	 * @param strLocatorName: 	Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * @throws Throwable
	 *****************************************************************************************************************************/
	public static boolean gf_TypeAndVerify(By objLocator, String strTestdata, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			driver.findElement(objLocator).clear();
			driver.findElement(objLocator).sendKeys(strTestdata);
			blnFlag = true;
			Thread.sleep(lSleep_Low);
			if(!strTestdata.trim().equalsIgnoreCase(driver.findElement(objLocator).getAttribute("value").trim()))
				blnFlag = false;
			return blnFlag;
		}
		catch (Exception e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!(strTestdata.equalsIgnoreCase("")||strTestdata==null))
			{
				if(!blnFlag)
					Reporters.failureReport("Enter data in "+strLocatorName,"Failed to enter data. ", false );
				else if(blnEventReport && blnFlag)
					Reporters.successReport("Enter data in "+strLocatorName,"Data '"+strTestdata+"' entered successfully.");
				Reporters.successReport("Verify data in "+strLocatorName,"Data '"+strTestdata+"' remains in the textbox.");	

			}
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:	gf_MouseOver()
	 * Description				:	Moves the mouse to the middle of the element. The element is scrolled into view and its location is calculated using getBoundingClientRect.
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:	Meaningful name to the element (Ex:link,menus  etc..)
	 * @throws Throwable
	 *****************************************************************************************************************************/
	public static boolean gf_MouseOver(By objLocator, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			WebElement mo = driver.findElement(objLocator);
			new Actions(driver).moveToElement(mo).build().perform();
			blnFlag = true;
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Mouse hover on "+strLocatorName,"Failed to mouse hover.", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Mouse hover on "+strLocatorName,"Successfully mouse hovered on "+strLocatorName+".");
		}
	}

	/******************************************************************************************************************************
	 * Function Name			:	gf_Draggable()
	 * Description				:	A convenience method that performs click-and-hold at the location of the source element, moves by a given offset, then releases the mouse.
	 * @param objSource			: 	Element to emulate button down at.
	 * @param intXCoordinate	: 	Horizontal move offset.
	 * @param intYCoordinate	: 	Vertical move offset.
	 *****************************************************************************************************************************/
	public static boolean gf_Draggable(By objSource, int intXCoordinate, int intYCoordinate, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {

			WebElement dragitem = driver.findElement(objSource);
			new Actions(driver).dragAndDropBy(dragitem, intXCoordinate, intYCoordinate).build().perform();
			Thread.sleep(lSleep_Medium);
			blnFlag = true;
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Element drag and drop "+strLocatorName,"Failed to move element. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Element drag and drop "+strLocatorName,"Successfully element moved");
		}
	}

	/***************************************************************************************************************************
	 * Function Name			:   gf_DragAndDrop()
	 * Description				:	A convenience method that performs click-and-hold at the location of the
	 * 								source element, moves to the location of the target element, then
	 * 								releases the mouse.
	 * @param objSource			: Element to emulate button down at.
	 * @param objTarget			: Element to move to and release the mouse at.
	 * @param strLocatorName		: Meaningful name to the element (Ex:Button,image etc..)
	 *****************************************************************************************************************************/
	public static boolean gf_DragAndDrop(By objSource, By objTarget, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			WebElement from = driver.findElement(objSource);
			WebElement to = driver.findElement(objTarget);
			new Actions(driver).dragAndDrop(from, to).perform();
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} 
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Element drag and drop "+strLocatorName,"Failed to move element. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Element drag and drop "+strLocatorName,"Successfully element moved" );
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_Slider()
	 * Description				:	To slide an object to some distance
	 * @param objSlider			: 	Action to be performed on element
	 * @param locatorName		: 	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @param iXCoordinate		:	X-Coordinate Value To slide
	 * @param iYCoordinate		:	Y-Coordinate Value To slide
	 ******************************************************************************************************************************/
	public static boolean gf_Slider(By objSlider, int iXCoordinate,int iYCoordinate,String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			WebElement dragitem = driver.findElement(objSlider);
			new Actions(driver).dragAndDropBy(dragitem, iXCoordinate, iYCoordinate).build().perform();
			Thread.sleep(lSleep_Medium);
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Element sliding "+strLocatorName,"Failed to slide element. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Element sliding "+strLocatorName,"Successfully element slided");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   bf_RightClick()
	 * Description				:	To right click on an element
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @throws Throwable
	 ****************************************************************************************************************************/
	public static boolean bf_RightClick(By objLocator, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			WebElement elementToRightClick = driver.findElement(objLocator);
			Actions clicker = new Actions(driver);
			clicker.contextClick(elementToRightClick).perform();
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Open Context menu"+strLocatorName,"Failed to open menu. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Open Context menu"+strLocatorName,"Successfully opened menu");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_ContainsString()
	 * Description				:	This Method is used to compare two strings.
	 * @param strActualText		: 	Actual text to compare.
	 * @param strExpectedText	:   Expected result.
	 * @param strLocatorName	:   Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return
	 * @throws Throwable
	 ***************************************************************************************************************************/
	public static boolean gf_ContainsString(String strActualText,String strExpectedText,String strLocatorName) throws Throwable{
		boolean flag=false;
		try
		{
			Thread.sleep(2000);
			if(strActualText.trim().contains(strExpectedText.trim()))
				flag=true;
			return flag;

		} catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return false;
		} finally
		{
			if(!flag)
			{
				Reporters.failureReport(ReadResourceData.getResourceData("CS_005", "Desc", ""), ReadResourceData.getResourceData("CS_005", "Fail", strExpectedText), false);
				//Reporters.failureReport("Verify "+strLocatorName,"Expected Text: "+strExpectedText+" Actual Text:"+strActualText+"</br>  is Not Matching. " );
			}
			else if(blnEventReport && flag){
				Reporters.successReport(ReadResourceData.getResourceData("CS_005", "Desc", ""), ReadResourceData.getResourceData("CS_005", "Pass", strExpectedText));
				//Reporters.successReport("Verify "+strLocatorName,"Expected Text: "+strExpectedText+" Actual Text:"+strActualText+" is  Matching" );
			}
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_WaitForTitlePresent()
	 * Description				:
	 * Wait for an element
	 * @param objLocator			: Action to be performed on element (Get it from Object repository)
	 ***************************************************************************************************************************/
	public static boolean gf_WaitForTitlePresent(By objLocator) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			for (int i = 0; i < 200; i++) 
			{
				if (driver.findElements(objLocator).size()>0) 
					break;
				else 
					Thread.sleep(lWait_Medium);
			}
			//driver.wait(lSleep_Medium);
			blnFlag = true;
			return blnFlag;
		}catch (InterruptedException e) {
			gStrErrMsg = e.getMessage();
			return false;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Wait for Title Present","Title not present. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Wait for Title Present","Title present");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_WaitForElementPresent()
	 * Description				:	Wait for an ElementPresent
	 * @param objLocator			: Action to be performed on element (Get it from Object repository)
	 * @param lTimeSpan
	 * @return Whether or not the element is displayed
	 ***************************************************************************************************************************/
	public static boolean gf_WaitForElementPresent(By objLocator, long lTimeSpan) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			for (int i = 0; i < 200; i++) 
			{
				if (driver.findElement(objLocator).isDisplayed()) 
					break;
				else
					Thread.sleep(lSleep_Low);
			}
			driver.wait(lTimeSpan);
			blnFlag = true;
			return blnFlag;
		} 
		catch (InterruptedException e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Wait for element Present","Element not present. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Wait for element Present","Element present" );
		}
	}
	
	/****************************************************************************************************************************
	 * Function Name			:   gf_WaitForElementPresent()
	 * Description				:	Wait for an ElementPresent
	 * @param objLocator			: Action to be performed on element (Get it from Object repository)
	 * @param lTimeSpan
	 * @return Whether or not the element is displayed
	 ***************************************************************************************************************************/
	public static boolean gf_WaitForElementPresent(By objLocator, String objectString) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			for (int i = 0; i < 200; i++) 
			{
				if (driver.findElement(objLocator).isDisplayed()) 
					break;
				else
					Thread.sleep(lSleep_Low);
			}
			//driver.wait(2000);
			blnFlag = true;
			return blnFlag;
		} 
		catch (InterruptedException e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		/*finally
		{
			if(!blnFlag)
				Reporters.failureReport("Wait for element Present","Element not present. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Wait for element Present","Element present" );
		}*/
	}
	
	
	public static void waitForPageLoaded(WebDriver driver)
	{
	    ExpectedCondition<Boolean> expectation = new
	ExpectedCondition<Boolean>() 
	    {
	        public Boolean apply(WebDriver driver)
	        {
	            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	    };
	    Wait<WebDriver> wait = new WebDriverWait(driver,30);
	    try
	    {
	        wait.until(expectation);
	    }
	    catch(Throwable error)
	    {
	    	try {
				Reporters.failureReport("Wait for Page to Load","Page could not be loaded in 30 sec", false);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	
	

	/****************************************************************************************************************************
	 * Function Name			:   gf_ClickAndWaitForElementPresent()
	 * Description				:	This method Click on element and wait for an element
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param objWaitElement	:	Element name wish to wait for that (Get it from Object repository)
	 * @param strLocatorName	:	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_ClickAndWaitForElementPresent(By objLocator, By objWaitElement, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			gf_Click(objLocator, strLocatorName);
			gf_WaitForElementPresent(objWaitElement,lWait_Slow );
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click and Wait for element Present","Element not present. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click and Wait for element Present","Element present");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SelectByIndex()
	 * Description				:	select value from DropDown by using selectByIndex
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param intIndex			: 	Index of value wish to select from dropdown list.
	 * @param strLocatorName	:	Meaningful name to the element (Ex:Year Dropdown, items Listbox etc..)
	 ****************************************************************************************************************************/
	public static boolean gf_SelectByIndex(By objLocator, int intIndex, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			Select s = new Select(driver.findElement(objLocator));
			s.selectByIndex(intIndex);
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Select value from drop down on "+strLocatorName,"Failed to select. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Select value from drop down on "+strLocatorName,"Successfully selected by index is "+intIndex);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SelectByValue()
	 * Description				:	select value from DD by using value
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param strValue			:	Value wish to select from dropdown list.
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Year Dropdown, items Listbox etc..)
	 ****************************************************************************************************************************/
	public static boolean gf_SelectByValue(By objLocator, String strValue, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			Select s = new Select(driver.findElement(objLocator));
			s.selectByValue(strValue);
			blnFlag = true;
			return blnFlag;
		}
	/*	catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}*/
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Select value from drop down on "+strLocatorName,"Failed to select. ", true);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Select value from drop down on "+strLocatorName,"Successfully selected value : "+strValue);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SelectByVisibleText()
	 * Description				:	select value from DropDown by using selectByVisibleText
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strVisibletext	: 	VisibleText wish to select from dropdown list.
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Year Dropdown, items Listbox etc..)
	 **************************************************************************************************************************/
	public static boolean gf_SelectByVisibleText(By objLocator, String strVisibletext,String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			
			Select dropDown = new Select(driver.findElement(objLocator));           

			List<WebElement> Options = dropDown.getOptions();
			for(WebElement option:Options)
			{

				if(option.getText().trim().replace("\n", "").replaceAll("[^a-zA-Z0-9_-]", "").equals(strVisibletext.trim().replace("\n", "").replaceAll("[^a-zA-Z0-9_-]", "")))
				{
					Thread.sleep(lSleep_Low);
					option.click();
					blnFlag = true;
					return blnFlag;
				}
			}
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Select value from drop down on "+strLocatorName,"Failed to select from drop down.", true);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Select value from drop down on "+strLocatorName,"Successfully selected value : "+strVisibletext);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SelectByVisibleTextAndVerify()
	 * Description				:	select value from DropDown by using selectByVisibleText and verify the value is selected or not
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strVisibletext	: 	VisibleText wish to select from dropdown list.
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Year Dropdown, items Listbox etc..)
	 **************************************************************************************************************************/
	public static boolean gf_SelectByVisibleTextAndVerify(By objLocator, String strVisibletext,String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			Select dropDown = new Select(driver.findElement(objLocator));           

			List<WebElement> Options = dropDown.getOptions();
			for(WebElement option:Options)
			{			
				if(option.getText().trim().replace("\n", "").replaceAll("[^a-zA-Z0-9_-]", "").equals(strVisibletext.trim().replace("\n", "").replaceAll("[^a-zA-Z0-9_-]", "")))
				{
					option.click();
					blnFlag = true;
					return blnFlag;
				}
			}
			Thread.sleep(lSleep_Low);
			if(!strVisibletext.equalsIgnoreCase(dropDown.getFirstSelectedOption().getText().toString()))
				blnFlag = false;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Select value from drop down on "+strLocatorName,"Failed to select from drop down.", true);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Select value from drop down on "+strLocatorName,"Successfully selected value : "+strVisibletext);
			Reporters.successReport("Verify value in drop down "+strLocatorName,"Selected value "+strVisibletext+" remains in the dropdown box.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchWindowByTitle()
	 * Description				:	SWITCH TO WINDOW BY USING TITLE
	 * @param strWindowTitle	: 	Title of window wish to switch
	 * @param intCount			: 	Selenium launched Window id (integer no)
	 * @return: Boolean value(true or false)
	 ****************************************************************************************************************************/
	public static Boolean gf_SwitchWindowByTitle(String strWindowTitle, int intCount) throws Throwable 
	{
		boolean blnFlag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();
			int windowCount = windowList.size();

			String[] array = windowList.toArray(new String[0]);

			for (int i = 0; i <=windowCount; i++) {

				driver.switchTo().window(array[intCount-1]);
				if (driver.getTitle().contains(strWindowTitle))
				{
					blnFlag = true;
					return blnFlag;
				}
			}
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch between windows by title","Failed to switch between windows by title", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch between windows by title","Succeessfully switched to window title is "+strWindowTitle);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_GetColumncount()
	 * Description				:	Function To get column count and print data in Columns
	 * @param objLocator			: 	Action to be performed on element (Get it from Object repository)
	 * @return					: 	Returns no of columns.
	 * @throws Throwable 
	 ******************************************************************************************************************************/
	public int gf_GetColumncount(By objLocator) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			WebElement tr = driver.findElement(objLocator);
			List<WebElement> columns = tr.findElements(By.tagName("td"));
			int a = columns.size();
			blnFlag = true;
			return a;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return 0;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("GetColumn count from webtable","Failed to get column count. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("GetColumn count from webtable","Succeessfully get the column count");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_VerifyHeadersofTable()
	 * Description				:  Function To get column count and print data in Columns
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * @return					: Returns no of columns.
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_VerifyHeadersofTable(By objLocator,String strExpectedHeaderText) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			WebElement tr = driver.findElement(objLocator);
			List<WebElement> columns = tr.findElements(By.tagName("th"));
			int a = columns.size();
			for (int i = 0; i < a; i++) 
			{
				WebElement column=columns.get(i);
				String ActualHeaderText=column.getText();
				if(ActualHeaderText.equals(strExpectedHeaderText))
				{  
					blnFlag = true;
					return blnFlag;
				}
			}
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify table headers","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify table headers","Succeessfully header text is "+strExpectedHeaderText);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsTextNotPresent()
	 * Description				:  Text present or not
	 * @param strText			: Text wish to verify on current page 
	 * @return					: boolean value(true: if Text present, false: if text not present)
	 ****************************************************************************************************************************/
	public static boolean  gf_IsTextNotPresent(String strText) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			boolean bValue = driver.getPageSource().contains(strText);
			if (!bValue) 
			{
				blnFlag = true;
				return blnFlag;
			}
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify text present on page","Text was not present page", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify text present on page","Succeessfully verified text.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_GetRowCount()
	 * Description				:  Function To get row count and print data in rows
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @return					:  returns no of rows. 
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public static int gf_GetRowCount(By objLocator) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			WebElement table = driver.findElement(objLocator);
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			int a = rows.size() - 1;
			blnFlag = true;
			return a;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return 0;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Get table row count","Failed to return row count. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Get table row count","returned row count.");
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:  gf_Alert()
	 * Description				: Accept alert
	 * @return:  Boolean (True: If alert preset, False: If no alert)
	 **************************************************************************************************************************/
	public static boolean gf_Alert() throws Throwable {

		boolean blnFlag = false;
		Alert alert = null;
		String StrTextOnAlert="";
		try {
			if(gf_IsAlertPresent())
			{
				alert = driver.switchTo().alert(); 
				// Alert present; set the flag
				blnFlag = true;
				// if present consume the alert
				StrTextOnAlert=alert.getText();
				alert.accept();
			}
			else
				blnFlag = true;
			return blnFlag;

		} catch (NoAlertPresentException ex) {
			gStrErrMsg = ex.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch to alert and Accept alert",StrTextOnAlert+"Failed to accept alert. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch to alert and Accept alert",StrTextOnAlert+" Alert accepted successfully.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsAlertPresent()
	 * Description				: Verify alert present or not
	 * @return:  Boolean (True: If alert preset, False: If no alert)
	 **************************************************************************************************************************/

	public static boolean gf_IsAlertPresent() 
	{ 
		try 
		{ 
			driver.switchTo().alert(); 
			return true; 
		}   // try 
		catch (NoAlertPresentException Ex) 
		{ 
			return false; 
		}   // catch 
	}   // isAlertPresent()

	

	/****************************************************************************************************************************
	 * Function Name			:  gf_LaunchUrl()
	 * Description				:  To launch URL
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public static void gf_LaunchUrl(String strURL) throws Throwable {
		if(Base.aiException==null){	
			try{
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				driver.get(strURL);
				
				Reporters.successReport("Launch Application URL","Successfully launched URL : "+strURL);
			}
			catch(Exception e)
			{
				gStrErrMsg = e.getMessage();
				Reporters.failureReport("Launch Application URL","Failed to launch URL. ", false);
			}
		}
		else{
			com.qentelli.automation.utilities.RepairTests.fixTheIssue(aiException);
			throw new Exception();
		}
		
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_LaunchUrl()
	 * Description				:  To launch URL
	 * @param strURL 
	 * @param strURL 
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public static void gf_LaunchUrl(String strUserName, String strPassword, String strURL) throws Throwable {
		try{
			String sChrome_Url = strURL.replace("http://", "@");
			String sIe_Url = strURL;

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			Thread.sleep(lSleep_Low);
			if(sBrowser.equalsIgnoreCase("chrome"))
			{
				String  ChromeUserName=URLEncoder.encode(strUserName,"UTF-8");
				String ChromePassword=URLEncoder.encode(strPassword,"UTF-8");  
				String canonical = "http://"+ChromeUserName+":"+ChromePassword+""+sChrome_Url+"/";
				getDriver().get(canonical);
			}
			else if(sBrowser.equalsIgnoreCase("firefox"))
			{
				Robot robot = new Robot();
				StringSelection stringSelection = new StringSelection(sIe_Url);
				Thread.sleep(lSleep_Medium);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null); robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(lSleep_Medium);
				stringSelection = new StringSelection(strUserName);
				Thread.sleep(lSleep_Medium);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null); robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(lSleep_Medium);
				robot.keyPress(KeyEvent.VK_TAB);
				stringSelection = new StringSelection(strPassword);
				Thread.sleep(lSleep_Medium);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null); robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);

				// Accept the authentication window.
				robot.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(lSleep_Medium);
				robot.keyPress(KeyEvent.VK_ENTER);
			}
			else if(sBrowser.equalsIgnoreCase("ie"))
			{
				getDriver().get(sIe_Url);
				Thread.sleep(lSleep_Medium);
				if(gf_IsAlertPresent())
				{
					driver.switchTo().alert().accept();
					Alert authenticationWindow = driver.switchTo().alert();
					authenticationWindow.sendKeys(strUserName);
					Thread.sleep(lSleep_Medium);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					StringSelection stringSelection = new StringSelection(strPassword);
					Thread.sleep(lSleep_Medium);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null); robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);

					// Accept the authentication window.
					robot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(lSleep_Medium);
					robot.keyPress(KeyEvent.VK_ENTER);
				}
			}
			Reporters.successReport("Launch Application URL","Successfully launched URL : "+strURL);

		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			Reporters.failureReport("Launch Application URL","Failed to launch URL. ", false);
			throw new UserDefinedException(e);
		}
		
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsChecked()
	 * Description				:  This method verify check box is checked or not
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * 
	 * @param strLocatorName	: Meaningful name to the element (Ex:sign in Checkbox etc..)
	 * 
	 * @return					: boolean value(True: if it is checked, False: if not checked)
	 *  
	 ***************************************************************************************************************************/
	public static boolean gf_IsChecked(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			if (driver.findElement(objLocator).isSelected()) {
				blnFlag = true;
			}

			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify element is checked/selected for "+strLocatorName,"Failed to verify. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify element is checked/selected for "+strLocatorName,"Successfully verified element selection");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsUnChecked()
	 * Description				:  This method verify check box is unchecked or not
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: Meaningful name to the element (Ex:sign in Checkbox etc..)
	 * @return					: boolean value(True: if it is checked, False: if not checked)
	 ***************************************************************************************************************************/
	public static boolean gf_IsUnChecked(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = true;
		try {
			if (driver.findElement(objLocator).isSelected()) {
				blnFlag = false;
			}

			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify element is Unchecked/selected for "+strLocatorName,"Failed to verify element is checked", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify element is Unchecked/selected for "+strLocatorName,"Successfully verified element is unchecked");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsEnabled()
	 * Description				:  Element is enable or not
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:  Meaningful name to the element (Ex:Login Button, UserName Textbox etc..)
	 * @return: boolean value (True: if the element is enabled, false: if it not enabled).
	 ****************************************************************************************************************************/
	public static boolean gf_IsEnabled(By objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			if (driver.findElement(objLocator).isEnabled()) {
				blnFlag = true;
			}
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify element is enabled or not for "+strLocatorName,"Failed to verify  "+strLocatorName+" is enabled", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify element is enabled or not for "+strLocatorName,"Successfully verified -"+strLocatorName+" is enabled");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IsVisible()
	 * Description				:  Element visible or not
	 * @param objLocator			: Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName		: Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return					: boolean value(True: if the element is visible, false: If element not visible)
	 ****************************************************************************************************************************/
	public static Boolean gf_IsVisible(By objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			blnFlag = driver.findElement(objLocator).isDisplayed();
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify element is visible or not for "+strLocatorName,"Failed to verify. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify element is visible or not for "+strLocatorName,"Successfully verified");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			: gf_GetCssValue()
	 * Description				: Get the CSS value of an element
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: Meaningful name to the element (Ex:Login Button, label color etc..)
	 * @param strCssAttribute	: CSS attribute name wish to verify the value (id, name, etc..)
	 * @return					: String CSS value of the element
	 ************************************************************************************************************************/
	public String gf_GetCssValue(By objLocator, String strCssAttribute,String strLocatorName) throws Throwable {
		String value = "";
		if (gf_IsElementPresent(objLocator,strLocatorName)) {
			value = driver.findElement(objLocator).getCssValue(strCssAttribute);
		}
		return value;
	}
	/****************************************************************************************************************************
	 * Function Name			:  gf_AssertValue()
	 * Description				:  Check the expected value is available or not
	 * @param strExpValue		: Expected value of attribute
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * @param strAttribute		: Attribute name of element wish to assert 
	 * @param strLocatorName	: Meaningful name to the element (Ex:link text, label text etc..)
	 ************************************************************************************************************************/
	public static boolean gf_AssertValue(String strExpValue, By objLocator, String strAttribute, String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try{
			AssertJUnit.assertEquals(strExpValue, gf_GetAttribute(objLocator, strAttribute, strLocatorName));
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify attribute value for "+strLocatorName,"Failed to verify. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify attribute value for "+strLocatorName ,"Successfully verified "+strExpValue+" attribute");
		}

	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_AssertTextPresent()
	 * Description				:  Check the text is presnt or not
	 * @param strText    		: Text wish to assert on the page.
	 ***************************************************************************************************************************/
	public static boolean gf_AssertTextPresent(String strText) throws Throwable {
		boolean blnFlag = false;
		try {
			AssertJUnit.assertTrue(gf_IsTextPresent(strText));
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Assert text present or not","Failed to assert. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Assert text present or not","Successfully asserted text is "+strText);
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:  gf_AssertElementPresent()
	 * Description				:  Assert element present or not
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:  Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_AssertElementPresent(By objLocator,String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			AssertJUnit.assertTrue(gf_IsElementPresent(objLocator,strLocatorName));
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Assert "+strLocatorName+" element present or not","Failed to assert. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Assert "+strLocatorName+" element present or not","Successfully asserted.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_AssertText()
	 * Description				:  Assert text on element
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @param strText			:  expected text to assert on the element
	 ***************************************************************************************************************************/
	public static boolean gf_AssertText(By objLocator, String strText) throws Throwable {
		boolean blnFlag = false;
		try {
			AssertJUnit.assertEquals(gf_GetText(objLocator, strText).trim(), strText.trim());
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Assert "+strText+" text present or not","Failed to assert. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Assert "+strText+" text present or not","Successfully asserted.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_VerifyText()
	 * Description				:  Assert text on element
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @param strText			:  expected text to assert on the element
	 * @param strLocatorName	:  Meaningful name to the element (Ex:link text, label text etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_VerifyText(By objLocator, String strText,String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		String vtxt=null;
		try {
			vtxt=gf_GetText(objLocator, strLocatorName).trim();		
			if(vtxt.equals(strText.trim()))
				blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify "+strText+" text present or not","Failed to verify "+strText+" Displayed Text is "+vtxt, false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify "+strText+" text present or not","Successfully verified -"+strText);
		}
	}

	/****************************************************************************************************************************
	 * Function Name : gf_VerifyReadOnlyText() Description : Gets Text from Read only 
	 * 
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strText
	 *            : expected text to assert on the element
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:link text, label text
	 *            etc..)
	 ***************************************************************************************************************************/

	public static boolean gf_VerifyReadOnlyText(By objLocator, String strText, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			String vtxt = gf_GetAttribute(objLocator, "value" ,strLocatorName).trim();
			if (vtxt.equals(strText.trim()))
				blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} finally {
			if (!blnFlag){
				Reporters.failureReport("Verify " + strText + " text present or not", "Failed to verify. ", false);
			}
			else {
				Reporters.successReport("Verify " + strText + " text present or not", "Successfully verified.");
			}
		}
	}
	
	
	/****************************************************************************************************************************
	 * Function Name			:  gf_VerifypartialText()
	 * Description				:  Assert text on element
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @param strText			:  expected text to assert on the element
	 * @param strLocatorName	:  Meaningful name to the element (Ex:link text, label text etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_VerifypartialText(By objLocator, String strText,String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			String vtxt=gf_GetText(objLocator, strLocatorName).trim();		
			if(vtxt.contains(strText.trim()))
				blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify "+strText+" text present or not","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify "+strText+" text present or not","Successfully verified.");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_VerifyImageSource()
	 * Description				:  Assert Image Source
	 * @param objLocator		:  Action to be performed on element (Get it from Object repository)
	 * @param strText			:  expected image source
	 * @param strLocatorName	:  Meaningful name to the element (Ex:link text, label text etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_VerifyImageSource(By objLocator, String strSRC,String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		String imgSource=null;
		try {
			imgSource=gf_GetAttribute(objLocator,"src", strLocatorName);		
			if(imgSource.toLowerCase().contains(strSRC.trim().toLowerCase()))
				blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify the image source contains "+strSRC+" or not","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify the image source contains "+strSRC+" or not","Successfully verified."+imgSource);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   getTitle()
	 * Description				:	Get current title of the page
	 * @return					: return title of current page.
	 * @throws Throwable
	 ****************************************************************************************************************************/
	public static String getTitle() throws Throwable {

		String text = driver.getTitle();
		//	Reporters1.SuccessReport("Title", "Title of the page is:" + text);

		return text;
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_AsserTitle()
	 * Description				:	Assert Title of the page.
	 * @param title				: 	Expected title of the page.
	 *****************************************************************************************************************************/
	
	/****************************************************************************************************************************
	 * Function Name			:   gf_ClickonTitle()
	 * Description				:
	 * @param strTitle
	 * @return
	 * @throws Throwable
	 ***************************************************************************************************************************/
	public static boolean gf_ClickonTitle(String strTitle) throws Throwable {

		boolean blnFlag = false;
		try{
			By windowTitle = By.xpath("//title[contains(text(),'"+strTitle+"')]");
			driver.findElement(windowTitle).click();
			//Assert.assertEquals(getTitle(), title);
			blnFlag = true;
			return blnFlag;
		}catch(Exception ex){
			gStrErrMsg = ex.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click on window title","Failed to click. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click on window title","Successfully clicked on title.");
		}
	}
	/****************************************************************************************************************************
	 * Function Name			:   gf_VerifyTitle()
	 * Description				:	Verify Title of the page
	 * @param strTitle			: Expected title of the page.
	 ***************************************************************************************************************************/
	public static boolean gf_VerifyTitle(String strTitle) throws Throwable {

		if(getTitle().equals(strTitle))
		{
			Reporters.successReport("VerifyTitle"," Page title is verified as "+strTitle);
			return true;
		}
		else
		{
			Reporters.failureReport("VerifyTitle",getTitle()+"Page title is not matched with "+strTitle, false);
			return false;
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_VerifyTextPresent()
	 * Description				:	Verify text present or not
	 * @param strText           :	Text wish to verify on the current page.
	 **************************************************************************************************************************/
	public static boolean  gf_VerifyTextPresent(String strText) throws Throwable
	{  

		if((driver.getPageSource()).contains(strText))
		{
			Reporters.successReport("Verify Text Present on Page"," Page is verified with "+strText);
			return true;
		}
		else
		{
			Reporters.failureReport("Verify Text Present on Page","Page does not contains "+strText, false);
			return false;
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_Verify404()
	 * Description				:	Verify the 404 error or broken links
	 * @param strText			: Text expect to display when 404 error / broken link. 
	 ****************************************************************************************************************************/
	public static boolean gf_Verify404(String strText) throws Throwable
	{
		if (driver.getPageSource().contains("404")) {
			Reporters.failureReport("Verify404",strText+" is present in the page", false);
			return true;
		}
		else
			return false;
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_GetAttribute()
	 * Description				:	Get the value of a the given attribute of the element.
	 * @param by				: 	Action to be performed on element (Get it from Object repository)
	 * @param attribute			: 	Attribute name wish to assert the value.
	 * @param locatorName		: 	Meaningful name to the element (Ex:label, SignIn Link etc..)
	 * @return: String attribute value
	 ***************************************************************************************************************************/
	public static String gf_GetAttribute(By by, String attribute,String locatorName) throws Throwable {
		String value = "";
		if (gf_IsElementPresent(by,locatorName)) {
			value = driver.findElement(by).getAttribute(attribute);
		}
		return value;
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsTextPresent()
	 * Description				:	Text present or not
	 * @param text				: 	Text wish to verify on current page 
	 * @return					:	boolean value(true: if Text present, false: if text not present)
	 *********************************************************************************************************************/

	public static boolean  gf_IsTextPresent(String strText) throws Throwable {
		boolean blnFlag = false;
		try {
			blnFlag = driver.getPageSource().contains(strText);
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify Text contains on page or not","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify Text contains on page or not","Successfully verified text is "+strText);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsTextEquals()
	 * Description				:	Compares text in page source.
	 * @param strText			:   Text To verify
	 * @return
	 * @throws Throwable
	 ***************************************************************************************************************************/
	public static boolean  gf_IsTextEquals(String strText) throws Throwable {

		boolean blnFlag = false;
		try {
			blnFlag = driver.getPageSource().equals(strText);
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify Text equals on page or not","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify Text equals on page or not","Successfully verified text is "+strText );
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_GetText()
	 * Description				:	The innerText of this element.
	 * @param locator			: 	Action to be performed on element (Get it from Object repository)
	 * @param locatorName		: 	Meaningful name to the element (Ex:label text, SignIn Link etc..)
	 * @return					: 	String return text on element
	 **************************************************************************************************************************/

	public static String gf_GetText(By locator,String locatorName)
			throws Throwable {
		String text = "";
		try{
		if (gf_IsElementPresent(locator,locatorName)) {
			text = driver.findElement(locator).getText();
		}
		return text;
		}
		catch(Exception e)
		{
			return text;
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_ScreenShot()
	 * Description				:	Capture Screenshot
	 * @param fileName: FileName screenshot save in local directory 
	 ****************************************************************************************************************************/
	public static void gf_ScreenShot(String fileName) {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			// Now you can do whatever you need to do with it, for example copy
			// somewhere
			FileUtils.copyFile(scrFile, new File(fileName));
			File sourceFilePathObject=new File(fileName);
			File destDir=new File(configProps.getProperty("testRailImagesLoc"));
			FileUtils.copyFileToDirectory(sourceFilePathObject, destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_MouseHoverByJavaScript()
	 * Description				:	MouseHover using java script executor
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:SignIn Link, menu's etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_MouseHoverByJavaScript(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			WebElement mo = driver.findElement(objLocator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			blnFlag = true;
			return blnFlag;
		}
		catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Mouse over on element","Mouse move failed. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Mouse over on element","Successfully mouse moved to element "+strLocatorName);
		}
	}
	
	/****************************************************************************************************************************
	 * Function Name			:   gf_MouseHoverByJavaScript()
	 * Description				:	MouseHover using java script executor
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:SignIn Link, menu's etc..)
	 ***************************************************************************************************************************/
	public static boolean gf_MouseHoverByJavaScriptWithoutReport(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			WebElement mo = driver.findElement(objLocator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			blnFlag = true;
			return blnFlag;
		}
		catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchToFrameByIndex()
	 * Description				:	This method switch the focus to selected frame using frame index
	 * @param intIndex			: 	Index of frame wish to switch
	 ****************************************************************************************************************************/
	public static boolean gf_SwitchToFrameByIndex(int intIndex) throws Throwable
	{   
		boolean blnFlag=false;
		try
		{
			driver.switchTo().frame(intIndex);
			blnFlag=true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch to frame","fail to switch. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch to frame","Successfully switch to frame by index "+intIndex);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchToFrameById()
	 * Description				:	This method switch the to frame using frame ID.
	 * @param strValue			: Frame ID wish to switch
	 ***************************************************************************************************************************/
	public static boolean gf_SwitchToFrameById(String strValue)throws Throwable
	{   
		boolean blnFlag=false;
		try
		{
			driver.switchTo().frame(strValue);
			blnFlag=true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch to frame","fail to switch. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch to frame","Successfully switch to frame by id "+strValue );
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchToFrameByName()
	 * Description				:	This method switch the to frame using frame Name.
	 * @param strName			:	Frame Name wish to switch
	 ***************************************************************************************************************************/
	public static boolean gf_SwitchToFrameByName(String strName)throws Throwable
	{   
		boolean blnFlag=false;
		try
		{
			driver.switchTo().frame(strName);
			blnFlag=true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch to frame","fail to switch. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch to frame","Successfully switch to frame by name "+strName );
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchToFrameByLocator()
	 * Description				:	This method switch the to frame using frame Name.
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName 	:   Meaningful name to the element (Ex:SignIn Link, login button etc..)
	 **************************************************************************************************************************/
	public static boolean gf_SwitchToFrameByLocator(By objLocator,String strLocatorName)throws Throwable
	{   
		boolean blnFlag=false;
		try
		{
			driver.switchTo().frame(driver.findElement(objLocator));
			blnFlag=true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Switch to frame","fail to switch. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Switch to frame","Successfully switch to frame by webelement "+strLocatorName);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_ImplicitWait()
	 * Description				:	This method wait selenium until element present on web page.
	 ***************************************************************************************************************************/
	public static void gf_ImplicitWait(){

		driver.manage().timeouts().implicitlyWait(lWait_Medium, TimeUnit.SECONDS);
	}

	/****************************************************************************************************************************
	 * Function Name : gf_SwitchToDefault() Description : This method switch to
	 * default content on web page.
	 ***************************************************************************************************************************/
	public static void gf_SwitchToDefault() {

		driver.switchTo().defaultContent();
	}
	

	/****************************************************************************************************************************
	 * Function Name : gf_GetCurrentWindow() Description : This method switches
	 * to a window.
	 ***************************************************************************************************************************/
	public static void gf_SwitchToWindow(String window) {

		driver.switchTo().window(window);
	}

	/****************************************************************************************************************************
	 * Function Name : gf_GetCurrentWindow() Description : This method get the
	 * current window.
	 ***************************************************************************************************************************/
	public static String gf_GetCurrentWindow() {

		return driver.getWindowHandle();
	}
	
	/****************************************************************************************************************************
	 * Function Name : gf_GetCurrentWindow() Description : This method get all
	 * windows.
	 ***************************************************************************************************************************/
	public static Set<String> gf_GetAllWindows() {

		return driver.getWindowHandles();
	}
	
	/****************************************************************************************************************************
	 * Function Name			:   driverwait()
	 * Description				:	This method wait driver until given driver time.
	 ***************************************************************************************************************************/
	public static WebDriverWait driverwait(){

		WebDriverWait wait = new WebDriverWait(driver, 30);
		return wait;
	}
	/****************************************************************************************************************************
	 * Function Name			:   gf_WaitForVisibilityOfElement()
	 * Description				:	This method wait selenium until visibility of Elements on WebPage.
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param lTime				: 	Wait time
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_WaitForVisibilityOfElement(By objLocator,long lTime) throws Throwable{
		boolean blnFlag = false;
		try{
			WebDriverWait objWait = new WebDriverWait(driver, lTime);
			objWait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		/*finally
		{
			if(!blnFlag)
				Reporters.failureReport("Wait for element visibility","Element was not visible. " );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Wait for element visibility",strLocatorName+" element visible");
		}*/
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_WaitForInVisibilityOfElement()
	 * Description				:	This method wait driver until Invisibility of Elements on WebPage.
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:	Meaningful name to the element (Ex:Textbox, checkbox etc)
	 * @param					:   Wait time value
	 * @throws Throwable  
	 ****************************************************************************************************************************/
	public static boolean gf_WaitForInVisibilityOfElement(By objLocator, long lWait, String strLocatorName) throws Throwable{
		boolean blnFlag = false;
		try{
			WebDriverWait wait = new WebDriverWait(driver, lWait);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Wait for element invisibility","Failed to check invisible. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Wait for element invisibility",strLocatorName+" element not visible");
		}
	}

	/**
	 * 
	 * @return
	 */
	public static WebDriver getDriver(){
		return driver;
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_ClickOnValueBasedOnTagname()
	 * Description				:	Click on element Using tag name
	 * @param strTagName
	 * @param strValue 			: 	Link Name to which to be clicked
	 * @return
	 * @throws Throwable
	 **************************************************************************************************************************/
	public static boolean gf_ClickOnValueBasedOnTagname(String strTagName,String strValue) throws Throwable{
		boolean blnFlag = false;
		try
		{
			gf_WaitForVisibilityOfElement(By.xpath(".//"+strTagName+"[contains(text(),'"+strValue+"')]"), lWait_Medium);
			WebElement E=driver.findElement(By.xpath(".//"+strTagName+"[contains(text(),'"+strValue+"')]"));
			E.click();
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{			
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click on element by using "+strTagName+" tagname","Failed to click. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click on element by using "+strTagName+" tagname","Successfully clicked");
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_ClickonElementBasedonObjectprop()
	 * Description				:
	 * @param objLocator 		: The Object Name should be in Object Repository
	 * @param strTagName		:The Tagname can be a,span etc..
	 * @param strElementText	: The Text of the element
	 * @return
	 * @throws Throwable
	 **************************************************************************************************************************/
	public static boolean gf_ClickonElementBasedonObjectprop(By objLocator,String strTagName,String strElementText) throws Throwable 
	{
		boolean blnFlag = false;
		try 
		{   
			WebElement e=driver.findElement(objLocator);
			e.findElement(By.xpath(".//"+strTagName+"[contains(text(),'"+strElementText+"')]")).click();
			blnFlag = true;
			return blnFlag;
		}
		catch (Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click on element by element property "+strElementText,"Failed to click. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click on element by element property "+strElementText,"Successfully clicked on :"+strElementText);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsElementPresent()
	 * Description				:	This method returns check existence of element
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strDesc 			:	Meaningful name to the element (Ex:Textbox, checkbox etc)
	 * @return					: 	Boolean value(True or False)
	 * @throws Throwable 
	 * @throws NoSuchElementException
	 ***************************************************************************************************************************/	
	public static boolean gf_IsElementPresent(By objLocator,String strDesc) throws Throwable 
	{
		boolean blnFlag = false;
		try 
		{			
			driver.findElement(objLocator);
			blnFlag=true;
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{ 
			if(!blnFlag)
				Reporters.failureReport("Verify "+strDesc+" is present or not.", strDesc +" is not present", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify "+strDesc+" is present or not.",strDesc +" is present");
		}

	}		

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsElementNotPresent()
	 * Description				: 	To verify element not present on page.
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return
	 * @throws Throwable
	 ***************************************************************************************************************************/
	public static boolean gf_IsElementNotPresent(By objLocator, String strLocatorName)throws Throwable 
	{ 
		boolean blnFlag = true;
		try {

			driver.findElement(objLocator);
			blnFlag = false;
			return blnFlag;
		} catch (Exception e) {				
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify "+strLocatorName+" element not present","Failed to verify.", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify "+strLocatorName+" element not present","Successfully verified " );
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsElementVisibile()
	 * Description				:	This method wait selenium until visibility of Elements on WebPage.
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param lTime				: 	Wait time
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_IsElementVisibile(By objLocator, String strLocatorName) throws Throwable{
		boolean blnFlag = false;
		try{
			WebDriverWait objWait = new WebDriverWait(driver, 2);
			objWait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Check "+strLocatorName+" visibility",strLocatorName+" was not visible. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Check "+strLocatorName+" visibility",strLocatorName+" is visible");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsElementVisibileWithoutReport()
	 * Description				:	This method wait selenium until visibility of Elements on WebPage.
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param lTime				: 	Wait time
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_IsElementVisibileWithoutReport(By objLocator, String strLocatorName) throws Throwable{
		boolean blnFlag = false;
		try{
			WebDriverWait objWait = new WebDriverWait(driver, 2);
			objWait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			blnFlag = true;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_IsElementNotVisibile()
	 * Description				:	This method wait selenium until visibility of Elements on WebPage.
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param lTime				: 	Wait time
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_IsElementNotVisibile(By objLocator, String strLocatorName) throws Throwable{
		boolean blnFlag = true;
		try{
			WebDriverWait objWait = new WebDriverWait(driver, 1);
			objWait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			blnFlag = false;
			return blnFlag;
		}
		catch(Exception e)
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Validate element visible are not",strLocatorName+" element is visible. ", false );
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Validate element visible are not",strLocatorName+" element was not visible");
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_InvalidElementPresent()
	 * Description				:  	
	 * @param objLocator		:	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	:	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return
	 * @throws Throwable
	 ****************************************************************************************************************************/
	public static boolean gf_InvalidElementPresent(By objLocator, String strLocatorName) throws Throwable 
	{ //
		boolean blnFlag = false;
		try {
			driver.findElement(objLocator);
			blnFlag = true;
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		} 
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify "+strLocatorName+" element not present","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify "+strLocatorName+" element not present","Successfully verified " );
		}
	}


	/****************************************************************************************************************************
	 * Function Name			:   gf_JsClick()
	 * Description				:   Clicks An an element using java executor
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return boolean (true or false)
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public static boolean gf_JsClick(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(objLocator));
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Click on "+strLocatorName,"Failed to click on "+strLocatorName, false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Click on "+strLocatorName,"Successfully clicked on "+strLocatorName );
		} 
	}
	/****************************************************************************************************************************
	 * Function Name			:   gf_JsClickWithOutReport()
	 * Description				:   Clicks An an element using java executor
	 * @param objLocator		: 	Action to be performed on element (Get it from Object repository)
	 * @param strLocatorName	: 	Meaningful name to the element (Ex:Login Button, SignIn Link etc..)
	 * @return boolean (true or false)
	 * @throws Throwable 
	 ***************************************************************************************************************************/
	public static boolean gf_JsClickWithOutReport(By objLocator, String strLocatorName) throws Throwable {
		boolean blnFlag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(objLocator));
			blnFlag = true;
			return blnFlag;
		} catch (Exception e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	}
	/****************************************************************************************************************************
	 * Function Name			:   gf_GetCurrentDate()
	 * Description				:	To get current date value.
	 * @param strFormat			:	Date Format to get current date
	 * @return
	 *  Returns current date in prescribe format
	 ***************************************************************************************************************************/
	public static String gf_GetCurrentDate(String strFormat)
	{
		try{

			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			Date dateObj = new Date();
			return dateFormat.format(dateObj);
		}
		catch(Exception e){
			return null;
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_GetPreviousDate()
	 * Description				:	To get previous date value.
	 * @param strFormat			:	Date Format to get current date
	 * @return
	 *  Returns current date in prescribe format
	 ***************************************************************************************************************************/
	public static String gf_GetPreviousDate(String strFormat,int iPrevious)
	{
		try{
			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -iPrevious);    
			return dateFormat.format(cal.getTime());
		}
		catch(Exception e){
			return null;
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:   gf_SwitchToRecentOpenedWindow()
	 * Description				:	To switch form old window to recent opened window
	 * @throws InterruptedException 
	 ***************************************************************************************************************************/
	public static void gf_SwitchToRecentOpenedWindow() throws InterruptedException
	{
		String strPrntWnd = driver.getWindowHandle();
		Thread.sleep(lSleep_Low);
		Set<String> strWnds = driver.getWindowHandles();
		for(String a : strWnds)
		{
			if(!a.equalsIgnoreCase(strPrntWnd))
				driver.switchTo().window(a);
			Thread.sleep(lSleep_Low);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_VerifyHeadingsofTable()
	 * Description				:  Function To get column count and print data in Columns
	 * @param objLocator		: Action to be performed on element (Get it from Object repository)
	 * @return					: Returns no of columns.
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static boolean gf_VerifyHeadingsofTable(By objLocator,String strExpectedHeaderText) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			WebElement tr = driver.findElement(objLocator);
			List<WebElement> columns = tr.findElements(By.tagName("th"));
			String[] strExpectedHeader=strExpectedHeaderText.split(",");
			int length=strExpectedHeader.length;
			int a = columns.size()-1;
			for (int i = 0; i < a; i++) 
			{
				WebElement column=columns.get(i);
				String ActualHeaderText=column.getText().trim();
				for(int j=0;j<=length;j++){
					if(ActualHeaderText.trim().contentEquals(strExpectedHeader[i]))
					{  
						blnFlag = true;
						break;
					}
				}
			}
			return blnFlag;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return blnFlag;
		}
		finally
		{
			if(!blnFlag)
				Reporters.failureReport("Verify table headers","Failed to verify. ", false);
			else if(blnEventReport && blnFlag)
				Reporters.successReport("Verify table headers","Successfully verified and header text is "+strExpectedHeaderText);
		}
	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_CloseAndOpenBrowser()
	 * Description				:  Function To close and reopen the browser
	 * @param objLocator		: 
	 * @return					: 
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static void gf_CloseAndOpenBrowser() throws Exception
	{
		driver.close();
		Thread.sleep(lSleep_Low);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		if(sBrowser.equalsIgnoreCase("firefox"))
		{
			driver= new FirefoxDriver();
			driver.manage().window().maximize();
		}
		else if(sBrowser.equalsIgnoreCase("ie"))
		{
			File file = new File("Drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			driver= new InternetExplorerDriver();
			driver.manage().window().maximize();
		}
		else if(sBrowser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");	
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--test-type");
			driver=new ChromeDriver(options);
			driver.manage().window().maximize();				
		}

	/*	driver = new EventFiringWebDriver(webDriver);
		MyListener myListener = new MyListener();
		driver.register(myListener);*/
		try 
		{
			driver.manage().deleteAllCookies();
			int implicitWaitTime = 20;
			if("ie".equalsIgnoreCase(configProps.getProperty("browserType"))){
				driver.manage().timeouts().implicitlyWait(implicitWaitTime * 2, TimeUnit.SECONDS);
			}else{
				driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			}
		}
		catch (Exception e1)
		{
			System.out.println(e1);
		}

	}

	/****************************************************************************************************************************
	 * Function Name			:  gf_IntranetCredentials()
	 * Description				:  Function To get intranet application credentials
	 * @param objLocator		: 
	 * @return					: 
	 * @throws Throwable 
	 ****************************************************************************************************************************/
	public static void gf_IntranetCredentials() throws Exception
	{
		ReadResourceData.readIntranetCredentialsData();
		IUSER_NAME = ReadResourceData.IntranetCredentialsMap.get("User_Name");
		IUSER_ID = ReadResourceData.IntranetCredentialsMap.get("User_Id");
		IPASSWORD = ReadResourceData.IntranetCredentialsMap.get("Password");
		LANGUAGE = ReadResourceData.IntranetCredentialsMap.get("Language");
		INTRANET_URL_Test = ReadResourceData.IntranetCredentialsMap.get("Intranet_Test");
		INVOICING_URL_Test = ReadResourceData.IntranetCredentialsMap.get("Invoicing_Test");
		PAYMENTS_URL_Test = ReadResourceData.IntranetCredentialsMap.get("Payments_Test");
		INTRANET_URL_Dev = ReadResourceData.IntranetCredentialsMap.get("Intranet_Dev");
		INVOICING_URL_Dev = ReadResourceData.IntranetCredentialsMap.get("Invoicing_Dev");
		PAYMENTS_URL_Dev = ReadResourceData.IntranetCredentialsMap.get("Payments_Dev");
		DOWNLOADLOCATION = ReadResourceData.IntranetCredentialsMap.get("DownloadLocation");
	}

	public static void  stepReport( String sTestCaseStep)throws Throwable
	{
		int intReporterType=Integer.parseInt(configProps.getProperty("reportsType"));
		if(intReporterType == 1){
			HtmlReporters.onSuccessStepReport(sTestCaseStep, "");
		}else if(intReporterType == 2){
			Reporters.successReport(sTestCaseStep, "");
		}
	}

	/********************************************************************************************************
	 * Function Name		:	gf_CatchBlock()
	 * Author				:	 
	 * Description			:	This method is used to handle the exception
	 * Date of creation 	:	
	 * Input Parameters		:	
	 * modifying person 	: 	
	 * Date of modification	:
	 * @throws Throwable
	 ********************************************************************************************************/
	public void gf_CatchBlock(Exception e) throws Throwable{
		logger.warning("Script Failed due to :"+e.getMessage());
		if(e.getMessage() != null)
			gStrErrMsg=e.getMessage();
		if(gStrErrMsg.toLowerCase().trim().contains("closed"))
		{
			stepReport("Due to Error browser closed");
			HtmlReporters.closeDetailedReport1();
			//openBrowser();		
			Thread.sleep(lSleep_Low);
		}
		else
		{
			if(blnExcFlag)
				stepReport("Closing the browser");
			HtmlReporters.closeDetailedReport1();
		}
	}
	
	public static boolean gf_AsserTitle(String title) throws Throwable {
		 
        boolean blnFlag = false;
        try{
               By windowTitle = By.xpath("//title[contains(text(),'"+title+"')]");
               gf_WaitForTitlePresent(windowTitle);
               //AssertJUnit.assertEquals(getTitle(), title);              
               if(getTitle().equals(title)){
               blnFlag = true;
               return blnFlag;
               }
               else
               {
                     blnFlag = false;
               }
        }catch(Exception ex){
               gStrErrMsg = ex.getMessage();
              
        }
        finally
        {
               if(!blnFlag)
                     Reporters.failureReport("Assert page title","Failed to assert Title "+title, false);
               else if(blnEventReport && blnFlag)
                     Reporters.successReport("Assert page title","Successfully asserted Title "+title);
        }
        return blnFlag;
 }
	
	public static boolean gf_KeyEventTab(By objLocator,String strLocatorName) throws Throwable 
	{
		boolean blnFlag = false;
		try
		{
			driver.findElement(objLocator).sendKeys(Keys.TAB);
			blnFlag = true;
			return blnFlag;
		}
		catch (Exception e) 
		{
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	
}
	public static void waitTime() throws InterruptedException {

		Thread.sleep(5000);
	}


	public static int getElementCount(By objLocator, String strLocatorName)
			throws Throwable {
		int eleCount = 0;
		try {
			eleCount = driver.findElements(objLocator).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eleCount;
	}

	public static String getCurrentURL() throws Throwable {
		String currentURL = null;
		try {
			currentURL = driver.getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentURL;
	}
}