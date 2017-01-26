package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class SwapImsi_Page extends FunctionLibrary {

	public synchronized String SwapImsi_PageObj(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("SWAPImsiLink", "xpath#.//*[@id='SIM_ImsiSwap']//div[@class='thumbnail-icon']");
			hs.put("Submit_button", "id#btnSubmit");
			hs.put("NewMSISDN", "id#newMSISDN");
			hs.put("newICCID", "id#newICCID");
			hs.put("PAN_Number", "id#panNumber");
			hs.put("CIP", "id#cip");
			hs.put("AlertMessage", "id#responseMessage");
			hs.put("FrequentDialedNumber", "id#frequentCalNumber");
			hs.put("CIPErrorMessage", "id#cip-error");
			hs.put("PANErrorMessage", "id#panNumber-error");
			hs.put("Reject_Button", "id#btnReject");
			hs.put("SwapIMSIApprovalLink", "xpath#.//a[text()='Swap IMSI']");
			hs.put("RequestID", "xpath#.//*[@id='gs_Id']");	
			hs.put("PreAuthorize_button", "id#btnPendingApp");	
			hs.put("PreAuthorize_button_admin", "id#btnApprove");	
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String SwapIMSI_Pending_Approvals(String locator, String dynamicValue){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("pendingApproval_SwapIMSI", "xpath#//td[@title='"+dynamicValue+"']/following-sibling::td[@aria-describedby='pendingItemTable_Msisdn']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
