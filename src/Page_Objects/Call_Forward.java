package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Call_Forward extends FunctionLibrary {

	public synchronized String callForwardPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("billingMenuDropdown", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("billingButton", "xpath#//a[@title='Billing']");
			hs.put("callFwdButton", "xpath#//div[@title='Call Forward']");
			hs.put("ticketID", "id#txtTicketId");
			hs.put("reason", "id#txtReason");
			hs.put("callForwarding", "xpath#//label[@class='i-switch bg-success']//input[@id='ChkCallForwarding']");
			hs.put("unConditional_Chkbox", "id#ChkUnConditional");
			hs.put("unConditional_Txtbox", "id#txtUnconditional");

			hs.put("noAnswer_Chkbox", "id#ChkNoAnswer");
			hs.put("noAnswer_Txtbox", "id#txtNoAnswer");
			hs.put("switchedOff_Chkbox", "id#ChkSwitchedOff");
			hs.put("switchedOff_Txtbox", "id#txtSwitchedOff");
			hs.put("busy_Chkbox", "id#ChkBusy");
			hs.put("busy_Txtbox", "id#txtBusy");
			hs.put("checkAns_ChkBox", "xpath#//input[@id='ChkNoAnswer' and contains(@checked, 'checked')]");
			hs.put("callFwd_Enabled", "xpath#//input[@id='ChkCallForwarding' and contains(@checked, 'checked')]");

			hs.put("unCond_TxtboxDisabled", "xpath#//input[@id='txtUnconditional' and contains(@disabled, 'disabled')]");

			hs.put("submitButton", "id#btnSubmitCF");
			hs.put("confirm_Message", "xpath#//label[@id='lblError' and contains(@style, 'rgb')]");
			hs.put("validationMsg", "xpath#//b[@class='false']");

			//Validations
			hs.put("valTitle", "xpath#//input[@id='txtTicketId' and contains(@aria-required, 'true')]");
			hs.put("valReason", "xpath#//input[@id='txtReason' and contains(@aria-required, 'true')]");
			hs.put("valNoAnswer", "xpath#//input[@id='txtNoAnswer' and contains(@aria-required, 'true')]");

			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
