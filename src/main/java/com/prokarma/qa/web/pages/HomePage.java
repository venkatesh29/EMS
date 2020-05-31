package com.prokarma.qa.web.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.prokarma.qa.base.CommonFunctions;
import com.prokarma.qa.web.helpers.ExcelFileUtil;
import com.prokarma.qa.web.helpers.Log;

public class HomePage  extends CommonFunctions{
	
	private WebDriver driver;

	public HomePage(WebDriver driver ) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	@FindBy(linkText="WORKSPACE")
	public WebElement workspace_lnk;
	
	@FindBy(xpath="//a[text()='Tools ']")
	public WebElement Toos_btn;
	
	
	//******************* SIDE BAR BUTTONS **********************//
	
	@FindBy(xpath="//h3[text()='Analytics']//ancestor::li")
	public WebElement analytics;
	
	@FindBy(xpath="(//h3[text()='Analytics']//parent::div/div/ul/li)[1]")
	public WebElement analytics_Reporting;
	
	@FindBy(xpath="//h3[text()='Analytics']//parent::div/div[2]/ul/li[1]")
	public WebElement analytics_Reporting_Spendandconsumption;
	
	@FindBy(xpath="//h3[text()='Analytics']//parent::div/div[2]/ul/li[8]")
	public WebElement analytics_Reporting_SpendandconsumptionYOY;
	
	
	//******************* Spend & Consumption - YOY BUTTONS **********************//
	
	@FindBy(id="ddlClient")
	public WebElement clientYOY;
	
	@FindBy(xpath="(//div[contains(@id,'jqxScrollThumbverticalScrollBarinnerListBoxjqxWidget')])[13]")
	public WebElement clientWindowYOY;
	
	@FindBy(xpath="//span[text()='All']//parent::div/child::div/child::div/child::div")
	public List<WebElement>  clientWindowYOY_all; 
	
	@FindBy(id="ddlCommodity")
	public WebElement utilityYOY;
	
	@FindBy(xpath="(//div[contains(@id,'jqxScrollThumbverticalScrollBarinnerListBoxjqxWidget')])[14]")
	public WebElement utilityWindowYOY;
	
	@FindBy(xpath="//span[text()='All']//parent::div/child::div/child::div/child::div")
	public List<WebElement>  utilityWindowYOY_all; 
	
	@FindBy(xpath="//label[contains(text(),'From')]//following::angulardropdownlist[1]")
	public WebElement commodityFromMonthYOY;
	
	@FindBy(xpath="//label[contains(text(),'From')]//following::angulardropdownlist[2]")
	public WebElement commodityFromYearYOY;
	
	@FindBy(xpath="//label[contains(text(),'To')]//following::angulardropdownlist[1]")
	public WebElement commodityToMonthYOY;
	
	@FindBy(xpath="//label[contains(text(),'To')]//following::angulardropdownlist[2]")
	public WebElement commodityToYearYOY;
	
	@FindBy(id="btnMeterDetails")
	public WebElement spendConsumptionsearchYOY;  
	
	@FindBy(xpath="//button[contains(text(),'SEARCH')]")
	public WebElement spendConsumptionsearch;  
	
	@FindBy(xpath="//div[contains(@id,'row0')]/div/div")
	public List<WebElement> spendConsumptionYOYReportRow0;
	
	@FindBy(xpath="//div[@role='columnheader']/div/div[1]/span")
	public List<WebElement> spendConsumptionYOYheaders;
	
	@FindBy(xpath="(//*[contains(@id,'jqxScrollThumbverticalScrollBarjqxWidget')])[2]")
	public WebElement spendConsumptionYOYReportWindow;
	
	@FindBy(xpath="//li[@class='dropdown nav-client']")
	public WebElement allClientWindow;
	
	@FindBy(xpath="//a[text()='ALL']")
	public WebElement allClientWindowAllOption;
	
	@FindBy(xpath="//label[contains(text(),'Commodity')]//following::div[1]")
	public WebElement  spendConsumptionCommodity;
	
	@FindBy(xpath="//label[contains(text(),'From')]//following::angulardropdownlist[1]")
	public WebElement  spendConsumptionCommodityFromMonth;
	
	@FindBy(xpath="//label[contains(text(),'To')]//following::angulardropdownlist[1]")
	public WebElement  spendConsumptionCommodityToMonth;
	
