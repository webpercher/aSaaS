package payroll_PaySlip_Calculation;


import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
 * Display the calculated amount of health insurance. 
 **/
public class ID660 extends TestBase{
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
	 * If Employee#fixedAmountHealthInsuranceFlag is false
	 * If ClientSocialInsurance#healthInsuranceType='kyokai'
	 **/
	@Test
	public void id660_1_1() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"kyokai");
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"false",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.healthInsurance.xpath)).getAttribute("value");
		//P =Employee#healthInsuranceMonthlyAmount×{ the latest ClientHealthInsurance#insuredPersonPaymentProportion that ClientHealthInsurance#insuranceType='kyokaiHealth' and ClientHealthInsurance#useStartMonth <= year and month of the selected PayrollMonth#paymentDate}
		//#id660= P rounded to the nearest whole number according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod(*1)
		//round half down
		int expected = Common.roundHalfDown(healthInsuranceMonthlyAmount,insuredPersonPaymentProportion);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id660_1_1 Pass");
		}else{
			System.out.println("id660_1_1 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"insuredPersonPaymentProportion ="+ insuredPersonPaymentProportion+"\r\n"+
					"value ="+ value+"\r\n"+
					"healthInsuranceMonthlyAmount ="+ healthInsuranceMonthlyAmount);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountHealthInsuranceFlag is false
	 * If ClientSocialInsurance#healthInsuranceType='union'
	 **/
	@Test
	public void id660_1_2() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"union");
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"false",String.valueOf(value)).replace(",","");
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.healthInsurance.xpath)).getAttribute("value");
		//P =Employee#healthInsuranceMonthlyAmount×{ the latest ClientHealthInsurance#insuredPersonPaymentProportion that ClientHealthInsurance#insuranceType='unionHealth' and ClientHealthInsurance#useStartMonth <= year and month of the selected PayrollMonth#paymentDate}
		//round half down
		int expected = Common.roundHalfDown(healthInsuranceMonthlyAmount,insuredPersonPaymentProportion);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id660_1_2 Pass");
		}else{
			System.out.println("id660_1_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"insuredPersonPaymentProportion ="+ insuredPersonPaymentProportion+"\r\n"+
					"value ="+ value+"\r\n"+
					"healthInsuranceMonthlyAmount ="+ healthInsuranceMonthlyAmount);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountHealthInsuranceFlag is false
	 * If ClientSocialInsurance#healthInsuranceType='other'
	 **/
	@Test
	public void id660_1_3() throws Exception {
		String insuredPersonPaymentProportion = changeCompanyInfo(driver,"other");
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		Thread.sleep(2000);
		//wait for page displayed
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatements.edit.xpath)).click();
		Thread.sleep(2000);
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.healthInsurance.xpath)).getAttribute("value");
		//#id660={ the latest ClientHealthInsurance#insuranceAmount that ClientHealthInsurance#insuranceType='otherHealth' and ClientHealthInsurance#useStartMonth <= year and month of the selected PayrollMonth#paymentDate}
		//round half down
		int expected = Integer.parseInt(insuredPersonPaymentProportion.replace(",",""));
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id660_1_3 Pass");
		}else{
			System.out.println("id660_1_3 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"insuredPersonPaymentProportion ="+ insuredPersonPaymentProportion);
		}
		//close
		driver.findElement(By.xpath(paymentStatements.close.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatements.cancelPayment.xpath)).click();
		Thread.sleep(2000);
	}
	
	/**
	 * If Employee#fixedAmountHealthInsuranceFlag is true
	 **/
	@Test
	public void id660_2() throws Exception {
		int value = (int)(Math.random()*1000000);
		String healthInsuranceMonthlyAmount = changeEmployeeInfo(driver,"true",String.valueOf(value));
		//get value
		String actual = driver.findElement(By.xpath(paymentStatements.healthInsurance.xpath)).getAttribute("value");
		//#id660 = Employee#healthInsuranceFixedAmount
		int expected = Integer.parseInt(healthInsuranceMonthlyAmount);
		//check the data
		if(Common.formatNum(String.valueOf(expected)).equals(actual)){
			System.out.println("id660_2 Pass");
		}else{
			System.out.println("id660_2 Failed");
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(expected))+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(expected))+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"healthInsuranceMonthlyAmount = "+ healthInsuranceMonthlyAmount+"\r\n"+
					"value ="+ value);
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
		new WebDriverWait( driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(employeeSetting.employeeCode.xpath)));
		Thread.sleep(3000);
		//click edit button
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(employeeSetting.koMonthlyTable.xpath)).click();
		Thread.sleep(2000);
		//check the button
		if(!driver.findElement(By.xpath(employeeSetting.insured.xpath)).isSelected()){
			driver.findElement(By.xpath(employeeSetting.insured.xpath)).click();
			Thread.sleep(1000);
		}
		//change the payment type
		if(type=="false"){
			if(driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).click();
				Thread.sleep(2000);
			}
		}else{
			if(!driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).isSelected()){
				driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).click();
				Thread.sleep(2000);
			}
			//input the value
			Common.clear(driver,employeeSetting.fixedAmountHealthInsurance_input.xpath);
			driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance_input.xpath)).sendKeys(value);
			Thread.sleep(1000);
		}
		//input the value
		driver.findElement(By.xpath(employeeSetting.generalRewardMonthlyAmount.xpath)).click();
		Thread.sleep(2000);
		int num = (int)(Math.random()*47+1);
		String newXpath = employeeSetting.stdMonthlyRemuneration_select.xpath.replace("x",String.valueOf(num));
		driver.findElement(By.xpath(newXpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).click();
		Thread.sleep(2000);
		//get value 
		String result = driver.findElement(By.xpath(employeeSetting.stdMonthlyRemuneration.xpath)).getAttribute("value");
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
	
	public static String changeCompanyInfo(WebDriver driver,String type) throws Exception{
		Common.forwardToTopPage();
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		while(!driver.findElement(By.xpath(clientSetting.closingDay.xpath)).isDisplayed()){
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
			Thread.sleep(1000);	
		}
		if(type=="kyokai"){
			driver.findElement(By.xpath(clientSetting.healthInsuranceType1.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="union"){
			driver.findElement(By.xpath(clientSetting.healthInsuranceType.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="other"){
			driver.findElement(By.xpath(clientSetting.healthInsuranceType2.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(4000);
		//click the 'ok' button
		if(Common.isElementPresent(driver, By.xpath(clientSetting.definition.xpath))){
			driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
			Thread.sleep(1000);	
		}
		//for to calculate page
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		while(!driver.findElement(By.xpath(clientSetting.healthInsurance_paymentYear.xpath)).isDisplayed()){
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
			Thread.sleep(1000);	
		}
		if(type=="kyokai"){
			Select sel = new Select(driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
			sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get("initial"));
			Thread.sleep(5000);
			String xpath = clientSetting.Ok.xpath.replace("OK",clientSetting.definition.getDatas().get("title"));
			if(Common.isElementPresent(driver, By.xpath(xpath))){
				driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
				Thread.sleep(1000);	
			}
			String all[] = driver.findElement(By.xpath(clientSetting.perfectureType.xpath)).getText().split("\\\n");
			int num = (int)(Math.random()*47+1);
			Select sel2 = new Select(driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
			sel2.selectByVisibleText(all[num]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
			Thread.sleep(2000);	
		}else if(type=="union"){
			//input the month
			Common.clear(driver,clientSetting.healthInsOldAmountMonth.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsOldAmountMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.healthInsOldAmountDay.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsOldAmountDay.xpath)).sendKeys("1");
			Thread.sleep(1000);
			String num = String.valueOf(Math.random()*99+1);
			Common.clear(driver,clientSetting.insuredPersonPaymentProportion_input.xpath);
			driver.findElement(By.xpath(clientSetting.insuredPersonPaymentProportion_input.xpath)).sendKeys(num.substring(0,num.indexOf(".")+3));
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.healthInsurance_paymentMonth.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsurance_paymentMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsranceOldAmountMonth.xpath);
			driver.findElement(By.xpath(clientSetting.careInsranceOldAmountMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsranceOldAmountDay.xpath);
			driver.findElement(By.xpath(clientSetting.careInsranceOldAmountDay.xpath)).sendKeys("1");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsurance_paymentMonth.xpath);
			driver.findElement(By.xpath(clientSetting.careInsurance_paymentMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
		}else if(type=="other"){
			//input the month
			Common.clear(driver,clientSetting.healthInsOldAmountMonth.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsOldAmountMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.healthInsOldAmountDay.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsOldAmountDay.xpath)).sendKeys("1");
			Thread.sleep(1000);
			String num = String.valueOf(Math.random()*10000000);
			Common.clear(driver,clientSetting.insuredPersonPaymentProportion_other.xpath);
			driver.findElement(By.xpath(clientSetting.insuredPersonPaymentProportion_other.xpath)).sendKeys(num);
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.healthInsurance_paymentMonth.xpath);
			driver.findElement(By.xpath(clientSetting.healthInsurance_paymentMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsranceOldAmountMonth.xpath);
			driver.findElement(By.xpath(clientSetting.careInsranceOldAmountMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsranceOldAmountDay.xpath);
			driver.findElement(By.xpath(clientSetting.careInsranceOldAmountDay.xpath)).sendKeys("1");
			Thread.sleep(1000);
			Common.clear(driver,clientSetting.careInsurance_paymentMonth.xpath);
			driver.findElement(By.xpath(clientSetting.careInsurance_paymentMonth.xpath)).sendKeys("4");
			Thread.sleep(1000);
		}
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		String result = driver.findElement(By.xpath(clientSetting.insuredPersonPaymentProportion.xpath)).getText();
		if(type=="other"){
			result = driver.findElement(By.xpath(clientSetting.insuredPersonPaymentProportion_other_dis.xpath)).getText();
		}
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
		return result;
	}
}