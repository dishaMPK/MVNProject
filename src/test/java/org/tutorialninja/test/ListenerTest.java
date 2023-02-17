package org.tutorialninja.test;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTest implements ITestListener{

	public void onTestStart(ITestResult result) {
		System.out.println("Test is started");
	}
	
	public void onTestFailure(ITestResult result) {
		System.out.println("Test is failed");
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test is passed");
	}
	
	public void onFinish(ITestContext context) {
		System.out.println("Test is finished");
	}


}
