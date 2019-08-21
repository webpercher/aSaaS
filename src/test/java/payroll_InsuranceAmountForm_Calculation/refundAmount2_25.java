package payroll_InsuranceAmountForm_Calculation;


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
 * <refundAmount displayOrder="2">
 **/
public class refundAmount2_25 extends TestBase{
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
	}

	/**
	 * If (<appropriationIntent> is '2'){
	 * blank
	 **/
	@Test
	public void refundAmount2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		//input the value
		driver.findElement(By.xpath(socialInsuranceCalculation.delayedPayment.xpath)).sendKeys("1");
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		double rate_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Rate.xpath)).sendKeys(String.valueOf(rate_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		//get value
		String appropriationAmount2 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount2.xpath)).getText();
		
		int estimatedInsuranceAmount =  (int)(Math.random()*totalAmount0_displayOrder);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(String.valueOf(estimatedInsuranceAmount));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		inputEstimatedInsuranceAmount();
		
		//input value 
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).clear();
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).sendKeys("2");
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		//get value
		String appropriationAmount1 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount1.xpath)).getText();
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.refundAmount2.xpath)).getText();
		//check the data
		if(actual.isEmpty()){
			System.out.println("refundAmount2_1 Pass");
		}else{
			System.out.println("refundAmount2_1 Failed");
			throw new Exception("Error of calculation, expected: <[]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+"\r\n"+
					"appropriationAmount1 = "+ appropriationAmount1+"\r\n"+
					"appropriationAmount2 = "+ appropriationAmount2+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If ( <appropriationAmount displayOrder="1"> ＜ <appropriationAmount displayOrder="2"> ){
	 **/
	@Test
	public void refundAmount2_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		//input the value
		driver.findElement(By.xpath(socialInsuranceCalculation.delayedPayment.xpath)).sendKeys("1");
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		double rate_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Rate.xpath)).sendKeys(String.valueOf(rate_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		//get value
		String appropriationAmount2 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount2.xpath)).getText();
		
		int estimatedInsuranceAmount =  (int)(Math.random()*totalAmount0_displayOrder);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(String.valueOf(estimatedInsuranceAmount));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		inputEstimatedInsuranceAmount();
		
		//input value
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).clear();
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).sendKeys("1");
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		//get value
		String appropriationAmount1 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount1.xpath)).getText();
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.refundAmount2.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(appropriationAmount1)).equals(actual)){
			System.out.println("refundAmount2_2_1 Pass");
		}else{
			System.out.println("refundAmount2_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(appropriationAmount1))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(appropriationAmount1))+"\r\n"+
					"appropriationAmount2 = "+ appropriationAmount2+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 **/
	@Test
	public void refundAmount2_2_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		//input the value
		driver.findElement(By.xpath(socialInsuranceCalculation.delayedPayment.xpath)).sendKeys("3");
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		double rate_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Rate.xpath)).sendKeys(String.valueOf(rate_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		//get value
		String appropriationAmount2 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount2.xpath)).getText();
		
		int estimatedInsuranceAmount =  (int)(Math.random()*10000+totalAmount0_displayOrder*3);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(String.valueOf(estimatedInsuranceAmount));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		inputEstimatedInsuranceAmount();
		
		//input value 
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).clear();
		driver.findElement(By.xpath(socialInsuranceCalculation.appropriationIntent.xpath)).sendKeys("3");
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		//get value
		String appropriationAmount1 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount1.xpath)).getText();
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.refundAmount2.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(appropriationAmount2)).equals(actual)){
			System.out.println("refundAmount2_2_2 Pass");
		}else{
			System.out.println("refundAmount2_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(appropriationAmount2))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(appropriationAmount2))+"\r\n"+
					"appropriationAmount1 = "+ appropriationAmount1+"\r\n"+
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
	
	public static void inputEstimatedInsuranceAmount() throws Exception {
		String estimatedInsuranceAmount = driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).getAttribute("value");
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).clear();
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(estimatedInsuranceAmount+"0");
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).clear();
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(estimatedInsuranceAmount);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
	}
}