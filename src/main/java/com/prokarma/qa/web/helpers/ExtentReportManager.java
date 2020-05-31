package com.prokarma.qa.web.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportManager {
	
	static ExtentTest test;
	static ExtentReports report;
	
	
	public ExtentTest startTest(String testCaseName) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
		String Currenttime=sdf.format(date);
		
		report = new ExtentReports(System.getProperty("user.dir")+File.separator+"Reports"+File.separator+testCaseName+Currenttime+".html");
		test = report.startTest(testCaseName);
		return test;
	}
	
	public void write(String logStatus,String message) {
		
		if(logStatus.equalsIgnoreCase("INFO")){
			test.log(LogStatus.INFO, message);
		}
		
		if(logStatus.equalsIgnoreCase("PASS")) {
			test.log(LogStatus.PASS, message);
		}
		
		if(logStatus.equalsIgnoreCase("FAIL")) {
			test.log(LogStatus.FAIL, message);
		}
	}
	
	public void endTest() {
		report.endTest(test);
		report.flush();
	}
	
	

}
