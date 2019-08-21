package payroll_PaySlip_Calculation;


import org.junit.*;
import org.openqa.selenium.*;

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
 * Display the sum of deduction amount.
 **/
public class ID880 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	
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
		Common.addSimpleEmployeeInfo(driver);
		changeCompanyInfo(driver);
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * #id880 = #id710(social insurance sum amount)+#id720(income tax amount) + #id730(residence tax amount)+#id750(deduction1 amount)+#id770(deduction2 amount)+#id790(deduction3 amount)+#id810(deduction4 amount)+#id830(deduction5 amount)+#id850(deduction6 amount)+#id870(deduction7 amount)
	 **/
	@Test
	public void id880_1() throws Exception {
		int id660 = (int)(Math.random()*1000000);
		int id670 = (int)(Math.random()*1000000);
		int id680 = (int)(Math.random()*1000000);
		int id690 = (int)(Math.random()*1000000);
		int id700 = (int)(Math.random()*1000000);
		int id890 = (int)(Math.random()*1000000);
		int id710 = id660+id670+id680+id690+id700+id890;
		
		int id720 = (int)(Math.random()*1000000);
		int id730 = (int)(Math.random()*1000000);
		int id750 = (int)(Math.random()*1000000);
		int id770 = (int)(Math.random()*1000000);
		int id790 = (int)(Math.random()*1000000);
		int id810 = (int)(Math.random()*1000000);
		int id830 = (int)(Math.random()*1000000);
		int id850 = (int)(Math.random()*1000000);
		int id870 = (int)(Math.random()*1000000);
		
		int id880 =  id710+id720+id730+id750+id770+id790+id810+id830+id850+id870;
		//input the value
		Common.clear(driver,paymentStatements.healthInsurance.xpath);		
		driver.findElement(By.xpath(paymentStatements.healthInsurance.xpath)).sendKeys(String.valueOf(id660));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.careInsurance.xpath);		
		driver.findElement(By.xpath(paymentStatements.careInsurance.xpath)).sendKeys(String.valueOf(id670));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.employeePension.xpath);		
		driver.findElement(By.xpath(paymentStatements.employeePension.xpath)).sendKeys(String.valueOf(id680));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.welfarePensionFund.xpath);		
		driver.findElement(By.xpath(paymentStatements.welfarePensionFund.xpath)).sendKeys(String.valueOf(id690));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.employeeInsurance.xpath);		
		driver.findElement(By.xpath(paymentStatements.employeeInsurance.xpath)).sendKeys(String.valueOf(id700));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.socialInsurance.xpath);		
		driver.findElement(By.xpath(paymentStatements.socialInsurance.xpath)).sendKeys(String.valueOf(id890));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.totalSocialInsurance.xpath)).getText().equals(Common.formatNum(String.valueOf(id710)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//input the value
		Common.clear(driver,paymentStatements.tax.xpath);		
		driver.findElement(By.xpath(paymentStatements.tax.xpath)).sendKeys(String.valueOf(id720));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.residentTax.xpath);		
		driver.findElement(By.xpath(paymentStatements.residentTax.xpath)).sendKeys(String.valueOf(id730));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction1.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction1.xpath)).sendKeys(String.valueOf(id750));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction2.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction2.xpath)).sendKeys(String.valueOf(id770));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction3.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction3.xpath)).sendKeys(String.valueOf(id790));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction4.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction4.xpath)).sendKeys(String.valueOf(id810));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction5.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction5.xpath)).sendKeys(String.valueOf(id830));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction6.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction6.xpath)).sendKeys(String.valueOf(id850));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.payrollDeduction7.xpath);		
		driver.findElement(By.xpath(paymentStatements.payrollDeduction7.xpath)).sendKeys(String.valueOf(id870));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.totalDeduction.xpath)).getText().equals(Common.formatNum(String.valueOf(id880)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.totalDeduction.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(id880)).equals(actual)){
			System.out.println("id880_1 Pass");
		}else{
			System.out.println("id880_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id880))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id880))+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void changeCompanyInfo(WebDriver driver) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		while(!driver.findElement(By.xpath(clientSetting.weekdayNormal.xpath)).isDisplayed()){
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
			Thread.sleep(1000);	
		}
		if(!driver.findElement(By.xpath(clientSetting.pensionFoundAmount.xpath)).isSelected()){
			driver.findElement(By.xpath(clientSetting.pensionFoundAmount.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
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
		driver.findElement(By.xpath(paymentStatements.deduction.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
		//input the name
		driver.findElement(By.xpath(paymentStatements.deductionName.xpath)).sendKeys("1");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name2.xpath)).sendKeys("2");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name3.xpath)).sendKeys("3");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name4.xpath)).sendKeys("4");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name5.xpath)).sendKeys("5");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name6.xpath)).sendKeys("6");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.deductionName_Name7.xpath)).sendKeys("7");
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
}