package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Tickets extends FunctionLibrary {

	public synchronized String tickets_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("services_Select", "id#loadType");
			hs.put("mobileNo_Textbox", "id#loadParameter");
			hs.put("loadSubscriber_Button", "id#btnLoadSubscriber");
			hs.put("create_Ticket", "xpath#//div[@title='Create Ticket']");
			hs.put("new_Ticket", "xpath#//div[@class='ticket-title' and (contains(@style, 'width'))]");
			hs.put("country_Name", "xpath#//*[@id='Countryspan']");
			hs.put("product_dropdown", "id#Productspan");
			hs.put("category_dropdown", "id#Categoryspan");
			hs.put("subCategory_dropdown", "id#SubCategoryspan");
			hs.put("designation_dropdown", "id#Designationspan");
			hs.put("upload_File", "xpath#//span[@class='upload-icon' and (contains(@style, 'float'))]");
			hs.put("uploaded_File_Icon", "xpath#//select[@id='Designationspan']/ancestor::div/following-sibling::div[@class='col-xs-6 form-group']//span[contains(@style,'display: block')]//span[@class='attachments overrideattachement']");
			hs.put("assign_To_Span_dropdown", "id#Assigntospan");
			hs.put("priority_Span_dropdown", "id#Priorityspan");
			hs.put("FCR_dropdown", "id#FCR");
			hs.put("custFeedBack_dropdown", "id#CustFeedBackspan");
			hs.put("create_Button", "id#create");
			hs.put("ticket_ID", "xpath#//div[contains(text(),'Ticket ID')]");
			hs.put("close_Button", "id#ErrmsgClose");
			hs.put("filter_Button", "xpath#//*[@id='CreateTicket']/div");
			hs.put("filters_Body", "id#create-ticket-template");
			hs.put("scroll_Bar", "xpath#//div[@class='slimScrollBar'and not(contains(@style, 'display: none;'))]");
			hs.put("attachment_Link", "xpath#//*[@id='ulAttachList']/li/div/a");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String ticketID_DatePicker(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("close_Date_Picker", "Id#txtEstCloDate");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String ticketID(String locator, String ticketID){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("Attachment_TicketID", "xpath#//span[text()='"+ticketID+"']/ancestor::div[@class='ticket-header']/following-sibling::div//span[@title='Attachments']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}
}
