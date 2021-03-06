package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * <h1>ScriptDetails</h1>
 * 
 * <p>This class is used to Create HTML Overview Excel Report and also updated the Script details tracker which has to be placed in the desired path.</p>
 *  * Following 2 actions will be Generated by this Script.
 * 1. HTMLOverView Report Generation
 * 2. Script details Tracker updation.
 * 
 * <b>HTMLOverView Report Generation</b>
 * This is a Excel Report generated based on the HTML Report avaialble in the Given Path
 * <b>Script details Tracker updation</b>
 * Script details should be placed in the Path configured in the System Config Property file, if placed then the tracker will be updated
 * If not placed only HTML Overview report will only get generated.
 * 
 * On completion of each testNG.xml file, this script will be triggered.
 * 
 * <b>Note:If you want to execute this script standalone then, uncomment the Main and Run this Script as JAVA Application then the same actions will happen.</b>
 * <b>Kindly update/refer all the values in the SystemConfig property file and start the script</b>
 * 
 * @author Lakshman A
 * @since DEC 21, 2016
 *
 */

public class ScriptDetails {

	static boolean reported=false;
	static boolean reportAllSteps=true;
	static Properties sysProperty;
	static boolean updateStatus=true;

	public static void main(String[] args) {
		generateExcelReports();
	}

