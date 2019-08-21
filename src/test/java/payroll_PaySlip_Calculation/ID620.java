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
 * Display the calculated value of commuting allowance according to commuting payment cycle type. 
 **/
public class ID620 extends TestBase{
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
	 * Employee#commutingAllowancePaymentType is 'everyMonth'
	 **/
	@Test
	public void id620_1() throws Exception {
		String expected = String.valueOf((int) (Math.random()*1000000));
		changeEmployeeInfo(driver,"everyMonth",expected);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value");
		//check the data
		if(Common.formatNum(expected).equals(actual)){
			System.out.println("id620_1 Pass");
		}else{
			System.out.println("id620_1 Failed");
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
	 * If Employee#commutingAllowancePaymentType is 'none'
	 **/
	@Test
	public void id620_2() throws Exception {
		changeEmployeeInfo(driver,"none","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value");
		//check the data
		if(actual.trim().isEmpty()){
			System.out.println("id620_2 Pass");
		}else{
			System.out.println("id620_2 Failed");
			throw new Exception("Error of calculation, expected: <[empty]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+"empty"+"\r\n"+
					"actual = "+ actual);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#commutingAllowancePaymentType is 'everyDay' and employee#paymentCycleType is 'hourly' or 'monthly'
	 **/
	@Test
	public void id620_3() throws Exception {
		String expected = String.valueOf((int) (Math.random()*1000000));
		String days = String.valueOf((int) (Math.random()*32));
		changeEmployeeInfo(driver,"everyDay-monthHour",expected);
		//check empty
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value").isEmpty());
		//input the attend days
		driver.findElement(By.xpath(paymentStatements.attendanceDays.xpath)).sendKeys(days);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).click();
		Thread.sleep(2000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value");
		//check the data
		//#id620=#id130(attendance days)×Employee#commutingAllowanceAmout
		if(Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days))).equals(actual)){
			System.out.println("id620_3 Pass");
		}else{
			System.out.println("id620_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days)))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days)))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"days = "+ days);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#commutingAllowancePaymentType is 'everyDay' and employee#paymentCycleType is 'daily' 
	 * If more than 2 of which is [Employee]]#dailyPay1 and Employee#dailyPay2 and Employee#dailyPay3 have value
	 **/
	@Test
	public void id620_4_1() throws Exception {
		String commutingAllowanceAmout = String.valueOf((int) (Math.random()*1000000));
		String days1 = String.valueOf((int) (Math.random()*1000000));
		String days2 = String.valueOf((int) (Math.random()*1000000));
		String days3 = String.valueOf((int) (Math.random()*1000000));
		String day1 = String.valueOf((int) (Math.random()*32));
		String day2 = String.valueOf((int) (Math.random()*32));
		String day3 = String.valueOf((int) (Math.random()*32));
		changeEmployeeInfo(driver,"everyDay-daily",commutingAllowanceAmout+"_"+days1+"-"+days2+"-"+days3);
		//check empty
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value").isEmpty());
		//input the attend days
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_1.xpath)).sendKeys(day1);
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_2.xpath)).sendKeys(day2);
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.attendanceDays_monthly_3.xpath)).sendKeys(day3);
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).click();
		Thread.sleep(2000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value");
		//check the data
		//#id620=(#id100(attendance days1)+#id110(attendance days2)+#id120(attendance days3)×Employee#commutingAllowanceAmout
		if(Common.formatNum(String.valueOf(((Integer.parseInt(day1)+Integer.parseInt(day2)+Integer.parseInt(day3))*Integer.parseInt(commutingAllowanceAmout)))).equals(actual)){
			System.out.println("id620_4_1 Pass");
		}else{
			System.out.println("id620_4_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(((Integer.parseInt(day1)+Integer.parseInt(day2)+Integer.parseInt(day3))*Integer.parseInt(commutingAllowanceAmout))))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(((Integer.parseInt(day1)+Integer.parseInt(day2)+Integer.parseInt(day3))*Integer.parseInt(commutingAllowanceAmout))))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"commutingAllowanceAmout = "+ commutingAllowanceAmout+"\r\n"+
					"days1 = "+ days1+"\r\n"+
					"days2 = "+ days2+"\r\n"+
					"days3 = "+ days3+"\r\n"+
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
	 * If Employee#commutingAllowancePaymentType is 'everyDay' and employee#paymentCycleType is 'daily' 
	 * If else
	 **/
	@Test
	public void id620_4_2() throws Exception {
		String expected = String.valueOf((int) (Math.random()*1000000));
		String days1 = String.valueOf((int) (Math.random()*1000000));
		String days = String.valueOf((int) (Math.random()*32));
		changeEmployeeInfo(driver,"everyDay-daily",expected+"_"+days1);
		//check empty
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value").isEmpty());
		//input the attend days
		driver.findElement(By.xpath(paymentStatements.attendanceDays.xpath)).sendKeys(days);
		driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).click();
		Thread.sleep(2000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.travelAllowance.xpath)).getAttribute("value");
		//check the data
		//#id620=#id130(attendance days)×Employee#commutingAllowanceAmout
		if(Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days))).equals(actual)){
			System.out.println("id620_4_2 Pass");
		}else{
			System.out.println("id620_4_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days)))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(Integer.parseInt(expected)*Integer.parseInt(days)))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"days1 = "+ days1+"\r\n"+
					"days = "+ days);
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
		if(paymentType=="everyMonth"){
			driver.findElement(By.xpath(employeeSetting.paymentUnit_Month.xpath)).click();
			Thread.sleep(2000);
			//input the value
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).click();
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(Keys.DELETE);
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(value);
			Thread.sleep(2000);
		}else if(paymentType=="none"){
			driver.findElement(By.xpath(employeeSetting.paymentUnit_None.xpath)).click();
			Thread.sleep(2000);
		}else if(paymentType.contains("everyDay")){
			driver.findElement(By.xpath(employeeSetting.paymentUnit_Day.xpath)).click();
			Thread.sleep(2000);
			//input the value
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).click();
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(Keys.DELETE);
			driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(value.split("_")[0]);
			Thread.sleep(2000);
			if(paymentType.contains("daily")){
				//choose daily
				driver.findElement(By.xpath(employeeSetting.paymentCycleDay.xpath)).click();
				Thread.sleep(2000);
				if(value.contains("_")&&value.contains("-")){
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
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("_")[1].split("-")[0]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay2.xpath)).sendKeys(value.split("_")[1].split("-")[1]);
					Thread.sleep(1000);
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay3.xpath)).sendKeys(value.split("_")[1].split("-")[2]);
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
					driver.findElement(By.xpath(employeeSetting.paymentCycleUM_pay1.xpath)).sendKeys(value.split("_")[1]);
					Thread.sleep(1000);
				}
			}else{
				//choose month or hour
				int num = (int)(Math.random()*10);
				if(num<5){
					driver.findElement(By.xpath(employeeSetting.paymentCycleM.xpath)).click();
					Thread.sleep(2000);					
				}else{
					driver.findElement(By.xpath(employeeSetting.paymentCycleHour.xpath)).click();
					Thread.sleep(2000);
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
}
