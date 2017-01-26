package temp;

import java.io.File;

public class check2 {

	 public static void main(String args[])
	    {
	        try {
	        	
	        	File file =new File("./Reports/ExtentReports/bkp/15_12_2016/TestReport_15122016_074332_PM.html");
	    		double reportFileSizeInBytes = file.length();
	    		double reportFileSizeInKB = (reportFileSizeInBytes / 1024);
	    		double reportFileSizeInMB = (reportFileSizeInKB / 1024);
	    		int size= (int) reportFileSizeInKB;
	    		int sizeMB= (int) reportFileSizeInMB;
	    		
	    		System.out.println("File size in Bytes : "+reportFileSizeInBytes);
	    		System.out.println("File size in KB : "+reportFileSizeInKB);
	    		System.out.println("File size in MB : "+reportFileSizeInMB);
	    		System.out.println("KB in int : "+size);
	    		System.out.println("MB in int : "+sizeMB);
	    		
	            System.out.println("done");
	        } catch (Throwable e) {
	            System.err.println(e);
	        }
	    }
	 
}