	public static boolean generateExcelReports(){

		String strAbsResultFileName;
		File resultFolderArray[]=null;
		int rowNumber=0;
		try{

			sysProperty =new Properties();
			sysProperty.load(new FileInputStream(new File("./src/properties/SystemConfig.properties")));

			String excelFilename = sysProperty.getProperty("OverviewExcelReportPath")+sysProperty.getProperty("OverviewExcelReportName");
			String reportFilePath= sysProperty.getProperty("testReportFilePath");
			String reportFileName= sysProperty.getProperty("testReportFileName");

			//Script_Details sheet details
			String sdSheetName=sysProperty.getProperty("testReportSheetName");
			String scriptColName=sysProperty.getProperty("testReportScriptColName");
			String statusColName=sysProperty.getProperty("testReportStatusColName");
			String reasonColName=sysProperty.getProperty("testReportReasonColName");
			String reportColName=sysProperty.getProperty("testReportColReportName");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(sysProperty.getProperty("OverviewExcelReportSheetName"));

			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("SNo");
			rowhead.createCell(1).setCellValue("TestReport FileName");
			rowhead.createCell(2).setCellValue("Module Name");
			rowhead.createCell(3).setCellValue("TestScript Name");
			rowhead.createCell(4).setCellValue("TestCase Desc");
			rowhead.createCell(5).setCellValue("TestCase Status");
			rowhead.createCell(6).setCellValue("First Failure Reason");
			rowhead.createCell(7).setCellValue("All Failed Steps [ <ComponentName> : Failed Step Desc ]");
			rowhead.createCell(8).setCellValue("Test Total Time");
			rowhead.createCell(9).setCellValue("Test Start Time");
			rowhead.createCell(10).setCellValue("Test End Time");
			rowhead.createCell(11).setCellValue("No of Components");
			rowhead.createCell(12).setCellValue("Component Names");
			rowhead.createCell(13).setCellValue("No of Comp. Passed");
			rowhead.createCell(14).setCellValue("No of Comp. Failed");
			rowhead.createCell(15).setCellValue("Author Name");
			rowhead.createCell(16).setCellValue("Browser Name");
			rowhead.createCell(17).setCellValue("Report Link");

			File htmlReportPath = new File(sysProperty.getProperty("ExtentReportsPath"));

			ArrayList<String> arrayReportHtmlNames=new ArrayList<String>();
			resultFolderArray=htmlReportPath.listFiles();

			for(int fileNumber=0; fileNumber < htmlReportPath.listFiles().length; fileNumber++){
				strAbsResultFileName=resultFolderArray[fileNumber].getAbsoluteFile().getName().toString();
				if(strAbsResultFileName.contains(".html")){
					arrayReportHtmlNames.add(strAbsResultFileName);
				}
			}
			
			arrayReportHtmlNames.sort(null);

			System.out.println("No of files : "+arrayReportHtmlNames.size());

			for (String reportFile : arrayReportHtmlNames) {

				System.out.println("reportFile is "+reportFile);

				Document doc = Jsoup.parse(new File(htmlReportPath+"\\"+reportFile), "utf-8");
				//String title = doc.title();  
				//System.out.println("Report Title is: " + title);  

				Elements liCollectionNodes = doc.getElementsByAttributeValueContaining("class", "collection-item test displayed active");

				for (Element liCollectionNode : liCollectionNodes) {
					reported=false;
					rowNumber++;
					HSSFRow row = sheet.createRow(rowNumber);

					//System.out.println("********************************** TEST CASE STARTS *************************************************");

					String testCaseName =liCollectionNode.getElementsByClass("test-name").text();
					String testCaseStatus =liCollectionNode.getElementsByAttributeValueContaining("class", "test-status label right outline capitalize").text();

					//String reportFileName= resultFolderArray[i].getAbsoluteFile().getName();
					//System.out.println("Report Name "+reportFile);
					System.out.println("Testcase : "+testCaseName+"|| Status : "+testCaseStatus);
					//System.out.println();
					row.createCell(0).setCellValue(String.valueOf(rowNumber));

					row.createCell(3).setCellValue(testCaseName);
					row.createCell(5).setCellValue(testCaseStatus.toUpperCase());
					updateScriptDetailsExcel(reportFilePath+reportFileName, sdSheetName, scriptColName, testCaseName, statusColName, testCaseStatus.toUpperCase());
					updateScriptDetailsExcel(reportFilePath+reportFileName, sdSheetName, scriptColName, testCaseName, reportColName, reportFile);

					row.createCell(1).setCellValue(reportFile);
					row.createCell(17).setCellValue(htmlReportPath+"\\"+reportFile);

					Elements testBodyNodes=liCollectionNode.getElementsByClass("test-body");
					//System.out.println("testIbodyNode length is : "+testBodyNodes.size());

					for (Element testBody : testBodyNodes) {

						Elements testBodyChildrenNodes=testBody.children();
						//System.out.println("testBodyChildrenNodes is "+testBodyChildrenNodes.size());

						for (Element testBodyChildrenNode : testBodyChildrenNodes) {

							//System.out.println("Class Name : "+testBodyChildrenNode.className());

							if(testBodyChildrenNode.className().equalsIgnoreCase("test-info")){

								Elements testStartedTimeNodes = testBodyChildrenNode.getElementsByAttributeValue("title", "Test started time");
								String testStartTime=testStartedTimeNodes.text();
								row.createCell(9).setCellValue(testStartTime);
								//System.out.println("Testcase started Time : "+testStartTime);

								Elements tesEndedTimeNodes = testBodyChildrenNode.getElementsByAttributeValue("title", "Test ended time");
								String testEndTime=tesEndedTimeNodes.text();
								//System.out.println("Testcase Ended Time : "+testEndTime);
								row.createCell(10).setCellValue(testEndTime);

								Elements testTotalTimeNodes = testBodyChildrenNode.getElementsByAttributeValue("title", "Time taken to finish");
								String totalTimeTaken=testTotalTimeNodes.text();
								//System.out.println("Testcase Total Time : "+totalTimeTaken);
								row.createCell(8).setCellValue(totalTimeTaken);

							}

							if(testBodyChildrenNode.className().equalsIgnoreCase("test-desc")){
								String testCaseDesc=testBodyChildrenNode.text();
								//System.out.println("Testcase Desc : "+testCaseDesc);
								row.createCell(4).setCellValue(testCaseDesc);
							}

							if(testBodyChildrenNode.className().equalsIgnoreCase("test-attributes")){

								Elements testcategoriesNodes = testBodyChildrenNode.getElementsByClass("categories");

								for (Element testcategory : testcategoriesNodes) {

									Elements categoryChildren = testcategory.children();

									if(categoryChildren.size()>1){
										String browser=categoryChildren.get(0).text();
										//System.out.println("Testcase Category : "+moduleName);
										row.createCell(16).setCellValue(browser);

										String moduleName=categoryChildren.get(1).text();
										//System.out.println("Testcase Category : "+moduleName);
										row.createCell(2).setCellValue(moduleName);
									}else{
										String moduleName=testcategoriesNodes.text();
										//System.out.println("Testcase Category : "+moduleName);
										row.createCell(2).setCellValue(moduleName);
									}
								}

								Elements testauthorNodes = testBodyChildrenNode.getElementsByClass("authors");
								String testAuthor=testauthorNodes.text();
								//System.out.println("Testcase Authors : "+testAuthor);
								row.createCell(15).setCellValue(testAuthor);
								//System.out.println();
							}


							if(testBodyChildrenNode.className().equalsIgnoreCase("test-steps")){

								Elements liComponentNodes = testBodyChildrenNode.getElementsByTag("li");
								String totalNoOfComponents=String.valueOf(liComponentNodes.size());
								row.createCell(11).setCellValue(totalNoOfComponents);

								int compPassCount=0;
								int compFailCount=0;
								String reportAllFailSteps="";
								String componentNames="";
								for (Element liComponentNode : liComponentNodes) {

									Elements  liComponentNodeChildren = liComponentNode.children();


									for (Element liComponentNodeChild : liComponentNodeChildren) {

										if(liComponentNodeChild.className().equalsIgnoreCase("collapsible-header test-node pass")){

											Elements compNameNodes = liComponentNodeChild.getElementsByClass("test-node-name");
											//System.out.println("##Component Name	: "+compNameNodes.text());
											//Elements compDescNodes = liComponentNodeChild.getElementsByClass("test-node-desc");
											//.out.println("##Component Desc : "+compDescNodes.text());

											/*Elements compStatusNodes = liComponentNodeChild.getElementsByAttributeValueMatching("class", "test-status label outline capitalize(.*)");
											//System.out.println("Component Status : "+compStatusNodes.text());
											Elements compStartedTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Test started time");
											//System.out.println("Component started Time : "+compStartedTimeNodes.text());
											Elements compendedTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Test ended time");
											//System.out.println("Component Ended Time : "+compendedTimeNodes.text());
											Elements compTotalTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Time taken to finish");
											//System.out.println("Component Total Time : "+compTotalTimeNodes.text());
											System.out.println();*/
											componentNames=componentNames+compNameNodes.text()+"\n";
											compPassCount++;
										}


										if(liComponentNodeChild.className().equalsIgnoreCase("collapsible-header test-node fail")){

											Elements compNameNodes = liComponentNodeChild.getElementsByClass("test-node-name");
											//System.out.println("##Component Name	: "+compNameNodes.text());
											//Elements compDescNodes = liComponentNodeChild.getElementsByClass("test-node-desc");
											//System.out.println("##Component Desc : "+compDescNodes.text());
											//Elements compStatusNodes = liComponentNodeChild.getElementsByAttributeValueMatching("class", "test-status label outline capitalize(.*)");
											//System.out.println("Component Status : "+compStatusNodes.text());

											/*Elements compStartedTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Test started time");
											System.out.println("Component started Time : "+compStartedTimeNodes.text());
											Elements compendedTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Test ended time");
											System.out.println("Component Ended Time : "+compendedTimeNodes.text());
											Elements compTotalTimeNodes = liComponentNodeChild.getElementsByAttributeValue("title", "Time taken to finish");
											System.out.println("Component Total Time : "+compTotalTimeNodes.text());
											//System.out.println();*/
											componentNames=componentNames+compNameNodes.text()+"\n";

											Elements travelBackToParentThenChildrenNodes=liComponentNodeChild.parent().children();

											for (Element liChildNode : travelBackToParentThenChildrenNodes) {
												//System.out.println("Class name : "+liChildNode.className());

												if(liChildNode.className().equalsIgnoreCase("collapsible-body")){
													//System.out.println("Class name inside needed : "+liChildNode.className());

													//Elements compFailedNodes = liChildNode.getElementsByClass("status fail");
													Elements compFailedNodes = liChildNode.getElementsByAttributeValue("class", "status fail");

													for (Element eventFailedNode : compFailedNodes) {

														Elements failureNodeSiblings = eventFailedNode.siblingElements();

														for (Element eachNodeInFailedRow : failureNodeSiblings) {

															if(!reported){
																if(eachNodeInFailedRow.className().equalsIgnoreCase("step-details")){
																	String failedReason=eachNodeInFailedRow.text();
																	//System.out.println("Failed Step : "+failedReason);
																	row.createCell(6).setCellValue(failedReason);
																	updateStatus = updateScriptDetailsExcel(reportFilePath+reportFileName,sdSheetName, scriptColName, testCaseName, reasonColName, failedReason);
																	//updateScriptDetailsExcel(reportFilePath+reportFileName,sdSheetName, scriptColName, testCaseName, reasonColName, failedReason);
																	reported=true;
																}
															}

															if(reportAllSteps){
																if(eachNodeInFailedRow.className().equalsIgnoreCase("step-details")){
																	String failedReason=eachNodeInFailedRow.text();
																	reportAllFailSteps=reportAllFailSteps+"<"+compNameNodes.text()+"> : "+failedReason+"\n";
																}
															}

														}

													}

												}

											}
											compFailCount++;
											//System.out.println();
										}

									}
									row.createCell(13).setCellValue(String.valueOf(compPassCount));
									row.createCell(14).setCellValue(String.valueOf(compFailCount));
									row.createCell(7).setCellValue(reportAllFailSteps);
									row.createCell(12).setCellValue(componentNames);

								}

							}

						}

					}
					//System.out.println("********************************** TEST CASE END *************************************************\n");
					FileOutputStream fileOut = new FileOutputStream(excelFilename);
					workbook.write(fileOut);
					workbook.close();
					fileOut.close();
				}  
				System.out.println("*********** Report END ************\n");
			}

			System.out.println("*** Script Details function Done ***");

			if(!updateStatus){
				System.out.println("******* Mismatch between TestReport and Script details occured. Others Rows are executed successfully. ********");
			}

			return true;

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}


	}

