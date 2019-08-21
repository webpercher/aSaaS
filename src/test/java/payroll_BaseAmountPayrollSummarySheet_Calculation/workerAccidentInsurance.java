package payroll_BaseAmountPayrollSummarySheet_Calculation;


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
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;

/**
 * ( PayrollSummary#amount having
 * PayrollSummary#type is 'worker's accident insurance' and PayrollSummary#displayOrder is '4'
 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 1000
 * ※ A fraction less than'1000' yen is rounded down after decimal point.
 **/
public class workerAccidentInsurance extends TestBase{
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
		socialInsuranceCalculation = (SocialInsuranceCalculation) helper.getPage("SocialInsuranceCalculation");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
	}

	/**
	 * ( PayrollSummary#amount having
	 * PayrollSummary#type is 'worker's accident insurance' and PayrollSummary#displayOrder is '4'
	 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 1000
	 * ※ A fraction less than'1000' yen is rounded down after decimal point.
	 **/
	@Test
	public void workerAccidentInsurance_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.SummarySheetCaption.xpath)));
		
		int subject2displayOrder21amount = (int)(Math.random()*10000000);
		int subject2displayOrder21amount2 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount3 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount4 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount5 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount6 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount7 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount8 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount9 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount10 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount11 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount12 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount13 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount14 = (int)(Math.random()*10000000);
		int subject2displayOrder21amount15 = (int)(Math.random()*10000000);
		
		int subject2displayOrder22amount = (int)(Math.random()*10000000);
		int subject2displayOrder22amount2 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount3 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount4 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount5 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount6 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount7 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount8 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount9 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount10 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount11 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount12 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount13 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount14 = (int)(Math.random()*10000000);
		int subject2displayOrder22amount15 = (int)(Math.random()*10000000);
		
		int subject2displayOrder23amount = (int)(Math.random()*10000000);
		int subject2displayOrder23amount2 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount3 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount4 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount5 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount6 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount7 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount8 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount9 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount10 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount11 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount12 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount13 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount14 = (int)(Math.random()*10000000);
		int subject2displayOrder23amount15 = (int)(Math.random()*10000000);
		long totalamount = subject2displayOrder21amount
				+ subject2displayOrder21amount2 + subject2displayOrder21amount3
				+ subject2displayOrder21amount4 + subject2displayOrder21amount5
				+ subject2displayOrder21amount6 + subject2displayOrder21amount7
				+ subject2displayOrder21amount8 + subject2displayOrder21amount9
				+ subject2displayOrder21amount10
				+ subject2displayOrder21amount11
				+ subject2displayOrder21amount12
				+ subject2displayOrder21amount13
				+ subject2displayOrder21amount14
				+ subject2displayOrder21amount15 + subject2displayOrder22amount
				+ subject2displayOrder22amount2 + subject2displayOrder22amount3
				+ subject2displayOrder22amount4 + subject2displayOrder22amount5
				+ subject2displayOrder22amount6 + subject2displayOrder22amount7
				+ subject2displayOrder22amount8 + subject2displayOrder22amount9
				+ subject2displayOrder22amount10
				+ subject2displayOrder22amount11
				+ subject2displayOrder22amount12
				+ subject2displayOrder22amount13
				+ subject2displayOrder22amount14
				+ subject2displayOrder22amount15 + subject2displayOrder23amount
				+ subject2displayOrder23amount2 + subject2displayOrder23amount3
				+ subject2displayOrder23amount4 + subject2displayOrder23amount5
				+ subject2displayOrder23amount6 + subject2displayOrder23amount7
				+ subject2displayOrder23amount8 + subject2displayOrder23amount9
				+ subject2displayOrder23amount10
				+ subject2displayOrder23amount11
				+ subject2displayOrder23amount12
				+ subject2displayOrder23amount13
				+ subject2displayOrder23amount14
				+ subject2displayOrder23amount15;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount2.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount3.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount4.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount5.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount6.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount7.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount8.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount9.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount10.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount11.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount12.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount13.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount14.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount15.xpath)).sendKeys(String.valueOf(subject2displayOrder21amount15));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount2.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount3.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount4.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount5.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount6.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount7.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount8.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount9.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount10.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount11.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount12.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount13.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount14.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22amount15.xpath)).sendKeys(String.valueOf(subject2displayOrder22amount15));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount2.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount3.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount4.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount5.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount6.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount7.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount8.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount9.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount10.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount11.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount12.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount13.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount14.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount15.xpath)).sendKeys(String.valueOf(subject2displayOrder23amount15));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23totalAmount.xpath)).getText().equals(Common.formatNum(String.valueOf(totalamount)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23amount.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.workerAccidentInsurance.xpath)).getText();
		//workerAccidentInsurance = workerAccidentTotalInsurance/1000
		int workerAccidentInsurance = (int)Math.floor(Double.valueOf(String.valueOf(totalamount))/1000);
		//check the data
		if(Common.formatNum(String.valueOf(workerAccidentInsurance)).equals(actual)){
			System.out.println("workerAccidentInsurance_1 Pass");
		}else{
			System.out.println("workerAccidentInsurance_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(workerAccidentInsurance))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(workerAccidentInsurance))+"\r\n"+
					"totalamount = "+ totalamount+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
}