	@FindBy(xpath="//label[contains(text(),'From')]//following::angulardropdownlist[2]")
	public WebElement  spendConsumptionCommodityFromYear;
	
	@FindBy(xpath="//label[contains(text(),'To')]//following::angulardropdownlist[2]")
	public WebElement  spendConsumptionCommodityToYear;
	
	@FindBy(xpath="//i[@ngbtooltip='Download']")
	public WebElement  spendConsumptionDownloadArrow;
	
	@FindBy(xpath="(//angularradiobutton)[18]")
	public WebElement  spendConsumptionExcelDownloadRadio; 
	
	@FindBy(xpath="//button[text()='DOWNLOAD']")
	public WebElement  spendConsumptionDownloadBtn;
	
	@FindBy(xpath="//*[text()='No data to display']")
	public WebElement  textNotDisplay;
	
	public void fn_hp_click_workspace_lnk() {
		fnWaitForElement(workspace_lnk,60);
		fnClickElementByJS("workspace_lnk",workspace_lnk);
	}
	
	public void checkForLogin() {
		try {
			fnWaitForElement(workspace_lnk,60);
			Assert.assertTrue(workspace_lnk.isDisplayed());		
			Log.info("Login Successfull");
			write("PASS", "Login Successfull");
		}catch(Exception e) {
			Log.info("Login Failed");
			write("FAIL", "Login Successfull");
		}
	}
	
	public void fnSelectWorkspace() throws Exception {
		try {
			fnWaitForElement(workspace_lnk,60);
			fnClickElementByJS("workspace_lnk",workspace_lnk);
			fnVisibleOnPage(Toos_btn,"Toos_btn");
			fnClickElementByJS("workspace_lnk",workspace_lnk);
			Log.info("workspace selected");
		}catch(Exception e) {
			Log.info("click on workspace failed");
		}
	}
	public void fn_hp_select_bubble(WebElement mainIcon,WebElement parentBubble,WebElement childBubble,String selectionMessage) throws Exception {
		try {
			mainIcon.click();
			Thread.sleep(7000);
			parentBubble.click();
			Thread.sleep(7000);
			childBubble.click();
			Log.info("Bubble : "+selectionMessage+" Selected");
			write("PASS", "Bubble : "+selectionMessage+" Selected");
		}catch(Exception e) {
			Log.info("Bubble : "+selectionMessage+" Selection error");
			write("FAIL", "Bubble : "+selectionMessage+" Selection error");
		}
			
	}
	
	 public void selectClientFromClientsWindow(String clientName) throws Throwable {
		 try {
			  fnWaitForElement(allClientWindow,60);	
			  allClientWindow.click();
			  fnWaitForElement(allClientWindowAllOption,60);	
			  allClientWindowAllOption.click();	
			  driver.findElement(By.xpath("//img[@alt='"+clientName+"']//parent::a")).click();
			  Log.info(clientName+" Selected");
			  write("PASS", clientName+" Selected");
		}catch(Exception e) {
			 Log.info(clientName+" Selection error");
			 write("FAIL", clientName+" Selection error");
		}
	 }
	
	 public void selectClient() throws Throwable {
		 		String exp_option =getTestInput("Citi", geTtestCaseName(), "ClientName");
		 try {
				fnWaitForElement(clientYOY,60);
			    clientYOY.click();
			    Thread.sleep(5000);
			    boolean allClickStatus=false;
		    
			    for(WebElement all:clientWindowYOY_all) {
			    	if(allClickStatus==false) {
				    	try {
				    		if(!all.isSelected()) {
				    			all.click();
				    		    allClickStatus=true;
				    		    Thread.sleep(5000);
				    		}
				    	}catch(Exception e) {
				    		
				    	}
			    	}	
			    }

			     boolean scrollStatus=true;  
				  try {
					  scrollStatus=clientWindowYOY.isDisplayed();
				  }catch(Exception e) {
					  scrollStatus=false;
				  }
				  
				  Point location = clientWindowYOY.getLocation();
				  int x = location.getX();
				  int y = location.getY();

			    if(scrollStatus==true) {
					int xOffset = clientWindowYOY.getLocation().getX();		
					String[] req_Options=exp_option.split("/");
					
								for(String option:req_Options) {
									Actions actions=new Actions(driver);
									
									boolean clickStatus=false;
									
									while(clickStatus==false) {
										try {
											driver.findElement(By.xpath("//span[text()='"+option+"']//parent::div/child::div/child::div/child::div")).click();
											break;
										}catch(Exception e) {
											
											Actions dragger = new Actions(driver);
											dragger.moveToElement(clientWindowYOY).clickAndHold().moveByOffset(0,20).build().perform();
											
										}
									}	
								}
			    }else {
			    			String[] req_Options=exp_option.split("/");
					    	for(String option:req_Options) {
								driver.findElement(By.xpath("//span[text()='"+option+"']//parent::div/child::div/child::div/child::div")).click();
					    	}		
			    }
				    	Log.info(exp_option+" selected from client dropdown");
				    	write("PASS", exp_option+" selected from client dropdown");    	
			}catch(Exception e) {
						Log.info(exp_option+" selected error in client dropdown");
				    	write("PASS", exp_option+" selected error in client dropdown");
			}
	}
	 