	public static boolean updateScriptDetailsExcel(String filePath,String sheetName,String colNameToSearch,String rowContent,String colNameToWrite,String contentToWrite) throws Exception{
		HSSFWorkbook workbook=null;
		try{

			FileInputStream fis = new FileInputStream(new File(filePath));
			workbook= new HSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;

			HSSFSheet sheet = workbook.getSheetAt(index);
			HSSFRow headRow=sheet.getRow(0);

			int rowCount = sheet.getLastRowNum();
			int colCount = headRow.getLastCellNum();

			int colNumber=-1;	
			int colNumberToWrite=-1;	
			int rowNumber=-1;	

			for(int i=0; i<colCount; i++){
				if(headRow.getCell(i).getStringCellValue().equals(colNameToSearch.trim())){
					colNumber=i;	
					break;
				}					
			}

			if(colNumber==-1){
				throw new RuntimeException("colNumber  -1");				
			}

			for(int k=0; k<colCount; k++){
				if(headRow.getCell(k).getStringCellValue().equals(colNameToWrite.trim())){
					colNumberToWrite=k;	
					break;
				}					
			}

			if(colNumberToWrite==-1){
				throw new RuntimeException("colNumberToWrite  -1");				
			}

			//Following Commented logic will Write in the Forst found Row and Comes out of Loop
			/*for(int j=1; j<=rowCount; j++){
				HSSFRow neededRow = sheet.getRow(j);	
				if(neededRow.getCell(colNumber).getStringCellValue().equals(rowContent.trim())){
					rowNumber=j;	
					break;
				}					
			}
						if(rowNumber==-1){
				return false;				
			}

			HSSFRow rowToWrite=sheet.getRow(rowNumber);
			HSSFCell cell= rowToWrite.getCell(colNumberToWrite);

			if(cell==null){
				cell = rowToWrite.createCell(colNumberToWrite);
			}

			cell.setCellValue(contentToWrite);
			
			*/
			
			//Following logic will Write the given content in all the Rows whereever the Row values matches
			for(int j=1; j<=rowCount; j++){
				HSSFRow neededRow = sheet.getRow(j);	
				if(neededRow.getCell(colNumber).getStringCellValue().equals(rowContent.trim())){
					rowNumber=j;	

					HSSFRow rowToWrite=sheet.getRow(rowNumber);
					HSSFCell cell= rowToWrite.getCell(colNumberToWrite);

					if(cell==null){
						cell = rowToWrite.createCell(colNumberToWrite);
					}
					cell.setCellValue(contentToWrite);
				}					
			}

			FileOutputStream fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();
			return true;

		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Scrip details file is not available in the Given Path");
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			workbook.close();
		}

	}

}
