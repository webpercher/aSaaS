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
 * #id441(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount) is inputted and set [#id441] to [#id130] having #id431(Ditto Tax Amount Refund Collected Month > Month). 
 **/
public class ID441 extends TestBase{
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
	}

	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "0" 
	 * If AnnualAdjustmentInformation#adjustmentMonth is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 * If AnnualAdjustmentInformation#adjustmentMonth is #id431(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_1_1_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("payroll");
		//(#id130(Salary/Allowance > Balance Tax Amount) of #id430(Ditto Tax Amount Refund Collected Month > Month)= 
		//(#id940(Balance Excessive or Deficit Amount) + #id990(Refund Amount Carried Over To Next Year) - #id1010(Collected Amount Carried Over To Next Year)) + #id440(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount) + #id441(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount)
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
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_12 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120_12));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//get value
		int id940 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceExcessiveOrDeficitAmount.xpath)).getText().replace(",",""));
		//calcu except value
		//int id130 = id940+id990-id1010+id440+id441 
		int id130 = id940+id990-id1010+id440+id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount12.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_12+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_1_1_1 Pass");
		}else{
			System.out.println("id441_1_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id440 = "+ id440+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120 = "+ id120+"\r\n"+
					"id990 = "+ id990+"\r\n"+
					"id120_12 = "+ id120_12+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"id940 = "+ id940+"\r\n"+
					"id1010 = "+ id1010);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "0" 
	 * If AnnualAdjustmentInformation#adjustmentMonth is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_1_1_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("payroll");
		//(#id130(Salary/Allowance > Balance Tax Amount) of #id430(Ditto Tax Amount Refund Collected Month > Month) = 
		//(#id940(Balance Excessive or Deficit Amount) + #id990(Refund Amount Carried Over To Next Year) - #id1010(Collected Amount Carried Over To Next Year)) + #id440(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount)
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 11;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 12;
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_12 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120_12));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//get value
		int id940 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceExcessiveOrDeficitAmount.xpath)).getText().replace(",",""));
		//calcu except value
		//int id130 = id940+id990-id1010+id440 
		int id130 = id940+id990-id1010+id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount12.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_12+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_1_1_2 Pass");
		}else{
			System.out.println("id441_1_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120 = "+ id120+"\r\n"+
					"id990 = "+ id990+"\r\n"+
					"id120_12 = "+ id120_12+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"id940 = "+ id940+"\r\n"+
					"id1010 = "+ id1010);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "0" 
	 * If AnnualAdjustmentInformation#adjustmentMonth is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_1_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("payroll");
		//Else
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 10;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 11;
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_11 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_11.xpath)).sendKeys(String.valueOf(id120_11));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//calcu except value
		//int id130 = id440 
		int id130 = id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount11.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_11+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_1_2_1 Pass");
		}else{
			System.out.println("id441_1_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120_11 = "+ id120_11+"\r\n"+
					"id130 = "+ id130+"\r\n");
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "1"  
	 **/
	@Test
	public void id441_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("bonus");
		//Else
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 10;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 11;
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_11 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_11.xpath)).sendKeys(String.valueOf(id120_11));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//calcu except value
		//int id130 = id440 
		int id130 = id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount11.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_11+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_2_1 Pass");
		}else{
			System.out.println("id441_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120_11 = "+ id120_11+"\r\n"+
					"id130 = "+ id130+"\r\n");
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "2"  
	 * If '12' is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 * If '12' is #id431(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_3_1_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("other");
		//(#id130(Salary/Allowance > Balance Tax Amount) of #id430(Ditto Tax Amount Refund Collected Month > Month)= 
		//(#id940(Balance Excessive or Deficit Amount) + #id990(Refund Amount Carried Over To Next Year) - #id1010(Collected Amount Carried Over To Next Year)) + #id440(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount) + #id441(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount)
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
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_12 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120_12));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//get value
		int id940 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceExcessiveOrDeficitAmount.xpath)).getText().replace(",",""));
		//calcu except value
		//int id130 = id940+id990-id1010+id440+id441 
		int id130 = id940+id990-id1010+id440+id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount12.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_12+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_3_1_1 Pass");
		}else{
			System.out.println("id441_3_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id440 = "+ id440+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120 = "+ id120+"\r\n"+
					"id990 = "+ id990+"\r\n"+
					"id120_12 = "+ id120_12+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"id940 = "+ id940+"\r\n"+
					"id1010 = "+ id1010);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "2"  
	 * If '12' is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_3_1_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("other");
		//(#id130(Salary/Allowance > Balance Tax Amount) of #id430(Ditto Tax Amount Refund Collected Month > Month) = 
		//(#id940(Balance Excessive or Deficit Amount) + #id990(Refund Amount Carried Over To Next Year) - #id1010(Collected Amount Carried Over To Next Year)) + #id440(Ditto Tax Amount Refund Collected Month > Refund Collected Tax Amount)
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 11;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 12;
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_12 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120_12));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//get value
		int id940 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceExcessiveOrDeficitAmount.xpath)).getText().replace(",",""));
		//calcu except value
		//int id130 = id940+id990-id1010+id440 
		int id130 = id940+id990-id1010+id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount12.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_12+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_3_1_2 Pass");
		}else{
			System.out.println("id441_3_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120 = "+ id120+"\r\n"+
					"id990 = "+ id990+"\r\n"+
					"id120_12 = "+ id120_12+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"id940 = "+ id940+"\r\n"+
					"id1010 = "+ id1010);
		}
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.cancelForAnnual.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If AnnualAdjustmentInformation#adjustmentType is "2" 
	 * If AnnualAdjustmentInformation#adjustmentMonth is #id430(Ditto Tax Amount Refund Collected Month > Month)
	 **/
	@Test
	public void id441_3_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		changeAdjType("other");
		//Else
		//input the value
		int id420 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.carriedOverAmountFromPreviousYear.xpath)).sendKeys(String.valueOf(id420));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)));
		//input the value
		int id430 = 10;
		int id440 = (int) (Math.random()*id420);
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth.xpath)).sendKeys(String.valueOf(id430));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount.xpath)).sendKeys(String.valueOf(id440));
		//input the value
		int id431 = 11;
		int id441 = id420-id440;
		driver.findElement(By.xpath(annualAdjustment.DittoTaxAmountRefundCollectedMonth2.xpath)).sendKeys(String.valueOf(id431));
		driver.findElement(By.xpath(annualAdjustment.RefundCollectedTaxAmount2.xpath)).sendKeys(String.valueOf(id441));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int value = (int) (Math.random()*1000000);
		int id120 = 0;
		if(Math.random()*10<5){
			id120 = value;
		}else{
			id120 = -value;
		}
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id990 = (int) (Math.random()*value);
		int id1010 = (int) (Math.random()*value);
		if(driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).isEnabled()){
			driver.findElement(By.xpath(annualAdjustment.refundAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id990));
			id1010 = 0;
		}else{
			driver.findElement(By.xpath(annualAdjustment.collectedAmountCarriedOverToNextYear.xpath)).sendKeys(String.valueOf(id1010));
			id990 = 0;
		}
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//input the value
		int id120_11 = (int) (Math.random()*100000);
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_11.xpath)).sendKeys(String.valueOf(id120_11));
		driver.findElement(By.xpath(annualAdjustment.paymentDate_1ExcessDeficit.xpath)).click();
		Thread.sleep(3000);
		//calcu except value
		//int id130 = id440 
		int id130 = id441;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount11.xpath)).getText().replace(",",""));
		//id140= (#id120 + #id130)
		int id140 = id120_11+id130; 
		//check the data
		if(id140==actual){
			System.out.println("id441_3_2_1 Pass");
		}else{
			System.out.println("id441_3_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id140+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id140+"\r\n"+
					"id420 = "+ id420+"\r\n"+
					"id441 = "+ id441+"\r\n"+
					"id120_11 = "+ id120_11+"\r\n"+
					"id130 = "+ id130+"\r\n");
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
}
