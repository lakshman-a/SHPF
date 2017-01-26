package temp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LogTest {
    private static ExtentReports extent;

    public static void main(String[] args) {
        extent = new ExtentReports("./Reports/ExtentReports/sample.html", true);

        // creates a toggle for the given test, adds all log events under it    
        ExtentTest test = extent.startTest("My First Test", "Sample description");

        // log(LogStatus, details)
        test.log(LogStatus.INFO, "This step shows usage of log(logStatus, details)");

        test.log(LogStatus.PASS, "Step details");
        
        String xml="<GETACCOUNTDETAILSRESPONSE><RETURNCODE>0</RETURNCODE><ERRDESCRITION>success</ERRDESCRITION><SESSIONID>336398524</SESSIONID><DIALOGID></DIALOGID><MSISDN>9944020211</MSISDN><NETWORKID>1</NETWORKID><MHAPIN></MHAPIN><ACCOUNTSTATUS>1</ACCOUNTSTATUS><VALIDITYDATE>22-02-2022</VALIDITYDATE><CURRENTBALANCE>1000.00</CURRENTBALANCE><LANGUAGE>1</LANGUAGE><VMS_PIN>1232</VMS_PIN><TRANS_PIN>213132</TRANS_PIN><PLANID>4</PLANID><SUBSTATUS>0</SUBSTATUS><SIMSTATUS>0</SIMSTATUS><LIFECYCLESTATE>Active</LIFECYCLESTATE><SUBSTYPE>1</SUBSTYPE><MAINBALANCE>1000.0000</MAINBALANCE><CBS>0</CBS><PROMOBALANCE>0.0000</PROMOBALANCE><PROMOVALIDITYDATE>18-01-2017</PROMOVALIDITYDATE><TOPUPIND>1</TOPUPIND><MNPIND>0</MNPIND><ACCOUNTID>1199294</ACCOUNTID><FAMILY_ACC_ID></FAMILY_ACC_ID><FAMILY_ACC_BAL>0.0000</FAMILY_ACC_BAL><FAMILY_MEMBER_TYPE>0</FAMILY_MEMBER_TYPE><FAMILY_STATUS>0</FAMILY_STATUS><LTE_EXPIRY_DATE></LTE_EXPIRY_DATE><DISCOUNT_CODE_AVAILABLE>0</DISCOUNT_CODE_AVAILABLE><DEDICATED_ACCOUNT_BALANCE></DEDICATED_ACCOUNT_BALANCE><ACTUAL_CREDIT_LIMIT></ACTUAL_CREDIT_LIMIT><AVAILABLE_CREDIT_LIMIT></AVAILABLE_CREDIT_LIMIT><BUNDLE_TOPUP_INDICATOR>1</BUNDLE_TOPUP_INDICATOR><OBA_BUNDLE_CODE>0</OBA_BUNDLE_CODE><OBA_DUE_AMOUNT>0.0000</OBA_DUE_AMOUNT><FIRST_ACTIVATION_DATE>29-08-2016</FIRST_ACTIVATION_DATE><LAST_TOPUP_DATE>09-01-2017</LAST_TOPUP_DATE><FLH_SUBSCRIPTION_STATUS>0</FLH_SUBSCRIPTION_STATUS></GETACCOUNTDETAILSRESPONSE>";
        
        test.log(LogStatus.PASS, xml);
        
        String replaced = xmlEscapeText(xml);
        
        test.log(LogStatus.PASS,replaced);
        
        // end test
        extent.endTest(test);
        
        // calling flush writes everything to the log file
        extent.flush();
        
        System.out.println("done");
    }
    
    static String xmlEscapeText(String t) {
		   StringBuilder sb = new StringBuilder();
		   for(int i = 0; i < t.length(); i++){
		      char c = t.charAt(i);
		      switch(c){
		      case '<': sb.append("&lt;"); break;
		      case '>': sb.append("&gt;"); break;
		      case '\"': sb.append("&quot;"); break;
		      case '&': sb.append("&amp;"); break;
		      case '\'': sb.append("&apos;"); break;
		      default:
		         if(c>0x7e) {
		            sb.append("&#"+((int)c)+";");
		         }else
		            sb.append(c);
		      }
		   }
		   return sb.toString();
		}
}