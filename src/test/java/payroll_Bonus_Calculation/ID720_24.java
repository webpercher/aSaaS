package payroll_Bonus_Calculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.BonusPaymentStatements;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
/**
 * Calculate the withholding tax amount to be collected from each employee.
 * If #paymentDate of the selected payrollBonus <= 2012/12/31
 */
public class ID720_24 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static EmployeeSettingPage employeeSetting;
	private static PaymentStatements paymentStatement;
	private static BonusPaymentStatements bonusPayment;
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		paymentStatement = (PaymentStatements) helper.getPage("PaymentStatements");
		bonusPayment = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		Thread.sleep(4000);
		//clean environment
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		//create company
		Common.createCompanyClient(driver, clientSetting.currentYear.getDatas().get("TS11265"));	
		//add EmployeeInfo
		Common.addSimpleEmployeeInfo(driver);	
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting, "2", "12");
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		//add subsidies type
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		ID650.addSubsidiesType(driver, bonusPayment);
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B <= #id1300 × 10
	 * #id720 = ( B × #id1310) rounded down to the nearest yen (i.e. 1000.1 gets rounded to 1000). If the result amount < 0, 0.
	 */
	@Test
	public void id720_24_1() throws Exception {
		//input value and get B
		int B = getB(	inputValues());
		//If B <= #id1300 × 10
		int id1300 = getID1300(">=", (B/10)+1);
		//get id1310
		double id1310 = 0.0;
		id1310 = Double.valueOf(getID1310(driver, id1300)) * 0.01;
		//#id720 = ( B × #id1310) rounded down to the nearest yen (i.e. 1000.1 gets rounded to 1000).
		int id720 = (int)(B * id1310);
		//If the result amount < 0, 0.
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("ID720_24_1 Pass");
		}else {
			System.out.println("ID720_24_1 Failed");
			System.out.println("B = " + B + "\n" + "id1300 = " + id1300 + "\n" + "id1310 = " + id1310 + "\n" + "Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}	
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is false
	 */
	@Test
	public void id720_24_2_1() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		int id390 = (int)((Math.random()) * (6100000 - 540000)) + 540000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//C = (WithholdingTaxAmount#otuTaxAmount - Employee#dependentsNumber×1580) rounded off to the nearest ten
		int otsuTaxAmount = Integer.parseInt(getCSVData("otsuTaxAmount", V));
		int C = otsuTaxAmount - (dependentNumber * 1580);
		//#id720 = C × A . If the result amount < 0, 0.
		int id720 = C * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("ID720_24_2_1 Pass");
		}else {
			System.out.println("ID720_24_2_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "C = " + C + "\n" + "dependentNumber = " + dependentNumber);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 * If V < 1,010,000 yen
	 */
	@Test
	public void id720_24_2_2_1() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		int id390 = (int)((Math.random()) * 528000);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//C = ( V ×WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580)
		double specialOtsuRate = Double.parseDouble(getCSVData("specialOtsuRate", V));
		int C =(int)(Math.round((V * specialOtsuRate) - (dependentNumber * 1580)));
		//#id720 = C × A . If the result amount < 0, 0.
		int id720 = C * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_2_2_1 Pass");
		}else {
			System.out.println("id720_24_2_2_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "C = " + C + "\n" + "dependentNumber = " + dependentNumber);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 * If V >= 1,010,000 yen
	 */
	@Test
	public void id720_24_2_2_2() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		int id390 = (int)((Math.random()) * 10000000) + 6100000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//C = ({WithholdingTaxAmount#otuTaxAmount which WithholdingTaxAmount#lowerBoundAmountIncluded =1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//     + ( V - 1010000) × WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580 )
		double specialOtsuRate = Double.parseDouble(getCSVData("specialOtsuRate", V));
		double otsuTaxAmount = Double.parseDouble(getCSVData("otsuTaxAmount", 1010000));
		int C = (int) Math.round(otsuTaxAmount + ((V - 1010000) * specialOtsuRate) - (dependentNumber * 1580));
		//#id720 = C × A . If the result amount < 0, 0.
		int id720 = C * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_2_2_2 Pass");
		}else {
			System.out.println("id720_24_2_2_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "C = " + C  + "\n" + "dependentNumber = " + dependentNumber);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_3_1_1_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// select withholdingIncomeTaxCalculationType is "monthlyAmountTable"
		selectTaxCalculationType(driver, clientSetting, "monthlyAmountTable");
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * 6100000);
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//D = {the amount that corresponds to the number of dependents of the acquired record WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents}
		int D = Integer.parseInt(getCSVData("dependents", V, dependentNumber));
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_1_1 Pass");
		}else {
			System.out.println("id720_24_3_1_1_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "D = " + D + "\n" +"dependentNumber = " + dependentNumber);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_3_1_1_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * 6100000);
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//D = WithholdingTaxAmount#koTaxAmountSevenDependents - (Employee#dependentsNumber - 7 )×1580
		int D = Integer.parseInt(getCSVData("dependents", V, 7)) - ((dependentNumber - 7)*1580);
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_1_2 Pass");
		}else {
			System.out.println("id720_24_3_1_1_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "D = " + D + "dependentNumber = " + dependentNumber + "\n");
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V > 1,760,000 yen
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_3_1_2_1_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * 10000000) + 10700000;
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//m = {WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents corresponding to the dependents number in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1760000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate} 
		//       + ( V - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate
		double specialKouRate = Double.parseDouble(getCSVData("specialKouRate", V, dependentNumber));
		double dependents = Double.parseDouble(getCSVData("dependents", 1760000, dependentNumber));
		double m = dependents + (V - 1760000)*specialKouRate;
		//D = m rounded down after decimal point.
		int D = (int)m;
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_2_1_1 Pass");
		}else {
			System.out.println("id720_24_3_1_2_1_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "dependentNumber = " + dependentNumber + "\n" + "D = " + D);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V > 1,760,000 yen
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_3_1_2_1_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * 100000000) + 10700000;
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1760000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//      + ( V - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate - (Employee#dependentsNumber - 7 ) × 1580
		double specialKouRate = Double.parseDouble(getCSVData("specialKouRate", V, dependentNumber));
		double dependents = Double.parseDouble(getCSVData("dependents", 1760000, dependentNumber));
		double m = dependents + (V - 1760000)*specialKouRate - (dependentNumber - 7)*1580;
		//D = m rounded down after decimal point.
		int D = (int)m;
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_2_1_2 Pass");
		}else {
			System.out.println("id720_24_3_1_2_1_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "dependentNumber = " + dependentNumber + "\n" + "D = " + D);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V <1,760,000 yen
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_3_1_2_2_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * (10700000 - 6100000)) + 6100000;
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//m = {WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents corresponding to the dependents number in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//      + ( V - 1010000 yen ) × WithholdingTaxAmount#koSpecialTaxRate
		double specialKouRate = Double.parseDouble(getCSVData("specialKouRate", V, dependentNumber));
		double dependents = Double.parseDouble(getCSVData("dependents", 1010000, dependentNumber));
		double m = dependents + (V - 1010000)*specialKouRate;
		//D = m rounded down after decimal point.
		int D = (int)m;
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_2_2_1 Pass");
		}else {
			System.out.println("id720_24_3_1_2_2_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "dependentNumber = " + dependentNumber + "\n" + "D = " + D);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V <1,760,000 yen
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_3_1_2_2_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int id390 = (int)((Math.random()) * (10700000 - 6100000)) + 6100000;
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//      + ( V - 1010000 yen ) × WithholdingTaxAmount#koSpecialTaxRate - ( Employee#dependentsNumber - 7 ) × 1580
		double specialKouRate = Double.parseDouble(getCSVData("specialKouRate", 1010000, dependentNumber));
		double dependents = Double.parseDouble(getCSVData("dependents", 1010000, dependentNumber));
		double m = dependents + (V - 1010000)*specialKouRate - (dependentNumber - 7)*1580;
		//D = m rounded down after decimal point.
		int D = (int)m;
		//#id720 = D × A . If the result amount < 0, 0.
		int id720 = D * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_1_2_2_2 Pass");
		}else {
			System.out.println("id720_24_3_1_2_2_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "dependentNumber = " + dependentNumber + "\n" + "D = " + D);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If #id1300<=0, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "autoCaliculation"
	 */
	@Test
	public void id720_24_3_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// select withholdingIncomeTaxCalculationType is "autoCaliculation"	
		selectTaxCalculationType(driver, clientSetting, "autoCaliculation");
		// input value
		int B = getB(inputValues());
		Thread.sleep(5000);
		//W(income deduction amount) = ( B × SpecialCasesCalculatorIncomeExemption#rateCoefficient 
		//		+ SpecialCasesCalculatorIncomeExemption#additionalValue) rounded up to the nearest integer
		int W = (int)Math.floor(B * Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorIncomeExemption", "rate", B))
					+ Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorIncomeExemption", "addend", B)));
		//X((personal deduction amount) = (Employee#dependentsNumber + 1) × SpecialCasesCalculatorPersonalExemption#basicExemptionAmount
		int X = (dependentNumber + 1) * 31667;
		//#id720 =( ( B - W - X ) × SpecialCasesCalculatorTaxAmount#rateCoefficient + SpecialCasesCalculatorTaxAmount#additionalValue ) rounded half up to the nearest ten. 
		//If the result amount < 0, 0.
		int BWX = B - W -X;
		double id720Dou = BWX * Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorTaxAmount", "rate", BWX))
									  + Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorTaxAmount", "addend", BWX));
		int id720 = (int) (Math.round(id720Dou/10) * 10);
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_3_2 Pass");
		}else {
			System.out.println("id720_24_3_2 Failed");
			System.out.println("B = " + B + "\n" + "W = " + W + "\n" + "X = " + X + "\n" + "dependentNumber = " + dependentNumber );
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is false
	 */
	@Test
	public void id720_24_4_1() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		// select withholdingIncomeTaxCalculationType is "monthlyAmountTable"	
		selectTaxCalculationType(driver, clientSetting, "monthlyAmountTable");
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * (3850000 - 540000)) + 540000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//E = (WithholdingTaxAmount#otuTaxAmount - Employee#dependentsNumber×1580) rounded off to the nearest ten
		int E = Integer.parseInt(getCSVData("otsuTaxAmount", V + id1300)) - (dependentNumber * 1580);
		//#id720 = ( E - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (E - incomeTaxAmount) * A;
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_4_1 Pass");
		}else {
			System.out.println("id720_24_4_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "E = " + E);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 * If ( V + #id1300 ) < 1,010,000 yen
	 */
	@Test
	public void id720_24_4_2_1() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * 330000);
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//e = ( ( V + #id1300 ) ×WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580)
		double e = ((V + id1300) * Double.parseDouble(getCSVData("specialOtsuRate", V + id1300)) - (dependentNumber * 1580));
		//E = e rounded down after decimal point.
		int E = (int) Math.round(e);
		// #id720 = ( E - PaySlip#incomeTaxAmount of
		// PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (E - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_4_2_1 Pass");
		} else {
			System.out.println("id720_24_4_2_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = "+ V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n"+ "incomeTaxAmount = " + incomeTaxAmount + "\n" + "E = " + E);
			System.out.println("Theoretical value = " + id720 + "\n"+ "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+ Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = "+ Common.formatNum(String.valueOf(id720)) + "\r\n"+ "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "OTU" 
	 * If WithholdingTaxAmount#otuSpecialCalculationFlag is true
	 * If ( V + #id1300 ) >= 1,010,000 yen
	 */
	@Test
	public void id720_24_4_2_2() throws Exception {
		// select monthlyTableType is "OTU" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "OTU", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * 1000000) + 6100000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//e =({ WithholdingTaxAmount#otuTaxAmount which WithholdingTaxAmount#lowerBoundAmountIncluded =1010000 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//     + ( ( V + #id1300 ) - 1010000) × WithholdingTaxAmount#otuSpecialTaxRate - Employee#dependentsNumber×1580 )
		double e = (Integer.parseInt(getCSVData("otsuTaxAmount", 1010000)))
					+ ((V + id1300 - 1010000) * Double.parseDouble(getCSVData("specialOtsuRate", V + id1300)) - (dependentNumber * 1580));
		//E = e rounded down after decimal point.
		int E = (int) Math.round(e);
		// #id720 = ( E - PaySlip#incomeTaxAmount of
		// PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (E - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_4_2_2 Pass");
		} else {
			System.out.println("id720_24_4_2_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = "+ V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "E = " + E);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_5_1_1_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * (3850000 - 540000)) + 540000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//F = {the amount corresponding to the number of dependents in the acquired record WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents}
		int F = Integer.parseInt(getCSVData("dependents", V + id1300, dependentNumber));
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_1_1 Pass");
		} else {
			System.out.println("id720_24_5_1_1_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is false
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_5_1_1_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * (3850000 - 540000)) + 540000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//F = WithholdingTaxAmount#koTaxAmountSevenDependents - (Employee#dependentsNumber - 7 )×1580
		int F = Integer.parseInt(getCSVData("dependents", V + id1300, 7)) - ((dependentNumber - 7)*1580);
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_1_2 Pass");
		} else {
			System.out.println("id720_24_5_1_1_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If ( V + #id1300) > 1,760,000 yen
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_5_1_2_1_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * 1000000) + 10700000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//	m = {WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents corresponding to the dependents number in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1760000
		//       and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate} 
		//       + ( ( V + #id1300) - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate
		double m = Integer.parseInt(getCSVData("dependents", 1760000, dependentNumber)) 
						   + ((V + id1300 -1760000)*Double.parseDouble(getCSVData("specialKouRate", 1760000, dependentNumber)));
		//F = m rounded down after decimal point.
		int F = (int)m;
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_2_1_1 Pass");
		} else {
			System.out.println("id720_24_5_1_2_1_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If ( V + #id1300) > 1,760,000 yen
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_5_1_2_1_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * 1000000) + 10700000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1760000
		//			and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//			+ (( V + #id1300) - 1760000 ) × WithholdingTaxAmount#koSpecialTaxRate
		//			- (Employee#dependentsNumber - 7 ) × 1580
		double m = Integer.parseInt(getCSVData("dependents", 1760000, 7)) + ((V + id1300 -1760000)*Double.parseDouble(getCSVData("specialKouRate", 1760000, dependentNumber))) 
						- ((dependentNumber - 7)*1580);
		//F = m rounded down after decimal point.
		int F = (int)m;
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_2_1_2 Pass");
		} else {
			System.out.println("id720_24_5_1_2_1_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V <1,760,000 yen
	 * If Employee#dependentsNumber is 7 or less
	 */
	@Test
	public void id720_24_5_1_2_2_1() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 8);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * (10700000 - 6100000)) + 6100000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", (1760000 - V) < B/10 ? (1760000 - V) : B/10);
		//	m = {WithholdingTaxAmount#koTaxAmountNoDependent 〜 #koTaxAmountSevenDependents corresponding to the dependents number in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1010000
		//		and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//		+ ( ( V + #id1300) - 1010000 yen ) × WithholdingTaxAmount#koSpecialTaxRate
		double m = Integer.parseInt(getCSVData("dependents", 1010000, dependentNumber)) 
						   + ((V + id1300 -1010000)*Double.parseDouble(getCSVData("specialKouRate", V, dependentNumber)));
		//F = m rounded down after decimal point.190177
		int F = (int)m;
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_2_2_1 Pass");
		} else {
			System.out.println("id720_24_5_1_2_2_1 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "monthlyAmountTable" 
	 * If WithholdingTaxAmount#koSpecialCalculationFlag is true
	 * If V <1,760,000 yen
	 * If Employee#dependentsNumber is 8 or more
	 */
	@Test
	public void id720_24_5_1_2_2_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 3) + 8;
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// input incomeTaxAmount 
		int incomeTaxAmount = setIncomeTaxAmount();
		int id390 = (int)((Math.random()) * (10700000 - 6100000)) + 6100000;
		//V(the amount after the deduction of social insurance premiums, etc.) = ( B / A ) rounded down to the nearest yen (i.e. 1000.8 gets rounded to 1000)
		int B = getB(inputValues(id390));
		int A = getA();
		int V = B/A;
		//If B > #id1300×10
		int id1300 = getID1300("<", (1760000 - V) < B/10 ? (1760000 - V) : B/10);
		//m = {WithholdingTaxAmount#koTaxAmountSevenDependents in the record of which WithholdingTaxAmount#lowerBoundAmountIncluded=1010000
		//		 and WithholdingTaxAmount#startDate <= #paymentDate of the selected payrollBonus <= WithholdingTaxAmount#endDate}
		//		+ ( ( V + #id1300) - 1010000 yen ) × WithholdingTaxAmount#koSpecialTaxRate
		//		- ( Employee#dependentsNumber - 7 ) × 1580
		double m = Integer.parseInt(getCSVData("dependents", 1010000, 7)) + ((V + id1300 -1010000)*Double.parseDouble(getCSVData("specialKouRate", 1010000, dependentNumber))) 
						- ((dependentNumber - 7)*1580);
		//F = m rounded down after decimal point.
		int F = (int)m;
		//#id720 = ( F - PaySlip#incomeTaxAmount of PayrollBonus#targetPayrollMonth) × A . If the result amount < 0, 0.
		int id720 = (F - incomeTaxAmount) * A;
		if (id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_1_2_2_2 Pass");
		} else {
			System.out.println("id720_24_5_1_2_2_2 Failed");
			System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "V = " + V + "\n" + "id1300 = " + id1300 + "\n"
					+ "dependentNumber = " + dependentNumber + "\n" + "incomeTaxAmount = " + incomeTaxAmount + "\n" + "F = " + F);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <[" + Common.formatNum(String.valueOf(id720))
					+ "]> but was: <[" + id720Str + "]> , " + "\r\n" + "Please refer to input and output values below: "
					+ "\r\n" + "expected = " + Common.formatNum(String.valueOf(id720)) + "\r\n" + "actual = " + id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If B > #id1300×10, and also Employee#monthlyTableType is "KO"
	 * If PayrollCalculationSetting#withholdingIncomeTaxCalculationType is "autoCaliculation"
	 */
	@Test
	public void id720_24_5_2() throws Exception {
		// select monthlyTableType is "KO" and input dependentNumber
		int dependentNumber = (int)(Math.random() * 10);
		addEmployeeInfo(driver, employeeSetting, "KO", dependentNumber);
		// select withholdingIncomeTaxCalculationType is "autoCaliculation"
		selectTaxCalculationType(driver, clientSetting, "autoCaliculation");
		// input value
		int B = getB(inputValues());
		Thread.sleep(5000);
		//If B > #id1300×10
		int id1300 = getID1300("<", B/10);
		//W(income deduction amount) = ( B × SpecialCasesCalculatorIncomeExemption#rateCoefficient
		//				+ SpecialCasesCalculatorIncomeExemption#additionalValue) rounded up to the nearest integer
		int W = (int)Math.ceil(B * Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorIncomeExemption", "rate", B))
					+ Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorIncomeExemption", "addend", B)));
		//X(personal deduction amount) = (Employee#dependentsNumber + 1) × SpecialCasesCalculatorPersonalExemption#basicExemptionAmount
		int X = (dependentNumber + 1) * 31667;
		//#id720 =( ( B - W - X ) × SpecialCasesCalculatorTaxAmount#rateCoefficient + SpecialCasesCalculatorTaxAmount#additionalValue ) rounded half up to the nearest ten. 
		//If the result amount < 0, 0.
		int BWX = B - W -X;
		double id720Dou = BWX * Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorTaxAmount", "rate", BWX))
									  + Double.parseDouble(getCSVSpecial("SpecialCasesCalculatorTaxAmount", "addend", BWX));
		int id720 = (int) (Math.round(id720Dou/10) * 10);
		if(id720 < 0) {
			id720 = 0;
		}
		for (int i = 0; i < 30; i++) {
			if (!driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id720)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id720)).equals(id720Str)) {
			System.out.println("id720_24_5_2 Pass");
		}else {
			System.out.println("id720_24_5_2 Failed");
			System.out.println("B = " + B + "\n" + "W = " + W + "\n" + "X = " + X + "\n" + "id1300 = " + id1300 + "\n" + "dependentNumber = " + dependentNumber);
			System.out.println("Theoretical value = " + id720 + "\n" + "Actual value = " + id720Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id720))+"]> but was: <["+id720Str+"]> , " + "\r\n" +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id720))+"\r\n"+
					"actual = "+ id720Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//clean environment
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);	
	}
	
	private static int setIncomeTaxAmount() throws Exception {
		//for to page PaySlip
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(paymentStatement.edit.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(paymentStatement.edit.xpath)).click();
		}
		Common.isNotPending();
		Thread.sleep(1000);
		int incomeTaxAmount = (int) (Math.random() * 1000000);
		Common.clear(driver, paymentStatement.tax.xpath);
		driver.findElement(By.xpath(paymentStatement.tax.xpath)).sendKeys(String.valueOf(incomeTaxAmount));
		Thread.sleep(1000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElementValue(By.xpath(paymentStatement.tax.xpath),Common.formatNum(String.valueOf(incomeTaxAmount))));
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(paymentStatement.save.xpath)).click();
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatement.close.xpath)).click();
		
		return incomeTaxAmount;
	}
	
	private static int inputValues(int id390) throws Exception {
		//for to page BonusPaymentStatements
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		// set value
		for(int i = 0; i < 5; i++) {			
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(2000);
		for(int i = 0; i < 5; i++) {
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
		}
		String id650Str = driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText();
		return Integer.parseInt(id650Str.replace(",", ""));
	}
	
	private static int inputValues() throws Exception {
		//for to page BonusPaymentStatements
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		int id390 = (int) (Math.random()*100000000);
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		int id650 = id390 + id410 + id430 + id450 + id470 + id490 + id510 + id530 + id550 + id570 + id590 + id610;
		// set value
		for(int i = 0; i < 5; i++) {			
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction2.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction3.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction4.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction5.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction6.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction7.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction8.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction9.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction10.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction11.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		for(int i = 0; i < 30; i++) {
			if(!driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText().equals(Common.formatNum(String.valueOf(id650)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id650Str = driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText();
		return Integer.parseInt(id650Str.replace(",", ""));
	}
	
	private static int getA() throws Exception {
		Select select = new Select(driver.findElement(By.xpath(bonusPayment.calculationBasePeriod.xpath)));
		if(select.getFirstSelectedOption().getText().equals(bonusPayment.calculationBasePeriod.getDatas().get("def"))) {
			return 6;
		}else if(select.getFirstSelectedOption().getText().equals(bonusPayment.calculationBasePeriod.getDatas().get("TS1893"))) {
			return 12;
		}
		return 0;
	}
	
	private static int getB(int id650) throws Exception {
		for(int i = 0; i < 10; i++) {
			if(driver.findElement(By.xpath(bonusPayment.socialInsranceSum2.xpath)).getText().equals("")) {
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id710Str = driver.findElement(By.xpath(bonusPayment.socialInsranceSum2.xpath)).getText();
		int id710;
		if(id710Str.equals("")) {
			id710 = 0;
		}else {		
			id710 = Integer.parseInt(id710Str.replace(",", ""));
		}
		//B = #id390 - #id710
		return id650 - id710;
	}
	
	private static String getID1310(WebDriver driver, int id1300) throws Exception {
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.taxRate.xpath)).getText().equals("")) {
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1310Str = driver.findElement(By.xpath(bonusPayment.taxRate.xpath)).getText();
		return id1310Str.replace("%", "");
	}
	
	private static int getID1300(String symbol, int num) {
		int id1300;
		if(symbol.equals("<")) {
			id1300 = (int) (Math.random() * num);
		}else if(symbol.equals("==")) {
			id1300 = num;
		}else {
			id1300 = (int) (Math.random() * 1000) + num;
		}
		//input id1300
		driver.findElement(By.xpath(bonusPayment.paymentAmountEtc.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.paymentAmountEtc.xpath)).sendKeys(String.valueOf(id1300));
		
		return id1300;
	}
	
	private static String getCSVData(String key, int value, int dependentNumber) throws Exception{
		if(dependentNumber > 7 && key.equals("dependents")) {
			dependentNumber = 7;
		}
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "B_WithholdingTaxAmount24.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		line = reader.readLine();
		String [] items = line.split(",");
		int num = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i].equals(key + dependentNumber)) {
				num = i;
			}
		}
		String result = "";
		if(key.equals("specialKouRate")) {
			if(value >= 1010000 && value < 1760000){
				result = "0.315";
				reader.close();
				return result;
			}else if(value >= 1760000) {
				result = "0.38";
				reader.close();
				return result;
			}
		}
		line = reader.readLine();
		while(line != null) {
			int amountOver = Integer.parseInt(line.split(",")[2]);
			int amountUnder = Integer.parseInt(line.split(",")[3]);
			if(value >= amountOver && value < amountUnder) {
				result = line.split(",")[num];
				reader.close();
				return result;
			}
			line = reader.readLine();
		}
		reader.close();
		return result;
	}
	
	private static String getCSVData(String key, int value) throws Exception {
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "B_WithholdingTaxAmount24.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		line = reader.readLine();
		String [] items = line.split(",");
		int num = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i].equals(key)) {
				num = i;
			}
		}
		String result = "";
		if (key.equals("specialOtsuRate")) {
			if (value < 88000) {
				result = "0.03";
				reader.close();
				return result;
			}else {
				result = "0.3800";
				reader.close();
				return result;
			}
		}
		line = reader.readLine();
		while(line != null) {
			int amountOver = Integer.parseInt(line.split(",")[2]);
			int amountUnder = Integer.parseInt(line.split(",")[3]);
			if(value >= amountOver && value < amountUnder) {
				result = line.split(",")[num];
				reader.close();
				return result;
			}
			line = reader.readLine();
		}
		reader.close();
		return result;
	}
	
	private static String getCSVSpecial(String title, String key, int value) throws Exception {
		File file = new File(".");
		String filePath = "";
		if(title.equals("SpecialCasesCalculatorIncomeExemption")) {
			filePath = file.getCanonicalPath()
					+ "\\resources\\pages\\testdata\\" + "B_SpecialCasesCalculatorIncomeExemption24.csv";
		}else if(title.equals("SpecialCasesCalculatorTaxAmount")) {
			filePath = file.getCanonicalPath()
					+ "\\resources\\pages\\testdata\\" + "B_SpecialCasesCalculatorTaxAmount24.csv";
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		line = reader.readLine();
		String [] items = line.split(",");
		int num = 0;
		for(int i = 0; i < items.length; i++) {
			if(items[i].equals(key)) {
				num = i;
			}
		}
		String result = "";
		line = reader.readLine();
		while(line != null) {
			if(line.split(",")[3].equals("")) {
				result = line.split(",")[num];
				reader.close();
				return result;
			}else {
				int amountOver = Integer.parseInt(line.split(",")[2]);
				int amountUnder = Integer.parseInt(line.split(",")[3]);
				if(value >= amountOver && value < amountUnder) {
					result = line.split(",")[num];
					reader.close();
					return result;
				}
			}
			line = reader.readLine();
		}
		reader.close();
		return result;
	}
		
	public static void selectTaxCalculationType(WebDriver driver, ClientSettingPage clientSetting, String type) throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.monthlyTable.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(1000);
		String xpath = "";
		if(type.equals("monthlyAmountTable")) {
			xpath = clientSetting.monthlyTable.xpath;
		}else if(type.equals("autoCaliculation")) {
			xpath = clientSetting.specialEquipmentCalc.xpath;
		}
		for(int i = 0; i < 5; i++) {
			if(! driver.findElement(By.xpath(xpath)).isSelected()) {
				driver.findElement(By.xpath(xpath)).click();
			}
		}
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(xpath),true));
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		if(Common.isElementPresent(driver, By.xpath(clientSetting.dateSettingYesButton.xpath))) {
			driver.findElement(By.xpath(clientSetting.dateSettingYesButton.xpath)).click();
		}
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}
	
	public static void addEmployeeInfo(WebDriver driver, EmployeeSettingPage employeeSetting, String type, int number) throws Exception {
		String xpath = "";
		if(type.equals("KO")) {
			xpath = employeeSetting.koMonthlyTable.xpath;
		}else if(type.equals("OTU")) {
			xpath = employeeSetting.monthlyTableType.xpath;
		}
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(employeeSetting.employeeCode_disp.xpath)));
		Common.isNotPending();
		//click edit
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//select monthlyTableType
		if(!driver.findElement(By.xpath(xpath)).isSelected()) {
			driver.findElement(By.xpath(xpath)).click();
		}
		Thread.sleep(2000);
		//input value
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(String.valueOf(number));
		Thread.sleep(2000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(xpath),true));
		new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElementValue(By.xpath(employeeSetting.dependentsNumber.xpath), String.valueOf(number)));
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		if(Common.isElementPresent(driver, By.xpath(employeeSetting.YES.xpath))) {
			driver.findElement(By.xpath(employeeSetting.YES.xpath)).click();
			Thread.sleep(2000);
		}
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
	}
	
}
