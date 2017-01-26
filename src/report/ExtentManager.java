package report;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * 
 * <h1>ExtentManager</h1>
 * 
 * <p>
 * <b>Main Class to Create Extent Reports for Parallel Execution</b> 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class ExtentManager {
	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		extent = null;
		extent = new ExtentReports(filePath, true);
		return  extent;
	}

	public synchronized static ExtentReports getReporter() {
		return extent;
	}
}
