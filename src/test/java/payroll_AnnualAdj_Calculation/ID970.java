package payroll_AnnualAdj_Calculation;

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
import com.asaas.test.util.AnnualAdjustment;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;


/**
 * Calculate total amount of #id970(Refund Amount)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000, show Calculation when at any of #id940(Balance Excessive or Deficit Amount) or #id950(Collected Tax Amount From Last Pay) or #id960(Uncollected Tax Amount Of Unpaid) is altered
 **/
public class ID970 extends TestBase{
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
	 * If WithholdingIncomeTaxLedger#excessiveDeficitAmount ＜ 0
	 * #id970=(-(#id940(Balance Excessive or Deficit Amount)) - #id950(Collected Tax Amount From Last Pay) - #id960(Uncollected Tax Amount Of Unpaid))
	 **/
	@Test
	public void id970_1() throws Exception {
		//id930
		changeID930("excessive");
		//id960
		inputID960();
		//【10】=#id120(Salary/Allowance > Calculated Tax Amount) of (0 ＝＝ AnnualAdjustmentInformationadjustmentTyp and #id69(n) ＝＝ AnnualAdjustmentInformation#adjustmentMonth)
		int id120 = (int) (Math.random()*1000000+1001);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_11.xpath)).click();
		Thread.sleep(2000);
		//id940
		int id940 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceExcessiveOrDeficitAmount.xpath)).getText().replace(",",""));
		int id960 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.uncollectedTaxAmountOfUnpaid.xpath)).getText().replace(",",""));		
		//#id950=【10】 - #id960(Uncollected Tax Amount Of Unpaid)
		int id950 = id120-1000;
		//#id970=(-(#id940) - #id950 - #id960)
		int id970 = (-id940)-id950-id960;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.refundAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id970==actual){
			System.out.println("id970_1 Pass");
		}else{
			System.out.println("id970_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id970+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id970 = "+ id970+"\r\n"+
					"id940 = "+ id940+"\r\n"+
					"id950 = "+ id950+"\r\n"+
					"id960 = "+ id960);
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void changeAdjType(String type) throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));		
		//change the type
		Select sel = new Select(driver.findElement(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		if(type=="payroll"){
			sel.selectByVisibleText(annualAdjustment.shortfallOverCheckoutType.getDatas().get("def"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(annualAdjustment.shortfallOverCheckoutValue.xpath)).click();
			driver.findElement(By.xpath(annualAdjustment.shortfallOverCheckoutValue.xpath)).sendKeys("12");
			Thread.sleep(2000);
		}else if(type=="bonus"){
			sel.selectByVisibleText(annualAdjustment.shortfallOverCheckoutType.getDatas().get("TS408"));
			Thread.sleep(2000);
			driver.findElement(By.xpath(annualAdjustment.shortfallOverCheckoutValue.xpath)).click();
			driver.findElement(By.xpath(annualAdjustment.shortfallOverCheckoutValue.xpath)).sendKeys("4");
			Thread.sleep(2000);
		}else if(type=="other"){
			sel.selectByVisibleText(annualAdjustment.shortfallOverCheckoutType.getDatas().get("TS408_2"));
			Thread.sleep(2000);
		}
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(3000);
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));		
	}
	
	public static void changeID930(String type) throws Exception {
		int calculatedTaxAmount = 1;
		if(type=="excessive"){
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).clear();
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(calculatedTaxAmount));
		}else if(type=="deficit"){
			calculatedTaxAmount = -calculatedTaxAmount;
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).clear();
			driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(calculatedTaxAmount));
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_2.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void inputID960() throws Exception {
		int id410 = 1000;
		driver.findElement(By.xpath(annualAdjustment.owingCalculatedTaxAmount.xpath)).clear();
		driver.findElement(By.xpath(annualAdjustment.owingCalculatedTaxAmount.xpath)).sendKeys(String.valueOf(id410));
		driver.findElement(By.xpath(annualAdjustment.owingTaxablePaymentAmount.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(3000);
	}
}