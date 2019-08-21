package payroll_AnnualAdj_Calculation;


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
import com.asaas.test.util.AnnualAdjustment;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;


/**
 * Calculate total amount of #id820(Small corporation declared Mutual Aid Amount of Social Insurance Rate Deduction from Salary)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000 , show Calculation when #id730(Total Payment Amount) is altered
 * #id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】 
 **/
public class ID820 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static AnnualAdjustment annualAdjustment;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static int id820_10;
	public static int id820_20;
	public static int id820_30;
	public static int id820_40;
	public static int id820_50;
	public static int id820_60;
	public static int id820_70;
	public static int id820_80;
	public static int id820_90;
	public static int id820_100;
	public static int id820_110;
	public static int id820_120;
	public static int id820_130;
	public static int id820_140;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		annualAdjustment = (AnnualAdjustment) helper.getPage("AnnualAdjustment");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		id820_10 = 0;
		id820_20 = 0;
		id820_30 = 0;
		id820_40 = 0;
		id820_50 = 0;
		id820_60 = 0;
		id820_70 = 0;
		id820_80 = 0;
		id820_90 = 0;
		id820_100 = 0;
		id820_110 = 0;
		id820_120 = 0;
		id820_130 = 0;
		id820_140 = 0;
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
	}

	/**
	 * 【10】=380000
	 **/
	@Test
	public void id820_10() throws Exception {
		//input the id730 
		int totalAmount = (int) (Math.random()*20000000);
		int id90 = (int) (Math.random()*totalAmount);
		int id210 = totalAmount-id90;
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment.xpath)).sendKeys(String.valueOf(id90));
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_1.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).click();
		Thread.sleep(2000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",","").equals(String.valueOf(totalAmount))){
				driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).click();
				Thread.sleep(2000);
			}
		}
		//save the data 
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_10 = 380000;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id820==actual){
			System.out.println("id820_10 Pass");
		}else{
			System.out.println("id820_10 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * If #id470(Deduction Spouse (general)) is "" and #id480(Deduction Spouse (senior)) is "" 
	 * 【20】=0
	 **/
	@Test
	public void id820_20_1() throws Exception {
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】 
		//open DeclarationButton
		if(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath.replace("/td[3]/div/span",""))).getCssValue("display").equals("none")){
			driver.findElement(By.xpath(annualAdjustment.DeclarationButton.xpath)).click();
			Thread.sleep(2000);
		}
		org.junit.Assert.assertEquals(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath)).getText(),annualAdjustment.generalDeductionSpouse.getDatas().get("TS11669"));
		org.junit.Assert.assertEquals(driver.findElement(By.xpath(annualAdjustment.seniorDeductionSpouse.xpath)).getText(),annualAdjustment.generalDeductionSpouse.getDatas().get("TS11669"));
		id820_20 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id820==actual){
			System.out.println("id820_20_1 Pass");
		}else{
			System.out.println("id820_20_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * If AnnualAdjustmentInformation#deducibleSpouseNumber ＞ 0 or AnnualAdjustmentInformation#deducibleSeniorSpouseNumber ＞ 0 
	 * 【20】=380000
	 **/
	@Test
	public void id820_20_2() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//set AnnualAdjustmentInformation#deducibleSpouseNumber ＞ 0
		driver.findElement(By.xpath(annualAdjustment.spouse.xpath)).click();
		Thread.sleep(2000);
		Select sel = new Select(driver.findElement(By.xpath(annualAdjustment.dependenttype.xpath)));
		sel.selectByVisibleText(annualAdjustment.dependenttype.getDatas().get("TS1623"));
		Thread.sleep(2000);
		//save 
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_20 = 380000;
		id820_30 = 100000;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id820==actual){
			System.out.println("id820_20_2 Pass");
		}else{
			System.out.println("id820_20_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * If AnnualAdjustmentInformation#deducibleSeniorSpouseNumber = 0 
	 * 【30】=0
	 **/
	@Test
	public void id820_30_1() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//set AnnualAdjustmentInformation#deducibleSpouseNumber ＞ 0
		driver.findElement(By.xpath(annualAdjustment.spouse2.xpath)).click();
		Thread.sleep(2000);
		//save 
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_20 = 0;
		id820_30 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id820==actual){
			System.out.println("id820_30_1 Pass");
		}else{
			System.out.println("id820_30_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * If AnnualAdjustmentInformation#deducibleSeniorSpouseNumber ＞ 0 
	 * 【30】=100000
	 **/
	@Test
	public void id820_30_2() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//set AnnualAdjustmentInformation#deducibleSpouseNumber ＞ 0
		driver.findElement(By.xpath(annualAdjustment.spouse.xpath)).click();
		Thread.sleep(2000);
		Select sel = new Select(driver.findElement(By.xpath(annualAdjustment.dependenttype.xpath)));
		sel.selectByVisibleText(annualAdjustment.dependenttype.getDatas().get("TS1623"));
		Thread.sleep(2000);
		//save 
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_20 = 380000;
		id820_30 = 100000;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id820==actual){
			System.out.println("id820_30_2 Pass");
		}else{
			System.out.println("id820_30_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【40】=380000 × {AnnualAdjustmentInformation#deducibleDependentsNumber + AnnualAdjustmentInformation#specialDependentsNumber + AnnualAdjustmentInformation#seniorInHouseNumber + AnnualAdjustmentInformation#seniorDependentsNumber
	 **/
	@Test
	public void id820_40_1() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//add
		if(!Common.isElementPresent(driver, By.xpath(annualAdjustment.dependents7.xpath.replace("tr[13]","tr[15]")))){
			driver.findElement(By.xpath(annualAdjustment.add.xpath)).click();
			Thread.sleep(2000);			
		}
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents.xpath)));
		sel1.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1630"));
		Thread.sleep(1000);
		Select sel2 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents2.xpath)));
		sel2.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1630"));
		Thread.sleep(1000);
		Select sel3 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents3.xpath)));
		sel3.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1631"));
		Thread.sleep(1000);
		Select sel4 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents4.xpath)));
		sel4.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1631"));
		Thread.sleep(1000);
		Select sel5 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents5.xpath)));
		sel5.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS11914"));
		Thread.sleep(1000);
		Select sel6 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents6.xpath)));
		sel6.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS11914"));
		Thread.sleep(1000);
		Select sel7 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents7.xpath)));
		sel7.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1633"));
		Thread.sleep(1000);
		Select sel8 = new Select(driver.findElement(By.xpath(annualAdjustment.dependents7.xpath.replace("tr[13]","tr[15]"))));
		sel8.selectByVisibleText(annualAdjustment.dependents.getDatas().get("TS1633"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		if(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath.replace("/td[3]/div/span",""))).getCssValue("display").equals("none")){
			driver.findElement(By.xpath(annualAdjustment.DeclarationButton.xpath)).click();
			Thread.sleep(2000);
		}
		//get the value
		int id490 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.NormalDependent.xpath)).getText());
		int id500 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.SpecialDependent.xpath)).getText());
		int id510 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.inHouseSeniorDependent.xpath)).getText());
		int id520 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.othersSeniorDependent.xpath)).getText());
		//【40】=380000 × {#id490+#id500+#id510+#id520}
		id820_40 = 380000*(id490+id500+id510+id520);
		id820_50 = 250000*id500;
		id820_60 = 100000*id520;
		id820_70 = 200000*id510;
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_40_1 Pass");
		}else{
			System.out.println("id820_40_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【50】=250000 × AnnualAdjustmentInformation#specialDependentsNumber
	 **/
	@Test
	public void id820_50_1() throws Exception {
		//【50】=250000 × #id500
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_50_1 Pass");
		}else{
			System.out.println("id820_50_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【60】=100000 × AnnualAdjustmentInformation#seniorDependentsNumber
	 **/
	@Test
	public void id820_60_1() throws Exception {
		//【60】=100000 × #id520
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_60_1 Pass");
		}else{
			System.out.println("id820_60_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【70】=200000 × AnnualAdjustmentInformation#seniorInHouseNumber
	 **/
	@Test
	public void id820_70_1() throws Exception {
		//【70】=200000 × #id510
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_70_1 Pass");
		}else{
			System.out.println("id820_70_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【80】 =【A】+【B】+【C】
	 * if AnnualAdjustmentInformation#disabilityPersonType is "general" 
	 * 【A】= 270000
 	 * if NoWorkingDependent#spouseFlag is true having NoWorkingDependent#disabilityPersonType is "general" 
 	 * 【B】= 270000
 	 * if NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "general" 
 	 * 【C】= 270000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "general")}
	 **/
	@Test
	public void id820_80_1() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.disabilityPersonType.getDatas().get("TS44_2"));
		Thread.sleep(1000);
		Select sel2 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPerson1.xpath)));
		sel2.selectByVisibleText(annualAdjustment.disabilityPerson1.getDatas().get("TS120_2"));
		Thread.sleep(1000);
		Select sel3 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep.xpath)));
		sel3.selectByVisibleText(annualAdjustment.disabilityPersonDep3.getDatas().get("TS2671"));
		Thread.sleep(1000);
		Select sel4 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep2.xpath)));
		sel4.selectByVisibleText(annualAdjustment.disabilityPersonDep3.getDatas().get("TS2671"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		if(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath.replace("/td[3]/div/span",""))).getCssValue("display").equals("none")){
			driver.findElement(By.xpath(annualAdjustment.DeclarationButton.xpath)).click();
			Thread.sleep(2000);
		}
		int GeneralDisabilityPersonNumber = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.GeneralDisabilityPersonNumber.xpath)).getText());
		//【A】= 270000
		//【B】= 270000
		//【C】= 270000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "general")}
		int A = 270000;
		int B = 270000;
		int C = 270000*GeneralDisabilityPersonNumber;
		id820_80 = A+B+C;
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_80_1 Pass");
		}else{
			System.out.println("id820_80_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【90】 =【D】+【E】+【F】
	 * if AnnualAdjustmentInformation#disabilityPersonType is "especial" 
	 * 【D】= 400000
	 * if NoWorkingDependent#spouseFlag is true having NoWorkingDependent#disabilityPersonType is "especial" 
	 * 【E】= 400000
	 * if NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial" 
	 * 【F】= 400000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial")}
	 **/
	@Test
	public void id820_90_1() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.disabilityPersonType.getDatas().get("TS44"));
		Thread.sleep(1000);
		Select sel2 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPerson1.xpath)));
		sel2.selectByVisibleText(annualAdjustment.disabilityPerson1.getDatas().get("TS120_3"));
		Thread.sleep(1000);
		Select sel3 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep.xpath)));
		sel3.selectByVisibleText(annualAdjustment.disabilityPersonDep4.getDatas().get("TS2680"));
		Thread.sleep(1000);
		Select sel4 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep2.xpath)));
		sel4.selectByVisibleText(annualAdjustment.disabilityPersonDep4.getDatas().get("TS2680"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		if(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath.replace("/td[3]/div/span",""))).getCssValue("display").equals("none")){
			driver.findElement(By.xpath(annualAdjustment.DeclarationButton.xpath)).click();
			Thread.sleep(2000);
		}
		int SpecialDisabilityPersonNumber = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.SpecialDisabilityPersonNumber.xpath)).getText());
		//【D】= 400000
		//【E】= 400000
		//【F】= 400000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial")}
		int D = 400000;
		int E = 400000;
		int F = 400000*SpecialDisabilityPersonNumber;
		id820_80 = 0;
		id820_90 = D+E+F;
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_90_1 Pass");
		}else{
			System.out.println("id820_90_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 * 【100】=【G】+【H】
	 * if NoWorkingDependent#spouseFlag is true having NoWorkingDependent#disabilityPersonType is "especial in house" 
	 * 【G】= 750000
 	 * if NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial in house" 
	 * 【H】= 750000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial in house")}
	 **/
	@Test
	public void id820_100_1() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.disabilityPersonType.getDatas().get("def"));
		Thread.sleep(1000);
		Select sel2 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPerson1.xpath)));
		sel2.selectByVisibleText(annualAdjustment.disabilityPerson1.getDatas().get("TS1603"));
		Thread.sleep(1000);
		Select sel3 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep.xpath)));
		sel3.selectByVisibleText(annualAdjustment.disabilityPersonDep.getDatas().get("TS1627"));
		Thread.sleep(1000);
		Select sel4 = new Select(driver.findElement(By.xpath(annualAdjustment.disabilityPersonDep2.xpath)));
		sel4.selectByVisibleText(annualAdjustment.disabilityPersonDep.getDatas().get("TS1627"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		if(driver.findElement(By.xpath(annualAdjustment.generalDeductionSpouse.xpath.replace("/td[3]/div/span",""))).getCssValue("display").equals("none")){
			driver.findElement(By.xpath(annualAdjustment.DeclarationButton.xpath)).click();
			Thread.sleep(2000);
		}
		int SpecialDisabilityPersonInHouseNumber = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.SpecialDisabilityPersonInHouseNumber.xpath)).getText());
		//【G】= 750000
		//【H】= 750000 × {number of (NoWorkingDependent#spouseFlag is false having NoWorkingDependent#disabilityPersonType is "especial in house")}
		int G = 750000;
		int H = 750000*SpecialDisabilityPersonInHouseNumber;
		id820_80 = 0;
		id820_90 = 0;
		id820_100 = G+H;
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		//check the data
		if(id820==actual){
			System.out.println("id820_100_1 Pass");
		}else{
			System.out.println("id820_100_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType isn't "1" 
	 *【110】=0
	 **/
	@Test
	public void id820_110_1() throws Exception {
		changeSex(driver,"fmale");
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_110 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_110_1 Pass");
		}else{
			System.out.println("id820_110_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType is "1" 
	 *【110】=270000
	 **/
	@Test
	public void id820_110_2() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.widowType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.widowType.getDatas().get("id820_2"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//【110】=270000
		id820_110 = 270000;
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_110_2 Pass");
		}else{
			System.out.println("id820_110_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType isn't "2" 
	 *【120】=0
	 **/
	@Test
	public void id820_120_1() throws Exception {
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_120 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_120_1 Pass");
		}else{
			System.out.println("id820_120_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType is "2" 
	 *【120】=350000
	 **/
	@Test
	public void id820_120_2() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.widowType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.widowType.getDatas().get("id820_1"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//【120】=350000
		id820_110 = 0;
		id820_120 = 350000;
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_120_2 Pass");
		}else{
			System.out.println("id820_120_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType isn't "3" 
	 *【130】=0
	 **/
	@Test
	public void id820_130_1() throws Exception {
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_130 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_130_1 Pass");
		}else{
			System.out.println("id820_130_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_130 = "+ id820_130+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#widowType is "3" 
	 *【130】=270000
	 **/
	@Test
	public void id820_130_2() throws Exception {
		changeSex(driver,"male");
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//select 
		Select sel1 = new Select(driver.findElement(By.xpath(annualAdjustment.widowType.xpath)));
		sel1.selectByVisibleText(annualAdjustment.widowType.getDatas().get("TS49"));
		Thread.sleep(1000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//【130】=270000
		id820_120 = 0;
		id820_130 = 270000;
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_130_2 Pass");
		}else{
			System.out.println("id820_130_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_130 = "+ id820_130+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#workingStudent is "false" 
	 *【140】=0
	 **/
	@Test
	public void id820_140_1() throws Exception {
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		id820_140 = 0;
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_140_1 Pass");
		}else{
			System.out.println("id820_140_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_130 = "+ id820_130+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	/**
	 *If AnnualAdjustmentInformation#workingStudent is "true" 
	 *【140】=270000
	 **/
	@Test
	public void id820_140_2() throws Exception {
		//forward to annualEmployeeDep page
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		//check
		driver.findElement(By.xpath(annualAdjustment.workingStudent.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.okForAnnual.xpath)));
		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		Thread.sleep(2000);
		while(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
			Thread.sleep(500);
		}
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//check the data
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//【140】=270000
		id820_140 = 270000;
		//#id820=【10】+【20】+【30】+【40】+【50】+【60】+【70】+【80】+【90】+【100】+【110】+【120】+【130】+【140】
		int id820 = id820_10+id820_20+id820_30+id820_40+id820_50+id820_60+id820_70+id820_80+id820_90+id820_100+id820_110+id820_120+id820_130+id820_140;
		if(id820==actual){
			System.out.println("id820_140_2 Pass");
		}else{
			System.out.println("id820_140_2 Failed");
			throw new Exception("Error of calculation, expected: <["+id820+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id820 = "+ id820+"\r\n"+
					"id820_20 = "+ id820_20+"\r\n"+
					"id820_30 = "+ id820_30+"\r\n"+
					"id820_40 = "+ id820_40+"\r\n"+
					"id820_50 = "+ id820_50+"\r\n"+
					"id820_60 = "+ id820_60+"\r\n"+
					"id820_70 = "+ id820_70+"\r\n"+
					"id820_80 = "+ id820_80+"\r\n"+
					"id820_90 = "+ id820_90+"\r\n"+
					"id820_100 = "+ id820_100+"\r\n"+
					"id820_110 = "+ id820_110+"\r\n"+
					"id820_120 = "+ id820_120+"\r\n"+
					"id820_130 = "+ id820_130+"\r\n"+
					"id820_140 = "+ id820_140+"\r\n"+
					"id820_10 = "+ id820_10);
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	public static void changeSex(WebDriver driver,String type) throws Exception {
		//close
		driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();
		//employee page
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Thread.sleep(2000);
		Common.isNotPending();
		Thread.sleep(2000);
		//edit
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//chaneg sex
		if(type=="male"){
			driver.findElement(By.xpath(employeeSetting.gender_male.xpath)).click();
			Thread.sleep(2000);
		}else if(type=="fmale"){
			driver.findElement(By.xpath(employeeSetting.gender_fmale.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		//click the 'Close' button
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));		
	}
}