package com.prokarma.qa.web.helpers;

public interface ExtentReportWrapper {
	
	public void startTest(String testCaseName); 
	public void endTest();
	public void write(String logStatus, String message);
	
}
