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
 * Display the calculated value of overtime work allowance. 
 **/
public class ID630 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	static DecimalFormat df = new DecimalFormat("#.###");  
	public static String weekdayNormalHourly = null;
	public static String weekdayLateNightHourly = null;
	public static String holidayNormalHourly = null;
	public static String holidayLateNightHourly = null;
	public static String statutoryHolidayNormalHourly = null;
	public static String statutoryHolidayLateNightHourly = null;
	public static String overSixtyWorkHourly = null;
	public static String overFortyFiveWorkHourly = null;
	public static String monthlyWorkerOvertimeWorkBaseHours = null;
	public static String dailyWorkerOverWorkBaseHours = null;
	public static String hourlyWorkerOverworkBaseHours  = null;
	
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		weekdayNormalHourly = String.valueOf(df.format((Math.random()*10)));
		weekdayLateNightHourly = String.valueOf(df.format((Math.random()*10)));
		holidayNormalHourly = String.valueOf(df.format((Math.random()*10)));
		holidayLateNightHourly = String.valueOf(df.format((Math.random()*10)));
		statutoryHolidayNormalHourly = String.valueOf(df.format((Math.random()*10)));
		statutoryHolidayLateNightHourly = String.valueOf(df.format((Math.random()*10)));
		overSixtyWorkHourly = String.valueOf(df.format((Math.random()*10)));
		overFortyFiveWorkHourly = String.valueOf(df.format((Math.random()*10)));
		monthlyWorkerOvertimeWorkBaseHours = String.valueOf((int)(Math.random()*249));
		dailyWorkerOverWorkBaseHours = String.valueOf((int)(Math.random()*25));
		hourlyWorkerOverworkBaseHours  = String.valueOf((int)(Math.random()*249));
		
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addEmployeeInfo(driver);
		changeCompanyInfo(driver,weekdayNormalHourly+"-"+weekdayLateNightHourly+"-"+holidayNormalHourly+"-"+holidayLateNightHourly+"-"+statutoryHolidayNormalHourly+"-"+statutoryHolidayLateNightHourly+"-"+overSixtyWorkHourly+"-"+overFortyFiveWorkHourly+"_"+monthlyWorkerOvertimeWorkBaseHours+","+dailyWorkerOverWorkBaseHours+","+hourlyWorkerOverworkBaseHours);
		changeAllowanceDeductionInfo(driver);
	}

	/**
	 * If Employee#calculateUnitSalaryFlag is false
	 **/
	@Test
	public void id630_1() throws Exception {
		String weekdayNormalHourly = String.valueOf((int) (Math.random()*100000));
		String weekdayLateNightHourly = String.valueOf((int) (Math.random()*100000));
		String holidayNormalHourly = String.valueOf((int) (Math.random()*100000));
		String holidayLateNightHourly = String.valueOf((int) (Math.random()*100000));
		String statutoryHolidayNormalHourly = String.valueOf((int) (Math.random()*100000));
		String statutoryHolidayLateNightHourly = String.valueOf((int) (Math.random()*100000));
		String overSixtyWorkHourly = String.valueOf((int) (Math.random()*100000));
		String overFortyFiveWorkHourly = String.valueOf((int) (Math.random()*100000));
		
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		changeEmployeeInfo(driver,"false",weekdayNormalHourly+"-"+weekdayLateNightHourly+"-"+holidayNormalHourly+"-"+holidayLateNightHourly+"-"+statutoryHolidayNormalHourly+"-"+statutoryHolidayLateNightHourly+"-"+overSixtyWorkHourly+"-"+overFortyFiveWorkHourly);
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//C = (#id210+#id220/60)×Employee#weekdayNormalHourlyPay (*2)
		//+(#id230+#id240/60)×Employee#weekdayLateNightHourlyPay (*2)
		//+(#id250+#id260/60)×Employee#holidayNormalHourlyPay (*2)
		//+(#id270+#id280/60)×Employee#holidayLateNightHourlyPay (*2)
		//+(#id290+#id300/60)×Employee#statutoryHolidayNormalHourlyPay (*2)
		//+(#id310+#id320/60)×Employee#statutoryHolidayLateNightHourlyPay (*2)
		//+(#id330+#id340/60)×Employee#overSixtyWorkHourlyPay (*2)
		//+(#id350+#id360/60)×Employee#overFortyFiveWorkHourlyPay (*2)
		//#id630 = C
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Integer.parseInt(weekdayNormalHourly))+
						(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Integer.parseInt(weekdayLateNightHourly))+
						(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Integer.parseInt(holidayNormalHourly))+
						(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Integer.parseInt(holidayLateNightHourly))+
						(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Integer.parseInt(statutoryHolidayNormalHourly))+
						(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Integer.parseInt(statutoryHolidayLateNightHourly))+
						(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Integer.parseInt(overSixtyWorkHourly))+
						(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Integer.parseInt(overFortyFiveWorkHourly)));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_1 Pass");
		}else{
			System.out.println("id630_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"weekdayNormalHourly = "+ weekdayNormalHourly+"\r\n"+
					"weekdayLateNightHourly = "+ weekdayLateNightHourly+"\r\n"+
					"holidayNormalHourly = "+ holidayNormalHourly+"\r\n"+
					"holidayLateNightHourly = "+ holidayLateNightHourly+"\r\n"+
					"statutoryHolidayNormalHourly = "+ statutoryHolidayNormalHourly+"\r\n"+
					"statutoryHolidayLateNightHourly = "+ statutoryHolidayLateNightHourly+"\r\n"+
					"overSixtyWorkHourly = "+ overSixtyWorkHourly+"\r\n"+
					"overFortyFiveWorkHourly = "+ overFortyFiveWorkHourly+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * E(unit price of monthly worker) =((#id390(basic salary) + D )/OvertimeWorkNoWorkSetting#monthlyWorkerOvertimeWorkBaseHours)
	 * If Employee#paymentCycleType is 'monthly'
	 **/
	@Test
	public void id630_2_1() throws Exception {
		String value = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"monthly",value);

		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//H = [(#id210+#id220/60)× { G ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { G ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { G ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { G ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { G ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { G ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = H
		String D = String.valueOf(Integer.parseInt(value)+id410+id430+id450+id470+id490+id510);
		double E = Double.valueOf((D))/Double.valueOf(monthlyWorkerOvertimeWorkBaseHours);
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(E*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(E*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(E*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(E*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(E*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(E*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(E*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(E*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_2_1 Pass");
		}else{
			System.out.println("id630_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * G(unit price of daily worker) = g
	 * If Employee#dailyPay1 has value, and both Employee#dailyPay2 and Employee#dailyPay3 are empty.
	 * g = ((Employee#dailyPay1 )/OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours) + ( D / (OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours × #id130))　
	 **/
	@Test
	public void id630_2_2_1() throws Exception {
		String value = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value);
		
		int id130 = (int) (Math.random()*32);
		//input the value
		driver.findElement(By.xpath(paymentStatements.attendanceDays.xpath)).sendKeys(String.valueOf(id130));
		driver.findElement(By.xpath(paymentStatements.attendanceHours.xpath)).click();
		Thread.sleep(2000);
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//H = [(#id210+#id220/60)× { G ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { G ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { G ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { G ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { G ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { G ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = H
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double G = Double.valueOf(value)/Double.valueOf(dailyWorkerOverWorkBaseHours)+
				   Double.valueOf(D)/(Double.valueOf(dailyWorkerOverWorkBaseHours)*id130);
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(G*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(G*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(G*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(G*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(G*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(G*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_2_2_1 Pass");
		}else{
			System.out.println("id630_2_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id130 = "+ id130+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * G(unit price of daily worker) = g
	 * If both Employee#dailyPay1 and Employee#dailyPay2 have value
	 * g = ((Employee#dailyPay1+Employee#dailyPay2 )/(OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours*2)) + ( D /(OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours ×(#id100+#id110)))　
	 **/
	@Test
	public void id630_2_2_2() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value1+"_"+value2);
		
		int id100 = (int) (Math.random()*32);
		int id110 = (int) (Math.random()*32);
		//input the value
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).sendKeys(String.valueOf(id100));
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).sendKeys(String.valueOf(id110));
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).click();
		Thread.sleep(2000);
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//H = [(#id210+#id220/60)× { G ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { G ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { G ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { G ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { G ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { G ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = H
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double G = (Double.valueOf(value1)+Double.valueOf(value2))/(Double.valueOf(dailyWorkerOverWorkBaseHours)*2)+
				   Double.valueOf(D)/(Double.valueOf(dailyWorkerOverWorkBaseHours)*(id100+id110));
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(G*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(G*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(G*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(G*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(G*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(G*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_2_2_2 Pass");
		}else{
			System.out.println("id630_2_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value1 = "+ value1+"\r\n"+
					"value2 = "+ value2+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * If all of Employee#dailyPay1 and Employee#dailyPay2 and Employee#dailyPay3 have more tha '1' value
	 * g = ((Employee#dailyPay1+Employee#dailyPay2+Employee#dailyPay3 )/(OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours*3))+ ( D / (OvertimeWorkNoWorkSetting#dailyWorkerOverWorkBaseHours × (#id100+#id110+#id120)))　
	 **/
	@Test
	public void id630_2_2_3() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		String value3 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"daily",value1+"-"+value2+"-"+value3);
		
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
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//H = [(#id210+#id220/60)× { G ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { G ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { G ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { G ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { G ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { G ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { G ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = H
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double G = (Double.valueOf(value1)+Double.valueOf(value2)+Double.valueOf(value3))/(Double.valueOf(dailyWorkerOverWorkBaseHours)*3)+
				   Double.valueOf(D)/(Double.valueOf(dailyWorkerOverWorkBaseHours)*(id100+id110+id120));
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(G*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(G*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(G*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(G*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(G*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(G*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(G*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_2_2_3 Pass");
		}else{
			System.out.println("id630_2_2_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value1 = "+ value1+"\r\n"+
					"value2 = "+ value2+"\r\n"+
					"value3 = "+ value3+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * I(unit price of hourly worker) = i 
	 * If Employee#hourlyPay1 has value, and both Employee#hourlyPay2 and Employee#hourlyPay3 are empty.
	 * i = Employee#hourlyPay1 + ( D / OvertimeWorkNoWorkSetting#hourlyWorkerOverworkBaseHours )
	 **/
	@Test
	public void id630_3_1() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1);
		
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//J = [(#id210+#id220/60)× { I ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { I ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { I ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { I ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { I ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { I ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = J
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double J = Double.valueOf(value1)+
				   Double.valueOf(D)/(Double.valueOf(hourlyWorkerOverworkBaseHours));
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(J*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(J*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(J*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(J*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(J*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(J*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_3_1 Pass");
		}else{
			System.out.println("id630_3_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value1 = "+ value1+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * I(unit price of hourly worker) = i 
	 * If both Employee#hourlyPay1 and Employee#hourlyPay2 have value
	 * i = ( Employee#hourlyPay1 + Employee#hourlyPay2 ) / 2 + ( D / ( OvertimeWorkNoWorkSetting#hourlyWorkerOverworkBaseHours ) )
	 **/
	@Test
	public void id630_3_2() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1+"_"+value2);

		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//J = [(#id210+#id220/60)× { I ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { I ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { I ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { I ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { I ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { I ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = J
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double J = (Double.valueOf(value1)+Double.valueOf(value2))/2+
				   Double.valueOf(D)/(Double.valueOf(hourlyWorkerOverworkBaseHours));
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(J*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(J*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(J*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(J*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(J*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(J*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_3_2 Pass");
		}else{
			System.out.println("id630_3_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value1 = "+ value1+"\r\n"+
					"value2 = "+ value2+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#calculateUnitSalaryFlag is true
	 * D(sum of allowance amount for automated calculation) = Sum of the allowance amount that 'unit price of overtime work' of VoluntaryItemSetting#allowanceSetting is off among #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
	 * I(unit price of hourly worker) = i 
	 * If all of Employee#hourlyPay1 and Employee#hourlyPay2 and Employee#hourlyPay3 have more tha '1' value
	 * i = ( Employee#hourlyPay1 + Employee#hourlyPay2 + Employee#hourlyPay3 ) / 3 + ( D / ( OvertimeWorkNoWorkSetting#hourlyWorkerOverworkBaseHours ) )
	 **/
	@Test
	public void id630_3_3() throws Exception {
		String value1 = String.valueOf((int) (Math.random()*100000));
		String value2 = String.valueOf((int) (Math.random()*100000));
		String value3 = String.valueOf((int) (Math.random()*100000));
		changeEmployeeInfo(driver,"hourly",value1+"-"+value2+"-"+value3);
		
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int id340 = (int) (Math.random()*60);
		int id360 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		int id210 = (int) (Math.random()*200);
		int id230 = (int) (Math.random()*200);
		int id250 = (int) (Math.random()*200);
		int id270 = (int) (Math.random()*200);
		int id290 = (int) (Math.random()*200);
		int id310 = (int) (Math.random()*200);
		int id330 = (int) (Math.random()*200);
		int id350 = (int) (Math.random()*200);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).sendKeys(String.valueOf(id340));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveMinute.xpath)).sendKeys(String.valueOf(id360));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixHours.xpath)).sendKeys(String.valueOf(id330));
		driver.findElement(By.xpath(paymentStatements.overFourtyFiveHours.xpath)).sendKeys(String.valueOf(id350));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		
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
		String actual = driver.findElement(By.xpath(paymentStatements.overtimePay.xpath)).getAttribute("value");
		//J = [(#id210+#id220/60)× { I ×OvertimeWorkNoWorkSetting#weekdayNormalRate (*2) (stored in PaySlip#weekdayNormalUnitPrice) }](*2)
		//+ [(#id230+#id240/60)× { I ×OvertimeWorkNoWorkSetting#weekdayLateNightRate (*2) (stored in PaySlip#weekdayLateNightUnitPrice) }](*2)
		//+ [(#id250+#id260/60)× { I ×OvertimeWorkNoWorkSetting#holidayNormalRate (*2) (stored in PaySlip#holidayNormalUnitPrice) }](*2)
		//+ [(#id270+#id280/60)× { I ×OvertimeWorkNoWorkSetting#holidayLateNightRate (*2) (stored in PaySlip#holidayLateNightUnitPrice) }](*2)
		//+ [(#id290+#id300/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayNormalRate (*2) (stored in PaySlip#statutoryNormalUnitPrice) }](*2)
		//+ [(#id310+#id320/60)× { I ×OvertimeWorkNoWorkSetting#statutoryHolidayLateNightRate (*2) (stored in PaySlip#statutoryLateNightUnitPrice) }](*2)
		//+ [(#id330+#id340/60)× { I ×OvertimeWorkNoWorkSetting#overSixtyWorkRate (*2) }](*2)
		//+ [(#id350+#id360/60)× { I ×OvertimeWorkNoWorkSetting#overFortyFiveWorkRate (*2) }](*2)
		//#id630 = J
		String D = String.valueOf(id410+id430+id450+id470+id490+id510);
		double J = (Double.valueOf(value1)+Double.valueOf(value2)+Double.valueOf(value3))/3+
				   Double.valueOf(D)/(Double.valueOf(hourlyWorkerOverworkBaseHours));
		String expected = 
		 String.valueOf((int)Math.ceil((id210+Double.valueOf(String.valueOf(id220))/60)*Math.ceil(J*Double.valueOf(weekdayNormalHourly)))+
					(int)Math.ceil((id230+Double.valueOf(String.valueOf(id240))/60)*Math.ceil(J*Double.valueOf(weekdayLateNightHourly)))+
					(int)Math.ceil((id250+Double.valueOf(String.valueOf(id260))/60)*Math.ceil(J*Double.valueOf(holidayNormalHourly)))+
					(int)Math.ceil((id270+Double.valueOf(String.valueOf(id280))/60)*Math.ceil(J*Double.valueOf(holidayLateNightHourly)))+
					(int)Math.ceil((id290+Double.valueOf(String.valueOf(id300))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayNormalHourly)))+
					(int)Math.ceil((id310+Double.valueOf(String.valueOf(id320))/60)*Math.ceil(J*Double.valueOf(statutoryHolidayLateNightHourly)))+
					(int)Math.ceil((id330+Double.valueOf(String.valueOf(id340))/60)*Math.ceil(J*Double.valueOf(overSixtyWorkHourly)))+
					(int)Math.ceil((id350+Double.valueOf(String.valueOf(id360))/60)*Math.ceil(J*Double.valueOf(overFortyFiveWorkHourly))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id630_3_3 Pass");
		}else{
			System.out.println("id630_3_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"value1 = "+ value1+"\r\n"+
					"value2 = "+ value2+"\r\n"+
					"value3 = "+ value3+"\r\n"+
					"int id410 = "+ id410+"\r\n"+
					"int id430 = "+ id430+"\r\n"+
					"int id450 = "+ id450+"\r\n"+
					"int id470 = "+ id470+"\r\n"+
					"int id490 = "+ id490+"\r\n"+
					"int id510 = "+ id510+"\r\n"+
					"int id530 = "+ id530+"\r\n"+
					"int id550 = "+ id550+"\r\n"+
					"int id570 = "+ id570+"\r\n"+
					"int id590 = "+ id590+"\r\n"+
					"int id610 = "+ id610+"\r\n"+
					"id220 = "+ id220+"\r\n"+
					"id240 = "+ id240+"\r\n"+
					"id260 = "+ id260+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id300 = "+ id300+"\r\n"+
					"id320 = "+ id320+"\r\n"+
					"id340 = "+ id340+"\r\n"+
					"id360 = "+ id360+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"id230 = "+ id230+"\r\n"+
					"id250 = "+ id250+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id290 = "+ id290+"\r\n"+
					"id310 = "+ id310+"\r\n"+
					"id330 = "+ id330+"\r\n"+
					"id350 = "+ id350);
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
			driver.findElement(By.xpath(employeeSetting.weekdayNormal.xpath)).sendKeys(value.split("-")[0]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.weekdayLateNight.xpath)).sendKeys(value.split("-")[1]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.holidayNormal.xpath)).sendKeys(value.split("-")[2]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.holidayLateNight.xpath)).sendKeys(value.split("-")[3]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.statutoryHolidayNormal.xpath)).sendKeys(value.split("-")[4]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.statutoryHolidayLateNight.xpath)).sendKeys(value.split("-")[5]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.overSixtyHoursWork.xpath)).sendKeys(value.split("-")[6]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(employeeSetting.overFourtyHoursWork.xpath)).sendKeys(value.split("-")[7]);
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
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(3000);
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
		driver.findElement(By.xpath(clientSetting.weekdayNormal.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.weekdayNormal.xpath)).sendKeys(value.split("-")[0]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.weekdaylateNight.xpath)).click();
		driver.findElement(By.xpath(clientSetting.weekdaylateNight.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.weekdaylateNight.xpath)).sendKeys(value.split("-")[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.holidaynormal.xpath)).click();
		driver.findElement(By.xpath(clientSetting.holidaynormal.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.holidaynormal.xpath)).sendKeys(value.split("-")[2]);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.holidaylateNight.xpath)).click();
		driver.findElement(By.xpath(clientSetting.holidaylateNight.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.holidaylateNight.xpath)).sendKeys(value.split("-")[3]);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.statutoryHolidayNormal.xpath)).click();
		driver.findElement(By.xpath(clientSetting.statutoryHolidayNormal.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.statutoryHolidayNormal.xpath)).sendKeys(value.split("-")[4]);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.statutoryHolidayLateNight.xpath)).click();
		driver.findElement(By.xpath(clientSetting.statutoryHolidayLateNight.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.statutoryHolidayLateNight.xpath)).sendKeys(value.split("-")[5]);
		Thread.sleep(1000);		
		if(!driver.findElement(By.xpath(clientSetting.overSixtyHourWorkPremiumRate.xpath)).isSelected()){
			driver.findElement(By.xpath(clientSetting.overSixtyHourWorkPremiumRate.xpath)).click();
		}
		driver.findElement(By.xpath(clientSetting.overSixtyHourWorkPremiumRate2.xpath)).click();
		driver.findElement(By.xpath(clientSetting.overSixtyHourWorkPremiumRate2.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.overSixtyHourWorkPremiumRate2.xpath)).sendKeys(value.split("-")[6]);
		Thread.sleep(1000);		
		if(!driver.findElement(By.xpath(clientSetting.overFourtyHourWorkPremiumRate.xpath)).isSelected()){
			driver.findElement(By.xpath(clientSetting.overFourtyHourWorkPremiumRate.xpath)).click();
		}
		driver.findElement(By.xpath(clientSetting.overFourtyHourWorkPremiumRate2.xpath)).click();
		driver.findElement(By.xpath(clientSetting.overFourtyHourWorkPremiumRate2.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.overFourtyHourWorkPremiumRate2.xpath)).sendKeys(value.split("-")[7]);
		Thread.sleep(1000);
		//input the value
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeOvertime.xpath)).click();
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeOvertime.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.monthlyEmployeeOvertime.xpath)).sendKeys(value.split("_")[1].split(",")[0]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.dailyEmployeeOvertime.xpath)).click();
		driver.findElement(By.xpath(clientSetting.dailyEmployeeOvertime.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.dailyEmployeeOvertime.xpath)).sendKeys(value.split("_")[1].split(",")[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeOvertime.xpath)).click();
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeOvertime.xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);		
		driver.findElement(By.xpath(clientSetting.hourlyEmployeeOvertime.xpath)).sendKeys(value.split("_")[1].split(",")[2]);
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
		driver.findElement(By.xpath(paymentStatements.unitPriceOfOverWork7.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unitPriceOfOverWork8.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unitPriceOfOverWork9.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unitPriceOfOverWork10.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.unitPriceOfOverWork11.xpath)).click();
		Thread.sleep(1000);
		//save the data
		driver.findElement(By.xpath(paymentStatements.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);	
	}
}