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
 * <unemploymentBenefit> - <olderUnemploymentBenefit>
 **/
public class diffUnemploymentBenefit extends TestBase{
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
	 * <unemploymentBenefit> - <olderUnemploymentBenefit>
	 **/
	@Test
	public void diffUnemploymentBenefit_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.SummarySheetCaption.xpath)));
		
		int subject3displayOrder21amount = (int)(Math.random()*10000000);
		int subject3displayOrder21amount2 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount3 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount4 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount5 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount6 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount7 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount8 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount9 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount10 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount11 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount12 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount13 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount14 = (int)(Math.random()*10000000);
		int subject3displayOrder21amount15 = (int)(Math.random()*10000000);
		
		int subject3displayOrder22amount = (int)(Math.random()*10000000);
		int subject3displayOrder22amount2 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount3 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount4 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount5 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount6 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount7 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount8 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount9 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount10 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount11 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount12 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount13 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount14 = (int)(Math.random()*10000000);
		int subject3displayOrder22amount15 = (int)(Math.random()*10000000);
		
		long totalamount1 = subject3displayOrder21amount
				+ subject3displayOrder21amount2 + subject3displayOrder21amount3
				+ subject3displayOrder21amount4 + subject3displayOrder21amount5
				+ subject3displayOrder21amount6 + subject3displayOrder21amount7
				+ subject3displayOrder21amount8 + subject3displayOrder21amount9
				+ subject3displayOrder21amount10
				+ subject3displayOrder21amount11
				+ subject3displayOrder21amount12
				+ subject3displayOrder21amount13
				+ subject3displayOrder21amount14
				+ subject3displayOrder21amount15 + subject3displayOrder22amount
				+ subject3displayOrder22amount2 + subject3displayOrder22amount3
				+ subject3displayOrder22amount4 + subject3displayOrder22amount5
				+ subject3displayOrder22amount6 + subject3displayOrder22amount7
				+ subject3displayOrder22amount8 + subject3displayOrder22amount9
				+ subject3displayOrder22amount10
				+ subject3displayOrder22amount11
				+ subject3displayOrder22amount12
				+ subject3displayOrder22amount13
				+ subject3displayOrder22amount14
				+ subject3displayOrder22amount15;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount2.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount3.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount4.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount5.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount6.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount7.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount8.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount9.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount10.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount11.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount12.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount13.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount14.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount15.xpath)).sendKeys(String.valueOf(subject3displayOrder21amount15));
		
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount2.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount3.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount4.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount5.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount6.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount7.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount8.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount9.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount10.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount11.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount12.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount13.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount14.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount15.xpath)).sendKeys(String.valueOf(subject3displayOrder22amount15));		
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22totalAmount.xpath)).getText().equals(Common.formatNum(String.valueOf(totalamount1)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder22amount12.xpath)).click();
				Thread.sleep(1000);
			}
		}
		
		int subject3displayOrder24amount = (int)(Math.random()*10000000);
		int subject3displayOrder24amount2 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount3 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount4 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount5 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount6 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount7 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount8 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount9 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount10 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount11 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount12 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount13 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount14 = (int)(Math.random()*10000000);
		int subject3displayOrder24amount15 = (int)(Math.random()*10000000);
		
		long totalamount2 = subject3displayOrder24amount
				+ subject3displayOrder24amount2 + subject3displayOrder24amount3
				+ subject3displayOrder24amount4 + subject3displayOrder24amount5
				+ subject3displayOrder24amount6 + subject3displayOrder24amount7
				+ subject3displayOrder24amount8 + subject3displayOrder24amount9
				+ subject3displayOrder24amount10
				+ subject3displayOrder24amount11
				+ subject3displayOrder24amount12
				+ subject3displayOrder24amount13
				+ subject3displayOrder24amount14
				+ subject3displayOrder24amount15;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount2.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount2));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount3.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount3));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount4.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount4));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount5.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount6.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount6));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount7.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount7));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount8.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount8));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount9.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount9));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount10.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount10));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount11.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount11));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount12.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount12));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount13.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount13));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount14.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount14));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount15.xpath)).sendKeys(String.valueOf(subject3displayOrder24amount15));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24totalAmount.xpath)).getText().equals(Common.formatNum(String.valueOf(totalamount2)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder24amount14.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.diffUnemploymentBenefit.xpath)).getText();
		int unemploymentBenefit = Integer.parseInt(driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentBenefit.xpath)).getText().replace(",",""));
		int olderUnemploymentBenefit = Integer.parseInt(driver.findElement(By.xpath(socialInsuranceCalculation.olderUnemploymentBenefit.xpath)).getText().replace(",",""));
		//<unemploymentBenefit> - <olderUnemploymentBenefit>
		int diffUnemploymentBenefit = (int)Math.floor(Double.valueOf(String.valueOf(unemploymentBenefit-olderUnemploymentBenefit)));
		//check the data
		if(Common.formatNum(String.valueOf(diffUnemploymentBenefit)).equals(actual)){
			System.out.println("diffUnemploymentBenefit_1 Pass");
		}else{
			System.out.println("diffUnemploymentBenefit_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(diffUnemploymentBenefit))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(diffUnemploymentBenefit))+"\r\n"+
					"unemploymentBenefit = "+ unemploymentBenefit+"\r\n"+
					"olderUnemploymentBenefit = "+ olderUnemploymentBenefit+"\r\n"+
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