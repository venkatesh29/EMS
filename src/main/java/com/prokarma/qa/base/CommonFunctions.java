package com.prokarma.qa.base;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.prokarma.qa.web.helpers.EMSException;
import com.prokarma.qa.web.helpers.Log;
import com.prokarma.qa.web.pages.HomePage;

public class CommonFunctions  extends BaseClass {
	
	public WebDriverWait wait = null;
	
	
	public void implicitWait(WebDriver driver,int timeSeconds) {
		driver.manage().timeouts().implicitlyWait(timeSeconds, TimeUnit.SECONDS);
	}
	
	public Boolean apply(WebDriver driver) {
		try {
			return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
					.equals("complete");
		} catch (Exception e) {
			Log.info("no java script present");
			return true;
		}
	}
	
	public void captureData(String testCaseName) throws Throwable
	{	
		FileWriter fw=new FileWriter(System.getProperty("user.dir")+File.separator+"test-output"+File.separator+"Data.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(testCaseName);
		bw.flush();
		bw.close();	
	}

	public String geTtestCaseName() throws IOException {
		FileReader fr=new FileReader(System.getProperty("user.dir")+File.separator+"test-output"+File.separator+"Data.txt");
		BufferedReader br=new BufferedReader(fr);
		String exp_data=br.readLine();
		return exp_data;
	}
	
	public WebElement getRequiredElement(List<WebElement> reqElementList) {
		
		WebElement requiredelement = null;
		  
		  for(WebElement e:reqElementList) {
			  try {
				  if(e.isDisplayed() && e.isEnabled()) {
					  requiredelement=e;
					  break;
				  }
			  }catch(Exception exception){
				  
			  }
		  }
		  
		  return requiredelement;
	}
	
	/**
	 * overloaded method to check the visibility of an element on the page
	 * 
	 */
	public void fnVisibleOnPage(WebElement webElement,String fieldName) throws Exception {
	    wait = new WebDriverWait(driver,30);
		try {		
			wait.until(ExpectedConditions.visibilityOf(webElement));
//			fnHighlightElement(driver,webElement);
//			Log.info("Visibility validation on [" + fieldName + "] Passed");		
			Log.info(fieldName+" is visible on the page");
//			write("PASS", "Visibility validation on [" + fieldName + "]");
		}catch(Exception e) {
			e.getMessage();
//			Log.info("Visibility validation on [" + fieldName + "] Failed");	
			Log.info(fieldName+" is not visible on the page");
//			write("FAIL", "Visibility validation on [" + fieldName + "]");
	//		throw new Exception(fieldName+" is not visible on "+pageName);
		} 	
    }
	
//	//method is used to highlight webelemnet
//		public static void fnhighlight(WebElement element) throws InterruptedException
//		 { try {
//			fnhighlightElement(driver, element);
//			Reporter.log(" highlighted successfully", true);
//			}
//		 	catch (Exception e) {
//				Reporter.log("Highlight unsuccessful", true);			
//			}
//		}
		
      public void fnHighlightElement(WebDriver driver, WebElement element){
    	  JavascriptExecutor js=(JavascriptExecutor)driver;  
		  js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		 
			try 
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) {
//				System.out.println(e.getMessage());
			} 
		 
			js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element); 
	}
		
	public void displayedOnPage(WebDriver driver,WebElement webElement,String fieldName) throws Exception {
	    //wait = new WebDriverWait(driver,30);
		try {		
			if(webElement.isDisplayed()){
				fnHighlightElement(driver,webElement);
//				Log.info("Display validation on [" + fieldName + "] Pass");		
//				write("PASS", "Display validation on [" + fieldName + "]");
			}
		}catch(Exception e) {
			//e.getMessage();
//			Log.info("Display validation on [" + fieldName + "] Fail");		
//			write("FAIL", "Display validation on [" + fieldName + "]");
//			throw new Exception(fieldName+" is not visible on "+pageName);
		} 	
    }
		
//	public void fnClickElementByJS(WebDriver driver, String elementName,WebElement element) {
	public void fnClickElementByJS(String elementName,WebElement element) {
		try {
			Thread.sleep(10000);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
//			write("PASS", "Click Successfull on [" + elementName + "]");
//			Log.info("Click Successfull on [" + elementName + "]");
			Log.info("Clicked  "+ elementName );
		} catch (EMSException e) {
			Log.info("Failed to click on element with [" + elementName + "]");
		} catch (Exception e) {
			Log.info("Generic exception e [" + e.getMessage() + "]");
		}
	}
	
//	public boolean fnWaitForElement(WebDriver driver, WebElement element, int seconds) {
	public boolean fnWaitForElement(WebElement element, int seconds) {
		boolean displayedElement = false;

		/*
		 * if(seconds>10) seconds=seconds-10;
		 */
		WebDriverWait wait = new WebDriverWait(driver, seconds);

		try {
			// verifyAppDown();
			wait.until(ExpectedConditions.visibilityOf(element));
			displayedElement = true;
		} catch (TimeoutException e) {
			Log.info("Failed to find a visible element with [" + element + "]");
			displayedElement = false;
			Assert.fail("Unable to find a visible element with [" + element + "]");
		} catch (Exception e) {
			Log.info("Generic exception e [" + e.getMessage() + "]");
			displayedElement = false;
		}
		return displayedElement;
	}
	
	
	public void fnMouseOverByAction(WebDriver driver, WebElement element) {
			fnWaitForElement(element, 60);
			Actions action = new Actions(driver);
			action.moveToElement(element).click().build().perform();
	}
	
	
	public void fnMouseOverByJS(WebDriver driver, WebElement element) {
		try {
			
			
//		String strJavaScript = "var element = arguments[0];"
//				+ "var mouseEventObj = document.createEvent('MouseEvents');"
//				+ "mouseEventObj.initEvent( 'mouseover', true, true );" + "element.dispatchEvent(mouseEventObj);";
		Thread.sleep(5000);	
			
		String strJavaScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(strJavaScript, element);
		
		}catch(Exception e) {
			
		}
	}
	
	public void closeBrowser(WebDriver driver) {
		driver.close();
		driver.quit();
	}
	
	public void pressEnter(WebDriver driver) {
		try {
			Thread.sleep(5000);
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.ENTER).perform();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public void takeScreenShot(WebDriver driver,String screenShotName, String testName) {
		try {
			File file = new File("Screenshots" + File.separator + "Results");
			if (!file.exists()) {
//				Log.info("File created " + file);
				file.mkdir();
			}

			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File("Screenshots" + File.separator + "Results"+ File.separator + testName, screenShotName);
			FileUtils.copyFile(screenshotFile, targetFile);
//			return screenShotName;
		} catch (Exception e) {
			Log.info("An exception occured while taking screenshot " + e.getCause());
//			return null;
		}
	}

	
	
	
}
