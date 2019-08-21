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
 * <deficitAmount displayOrder="2">
 **/
public class deficitAmount2 extends TestBase{
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
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
	}

	/**
	 * <deficitAmount displayOrder="1">
	 **/
	@Test
	public void deficitAmount2_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount0_displayOrder = (int)(Math.random()*1000000+1000);
		int estimatedInsuranceAmount =  (int)(Math.random()*totalAmount0_displayOrder);
		double rate_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate.xpath)).sendKeys(String.valueOf(rate_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.estimatedInsuranceAmount.xpath)).sendKeys(String.valueOf(estimatedInsuranceAmount));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject4Amount.xpath)).click();
		Thread.sleep(1000);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.deficitAmount2.xpath)).getText();
		//<totalAmount type="0" displayOrder="1"> - <estimatedInsuranceAmount>
		String deficitAmount1 = String.valueOf(totalAmount0_displayOrder-estimatedInsuranceAmount);
		if(Integer.parseInt(deficitAmount1)<=0){
			deficitAmount1 = "";
		}
		String deficitAmount2 = deficitAmount1;
		//check the data
		if(Common.formatNum(String.valueOf(deficitAmount2)).equals(actual)){
			System.out.println("deficitAmount2_1 Pass");
		}else{
			System.out.println("deficitAmount2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(deficitAmount2))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(deficitAmount2))+"\r\n"+
					"totalAmount0_displayOrder = "+ totalAmount0_displayOrder+"\r\n"+
					"estimatedInsuranceAmount = "+ estimatedInsuranceAmount+"\r\n"+
					"rate_displayOrder = "+ rate_displayOrder+"\r\n"+
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