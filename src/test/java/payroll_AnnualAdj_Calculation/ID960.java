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
 * Calculate total amount of #id960(Uncollected Tax Amount Of Unpaid)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and 
 * WithholdingIncomeTaxLedger#totalAmount =< 20,000,000, show Calculation when e#id930(ExcessiveDeficitAmountType) is altered
 **/
public class ID960 extends TestBase{
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
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
	}

	/**
	 * If WithholdingIncomeTaxLedger#excessiveDeficitType is "0" and #id410(Salary/Allowance > Balance Tax Amount) ＞ 0
	 **/
	@Test
	public void id960_1() throws Exception {
		//id930
		changeID930("excessive");
		//#id960=(#id410(Salary/Bonus > Owing Calculated Tax Amount))
		int id410 = (int) (Math.random()*10000000);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.owingCalculatedTaxAmount.xpath)).sendKeys(String.valueOf(id410));
		driver.findElement(By.xpath(annualAdjustment.owingTaxablePaymentAmount.xpath)).click();
		Thread.sleep(2000);
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.uncollectedTaxAmountOfUnpaid.xpath)).getText().replace(",",""));
		//#id960=(#id410(Salary/Bonus > Owing Calculated Tax Amount))
		int id960 = id410;
		//check the data
		if(id960==actual){
			System.out.println("id960_1 Pass");
		}else{
			System.out.println("id960_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id960+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id960 = "+ id960+"\r\n"+
					"id410 = "+ id410);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * Else
	 **/
	@Test
	public void id960_2() throws Exception {
		//id930
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeID930("deficit");
		//#id960=""
		int id410 = (int) (Math.random()*10000000);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.owingCalculatedTaxAmount.xpath)).sendKeys(String.valueOf(id410));
		driver.findElement(By.xpath(annualAdjustment.owingTaxablePaymentAmount.xpath)).click();
		Thread.sleep(2000);
		//get the value
		String actual = driver.findElement(By.xpath(annualAdjustment.uncollectedTaxAmountOfUnpaid.xpath)).getText().replace(",","");
		//#id960=""
		String id960 = "";
		//check the data
		if(id960.equals(actual)){
			System.out.println("id960_2 Pass");
		}else{
			System.out.println("id960_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id960+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id960 = "+ id960+"\r\n"+
					"id410 = "+ id410);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void changeID930(String type) throws Exception {
		int calculatedTaxAmount = (int) (Math.random()*10000000);
		if(type=="excessive"){
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(calculatedTaxAmount));
		}else if(type=="deficit"){
			calculatedTaxAmount = -calculatedTaxAmount;
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(calculatedTaxAmount));
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_2.xpath)).click();
		Thread.sleep(2000);
	}
}