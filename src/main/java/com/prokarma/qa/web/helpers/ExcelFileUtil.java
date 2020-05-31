package com.prokarma.qa.web.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	
		Workbook wb;
	
	//it will load all the Excel Sheet
		public ExcelFileUtil(String FilePath) throws Throwable
		{
//			FileInputStream fis=new FileInputStream("C:\\Users\\Venkatesh\\eclipse-workspace\\EMS1\\TestData\\InputSheet.xlsx");
//			FileInputStream fis=new FileInputStream("C:\\Users\\Venkatesh\\eclipse-workspace\\EMS1\\TestOutput\\OutPutSheet.xlsx");
			FileInputStream fis=new FileInputStream(FilePath);
			
			if(FilePath.contains("Spend & Consumption")) {
				wb=new XSSFWorkbook(fis);
			}else {
				wb=new HSSFWorkbook(fis);
			}
			
		}
		
		public int rowCount(String sheetname)
		{
			return wb.getSheet(sheetname).getLastRowNum();
		}
		
		public int colCount(String sheetname,int row)
		{
			return wb.getSheet(sheetname).getRow(row).getLastCellNum();
		}

		public String getData(String sheetname,int row ,int column)
		{
			String data="";
			
			if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
//				int celldata=(int)(wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue());
				data=NumberToTextConverter.toText(wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue());
//				int celldata=(int)(wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue());
//				data=String.valueOf(celldata);
				
			}else
			{
				data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
			}
			return data;
		}

		//Store data into excel sheet Pass Or Fail and Not Executed
		public void setData(String sheetname,int row, int column,String Status) throws Throwable
		{	
		//	System.out.println(sheetname+" "+row+" "+column+" "+Status);
			
			FileInputStream fis =new FileInputStream(System.getProperty("user.dir") + File.separator + "TestOutput"+ File.separator +"OutPutSheet.xls");
			Workbook workbook = new HSSFWorkbook(fis);
		    Sheet sheet = workbook.getSheet(sheetname);
		    
		    Row rownum;
		    
		    try {
			        rownum = sheet.getRow(row);
			        Cell cell = rownum.createCell(column);
					cell.setCellValue(Status);
		    }catch(Exception e) {
		    		rownum = sheet.createRow(row);
		    		Cell cell = rownum.createCell(column);
			        cell.setCellValue(Status);
		    }
			
		    FileOutputStream fos =new FileOutputStream(System.getProperty("user.dir") + File.separator + "TestOutput"+ File.separator +"OutPutSheet.xls");
			workbook.write(fos);
			fos.close();
			
		}
		
		public int getColIndex(String sheetname,String colName)
		{	
			int colCount=wb.getSheet(sheetname).getRow(0).getLastCellNum();
			int req_col = -1;
			for(int i=0;i<colCount;i++) {
				
				String colHeader=wb.getSheet(sheetname).getRow(0).getCell(i).getStringCellValue();
				if(colHeader.trim().equalsIgnoreCase(colName.trim())) {
					req_col=i;
					break;
				}
			}
			return req_col;
		}
		
		public String getTestInput(String sheetname,String testCaseName,String colName) throws Throwable {
			
//			ExcelFileUtil exlTestInput=new ExcelFileUtil(sheetname);
			int rowCount=rowCount(sheetname);
			
			int reqColIndex=getColIndex(sheetname,colName);
			String testData=null;
			
			for(int i=0;i<=rowCount;i++) {
				if(getData(sheetname, i, 0).equalsIgnoreCase(testCaseName)){
					testData=getData(sheetname, i, reqColIndex);
					break;
				}
			}
			
			return testData;	
		}


}
