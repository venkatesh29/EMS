package com.prokarma.qa.web.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private Log() {
			
	}
	
	private static String asterisks = "****************************************************************************************";
//	private static String log4jConfigFile ;
		//Initialize Log4j logs
	
//	private static Logger Log = Logger.getLogger(Log.class.getName());
	
//	static Logger logger = Logger.getLogger(Log.class);

static{
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
    }
    
private static Logger Log = Logger.getLogger(Log.class.getName());
    
    

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static void startTestCase(String sTestCaseName){
		PropertyConfigurator.configure("Log4j.properties");
		
		
//		 // creates pattern layout
//        PatternLayout layout = new PatternLayout();
//        String conversionPattern = "[%p] %d %c %M - %m%n";
//        layout.setConversionPattern(conversionPattern);
//        // creates daily rolling file appender
//        DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
//        rollingAppender.setFile("app.log");
//        rollingAppender.setDatePattern("'.'yyyy-MM-dd");
//        rollingAppender.setLayout(layout);
//        rollingAppender.activateOptions();
//        // configures the root logger
//        Logger rootLogger = Logger.getRootLogger();
//        rootLogger.setLevel(Level.DEBUG);
//        rootLogger.addAppender(rollingAppender);       
//        Logger logger = Logger.getLogger(Log.class);
		
		
		
		
		
//		 log4jConfigFile = System.getProperty("user.dir")
//                + File.separator + "log4j.xml";
//        DOMConfigurator.configure(log4jConfigFile);
 
//	   Log.info(asterisks);
	   Log.info(asterisks);
	   Log.info("$$$$$$$$$$$$$$$$$$$$$      "+" S-T-A-R-T       "+sTestCaseName+"        S-T-A-R-T"+"     $$$$$$$$$$$$$$$$$$$$$$$$$");
//	   Log.info(asterisks);
//	   Log.info(asterisks);
 
	   }
 
	//This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName){
		
		int testIndex=sTestCaseName.indexOf("tests");
		sTestCaseName=sTestCaseName.substring(testIndex+6);
		
	   Log.info("XXXXXXXXXXXXXXXXXXXXXXX    "+" -E---N---D       "+ sTestCaseName +"       E---N---D-"+"     XXXXXXXXXXXXXXXXXXXXXX");
	   Log.info(asterisks);
	 }
 
    // Need to create these methods, so that they can be called  
	public static void info(String message) {
		   Log.info(message);
		   }
 
	public static void warn(String message) {
	   Log.warn(message);
	   }
 
	public static void error(String message) {
	   Log.error(message);
	   }
 
	public static void fatal(String message) {
	   Log.fatal(message);
	   }
 
	public static void debug(String message) {
	   Log.debug(message);
	   }
 
	}