package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Registration_USA extends FunctionLibrary {

	public synchronized String registration_USA_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("newSubscriber_txtBox", "id#subsParameter");
			hs.put("register_Button", "id#btnNewRegister");
			hs.put("normal_Reg_ChkBox", "xpath#//input[@id='radio-button1-normal' and contains(@checked, checked)]");
			hs.put("title_Dropdown", "xpath#//select[@id='ddlTitle']");
			hs.put("firstName_TxtBox", "id#txtfirstName");
			hs.put("lastName_TxtBox", "id#txtlastName");
			hs.put("email_TxtBox", "id#txtEmail1");
			hs.put("confirmEmail_TxtBox", "id#txtConfirmEmail1");
			hs.put("contactNo_TxtBox", "id#txtContactNumber_1");
			hs.put("country_TxtBox", "id#txtCountry_1");
			hs.put("postCode_TxtBox", "id#txtPostCode_1");
			hs.put("findAddress_Btn", "xpath#//a[@id='btnFindAddress']//span[@class='glyphicon glyphicon-search']");
			hs.put("resetAddress_Btn", "xpath#//span[@class='glyphicon glyphicon-refresh']");
			hs.put("houseNo_TxtBox", "id#House_Number");
			hs.put("street_TxtBox", "id#txtStreet_1");
			hs.put("city_TxtBox", "id#txtCity_1");
			hs.put("state_TxtBox", "id#txtState_1");
			hs.put("PUK_Number", "id#PUK_Number");
			hs.put("MSISDN_TxtBox", "id#MSISDN_Number");
			hs.put("callMost_DD", "id#call_most");
			hs.put("hearAb_DD", "id#hear_about");
			hs.put("secretQues_DD", "id#Secret_Question");
			hs.put("secretAns_TxtBox", "id#Secret_Answer");
			hs.put("language_Dropdown", "xpath#//*[@id='Language']");
			hs.put("terms_Checkbox", "id#Terms_Conditions");
			hs.put("SMS_Checkbox", "id#cbxMarketingSMS");
			hs.put("cancel_Btn", "id#Cancel");
			hs.put("next_Btn", "id#Next");
			//Confirmation message details
			hs.put("confirm_Title", "id#step2_normal_title");
			hs.put("confirm_FirstName", "id#step2_normal_firstName");
			hs.put("confirm_LastName", "id#step2_normal_lastName");
			hs.put("confirm_DOB", "id#step2_normal_dob");
			hs.put("confirm_Email1", "id#step2_normal_email1");
			hs.put("confirm_Email2", "id#step2_normal_email1_confirm");
			hs.put("confirm_ContactNo", "id#step2_normal_contactNumber");
			hs.put("confirm_Country", "id#step2_normal_Country_1");
			hs.put("confirm_PostCode", "id#step2_normal_postCode_1");
			hs.put("confirm_HouseNo", "id#step2_normal_houseNumber");
			hs.put("confirm_Street", "id#step2_normal_street_1");
			hs.put("confirm_City", "id#step2_normal_city1");
			hs.put("confirm_State", "id#step2_normal_state1");
			hs.put("confirm_PUK_Number", "id#step2_normal_pukNumber");
			hs.put("confirm_MSISDN", "id#step2_normal_MSISDNNumber");
			hs.put("confirm_CallMost", "id#step2_normal_callMost");
			hs.put("confirm_HearAb", "id#step2_normal_hearAbout");
			hs.put("confirm_SecretQues", "id#step2_normal_secretQuestion");
			hs.put("confirm_SecretAns", "id#step2_normal_secretAnswer");
			hs.put("confirm_Language", "id#step2_normal_language");
			hs.put("confirm_SMS_Checkbox", "id#step2_cbxMarketingSMS");
			//Buttons in Registration
			hs.put("back_Button", "id#Back");
			hs.put("submit_Button", "id#Submit");
			//Load Icon
			hs.put("load_Icon", "id#spinIcon");
			hs.put("regPostCode_Load", "id#spnLoadMessage1");
			//Confirmation & error message
			hs.put("confirmation_Msg", "xpath#//span[@id='step1error' and contains(@style, 'color')]");
			hs.put("personal_Info", "xpath#//div[text()='Personal Information']");
			hs.put("restrict_Message", "id#restrictErrorMsg");
			hs.put("PUKCode_ValidMsg", "id#PUK_Number-error");
			hs.put("MSISDN_ValidMsg", "id#MSISDN_Number-error");
			hs.put("invalidEmail_ConfMsg", "id#txtEmail1-error");
			hs.put("invalidConfirmEmail_ConfMsg", "id#txtConfirmEmail1-error");
			//View Registration
			hs.put("expand_RegIcon", "xpath#//li[@class='su-expand-icon']");
			hs.put("view_RegBtn", "id#viewPreRegxn");
			hs.put("viewReg_LoadIcon", "xpath#//span[@class='glyphicon-rotate']");
			hs.put("title_Text", "xpath#//label[@class='bold' and contains(text(), 'Title')]");
			hs.put("view_Title", "xpath#//label[text()='Title']/ancestor::div/following-sibling::div[@class='form-group col-md-1']");
			hs.put("view_FirstName", "xpath#//label[text()='Title']/ancestor::div/following-sibling::div[@class='form-group col-md-2'][1]");
			hs.put("view_LastName", "xpath#//label[text()='Title']/ancestor::div/following-sibling::div[@class='form-group col-md-2'][2]");
			hs.put("view_DOB", "xpath#//text()[normalize-space()='Date Of Birth']/ancestor::div/following-sibling::div[@class='form-group col-md-2']");
			hs.put("view_Email", "xpath#//label[text()='E-Mail']/ancestor::div/following-sibling::div[@class='form-group col-md-2']");
			hs.put("view_ConEmail", "xpath#//label[text()='Confirm']/ancestor::div/following-sibling::div[@class='form-group col-md-2']");
			hs.put("view_Contact", "xpath#//label[text()='Contact']/ancestor::div/following-sibling::div[@class='form-group col-md-2']");
			hs.put("view_AccNo", "xpath#//label[text()='Account Number']/ancestor::div/following-sibling::div[@class='form-group col-md-2']");
			hs.put("view_Country", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[1]");
			hs.put("view_PostCode", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[2]");
			hs.put("view_HouseNo", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[3]");
			hs.put("view_StreetAdd", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[4]");
			hs.put("view_City", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[5]//div[@class='col-md-3 col-md-offset-2 b-margin']");
			hs.put("view_State", "xpath#//label[text()='Address']/parent::div/parent::div/following-sibling::div[5]//div/following-sibling::div");
			hs.put("view_PUKCode", "xpath#//label[text()='PUK Code']/ancestor::div/following-sibling::div[@class='col-md-3'][1]");
			hs.put("view_MSISDN", "xpath#//label[text()='MSISDN']/ancestor::div/following-sibling::div[@class='col-md-3']");
			hs.put("view_IMSI1", "xpath#//label[text()='IMSI-1']/ancestor::div/following-sibling::div[@class='col-md-3'][1]");
			hs.put("view_IMSI2", "xpath#//label[text()='IMSI-2']/ancestor::div/following-sibling::div[@class='col-md-3']");
			hs.put("view_ICCID", "xpath#//label[text()='ICCID']/ancestor::div/following-sibling::div[@class='col-md-3']");
			hs.put("view_MostCallCtry", "xpath#//label[text()='Country you call most']/ancestor::div/following-sibling::div[@class='form-group col-md-3'][1]");
			hs.put("view_HearAbUs", "xpath#//label[text()='How did you hear about us?']/ancestor::div/following-sibling::div[@class='form-group col-md-3']");
			hs.put("view_SecQues", "xpath#//label[text()='Secret Question']/ancestor::div/following-sibling::div[@class='form-group col-md-3']");
			hs.put("view_SecAns", "xpath#//label[text()='Secret Answer']/ancestor::div/following-sibling::div[@class='col-md-3']");
			hs.put("view_Language", "xpath#//label[text()='Language']/ancestor::div/following-sibling::div[@class='form-group col-md-3']");
			hs.put("view_CloseButton", "xpath#//h4[@class='modal-title' and contains(text(), 'United States of America')]/preceding-sibling::button[@class='close']");
			//Validation alert red boxes
			hs.put("val_HighMsg", "id#lblError");
			hs.put("val_Title", "xpath#//select[@id='ddlTitle' and contains(@aria-required, 'true')]");
			hs.put("val_FirstName", "xpath#//input[@id='txtfirstName' and contains(@aria-required, 'true')]");
			hs.put("val_LastName", "xpath#//input[@id='txtlastName' and contains(@aria-required, 'true')]");
			hs.put("val_DOB", "xpath#//input[@id='txtdateofbirth' and contains(@aria-required, 'true')]");
			hs.put("val_Email", "xpath#//input[@id='txtEmail1' and contains(@aria-required, 'true')]");
			hs.put("val_ConfirmEmail", "xpath#//input[@id='txtConfirmEmail1' and contains(@aria-required, 'true')]");
			hs.put("val_ContactNo", "xpath#//input[@id='txtContactNumber_1' and contains(@aria-required, 'true')]");
			hs.put("val_PostCode", "xpath#//input[@id='txtPostCode_1' and contains(@aria-required, 'true')]");
			hs.put("val_HouseNo", "xpath#//input[@id='House_Number' and contains(@aria-required, 'true')]");
			hs.put("val_Street", "xpath#//input[@id='txtStreet_1' and contains(@aria-required, 'true')]");
			hs.put("val_City", "xpath#//input[@id='txtCity_1' and contains(@aria-required, 'true')]");
			hs.put("val_State", "xpath#//select[@id='txtState_1' and contains(@aria-required, 'true')]");
			hs.put("val_PUK", "xpath#//input[@id='PUK_Number' and contains(@aria-required, 'true')]");
			hs.put("val_CallMost", "xpath#//select[@id='call_most' and contains(@aria-required, 'true')]");
			hs.put("val_HearAb", "xpath#//select[@id='hear_about' and contains(@aria-required, 'true')]");
			hs.put("val_SecretQues", "xpath#//select[@id='Secret_Question' and contains(@aria-required, 'true')]");
			hs.put("val_SecretAns", "xpath#//input[@id='Secret_Answer' and contains(@aria-required, 'true')]");
			hs.put("val_Language", "xpath#//select[@id='Language' and contains(@aria-required, 'true')]");
			hs.put("val_Terms", "xpath#//input[@id='Terms_Conditions' and contains(@style, 'red')]");
			//Edit Registration
			hs.put("edit_RegBtn", "id#editPreRegxn");
			hs.put("edit_CancelBtn", "id#Cancel");
			//Close Button
			hs.put("reg_CloseBtn", "xpath#//span[@class='close-icon']");
			hs.put("viewSubReg_CloseBtn", "xpath#//li[@class='close-icon']");
			//View_Subscriber
			hs.put("view_SubMSISDN", "xpath#//li[@id='liMSISDN']");
			hs.put("view_SubICICD", "xpath#//li[@id='liICICD']");
			//CRM Page loader
			hs.put("crm_PageLoader", "xpath#//div[@id='crmPreLoader']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String registration_USA_DatePicker(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("DOB", "txtdateofbirth");
			String locate = hs.get(locator);
			return locate;
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}
}
