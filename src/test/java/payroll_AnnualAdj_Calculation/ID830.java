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
 * Calculate total amount of #id830(Income Deduction Total Amount) 
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and if #id760(Social Insurance Deduction Amount (Payroll)) or #id770(Social Insurance Deduction Amount (Social Insurance Declaration)) or #id780(Social Insurance Deduction Amount (Small corporation declared Mutual Aid Amount)) or #id790(Deduction Amount of Life Insurance Rate) or #id800(Deduction Amount of Earthquake Insurance Rate) or #id810(Spouse Special Deduction Amount) or #id820(Personal Deduction Total Amount ) is altered when WithholdingIncomeTaxLedger#totalAmount =< 20,000,000,
 **/
public class ID830 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static AnnualAdjustment annualAdjustment;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	
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
	 * #id830=(#id760 + #id770 + #id780 + #id790 + #id800 + #id810 + #id820)
	 **/
	@Test
	public void id830_1() throws Exception {
		//id760
		//id760 = id160+id280
		int id100 = (int) (Math.random()*1000000);
		int id220 = (int) (Math.random()*1000000);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate.xpath)).sendKeys(String.valueOf(id100));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
		Thread.sleep(2000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.socialInsuranceDeductionAmountPayroll.xpath)).getText().replace(",","").equals(String.valueOf(id100+id220))){
				driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
				Thread.sleep(2000);
			}
		}
		int id160 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.socialInsuranceTotalAmount.xpath)).getText().replace(",","")); 
		int id280 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.bonusSocialInsuranceTotalAmount.xpath)).getText().replace(",",""));
		int id760 = id160+id280; 
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to deductionname
		driver.findElement(By.xpath(annualAdjustment.deductionname.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.generalLifeNew1.xpath)));		
		
		//id790
		//id790 = compareAmount
		int generalLifePaidRate1 = (int) (Math.random()*40000);
		driver.findElement(By.xpath(annualAdjustment.generalLifePaidRate1.xpath)).sendKeys(String.valueOf(generalLifePaidRate1));
		if(!driver.findElement(By.xpath(annualAdjustment.generalLifeNew1.xpath)).isSelected()){
			driver.findElement(By.xpath(annualAdjustment.generalLifeNew1.xpath)).click();			
		}
		Thread.sleep(2000);
		int compareAmount = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.compareAmount.xpath)).getText().replace(",",""));
		int id790 = compareAmount;
		
		//id800
		//id800 = earthquakePremiumsPaid1		
		int earthquakePremiumsPaid1 = (int) (Math.random()*40000);
		driver.findElement(By.xpath(annualAdjustment.earthquakePremiumsPaid1.xpath)).sendKeys(String.valueOf(earthquakePremiumsPaid1));
		if(!driver.findElement(By.xpath(annualAdjustment.earthquakeLongTerm1.xpath)).isSelected()){
			driver.findElement(By.xpath(annualAdjustment.earthquakeLongTerm1.xpath)).click();			
		}
		Thread.sleep(2000);
		int id800 = earthquakePremiumsPaid1;
		
		//id770
		//id770 = SocialPremiumsPaid1		
		int SocialPremiumsPaid1 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.SocialPremiumsPaid1.xpath)).sendKeys(String.valueOf(SocialPremiumsPaid1));
		Thread.sleep(2000);
		int id770 = SocialPremiumsPaid1;
		
		//id780
		//id780 = SMEsRIAmount		
		int SMEsRIAmount = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.SMEsRIAmount.xpath)).sendKeys(String.valueOf(SMEsRIAmount));
		Thread.sleep(2000);
		int id780 = SMEsRIAmount;
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to annualEmployeeDep
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		
		//id810
		//id810 =
		driver.findElement(By.xpath(annualAdjustment.spouse.xpath)).click();
		Thread.sleep(2000);
		Select sel = new Select(driver.findElement(By.xpath(annualAdjustment.dependenttype.xpath)));
		sel.selectByVisibleText(annualAdjustment.dependenttype.getDatas().get("TS1624"));
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.birthdayyear.xpath)).click();
		Thread.sleep(2000);
		//input the data
		int businessEarningAmount = 759999;
		driver.findElement(By.xpath(annualAdjustment.businessEarningAmount.xpath)).sendKeys(String.valueOf(businessEarningAmount));
		driver.findElement(By.xpath(annualAdjustment.miscellaneousEarningAmount.xpath)).click();
		Thread.sleep(2000);
		//get value 
		int spouseSpecialDeductionAmount = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.spouseSpecialDeductionAmount.xpath)).getText().replace(",",""));
		int id810 = spouseSpecialDeductionAmount;
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.incomeDeductionTotalAmount.xpath)).getText().replace(",",""));
		int id820 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id830=(#id760 + #id770 + #id780 + #id790 + #id800 + #id810 + #id820)
		int id830 = id760+id770+id780+id790+id800+id810+id820;
		//check the data
		if(id830==actual){
			System.out.println("id830_1 Pass");
		}else{
			System.out.println("id830_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id830+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id830 = "+ id830+"\r\n"+
					"id760 = "+ id760+"\r\n"+
					"id770 = "+ id770+"\r\n"+
					"id780 = "+ id780+"\r\n"+
					"id790 = "+ id790+"\r\n"+
					"id800 = "+ id800+"\r\n"+
					"id810 = "+ id810+"\r\n"+
					"id820 = "+ id820);
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