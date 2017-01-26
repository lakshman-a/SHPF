package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * <h1>ReadExcel</h1>
 * 
 * <p>
 * <b>All Functions to read and write in a Excel</b> 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class ReadExcel {

	public static String filename;
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;

	public ReadExcel(String path){
		this.path=path;

		try{
			fis = new FileInputStream(path);
			//fileOut = new FileOutputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum();
			return number;

		}
	}

	public int getColumnCount(String sheetName){

		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
		}

		if(row==null)
			return -1;

		return row.getLastCellNum();
	}

	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);

			int col_Num=-1;
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return null;

			row = sheet.getRow(rowNum);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim();

			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC )
				return String.valueOf(cell.getNumericCellValue()).trim();

			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue()).trim();

		}
		catch(Exception e){
			e.printStackTrace();
			// return "row "+rowNum+" or column "+colName +" does not exist in xls";
			return "";
		}

	}

	public int getNumericCellData(String sheetName,String colName,int rowNum){
		int value = -1;
		try{
			if(rowNum <=0)
				return -1;

			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return -1;

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);

			int col_Num=-1;
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return -1;

			row = sheet.getRow(rowNum);
			if(row==null)
				return -1;
			cell = row.getCell(col_Num);

			if(cell==null)
				return -1;

			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC )
				value = (int) (cell.getNumericCellValue());

			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return -1;
		}
		catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return value;

	}

	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);

			row = sheet.getRow(rowNum);
			if(row==null)
				return "";
			cell = row.getCell(colNum);

			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();

			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC )
				return String.valueOf(cell.getNumericCellValue());

			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}

	}

	public String RetrieveAutomationKeyFromExcel(String wsName, String colName, String rowName) throws Exception{

		try{

			int index = workbook.getSheetIndex(wsName);
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(1);

			HSSFRow headRow=sheet.getRow(1);

			int rowCount = getRowCount(wsName);
			int colCount = getColumnCount(wsName);
			int colNumber=-1;	
			int rowNumber=-1;	

			for(int i=0; i<colCount; i++){
				if(headRow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			if(colNumber==-1){
				throw new RuntimeException("colNumber  -1");				
			}

			for(int j=0; j<rowCount; j++){
				HSSFRow Suitecol = sheet.getRow(j);				
				if(Suitecol.getCell(1).getStringCellValue().equals(rowName.trim())){
					rowNumber=j;	
				}					
			}

			if(rowNumber==-1){
				return "";				
			}

			row = sheet.getRow(rowNumber);
			cell = row.getCell(colNumber);
			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim();

			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC )
				return String.valueOf(cell.getNumericCellValue()).trim();

			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue()).trim();

		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	public boolean CheckTestCaseNamedSheetExist(String filelocation, String TESTCASE_NAME) throws Exception{
		FileInputStream ipstr = null;
		HSSFWorkbook wb = null;
		boolean sheetExist=false;
		try{
			ipstr = new FileInputStream(filelocation);
			wb = new HSSFWorkbook(ipstr);
			ipstr.close();
			for(int k=0; k<wb.getNumberOfSheets(); k++){
				if(wb.getSheetName(k).equalsIgnoreCase(TESTCASE_NAME)){
					sheetExist=true;
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			ipstr = null;
			if(wb!=null){
				wb.close();
				wb=null;
			}
		}
		return sheetExist;
	}

}