	public void selectUtility() throws Throwable {
		String exp_option =getTestInput("Citi", geTtestCaseName(), "Commodity");
		try {	
			fnWaitForElement(utilityYOY,60);	
			utilityYOY.click();
			Thread.sleep(5000);
			boolean allClickStatus=false;
		    
			for(WebElement all:clientWindowYOY_all) {
		    	if(allClickStatus==false) {
			    	try {
			    		if(!all.isSelected()) {
			    			all.click();
			    		    allClickStatus=true;
			    		    Thread.sleep(5000);
			    		    break;
			    		}
			    	}catch(Exception e) {
			    		
			    	}
		    	}	
		    }
  
		  	  boolean scrollStatus=true;  
			  try {
				  scrollStatus=utilityWindowYOY.isDisplayed();
			  }catch(Exception e) {
				  scrollStatus=false;
			  }
	  
		    if(scrollStatus==true) {
				int xOffset = utilityWindowYOY.getLocation().getX();
				
				String[] req_Options=exp_option.split("/");
				
							for(String option:req_Options) {
								Actions actions=new Actions(driver);
								
								boolean clickStatus=false;
								
								while(clickStatus==false) {
									try {
										driver.findElement(By.xpath("//span[text()='"+option+"']//parent::div/child::div/child::div/child::div")).click();
										break;
									}catch(Exception e) {
//										actions.dragAndDropBy(utilityWindowYOY, 9, 10).perform();
										Actions dragger = new Actions(driver);
										dragger.moveToElement(utilityWindowYOY).clickAndHold().moveByOffset(0,20).build().perform();
									}
								}	
							}
		    }else {
		    			String[] req_Options=exp_option.split("/");
				    	for(String option:req_Options) {
							driver.findElement(By.xpath("//span[text()='"+option+"']//parent::div/child::div/child::div/child::div")).click();
				    	}		
		    }
		    
		    		Log.info(exp_option+" selected from utility dropdown");
		    		write("PASS", exp_option+" selected from utility dropdown");    	
			}catch(Exception e) {
						Log.info(exp_option+" selected error in utility dropdown");
				    	write("PASS", exp_option+" selected error in utility dropdown");
			}
	}
	
	public void selectYOYFromMonth() throws Throwable {
		String commodity_from_month =getTestInput("Citi", geTtestCaseName(), "Commodity_From_Month");
		commodityFromMonthYOY.click();
		driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_from_month+"')])[1]")).click();
	}
	
	public void selectYOYFromYear() throws Exception, Throwable {
		String commodity_from_year=getTestInput("Citi", geTtestCaseName(), "Commodity_From_Year");
		commodityFromYearYOY.click();
		driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_from_year+"')])[1]")).click();
	}
	
	public void selectYOYToMonth() throws Exception, Throwable {
		String commodity_to_month =getTestInput("Citi", geTtestCaseName(), "Commodity_To_Month");
		commodityToMonthYOY.click(); 
		driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_to_month+"')])[2]")).click();
	}
	
	public void selectYOYToYear() throws Exception, Throwable {
		String commodity_to_year=getTestInput("Citi", geTtestCaseName(), "Commodity_To_Year");
		commodityToYearYOY.click();
		driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_to_year+"')])[2]")).click();
		
	}
	
