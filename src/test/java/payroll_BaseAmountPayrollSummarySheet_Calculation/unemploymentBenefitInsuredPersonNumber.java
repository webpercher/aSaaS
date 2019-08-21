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
 * ( PayrollSummary#number having
 * PayrollSummary#type is 'unemployment benefit' and PayrollSummary#displayOrder is '3'
 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
 * ※ A fraction less than'1' yen is rounded down after decimal point.  
 **/
public class unemploymentBenefitInsuredPersonNumber extends TestBase{
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
	 * ( PayrollSummary#number having
	 * PayrollSummary#type is 'unemployment benefit' and PayrollSummary#displayOrder is '3'
	 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
	 * ※ A fraction less than'1' yen is rounded down after decimal point.  
	 **/
	@Test
	public void unemploymentBenefitInsuredPersonNumber_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.SummarySheetCaption.xpath)));
		
		int subject3displayOrder21number = (int)(Math.random()*10000);
		int subject3displayOrder21number2 = (int)(Math.random()*10000);
		int subject3displayOrder21number3 = (int)(Math.random()*10000);
		int subject3displayOrder21number4 = (int)(Math.random()*10000);
		int subject3displayOrder21number5 = (int)(Math.random()*10000);
		int subject3displayOrder21number6 = (int)(Math.random()*10000);
		int subject3displayOrder21number7 = (int)(Math.random()*10000);
		int subject3displayOrder21number8 = (int)(Math.random()*10000);
		int subject3displayOrder21number9 = (int)(Math.random()*10000);
		int subject3displayOrder21number10 = (int)(Math.random()*10000);
		int subject3displayOrder21number11 = (int)(Math.random()*10000);
		int subject3displayOrder21number12 = (int)(Math.random()*10000);
		
		int subject3displayOrder22number = (int)(Math.random()*10000);
		int subject3displayOrder22number2 = (int)(Math.random()*10000);
		int subject3displayOrder22number3 = (int)(Math.random()*10000);
		int subject3displayOrder22number4 = (int)(Math.random()*10000);
		int subject3displayOrder22number5 = (int)(Math.random()*10000);
		int subject3displayOrder22number6 = (int)(Math.random()*10000);
		int subject3displayOrder22number7 = (int)(Math.random()*10000);
		int subject3displayOrder22number8 = (int)(Math.random()*10000);
		int subject3displayOrder22number9 = (int)(Math.random()*10000);
		int subject3displayOrder22number10 = (int)(Math.random()*10000);
		int subject3displayOrder22number11 = (int)(Math.random()*10000);
		int subject3displayOrder22number12 = (int)(Math.random()*10000);
		
		int totalNumber = subject3displayOrder21number
				+ subject3displayOrder21number2 + subject3displayOrder21number3
				+ subject3displayOrder21number4 + subject3displayOrder21number5
				+ subject3displayOrder21number6 + subject3displayOrder21number7
				+ subject3displayOrder21number8 + subject3displayOrder21number9
				+ subject3displayOrder21number10
				+ subject3displayOrder21number11
				+ subject3displayOrder21number12 + subject3displayOrder22number
				+ subject3displayOrder22number2 + subject3displayOrder22number3
				+ subject3displayOrder22number4 + subject3displayOrder22number5
				+ subject3displayOrder22number6 + subject3displayOrder22number7
				+ subject3displayOrder22number8 + subject3displayOrder22number9
				+ subject3displayOrder22number10
				+ subject3displayOrder22number11
				+ subject3displayOrder22number12;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number.xpath)).sendKeys(String.valueOf(subject3displayOrder21number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number2.xpath)).sendKeys(String.valueOf(subject3displayOrder21number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number3.xpath)).sendKeys(String.valueOf(subject3displayOrder21number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number4.xpath)).sendKeys(String.valueOf(subject3displayOrder21number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number5.xpath)).sendKeys(String.valueOf(subject3displayOrder21number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number6.xpath)).sendKeys(String.valueOf(subject3displayOrder21number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number7.xpath)).sendKeys(String.valueOf(subject3displayOrder21number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number8.xpath)).sendKeys(String.valueOf(subject3displayOrder21number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number9.xpath)).sendKeys(String.valueOf(subject3displayOrder21number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number10.xpath)).sendKeys(String.valueOf(subject3displayOrder21number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number11.xpath)).sendKeys(String.valueOf(subject3displayOrder21number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number12.xpath)).sendKeys(String.valueOf(subject3displayOrder21number12));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number.xpath)).sendKeys(String.valueOf(subject3displayOrder22number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number2.xpath)).sendKeys(String.valueOf(subject3displayOrder22number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number3.xpath)).sendKeys(String.valueOf(subject3displayOrder22number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number4.xpath)).sendKeys(String.valueOf(subject3displayOrder22number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number5.xpath)).sendKeys(String.valueOf(subject3displayOrder22number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number6.xpath)).sendKeys(String.valueOf(subject3displayOrder22number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number7.xpath)).sendKeys(String.valueOf(subject3displayOrder22number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number8.xpath)).sendKeys(String.valueOf(subject3displayOrder22number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number9.xpath)).sendKeys(String.valueOf(subject3displayOrder22number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number10.xpath)).sendKeys(String.valueOf(subject3displayOrder22number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number11.xpath)).sendKeys(String.valueOf(subject3displayOrder22number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22number12.xpath)).sendKeys(String.valueOf(subject3displayOrder22number12));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22totalNumber.xpath)).getText().equals(Common.formatNum(String.valueOf(totalNumber)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21number.xpath)).click();
				Thread.sleep(1000);
			}
		}
		org.junit.Assert.assertEquals(driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentTotalNumber.xpath)).getText(),Common.formatNum(String.valueOf(totalNumber)));
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentNumber.xpath)).getText();
		//unemploymentNumber = unemploymentTotalNumber/12
		int unemploymentNumber = (int)Math.floor(Double.valueOf(String.valueOf(totalNumber))/12);
		//check the data
		if(Common.formatNum(String.valueOf(unemploymentNumber)).equals(actual)){
			System.out.println("unemploymentBenefitInsuredPersonNumber_1 Pass");
		}else{
			System.out.println("unemploymentBenefitInsuredPersonNumber_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(unemploymentNumber))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(unemploymentNumber))+"\r\n"+
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