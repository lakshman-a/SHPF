package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Disability_Services extends FunctionLibrary {

	public synchronized String disServicesPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			//Form Validation
			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("disablitySerBtn", "xpath#//div[@title='Disability Services']");
			hs.put("subRadioButton", "id#rdbtnSubscribe");
			hs.put("unSubRadioButton", "id#rdbtnUnsubscribe");
			hs.put("submit", "id#btnSubmit");
			hs.put("nominateYes", "id#rdbtnYes");
			hs.put("nominateNo", "id#rdbtnNo");
			hs.put("txtName", "id#txtName");
			hs.put("maleRadioButton", "id#rdbtnMale");
			hs.put("femaleRadioButton", "id#rdbtnFemale");
			hs.put("DOB", "id#txtDOB");
			hs.put("addressOne", "id#txtAddress1");
			hs.put("addressTwo", "id#txtAddress2");
			hs.put("contactAdd", "id#txtContactNumber");
			hs.put("emailID", "id#txtEmail");
			hs.put("clear", "id#btnClear");
			hs.put("confirmMsg", "xpath#//span[@id='ErrorMsg' and contains(@style, 'rgb')]");
			hs.put("validationTextMsg", "id#txtEmail-error");

			//Alert validation
			hs.put("valtxtName", "xpath#//input[@id='txtName' and contains(@aria-required, 'true')]");
			hs.put("valmaleRadioButton", "xpath#//input[@id='rdbtnMale' and contains(@style, 'red solid')]");
			hs.put("valfemaleRadioButton", "xpath#//input[@id='rdbtnFemale' and contains(@style, 'red solid')]");
			hs.put("valDOB", "xpath#//input[@id='txtDOB' and contains(@aria-required, 'true')]");
			hs.put("valcontactAdd", "xpath#//input[@id='txtContactNumber' and contains(@aria-required, 'true')]");
			hs.put("valNominYes", "xpath#//input[@id='rdbtnYes' and contains(@style, 'red')]");
			hs.put("valNominNo", "xpath#//input[@id='rdbtnNo' and contains(@style, 'red')]");

			//Table-View

			hs.put("nomineeDetailsBox", "id#gbox_NominateDetails");
			hs.put("nomineeID", "xpath#//td[@aria-describedby='NominateDetails_nomineeId']");
			hs.put("nomineeName", "xpath#//td[@aria-describedby='NominateDetails_nomineeName']");
			hs.put("status", "xpath#//td[@aria-describedby='NominateDetails_status']");
			hs.put("action1", "xpath#//td[@aria-describedby='NominateDetails_action1']//p");
			hs.put("action2", "xpath#//td[@aria-describedby='NominateDetails_action2']//p");
			hs.put("action3", "xpath#//td[@aria-describedby='NominateDetails_action3']//p");
			hs.put("editName", "xpath#//input[@id='txtName' and contains(@class, 'w-md refresh valid')]");
			hs.put("submitDisabled", "xpath#//button[@id='btnSubmit' and contains(@disabled, 'disabled')]");
			hs.put("deleteConfText", "xpath#//button[@id='Yes']/parent::div/preceding-sibling::div[@class='modal-body']//p");
			hs.put("deleteYes", "id#Yes");
			hs.put("deleteNo", "id#No");
			hs.put("nomineeNameSort", "id#jqgh_NominateDetails_nomineeName");
			hs.put("nomineeNameDescOrder", "xpath#//th[@id='NominateDetails_nomineeName']//span[@sort='desc']");
			hs.put("sort1", "xpath#//tr[@id='1']//td[@aria-describedby='NominateDetails_nomineeName']");
			hs.put("sort2", "xpath#//tr[@id='2']//td[@aria-describedby='NominateDetails_nomineeName']");
			hs.put("sort3", "xpath#//tr[@id='3']//td[@aria-describedby='NominateDetails_nomineeName']");
			return hs.get(locator);

		}catch(Exception e){
			System.out.println("Error occurred in POM classes :"+e);
			return null;

		}

	}

}
