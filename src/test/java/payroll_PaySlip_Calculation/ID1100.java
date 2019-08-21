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
 * Display the calculated value of social insurance sum amount except unemployment insurance.
 **/
public class ID1100 extends TestBase{
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
	}

	/**
	 * #1100 = #id660(health insurance amount) + #id670(nursing insurance amount) + #id680(social security amount) + #id690(pension funds amount) + #id890(adjustment amount of social insurance)
	 **/
	@Test
	public void id1100_1() throws Exception {
		int id660 = (int)(Math.random()*1000000);
		int id670 = (int)(Math.random()*1000000);
		int id680 = (int)(Math.random()*1000000);
		int id690 = (int)(Math.random()*1000000);
		int id890 = (int)(Math.random()*1000000);
		int id1100 = id660+id670+id680+id690+id890;
		
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
		Common.clear(driver,paymentStatements.socialInsurance.xpath);		
		driver.findElement(By.xpath(paymentStatements.socialInsurance.xpath)).sendKeys(String.valueOf(id890));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sum_social_insurance.xpath)).getText().equals(Common.formatNum(String.valueOf(id1100)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.sum_social_insurance.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(id1100)).equals(actual)){
			System.out.println("id1100_1 Pass");
		}else{
			System.out.println("id1100_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1100))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1100))+"\r\n"+
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
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(2000);
	}
}