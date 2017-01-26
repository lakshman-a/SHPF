package temp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class sotresd {

	public static void main(String[] args) {
		
		File htmlReportPath = new File("D:\\D Drive\\AUTOMATION\\WORKSPACE\\CRM\\CRM_Parallel_Framework_v3\\Reports\\ExtentReports\\");
		File resultFolderArray[]=null;
		String strAbsResultFileName;
		
		ArrayList<String> arrayReportHtmlNames=new ArrayList<String>();
		resultFolderArray=htmlReportPath.listFiles();

		for(int fileNumber=0; fileNumber < htmlReportPath.listFiles().length; fileNumber++){
			strAbsResultFileName=resultFolderArray[fileNumber].getAbsoluteFile().getName().toString();
			if(strAbsResultFileName.contains(".html")){
				arrayReportHtmlNames.add(strAbsResultFileName);
			}
		}
		
		//arrayReportHtmlNames.sort();
		
		System.out.println("No of files : "+arrayReportHtmlNames.size());

		for (String reportFile : arrayReportHtmlNames) {

			System.out.println("reportFile is "+reportFile);
		
		}
		
		String datetime=new SimpleDateFormat ("_dd_MM_yyyy_HHmmss").format(new Date());
		System.out.println(datetime);
		
		
	}
	
}
