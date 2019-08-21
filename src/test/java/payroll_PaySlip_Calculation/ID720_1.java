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
 * Display the Calculated amount of withholding tax amount.
 * If #paymentDate of the selected payroll month <= 2012/12/31
 **/
public class ID720_1 extends TestBase{
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
		Common.createCompanyClient(driver,"24");
		Common.addSimpleEmployeeInfo(driver);
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * If Employee#monthlyTableType is 'OTU'
	 **/
	@Test
	public void id720_1_() throws Exception {
		int id660 = (int)(Math.random()*10000000);
		int id670 = (int)(Math.random()*10000000);
		int id680 = (int)(Math.random()*10000000);
		int id690 = (int)(Math.random()*10000000);
		int id700 = (int)(Math.random()*10000000);
		int id890 = (int)(Math.random()*10000000);
		int sum = id660+id670+id680+id690+id700+id890;
		
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
			if(!driver.findElement(By.xpath(paymentStatements.totalSocialInsurance.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.totalSocialInsurance.xpath)).getText();
		//check the data
		org.junit.Assert.assertEquals(Common.formatNum(String.valueOf(sum)),actual);
		System.out.println("id710_1 Pass");
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
	
	public static void changeEmployeeInfo(WebDriver driver,String type) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		if(type=="OUT"){
			
		}else if(type=="KO"){
			
		}		
		
		
		
		
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(2000);
	}
	
	public static String changeCompanyInfo(WebDriver driver,String type) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		//change the tpye
		if(type=="general"){
			driver.findElement(By.xpath(clientSetting.specialPaymentPeriodFlag.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="construction"){
			driver.findElement(By.xpath(clientSetting.constructionPeriodFlag.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="other"){
			driver.findElement(By.xpath(clientSetting.otherPeriodFlag.xpath)).click();
			Thread.sleep(2000);			
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//get value
		String result = driver.findElement(By.xpath(clientSetting.socialSecurityOldPersonPaymentPr2_dis.xpath)).getText();
		Common.forwardToTopPage();
		return result;
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
		//set allowanceSetting
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit7.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit8.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit9.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit10.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit11.xpath)).click();
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
	}
}