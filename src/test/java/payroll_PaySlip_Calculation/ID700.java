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
 * Display the calculated amount of unemployment insurance.
 * PayrollCalculationSetting#joinedLaborInsuranceFlag is true.
 * Employee#insuredUnemploymentInsuranceType is 'insured person'.
 * T(sum of allowance amount for unemployment insurance) = sum of allowance amount that 'unemployment insurance' of VoluntaryItemSetting#allowanceSetting is off amoung #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610. 
 **/
public class ID700 extends TestBase{
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
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * If PayrollCalculationSetting#unemploymentInsuranceType is Employee#unemploymentInsuranceType
	 **/
//	@Test
	public void id700_1() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"general");
		changeEmployeeInfo(driver,"general");
		
		int id390 = (int)(Math.random()*10000000);
		int id410 = (int)(Math.random()*10000000);
		int id430 = (int)(Math.random()*10000000);
		int id450 = (int)(Math.random()*10000000);
		int id470 = (int)(Math.random()*10000000);
		int id490 = (int)(Math.random()*10000000);
		int id510 = (int)(Math.random()*10000000);
		int id530 = (int)(Math.random()*10000000);
		int id550 = (int)(Math.random()*10000000);
		int id570 = (int)(Math.random()*10000000);
		int id590 = (int)(Math.random()*10000000);
		int id610 = (int)(Math.random()*10000000);
		int id620 = (int)(Math.random()*10000000);
		int id630 = (int)(Math.random()*10000000);
		int id640 = (int)(Math.random()*10000000);
		int sum = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
		//input the value
		Common.clear(driver,paymentStatements.baseSalary.xpath);		
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance2.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance3.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance4.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance5.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance6.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance7.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance8.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance9.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance10.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance11.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.travelAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).sendKeys(String.valueOf(id620));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.overtimePay.xpath);
		driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).sendKeys(String.valueOf(id630));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.nonEmploymentDeduction.xpath);
		driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).sendKeys(String.valueOf(id640));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//#id700 = (#id390(basic salary)+ T + #id620(commuting allowance)+#id630(overtime work allowance)-#id640)×{the latest ClientUnemploymentBenefit#insuredPersonPaymentProportion that ClientUnemploymentBenefit#useStartMont <= year and month of the selected PayrollMonth#paymentDate} rounded to the nearest whole number according to PayrollCalculationSetting#laborInsuranceCalcRoundingMethod.(*1)
		int T = id410+id430+id450+id470+id490+id510;
		int expected = Common.roundHalfDown(String.valueOf((id390+T+id620+id630-id640)),insuredPersonPaymentProportion);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.employeeInsurance.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id700_1 Pass");
		}else{
			System.out.println("id700_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id390 ="+ id390+"\r\n"+
					"id410 ="+ id410+"\r\n"+
					"id430 ="+ id430+"\r\n"+
					"id450 ="+ id450+"\r\n"+
					"id470 ="+ id470+"\r\n"+
					"id490 ="+ id490+"\r\n"+
					"id510 ="+ id510+"\r\n"+
					"id530 ="+ id530+"\r\n"+
					"id550 ="+ id550+"\r\n"+
					"id570 ="+ id570+"\r\n"+
					"id590 ="+ id590+"\r\n"+
					"id610 ="+ id610+"\r\n"+
					"id620 ="+ id620+"\r\n"+
					"id630 ="+ id630+"\r\n"+
					"id640 ="+ id640+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#unemploymentInsuranceType is 'general' 
	 **/
	@Test
	public void id700_2_1() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"general");
		changeEmployeeInfo(driver,"general");
		
		int id390 = (int)(Math.random()*10000000);
		int id410 = (int)(Math.random()*10000000);
		int id430 = (int)(Math.random()*10000000);
		int id450 = (int)(Math.random()*10000000);
		int id470 = (int)(Math.random()*10000000);
		int id490 = (int)(Math.random()*10000000);
		int id510 = (int)(Math.random()*10000000);
		int id530 = (int)(Math.random()*10000000);
		int id550 = (int)(Math.random()*10000000);
		int id570 = (int)(Math.random()*10000000);
		int id590 = (int)(Math.random()*10000000);
		int id610 = (int)(Math.random()*10000000);
		int id620 = (int)(Math.random()*10000000);
		int id630 = (int)(Math.random()*10000000);
		int id640 = (int)(Math.random()*10000000);
		int sum = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
		//input the value
		Common.clear(driver,paymentStatements.baseSalary.xpath);		
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance2.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance3.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance4.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance5.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance6.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance7.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance8.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance9.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance10.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance11.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.travelAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).sendKeys(String.valueOf(id620));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.overtimePay.xpath);
		driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).sendKeys(String.valueOf(id630));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.nonEmploymentDeduction.xpath);
		driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).sendKeys(String.valueOf(id640));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//#id700 = (#id390(basic salary)+ T + #id620(commuting allowance)+#id630(overtime work allowance)-#id640)×{the latest ClientUnemploymentBenefit#insuredPersonPaymentProportion that ClientUnemploymentBenefit#useStartMont <= year and month of the selected PayrollMonth#paymentDate} rounded to the nearest whole number according to PayrollCalculationSetting#laborInsuranceCalcRoundingMethod.(*1)
		int T = id410+id430+id450+id470+id490+id510;
		int expected = Common.roundHalfDown(String.valueOf((id390+T+id620+id630-id640)),insuredPersonPaymentProportion);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.employeeInsurance.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id700_2_1 Pass");
		}else{
			System.out.println("id700_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id390 ="+ id390+"\r\n"+
					"id410 ="+ id410+"\r\n"+
					"id430 ="+ id430+"\r\n"+
					"id450 ="+ id450+"\r\n"+
					"id470 ="+ id470+"\r\n"+
					"id490 ="+ id490+"\r\n"+
					"id510 ="+ id510+"\r\n"+
					"id530 ="+ id530+"\r\n"+
					"id550 ="+ id550+"\r\n"+
					"id570 ="+ id570+"\r\n"+
					"id590 ="+ id590+"\r\n"+
					"id610 ="+ id610+"\r\n"+
					"id620 ="+ id620+"\r\n"+
					"id630 ="+ id630+"\r\n"+
					"id640 ="+ id640+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#unemploymentInsuranceType is 'construction' 
	 **/
	@Test
	public void id700_2_2() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"construction");
		changeEmployeeInfo(driver,"construction");
		
		int id390 = (int)(Math.random()*10000000);
		int id410 = (int)(Math.random()*10000000);
		int id430 = (int)(Math.random()*10000000);
		int id450 = (int)(Math.random()*10000000);
		int id470 = (int)(Math.random()*10000000);
		int id490 = (int)(Math.random()*10000000);
		int id510 = (int)(Math.random()*10000000);
		int id530 = (int)(Math.random()*10000000);
		int id550 = (int)(Math.random()*10000000);
		int id570 = (int)(Math.random()*10000000);
		int id590 = (int)(Math.random()*10000000);
		int id610 = (int)(Math.random()*10000000);
		int id620 = (int)(Math.random()*10000000);
		int id630 = (int)(Math.random()*10000000);
		int id640 = (int)(Math.random()*10000000);
		int sum = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
		//input the value
		Common.clear(driver,paymentStatements.baseSalary.xpath);		
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance2.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance3.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance4.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance5.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance6.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance7.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance8.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance9.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance10.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance11.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.travelAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).sendKeys(String.valueOf(id620));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.overtimePay.xpath);
		driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).sendKeys(String.valueOf(id630));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.nonEmploymentDeduction.xpath);
		driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).sendKeys(String.valueOf(id640));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//#id700 = (#id390(basic salary)+ T + #id620(commuting allowance)+#id630(overtime work allowance)-#id640)×{the latest ClientUnemploymentBenefit#insuredPersonPaymentProportion that ClientUnemploymentBenefit#useStartMont <= year and month of the selected PayrollMonth#paymentDate} rounded to the nearest whole number according to PayrollCalculationSetting#laborInsuranceCalcRoundingMethod.(*1)
		int T = id410+id430+id450+id470+id490+id510;
		int expected = Common.roundHalfDown(String.valueOf((id390+T+id620+id630-id640)),insuredPersonPaymentProportion);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.employeeInsurance.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id700_2_2 Pass");
		}else{
			System.out.println("id700_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id390 ="+ id390+"\r\n"+
					"id410 ="+ id410+"\r\n"+
					"id430 ="+ id430+"\r\n"+
					"id450 ="+ id450+"\r\n"+
					"id470 ="+ id470+"\r\n"+
					"id490 ="+ id490+"\r\n"+
					"id510 ="+ id510+"\r\n"+
					"id530 ="+ id530+"\r\n"+
					"id550 ="+ id550+"\r\n"+
					"id570 ="+ id570+"\r\n"+
					"id590 ="+ id590+"\r\n"+
					"id610 ="+ id610+"\r\n"+
					"id620 ="+ id620+"\r\n"+
					"id630 ="+ id630+"\r\n"+
					"id640 ="+ id640+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#unemploymentInsuranceType is 'other' 
	 **/
	@Test
	public void id700_2_3() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"other");
		changeEmployeeInfo(driver,"other");
		
		int id390 = (int)(Math.random()*10000000);
		int id410 = (int)(Math.random()*10000000);
		int id430 = (int)(Math.random()*10000000);
		int id450 = (int)(Math.random()*10000000);
		int id470 = (int)(Math.random()*10000000);
		int id490 = (int)(Math.random()*10000000);
		int id510 = (int)(Math.random()*10000000);
		int id530 = (int)(Math.random()*10000000);
		int id550 = (int)(Math.random()*10000000);
		int id570 = (int)(Math.random()*10000000);
		int id590 = (int)(Math.random()*10000000);
		int id610 = (int)(Math.random()*10000000);
		int id620 = (int)(Math.random()*10000000);
		int id630 = (int)(Math.random()*10000000);
		int id640 = (int)(Math.random()*10000000);
		int sum = id390+id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610+id620+id630-id640;
		
		//input the value
		Common.clear(driver,paymentStatements.baseSalary.xpath);		
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).sendKeys(String.valueOf(id390));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance2.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance3.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance4.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance5.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance6.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance7.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance8.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance9.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance10.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.baseSalaryAllowance11.xpath);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.travelAllowance.xpath);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).sendKeys(String.valueOf(id620));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.overtimePay.xpath);
		driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).sendKeys(String.valueOf(id630));
		Thread.sleep(1000);
		Common.clear(driver,paymentStatements.nonEmploymentDeduction.xpath);
		driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).sendKeys(String.valueOf(id640));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(paymentStatements.sumPay.xpath)).getText().equals(Common.formatNum(String.valueOf(sum)))){
				driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//#id700 = (#id390(basic salary)+ T + #id620(commuting allowance)+#id630(overtime work allowance)-#id640)×{the latest ClientUnemploymentBenefit#insuredPersonPaymentProportion that ClientUnemploymentBenefit#useStartMont <= year and month of the selected PayrollMonth#paymentDate} rounded to the nearest whole number according to PayrollCalculationSetting#laborInsuranceCalcRoundingMethod.(*1)
		int T = id410+id430+id450+id470+id490+id510;
		int expected = Common.roundHalfDown(String.valueOf((id390+T+id620+id630-id640)),insuredPersonPaymentProportion);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.employeeInsurance.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id700_2_3 Pass");
		}else{
			System.out.println("id700_2_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id390 ="+ id390+"\r\n"+
					"id410 ="+ id410+"\r\n"+
					"id430 ="+ id430+"\r\n"+
					"id450 ="+ id450+"\r\n"+
					"id470 ="+ id470+"\r\n"+
					"id490 ="+ id490+"\r\n"+
					"id510 ="+ id510+"\r\n"+
					"id530 ="+ id530+"\r\n"+
					"id550 ="+ id550+"\r\n"+
					"id570 ="+ id570+"\r\n"+
					"id590 ="+ id590+"\r\n"+
					"id610 ="+ id610+"\r\n"+
					"id620 ="+ id620+"\r\n"+
					"id630 ="+ id630+"\r\n"+
					"id640 ="+ id640+"\r\n"+
					"insuredPersonPaymentProportion = "+ insuredPersonPaymentProportion);
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
	
	public static void changeEmployeeInfo(WebDriver driver,String type) throws Exception{
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
		//change the payment type
		if(type=="general"){
			driver.findElement(By.xpath(employeeSetting.generalBusiness.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="construction"){
			driver.findElement(By.xpath(employeeSetting.bussnissType_construction.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="other"){
			driver.findElement(By.xpath(employeeSetting.bussnissType_other.xpath)).click();
			Thread.sleep(2000);			
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
	}
	
	public static String changeCompanyInfo(WebDriver driver,String type) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		//change the tpye
		if(type=="general"){
			driver.findElement(By.xpath(clientSetting.specialPaymentPeriodFlag.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="construction"){
			driver.findElement(By.xpath(clientSetting.constructionPeriodFlag.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="other"){
			driver.findElement(By.xpath(clientSetting.otherPeriodFlag.xpath)).click();
			Thread.sleep(2000);			
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//get value
		String result = driver.findElement(By.xpath(clientSetting.socialSecurityOldPersonPaymentPr2_dis.xpath)).getText();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(3000);
		Common.forwardToTopPage();
		return result;
	}
	
	public static void changeAllowanceDeductionInfo(WebDriver driver) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.allowanceDeductionSetting.xpath)).click();
		Thread.sleep(1000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
		//input the name
		driver.findElement(By.xpath(paymentStatements.allowanceName.xpath)).sendKeys("1");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name2.xpath)).sendKeys("2");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name3.xpath)).sendKeys("3");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name4.xpath)).sendKeys("4");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name5.xpath)).sendKeys("5");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name6.xpath)).sendKeys("6");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name7.xpath)).sendKeys("7");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name8.xpath)).sendKeys("8");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name9.xpath)).sendKeys("9");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name10.xpath)).sendKeys("10");
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.allowanceName_Name11.xpath)).sendKeys("11");
		Thread.sleep(1000);
		//set allowanceSetting
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit7.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit8.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit9.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit10.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unemployeeBenefit11.xpath)).click();
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
	}
}