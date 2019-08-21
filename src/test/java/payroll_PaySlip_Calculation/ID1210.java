package payroll_PaySlip_Calculation;


import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;

/**
 * Display the calculated value of non-taxable payment amount.
 **/
public class ID1210 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static int noneTaxableAmount = 0;
	public static int id1200 = 0;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		noneTaxableAmount = Integer.parseInt(addSimpleEmployeeInfo(driver));
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * #id1210 = #id650 - #id1200
	 **/
	@Test
	public void id1210_1() throws Exception {
		int id390 = (int)(Math.random()*10000000);
		int id410 = (int)(Math.random()*10000000);
		int id430 = (int)(Math.random()*10000000);
		int id450 = (int)(Math.random()*10000000);
		int id470 = (int)(Math.random()*10000000);
		int id490 = (int)(Math.random()*10000000);
		int id510 = (int)(Math.random()*10000000);
		int id530 = (int)(Math.random()*10000000);
		int id550 = (int)(Math.random()*10000000);
		int id570 = (int)(Math.random()*10000000);
		int id590 = (int)(Math.random()*10000000);
		int id610 = (int)(Math.random()*10000000);
		int id620 = (int)(Math.random()*10000000);
		int id630 = (int)(Math.random()*10000000);
		int id640 = (int)(Math.random()*10000000);
		int sum = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
		//input the value
		Common.clear(driver,paymentStatements.baseSalary.xpath);		
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance2.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance3.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance4.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance5.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance6.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance7.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance8.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance9.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance10.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance11.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.travelAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).sendKeys(String.valueOf(id620));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.overtimePay.xpath);
		driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).sendKeys(String.valueOf(id630));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.nonEmploymentDeduction.xpath);
		driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).sendKeys(String.valueOf(id640));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonTaxablePayment.xpath)).getText();
		String id650 = driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().replace(",","");
		String id1200 = driver.findElement(By.xpath(paymentStatements.taxablePayment.xpath)).getText().replace(",","");;
		int id1210 = Integer.parseInt(id650)-Integer.parseInt(id1200);
		//check the data
		if(Common.formatNum(String.valueOf(id1210)).equals(actual)){
			System.out.println("id1210_1 Pass");
		}else{
			System.out.println("id1210_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1210))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1210))+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void changeAllowanceDeductionInfo(WebDriver driver) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.allowanceDeductionSetting.xpath)).click();
		Thread.sleep(1000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
		//input the name
		driver.findElement(By.xpath(paymentStatements.allowanceName.xpath)).sendKeys("1");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name2.xpath)).sendKeys("2");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name3.xpath)).sendKeys("3");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name4.xpath)).sendKeys("4");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name5.xpath)).sendKeys("5");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name6.xpath)).sendKeys("6");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name7.xpath)).sendKeys("7");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name8.xpath)).sendKeys("8");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name9.xpath)).sendKeys("9");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name10.xpath)).sendKeys("10");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name11.xpath)).sendKeys("11");
		Thread.sleep(1000);
		//select the check box
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax7.xpath)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax8.xpath)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax9.xpath)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax10.xpath)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax11.xpath)).click();;
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
	}
	
	public static String addSimpleEmployeeInfo(WebDriver driver) throws Exception
	{
		helper = new TestHelper();
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'employee Information'  page
		new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'KO' button 
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		//set the 'Employee Code *' value 
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).sendKeys(employeeSetting.employeeCode.getDatas().get("common"));
		//set the 'Family name Kana' value 
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).sendKeys(employeeSetting.familyNameKana.getDatas().get("common"));
		//set the 'First name kana' value
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).sendKeys(employeeSetting.firstNameKana.getDatas().get("common"));
		//set the 'Family name' value
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).sendKeys(employeeSetting.familyName.getDatas().get("common"));
		//set the 'First name' value
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).sendKeys(employeeSetting.firstName.getDatas().get("common"));
		Thread.sleep(2000);
		//choose the select
		driver.findElement(By.xpath(employeeSetting.paymentUnit_Month.xpath)).click();
		Thread.sleep(2000);
		String xpath = employeeSetting.commuterType.xpath;
		String value = String.valueOf((int)(Math.random()*8+1));
		Select sel = new Select(driver.findElement(By.xpath(xpath)));
		sel.selectByValue(value);
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'Close' button
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		String result="";
		String list[] = {"100000","4100","6500","11300","16100","20900","24500","0"};
		for(int i=0;i<list.length;i++){
			if(String.valueOf(i+1).equals(value)){
				result = list[i];
			}
		}
		return result;
	}
}