//	public void get_spendConsumptionYOY_records(String testCaseName) throws Throwable {
	public boolean get_spendConsumptionYOY_records() throws Throwable {
		
				boolean table_status = false;
		
				fn_hp_select_bubble(analytics,analytics_Reporting,analytics_Reporting_SpendandconsumptionYOY,"Analytics>Reporting>Spend and ConsumptionYOY");	
				selectClient();
				selectUtility();
				selectYOYFromMonth();
				selectYOYFromYear();
				selectYOYToMonth();
				selectYOYToYear();
				
				Thread.sleep(10000);
				spendConsumptionsearchYOY.click();
				
				implicitWait(driver,20);
		
		 ExcelFileUtil exlTestoutput=new ExcelFileUtil(getTestOutputFile());
		  if(spendConsumptionYOYReportRow0.size()!=0) {
					  ArrayList<String> outputList = new ArrayList<String>();
					  ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
					   
					  mainList=getheaders(driver,mainList);
					  mainList=getfirsthalfrecords(driver,mainList);
					  mainList=getsecondhalfrecords(driver, mainList);
						
					  int i=0;
					  for(ArrayList<String> list:mainList) {
						 int j=0;
						 for(String listvalue:list) {
								exlTestoutput.setData("Citi",i, j, listvalue);
								j++;
						 }
						i++;	
					   }
					  	
					  String clientName = getTestInput("Citi",geTtestCaseName(), "ClientName");
					  selectClientFromClientsWindow(clientName);		
						
						exlTestoutput=new ExcelFileUtil(getTestOutputFile());						
						int totalrows=exlTestoutput.rowCount("Citi");
						int statuscolindex=exlTestoutput.getColIndex("Citi", "Status");
						
						for(int j=1;j<=totalrows;j++) {
							exlTestoutput.setData("Citi",j, statuscolindex, "");
						}
							
			}else { 
					  try {
					  	 table_status=textNotDisplay.isDisplayed();
						  if(table_status==true) {
							 String table_empty_status=  textNotDisplay.getText();
							 System.out.println(table_empty_status);
						  }
					  }catch(Exception e) {
						  
					  }  			  	
		  }	
		  
		  return table_status;
	  }

	
	 public ArrayList<ArrayList<String>> getfirsthalfrecords(WebDriver driver,ArrayList<ArrayList<String>> mainList ) {
			
			int i =0;		
			while(i<=4) {	
				ArrayList<String> templist=new ArrayList<String>();
				String xpath="//div[contains(@id,'row"+i+"')]/div/div";
				List<WebElement> cells=driver.findElements(By.xpath(xpath));
				for(int k=0;k<cells.size();k++) {
					String cellValue=cells.get(k).getText();
					templist.add(cellValue);
//					System.out.println(cellValue);
				}
				mainList.add(templist);
				i++;		
			}	
			return mainList;
	  }
		
		public ArrayList<ArrayList<String>> getsecondhalfrecords(WebDriver driver,ArrayList<ArrayList<String>> mainList) {
					
//			int xOffset = spendConsumptionYOYReportWindow.getLocation().getX();
			
			Actions actions=new Actions(driver);
			actions.dragAndDropBy(spendConsumptionYOYReportWindow, 9, 153).perform();
			
			int i =0;	
			while(i<5) {		
				ArrayList<String> templist=new ArrayList<String>();
				String xpath="//div[contains(@id,'row"+i+"')]/div/div";
				List<WebElement> cells=driver.findElements(By.xpath(xpath));

				for(int k=0;k<cells.size();k++) {
					String cellValue=cells.get(k).getText();
					templist.add(cellValue);
				}
				mainList.add(templist);
			    i++;
			}
			return mainList;
		 }
		
		public ArrayList<ArrayList<String>> getheaders(WebDriver driver,ArrayList<ArrayList<String>> mainList) {
			;
			ArrayList<String> templist=new ArrayList<String>();
			
			for(WebElement header:spendConsumptionYOYheaders) {
				String headertext=header.getText();			
				templist.add(headertext);
			}
			templist.add("Status");
			mainList.add(templist);
			return mainList;
		}
	
		 public boolean downloadspendConsumptionReport() throws Throwable {
			 	
			  boolean table_status=false;
			 
	   		  deleteFile("Spend & Consumption");
			  fnWaitForElement(analytics,60);
			  Thread.sleep(8000);
//			  PageFactory.initElements(this.driver, this);
			  fn_hp_select_bubble(analytics,analytics_Reporting,analytics_Reporting_Spendandconsumption,"Analytics>Reporting>Spend and Consumption");	
			  Thread.sleep(8000);
			  spendConsumptionCommodity.click();
			  String commodity =getTestInput("Citi", geTtestCaseName(), "Commodity"); 
			  Thread.sleep(8000);
			  
			  Actions actions=new Actions(driver);
			  List<WebElement> r=driver.findElements(By.xpath("//label[contains(text(),'Commodity')]//parent::div//following::div[contains(@id,'jqxScrollThumbverticalScrollBarinnerListBoxjqxWidget')]"));
			 
			  WebElement requiredelement=getRequiredElement(r);
			  	boolean clickStatus=false;
				while(clickStatus==false) {
					try {
						  	 List<WebElement> options=driver.findElements(By.xpath("//label[contains(text(),'Commodity')]//following::span[text()='"+commodity+"']"));
							 WebElement requiredwindowelement=getRequiredElement(options);
							 actions.moveToElement(requiredwindowelement).click().build().perform();
							 break;
					}catch(Exception e) {
						actions.dragAndDropBy(requiredelement, 9, 20).perform();	
						Thread.sleep(5000);
					}
				}
				
			 Thread.sleep(5000);
			  
			  spendConsumptionCommodityFromMonth.click();
			  String commodity_from_month =getTestInput("Citi", geTtestCaseName(), "Commodity_From_Month");
//			  driver.findElement(By.xpath("//span[contains(text(),'"+commodity_from_month+"')]")).click();
			  r=driver.findElements(By.xpath("//span[contains(text(),'"+commodity_from_month+"')]"));
			  requiredelement=getRequiredElement(r);
			  requiredelement.click();
			  
			  
			  spendConsumptionCommodityFromYear.click();
			  String commodity_from_year=getTestInput("Citi", geTtestCaseName(), "Commodity_From_Year");
//			  driver.findElement(By.xpath("//span[contains(text(),'"+commodity_from_year+"')]//parent::div")).click();
			  r=driver.findElements(By.xpath("//span[contains(text(),'"+commodity_from_year+"')]//parent::div"));
			  requiredelement=getRequiredElement(r);
			  requiredelement.click();
			  
			  spendConsumptionCommodityToMonth.click();
			  String commodity_to_month =getTestInput("Citi", geTtestCaseName(), "Commodity_To_Month");
//			  driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_to_month+"')])[2]")).click();
			  r=driver.findElements(By.xpath("//span[contains(text(),'"+commodity_to_month+"')]"));
			  requiredelement=getRequiredElement(r);
			  requiredelement.click();
			  
			  spendConsumptionCommodityToYear.click();
			  String commodity_to_year=getTestInput("Citi", geTtestCaseName(), "Commodity_To_Year");
//			  driver.findElement(By.xpath("(//span[contains(text(),'"+commodity_to_year+"')])[2]//parent::div")).click();
			  r=driver.findElements(By.xpath("//span[contains(text(),'"+commodity_to_year+"')]//parent::div"));
			  requiredelement=getRequiredElement(r);
			  requiredelement.click();
			  
			  spendConsumptionsearch.click();
			  
			  Thread.sleep(20000);
			  
			  try {
//				  fnWaitForElement(textNotDisplay,60);
			  	  table_status=textNotDisplay.isDisplayed();
				  if(table_status==true) {
					 String table_empty_status=  textNotDisplay.getText();
					 System.out.println(table_empty_status);
				  }
			  }catch(Exception e) {
				  table_status=false;
				  fnWaitForElement(spendConsumptionDownloadArrow,60);
				  fnClickElementByJS("spendConsumptionDownloadArrow", spendConsumptionDownloadArrow);
				  fnWaitForElement(spendConsumptionExcelDownloadRadio,60);
				  spendConsumptionExcelDownloadRadio.click();
				  spendConsumptionDownloadBtn.click();
			  } 
			
			  return table_status;
		 }
		 
		 public void deleteFile(String fileToDelete) throws InterruptedException {
				
				File dir = new File("TestOutput");
				  String reqFileName="";
			      String[] children = dir.list();
			      
			      if (children == null) {
			         System.out.println("does not exist or  is not a directory");
			      } else {
			         for (int i = 0; i < children.length; i++) {
			              String filename = children[i];
				          if(filename.contains(fileToDelete)){
				        	  reqFileName=filename;
				        	  File f=new File(System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+reqFileName);
				        	  f.delete();
				        	  break;
				          }
			         }
			      }
			    
		}
}
