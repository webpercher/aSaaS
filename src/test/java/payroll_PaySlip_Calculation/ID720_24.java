package payroll_PaySlip_Calculation;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
 * Display the Calculated amount of withholding tax amount.
 * If #paymentDate of the selected payroll month <= 2012/12/31
 * V(amount subtracted social insurance amount) = #id390 
 * + ({sum of allowance amount that 'withholding income tax' of VoluntaryItemSetting#allowanceSetting is off amoung #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610. })
 * + (#id620 - {Employee#commutingAllowanceNoneTaxableAmountId -> CommutingAllowanceNoneTaxableAmount#noneTaxableAmount } ※ If the value =< 0, 0.)
 * + #id630 - #id640 - #id710
 **/
public class ID720_24 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static int noneTaxableAmount = 0;
	public static int dependentsNumber = 0;
	
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
		noneTaxableAmount = Integer.parseInt(addSimpleEmployeeInfo(driver));
		changeCompanyInfo(driver,"");
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * If Employee#monthlyTableType is 'OTU'
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is false
	 **/
	@Test
	public void id720_24_1_1() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"OTU","");
		int value = Integer.parseInt(String.valueOf(Math.random()*922000+88000).split("\\.")[0]);
		int otsuTaxAmount = Integer.parseInt(getCSVData("otsuTaxAmount",value));
		inputPayment(driver,value);	
		//#id720= (WithholdingTaxAmount#otuTaxAmount - Employee#dependentsNumber×1580) rounded (half up) to the nearest 10. If the result amount < 0, 0. 
		int id720 =  Math.round(otsuTaxAmount-dependentsNumber*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_1_1 Pass");
		}else{
			System.out.println("id720_24_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"value = "+ value+"\r\n"+
					"otsuTaxAmount = "+ otsuTaxAmount);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'OTU'
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 **/
	@Test
	public void id720_24_1_2_1() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"OTU","");
		int value = Integer.parseInt(String.valueOf(Math.random()*88000).split("\\.")[0]);
		double specialOtsuRate = Double.valueOf(getCSVData("specialOtsuRate",value));
		int V = inputPayment(driver,value);	
		////If V < 1,010,000 yen
		//result = ( V ×WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580)
		//#id720 = result rounded down after the decimal point. If the result amount < 0, 0. 
		int id720 =  (int)Math.floor(V*specialOtsuRate-dependentsNumber*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_1_2_1 Pass");
		}else{
			System.out.println("id720_24_1_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"value = "+ value+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialOtsuRate = "+ specialOtsuRate);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'OTU'
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 **/
	@Test
	public void id720_24_1_2_2() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"OTU","");
		int value = Integer.parseInt(String.valueOf(Math.random()*5000000+1010001).split("\\.")[0]);
		double specialOtsuRate = Double.valueOf(getCSVData("specialOtsuRate",value));
		double otsuTaxAmount = Double.valueOf(getCSVData("otsuTaxAmount",value));
		int V = inputPayment(driver,value);	
		//If V >= 1,010,000 yen
		//result =({ WithholdingTaxAmount#otuTaxAmount of (WithholdingTaxAmount#lowerBoundAmountIncluded =1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= WithholdingTaxAmount#endDate) }
		//+ ( V - 1010000) × WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580 )
		//#id720 = result rounded down after the decimal point. If the result amount < 0, 0. 
		int id720 =  (int)Math.floor(otsuTaxAmount+(V-1010000)*specialOtsuRate-dependentsNumber*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_1_2_2 Pass");
		}else{
			System.out.println("id720_24_1_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"otsuTaxAmount = "+ otsuTaxAmount+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"value = "+ value+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialOtsuRate = "+ specialOtsuRate);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber <= 7
	 **/
	@Test
	public void id720_24_2_1_1_1() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","less");
		int value = Integer.parseInt(String.valueOf(Math.random()*922000+88000).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		inputPayment(driver,value);	
		//#id720= { WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents of the record according to Employee#dependentsNumber }. If the result amount < 0, 0. 
		int id720 =  (int)(dependents);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_1_1 Pass");
		}else{
			System.out.println("id720_24_2_1_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber >=8
	 **/
	@Test
	public void id720_24_2_1_1_2() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","more");
		int value = Integer.parseInt(String.valueOf(Math.random()*922000+88000).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		inputPayment(driver,value);	
		//#id720= { WithholdingTaxAmount#koTaxAmountSevenDependents of the record } - (Employee#dependentsNumber - 7 )×1580. If the result amount < 0, 0. 
		int id720 =  (int)(dependents-(dependentsNumber-7)*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_1_2 Pass");
		}else{
			System.out.println("id720_24_2_1_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * #id720 = m rounded down after decimal point. If the result amount < 0, 0.
	 * If V > 1,760,000 yen
	 * Employee#dependentsNumber <= 7
	 **/
	@Test
	public void id720_24_2_1_2_1_1() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","less");
		int value = Integer.parseInt(String.valueOf(Math.random()*4000000+1760000).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		double specialKouRate = Double.valueOf(getCSVData("specialKouRate",value));
		int V = inputPayment(driver,value);	
		//m = {one of which is WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents at a record of (WithholdingTaxAmount#lowerBoundAmountIncluded=1760000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= WithholdingTaxAmount#endDate ) according to Employee#dependentsNumber. } 
		//+ ( V - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate 
		int id720 =  (int)(dependents+(V-1760000)*specialKouRate);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_2_1_1 Pass");
		}else{
			System.out.println("id720_24_2_1_2_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialKouRate = "+ specialKouRate+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * #id720 = m rounded down after decimal point. If the result amount < 0, 0.
	 * If V > 1,760,000 yen
	 * Employee#dependentsNumber >= 8
	 **/
	@Test
	public void id720_24_2_1_2_1_2() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","more");
		int value = Integer.parseInt(String.valueOf(Math.random()*4000000+1760000).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		double specialKouRate = Double.valueOf(getCSVData("specialKouRate",value));
		int V = inputPayment(driver,value);	
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents at record of ( WithholdingTaxAmount#lowerBoundAmountIncluded=1760000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= WithholdingTaxAmount#endDate ) }
		//+ ( V - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate
		//- (Employee#dependentsNumber - 7 ) × 1580 
		int id720 =  (int)(dependents+(V-1760000)*specialKouRate-(dependentsNumber-7)*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_2_1_2 Pass");
		}else{
			System.out.println("id720_24_2_1_2_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialKouRate = "+ specialKouRate+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * #id720 = m rounded down after decimal point. If the result amount < 0, 0.
	 * If V <1,760,000 yen
	 * Employee#dependentsNumber <= 7
	 **/
	@Test
	public void id720_24_2_1_2_2_1() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","less");
		int value = Integer.parseInt(String.valueOf(Math.random()*749999+1010001).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		double specialKouRate = Double.valueOf(getCSVData("specialKouRate",value));
		int V = inputPayment(driver,value);	
		//m = {one of which is WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents at record of ( WithholdingTaxAmount#lowerBoundAmountIncluded=1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= WithholdingTaxAmount#endDate) according to Employee#dependentsNumber.}
		//+ ( V - 1010000円 ) × WithholdingTaxAmount#koSpecialTaxRate
		int id720 =  (int)(dependents+(V-1010000)*specialKouRate);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_2_2_1 Pass");
		}else{
			System.out.println("id720_24_2_1_2_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialKouRate = "+ specialKouRate+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'monthlyAmountTable'
	 * Get a record according to { WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMoth <= WithholdingTaxAmount#endDate} and {WithholdingTaxAmount#lowerBoundAmountIncluded =< V < WithholdingTaxAmount#upperBoundAmountNotIncluded }
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * #id720 = m rounded down after decimal point. If the result amount < 0, 0.
	 * If V <1,760,000 yen
	 * If Employee#dependentsNumber >=8 
	 **/
	@Test
	public void id720_24_2_1_2_2_2() throws Exception {
		dependentsNumber = changeEmployeeInfo(driver,"KO","more");
		int value = Integer.parseInt(String.valueOf(Math.random()*749999+1010001).split("\\.")[0]);
		int dependents = Integer.parseInt(getCSVData("dependents"+dependentsNumber,value));
		double specialKouRate = Double.valueOf(getCSVData("specialKouRate",value));
		int V = inputPayment(driver,value);	
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents at record of ( WithholdingTaxAmount#lowerBoundAmountIncluded=1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= WithholdingTaxAmount#endDate) }
		//+ ( V - 1010000円 ) × WithholdingTaxAmount#koSpecialTaxRate
		//- ( Employee#dependentsNumber - 7 ) × 1580
		int id720 =  (int)(dependents+(V-1010000)*specialKouRate-(dependentsNumber-7)*1580);
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_1_2_2_2 Pass");
		}else{
			System.out.println("id720_24_2_1_2_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependents = "+ dependents+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"V = "+ V+"\r\n"+
					"specialKouRate = "+ specialKouRate+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#monthlyTableType is 'KO'
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is 'autoCaliculation'
	 * Get a record according to { SpecialCasesCalculatorIncomeExemption#startDate <= #paymentDate of the selected payrollMonth <= { SpecialCasesCalculatorIncomeExemption#end} and {SpecialCasesCalculatorIncomeExemption#lowerBoundAmountIncluded =< V <= upperBoundAmountIncluded}
	 **/
	@Test
	public void id720_24_2_2() throws Exception {
		changeCompanyInfo(driver,"autoCaliculationl");
		dependentsNumber = changeEmployeeInfo(driver,"KO","");
		int value = Integer.parseInt(String.valueOf(Math.random()*749999+1010001).split("\\.")[0]);
		double rateCoefficient = Double.valueOf(getIncomeCSVData("rate",value));
		double additionalValue = Double.valueOf(getIncomeCSVData("addend",value));
		int V = inputPayment(driver,value);	
		//W(income exemption amount) = Take the ceiling of ( V × SpecialCasesCalculatorIncomeExemption#rateCoefficient + SpecialCasesCalculatorIncomeExemption#additionalValue) to the nearest whole number.
		int W = Integer.parseInt(String.valueOf(Math.ceil((V*rateCoefficient+additionalValue))).split("\\.")[0]);
		//X(personal exemption amount) = (Employee#dependentsNumber + 1) × SpecialCasesCalculatorPersonalExemption#basicExemptionAmount
		//Get a record according to {SpecialCasesCalculatorTaxAmount#startDate <= #paymentDate of the selected payrollMonth <= SpecialCasesCalculatorTaxAmount#endDate} and {SpecialCasesCalculatorTaxAmount#lowerBoundAmountIncluded =< V - W - X =< SpecialCasesCalculatorTaxAmount#upperBoundAmountIncluded}		
		int X = (dependentsNumber+1)*31667;
		//#id720 =( ( V - W - X ) × SpecialCasesCalculatorTaxAmount#rateCoefficient + SpecialCasesCalculatorTaxAmount#additionalValue ) rounded (half up) to the nearest 10. If the result amount < 0, 0.
		double rateCoefficient_720 = Double.valueOf(getTaxCSVData("rate",V-W-X));
		double additionalValue_720 = Double.valueOf(getTaxCSVData("addend",V-W-X));
		int id720 =  Integer.parseInt(String.valueOf((Math.round((Double.valueOf(String.valueOf((V-W-X)*rateCoefficient_720+additionalValue_720).split("\\.")[0])/10))*10)));
		if(id720<0){
			id720 = 0;
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.tax.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(id720)).equals(actual)){
			System.out.println("id720_24_2_2 Pass");
		}else{
			System.out.println("id720_24_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"id720 = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"dependentsNumber = "+ dependentsNumber+"\r\n"+
					"V = "+ V+"\r\n"+
					"W = "+ W+"\r\n"+
					"X = "+ X+"\r\n"+
					"rateCoefficient = "+ rateCoefficient+"\r\n"+
					"additionalValue = "+ additionalValue+"\r\n"+
					"rateCoefficient_720 = "+ rateCoefficient_720+"\r\n"+
					"additionalValue_720 = "+ additionalValue_720+"\r\n"+
					"value = "+ value);
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
	
	public static int changeEmployeeInfo(WebDriver driver,String type,String dependent) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		if(type=="OTU"){
			driver.findElement(By.xpath(employeeSetting.monthlyTableType.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="KO"){
			driver.findElement(By.xpath(employeeSetting.koMonthlyTable.xpath)).click();
			Thread.sleep(2000);
		}		
		int dependentsNumber = 0;
		if(dependent.contains("less")){
			dependentsNumber = (int)(Math.random()*7+1);
		}else if(dependent.contains("more")){
			dependentsNumber = (int)(Math.random()*2+8); 	
		}else{
			dependentsNumber = (int)(Math.random()*9+1);
		}
		Common.clear(driver,employeeSetting.dependentsNumber.xpath);
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(String.valueOf(dependentsNumber));
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		return dependentsNumber;
	}
	
	public static void changeCompanyInfo(WebDriver driver,String type) throws Exception{
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
		if(type.equals("autoCaliculationl")){
			driver.findElement(By.xpath(clientSetting.specialEquipmentCalc.xpath)).click();
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
		//set allowanceSetting
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax6.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax7.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax8.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax9.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax10.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.withholdingIncomeTax11.xpath)).click();
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
	}
	
	public static int inputPayment(WebDriver driver,int value) throws Exception{
		int id390 = value;
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
		
		int id620_after = 0;
		if(!(id620-noneTaxableAmount>0)){
			id620_after = 0;
		}else{
			id620_after = id620-noneTaxableAmount;
		}
		
		int id640 = id410;
		int id660 = id430;
		int id670 = id450;
		int id680 = id470;
		int id690 = id490;
		int id700 = id620_after;
		int id890 = id630;
		int id710 = id660+id670+id680+id690+id700+id890;
		
		int V = id390+id410+id430+id450+id470+id490+id620_after+id630-id640-id710;
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
		//input the name
		Common.clear(driver,paymentStatements.baseSalary.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.allowanceName1.xpath);
		driver.findElement(By.xpath(paymentStatements.allowanceName1.xpath)).sendKeys(String.valueOf(id410));
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
		//update the data
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.totalSocialInsurance.xpath)).getText().equals(Common.formatNum(String.valueOf(id710)))){
				driver.findElement(By.xpath(paymentStatements.adjustmentOne.xpath)).click();
				Thread.sleep(1000);
			}
		}
		return V;
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
	
	public static String getCSVData(String key,int value) throws Exception
	{
		if(dependentsNumber>7&&key.contains("dependents")){
			key = "dependents7";
		}
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "WithholdingTaxAmount24.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		//get max account of one line 
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for(int i =0;i<item.length;i++){
			if(item[i].equals(key)){
				num = i;
			}
		}
		String result = "";
		if(value>1010000){
			if(key.equals("otsuTaxAmount")){
				result = "367400";
				return result;
			}else if(key.equals("specialOtsuRate")){
				result = "0.38";
				return result;
			}else if(key.contains(("dependents"))){
				if(value<1760000){
					value = 1010000;					
				}else{
					value = 1760000;
				}
			}else if(key.equals("specialKouRate")){
				if(value<1760000){
					result = "0.3150";
					return result;					
				}else{
					result = "0.38";
					return result;
				}
			}
		}
			line = reader.readLine();
			int amountOver;
			int amountUnder;
			while ((line) != null) {
				amountOver = Integer.parseInt(line.split(",")[2]);
				amountUnder = Integer.parseInt(line.split(",")[3]);
				if(amountOver<=value&&value<amountUnder){
					result = line.split(",")[num];
					return result;
				}
				line = reader.readLine();
			}
			return result;
	}
	
	public static String getIncomeCSVData(String key,int value) throws Exception
	{
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "SpecialCasesCalculatorIncomeExemption24.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		//get max account of one line 
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for(int i =0;i<item.length;i++){
			if(item[i].equals(key)){
				num = i;
			}
		}
		String result = "";
		if(value>833334){
			if(key.equals("rate")){
				result = "0.05";
			}else if(key.equals("addend")){
				result = "141667";
			}
		}else{
			line = reader.readLine();
			int amountOver;
			int amountUnder;
			while ((line) != null) {
				amountOver = Integer.parseInt(line.split(",")[2]);
				amountUnder = Integer.parseInt(line.split(",")[3]);
				if(amountOver<=value&&value<=amountUnder){
					result = line.split(",")[num];
					return result;
				}
				line = reader.readLine();
			}
		}
		return result;
	}
	
	public static String getTaxCSVData(String key,int value) throws Exception
	{
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "SpecialCasesCalculatorTaxAmount24.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		//get max account of one line 
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for(int i =0;i<item.length;i++){
			if(item[i].equals(key)){
				num = i;
			}
		}
		String result = "";
		if(value>1500001){
			if(key.equals("rate")){
				result = "0.4";
			}else if(key.equals("addend")){
				result = "-233000";
			}
		}else{
			line = reader.readLine();
			int amountOver;
			int amountUnder;
			while ((line) != null) {
				amountOver = Integer.parseInt(line.split(",")[2]);
				amountUnder = Integer.parseInt(line.split(",")[3]);
				if(amountOver<=value&&value<=amountUnder){
					result = line.split(",")[num];
					return result;
				}
				line = reader.readLine();
			}
		}
		return result;
	}
}