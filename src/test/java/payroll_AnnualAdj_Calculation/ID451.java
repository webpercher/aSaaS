package payroll_AnnualAdj_Calculation;


import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.AnnualAdjustment;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;


/**
 * Calculate total amount of #id451(Ditto Tax Amount Refund Collected Month > Balance Amount) 
 **/
public class ID451 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static AnnualAdjustment annualAdjustment;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		annualAdjustment = (AnnualAdjustment) helper.getPage("AnnualAdjustment");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
	}

	/**
	 * #id451=#id450 - #id441
	 **/
	@Test
	public void id451_1() throws Exception {
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 12;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 12;
		int id441 = (int) (Math.random()*(id420-id440));
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//get the value
		int id450 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.DittoRefundBalanceAmount.xpath)).getText().replace(",",""));
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.DittoRefundBalanceAmount2.xpath)).getText().replace(",",""));
		//#id451=#id450 - #id441
		int id451 = id450-id441; 
		//check the data
		if(id451==actual){
			System.out.println("id451_1 Pass");
		}else{
			System.out.println("id451_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id451+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id451 = "+ id451+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id440 = "+ id440+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id441 = "+ id441);
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
}
