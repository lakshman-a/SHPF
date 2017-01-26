package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Security_Policy extends FunctionLibrary {

	public synchronized String Security_Policy_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("Alert_Message", "id#lblresult");
			hs.put("Old_Password", "id#agentOldPassword");
			hs.put("New_Password", "id#agentNewPassword");
			hs.put("Retype_Password", "id#agentConfirmNewPassword");
			hs.put("Update", "id#btnSubmitChangePass");
			hs.put("Cancel", "id#btnCancelChangePass");
			hs.put("Information", "xpath#//span[@class='glyphicon glyphicon-info-sign']");
			hs.put("Role_Management", "id#RoleManagement");
			hs.put("Admin_Settings", "xpath#//div[@userlinkname='admin']/../div[@class='close editUserCategory glyphicon glyphicon-edit close-icon-green']");
			hs.put("Password_Policy", "xpath#.//*[@id='chkPasswordPolicy']/..");
			hs.put("OK_Button", "id#btnManageUserCategory");
			hs.put("Confirm_Message", "xpath#//span[@class='ResponseMessage success']");
			hs.put("Close_Button", "id#closeUserCatgModal");
			hs.put("Secret_Question_1", "id#ddlQuestion1");
			hs.put("Secret_Question_2", "id#ddlQuestion2");
			hs.put("Secret_Question_3", "id#ddlQuestion3");
			hs.put("Secret_Answer_1", "id#txtAnswer1");
			hs.put("Secret_Answer_2", "id#txtAnswer2");
			hs.put("Secret_Answer_3", "id#txtAnswer3");
			hs.put("Csagent_Settings", "xpath#//div[@userlinkname='csagent']/../div[@class='close editUserCategory glyphicon glyphicon-edit close-icon-green']");
			hs.put("Update_Secret_Question", "id#btnSubmitSecretQuestion");
			hs.put("Reset_Secret_Question", "id#btnResetSecretQuestion");
			hs.put("Update_Password_Message", "id#chgPwdMessage");
			
			hs.put("Password_Help_1", "xpath#.//*[@id='divhelpPassword']/ol/li[1]");
			hs.put("Password_Help_2", "xpath#.//*[@id='divhelpPassword']/ol/li[2]");
			hs.put("Password_Help_3", "xpath#.//*[@id='divhelpPassword']/ol/li[3]");
			hs.put("Password_Help_4", "xpath#.//*[@id='divhelpPassword']/ol/li[4]");
			hs.put("Password_Help_5", "xpath#.//*[@id='divhelpPassword']/ol/li[5]");
			
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
