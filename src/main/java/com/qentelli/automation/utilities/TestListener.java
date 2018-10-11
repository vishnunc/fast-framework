package com.qentelli.automation.utilities;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

import com.qentelli.automation.accelerators.Base;

import org.testng.IClass;

public class TestListener implements ITestListener,IClass {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("on test method " +  getTestMethodName(result) + " start");
		Base.currentTestname=getTestMethodName(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("on test method " + getTestMethodName(result) + " success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("on test method " + getTestMethodName(result) + " failure");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("test method " + getTestMethodName(result) + " skipped");
		result.setStatus(ITestResult.FAILURE);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("test failed but within success % " + getTestMethodName(result));
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("on start of test " + context.getName());
		getRealClass();
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("on finish of test " + context.getName());
	}
	
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XmlTest getXmlTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XmlClass getXmlClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTestName() {
		System.out.println();// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getRealClass() {
		// TODO Auto-generated method stub
		this.getClass().getName();
		return null;
	}

	@Override
	public Object[] getInstances(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInstanceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long[] getInstanceHashCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInstance(Object instance) {
		// TODO Auto-generated method stub
		
	}
}