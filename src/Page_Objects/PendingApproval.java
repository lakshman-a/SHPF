package Page_Objects;

import java.util.Hashtable;

import utils.ReadExcel;
import wrappers.FunctionLibrary;

public class PendingApproval extends FunctionLibrary{

	public synchronized String pendingApproval_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			//hs.put("pendingApproval_Body", "xpath#//*[@id='pendingSlimScroll']/table/tbody");
			hs.put("pendingApproval_Body", "xpath#//div[@class='pending-wrapper']//*//*");
			hs.put("staff_TopUp", "xpath#//a[text()='Staff Topup']");
			hs.put("approve_Button", "id#btnPendingApp");
			hs.put("reject_Button", "id#btnReject");
			hs.put("pendingApproval_Text", "id#crmPageHeader");
			hs.put("nextBtn", "xpath#//span[@class='ui-icon ui-icon-seek-next']");
			hs.put("pageCount", "xpath#//span[@id='sp_1_pendingItemPager']");
			hs.put("requestID_Text", "xpath#//input[@id='gs_Id']");
			hs.put("repsonse_Message", "id#responseMessage");
			hs.put("sim_block", "xpath#//a[text()='SIM Block']");
			hs.put("sim_unblock", "xpath#//a[text()='SIM UnBlock']");
			hs.put("Credit", "xpath#//a[text()='Credit']");
			hs.put("Credit_Approve_Button", "id#btnApprove");
			hs.put("Credit_Reject_Button", "id#btnReject");
			hs.put("Credit_TicketID", "id#ReCreditDetails_TicketId");
			hs.put("Credit_Amount", "id#ReCreditDetails_RecreditAmt");
			hs.put("Credit_Reason", "id#ddlReason");
			hs.put("Credit_Comments", "id#txtComment");
			hs.put("Credit_Admin_Comments", "id#txtAdminComment");
			hs.put("Credit_Confirm_Message", "id#NumberLockerMsg");
			hs.put("Credit_Old_Balance_Message", "id#OldBalanceMsg");
			hs.put("Credit_New_Balance_Message", "id#NewBalanceMsg");
			hs.put("Debit", "xpath#//a[text()='Debit']");
			hs.put("balanceTransfer", "xpath#//a[text()='Balance Transfer']");
			hs.put("partialbalanceTransfer", "xpath#//a[text()='Partial Balance Transfer']");
			hs.put("PBT_Transfer_Amount", "id#txtamount");
			hs.put("PBT_toMSISDN", "id#toMSISDN");
			hs.put("Partial_Balance_Reason", "id#PartialTransferReason");
			hs.put("PBT_ticketID", "id#ticketID");
			hs.put("PBT_Comments", "id#ticketComment");
			hs.put("PBT_Admin_Comments", "id#PaComments");
			hs.put("PBT_Confirm_Message", "id#lblbBalTransMessage");
			hs.put("pendingApproval_Body_Type", "xpath#//b[contains(text(),'Type')]");
			hs.put("OBAPlanUpgrade", "xpath#//a[text()='OBA Plan Upgrade']");
			hs.put("obaAddCredit", "xpath#//a[text()='OBA Additional Credit']");
			hs.put("Stafftopup_Approve_Button", "id#btnPendingApp");
			hs.put("Stafftopup_Reject_Button", "id#btnReject");
			hs.put("Stafftopup_TicketID", "id#txtTicketId");
			hs.put("Stafftopup_Amount", "id#txttopupamt");
			hs.put("Stafftopup_Reason", "id#ddlReason");
			hs.put("Stafftopup_Comments", "id#txtComments");
			hs.put("Stafftopup_Confirm_Message", "id#bStaffTopupsMessage");
			hs.put("Stafftopup_Topup_Type", "id#ddlTopupType");
			hs.put("Stafftopup_Bundle", "id#ddlBundle");
			hs.put("Stafftopup_Bundle_Autorenewal", "id#chkBundleARenewal");
			hs.put("Stafftopup_radio_StaffTopup", "id#rdbStaffTopup");
			hs.put("VIP_staff_TopUp", "xpath#//a[text()='VIP Staff Topup']");
			hs.put("Auto_Topup_Checkbox", "id#chkAutoTopup");
			hs.put("Threshold_Limit", "id#txtThresholdLimit");
			hs.put("Frequency", "id#ddlFrequency");
			hs.put("radio_Account_Balance", "id#rdbAccBalance");
			hs.put("Refund_Payment", "xpath#//a[text()='Refund Payment']");
			hs.put("Refund_Payment_Accept_Button", "id#btnSubmitRefundAuthorise");
			hs.put("Refund_Payment_Reject_Button", "id#btnRejectRefundAuthorise");
			hs.put("Refund_Payment_RequestID", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Request ID')]/../../div[2]");
			hs.put("Refund_Payment_Transaction_ID", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Transaction ID')]/../../div[2]");
			hs.put("Refund_Payment_Topup_Type", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Topup Type')]/../../div[2]");
			hs.put("Refund_Payment_OBA_Credit_Allocated", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'OBA Credit Allocated')]/../../div[2]");
			hs.put("Refund_Payment_OBA_Credit_Used", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'OBA Credit Used')]/../../div[2]");
			hs.put("Refund_Payment_Refund_To", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Refund To')]/../../div[2]");
			hs.put("Refund_Payment_TAX_Amount", "id#refundTaxAmount");
			hs.put("Refund_Payment_VAT_Amount", "id#refundVatAmount");
			hs.put("Refund_Payment_Refund_Amount", "id#refundTotalAmount");
			hs.put("Refund_Payment_Action", "id#ddlRefundAction");
			hs.put("Refund_Payment_Refund_Reason", "id#authRefundReason");
			hs.put("Refund_Payment_Comments", "id#authRefundComment");
			hs.put("Refund_Payment_Submitted_By", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Submitted By')]/../../div[2]");
			hs.put("Refund_Payment_Submitted_Date", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Submitted date')]/../../div[2]");
			hs.put("Refund_Payment_Reinitiation_Date", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Re-Inititiation Date')]/../../div[2]");
			hs.put("Refund_Payment_Request_Count", "xpath#.//*[@id='refundAuthoriseForm']//strong[contains(text(),'Request Count')]/../../div[2]");
			hs.put("Refund_Payment_Message", "id#lblErrorAR");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String RetrieveTestDataValue(String filePath,String compName,String strColumnName,String gblrecordsCounter,int strExecEventFlag) throws Exception{
		String strData=null;
		ReadExcel readExcel=new ReadExcel(filePath);

		if(strExecEventFlag!=0){
			strData=readExcel.getCellData(compName, strColumnName, Integer.parseInt(gblrecordsCounter));
		}
		return strData;
	}

	public synchronized String staff_TopUp_Pending_Approvals(String filePath,String compName,String gblrecordsCounter,String locator, String dynamicValue){

		try{
			String envName =RetrieveTestDataValue(filePath, compName, dynamicValue,gblrecordsCounter, 1);
			String dynamicValueToEnter= Runtimevalue.getProperty(envName);
			
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("pendingApproval_MSISDN", "xpath#//text()[normalize-space()='Request ID']/ancestor::div/following-sibling::div//td[@title='"+dynamicValueToEnter+"']/following-sibling::td[@aria-describedby='pendingItemTable_Msisdn']//a");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

	public synchronized String Sim_Block_Pending_Approvals(String filePath,String compName,String gblrecordsCounter,String locator, String dynamicValue){

		try{
			String envName =RetrieveTestDataValue(filePath, compName, dynamicValue,gblrecordsCounter, 1);
			String dynamicValueToEnter= Runtimevalue.getProperty(envName);
			
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("pendingApproval_SimBlockRID", "xpath#//td[@title='"+dynamicValueToEnter+"']/following-sibling::td[@aria-describedby='pendingItemTable_Msisdn']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

	public synchronized String pendingApproval_Page_ScrollBar(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("scrollBar_JS", "//div[@class='slimScrollBar']");
			String locate = hs.get(locator);
			return locate;

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
