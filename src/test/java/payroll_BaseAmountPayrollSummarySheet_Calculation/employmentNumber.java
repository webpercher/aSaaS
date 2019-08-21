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
 * PayrollSummary#number having
 * PayrollSummary#type is 'worker's accident insurance' and PayrollSummary#displayOrder is '4'
 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
 * ※ A fraction less than'1' yen is rounded down after decimal point. 
 **/
public class employmentNumber extends TestBase{
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
	 * PayrollSummary#number having
	 * PayrollSummary#type is 'worker's accident insurance' and PayrollSummary#displayOrder is '4'
	 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
	 * ※ A fraction less than'1' yen is rounded down after decimal point. 
	 **/
	@Test
	public void employmentNumber_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.SummarySheetCaption.xpath)));
		
		int subject2displayOrder21number = (int)(Math.random()*10000);
		int subject2displayOrder21number2 = (int)(Math.random()*10000);
		int subject2displayOrder21number3 = (int)(Math.random()*10000);
		int subject2displayOrder21number4 = (int)(Math.random()*10000);
		int subject2displayOrder21number5 = (int)(Math.random()*10000);
		int subject2displayOrder21number6 = (int)(Math.random()*10000);
		int subject2displayOrder21number7 = (int)(Math.random()*10000);
		int subject2displayOrder21number8 = (int)(Math.random()*10000);
		int subject2displayOrder21number9 = (int)(Math.random()*10000);
		int subject2displayOrder21number10 = (int)(Math.random()*10000);
		int subject2displayOrder21number11 = (int)(Math.random()*10000);
		int subject2displayOrder21number12 = (int)(Math.random()*10000);
		
		int subject2displayOrder22number = (int)(Math.random()*10000);
		int subject2displayOrder22number2 = (int)(Math.random()*10000);
		int subject2displayOrder22number3 = (int)(Math.random()*10000);
		int subject2displayOrder22number4 = (int)(Math.random()*10000);
		int subject2displayOrder22number5 = (int)(Math.random()*10000);
		int subject2displayOrder22number6 = (int)(Math.random()*10000);
		int subject2displayOrder22number7 = (int)(Math.random()*10000);
		int subject2displayOrder22number8 = (int)(Math.random()*10000);
		int subject2displayOrder22number9 = (int)(Math.random()*10000);
		int subject2displayOrder22number10 = (int)(Math.random()*10000);
		int subject2displayOrder22number11 = (int)(Math.random()*10000);
		int subject2displayOrder22number12 = (int)(Math.random()*10000);
		
		int subject2displayOrder23number = (int)(Math.random()*10000);
		int subject2displayOrder23number2 = (int)(Math.random()*10000);
		int subject2displayOrder23number3 = (int)(Math.random()*10000);
		int subject2displayOrder23number4 = (int)(Math.random()*10000);
		int subject2displayOrder23number5 = (int)(Math.random()*10000);
		int subject2displayOrder23number6 = (int)(Math.random()*10000);
		int subject2displayOrder23number7 = (int)(Math.random()*10000);
		int subject2displayOrder23number8 = (int)(Math.random()*10000);
		int subject2displayOrder23number9 = (int)(Math.random()*10000);
		int subject2displayOrder23number10 = (int)(Math.random()*10000);
		int subject2displayOrder23number11 = (int)(Math.random()*10000);
		int subject2displayOrder23number12 = (int)(Math.random()*10000);
		int totalNumber = subject2displayOrder21number
				+ subject2displayOrder21number2 + subject2displayOrder21number3
				+ subject2displayOrder21number4 + subject2displayOrder21number5
				+ subject2displayOrder21number6 + subject2displayOrder21number7
				+ subject2displayOrder21number8 + subject2displayOrder21number9
				+ subject2displayOrder21number10
				+ subject2displayOrder21number11
				+ subject2displayOrder21number12 + subject2displayOrder22number
				+ subject2displayOrder22number2 + subject2displayOrder22number3
				+ subject2displayOrder22number4 + subject2displayOrder22number5
				+ subject2displayOrder22number6 + subject2displayOrder22number7
				+ subject2displayOrder22number8 + subject2displayOrder22number9
				+ subject2displayOrder22number10
				+ subject2displayOrder22number11
				+ subject2displayOrder22number12 + subject2displayOrder23number
				+ subject2displayOrder23number2 + subject2displayOrder23number3
				+ subject2displayOrder23number4 + subject2displayOrder23number5
				+ subject2displayOrder23number6 + subject2displayOrder23number7
				+ subject2displayOrder23number8 + subject2displayOrder23number9
				+ subject2displayOrder23number10
				+ subject2displayOrder23number11
				+ subject2displayOrder23number12;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number.xpath)).sendKeys(String.valueOf(subject2displayOrder21number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number2.xpath)).sendKeys(String.valueOf(subject2displayOrder21number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number3.xpath)).sendKeys(String.valueOf(subject2displayOrder21number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number4.xpath)).sendKeys(String.valueOf(subject2displayOrder21number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number5.xpath)).sendKeys(String.valueOf(subject2displayOrder21number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number6.xpath)).sendKeys(String.valueOf(subject2displayOrder21number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number7.xpath)).sendKeys(String.valueOf(subject2displayOrder21number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number8.xpath)).sendKeys(String.valueOf(subject2displayOrder21number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number9.xpath)).sendKeys(String.valueOf(subject2displayOrder21number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number10.xpath)).sendKeys(String.valueOf(subject2displayOrder21number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number11.xpath)).sendKeys(String.valueOf(subject2displayOrder21number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21number12.xpath)).sendKeys(String.valueOf(subject2displayOrder21number12));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number.xpath)).sendKeys(String.valueOf(subject2displayOrder22number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number2.xpath)).sendKeys(String.valueOf(subject2displayOrder22number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number3.xpath)).sendKeys(String.valueOf(subject2displayOrder22number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number4.xpath)).sendKeys(String.valueOf(subject2displayOrder22number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number5.xpath)).sendKeys(String.valueOf(subject2displayOrder22number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number6.xpath)).sendKeys(String.valueOf(subject2displayOrder22number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number7.xpath)).sendKeys(String.valueOf(subject2displayOrder22number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number8.xpath)).sendKeys(String.valueOf(subject2displayOrder22number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number9.xpath)).sendKeys(String.valueOf(subject2displayOrder22number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number10.xpath)).sendKeys(String.valueOf(subject2displayOrder22number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number11.xpath)).sendKeys(String.valueOf(subject2displayOrder22number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder22number12.xpath)).sendKeys(String.valueOf(subject2displayOrder22number12));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number.xpath)).sendKeys(String.valueOf(subject2displayOrder23number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number2.xpath)).sendKeys(String.valueOf(subject2displayOrder23number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number3.xpath)).sendKeys(String.valueOf(subject2displayOrder23number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number4.xpath)).sendKeys(String.valueOf(subject2displayOrder23number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number5.xpath)).sendKeys(String.valueOf(subject2displayOrder23number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number6.xpath)).sendKeys(String.valueOf(subject2displayOrder23number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number7.xpath)).sendKeys(String.valueOf(subject2displayOrder23number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number8.xpath)).sendKeys(String.valueOf(subject2displayOrder23number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number9.xpath)).sendKeys(String.valueOf(subject2displayOrder23number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number10.xpath)).sendKeys(String.valueOf(subject2displayOrder23number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number11.xpath)).sendKeys(String.valueOf(subject2displayOrder23number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder23number12.xpath)).sendKeys(String.valueOf(subject2displayOrder23number12));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder24totalNumber.xpath)).getText().equals(Common.formatNum(String.valueOf(totalNumber)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject2displayOrder21amount.xpath)).click();
				Thread.sleep(1000);
			}
		}
		org.junit.Assert.assertEquals(driver.findElement(By.xpath(socialInsuranceCalculation.employmentTotalNumber.xpath)).getText(),Common.formatNum(String.valueOf(totalNumber)));
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.employmentNumber.xpath)).getText();
		//employmentNumber = employmentTotalNumber/12
		int employmentNumber = (int)Math.floor(Double.valueOf(String.valueOf(totalNumber))/12);
		//check the data
		if(Common.formatNum(String.valueOf(employmentNumber)).equals(actual)){
			System.out.println("employmentNumber_1 Pass");
		}else{
			System.out.println("employmentNumber_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(employmentNumber))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(employmentNumber))+"\r\n"+
					"totalNumber = "+ totalNumber+"\r\n"+
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