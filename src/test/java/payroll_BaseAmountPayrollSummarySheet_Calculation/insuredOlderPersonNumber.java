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
 * PayrollSummary#type is 'unemployment benefit' and PayrollSummary#displayOrder is '4'
 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
 * ※ A fraction less than'1' yen is rounded down after decimal point.  
 **/
public class insuredOlderPersonNumber extends TestBase{
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
	 * PayrollSummary#type is 'unemployment benefit' and PayrollSummary#displayOrder is '4'
	 * BaseAmountPayrollSummarySheetDetail#type is 'total' and BaseAmountPayrollSummarySheetDetail#displayOrder is '1' ) / 12
	 * ※ A fraction less than'1' yen is rounded down after decimal point.  
	 **/
	@Test
	public void insuredOlderPersonNumber_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.SummarySheetCaption.xpath)));
		
		int subject3displayOrder24number = (int)(Math.random()*10000);
		int subject3displayOrder24number2 = (int)(Math.random()*10000);
		int subject3displayOrder24number3 = (int)(Math.random()*10000);
		int subject3displayOrder24number4 = (int)(Math.random()*10000);
		int subject3displayOrder24number5 = (int)(Math.random()*10000);
		int subject3displayOrder24number6 = (int)(Math.random()*10000);
		int subject3displayOrder24number7 = (int)(Math.random()*10000);
		int subject3displayOrder24number8 = (int)(Math.random()*10000);
		int subject3displayOrder24number9 = (int)(Math.random()*10000);
		int subject3displayOrder24number10 = (int)(Math.random()*10000);
		int subject3displayOrder24number11 = (int)(Math.random()*10000);
		int subject3displayOrder24number12 = (int)(Math.random()*10000);
		int totalNumber = subject3displayOrder24number
				+ subject3displayOrder24number2 + subject3displayOrder24number3
				+ subject3displayOrder24number4 + subject3displayOrder24number5
				+ subject3displayOrder24number6 + subject3displayOrder24number7
				+ subject3displayOrder24number8 + subject3displayOrder24number9
				+ subject3displayOrder24number10
				+ subject3displayOrder24number11
				+ subject3displayOrder24number12;
		
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number.xpath)).sendKeys(String.valueOf(subject3displayOrder24number));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number2.xpath)).sendKeys(String.valueOf(subject3displayOrder24number2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number3.xpath)).sendKeys(String.valueOf(subject3displayOrder24number3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number4.xpath)).sendKeys(String.valueOf(subject3displayOrder24number4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number5.xpath)).sendKeys(String.valueOf(subject3displayOrder24number5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number6.xpath)).sendKeys(String.valueOf(subject3displayOrder24number6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number7.xpath)).sendKeys(String.valueOf(subject3displayOrder24number7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number8.xpath)).sendKeys(String.valueOf(subject3displayOrder24number8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number9.xpath)).sendKeys(String.valueOf(subject3displayOrder24number9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number10.xpath)).sendKeys(String.valueOf(subject3displayOrder24number10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number11.xpath)).sendKeys(String.valueOf(subject3displayOrder24number11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number12.xpath)).sendKeys(String.valueOf(subject3displayOrder24number12));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24totalNumber.xpath)).getText().equals(Common.formatNum(String.valueOf(totalNumber)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24number.xpath)).click();
				Thread.sleep(1000);
			}
		}
		org.junit.Assert.assertEquals(driver.findElement(By.xpath(socialInsuranceCalculation.insuredOlderPersonTotalNumber.xpath)).getText(),Common.formatNum(String.valueOf(totalNumber)));
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.insuredOlderPersonNumber.xpath)).getText();
		//unemploymentNumber = unemploymentTotalNumber/12
		int unemploymentNumber = (int)Math.floor(Double.valueOf(String.valueOf(totalNumber))/12);
		//check the data
		if(Common.formatNum(String.valueOf(unemploymentNumber)).equals(actual)){
			System.out.println("insuredOlderPersonNumber_1 Pass");
		}else{
			System.out.println("insuredOlderPersonNumber_1 Failed");
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