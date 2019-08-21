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
 * Calculate total amount of #id980(Refund Amount During Current Year)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000, show Calculation when any of #id930(Balance Excessive or Deficit Type) or #id970(Refund Amount) of #id990(Refund Amount Carried Over To Next Year) is altered
 **/
public class ID980 extends TestBase{
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
	 * If WithholdingIncomeTaxLedger#excessiveDeficitType is "0" and [WithholdingIncomeTaxLedger]]#carriedOverRefundAmount isn't "0" 
	 * #id980=(#id970(Refund Amount) - #id990(Refund Amount Carried Over To Next Year))
	 **/
	@Test
	public void id980_1() throws Exception {
		//id930
		changeID930("excessive");
		//get data
		int id970 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.refundAmount.xpath)).getText().replace(",",""));		
		int id990 = (int) (Math.random()*id970);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_2.xpath)).sendKeys(String.valueOf(id990));
		Thread.sleep(2000);
		//#id980=(#id970 - #id990)
		int id980 = id970-id990;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.refundAmountDuringCurrentYear.xpath)).getText().replace(",",""));
		//check the data
		if(id980==actual){
			System.out.println("id980_1 Pass");
		}else{
			System.out.println("id980_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id980+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id980 = "+ id980+"\r\n"+
					"id970 = "+ id970+"\r\n"+
					"id990 = "+ id990);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * If WithholdingIncomeTaxLedger#excessiveDeficitType is "0" and [WithholdingIncomeTaxLedger]]#carriedOverRefundAmount is "0" or ""  
	 * #id980=#id970(#id970(Refund Amount)
	 **/
	@Test
	public void id980_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//id930
		changeID930("excessive");
		//get data
		int id970 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.refundAmount.xpath)).getText().replace(",",""));		
		//#id980=#id970
		int id980 = id970;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.refundAmountDuringCurrentYear.xpath)).getText().replace(",",""));
		//check the data
		if(id980==actual){
			System.out.println("id980_2 Pass");
		}else{
			System.out.println("id980_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id980+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id980 = "+ id980+"\r\n"+
					"id970 = "+ id970);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * Else
	 * #id980=""
	 **/
	@Test
	public void id980_3() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//id930
		changeID930("deficit");
		String id980 = "";
		//get the value
		String actual = driver.findElement(By.xpath(annualAdjustment.refundAmountDuringCurrentYear.xpath)).getText().replace(",","");
		//check the data
		if(id980.equals(actual)){
			System.out.println("id980_3 Pass");
		}else{
			System.out.println("id980_3 Failed");
			throw new Exception("Error of calculation, expected: <["+id980+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id980 = "+ id980);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
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
		int calculatedTaxAmount = (int) (Math.random()*1000000);
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
}