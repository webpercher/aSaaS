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
 * <deficitAmount displayOrder="4">
 **/
public class deficitAmount4 extends TestBase{
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
		Common.createCompanyClient(driver,"24");
		Common.addSimpleEmployeeInfo(driver);
	}

	/**
	 * <appropriationAmount displayOrder="4"> - <refundAmount displayOrder="4">
	 **/
	@Test
	public void deficitAmount4_1() throws Exception {
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
		String appropriationAmount4 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount4.xpath)).getText().replace(",","");
		
		//input the value
		int estimatedInsuranceAmount = (int)(Math.random()*Integer.parseInt(appropriationAmount4)+1+Integer.parseInt(appropriationAmount4));
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(String.valueOf(estimatedInsuranceAmount));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		
		inputEstimatedInsuranceAmount();

		//get value 
		String refundAmount2 = driver.findElement(By.xpath(socialInsuranceCalculation.refundAmount2.xpath)).getText().replace(",","");
		String appropriationAmount1 = driver.findElement(By.xpath(socialInsuranceCalculation.appropriationAmount1.xpath)).getText().replace(",","");
		//<appropriationAmount displayOrder="1"> - <refundAmount displayOrder="2">
		int refundAmount4 = Integer.parseInt(appropriationAmount1)-Integer.parseInt(refundAmount2);
		//<appropriationAmount displayOrder="4"> - <refundAmount displayOrder="4">
		int deficitAmount4 =  Integer.parseInt(appropriationAmount4)-refundAmount4;
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.deficitAmount4.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(deficitAmount4)).equals(actual)){
			System.out.println("deficitAmount4_1 Pass");
		}else{
			System.out.println("deficitAmount4_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(deficitAmount4))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(deficitAmount4))+"\r\n"+
					"appropriationAmount1 = "+ appropriationAmount1+"\r\n"+
					"refundAmount2 = "+ refundAmount2+"\r\n"+
					"appropriationAmount4 = "+ appropriationAmount4+"\r\n"+
					"refundAmount4 = "+ refundAmount4+"\r\n"+
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