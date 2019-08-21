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
 * <laborInsuranceType>
 **/
public class laborInsuranceType extends TestBase{
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
	 * If (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '1' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0') {
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '11'
	 **/
	@Test
	public void laborInsuranceType_1_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_1.xpath)).isSelected()&&
				driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_1_1 Pass");
		}else{
			System.out.println("laborInsuranceType_1_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_1 or EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '2' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0'){
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '1*'
	 **/
	@Test
	public void laborInsuranceType_2_1_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate2.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_1.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_1_1 Pass");
		}else{
			System.out.println("laborInsuranceType_2_1_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_1 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '0*'
	 **/
	@Test
	public void laborInsuranceType_2_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).sendKeys(String.valueOf(-totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate2.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(-totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(!driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_1.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_2_1 Pass");
		}else{
			System.out.println("laborInsuranceType_2_2_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_1 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ -totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '4' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0') or (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '5' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0'){
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '*1'
	 * 04
	 **/
	@Test
	public void laborInsuranceType_2_3_1_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_3_1 Pass");
		}else{
			System.out.println("laborInsuranceType_2_3_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '4' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0') or (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '5' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0'){
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '*1'
	 * 05
	 **/
	@Test
	public void laborInsuranceType_2_3_1_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate5.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_3_1_2 Pass");
		}else{
			System.out.println("laborInsuranceType_2_3_1_2 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '*0'
	 * 04
	 **/
	@Test
	public void laborInsuranceType_2_3_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).sendKeys(String.valueOf(-totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(-totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(!driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_3_2_1 Pass");
		}else{
			System.out.println("laborInsuranceType_2_3_2_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ -totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * ELSE
	 * EstimateAndDefiniteInsuranceAmountForm#laborInsuranceType is '*0'
	 * 05
	 **/
	@Test
	public void laborInsuranceType_2_3_2_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).sendKeys(String.valueOf(-totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate5.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).click();
		Thread.sleep(2000);
		int totalAmount = (int)Math.floor(-totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(!driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_3_2_2 Pass");
		}else{
			System.out.println("laborInsuranceType_2_3_2_2 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ -totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount = "+ totalAmount);
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '2' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0') and (0 ≦ EstimateAndDefiniteInsuranceAmountFormDetail#totalAmount having EstimateAndDefiniteInsuranceAmountFormDetail#displayOrder is '4' and EstimateAndDefiniteInsuranceAmountFormDetail#type is '0')
	 * Set '11'
	 **/
	@Test
	public void laborInsuranceType_2_4_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount1_displayOrder = (int)(Math.random()*1000000+1);
		double rate0_displayOrder = 1;
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate2.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).sendKeys(String.valueOf(totalAmount1_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).click();
		Thread.sleep(2000);
		int totalAmount2 = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		int totalAmount4 = (int)Math.floor(totalAmount1_displayOrder*rate0_displayOrder);
		//check the data
		if(driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_1.xpath)).isSelected()&&
				driver.findElement(By.xpath(socialInsuranceCalculation.EstimateAndDefiniteInsuranceAmountForm_2.xpath)).isSelected()){
			System.out.println("laborInsuranceType_2_4_1 Pass");
		}else{
			System.out.println("laborInsuranceType_2_4_1 Failed");
			throw new Exception("EstimateAndDefiniteInsuranceAmountForm_1 or EstimateAndDefiniteInsuranceAmountForm_2 is not selected"+"\r\n"+
					"Please refer to input values below: "+"\r\n"+
					"totalAmount1_displayOrder = "+ totalAmount1_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"totalAmount2 = "+ totalAmount2+"\r\n"+
					"totalAmount4 = "+ totalAmount4);
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