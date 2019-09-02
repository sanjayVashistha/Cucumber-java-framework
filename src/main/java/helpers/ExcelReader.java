package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import com.gargoylesoftware.htmlunit.javascript.host.file.File;




import jxl.*;
import jxl.read.biff.BiffException;
//import jxl.read.biff.File;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
//import jxl.write.biff.RowsExceededException;

public class ExcelReader {
	
	Workbook wbWorkbook;
	Sheet shSheet;
	Sheet testSuit;
	Sheet testCases;
	Sheet locators;
	Sheet config;
	FileInputStream fs;
	public void openFile(String filePath)
	{
		FileInputStream newFile = null;
		try {
			newFile = new FileInputStream(filePath);
			fs = newFile;
			WorkbookSettings ws = new WorkbookSettings();
			ws.setSuppressWarnings(true);
			wbWorkbook = Workbook.getWorkbook(fs, ws);
			testSuit = wbWorkbook.getSheet("Test Suit");
			System.out.println("***workbook"+wbWorkbook.getSheet(1));
			testCases = wbWorkbook.getSheet("Test Cases");
			locators = wbWorkbook.getSheet("Locators");
			config = wbWorkbook.getSheet("Config");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(newFile!=null)
				try {
					newFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void openSheet(String filePath) {
		
		try {
			fs = new FileInputStream(filePath);
			wbWorkbook = Workbook.getWorkbook(fs);
			shSheet = wbWorkbook.getSheet(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void updateStatus(int col, int row, String result)
	{
		WritableWorkbook copy = null;
		try {
			copy = Workbook.createWorkbook(new File("output.xls"), wbWorkbook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WritableSheet sheet2 = copy.getSheet(0); 
		Label label = new Label(col,row,result); 
		try {
			sheet2.addCell(label);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copy.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copy.close();
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeFile()
	{
		if(fs!=null)
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public String getValueFromCellTestCases(int iColNumber, int iRowNumber) {
		return testCases.getCell(iColNumber, iRowNumber).getContents();
	}
	
	public String getValueFromCellLocators(int iColNumber, int iRowNumber) {
		return locators.getCell(iColNumber, iRowNumber).getContents();
	}
	
	public String getValueFromCellTestSuit(int iColNumber, int iRowNumber) {
		return testSuit.getCell(iColNumber, iRowNumber).getContents();
	}

	public int getRowCount(String sheetName){
		if(sheetName == "Test Suit")
			return testSuit.getRows();
		else if(sheetName == "Test Cases")
			return testCases.getRows();
		else if(sheetName == "Locators")
			return locators.getRows();
		else
			return 0;
	}
	public int getRowNumberTest(String testId)
	{
		int rowNum = 0;
		for(int row = 1;row<getRowCount("Test Cases");row++)
		{
			if(getValueFromCellTestCases(0,row)==testId)
			{
				rowNum = row;
				break;
			}
			
		}
		return rowNum;
	}
	public int getRowCountForTest(String testId)
	{
		int numbOfRows = 0;
		for(int row = 1;row<getRowCount("Test Cases");row++)
		{
			if(getValueFromCellTestCases(0,row)==testId)
				numbOfRows++;
		}
		return numbOfRows;
	}
	public int getRowCount() {
		return shSheet.getRows();
	}

	public int getColumnCount() {
		return testCases.getColumns();
	}
	
	

}
