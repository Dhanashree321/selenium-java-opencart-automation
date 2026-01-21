package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\OpenCart_LoginData.xlsx";
		
		ExcelUtility xlutil=new ExcelUtility(path);
		

	   // int totalrows = lastRowIndex;   // number of data rows (excluding header)
		
		int totalrows=xlutil.getRowConunt("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		// handle empty / header-only sheet
	    if (totalrows == 0) {
	        return new String[0][0];
	    }
		
		String logindata[][]=new String[totalrows][totalcols];
		
		for(int i=1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcols;j++)
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
		}
		return logindata;
	}

}
