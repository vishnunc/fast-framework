package com.qentelli.automation.utilities;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.maven.artifact.resolver.WarningResolutionListener;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.qentelli.automation.accelerators.Actiondriver;
import com.qentelli.automation.accelerators.Base;
import com.qentelli.automation.utilities.*;
import com.retailpos.objectRepository.Inventory;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class RepairTests extends Actiondriver {
	static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	static Calendar cal;
	private static int count = 1;
	public static int fixLine=0;
	public static String issueTime,exceptionClassName,exceptionSimpleName,exceptionMessage,fixType,fixProvided,status,issueCode;
	public static String rootCause12 = null, objIndentifictaion=null;
	
	public static void fixTheIssue(Exception e) throws Throwable {
		exceptionClassName = e.getClass().getName();
		exceptionSimpleName = e.getClass().getSimpleName();
		exceptionMessage=e.getMessage().split("\\r?\\n")[0];
		issueTime=Accessories.timeStamp().replace(" ", "_").replace(":", "_").replace(".", "_");
		//Writing the Exception to the DataBase
		writeExceptionToDataBase(exceptionClassName,exceptionSimpleName,exceptionMessage,issueTime);
		switch(exceptionSimpleName) {
			case "IllegalStateException":
			{
				System.out.println("The Excpetion caused is" + exceptionSimpleName+ "and the cause is "+ exceptionMessage);
				Reporters.failureReport(" Test Execution could not be initiated because of  "+ exceptionSimpleName, "  :  "+ exceptionMessage,false );
				fixType="Manual";
				issueCode="The Driver executable is not available";
				fixProvided="";
				status="Pending";
				writeFixToDB("","",0);
				break;
			}
			case "CommunicationsException":
			{
				System.out.println(exceptionClassName);
				try{
					if(exceptionClassName.contains("mysql")&&exceptionMessage.contains("Communications link failure")){
						Reporters.failureReport("Execution has been aborted because of the exception  "+ exceptionSimpleName, exceptionClassName+" :  "+ exceptionMessage,false );
						if(!isServerUp(3307)){
							executeAsAdministrator("C:\\Users\\Administrator\\Documents\\RajuVemu\\StartMYSQL.bat","");
						}
						if(isServerUp(3307)){
							issueCode="MYSQL Server - Not running";
							fixType="Automatic";
							fixProvided="MYSQL Server Started";
							status="Fixed";
							writeFixToDB("",fixProvided,0);
						}
						break;
					}
				}
				catch(Exception ex){
					
				}
				break;
			}
			case "NoSuchElementException":
			{
				System.out.println(e.getStackTrace().toString().split(":")[0]);
				try{
					rootCause12=ExceptionUtils.getRootCause(e).toString().split(":")[0];
					objIndentifictaion=ExceptionUtils.getRootCause(e).toString().split("\\{")[1].split("\\n")[0].replace("}", "");
				}
				catch(Exception ex){
					System.out.println("Exception message for get root cause is "+ex.getMessage());
				}
				System.out.println("The exception is : "+e.getMessage());
				Reporters.failureReport(" Test Execution failed because of the exception ",  e.getMessage().split("\\r?\\n")[0],false );
				String scriptName1=ClassLocator.getCallerClass().getName();
				String rootCause123=ExceptionUtils.getRootCause(e).toString().split(":")[0];
				String exceptionCommand123=ExceptionUtils.getRootCause(e).toString().split(":")[1];
				String filePath123 = null;
				int errorLine123=0;
				System.out.println("scriptName : "+scriptName1+"----------rootCause"+rootCause12+"-------exceptionCommand"+exceptionCommand123);
				int j1=e.getStackTrace().length;
				for (int i=0;i<j1;i++){
					StackTraceElement ln=e.getStackTrace()[i];
					if(ln.getClassName().contains(scriptName1)){
						System.out.println("The exception details are as : "+ln.getClassName()+"/"+ln.getMethodName()+":"+ln.getLineNumber());
						filePath123=ln.getFileName();
						errorLine123=ln.getLineNumber();
					}
				}
				String testScriptsFolderPath="C:\\Users\\Administrator\\Documents\\RajuVemu\\JsonAI\\JsonAI\\src\\main\\java\\";
				String testScriptFile=testScriptsFolderPath+scriptName1.replace(".", "\\")+".java";
				String line321 = Files.readAllLines(Paths.get(testScriptsFolderPath+scriptName1.replace(".", "\\")+".java")).get(errorLine123-1).replace("\"", "\\\"").replace(");", "").trim();
				String exceptionMethodTokens[]=line321.split("\\(",2);
				String exceptionCommandParameters[]=exceptionMethodTokens[1].replace("\\)","").split(",");
				System.out.println("Method Name "+exceptionMethodTokens[0]);
				for(int i=0;i<exceptionCommandParameters.length;i++){
					System.out.println("Paramter "+exceptionCommandParameters[i]);
				}
				By SelectorValue,SelectorIndex;
				String selectIndex="";
				switch(exceptionMethodTokens[0]){
				case "gf_SelectByValue":
					SelectorValue= getObject(exceptionCommandParameters[0]);
					HashMap<Integer,String> dropDownValues=getAllListItemsWithValue(SelectorValue);
				      Set set = dropDownValues.entrySet();
				      Iterator iterator = set.iterator();
				      while(iterator.hasNext()) {
				         Map.Entry mentry = (Map.Entry)iterator.next();
				         selectIndex=mentry.getKey().toString();
				         Reporters.failureReport("Number of Options in the Drop down and the available option-value pairs in the drop down are ",mentry.getKey()+" , "+mentry.getValue().toString(),false);
				      }
				      String methodcontents[]={"gf_SelectByIndex",exceptionCommandParameters[0].replace("\\", ""),selectIndex,exceptionCommandParameters[2]};
				      replaceFileContent(testScriptFile,errorLine123-1,methodcontents);
				    break;
				case "gf_SelectByIndex":
					SelectorIndex= getObject(exceptionCommandParameters[0]);
					break;
				default:
					changeObjectInRepository(exceptionCommandParameters,objIndentifictaion);
				}
				break;
			}
			default:
			{
			
			}
		}
	}
	
	private static void writeExceptionToDataBase(String exceptionClsName, String exceptionCauseSimpleName,String exceptionDetailMessage, String exceptionEnounteredTime) throws UnknownHostException {
		TransportClient client=TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		List<DiscoveryNode> connectedNodes = null;
		connectedNodes=client.connectedNodes();
		System.out.println("Number of Nodes"+connectedNodes.size());
		System.out.println("Name of Node 1 is "+connectedNodes.get(0).name());
		XContentBuilder builder;
		try {
			builder = jsonBuilder()
					.startObject()
					.field("exceptionClassName",exceptionClsName)
					.field("exceptionSimpleName",exceptionCauseSimpleName)
					.field("exceptionMessage",exceptionDetailMessage)
					.field("Time",exceptionEnounteredTime)
					.endObject();
			client.prepareIndex("scriptissues", "issueLine",issueTime).setSource(builder).execute().actionGet();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void changeObjectInRepository(String[] commandparameters1, String objIndentifictaion) throws Throwable {
		System.out.println("Change Object in repository");
		String className="com.aidemo.objectRepository.HotelBooking";
		String variable="passWord";
		HashMap<String,String> objectProperties=getObjectProperties(variable);
		String objprops[]={"id","className","xpath"};
		for(int i=0;i<objprops.length;i++){
			By newObject=getObject(objprops[i], objectProperties);
			Actiondriver AE=new Actiondriver();
			if(AE.gf_IsElementVisibileWithoutReport(newObject, variable)){
				updateObjectRepository("HotelBooking","passWord",objprops[i],objectProperties.get(objprops[i]));
				break;
			}
		}
	}

	public static void updateObjectRepository(String fileName,String VariableName,String prop,String Value) throws IOException, Throwable {
		String className="com.aidemo.objectRepository";
		String variable="passWord";
		Class c = Class.forName("com.aidemo.objectRepository.HotelBooking"); 
		java.lang.reflect.Field[] fields = c.getFields();
		System.out.println("HotelBooking.passWord   "+Inventory.passWord);
		String scriptPath1="C:\\Users\\Administrator\\Documents\\RajuVemu\\JsonAI\\JsonAI\\src\\main\\java\\"+className.replace(".","\\")+"\\"+fileName+".java";
		System.out.println("System.getProperty : "+ System.getProperty("user.dir"));
		File source = new File(scriptPath1);
        File dest = new File(System.getProperty("user.dir")+"/target/"+fileName+"_"+Base.timeStampBeforeSutie+".java");
		//System.out.println("Copy File To Create Back Up");
		Files.copy(source.toPath(), dest.toPath());
		//System.out.println("Copy File To Create Back Up");
		FileInputStream fstream = new FileInputStream(scriptPath1);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String abc="int a;";
		String strLine;
		int i=0;
		
		PrintWriter writer=new PrintWriter("C:\\Users\\Administrator\\Documents\\RajuVemu\\temp.java");
		while ((strLine = br.readLine()) != null)   {
			if(!strLine.contains(VariableName))
				writer.println(strLine);
			else{
				writer.println("// This is Fix During Execution"+System.getProperty("line.separator")+
						"//"+strLine+System.getProperty("line.separator")+
						strLine.split("=")[0]+" ="+" By."+prop+"(\""+Value+"\");");
				Reporters.successReport("The Object Repository is updated for "+VariableName,strLine.split("=")[0]+" ="+" By."+prop+"(\""+Value+"\");");
				issueCode=strLine;
				fixProvided=strLine.split("=")[0]+" ="+" By."+prop+"(\""+Value+"\")";
				fixLine=i;
			}
			i++;
		}
		fstream.close();
		writer.flush();
		writer.close();
	    //Writing Temp File To Object Repository File
		File file = new File(scriptPath1);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
		FileInputStream fstream1 = new FileInputStream("C:\\Users\\Administrator\\Documents\\RajuVemu\\temp.java");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
		String strLine1;
		PrintWriter writer1=new PrintWriter(file);
		while ((strLine1 = br1.readLine()) != null)   {
			writer1.println(strLine1);
		    i++;
		}
		writer1.flush();
		writer1.close();
		System.out.println("Fixed The issue");
		fixType="Automatic";
		status="Fixed";
		writeFixToDB(fileName,VariableName,fixLine+1);
	}

	public static void writeFixToDB(String fileName, String variableName, int i) throws IOException {
		System.out.println("Write  fix details to DB");
		try{
			TransportClient client=TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			List<DiscoveryNode> connectedNodes = null;
			connectedNodes=client.connectedNodes();
			System.out.println("Number of Nodes"+connectedNodes.size());
			System.out.println("Name of Node 1 is "+connectedNodes.get(0).name());
			XContentBuilder builder=jsonBuilder()
					.startObject()
					.field("TestIteration",itc.getSuite().getName()+Base.timeStampBeforeSutie)
					.field("TestCase",Base.currentTestname)
					.field("ExceptionName",exceptionSimpleName)
					.field("ExceptionDescription",exceptionMessage)
					.field("ExceptionEnounteredTime",issueTime)
					.field("ExceptionFile",fileName)
					.field("ExceptionLineNumber",i)
					.field("FixType",fixType)
					.field("errorLine",issueCode)
					.field("FixDetails",fixProvided)
					.field("FixStatus",status)
					.endObject();
			client.prepareIndex("trialwork",itc.getSuite().getName()+Base.timeStampBeforeSutie,issueTime).setSource(builder).execute().actionGet();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static HashMap<Integer,String> getAllListItemsWithValue(By locator) {
		WebElement element = driver.findElement(locator);
        Select s = new Select(element);
        HashMap<Integer,String> hoptionvalue=new HashMap<Integer,String>();
        List<WebElement> elementcount = s.getOptions();
        String optionvalue= "option-value";
        System.out.println(elementcount.size());
        for(int i=0 ;i<elementcount.size();i++)
        {
            String text = elementcount.get(i).getText();
            String value=elementcount.get(i).getAttribute("value");
            optionvalue=optionvalue+","+text+"-"+value;
        }
        hoptionvalue.put(elementcount.size()-1, optionvalue);
        return hoptionvalue;
	}
	
	public static boolean isServerUp(int port) throws UnknownHostException, IOException, SQLException {
	    boolean isUp = false;
	   try{
	    Socket socket = new Socket("127.0.0.1", port);
		isUp = true;
		socket.close();
	   }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	//System.out.println("Exception"); // Server is down
	    }
	    return isUp;
	}
	
	public static void executeAsAdministrator(String command, String args)
    {
        Shell32X.SHELLEXECUTEINFO execInfo = new Shell32X.SHELLEXECUTEINFO();
        execInfo.lpFile = new WString(command);
        if (args != null)
            execInfo.lpParameters = new WString(args);
        execInfo.nShow = Shell32X.SW_SHOWDEFAULT;
        execInfo.fMask = Shell32X.SEE_MASK_NOCLOSEPROCESS;
        execInfo.lpVerb = new WString("runas");
        boolean result = Shell32X.INSTANCE.ShellExecuteEx(execInfo);
        if (!result)
        {
            int lastError = Kernel32.INSTANCE.GetLastError();
            String errorMessage = Kernel32Util.formatMessageFromLastErrorCode(lastError);
            throw new RuntimeException("Error performing elevation: " + lastError + ": " + errorMessage + " (apperror=" + execInfo.hInstApp + ")");
        }
    }

	public static class ClassLocator extends SecurityManager {
		  public static Class getCallerClass() {
		    return new ClassLocator().getClassContext()[2];
		  }
		}
	
	 public static String getCallerInfo() {

	      String callerInfo = "DefaultName-callerUnknown";
	      // use class name instead of filename since filename is null for Sun/Java
	      // classes when running in a JRE-only context
	      String thisClassName = "org.stirling.stackinfo.CallerInfo";
	      Thread thread = Thread.currentThread();
	      StackTraceElement[] framesArray = thread.getStackTrace();

	      // look for the last stack frame from this class and then whatever is next
	      // is the caller we want to know about
	      for (StackTraceElement stackFrame : framesArray) {

	         // filter out Thread because we just created a couple frames using
	         // Thread
	         if (!stackFrame.getClassName().equals("java.lang.Thread")
	             && !stackFrame.getClassName().equals(thisClassName)) {
	            // handle case for file name when debug info is missing from
	            // classfile
	            String fileName = stackFrame.getFileName() != null ? stackFrame.getFileName(): "Unknown";
	            StringBuilder sb = new StringBuilder(stackFrame.getMethodName());
	            sb.append('(');
	            sb.append(fileName);
	            sb.append(':');
	            // if no debug info, returns a negative number
	            sb.append(stackFrame.getLineNumber());
	            sb.append(')');
	            callerInfo = sb.toString();
	            break;
	         }
	      }
	      return callerInfo;
	   }
	 public static By getObject(String object){
		 By obj=null;
		 String objProperties[]=object.replace("By.", "").split("\\(");
		// String abc=getvalue(objProperties[1]);
		int a= objProperties[1].indexOf("\"");
		int b=objProperties[1].lastIndexOf("\"");
		String objectValue=objProperties[1].substring(a+1, b-1);
		 switch(objProperties[0]){
		 case "id":
			 obj=By.id(objectValue);
			 break;
		 case "className":
			 obj=By.className(objProperties[1]);
			 break;
			 default:
		 }
		 return obj;
	 }
	 public static By getObject(String Property, HashMap<String,String> Objectmap){
		 By obj=null;
		 try {
			 System.out.println("Property "+Property);
			switch(Property){
			 case "id":
				 obj=By.id(Objectmap.get(Property).toString());
				 break;
			 case "className":
				 obj=By.className(Objectmap.get(Property));
				 break;
			 case "tagName":
				 obj=By.tagName(Objectmap.get(Property));
				 break;
			 case "xpath":
				 By.xpath("//"+Objectmap.get("tagName")+"[@id='"+Objectmap.get(Property)+"']");
				 default:
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return obj;
	 }

	public static void replaceFileContent(String fPath,int errorLine, String args[]) throws Throwable{
		 FileInputStream fstream = new FileInputStream(fPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String abc="int a;";
			String strLine;
			int i=0;
			PrintWriter writer=new PrintWriter("C:\\Users\\Administrator\\Documents\\RajuVemu\\temp.java");
			while ((strLine = br.readLine()) != null)   {
				if(i!=errorLine)
					writer.println(strLine);
				else{
					issueCode=args[0]+"("+args[1]+","+args[2]+","+args[3];
					writer.println("// This is Fix During Execution"+System.getProperty("line.separator")+"//"+strLine+System.getProperty("line.separator")+args[0]+"("+args[1]+","+args[2]+","+args[3].replace("\\", "")+");");
					Reporters.successReport("The issue is fixed by replacing the select type " , args[0]+"("+args[1]+","+args[2]+","+args[3].replace("\\", "")+");");
					fixProvided=args[0]+"("+args[1]+","+args[2]+","+args[3].replace("\\", "")+")";
					fixLine=i;
				}
				i++;
			}
			fstream.close();
			writer.flush();
			writer.close();
			File file = new File(fPath);
			if(file.delete()){
				System.out.println(file.getName() + " is deleted!");
			}else{
				System.out.println("Delete operation is failed.");
			}
			FileInputStream fstream1 = new FileInputStream("C:\\Users\\Administrator\\Documents\\RajuVemu\\temp.java");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
			String strLine1;
			PrintWriter writer1=new PrintWriter(fPath);
			while ((strLine1 = br1.readLine()) != null)   {
				writer1.println(strLine1);
			    i++;
			}
			writer1.flush();
			writer1.close();
			writeFixToDB(fPath, fixProvided, fixLine);
	 }
	public static void fixTheIssueObjectChange(Exception e) throws Throwable {
		System.out.println("The exception is : "+e.getMessage());
		String scriptName=ClassLocator.getCallerClass().getName();
		String rootCause=ExceptionUtils.getRootCause(e).toString().split(":")[0];
		String exceptionCommand=ExceptionUtils.getRootCause(e).toString().split(":")[1];
		String filePath = null;
		int errorLine=0;
		System.out.println("scriptName : "+scriptName+"----------rootCause"+rootCause+"-------exceptionCommand"+exceptionCommand);
		int j=e.getStackTrace().length;
		for (int i=0;i<j;i++){
			StackTraceElement ln=e.getStackTrace()[i];
			if(ln.getClassName().contains(scriptName)){
				System.out.println("The exception details are as : "+ln.getClassName()+"/"+ln.getMethodName()+":"+ln.getLineNumber());
				filePath=ln.getFileName();
				errorLine=ln.getLineNumber();
			}
		}
		String scriptPath="C:\\Users\\Administrator\\Documents\\RajuVemu\\JsonAI\\JsonAI\\src\\main\\java\\";
		String fPath=scriptPath+scriptName.replace(".", "\\")+".java";
		String line32 = Files.readAllLines(Paths.get(scriptPath+scriptName.replace(".", "\\")+".java")).get(errorLine-1).replace("\"", "\\\"").replace(");", "").trim();
		String commandTokens[]=line32.split("\\(",2);
		String commandparameters[]=commandTokens[1].replace("\\)","").split(",");
		System.out.println("Method Name "+commandTokens[0]);
		for(int i=0;i<commandparameters.length;i++){
			System.out.println("Paramter "+commandparameters[i]);
		}
		By SelectorValue,SelectorIndex;
		String selectIndex="";
		switch(commandTokens[0]){
		case "selectByValue":
			SelectorValue= getObject(commandparameters[0]);
			HashMap<Integer,String> dropDownValues=getAllListItemsWithValue(SelectorValue);
		      Set set = dropDownValues.entrySet();
		      Iterator iterator = set.iterator();
		      while(iterator.hasNext()) {
		         Map.Entry mentry = (Map.Entry)iterator.next();
		         selectIndex=mentry.getKey().toString();
		         Reporters.failureReport("Number of Options in the Drop down and the available option-value pairs in the drop down are ",mentry.getKey()+" , "+mentry.getValue().toString(),false);
		      }
		      String methodcontents[]={"selectByIndex",commandparameters[0].replace("\\", ""),selectIndex,commandparameters[2]};
		      replaceFileContent(fPath,errorLine-1,methodcontents);
		    break;
		case "selectByIndex":
			SelectorIndex= getObject(commandparameters[0]);
			break;
			default:
		}
	}
	
	public static Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"/ApplicationMap/Objects.xlsx");
	public static HashMap<String,String> getObjectProperties(String objectName){
		String sheetName="Objects";
		int objectRowNumb=0;
		for(int rNum=1;rNum<=xls.getRowCount(sheetName);rNum++){
			if(objectName.equalsIgnoreCase(xls.getCellData(sheetName, 0, rNum))){
				objectRowNumb = rNum;
				break;
			}
		}
		HashMap<String,String> objProperties=new HashMap<String,String>();;
		for(int cNum=1;cNum<xls.getColumnCount(sheetName);cNum++){
			System.out.println("My vlaues:"+xls.getCellData(sheetName, cNum, objectRowNumb)+"----asdadads");
			if(xls.getCellData(sheetName, cNum, objectRowNumb).length()>0){
				objProperties.put(xls.getCellData(sheetName, cNum, 1), xls.getCellData(sheetName, cNum, objectRowNumb));
			}
		}
		return objProperties;
	}
}
