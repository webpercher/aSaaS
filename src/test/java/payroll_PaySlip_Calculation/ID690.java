package payroll_PaySlip_Calculation;


import org.junit.*;
import org.openqa.selenium.*;

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
 * Display the calculated amount of pension funds. 
 * PayrollCalculationSetting#joinedSocialInsuranceFlag is true.
 * PayrollCalculationSetting#joinedPensionFundsFlag is true.
 * Employee#pensionInsuranceInsuredPersonFlag is true.
 **/
public class ID690 extends TestBase{
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
	 * If Employee#fixedAmountPensionFundsFlag is false
	 * If Employee#healthInsuranceMonthlyAmount < GeneralRewardMonthlyAmount#generalRewardMonthlyAmount having minimum #socialSecurityGrade
	 **/
	@Test
	public void id690_1_1() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver);
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"false-minimum",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.welfarePensionFund.xpath)).getAttribute("value");
		//S = GeneralRewardMonthlyAmount#generalRewardMonthlyAmount having minimum #socialSecurityGrade ×{the latest PensionFundsRate#insuredPersonPaymentProportion that ClientPensionInsurance#insuranceType='pensionFudns' and ClientPensionInsurance#useStartMonth <= year and month of the selected PayrollMonth#pamentDate}
		int expected = Common.roundHalfDown(healthInsuranceMonthlyAmount,insuredPersonPaymentProportion);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id690_1_1 Pass");
		}else{
			System.out.println("id690_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"healthInsuranceMonthlyAmount = "+ healthInsuranceMonthlyAmount+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountPensionFundsFlag is false
	 * If Employee#healthInsuranceMonthlyAmount > GeneralRewardMonthlyAmount#generalRewardMonthlyAmount having minimum #socialSecurityGrade
	 **/
	@Test
	public void id690_1_2() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver);
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"false-maximum",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.welfarePensionFund.xpath)).getAttribute("value");
		//S = GeneralRewardMonthlyAmount#generalRewardMonthlyAmount having minimum #socialSecurityGrade ×{the latest PensionFundsRate#insuredPersonPaymentProportion that ClientPensionInsurance#insuranceType='pensionFudns' and ClientPensionInsurance#useStartMonth <= year and month of the selected PayrollMonth#paymentDate}
		int expected = Common.roundHalfDown(healthInsuranceMonthlyAmount,insuredPersonPaymentProportion);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id690_1_2 Pass");
		}else{
			System.out.println("id690_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"healthInsuranceMonthlyAmount = "+ healthInsuranceMonthlyAmount+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountPensionFundsFlag is false
	 * If else
	 **/
	@Test
	public void id690_1_3() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver);
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"false-common",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.welfarePensionFund.xpath)).getAttribute("value");
		//S = Employee#healthInsuranceMonthlyAmount×{the latest PensionFundsRate#insuredPersonPaymentProportion that ClientPensionInsurance#insuranceType='pensionFudns' and ClientPensionInsurance#useStartMonth <= year and month od the selected PayrollMonth#paymentDate }
		int expected = Common.roundHalfDown(healthInsuranceMonthlyAmount,insuredPersonPaymentProportion);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id690_1_3 Pass");
		}else{
			System.out.println("id690_1_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"healthInsuranceMonthlyAmount = "+ healthInsuranceMonthlyAmount+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountPensionFundsFlag is true
	 **/
	@Test
	public void id690_2() throws Exception {
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"true",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.welfarePensionFund.xpath)).getAttribute("value");
		//#id680 = Employee#socialSecurityFixedAmount
		int expected = Integer.parseInt(healthInsuranceMonthlyAmount);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id690_2 Pass");
		}else{
			System.out.println("id690_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"healthInsuranceMonthlyAmount = "+ healthInsuranceMonthlyAmount+"\r\n"+
					"value = "+ value);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static String changeEmployeeInfo(WebDriver driver,String type,String value) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(employeeSetting.koMonthlyTable.xpath)).click();
		Thread.sleep(2000);
		//check the button
		if(!driver.findElement(By.xpath(employeeSetting.employmentInsurance.xpath)).isSelected()){
			driver.findElement(By.xpath(employeeSetting.employmentInsurance.xpath)).click();
			Thread.sleep(1000);
		}
		//change the payment type
		if(type.contains("false")){
			if(driver.findElement(By.xpath(employeeSetting.pensionFundsAmount.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.pensionFundsAmount.xpath)).click();
				Thread.sleep(1000);
			}
		}else{
			if(!driver.findElement(By.xpath(employeeSetting.pensionFundsAmount.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.pensionFundsAmount.xpath)).click();
				Thread.sleep(2000);
			}
			//input the value
			Common.clear(driver,employeeSetting.pensionFundsAmount_input.xpath);
			driver.findElement(By.xpath(employeeSetting.pensionFundsAmount_input.xpath)).sendKeys(value);
			Thread.sleep(1000);
		}
		//input the value
		driver.findElement(By.xpath(employeeSetting.generalRewardMonthlyAmount.xpath)).click();
		Thread.sleep(2000);
		int num;
		String newXpath ="";
		String result ="";
		String valueXpath;
		if(type.contains("minimum")){
			num = (int)(Math.random()*4+1);
			newXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x",String.valueOf(num));
			valueXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x","5")+"[3]";
			//get value
			result = driver.findElement(By.xpath(valueXpath)).getText();
		}else if(type.contains("maximum")){
			num = (int)(Math.random()*13+35);
			newXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x",String.valueOf(num));
			valueXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x","34")+"[3]";
			//get value
			result = driver.findElement(By.xpath(valueXpath)).getText();
		}else{
			num = (int)(Math.random()*30+5);
			newXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x",String.valueOf(num));
			valueXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x",String.valueOf(num))+"[3]";
			//get value
			result = driver.findElement(By.xpath(valueXpath)).getText();
		}
		driver.findElement(By.xpath(newXpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).click();
		Thread.sleep(2000);		
		//get value 
		if(type=="true"){
			result = value;
		}
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(2000);
		return result;
	}
	
	public static String changeCompanyInfo(WebDriver driver) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		while(!driver.findElement(By.xpath(clientSetting.weekdayNormal.xpath)).isDisplayed()){
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
			Thread.sleep(1000);	
		}
		if(!driver.findElement(By.xpath(clientSetting.pensionFoundAmount.xpath)).isSelected()){
			driver.findElement(By.xpath(clientSetting.pensionFoundAmount.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//change the data
		Common.clear(driver,clientSetting.pensionFundsAmount_input.xpath);
		double num = Math.random()*100;
		int a = String.valueOf(num).indexOf(".");
		String b = String.valueOf(num).substring(0,a+3);
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_input.xpath)).sendKeys(b);
		Thread.sleep(2000);
		Common.clear(driver,clientSetting.pensionFundsAmount_year.xpath);
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_year.xpath)).sendKeys("23");
		Thread.sleep(2000);
		Common.clear(driver,clientSetting.pensionFundsAmount_month.xpath);
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_month.xpath)).sendKeys("3");
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		String result = driver.findElement(By.xpath(clientSetting.pensionFundsAmount_input_dis.xpath)).getText();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
		return result;
	}
}