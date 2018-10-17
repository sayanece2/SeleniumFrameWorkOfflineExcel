package demo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HelperScript {
	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;

//This method is to set the File path and to open the Excel file
//Pass Excel Path and SheetName as Arguments to this method
   // @SuppressWarnings("null")
	public static ArrayList<String> GetScenarioList(String Path,String SheetName) throws Exception {
		
        FileInputStream ExcelFile = new FileInputStream(Path);
        ArrayList<String> lstTables = new ArrayList<>();
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheet(SheetName);
        String CellGetContent;
        try{
        for (int i = 1; i < ExcelWSheet.getPhysicalNumberOfRows(); i++) {
        	Row row = ExcelWSheet.getRow(i);
        	CellGetContent=row.getCell(1).getStringCellValue();
        	if(CellGetContent.equals("Y"))
        	{
        		lstTables.add(row.getCell(0).getStringCellValue());
        	}
        }
        return lstTables;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getCause());
			lstTables.clear();
		}
		return lstTables;
        
        
       }


//This method is to read the test data from the Excel cell
//In this we are passing parameters/arguments as Row Num and Col Num
public static ArrayList<String> GetTestCaseList(String Path,String SheetName,String ScName) throws Exception{
	FileInputStream ExcelFile = new FileInputStream(Path);
    ArrayList<String> lstTables = new ArrayList<>();
    ExcelWBook = new XSSFWorkbook(ExcelFile);
    ExcelWSheet = ExcelWBook.getSheet(SheetName);
    String CellGetContentSC;
    String CellGetContentTCFlag;
    try{
        for (int i = 1; i < ExcelWSheet.getPhysicalNumberOfRows(); i++) {
        	Row row = ExcelWSheet.getRow(i);
        	CellGetContentSC=row.getCell(0).getStringCellValue();
        	CellGetContentTCFlag=row.getCell(2).getStringCellValue();
        	if(CellGetContentSC.equals(ScName) && CellGetContentTCFlag.equals("Y"))
        	{
        	lstTables.add(row.getCell(1).getStringCellValue());
        	}
        }
        return lstTables;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getCause());
			lstTables.clear();
		}
		return lstTables;
	}
public static ArrayList<String> GetTestCaseSteps(String Path,String SheetName,String TcName) throws Exception{
	FileInputStream ExcelFile = new FileInputStream(Path);
    ArrayList<String> lstTables = new ArrayList<>();
    ExcelWBook = new XSSFWorkbook(ExcelFile);
    ExcelWSheet = ExcelWBook.getSheet(SheetName);
    String CellGetContentTC;
    String Action;
    String Description;
    String LogicalObjectName;
    String TestData;
    try{
        for (int i = 1; i < ExcelWSheet.getPhysicalNumberOfRows(); i++) {
        	Row row = ExcelWSheet.getRow(i);
        	CellGetContentTC=row.getCell(0).getStringCellValue();
        	if(CellGetContentTC.equals(TcName))
        	{
        		Action=row.getCell(1).getStringCellValue()+"||";
        		Description=row.getCell(2).getStringCellValue()+"||";
        		LogicalObjectName=row.getCell(3).getStringCellValue()+"||";
        		TestData=row.getCell(4).getStringCellValue();
        	    lstTables.add(Action+Description+LogicalObjectName+TestData);
        	}
        }
        return lstTables;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getCause());
			lstTables.clear();
		}
		return lstTables;
	}

}
