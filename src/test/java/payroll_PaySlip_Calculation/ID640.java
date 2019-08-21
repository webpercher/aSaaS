package payroll_PaySlip_Calculation;


import java.text.DecimalFormat;

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
 * Display the calculated value of deduction amount caused by no work and absence. 
 **/
public class ID640 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	static DecimalFormat df = new DecimalFormat("#.###");
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static String noWorkHourlyPay = null;
	public static String absenceDailyPay  = null;
	public static String monthlyEmployeeAbsence  = null;
	public static String monthlyEmployeeNoWork  = null;
	public static String dailyEmployeeNoWork  = null;
	public static String hourlyEmployeeNoWork  = null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		noWorkHourlyPay = String.valueOf(df.format((Math.random()*10)));
		absenceDailyPay = String.valueOf(df.format((Math.random()*10)));
		monthlyEmployeeAbsence = String.valueOf((int)(Math.random()*32));
		monthlyEmployeeNoWork = String.valueOf((int)(Math.random()*249));
		dailyEmployeeNoWork = String.valueOf((int)(Math.random()*25));
		hourlyEmployeeNoWork = String.valueOf((int)(Math.random()*249));
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addEmployeeInfo(driver);
		changeCompanyInfo(driver,absenceDailyPay+"-"+noWorkHourlyPay);
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * If Employee#calculateUnitNoWorkFlag is false
	 **/
	@Test
	public void id640_1() throws Exception {
		String value_absenceDays = String.valueOf((int) (Math.random()*100000));
		String value_noWork = String.valueOf((int) (Math.random()*100000));
		int id180 = (int) (Math.random()*32);
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		changeEmployeeInfo(driver,"false",value_absenceDays+"-"+value_noWork);
		
		//input the value
		driver.findElement(By.xpath(paymentStatements.absenceDays.xpath)).sendKeys(String.valueOf(id180));
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//I = ((#id180)×Employee#absenceDailyPay) (*1)
		//+ ((#id190+#id200/60)×Employee#noWorkHourlyPay) (*1)
		//#id640 = I
		String expected = 
			String.valueOf(
					(int)Math.floor(Double.valueOf(String.valueOf(id180))*Double.valueOf(value_absenceDays))+
					(int)Math.floor((id190+Double.valueOf(String.valueOf(id200))/60)*Double.valueOf(value_noWork)));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_1 Pass");
		}else{
			System.out.println("id640_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value_absenceDays = "+ value_absenceDays+"\r\n"+
					"value_noWork = "+ value_noWork+"\r\n"+
					"id180 = "+ id180+"\r\n"+
					"int id200 = "+ id200);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * K(unit price of absence of monthly worker) ={ (#id390+ J )/OvertimeWorkNoWorkSetting#monthlyWorkerAbsenceBaseDays} rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
	 * L(unit price of no work of monthly worker) ={(#id390+ J )/OvertimeWorkNoWorkSetting#monthlyWorkerNoWorkBaseHours } rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
	 **/
	@Test
	public void id640_2_1() throws Exception {
		String value = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"monthly",value);
		
		int id180 = (int) (Math.random()*32);
		int id190 = (int) (Math.random()*60);
		int id200 = (int) (Math.random()*60);
		
		//input the value
		driver.findElement(By.xpath(paymentStatements.absenceDays.xpath)).sendKeys(String.valueOf(id180));
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//M = ((#id180)× K ×OvertimeWorkNoWorkSetting#absenceRate) 　+ ((#id190+#id200/60)× L ×OvertimeWorkNoWorkSetting#noWorkRate)
		//#id640= M rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
		int J = id410+id430+id450+id470+id490+id510;
		double K = Math.floor(Double.valueOf(String.valueOf(Integer.parseInt(value)+J))/Double.valueOf(monthlyEmployeeAbsence));
		double L = Math.floor(Double.valueOf(String.valueOf(Integer.parseInt(value)+J))/Double.valueOf(monthlyEmployeeNoWork));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf(id180*K*Double.valueOf(absenceDailyPay)))+
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*L*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_2_1 Pass");
		}else{
			System.out.println("id640_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id180 = "+ id180+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * N(unit price of no work of daily worker) = n rounded to the nearest whole number according to
	 **/
	@Test
	public void id640_2_2_1() throws Exception {
		String value = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value);
		
		int id130 = (int) (Math.random()*32);
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		
		//input the value
		driver.findElement(By.xpath(paymentStatements.attendanceDays.xpath)).sendKeys(String.valueOf(id130));
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If Employee#dailyPay1 has value, and both Employee#dailyPay2 and Employee#dailyPay3 are empty
		//n = ((Employee#dailyPay1 )/OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours) + ( J / (OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours × #id130))
		//If Employee#paymentCycleType is 'daily'
		//O = (#id190+#id200/60)× N ×OvertimeWorkNoWorkSetting#noWorkRate)
		//#id640= O rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
		int J = id410+id430+id450+id470+id490+id510;
		double N = Math.floor(Double.valueOf(String.valueOf(Integer.parseInt(value)/Double.valueOf(dailyEmployeeNoWork)+
					Double.valueOf(String.valueOf(J))/(Double.valueOf(dailyEmployeeNoWork)*Double.valueOf(String.valueOf(id130))))));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*N*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_2_2_1 Pass");
		}else{
			System.out.println("id640_2_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * N(unit price of no work of daily worker) = n rounded to the nearest whole number according to
	 **/
	@Test
	public void id640_2_2_2() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value1+"_"+value2);
		
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		
		int id100 = (int) (Math.random()*32);
		int id110 = (int) (Math.random()*32);
		//input the value
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).sendKeys(String.valueOf(id100));
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).sendKeys(String.valueOf(id110));
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).click();
		Thread.sleep(2000);
		//input the value
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If both Employee#dailyPay1 and Employee#dailyPay2 have value
		//n = ((Employee#dailyPay1+Employee#dailyPay2 )/(OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours*2)) + ( J / (OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours × (#id100+#id110)))
		//If Employee#paymentCycleType is 'daily'
		//O = (#id190+#id200/60)× N ×OvertimeWorkNoWorkSetting#noWorkRate)
		//#id640= O rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
		int J = id410+id430+id450+id470+id490+id510;
		double N = Math.floor(
				(Double.valueOf(value1)+Double.valueOf(value2))/(Double.valueOf(dailyEmployeeNoWork)*2)+
				Double.valueOf(String.valueOf(J))/(Double.valueOf(dailyEmployeeNoWork)*(Double.valueOf(String.valueOf(id100))+Double.valueOf(String.valueOf(id110)))));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*N*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_2_2_2 Pass");
		}else{
			System.out.println("id640_2_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id100 = "+ id100+"\r\n"+
					"id110 = "+ id110+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * N(unit price of no work of daily worker) = n rounded to the nearest whole number according to
	 **/
	@Test
	public void id640_2_2_3() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		String value3 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value1+"-"+value2+"-"+value3);
		
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		
		int id100 = (int) (Math.random()*32);
		int id110 = (int) (Math.random()*32);
		int id120 = (int) (Math.random()*32);
		//input the value
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).sendKeys(String.valueOf(id100));
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).sendKeys(String.valueOf(id110));
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_3.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_3.xpath)).sendKeys(String.valueOf(id120));
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).click();
		Thread.sleep(2000);
		//input the value
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If both Employee#dailyPay1 and Employee#dailyPay2 have value
		//n = ((Employee#dailyPay1+Employee#dailyPay2 )/(OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours*2)) + ( J / (OvertimeWorkNoWorkSetting#dailyWorkerNoWorkBaseHours × (#id100+#id110)))
		//If Employee#paymentCycleType is 'daily'
		//O = (#id190+#id200/60)× N ×OvertimeWorkNoWorkSetting#noWorkRate)
		//#id640= O rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod.(*1)
		int J = id410+id430+id450+id470+id490+id510;
		double N = Math.floor(
				(Double.valueOf(value1)+Double.valueOf(value2)+Double.valueOf(value3))/(Double.valueOf(dailyEmployeeNoWork)*3)+
				Double.valueOf(String.valueOf(J))/(Double.valueOf(dailyEmployeeNoWork)*(Double.valueOf(String.valueOf(id100))+Double.valueOf(String.valueOf(id110))+Double.valueOf(String.valueOf(id120)))));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*N*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_2_2_3 Pass");
		}else{
			System.out.println("id640_2_2_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id100 = "+ id100+"\r\n"+
					"id110 = "+ id110+"\r\n"+
					"id120 = "+ id120+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * P(unit price of no work of hourly worker) = p rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod(*1)
	 **/
	@Test
	public void id640_3_1() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1);
		
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		//input the value
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If Employee#dailyPay1 has value, and both Employee#hourlyPay2 and Employee#hourlyPay3 are empty
		//p = Employee#hourlyPay1 + ( J / OvertimeWorkNoWorkSetting#hourlyWorkerNoWorkBaseHours )
		int J = id410+id430+id450+id470+id490+id510;
		double P = Math.floor(
				(Double.valueOf(value1))+
				Double.valueOf(String.valueOf(J))/(Double.valueOf(hourlyEmployeeNoWork)));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*P*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_3_1 Pass");
		}else{
			System.out.println("id640_3_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * P(unit price of no work of hourly worker) = p rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod(*1)
	 **/
	@Test
	public void id640_3_2() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1+"_"+value2);
		
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		//input the value
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If both Employee#hourlyPay1 and Employee#hourlyPay2 have value
		//p = ( Employee#hourlyPay1 + Employee#hourlyPay2 ) / 2 + ( J / (OvertimeWorkNoWorkSetting#hourlyWorkerNoWorkBaseHours ) )
		int J = id410+id430+id450+id470+id490+id510;
		double P = Math.floor(
				((Double.valueOf(value1))+(Double.valueOf(value2)))/2+
				Double.valueOf(String.valueOf(J))/(Double.valueOf(hourlyEmployeeNoWork)));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*P*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_3_2 Pass");
		}else{
			System.out.println("id640_3_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitNoWorkFlag is true
	 * J(sum of deduction amount for automated calculation) = sum of allowance amount that 'unit price of absence and no work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * P(unit price of no work of hourly worker) = p rounded to the nearest whole number according to PayrollCalculationSetting#noWorkCalcRoundingMethod(*1)
	 **/
	@Test
	public void id640_3_3() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		String value3 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1+"-"+value2+"-"+value3);
		
		int id190 = (int) (Math.random()*300);
		int id200 = (int) (Math.random()*60);
		//input the value
		driver.findElement(By.xpath(paymentStatements.noWork_Hours.xpath)).sendKeys(String.valueOf(id190));
		driver.findElement(By.xpath(paymentStatements.noWork_Mins.xpath)).sendKeys(String.valueOf(id200));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(5000);
		
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		//input the value to #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610 and id390
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance.xpath)).sendKeys(String.valueOf(id410));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance2.xpath)).sendKeys(String.valueOf(id430));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance3.xpath)).sendKeys(String.valueOf(id450));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance4.xpath)).sendKeys(String.valueOf(id470));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance5.xpath)).sendKeys(String.valueOf(id490));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance6.xpath)).sendKeys(String.valueOf(id510));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance7.xpath)).sendKeys(String.valueOf(id530));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance8.xpath)).sendKeys(String.valueOf(id550));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance9.xpath)).sendKeys(String.valueOf(id570));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance10.xpath)).sendKeys(String.valueOf(id590));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalaryAllowance11.xpath)).sendKeys(String.valueOf(id610));
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(5000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.nonEmploymentDeduction.xpath)).getAttribute("value");
		//If all of Employee#hourlyPay1 and Employee#hourlyPay2 and Employee#hourlyPay3 have value
		//p = ( Employee#hourlyPay1+Employee#hourlyPay2+Employee#hourlyPay3 ) / 3 + ( J / (OvertimeWorkNoWorkSetting#hourlyWorkerNoWorkBaseHours ) )
		int J = id410+id430+id450+id470+id490+id510;
		double P = Math.floor(
				((Double.valueOf(value1))+(Double.valueOf(value2))+(Double.valueOf(value3)))/3+
				Double.valueOf(String.valueOf(J))/(Double.valueOf(hourlyEmployeeNoWork)));
		String expected = 
			String.valueOf((int)Math.floor(
					Double.valueOf(String.valueOf((id190+Double.valueOf(String.valueOf(id200))/60)*P*Double.valueOf(noWorkHourlyPay)))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id640_3_3 Pass");
		}else{
			System.out.println("id640_3_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id190 = "+ id190+"\r\n"+
					"id200 = "+ id200+"\r\n"+
					"id410 = "+ id410+"\r\n"+
					"id430 = "+ id430+"\r\n"+
					"id450 = "+ id450+"\r\n"+
					"id470 = "+ id470+"\r\n"+
					"id490 = "+ id490+"\r\n"+
					"id510 = "+ id510+"\r\n"+
					"id530 = "+ id530+"\r\n"+
					"id550 = "+ id550+"\r\n"+
					"id570 = "+ id570+"\r\n"+
					"id590 = "+ id590+"\r\n"+
					"id610 = "+ id610);
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
	
	public static void changeEmployeeInfo(WebDriver driver,String paymentType,String value) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(employeeSetting.employeeCode.xpath)));
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//change the payment type
		if(paymentType=="false"){
			//uncheck the calculateUnitSalaryFlag
			if(driver.findElement(By.xpath(employeeSetting.caculateUnitSalary.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.caculateUnitSalary.xpath)).click();
			}
			driver.findElement(By.xpath(employeeSetting.absenceDays.xpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.absenceDays.xpath)).sendKeys(Keys.DELETE);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.absenceDays.xpath)).sendKeys(value.split("-")[0]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.noWork.xpath)).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.noWork.xpath)).sendKeys(Keys.DELETE);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.noWork.xpath)).sendKeys(value.split("-")[1]);
			Thread.sleep(1000);
		}else{
			if(!driver.findElement(By.xpath(employeeSetting.caculateUnitSalary.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.caculateUnitSalary.xpath)).click();
			}
			if(paymentType=="monthly"){
				driver.findElement(By.xpath(employeeSetting.paymentCycleM.xpath)).click();
				Thread.sleep(2000);
				//input the value
				driver.findElement(By.xpath(employeeSetting.monthly.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.monthly.xpath)).sendKeys(Keys.DELETE);
				driver.findElement(By.xpath(employeeSetting.monthly.xpath)).sendKeys(value);
				Thread.sleep(2000);
			}else if(paymentType=="daily"){
				driver.findElement(By.xpath(employeeSetting.paymentCycleDay.xpath)).click();
				Thread.sleep(2000);
				//all value
				if(value.contains("-")){
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("-")[0]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value.split("-")[1]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(value.split("-")[2]);
					Thread.sleep(1000);
				//two value
				}else if(value.contains("_")){
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("_")[0]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value.split("_")[1]);
					Thread.sleep(1000);
				}else{
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value);
					Thread.sleep(1000);
				}
			}else if(paymentType=="hourly"){
				driver.findElement(By.xpath(employeeSetting.paymentCycleHour.xpath)).click();
				Thread.sleep(2000);
				//all value
				if(value.contains("-")){
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("-")[0]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value.split("-")[1]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(value.split("-")[2]);
					Thread.sleep(1000);
				//two value
				}else if(value.contains("_")){
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("_")[0]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value.split("_")[1]);
					Thread.sleep(1000);
				}else{
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);					
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
					Thread.sleep(1000);
					//input the value
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value);
					Thread.sleep(1000);
				}
			}
		}
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'Close' button
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
	}
	
	public static void changeCompanyInfo(WebDriver driver,String value) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.overtimeAndNoWork.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		while(!driver.findElement(By.xpath(clientSetting.weekdayNormal.xpath)).isDisplayed()){
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
			Thread.sleep(2000);	
		}
		//del the value and input the value
		driver.findElement(By.xpath(clientSetting.absence1.xpath)).click();
		driver.findElement(By.xpath(clientSetting.absence1.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.absence1.xpath)).sendKeys(value.split("-")[0]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.noWork2.xpath)).click();
		driver.findElement(By.xpath(clientSetting.noWork2.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.noWork2.xpath)).sendKeys(value.split("-")[1]);
		Thread.sleep(1000);
		//input the data
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeAbsence.xpath)).click();
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeAbsence.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeAbsence.xpath)).sendKeys(monthlyEmployeeAbsence);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeNoWork.xpath)).click();
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeNoWork.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeNoWork.xpath)).sendKeys(monthlyEmployeeNoWork);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.dailyEmployeeNoWork.xpath)).click();
		driver.findElement(By.xpath(clientSetting.dailyEmployeeNoWork.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.dailyEmployeeNoWork.xpath)).sendKeys(dailyEmployeeNoWork);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeNoWork.xpath)).click();
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeNoWork.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeNoWork.xpath)).sendKeys(hourlyEmployeeNoWork);
		Thread.sleep(1000);
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
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
		driver.findElement(By.xpath(paymentStatements.absenceAndNoWork7.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.absenceAndNoWork8.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.absenceAndNoWork9.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.absenceAndNoWork10.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.absenceAndNoWork11.xpath)).click();
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
	}
}