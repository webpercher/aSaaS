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
 * Calculate the basic salary by employee's payment cycle type set at [ SC-PRL-EMP-1-S1Employee]. 
 **/
public class ID390 extends TestBase{
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
		Common.addEmployeeInfo(driver);
	}

	/**
	 * Employee#paymentCycleType is "monthly" id390=Employee#monthlyPay
	 **/
	@Test
	public void id390_1() throws Exception {
		String expected = String.valueOf((int) (Math.random()*100000000));
		changeEmployeeInfo(driver,"monthly",expected);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id390_1 Pass");
		}else{
			System.out.println("id390_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * Employee#paymentCycleType is "daily"
	 * more than 2 of which is Employee#dailyPay1 and Employee#dailyPay2 and Employee#dailyPay3 have value
	 */
	@Test
	public void id390_2_1() throws Exception {
		String basic1 = String.valueOf((int)(Math.random()*100000));
		String basic2 = String.valueOf((int)(Math.random()*100000));
		String basic3 = String.valueOf((int)(Math.random()*100000));
		String day1 = String.valueOf((int)(Math.random()*32));
		String day2 = String.valueOf((int)(Math.random()*32));
		String day3 = String.valueOf((int)(Math.random()*32));
		changeEmployeeInfo(driver,"daily",basic1+","+basic2+","+basic3);
		//input the value to id100 id110 id120
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).sendKeys(day1);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).sendKeys(day2);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_3.xpath)).sendKeys(day3);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).click();
		Thread.sleep(1000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).getAttribute("value");
		String expected = String.valueOf((int)(Math.ceil(Integer.parseInt(day1)*Integer.parseInt(basic1)))+
				(int)(Math.ceil(Integer.parseInt(day2)*Integer.parseInt(basic2)))+
				(int)(Math.ceil(Integer.parseInt(day3)*Integer.parseInt(basic3))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id390_2_1 Pass");
		}else{
			System.out.println("id390_2_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"basic1 = "+ basic1+"\r\n"+
					"basic2 = "+ basic2+"\r\n"+
					"basic3 = "+ basic3+"\r\n"+
					"day1 = "+ day1+"\r\n"+
					"day2 = "+ day2+"\r\n"+
					"day3 = "+ day3);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}

	/**
	 * Employee#paymentCycleType is "daily"
	 * If Employee#dailyPay1 has value, and both Employee#dailyPay2 and Employee#dailyPay3 are empty.
	 */
	@Test
	public void id390_2_2() throws Exception {
		String basic = String.valueOf((int)(Math.random()*10000000));
		String days = String.valueOf((int)(Math.random()*32));
		changeEmployeeInfo(driver,"daily",basic);
		//input the value to id130
		driver.findElement(By.xpath(paymentStatements.attendanceDays.xpath)).sendKeys(days);
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).click();
		Thread.sleep(2000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).getAttribute("value");
		String expected = String.valueOf((int)(Math.ceil((Integer.parseInt(basic)*(Integer.parseInt(days))))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id390_2_2 Pass");
		}else{
			System.out.println("id390_2_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"basic = "+ basic+"\r\n"+
					"days = "+ days);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * Employee#paymentCycleType is "hourly"
	 * more than 2 of which is Employee#dailyPay1 and Employee#dailyPay2 and Employee#dailyPay3 have value
	 */
	@Test
	public void id390_3_1() throws Exception {
		String basic1 = String.valueOf((int)(Math.random()*100000));
		String basic2 = String.valueOf((int)(Math.random()*100000));
		String basic3 = String.valueOf((int)(Math.random()*100000));
		String hour1 = String.valueOf((int)(Math.random()*100));
		String hour2 = String.valueOf((int)(Math.random()*100));
		String hour3 = String.valueOf((int)(Math.random()*100));
		String minute1 = String.valueOf((int)(Math.random()*60));
		String minute2 = String.valueOf((int)(Math.random()*60));
		String minute3 = String.valueOf((int)(Math.random()*60));
		changeEmployeeInfo(driver,"hourly",basic1+","+basic2+","+basic3);
		//input the value to id100 id110 id120
		driver.findElement(By.xpath(paymentStatements.attendanceHour_dayly_1.xpath)).sendKeys(hour1);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceMinute_dayly_1.xpath)).sendKeys(minute1);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceHour_dayly_2.xpath)).sendKeys(hour2);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceMinute_dayly_2.xpath)).sendKeys(minute2);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceHour_dayly_3.xpath)).sendKeys(hour3);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceMinute_dayly_3.xpath)).sendKeys(minute3);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceHour_dayly_3.xpath)).click();
		Thread.sleep(1000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).getAttribute("value");
		String expected = String.valueOf((int)(Math.ceil((Integer.parseInt(hour1)+Double.valueOf(minute1)/60)*Integer.parseInt(basic1)))+
				(int)(Math.ceil((Integer.parseInt(hour2)+Double.valueOf(minute2)/60)*Integer.parseInt(basic2)))+
				(int)(Math.ceil((Integer.parseInt(hour3)+Double.valueOf(minute3)/60)*Integer.parseInt(basic3))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id390_3_1 Pass");
		}else{
			System.out.println("id390_3_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"basic1 = "+ basic1+"\r\n"+
					"basic2 = "+ basic2+"\r\n"+
					"basic3 = "+ basic3+"\r\n"+
					"hour1 = "+ hour1+"\r\n"+
					"hour2 = "+ hour2+"\r\n"+
					"hour3 = "+ hour3+"\r\n"+
					"minute1 = "+ minute1+"\r\n"+
					"minute2 = "+ minute2+"\r\n"+
					"minute3 = "+ minute3);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);	
	}
	
	/**
	 * Employee#paymentCycleType is "hourly"
	 * If Employee#dailyPay1 has value, and both Employee#dailyPay2 and Employee#dailyPay3 are empty.
	 */
	@Test
	public void id390_3_2() throws Exception {
		String basic = String.valueOf((int)(Math.random()*100000));
		String hour = String.valueOf((int)(Math.random()*745));
		String minute = String.valueOf((int)(Math.random()*60));
		changeEmployeeInfo(driver,"hourly",basic);
		//input the value to id170
		driver.findElement(By.xpath(paymentStatements.attendanceHours.xpath)).sendKeys(hour);
		Thread.sleep(1000);
		//input the value to id172
		driver.findElement(By.xpath(paymentStatements.attendanceMins.xpath)).sendKeys(minute);
		Thread.sleep(1000);
		driver.findElement(By.xpath(paymentStatements.attendanceHours.xpath)).click();
		Thread.sleep(1000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.baseSalary.xpath)).getAttribute("value");
		String expected = String.valueOf((int)(Math.ceil((Integer.parseInt(hour)+Double.valueOf(minute)/60)*Integer.parseInt(basic))));
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id390_3_2 Pass");
		}else{
			System.out.println("id390_3_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(expected)+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(expected)+"\r\n"+
					"basic = "+ basic+"\r\n"+
					"hour = "+ hour+"\r\n"+
					"minute = "+ minute);
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
	
	public static void changeEmployeeInfo(WebDriver driver,String paymentType,String value) throws Exception
	{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//change the payment type
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
			if(value.contains(",")){
				String value1 = value.split(",")[0];
				String value2 = value.split(",")[1];
				String value3 = value.split(",")[2];
				//input the value 
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value1);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value2);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(value3);
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
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value);
				Thread.sleep(2000);
			}
		}else if(paymentType=="hourly"){
			driver.findElement(By.xpath(employeeSetting.paymentCycleHour.xpath)).click();
			Thread.sleep(2000);
			if(value.contains(",")){
				String value1 = value.split(",")[0];
				String value2 = value.split(",")[1];
				String value3 = value.split(",")[2];
				//input the value 
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).click();
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value1);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value2);
				Thread.sleep(1000);
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(value3);
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
				driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value);
				Thread.sleep(2000);
			}
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
		Thread.sleep(1000);
	}
}
