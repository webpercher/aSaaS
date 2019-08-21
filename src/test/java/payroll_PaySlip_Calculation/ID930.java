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
 * Display the calculated value subtracted deduction amount from payroll.
 **/
public class ID930 extends TestBase{
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
	 * #id930= #id650(payment amount) - #id880(deduction sum amount) + #id900(adjustment1) + #id910(adjustment2) + #id920(annual adjustment)
	 **/
	@Test
	public void id930_1() throws Exception {
		int id390 = (int)(Math.random()*1000000);
		int id410 = (int)(Math.random()*1000000);
		int id430 = (int)(Math.random()*1000000);
		int id450 = (int)(Math.random()*1000000);
		int id470 = (int)(Math.random()*1000000);
		int id490 = (int)(Math.random()*1000000);
		int id510 = (int)(Math.random()*1000000);
		int id530 = (int)(Math.random()*1000000);
		int id550 = (int)(Math.random()*1000000);
		int id570 = (int)(Math.random()*1000000);
		int id590 = (int)(Math.random()*1000000);
		int id610 = (int)(Math.random()*1000000);
		int id620 = (int)(Math.random()*1000000);
		int id630 = (int)(Math.random()*1000000);
		int id640 = (int)(Math.random()*1000000);
		int id650 = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
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
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(id650)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		
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
		
		int id900 = (int)(Math.random()*1000000);
		int id910 = (int)(Math.random()*1000000);
		int id920 = (int)(Math.random()*1000000);
		
		int id930 = id650-id880+id900+id910+id920;
		//input the value
		Common.clear(driver,paymentStatements.adjustmentOne.xpath);		
		driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).sendKeys(String.valueOf(id900));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.adjustmentTwo.xpath);		
		driver.findElement(By.xpath(paymentStatements.adjustmentTwo.xpath)).sendKeys(String.valueOf(id910));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.annualAdjustmentAmount.xpath);		
		driver.findElement(By.xpath(paymentStatements.annualAdjustmentAmount.xpath)).sendKeys(String.valueOf(id920));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.lessPayment.xpath)).getText().equals(Common.formatNum(String.valueOf(id930)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.lessPayment.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(id930)).equals(actual)){
			System.out.println("id930_1 Pass");
		}else{
			System.out.println("id930_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id930))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id930))+"\r\n"+
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
		driver.findElement(By.xpath(paymentStatements.deduction.xpath)).click();
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