package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Refund_Payment extends FunctionLibrary{

	public synchronized String Refund_Payment_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("Load_Type", "id#loadType");
			hs.put("Load_Parameter", "id#loadParameter");
			hs.put("Load_Subscriber", "id#btnLoadSubscriber");
			hs.put("Menu_Items", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("Bundle_Menu", "xpath#//a[@class='catgLinkID']//span[@class='bundles-menu-image']");
			hs.put("Refund_Payment", "xpath#//div[@class='refund-topup-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("Refund_Bundle_Grid", "id#dvRefundBundle");
			hs.put("Refund_Bundle_Button", "xpath#.//*[@id='dvRefundBundle']//span[@class='glyphicon glyphicon-retweet']");
			hs.put("TransactionID", "xpath#.//*[@id='bundleRefundForm']//strong[contains(text(),'Transaction ID')]/../../div[2]");
			hs.put("Product", "xpath#.//*[@id='bundleRefundForm']//strong[contains(text(),'Product')]/../../div[2]");
			hs.put("OBA_Credit_Allocated", "xpath#.//*[@id='bundleRefundForm']//strong[contains(text(),'OBA Credit Allocated')]/../../div[2]");
			hs.put("OBA_Credit_Used", "xpath#.//*[@id='bundleRefundForm']//strong[contains(text(),'OBA Credit Used')]/../../div[2]");
			hs.put("Tax_Amount", "id#bundleTaxAmount");
			hs.put("VAT_Amount", "id#bundleVatAmount");
			hs.put("Refund_Amount", "id#bundleRefundAmount");
			hs.put("Total_Amount", "xpath#.//*[@id='bundleRefundForm']//strong[contains(text(),'Total Amount')]/../../div[2]");
			hs.put("Refund_To", "id#bundleRefundTo");
			hs.put("Action", "id#bundleRefundAction");
			hs.put("Refund_Reason", "id#bundleRefundReason");
			hs.put("Confirm", "id#btnSubmitRefundBundle");
			hs.put("Confirm_Message", "id#lblErrorBR");
			hs.put("Close", "xpath#.//*[@id='dvBundleProp']//button[@class='crm-btn red']");
			hs.put("Loading_Image", "xpath#.//*[@id='dvBundleProp']//b[@class='false glyphicon-rotate']");
			hs.put("Allowance_Button", "xpath#.//*[@id='dvRefundBundle']//span[@class='glyphicon glyphicon-new-window']");
			hs.put("Bundle_Type_Overview", "xpath#.//*[@id='bundleOverview']//th[contains(text(),'Bundle Type')]/../th[2]");
			hs.put("Auto_Renewal_Status", "xpath#.//*[@id='bundleOverview']//th[contains(text(),'Auto Renewal Status')]/../td");
			hs.put("Mode_Of_Renewal", "xpath#.//*[@id='bundleOverview']//th[contains(text(),'Mode of Renewal')]/../td");
			hs.put("Expiry_Date_Overview", "xpath#.//*[@id='bundleOverview']//th[contains(text(),'Expiry Date')]/../td");
			hs.put("Status_Tab", "xpath#.//*[@id='dvTopupBundle']/ul/li/a[contains(@href, 'dvRefundStatus')]");
			hs.put("Refund_Status_Grid", "id#dvRefundStatus");
			hs.put("Request_Id_Link", "xpath#.//*[@id='dvRefundStatus']/table/tbody/tr/td[@ng-click = 'RefundStatusDetails(refundStatusInfo)']/b");
			hs.put("RefundStatus_RequestID", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Request ID')]/../../div[2]");
			hs.put("RefundStatus_Status", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Status')]/../../div[2]");
			hs.put("RefundStatus_TransactionID", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Transaction ID')]/../../div[2]");
			hs.put("RefundStatus_Topuptype", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Topup Type')]/../../div[2]");
			hs.put("RefundStatus_OBACreditAllocated", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'OBA Credit Allocated')]/../../div[2]");
			hs.put("RefundStatus_OBACreditUsed", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'OBA Credit Used')]/../../div[2]");
			hs.put("RefundStatus_Taxamount", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Tax Amount')]/../../div[2]");
			hs.put("RefundStatus_Vatamount", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'VAT Amount')]/../../div[2]");
			hs.put("RefundStatus_Refundamount", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Refund Amount')]/../../div[2]");
			hs.put("RefundStatus_Refundto", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Refund To')]/../../div[2]");
			hs.put("RefundStatus_Refundreason", "id#reInRefundReason");
			hs.put("RefundStatus_Comments", "id#reInRefundComment");
			hs.put("RefundStatus_Submittedby", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Submitted By')]/../../div[2]");
			hs.put("RefundStatus_Submitteddate", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Submitted date')]/../../div[2]");
			hs.put("RefundStatus_Reinitiationdate", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Re-Inititiation Date')]/../../div[2]");
			hs.put("RefundStatus_Authorisedby", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Authorized By')]/../../div[2]");
			hs.put("RefundStatus_Authoriseddate", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Authorized Date')]/../../div[2]");
			hs.put("RefundStatus_Rejectedby", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Reject By')]/../../div[2]");
			hs.put("RefundStatus_Rejecteddate", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Reject Date')]/../../div[2]");
			hs.put("RefundStatus_Requestcount", "xpath#.//*[@id='refundStatusForm']//strong[contains(text(),'Request Count')]/../../div[2]");
			hs.put("RefundStatus_Reinitiate", "id#btnSubmitRefundReInitiate");
			hs.put("RefundStatus_Close", "xpath#.//*[@id='dvReInitiateProp']//button[@class='crm-btn red']");
			hs.put("RefundStatus_Reinitiate_Message", "id#lblErrorRI");
			hs.put("Reinitiate_Loading_Image", "xpath#.//*[@id='dvReInitiateProp']//b[@class='false glyphicon-rotate']");
			hs.put("Voice_Tab", "xpath#.//*[@id='bundlePropCRM']/ul/li/a[contains(@href, 'bundleVoice')]");
			hs.put("SMS_Tab", "xpath#.//*[@id='bundlePropCRM']/ul/li/a[contains(@href, 'bundleSMS')]");
			hs.put("VAS_Data_Tab", "xpath#.//*[@id='bundlePropCRM']/ul/li/a[contains(@href, 'bundleVasData')]");
			hs.put("Voice_MO_Onnet", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'ON net')]/../th[2]");
			hs.put("Voice_MT_Onnet", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'ON net')]/../th[3]");
			hs.put("Voice_MT_Offnet", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'MT OFF-Net')]/../th[3]");
			hs.put("Voice_MO_Zone_1", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone 1')]/../th[2]");
			hs.put("Voice_MT_Zone_1", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone 1')]/../th[3]");
			hs.put("Voice_MO_Zone_2", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone 2')]/../th[2]");
			hs.put("Voice_MT_Zone_2", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone 2')]/../th[3]");
			hs.put("Voice_MO_Zone_3", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone3')]/../th[2]");
			hs.put("Voice_MT_Zone_3", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Zone3')]/../th[3]");
			hs.put("Voice_Roam", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Roam')]/../th[2]");
			hs.put("Voice_Local_Roam", "xpath#.//*[@id='bundleVoice']//th[contains(text(),'Local Roam')]/../th[2]");
			hs.put("SMS_MO_Onnet", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'ON net')]/../th[2]");
			hs.put("SMS_MT_Onnet", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'ON net')]/../th[3]");
			hs.put("SMS_MT_Offnet", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'MT OFF-Net')]/../th[3]");
			hs.put("SMS_MO_Zone_1", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 1')]/../th[2]");
			hs.put("SMS_MT_Zone_1", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 1')]/../th[3]");
			hs.put("SMS_MO_Zone_2", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 2')]/../th[2]");
			hs.put("SMS_MT_Zone_2", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 2')]/../th[3]");
			hs.put("SMS_MO_Zone_3", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 3')]/../th[2]");
			hs.put("SMS_MT_Zone_3", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Zone 3')]/../th[3]");
			hs.put("SMS_Roam", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Roam')]/../th[2]");
			hs.put("SMS_Local_Roam", "xpath#.//*[@id='bundleSMS']//th[contains(text(),'Local Roam')]/../th[2]");
			hs.put("VAS_Bundle_Balance", "xpath#.//*[@id='bundleVasData']//th[contains(text(),'Bundle Balance')]/../td");
			hs.put("VAS_BParty_Number", "xpath#.//*[@id='bundleVasData']//th[contains(text(),'B Party Number')]/../th[2]/p");
			hs.put("VAS_Add_On_Bundle", "xpath#.//*[@id='bundleVasData']//th[contains(text(),'Add On Bundle')]/../th[2]");
			hs.put("VAS_Data", "xpath#.//*[@id='bundleVasData']//th[contains(text(),'Data')]/../td");
			hs.put("Topup_Tab", "xpath#.//*[@id='dvTopupBundle']/ul/li/a[contains(@href, 'dvRefundTopup')]");
			hs.put("Refund_Topup_Grid", "id#dvRefundTopup");
			hs.put("Topup_TransactionID", "xpath#.//*[@id='topupRefundForm']//strong[contains(text(),'Transaction ID')]/../../div[2]");
			hs.put("Topup_Product", "xpath#.//*[@id='topupRefundForm']//strong[contains(text(),'Product')]/../../div[2]");
			hs.put("Topup_Tax_Amount", "id#topupTaxAmount");
			hs.put("Topup_VAT_Amount", "id#topupVatAmount");
			hs.put("Topup_Refund_Amount", "id#topupRefundAmount");
			hs.put("Topup_Total_Amount", "xpath#.//*[@id='topupRefundForm']//strong[contains(text(),'Total Amount')]/../../div[2]");
			hs.put("Topup_Refund_Reason", "id#topupRefundReason");
			hs.put("Topup_Confirm", "id#btnSubmitRefundTopup");
			hs.put("Topup_Confirm_Message", "id#lblErrorTR");
			hs.put("Topup_Close", "xpath#.//*[@id='dvTopupProp']//button[@class='crm-btn red']");
			hs.put("Topup_Loading_Image", "xpath#.//*[@id='dvTopupProp']//b[@class='false glyphicon-rotate']");
			hs.put("Refund_Topup_Button", "xpath#.//*[@id='dvRefundTopup']//span[@class='glyphicon glyphicon-retweet']");
			hs.put("close_button", "xpath#//li[@title='Subscriber Logout']");
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

}
