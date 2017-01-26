package Page_Objects;

import java.util.Hashtable;

import utils.ReadExcel;
import wrappers.FunctionLibrary;

public class Settings extends FunctionLibrary{

	public synchronized String RetrieveTestDataValue(String filePath,String compName,String strColumnName,String gblrecordsCounter,int strExecEventFlag) throws Exception{
		String strData=null;
		ReadExcel readExcel=new ReadExcel(filePath);

		if(strExecEventFlag!=0){
			strData=readExcel.getCellData(compName, strColumnName, Integer.parseInt(gblrecordsCounter));
		}
		return strData;
	}

	public synchronized String settings_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("SimType_Dropdown", "id#gs_SIMType");
			hs.put("KeyListed_Label", "xpath#//td[@aria-describedby='manageConfigTable_Key'][1]");
			hs.put("KeyListed_Value", "xpath#//td[@aria-describedby='manageConfigTable_Value'][1]");
			hs.put("KeyListed_Value1", "xpath#//td[@aria-describedby='manageConfigTable_Value' and contains(@style, 'center')]//input[@name='Value']");
			hs.put("closeSettingPage_Button", "xpath#//span[@class='pull-right close-icon']");
			hs.put("menu_Dropdown", "id#dropdownMenu1");
			hs.put("settings_Button", "id#settingsMenu");
			hs.put("configuration_Button", "id#Configuration");
			hs.put("country_Dropdown", "xpath#//img[@alt='GBR']/following-sibling::div//span[@class='caret']");
			hs.put("country_DropdownValues", "xpath#//img[@alt='GBR']/following-sibling::div//ul[@class='dropdown-menu']/li");
			hs.put("sim_TypeText", "id#jqgh_manageConfigTable_SIMType");
			hs.put("keyEnter", "xpath#(//input[@id='gs_Key'])[1]");
			hs.put("key_RRBSNetworkID", "xpath#//td[contains(@title, 'RRBSNetworkId') and contains(@style, 'text-align')]");
			hs.put("value_RRBSNetworkID", "xpath#//td[contains(@title, 'RRBSNetworkId') and contains(@style, 'text-align')]/following-sibling::td//input[@name='Value']");
			hs.put("pagination_Value", "xpath#//span[@id='sp_1_manageConfigPager']");
			hs.put("page_Click", "xpath#//*[@id='next_manageConfigPager']/span");
			hs.put("close_Btn", "xpath#//span[@class='pull-right close-icon']");
			hs.put("key_SmtpAddress", "xpath#//td[@title='SmtpAddress' and contains(@style, 'center')]");
			hs.put("value_SmtpAddress", "xpath#//td[@title='SmtpAddress' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SmtpPassword", "xpath#//td[@title='SmtpPassword' and contains(@style, 'center')]");
			hs.put("value_SmtpPassword", "xpath#//td[@title='SmtpPassword' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SmtpPort", "xpath#//td[@title='SmtpPort' and contains(@style, 'center')]");
			hs.put("value_SmtpPort", "xpath#//td[@title='SmtpPort' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_HLREntityName", "xpath#//td[@title='HLREntityName' and contains(@style, 'center')]");
			hs.put("value_HLREntityName", "xpath#//td[@title='HLREntityName' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_MSISDNMinLength_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='MSISDNMinLength' and contains(@style, 'center')]");
			hs.put("value_MSISDNMinLength_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='MSISDNMinLength' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_MSISDNMaxLength_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='MSISDNMaxLength' and contains(@style, 'center')]");
			hs.put("value_MSISDNMaxLength_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='MSISDNMaxLength' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_PlanChangeEnabler", "xpath#//td[@title='PlanChangeEnabler' and contains(@style, 'center')]");
			hs.put("value_PlanChangeEnabler", "xpath#//td[@title='PlanChangeEnabler' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SiteCode", "xpath#//td[@title='SiteCode' and contains(@style, 'center')]");
			hs.put("value_SiteCode", "xpath#//td[@title='SiteCode' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CountryCodeGAF", "xpath#//td[@title='CountryCodeGAF' and contains(@style, 'center')]");
			hs.put("value_CountryCodeGAF", "xpath#//td[@title='CountryCodeGAF' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPCOUNTRYCODE", "xpath#//td[@title='ESHOPCOUNTRYCODE' and contains(@style, 'center')]");
			hs.put("value_ESHOPCOUNTRYCODE", "xpath#//td[@title='ESHOPCOUNTRYCODE' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CountryPrefix", "xpath#//td[@title='CountryPrefix' and contains(@style, 'center')]");
			hs.put("value_CountryPrefix", "xpath#//td[@title='CountryPrefix' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CountryCode", "xpath#//td[@title='CountryCode' and contains(@style, 'center')]");
			hs.put("value_CountryCode", "xpath#//td[@title='CountryCode' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CountryName", "xpath#//td[@title='CountryName' and contains(@style, 'center')]");
			hs.put("value_CountryName", "xpath#//td[@title='CountryName' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_EmailFilePath", "xpath#//td[@title='EmailFilePath' and contains(@style, 'center')]");
			hs.put("value_EmailFilePath", "xpath#//td[@title='EmailFilePath' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SMSFilePath", "xpath#//td[@title='SMSFilePath' and contains(@style, 'center')]");
			hs.put("value_SMSFilePath", "xpath#//td[@title='SMSFilePath' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESMESMSEnabler", "xpath#//td[@title='ESMESMSEnabler' and contains(@style, 'center')]");
			hs.put("value_ESMESMSEnabler", "xpath#//td[@title='ESMESMSEnabler' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SendPrefLanguagetoRRBS", "xpath#//td[@title='SendPrefLanguagetoRRBS' and contains(@style, 'center')]");
			hs.put("value_SendPrefLanguagetoRRBS", "xpath#//td[@title='SendPrefLanguagetoRRBS' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_SMSMarkettingEnabler", "xpath#//td[@title='SMSMarkettingEnabler' and contains(@style, 'center')]");
			hs.put("value_SMSMarkettingEnabler", "xpath#//td[@title='SMSMarkettingEnabler' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_PrefLanguageConfig_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='PrefLanguageConfig' and contains(@style, 'center')]");
			hs.put("value_PrefLanguageConfig_Prepaid", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='PrefLanguageConfig' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_DateTimeformat", "xpath#//td[@title='DateTimeFormat' and contains(@style, 'center')]");
			hs.put("value_DateTimeformat", "xpath#//td[@title='DateTimeFormat' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CallForwardingMode", "xpath#//td[@title='CallForwardingMode' and contains(@style, 'center')]");
			hs.put("value_CallForwardingMode", "xpath#//td[@title='CallForwardingMode' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//Murali
			//Need to include below objects-Murali
			hs.put("key_RegistrationPortOutCheck", "xpath#//td[@title='RegistrationPortOutCheck' and contains(@style, 'center')]");
			hs.put("value_RegistrationPortOutCheck", "xpath#//td[@title='RegistrationPortOutCheck' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_DisableGAFEnableManualEntry", "xpath#//td[@title='DisableGAFEnableManualEntry' and contains(@style, 'center')]");
			hs.put("value_DisableGAFEnableManualEntry", "xpath#//td[@title='DisableGAFEnableManualEntry' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//Murali-End
			hs.put("key_BASIC_SERVICE_CODE", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='BASIC_SERVICE_CODE' and contains(@style, 'center')]");
			hs.put("value_BASIC_SERVICE_CODE", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='BASIC_SERVICE_CODE' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_BASIC_SERVICE_TYPE", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='BASIC_SERVICE_TYPE' and contains(@style, 'center')]");
			hs.put("value_BASIC_SERVICE_TYPE", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='BASIC_SERVICE_TYPE' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_CallForward", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='CallForward' and contains(@style, 'center')]");
			hs.put("value_CallForward", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='CallForward' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_NO_REPL_COND_TIMER", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='NO_REPL_COND_TIMER' and contains(@style, 'center')]");
			hs.put("value_NO_REPL_COND_TIMER", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='NO_REPL_COND_TIMER' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//USA Country
			hs.put("country_Dropdown_USA", "xpath#//img[@alt='USA']/following-sibling::span[@class='caret']");
			hs.put("country_DropdownValues_USA", "xpath#//img[@alt='USA']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			//GAF
			hs.put("key_UseGAF", "xpath#//td[@title='UseGAF' and contains(@style, 'center')]");
			hs.put("value_UseGAF", "xpath#//td[@title='UseGAF' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//Restricted Mail-IDs
			hs.put("key_RES_MAIL_IDS_Pre", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='RESTRICTED_MAIL_IDS' and contains(@style, 'center')]");
			hs.put("value_RES_MAIL_IDS_Pre", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='RESTRICTED_MAIL_IDS' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_RESTRICTED_MAIL_ID_CHECK_Pre", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='RESTRICTED_MAIL_ID_CHECK' and contains(@style, 'center')]");
			hs.put("value_RESTRICTED_MAIL_ID_CHECK_Pre", "xpath#//td[@title='PrePaid']/following-sibling::td[@title='RESTRICTED_MAIL_ID_CHECK' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//GBR
			hs.put("country_Dropdown_GBR", "xpath#//img[@alt='GBR']/following-sibling::span[@class='caret']");
			hs.put("country_DropdownValues_GBR", "xpath#//img[@alt='GBR']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			hs.put("key_TicketIDconfig", "xpath#//td[@title='TicketIDconfig' and contains(@style, 'center')]");
			hs.put("value_TicketIDconfig", "xpath#//td[@title='TicketIDconfig' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_OnlineFeature", "xpath#//td[@title='ESHOPONLINETOPUPFEATURENAME' and contains(@style, 'center')]");
			hs.put("value_OnlineFeature", "xpath#//td[@title='ESHOPONLINETOPUPFEATURENAME' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_RepeatFeature", "xpath#//td[@title='EshopRepeatTopupFeatureName' and contains(@style, 'center')]");
			hs.put("value_RepeatFeature", "xpath#//td[@title='EshopRepeatTopupFeatureName' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPUSERNAME", "xpath#//td[@title='ESHOPUSERNAME' and contains(@style, 'center')]");
			hs.put("value_ESHOPUSERNAME", "xpath#//td[@title='ESHOPUSERNAME' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPPASSWORD", "xpath#//td[@title='ESHOPPASSWORD' and contains(@style, 'center')]");
			hs.put("value_ESHOPPASSWORD", "xpath#//td[@title='ESHOPPASSWORD' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPCHANNELNAME", "xpath#//td[@title='ESHOPCHANNELNAME' and contains(@style, 'center')]");
			hs.put("value_ESHOPCHANNELNAME", "xpath#//td[@title='ESHOPCHANNELNAME' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPBRAND", "xpath#//td[@title='ESHOPBRAND' and contains(@style, 'center')]");
			hs.put("value_ESHOPBRAND", "xpath#//td[@title='ESHOPBRAND' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_ESHOPCOUNTRYCODE", "xpath#//td[@title='ESHOPCOUNTRYCODE' and contains(@style, 'center')]");
			hs.put("value_ESHOPCOUNTRYCODE", "xpath#//td[@title='ESHOPCOUNTRYCODE' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_eShopShowTax", "xpath#//td[@title='eShopShowTax' and contains(@style, 'center')]");
			hs.put("value_eShopShowTax", "xpath#//td[@title='eShopShowTax' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_eShopTaxSplit", "xpath#//td[@title='eShopTaxSplit' and contains(@style, 'center')]");
			hs.put("value_eShopTaxSplit", "xpath#//td[@title='eShopTaxSplit' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_eShopShowVat", "xpath#//td[@title='eShopShowVat' and contains(@style, 'center')]");
			hs.put("value_eShopShowVat", "xpath#//td[@title='eShopShowVat' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_eShopVatSplit", "xpath#//td[@title='eShopVatSplit' and contains(@style, 'center')]");
			hs.put("value_eShopVatSplit", "xpath#//td[@title='eShopVatSplit' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_eShopGetSimWithCredit", "xpath#//td[@title='ESHOPGETSIMWITHCREDIT' and contains(@style, 'center')]");
			hs.put("value_eShopGetSimWithCredit", "xpath#//td[@title='ESHOPGETSIMWITHCREDIT' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_PUKMaxLength", "xpath#//td[@title='PUKMaxLength' and contains(@style, 'center')]");
			hs.put("value_PUKMaxLength", "xpath#//td[@title='PUKMaxLength' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_EnableNewSubscriber_Number", "xpath#//td[@title='EnableNewSubscriber_Number' and contains(@style, 'center')]");
			hs.put("value_EnableNewSubscriber_Number", "xpath#//td[@title='EnableNewSubscriber_Number' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_EnableNewSubscriber_Number", "xpath#//td[@title='EnableNewSubscriber_Number' and contains(@style, 'center')]");
			hs.put("value_EnableNewSubscriber_Number", "xpath#//td[@title='EnableNewSubscriber_Number' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_TicketIDMinLength", "xpath#//td[@title='TicketIDMinLength' and contains(@style, 'center')]");
			hs.put("value_TicketIDMinLength", "xpath#//td[@title='TicketIDMinLength' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_TicketIDMaxLength", "xpath#//td[@title='TicketIDMaxLength' and contains(@style, 'center')]");
			hs.put("value_TicketIDMaxLength", "xpath#//td[@title='TicketIDMaxLength' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			hs.put("key_IsOtherAmtDecimal", "xpath#//td[@title='IsOtherAmtDecimal' and contains(@style, 'center')]");
			hs.put("value_IsOtherAmtDecimal", "xpath#//td[@title='IsOtherAmtDecimal' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']");
			//FRA
			hs.put("country_Dropdown_FRA", "xpath#//img[@alt='FRA']");
			hs.put("country_DropdownValues_FRA", "xpath#//img[@alt='FRA']/parent::div/following-sibling::ul/li");
			//NOR
			hs.put("country_Dropdown_NOR", "xpath#.//img[@alt='NOR']");
			hs.put("country_DropdownValues_NOR", "xpath#//img[@alt='NOR']/parent::div/following-sibling::ul/li");
			//Austria Country
			hs.put("country_Dropdown_AUT", "xpath#//img[@alt='AUT']/following-sibling::span[@class='caret']");
			hs.put("country_DropdownValues_AUT", "xpath#//img[@alt='AUT']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			

			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String Settings_Obj_runtime(String filePath,String compName,String gblrecordsCounter,String locator,String SIMType,String title,int strEventFlag) throws Exception{
		try{
			title =RetrieveTestDataValue(filePath, compName, title,gblrecordsCounter, 1);
			SIMType=RetrieveTestDataValue(filePath, compName, SIMType,gblrecordsCounter, 1);
			
			String Key_Configuration_XPath="xpath#//td[@title='"+SIMType+"']/following-sibling::td[@title='"+title+"' and contains(@style, 'center')]";
			String value_Configuration_XPath="xpath#//td[@title='"+SIMType+"']/following-sibling::td[@title='"+title+"' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']";
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("key_Configuration", Key_Configuration_XPath);
			hs.put("value_Configuration",value_Configuration_XPath);
			String locate = hs.get(locator);
			return locate;
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}

	}

	public synchronized String SET_COUNTRY_SETTINGS(String filePath,String compName,String gblrecordsCounter,String locator,String strCountryName,int strEventFlag) throws Exception{
		try{
			String countryName =RetrieveTestDataValue(filePath, compName, strCountryName,gblrecordsCounter, 1);
			Hashtable<String, String> hs = new Hashtable<String, String>();
			if(countryName.equalsIgnoreCase("GBR")){
				hs.put("country_Dropdown", "xpath#//img[@alt='GBR']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='GBR']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}else if(countryName.equalsIgnoreCase("POL")){
				hs.put("country_Dropdown", "xpath#//img[@alt='POL']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='POL']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}else if(countryName.equalsIgnoreCase("USA")){
				hs.put("country_Dropdown", "xpath#//img[@alt='USA']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='USA']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}else if(countryName.equalsIgnoreCase("AUT")){
				hs.put("country_Dropdown", "xpath#//img[@alt='AUT']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='AUT']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}else if(countryName.equalsIgnoreCase("FRA")){
				hs.put("country_Dropdown", "xpath#//img[@alt='FRA']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='FRA']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}else if(countryName.equalsIgnoreCase("NOR")){
				hs.put("country_Dropdown", "xpath#//img[@alt='NOR']/following-sibling::span[@class='caret']");
				hs.put("country_DropdownValues", "xpath#//img[@alt='NOR']/following-sibling::span[@class='caret']/parent::div/following-sibling::ul//li");
			}
			
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}

	}

	public synchronized String SET_CONFIG_VALUE(String filePath,String compName,String gblrecordsCounter,String locator,String strSIMType,String strTitle,int strEventFlag) throws Exception{

		String title=null;
		String SIMType=null;
		try{
			title =RetrieveTestDataValue(filePath, compName, strTitle,gblrecordsCounter, 1);
			SIMType =RetrieveTestDataValue(filePath, compName, strSIMType,gblrecordsCounter, 1);
			String Key_Configuration_XPath="xpath#//td[@title='"+SIMType+"']/following-sibling::td[@title='"+title+"' and contains(@style, 'center')]";
			String value_Configuration_XPath="xpath#//td[@title='"+SIMType+"']/following-sibling::td[@title='"+title+"' and contains(@style, 'center')]/following-sibling::td//input[@name='Value']";
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("key_Configuration", Key_Configuration_XPath);
			hs.put("value_Configuration",value_Configuration_XPath);
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}
}
