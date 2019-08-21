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
 * <construction>
 **/
public class construction extends TestBase{
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
	 * If EstimateAndDefiniteInsuranceAmountForm#unemploymentInsuranceType is 'general'
	 * EstimateAndDefiniteInsuranceAmountForm#construction is '01'
	 **/
	@Test
	public void construction_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		//select the data 
		driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentInsuranceType_general.xpath)).click();
		//syn
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)));
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)).click();
		Thread.sleep(2000);
		//check the data
		if(!driver.findElement(By.xpath(socialInsuranceCalculation.construction_1.xpath)).isSelected()&&
				driver.findElement(By.xpath(socialInsuranceCalculation.construction_2.xpath)).isSelected()){
			System.out.println("construction_1 Pass");
		}else{
			System.out.println("construction_1 Failed");
			throw new Exception("construction_1 is selected or construction_1 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n");
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 * EstimateAndDefiniteInsuranceAmountForm#construction is '10'
	 **/
	@Test
	public void construction_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		//select the data 
		driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentInsuranceType_construction.xpath)).click();
		//syn
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)));
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)).click();
		Thread.sleep(2000);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.construction_1.xpath)).isSelected()&&
				!driver.findElement(By.xpath(socialInsuranceCalculation.construction_2.xpath)).isSelected()){
			System.out.println("construction_2 Pass");
		}else{
			System.out.println("construction_2 Failed");
			throw new Exception("construction_1 is not selected or construction_1 is selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n");
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 * EstimateAndDefiniteInsuranceAmountForm#construction is '10'
	 **/
	@Test
	public void construction_3() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		//select the data 
		driver.findElement(By.xpath(socialInsuranceCalculation.unemploymentInsuranceType_other.xpath)).click();
		//syn
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)));
		driver.findElement(By.xpath(socialInsuranceCalculation.Synchroinzation_syn.xpath)).click();
		Thread.sleep(2000);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.construction_1.xpath)).isSelected()&&
				!driver.findElement(By.xpath(socialInsuranceCalculation.construction_2.xpath)).isSelected()){
			System.out.println("construction_3 Pass");
		}else{
			System.out.println("construction_3 Failed");
			throw new Exception("construction_1 is not selected or construction_1 is selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n");
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