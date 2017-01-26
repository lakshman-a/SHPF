package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Registration extends FunctionLibrary {
	
	public synchronized String Registration_AUT_Page(String locator){
		
		try{
		Hashtable<String, String> hs = new Hashtable<String, String>();

		hs.put("Text_Parameter", "id#subsParameter");
		hs.put("Btn_Register", "id#btnNewRegister");
		hs.put("Alert_Message_Register", "id#spnPortinSubscriberMsg");
		hs.put("Btn_FindAddress", "xpath#//a[@id='btnFindAddress']//span[@class='glyphicon glyphicon-search']");
		hs.put("Alert_Message_Email", "id#txtEmail1-error");
		hs.put("Restricted_Email_Alert", "id#restrictErrorMsg");
		hs.put("Btn_Next", "id#Next");
		hs.put("Error_Message", "id#lblError");
		hs.put("Title", "id#ddlTitle");
		hs.put("First_Name", "id#txtfirstName");
		hs.put("Last_Name", "id#txtlastName");
		hs.put("Academic_Title", "id#Academic_Title");
		hs.put("Date_Of_Birth", "id#txtdateofbirth");
		hs.put("Country", "id#ddCountry");
		hs.put("State", "id#txtState_1");
		hs.put("Post_Code", "id#txtPostCode_1");
		hs.put("City", "id#txtCity_1");
		hs.put("Street", "id#txtStreet_1");
		hs.put("House_Number", "id#House_Number");
		hs.put("House_Name", "id#House_Name");
		hs.put("Apartment_Number", "id#ApartmentNo");
		hs.put("Text_Email", "id#txtEmail1");
		hs.put("Contact_Number", "id#txtContactNumber_1");
		hs.put("Qualification", "id#Qualification");
		hs.put("Occupation", "id#Occupation");
		hs.put("MSISDN", "id#MSISDN_Number");
		hs.put("PUK_Code", "id#PUK_Number");
		hs.put("Country_Call_Most", "id#call_most");
		hs.put("Hear_About_Us", "id#hear_about");
		hs.put("Secret_Question", "id#Secret_Question");
		hs.put("Secret_Answer", "id#Secret_Answer");
		hs.put("IVR_Language", "id#IVR_Language");
		hs.put("Phone_Book", "id#Phone_Book");
		hs.put("Terms_Conditions", "id#Terms_Conditions");
		hs.put("Marketing_SMS", "id#cbxMarketingSMS");
		
		hs.put("list_City", "id#ddCity");
		hs.put("list_Street", "id#ddStreet");
		hs.put("GAF_House_Number", "id#ddHouseno");
		hs.put("GAF_Country", "id#txtCountry_1");
		
		hs.put("Confirm_Title", "id#step2Title");
		hs.put("Confirm_First_Name", "id#step2FirstName");
		hs.put("Confirm_Last_Name", "id#step2LastName");
		hs.put("Confirm_Academic_Title", "id#step2Academic_Title");
		hs.put("Confirm_Date_Of_Birth", "id#step2dob");
		hs.put("Confirm_Country", "id#step2txtCountry_1");
		hs.put("Confirm_State", "id#step2ddlState");
		hs.put("Confirm_Post_Code", "id#step2txtPostCode_1");
		hs.put("Confirm_City", "id#step2txtCity_1");
		hs.put("Confirm_Street", "id#step2txtStreet_1");
		hs.put("Confirm_House_Number", "id#step2House_Number");
		hs.put("Confirm_House_Name", "id#step2House_Name");
		hs.put("Confirm_Apartment_Number", "id#step2ApartmentNo");
		hs.put("Confirm_Email", "id#step2txtEmail1");
		hs.put("Confirm_Contact_Number", "id#step2txtContactNumber_1");
		hs.put("Confirm_Qualification", "id#step2Qualification");
		hs.put("Confirm_Occupation", "id#step2occupy");
		hs.put("Confirm_MSISDN", "id#step2MSISDN_Number");
		hs.put("Confirm_PUK_Code", "id#step2PUK_Number");
		hs.put("Confirm_Call_Country_Most", "id#step2call_most");
		hs.put("Confirm_Hear_About_Us", "id#step2hear_about");
		hs.put("Confirm_Secret_Question", "id#step2Secret_Question");
		hs.put("Confirm_Secret_Answer", "id#step2Secret_Answer");
		hs.put("Confirm_IVR_Language", "id#step2IVR_Language");
		hs.put("Confirm_Phone_Book", "id#step2Phone_Book");
		hs.put("Btn_Back", "id#Back");
		hs.put("Btn_Submit", "id#Submit");
		hs.put("Loading_Image", "xpath#.//label[@id='spinIcon'] and contains(@class, 'glyphicon-rotate')]");
		hs.put("Confirm_Message", "id#step1error");
		
		hs.put("Load_Type", "id#loadType");
		hs.put("Load_Parameter", "id#loadParameter");
		hs.put("Load_Subscriber", "id#btnLoadSubscriber");
		hs.put("Subscriber_View", "xpath#//li[@class='su-expand-icon'][@title='Subscriber View']");
		
		// View Registration
		hs.put("View_Registration", "id#viewPreRegxn");
		
		hs.put("View_Puk_Code", "xpath#//label[contains(text(),'PUK Code')]/parent::div/parent::div/div[4]");
		hs.put("View_IMSI_1", "xpath#//label[contains(text(),'IMSI-1')]/parent::div/parent::div/div[2]");
		hs.put("View_IMSI_2", "xpath#//label[contains(text(),'IMSI-2')]/parent::div/parent::div/div[4]");
		hs.put("View_MSISDN", "xpath#//label[text()[normalize-space() = 'LycaMobile']]/parent::div/parent::div/div[2]");
		hs.put("View_ICCID", "xpath#//label[text()[normalize-space() = 'ICCID']]/parent::div/parent::div/div[2]");
		
		hs.put("View_Title", "xpath#//label[contains(text(),'Title')]/parent::div/parent::div/div[2]");
		hs.put("View_First_Name", "xpath#//label[contains(text(),'Title')]/parent::div/parent::div/div[3]");
		hs.put("View_Last_Name", "xpath#//label[contains(text(),'Title')]/parent::div/parent::div/div[4]");
		hs.put("View_Academic_Title", "xpath#//label[contains(text(),'Academic')]/parent::div/parent::div/div[2]");
		hs.put("View_Date_Of_Birth", "xpath#//label[contains(text(),'Date Of Birth')]/parent::div/parent::div/div[2]");
		hs.put("View_Contact_Number", "xpath#//label[contains(text(),'Contact Number')]/parent::div/parent::div/div[4]");
		hs.put("View_Email", "xpath#//label[contains(text(),'E-Mail')]/parent::div/parent::div/div[2]");
		hs.put("View_Account_Number", "xpath#//label[contains(text(),'Account Number')]/parent::div/parent::div/div[2]");
		hs.put("View_Country", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[7]/div[1]");
		hs.put("View_State", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[8]/div[1]");
		hs.put("View_Post_Code", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[8]/div[2]");
		hs.put("View_City", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[9]/div[1]");
		hs.put("View_Street", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[9]/div[2]");
		hs.put("View_House_Number", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[10]/div[1]");
		hs.put("View_Apartment_Number", "xpath#//label[contains(text(),'Address')]/parent::div/parent::div/parent::div/div[12]/div[1]");
		hs.put("View_Qualification", "xpath#//label[contains(text(),'Qualification')]/parent::div/parent::div/div[2]");
		hs.put("View_Occupation", "xpath#//label[contains(text(),'Occupation')]/parent::div/parent::div/div[4]");
		hs.put("View_Country_Call_Most", "xpath#//label[contains(text(),'Country you call most')]/parent::div/parent::div/div[2]");
		hs.put("View_Hear_About_Us", "xpath#//label[contains(text(),'How did you hear about us?')]/parent::div/parent::div/div[4]");
		hs.put("View_Secret_Question", "xpath#//label[contains(text(),'Secret Question')]/parent::div/parent::div/div[2]");
		hs.put("View_Secret_Answer", "xpath#//label[contains(text(),'Secret Answer')]/parent::div/parent::div/div[4]");
		hs.put("View_IVR_Language", "xpath#//label[contains(text(),'IVR Language')]/parent::div/parent::div/div[2]");
		hs.put("View_Phone_Book", "xpath#//label[contains(text(),'Phone Book')]/parent::div/parent::div/div[4]");
		hs.put("View_SMS_Marketing", "id#cbxMarketingSMS");
		hs.put("View_Cancel", "xpath#.//*[@class='modal-footer']/button[@data-dismiss='modal' and contains(@class, 'crm-btn')]");
		hs.put("Loading_Image_Subscriber_View", "xpath#.//*[@class='spnRegDiv glyphicon-rotate']");
		
		// Edit Registration
		hs.put("Edit_Registration", "id#editPreRegxn");
		hs.put("Edit_Cancel", "id#Cancel");
		hs.put("Edit_Account_Number", "id#txtAccountNo");
		hs.put("Edit_MSISDN", "id#MSISDN_Number");
		hs.put("Edit_PUK_Code", "id#PUK_Number");
		hs.put("Edit_IMSI_1", "id#IMSI1");
		hs.put("Edit_IMSI_2", "id#IMS2");
		hs.put("Edit_ICCID", "id#ICCID");
		
		hs.put("close_button", "xpath#//span[@class='close-icon']");
		
		return hs.get(locator);
		
		}catch(Exception e){
			
			System.out.println("Error occurred in POM classes :"+e);
			
		}
		return null;
	}

}