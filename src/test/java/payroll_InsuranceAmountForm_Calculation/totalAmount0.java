package payroll_InsuranceAmountForm_Calculation;


import java.math.BigDecimal;

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
 * <totalAmount type="0">
 **/
public class totalAmount0 extends TestBase{
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
	 * If displayOrder is "1"{
	 * If (<amount type="0"> of displayOrder is "1") is blank
	 **/
	@Test
	public void totalAmount0_1_1() throws Exception {
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount0_displayOrder2 = (int)(Math.random()*1000000);
		int totalAmount0_displayOrder5 = (int)(Math.random()*1000000);
		String rate0_displayOrder2 = String.valueOf(Math.random()*999.999);
		String rate0_displayOrder5 = String.valueOf(Math.random()*(999.999-Double.valueOf(rate0_displayOrder2)));
		rate0_displayOrder2 = rate0_displayOrder2.substring(0,rate0_displayOrder2.indexOf(".")+4);
		rate0_displayOrder5 = rate0_displayOrder5.substring(0,rate0_displayOrder5.indexOf(".")+4);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder2));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate2.xpath)).sendKeys(String.valueOf(rate0_displayOrder2));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder5));
		driver.findElement(By.xpath(socialInsuranceCalculation.subject3displayOrder21amount5.xpath)).sendKeys(String.valueOf(rate0_displayOrder5));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate.xpath)).getAttribute("value").equals(String.valueOf(Double.valueOf(rate0_displayOrder2)+Double.valueOf(rate0_displayOrder5)))){
				driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount.xpath)).getText();
		String actualDisplayOrder2 = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount2.xpath)).getText().replace(",","");
		String actualDisplayOrder5 = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount5.xpath)).getText().replace(",","");
		int totalAmount0 = Integer.parseInt(actualDisplayOrder2.substring(0,actualDisplayOrder2.length()-1))+
						   Integer.parseInt(actualDisplayOrder5.substring(0,actualDisplayOrder5.length()-1));
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_1_1 Pass");
		}else{
			System.out.println("totalAmount0_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder2 = "+ totalAmount0_displayOrder2+"\r\n"+
					"totalAmount0_displayOrder5 = "+ totalAmount0_displayOrder5+"\r\n"+
					"actualDisplayOrder2 = "+ actualDisplayOrder2+"\r\n"+
					"actualDisplayOrder5 = "+ actualDisplayOrder5+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If displayOrder is "1"{
	 * totalAmount0_1_2
	 **/
	@Test
	public void totalAmount0_1_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		
		int totalAmount0_displayOrder0 = (int)(Math.random()*1000000);
		String rate0_displayOrder0 = String.valueOf(Math.random()*999.999);
		rate0_displayOrder0 = rate0_displayOrder0.substring(0,rate0_displayOrder0.indexOf(".")+4);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder0));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate.xpath)).sendKeys(String.valueOf(rate0_displayOrder0));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount.xpath)).click();
		Thread.sleep(2000);
		//<amount type="0"> × <rate type="0">
		BigDecimal bd = new BigDecimal(totalAmount0_displayOrder0*Double.valueOf(rate0_displayOrder0));
		int totalAmount0 = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_FLOOR).toString().split("\\.")[0]);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_1_2 Pass");
		}else{
			System.out.println("totalAmount0_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder0 = "+ totalAmount0_displayOrder0+"\r\n"+
					"rate0_displayOrder0 = "+ rate0_displayOrder0+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * <amount type="0"> × <rate type="0">
	 **/
	@Test
	public void totalAmount0_2_1() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		String rate0_displayOrder = String.valueOf(Math.random()*999.999);
		rate0_displayOrder = rate0_displayOrder.substring(0,rate0_displayOrder.indexOf(".")+4);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate2.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount2.xpath)).click();
		Thread.sleep(2000);
		//<amount type="0"> × <rate type="0">
		BigDecimal bd = new BigDecimal(totalAmount0_displayOrder*Double.valueOf(rate0_displayOrder));
		int totalAmount0 = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_FLOOR).toString().split("\\.")[0]);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount2.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_2_1 Pass");
		}else{
			System.out.println("totalAmount0_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder = "+ totalAmount0_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * <amount type="0"> × <rate type="0">
	 **/
	@Test
	public void totalAmount0_2_2() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		String rate0_displayOrder = String.valueOf(Math.random()*999.99);
		rate0_displayOrder = rate0_displayOrder.substring(0,rate0_displayOrder.indexOf(".")+3);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate4.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount4.xpath)).click();
		Thread.sleep(2000);
		//<amount type="0"> × <rate type="0">
		BigDecimal bd = new BigDecimal(totalAmount0_displayOrder*Double.valueOf(rate0_displayOrder));
		int totalAmount0 = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_FLOOR).toString().split("\\.")[0]);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount4.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_2_2 Pass");
		}else{
			System.out.println("totalAmount0_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder = "+ totalAmount0_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * <amount type="0"> × <rate type="0">
	 **/
	@Test
	public void totalAmount0_2_3() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		String rate0_displayOrder = String.valueOf(Math.random()*999.999);
		rate0_displayOrder = rate0_displayOrder.substring(0,rate0_displayOrder.indexOf(".")+4);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).click();
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate5.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount5.xpath)).click();
		Thread.sleep(2000);
		//<amount type="0"> × <rate type="0">
		BigDecimal bd = new BigDecimal(totalAmount0_displayOrder*Double.valueOf(rate0_displayOrder));
		int totalAmount0 = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_FLOOR).toString().split("\\.")[0]);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount5.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_2_2 Pass");
		}else{
			System.out.println("totalAmount0_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder = "+ totalAmount0_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
		}
		//close
		driver.findElement(By.xpath(socialInsuranceCalculation.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(socialInsuranceCalculation.cancelSave.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * <amount type="0"> × <rate type="0">
	 **/
	@Test
	public void totalAmount0_2_4() throws Exception {
		Common.forwardToPage(topPage.projectID.getDatas().get("socialinsurancecalculation"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.BaseAmountPayrollSummarySheet.xpath)));
		//forward to BaseAmountPayrollSummarySheet page 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountForm.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(socialInsuranceCalculation.InsuranceAmountFormCaption.xpath)));
		int totalAmount0_displayOrder = (int)(Math.random()*1000000);
		String rate0_displayOrder = String.valueOf(Math.random()*999.999);
		rate0_displayOrder = rate0_displayOrder.substring(0,rate0_displayOrder.indexOf(".")+4);
		//input the value 
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount6.xpath)).sendKeys(String.valueOf(totalAmount0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Rate6.xpath)).sendKeys(String.valueOf(rate0_displayOrder));
		driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3Amount6.xpath)).click();
		Thread.sleep(2000);
		//<amount type="0"> × <rate type="0">
		BigDecimal bd = new BigDecimal(totalAmount0_displayOrder*Double.valueOf(rate0_displayOrder));
		int totalAmount0 = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_FLOOR).toString().split("\\.")[0]);
		//get value
		String actual = driver.findElement(By.xpath(socialInsuranceCalculation.InsuranceAmountFormSubject3TotalAmount6.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(totalAmount0)).equals(actual.substring(0,actual.length()-1))){
			System.out.println("totalAmount0_2_2 Pass");
		}else{
			System.out.println("totalAmount0_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(totalAmount0))+"]> but was: <["+actual.substring(0,actual.length()-1)+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(totalAmount0))+"\r\n"+
					"totalAmount0_displayOrder = "+ totalAmount0_displayOrder+"\r\n"+
					"rate0_displayOrder = "+ rate0_displayOrder+"\r\n"+
					"actual = "+ actual.substring(0,actual.length()-1));
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