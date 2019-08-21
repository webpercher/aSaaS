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
 * Calculate the sum total of the input overtime working on [ SC-PRL-PSL-1-S1Pay Slip] 
 **/
public class ID370_ID380 extends TestBase{
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
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
	}

	@Test
	public void id370_id380() throws Exception {
		//calcu the id380
		//id380 = Mod((id220 +id240 +id260 +id280 +id300 +id320)/60) 
		//get the input value
		int id220 = (int) (Math.random()*60);
		int id240 = (int) (Math.random()*60);
		int id260 = (int) (Math.random()*60);
		int id280 = (int) (Math.random()*60);
		int id300 = (int) (Math.random()*60);
		int id320 = (int) (Math.random()*60);
		int actual_mintu = (id220+id240+id260+id280+id300+id320)%60;
		int actual_hour;
		if(id220+id240+id260+id280+id300+id320<60){
			actual_hour = 0;
		}else{
			actual_hour = (id220+id240+id260+id280+id300+id320)/60;
		}
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayMinute.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysMinute.xpath)).sendKeys(String.valueOf(id240));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayMinute.xpath)).sendKeys(String.valueOf(id260));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayMinute.xpath)).sendKeys(String.valueOf(id280));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id300));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayMinute.xpath)).sendKeys(String.valueOf(id320));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText().equals(String.valueOf(actual_mintu))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		//get the value
		int expected_minute = Integer.parseInt(driver.findElement(By.xpath(paymentStatements.offHoursMinute.xpath)).getText());
		int expected_hour = Integer.parseInt(driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText());
		//check the data
		org.junit.Assert.assertEquals(expected_minute,actual_mintu);
		org.junit.Assert.assertEquals(expected_hour,actual_hour);
		System.out.println("id380 Pass");
		
		
		//calcu the id370
		//id370 = (id210 +id230 +id250 +id270 +id290 +id310) + Mod((id220 +id240 +id260 +id280 +id300 +id320)/60)
		//get the input value
		int id210 = (int) (Math.random()*745);
		int id230 = (int) (Math.random()*745);
		int id250 = (int) (Math.random()*745);
		int id270 = (int) (Math.random()*745);
		int id290 = (int) (Math.random()*745);
		int id310 = (int) (Math.random()*745);
		int actual_hour_totle = (id210+id230+id250+id270+id290+id310)+actual_hour;
		//input the value
		driver.findElement(By.xpath(paymentStatements.normalWeekdayHour.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(paymentStatements.midnightOnWeekdaysHour.xpath)).sendKeys(String.valueOf(id230));
		driver.findElement(By.xpath(paymentStatements.ordinaryHolidayHour.xpath)).sendKeys(String.valueOf(id250));
		driver.findElement(By.xpath(paymentStatements.lateNightHolidayHour.xpath)).sendKeys(String.valueOf(id270));
		driver.findElement(By.xpath(paymentStatements.ordinaryStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id290));
		driver.findElement(By.xpath(paymentStatements.midnightStatutoryHolidayHour.xpath)).sendKeys(String.valueOf(id310));
		driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
		Thread.sleep(3000);
		while(!driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText().equals(String.valueOf(actual_hour_totle))){
			driver.findElement(By.xpath(paymentStatements.exceedSixMinute.xpath)).click();
			Thread.sleep(1000);
		}
		//get the value
		int expected_hour_totle = Integer.parseInt(driver.findElement(By.xpath(paymentStatements.offHoursHour.xpath)).getText());
		//check the data
		if(expected_hour_totle==actual_hour_totle){
			System.out.println("id370 Pass");
		}else{
			System.out.println("id370 Failed");
			throw new Exception("Error of calculation, expected: <["+expected_hour_totle+"]> but was: <["+actual_hour_totle+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected_hour_totle = "+ expected_hour_totle+"\r\n"+
					"actual_hour_totle = "+ actual_hour_totle+"\r\n"+
					"id220 = " +id220 +"\r\n"+
					"id220 = " +id220 +"\r\n"+
					"id240 = " +id240 +"\r\n"+
					"id260 = " +id260 +"\r\n"+
					"id280 = " +id280 +"\r\n"+
					"id300 = " +id300 +"\r\n"+
					"id320 = " +id320 +"\r\n"+
					"id210 = " +id210 +"\r\n"+
					"id230 = " +id230 +"\r\n"+
					"id250 = " +id250 +"\r\n"+
					"id270 = " +id270 +"\r\n"+
					"id290 = " +id290 +"\r\n"+
					"id310 = " +id310);
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
}
