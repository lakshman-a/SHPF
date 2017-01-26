package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Agent_Profile extends FunctionLibrary{
	
public synchronized String Agent_Profile_Page(String locator){
		
		try{
		Hashtable<String, String> hs = new Hashtable<String, String>();
		hs.put("menu_Dropdown", "id#dropdownMenu1");
		hs.put("Agent_Profile", "id#agentProfile");
		hs.put("Agent_Full_Name", "id#agentFullName");
		hs.put("Agent_User_Name", "id#agentUserName");
		hs.put("Phone", "id#agentPhone");
		hs.put("Email", "id#agentEMail");
		hs.put("Status", "id#agentStatus");
		hs.put("Last_Login", "xpath#.//*[@id='myProfileForm']//strong[contains(text(),'Last Login')]/../../div[2]");
		hs.put("Access_Level", "id#agentAccess");
		hs.put("Old_Password", "id#agentOldPassword");
		hs.put("New_Password", "id#agentNewPassword");
		hs.put("Retype_New_Password", "id#agentConfirmNewPassword");
		hs.put("Update", "id#btnSubmitChangePass");
		hs.put("Reset", "id#btnResetChangePass");
		hs.put("Alert_Message", "id#lblresult");
		hs.put("Change_Password_Message", "id#chgPwdMessage");
		hs.put("Upload_Profile_Image", "id#myProfileImg");
		hs.put("Remove_Profile_Image", "xpath#.//*[@id='btnRemoveProfileImg']/span[@class='glyphicon glyphicon-remove']");
		hs.put("Agent_Confirm_Password_Alert", "id#agentConfirmNewPassword-error");
		hs.put("Upload_Profile_Message", "xpath#//div[@id='myModel']//*//*//div[@class='modal-header']");
		hs.put("Close_Button", "id#ErrmsgClose");
		
		return hs.get(locator);

	}catch(Exception e){
		log.info("Error occurred in POM classes :"+e);
	}
	return null;
}

}
