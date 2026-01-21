package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility
{
	public FileInputStream fi;
	public  FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public ExcelUtility(String path)
	{
		this.path=path;
	}
	
	public int getRowConunt(String sheetName) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		
	    int lastRowIndex = sheet.getLastRowNum(); // index

		//int rowcount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		// if sheet has only header OR is empty
	    if (lastRowIndex < 1) {
	        return 0;   // 0 data rows
	    }
		return lastRowIndex;   // number of data rows (excluding header)
	}
	
	/*public int getCellCount(String sheetName,int rownum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.createRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		return cellcount;
	}*/
	
	public int getCellCount(String sheetName, int rownum) throws IOException
	{
	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);
	    sheet = workbook.getSheet(sheetName);

	    row = sheet.getRow(rownum);   // read existing row

	    int cellcount = 0;

	    if (row != null) {
	        cellcount = row.getLastCellNum();
	    }

	    workbook.close();
	    fi.close();

	    // when row is empty / missing
	    if (cellcount < 0) {
	        return 0;
	    }

	    return cellcount;
	}

	
	public String getCellData(String sheetName,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter=new DataFormatter();
		String data;
		try
		{
			data=formatter.formatCellValue(cell); //
		}
		catch(Exception e)
		{
			data="";
			
		}
		workbook.close();
		fi.close();
		return data;
	}
	
	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException
	{
		File xlfile=new File(path);
		if(!xlfile.exists())
		{
			workbook=new XSSFWorkbook();
			fo=new FileOutputStream(path);
			workbook.write(fo);
		}
		
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		
		if(workbook.getSheetIndex(sheetName)==-1)
		{
			workbook.createSheet(sheetName);
		}
		sheet=workbook.getSheet(sheetName);
		
		if(sheet.getRow(rownum)==null)
		{
			sheet.createRow(rownum);
		}
		row=sheet.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(colnum);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		
	}
	
	public void fillGreenColor(String sheetName,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		
	}
	
	public void fillRedColor(String sheetName,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

}
