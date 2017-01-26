package utils;

/**
 * 
 * <h1>Constants</h1>
 * 
 * <p>
 * <b>All Variables which remains constant in Excel Eg. Header Rows and SheetName</b> 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class Constants {

	//TestBatch sheet
	//public static String TestBatch_Sheet ="Test_Case_List";
	//public static String TestBatch_Sheet ="PARALLEL_TESTCASES";
	//public static String TestBatch_Sheet_Serial ="SERIAL_TESTCASES";
	public static String TestBatch_Sheet ="TESTCASE_LIST";
	public static String TestBatch_Sheet_Serial ="TESTCASE_LIST";
	public static String TestCase_ColName ="Test_Case_Name";
	public static String TestDescription_ColName ="Test_Case_Description";
	public static String Test_MaxTime_To_Run ="Max_Time_In_Min_Case_Can_Run";
	public static String TestRun_ColName ="Test_Case_Execute";
	public static String TestCategory_ColName ="Test_Category";
	public static String TestAuthor_ColName ="Test_Author";

	//TestData sheet
	public static String AppComp_Execute_ColName ="App_Component_Execute";
	public static String AppComp_Order_ColName ="Execution_Order";
	public static String AppComp_Desc_ColName ="App_Component_Description";
	public static String AppComp_TEvents_ColName="Total_Events";
	public static String AppComp_DEvents_ColName="Disable_Events";

	//TestData Component sheet
	public static String TestData_Execute_ColName ="TestData_Execute";
	public static String TestData_Objective_ColName="Objective";

	public static String RUNMODE_YES="Yes";
	public static String RUNMODE_NO="No";

	//Script details sheet
	public static String TestReport_Sheet ="Script_Details";
	public static String TestReport_ScriptColumnName ="AutomationScript";
	public static String TestReport_Status ="Status";
	public static String TestReport_Reasons ="Reasons";
	public static String TestReport_Owner ="Owner";
	public static String TestReport_ReportFile ="ReportFileName";

	//Script details sheet
	public static String BrowserList_Sheet ="Browser_List";
	public static String BrowserList_TestExecute ="Test_Case_Execute";
	public static String BrowserList_BrowserName ="Browser_Name";
	public static String BrowserList_BrowserDesc ="Browser_Description";


}
