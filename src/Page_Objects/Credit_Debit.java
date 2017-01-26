package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Credit_Debit extends FunctionLibrary{

	public synchronized String Credit_Debit_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("Load_Type", "id#loadType");
			hs.put("Load_Parameter", "id#loadParameter");
			hs.put("Load_Subscriber", "id#btnLoadSubscriber");
			hs.put("Menu_Items", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("Billing_Menu", "xpath#//a[@class='catgLinkID']//span[@class='billing-menu-image']");
			hs.put("Credit_Debit", "xpath#//div[@class='assign-promotion-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("radio_button_Credit", "id#rdbtnCredit");
			hs.put("radio_button_Debit", "id#rdbtnDebit");
			hs.put("Ticket_Id", "id#ReCreditDetails_TicketId");
			hs.put("Amount", "id#ReCreditDetails_RecreditAmt");
			hs.put("Reason", "id#ddlReason");
			hs.put("Comments", "id#txtAdminComment");
			hs.put("Confirm_Message", "id#NumberLockerMsg");
			hs.put("Old_Balance_Message", "id#OldBalanceMsg");
			hs.put("New_Balance_Message", "id#NewBalanceMsg");
			hs.put("Submit", "id#btnApprove");
			hs.put("Reset", "id#btnReset");
			hs.put("History", "id#btnHistory");
			hs.put("Credit_Debit_History_Table", "id#gbox_GETCreditDetails");
			hs.put("Dialed_Number", "id#ReCreditDetails_DialledMsisdn");
			hs.put("Dialed_Date", "id#ReCreditDetails_DialledDate");
			hs.put("Duration", "id#ReCreditDetails_Duration");
			hs.put("csagent_Comments", "id#txtComment");
			hs.put("Credit_History_Table", "xpath#//table[@aria-labelledby='gbox_GETCreditDetails']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}

	}

}
