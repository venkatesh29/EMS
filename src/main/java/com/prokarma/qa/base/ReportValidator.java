package com.prokarma.qa.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.prokarma.qa.web.helpers.ExcelFileUtil;
import com.prokarma.qa.web.helpers.Log;

public class ReportValidator extends CommonFunctions{
	
	public File getLatestFilefromDir(String dirPath) throws Exception{
		
		Thread.sleep(60000);
		
	    File dir = new File(dirPath);
	    
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }
	
	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	public void validateSpendConsumptionReport() throws Throwable {
		
		Thread.sleep(10000);
		String testoutput=System.getProperty("user.dir")+File.separator+"TestOutput";
		File modFile=getLatestFilefromDir(testoutput);
		String modFileName=modFile.getName();  
		String reqFileName="";
		boolean reportValidator=true;
		
		if(modFileName.contains("Spend & Consumption")){
      	  reqFileName=modFileName;
        }
		
//		  File dir = new File("TestOutput");
//		  String reqFileName="";
//	      String[] children = dir.list();
//	      
//	      if (children == null) {
//	         System.out.println("does not exist or  is not a directory");
//	      } else {
//	         for (int i = 0; i < children.length; i++) {
//	            String filename = children[i];
//		          if(filename.contains("Spend & Consumption")){
//		        	  reqFileName=filename;
//		          }
//	         }
//	      }
	      
	      Thread.sleep(10000);
	      
		  String outPutFilePath=System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+"OutPutSheet.xls";	
		  ExcelFileUtil outputExl=new ExcelFileUtil(outPutFilePath);
		 
			  ArrayList<String> column_list=new ArrayList<String>();
				  column_list.add("Site #>Site #");
				  column_list.add("Utility Type>Commodity");
				  column_list.add("Billing Month>Month");
				  column_list.add("Billing Year>Year");
				  column_list.add("Spend>Spend");
				  column_list.add("Consumption>Consumption");
//				  column_list.add("Demand>Demand");
				 
			  HashMap<String,String> month_map  = new HashMap<>(); 
				  month_map.put("Jan","1"); 
				  month_map.put("Feb","2"); 
				  month_map.put("Mar","3"); 
				  month_map.put("Apr","4");
				  month_map.put("May","5");		
				  month_map.put("Jun","6");
				  month_map.put("Jul","7");
				  month_map.put("Aug","8"); 
				  month_map.put("Sep","9"); 
				  month_map.put("Oct","10"); 
				  month_map.put("Nov","11");
				  month_map.put("Dec","12");		
	 
				  String downloadedReportFilePath=System.getProperty("user.dir")+File.separator+"TestOutput"+File.separator+reqFileName;	
				  ExcelFileUtil reportExl=new ExcelFileUtil(downloadedReportFilePath);
				   
				  String site_header_str=column_list.get(0);
				  
				  String[] headerlist=site_header_str.split(">");
				  
				  String output_header=headerlist[0];
				  int req_output_colindex=outputExl.getColIndex("Citi", output_header);
				  
				  String report_header=headerlist[1];
				  int req_report_colindex=reportExl.getColIndex("Data", report_header);	 
				 
				  int output_rowCount= outputExl.rowCount("Citi");
				  int report_rowCount= reportExl.rowCount("Data");
				  
				  for(int i=1;i<=output_rowCount;i++) {
					  
					  ArrayList<Integer> matching_row_list=new ArrayList<Integer>();
			
					  String output_primary_key= outputExl.getData("Citi", i, req_output_colindex);
					  
					  for(int j=1;j<=report_rowCount;j++) {
						  String report_primary_key= reportExl.getData("Data", j, req_report_colindex); 
						  if(output_primary_key.equalsIgnoreCase(report_primary_key)) {
							  matching_row_list.add(j);
						  }  
					  }
					  
					 for(int matching_row:matching_row_list) {
//						 System.out.println(output_primary_key+" "+matching_row);
						 
						 boolean status=true;
						 
						 for(String header:column_list) {
							 
							 boolean cellstatus=true;
							 
							 String[] headers=header.split(">");
							 
							 String output_header_str=headers[0];
							 
//							 System.out.println(output_header_str);
							 int output_header_str_colindex=outputExl.getColIndex("Citi", output_header_str);
							 String output_header_data=outputExl.getData("Citi", i, output_header_str_colindex);
							 output_header_data= output_header_data.replace(",", "");
							 
							 String report_header_data="";
							 if(output_header_str.equalsIgnoreCase("Billing Month")) {
								 String report_header_str=headers[1];
								 int report_header_str_colindex=reportExl.getColIndex("Data", report_header_str);
								 report_header_data=reportExl.getData("Data", matching_row, report_header_str_colindex);
								 report_header_data=month_map.get(report_header_data);		 
							 }else  if(output_header_str.equalsIgnoreCase("Spend")) {
								 String report_header_str=headers[1];
								 int report_header_str_colindex=reportExl.getColIndex("Data", report_header_str);
								 report_header_data=reportExl.getData("Data", matching_row, report_header_str_colindex);
								 report_header_data=report_header_data.replace(",", "");
								 
//								 float report_header_data_float= Float.parseFloat(report_header_data);
//								 report_header_data_float=report_header_data_float+0.01f;						 
//								 report_header_data=String.valueOf(report_header_data_float);  
								 
								 report_header_data= "$"+report_header_data;
								 int dot_index=report_header_data.indexOf('.');
								 report_header_data=report_header_data.substring(0, dot_index+3);
								 
							 }else {
								 String report_header_str=headers[1];
								 int report_header_str_colindex=reportExl.getColIndex("Data", report_header_str);
								 report_header_data=reportExl.getData("Data", matching_row, report_header_str_colindex);
							 }
							
							 if(output_header_str.equalsIgnoreCase("Spend")) {
								 
//								 System.out.println(output_header_data+" <> "+report_header_data);
								 
								 output_header_data=output_header_data.replace("$", "");
								 report_header_data=report_header_data.replace("$", "");
								 
								 float output_header_data_float = Float.parseFloat(output_header_data);
								 float report_header_data_float = Float.parseFloat(report_header_data);
								 
								 float diff1=output_header_data_float-report_header_data_float;
								 float diff2=report_header_data_float-output_header_data_float;
								 
//								 System.out.println("diff1 : "+diff1);
//								 System.out.println("diff2 : "+diff2);
								 
								 String diff1_str1=String.valueOf(diff1); 
								 String diff2_str2=String.valueOf(diff2); 
								 
								 if(diff1==0.0 || diff2==0.0 || diff1_str1.contains("0.01") || diff2_str2.contains("0.01") || diff1_str1.contains("0.00") || diff2_str2.contains("0.00")) {
									 cellstatus=true;
								 }else {
//									 System.out.println(output_header_data+" "+report_header_data);
									 cellstatus=false;
									 status=false;
									 break;
								 } 
							 }
							 else if(output_header_str.equalsIgnoreCase("Consumption")) {
								  
//								 System.out.println(output_header_data+" <> "+report_header_data);								 
//								 output_header_data=output_header_data.replace("$", "");
//								 report_header_data=report_header_data.replace("$", "");
								 
								 float output_header_data_float = Float.parseFloat(output_header_data);
								 float report_header_data_float = Float.parseFloat(report_header_data);
								 
								 float diff1=output_header_data_float-report_header_data_float;
								 float diff2=report_header_data_float-output_header_data_float;
								 
//								 System.out.println("diff1 : "+diff1);
//								 System.out.println("diff2 : "+diff2);
								 
								 String diff1_str1=String.valueOf(diff1); 
								 String diff2_str2=String.valueOf(diff2); 
								 
//								 System.out.println(output_header_data+" <> "+report_header_data);
//								 System.out.println(diff1+" <> "+diff1);
//								 System.out.println(diff2_str2+" <> "+diff2_str2);
								 
								 if(diff1==0.0 || diff2==0.0 || diff1_str1.contains("0.01") || diff2_str2.contains("0.01") || diff1_str1.contains("0.00") || diff2_str2.contains("0.00") || diff1_str1.contains("-0.00") || diff2_str2.contains("-0.00")) {
									 cellstatus=true;
								 }else {
//									 System.out.println(output_header_data+" "+report_header_data);
									 cellstatus=false;
									 status=false;
									 break;
								 } 
							 }
							 
							 if(!output_header_str.equalsIgnoreCase("Spend") && !output_header_str.equalsIgnoreCase("Consumption")) {						 
								 if(output_header_data.equalsIgnoreCase(report_header_data)) {
									 cellstatus=true;
								 }else {
		//							 System.out.println(output_header_data+" "+report_header_data);
									 cellstatus=false;
									 status=false;
									 break;
								 } 						 
							 } 
						 } 
						  
						 if(status==true) {
							 int statusColIndex=outputExl.getColIndex("Citi", "Status");	 
							 outputExl.setData("Citi", i, statusColIndex, "Found-Pass");					 
//							 System.out.println("Citi "+i+" Found-Pass");
							 break;
						 }else {
							 reportValidator=false;
							 int statusColIndex=outputExl.getColIndex("Citi", "Status");
							 outputExl.setData("Citi", i, statusColIndex, "Not Found-Fail");
						 } 
					 }    	  
				  }
				  	if(reportValidator==true) {
				  		Log.info("report validation successfull");
						write("PASS", "report validation successfull");
				  	}else {
				  		Log.info("report validation failed");
						write("FAIL", "report validation failed");
				  	}
   }

}
