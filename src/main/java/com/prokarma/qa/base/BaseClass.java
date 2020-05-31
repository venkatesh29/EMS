package com.prokarma.qa.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.prokarma.qa.web.helpers.EMSException;
import com.prokarma.qa.web.helpers.ExtentReportWrapper;
import com.prokarma.qa.web.helpers.ExcelFileUtil;
import com.prokarma.qa.web.helpers.Log;
import com.prokarma.qa.web.pages.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass implements ExtentReportWrapper{
	
	protected static WebDriver driver;
	protected static Ini prefs;
	protected static List<Object> setup;
	
	protected static ExtentReports report;
	protected static ExtentTest test;
	
	public void configurationProvider() {
		final File fname = new File(System.getProperty("user.dir") + File.separator+ "Configuration"+File.separator+"config.ini");
		try {
			prefs = new Ini(fname);
		} catch (InvalidFileFormatException e) {
			System.err.println("Configuration file format is not .ini. " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Configuration file does not exist. Check the path. " + e.getMessage());
		}
	}
	
	public List<Object> gettestup() {
		
			if(prefs.containsKey("ENVIRONMENT")==true) {
				
				setup=new ArrayList<Object>();
				
				if(prefs.get("ENVIRONMENT", "testingenvironment")!=null) {
					setup.add(prefs.get("ENVIRONMENT", "testingenvironment"));
				}else {
					throw new EMSException(this.getClass().getSimpleName(),
							Thread.currentThread().getStackTrace()[1].getMethodName(), "required testing environment not present in config file");	
				}
				
				if(prefs.get("ENVIRONMENT","testingbrowser")!=null) {
					setup.add(prefs.get("ENVIRONMENT","testingbrowser"));
				}else {
					throw new EMSException(this.getClass().getSimpleName(),
							Thread.currentThread().getStackTrace()[1].getMethodName(), "required testing browser not present in config file");	
				}
				
				if(prefs.get("ENVIRONMENT","testinguser")!=null) {
					setup.add(prefs.get("ENVIRONMENT","testinguser"));
					
					String testUser=prefs.get("ENVIRONMENT","testinguser");
					String testEnv=prefs.get("ENVIRONMENT", "testingenvironment");
					
					switch(testUser) {
							case "user1":
								getPassword(testEnv,"user1","password1");
								break;
							case "user2":
								getPassword(testEnv,"user2","password2");
								break;
							case "user3":
								getPassword(testEnv,"user3","password3");
								break;
					}
					
				}else {
					throw new EMSException(this.getClass().getSimpleName(),
							Thread.currentThread().getStackTrace()[1].getMethodName(), "required testing user not present in config file");	
				}

			}else {
				throw new EMSException(this.getClass().getSimpleName(),
						Thread.currentThread().getStackTrace()[1].getMethodName(), "Exception in fetching setup details");		
			}
			return setup;
	}
	
	public void getPassword(String testEnv,String userType,String passwordType) {
		String testPassword="";
		String testUser="";
		testUser=prefs.get(testEnv, userType);
		testPassword=prefs.get(testEnv,passwordType);
		if(testPassword!="") {
			setup.add(testUser);
			setup.add(testPassword);
		}else {
			throw new EMSException(this.getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "required testing password not present in config file");
		}
	}
	
	public WebDriver InvokeBrowser(){	
		
		String browserName=(String) setup.	get(1);
		
		try {
			if(browserName.equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver", "BrowserDrivers"+ File.separator+"chromedriver.exe");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			    Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);			
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "TestOutput");
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
			    driver= new ChromeDriver(options); 
	
			}else if(browserName.equalsIgnoreCase("FIREFOX")) {
				System.setProperty("webdriver.gecko.driver", "BrowserDrivers"+ File.separator+"geckodriver.exe");
				driver=new FirefoxDriver();
			}else if(browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", "BrowserDrivers"+ File.separator+"IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
			else
				throw new EMSException("Unsupported Platform/Browser Configuration " + browserName);		
		}catch(NullPointerException e) {
			throw new EMSException("Browser is " + browserName);
		}	
	
		return driver;
	}
	
	public void closeBrowser() {
		
		if(driver!=null) {
			driver.close();
			driver.quit();
			write("INFO","Closing browser successfull");
		}	
	}

	public LoginPage launchApp() {		
		String TEST_ENV=(String) setup.get(0);
		if(driver == null) {
//			Log.info("Application Launch Failed");
//			write("FAIL", "Application Launch Failed");
			throw new EMSException(this.getClass().getSimpleName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "WebDriver object was not Initialized to driver");
		}
		else {
		    try{
		    	driver.get(prefs.get(TEST_ENV, "url"));
		    	driver.manage().window().maximize();  
		    }catch (TimeoutException te){
		    	throw new EMSException(this.getClass().getSimpleName(),
						Thread.currentThread().getStackTrace()[1].getMethodName(), "url launching failed");
		    }		
		    
		    driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		    driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		    
//			Log.info("Application Launch Successfull");
//			write("PASS", "Application Launch Successfull");
		}
		return new LoginPage(driver);
	}
	
	public String getTestInputFile() {
		String testInput=System.getProperty("user.dir") + File.separator + "TestInput"+File.separator+"TestInput.xls";
		return testInput;
	}
	
	public String getTestOutputFile() {
		String testoutput=System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+"OutPutSheet.xls";
		return testoutput;
	}
	
	public String getTestInput(String sheetname,String testCaseName,String colName) throws Throwable {
		
		ExcelFileUtil exl_TestInput=new ExcelFileUtil(getTestInputFile());
		int rowCount=exl_TestInput.rowCount(sheetname);
		int reqColIndex=exl_TestInput.getColIndex(sheetname,colName);
		String testData=null;
		
		for(int i=0;i<=rowCount;i++) {
			if(exl_TestInput.getData(sheetname, i, 0).equalsIgnoreCase(testCaseName)){
				testData=exl_TestInput.getData(sheetname, i, reqColIndex);
				break;
			}
		}
		
		return testData;	
	}
	
	public void startTest(String testCaseName) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
		String Currenttime=sdf.format(date);
		
		report = new ExtentReports(System.getProperty("user.dir")+File.separator+"Reports"+File.separator+testCaseName+"_"+Currenttime+".html");
		test=report.startTest(testCaseName);	
		
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
		report.close();
	}
	
	public CommonFunctions maketestsetup(String testCaseName) throws Throwable {
		
		int testIndex=testCaseName.indexOf("tests");
		testCaseName=testCaseName.substring(testIndex+6);
		
		configurationProvider();
		gettestup();
		InvokeBrowser();
		CommonFunctions cf=new CommonFunctions();
		Log.startTestCase(testCaseName);
		startTest(testCaseName);
		cf.captureData(testCaseName);
		return cf;
	}
			
}
