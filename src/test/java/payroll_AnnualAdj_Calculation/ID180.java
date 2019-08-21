package payroll_AnnualAdj_Calculation;


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
import com.asaas.test.util.AnnualAdjustment;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;


/**
 * Calculate total amount of #id180(Salary/Allowance > Withhold Tax Total Amount) 
 **/
public class ID180 extends TestBase{
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

	@Test
	public void id180() throws Exception {
		//#id180=#id120(Salary/Allowance > Balance Tax Amount of #id69(n)=1) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=2) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=3) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=4) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=5) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=6) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=7) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=8) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=9) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=10) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=11) + #id120(Salary/Allowance > Balance Tax Amount of #id69(n)=12)
		int id120_1 = (int) (Math.random()*1000000);
		int id120_2 = (int) (Math.random()*1000000);
		int id120_3 = (int) (Math.random()*1000000);
		int id120_4 = (int) (Math.random()*1000000);
		int id120_5 = (int) (Math.random()*1000000);
		int id120_6 = (int) (Math.random()*1000000);
		int id120_7 = (int) (Math.random()*1000000);
		int id120_8 = (int) (Math.random()*1000000);
		int id120_9 = (int) (Math.random()*1000000);
		int id120_10 = (int) (Math.random()*1000000);
		int id120_11 = (int) (Math.random()*1000000);
		int id120_12 = (int) (Math.random()*1000000);
		
		int id170 = id120_1+id120_2+id120_3+id120_4+id120_5+id120_6+id120_7+id120_8+id120_9+id120_10+id120_11+id120_12;
		//input the value			
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount.xpath)).sendKeys(String.valueOf(id120_1));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_2.xpath)).sendKeys(String.valueOf(id120_2));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_3.xpath)).sendKeys(String.valueOf(id120_3));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_4.xpath)).sendKeys(String.valueOf(id120_4));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_5.xpath)).sendKeys(String.valueOf(id120_5));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_6.xpath)).sendKeys(String.valueOf(id120_6));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_7.xpath)).sendKeys(String.valueOf(id120_7));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_8.xpath)).sendKeys(String.valueOf(id120_8));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_9.xpath)).sendKeys(String.valueOf(id120_9));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_10.xpath)).sendKeys(String.valueOf(id120_10));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_11.xpath)).sendKeys(String.valueOf(id120_11));
		driver.findElement(By.xpath(annualAdjustment.calculatedTaxAmount_12.xpath)).sendKeys(String.valueOf(id120_12));
		driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
		Thread.sleep(3000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.calculatedTaxTotalAmount.xpath)).getText().replace(",","").equals(String.valueOf(id170))){
				driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
				Thread.sleep(1000);
			}
		}
		if(!driver.findElement(By.xpath(annualAdjustment.paymentDate_12ExcessDeficit.xpath)).getAttribute("value").isEmpty()){
			driver.findElement(By.xpath(annualAdjustment.paymentDate_12ExcessDeficit.xpath)).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
			Thread.sleep(1000);
		}
		int id140_1 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount1.xpath)).getText().replace(",",""));
		int id140_2 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount2.xpath)).getText().replace(",",""));
		int id140_3 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount3.xpath)).getText().replace(",",""));
		int id140_4 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount4.xpath)).getText().replace(",",""));
		int id140_5 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount5.xpath)).getText().replace(",",""));
		int id140_6 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount6.xpath)).getText().replace(",",""));
		int id140_7 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount7.xpath)).getText().replace(",",""));
		int id140_8 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount8.xpath)).getText().replace(",",""));
		int id140_9 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount9.xpath)).getText().replace(",",""));
		int id140_10 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount10.xpath)).getText().replace(",",""));
		int id140_11 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount11.xpath)).getText().replace(",",""));
		int id140_12 = Integer.valueOf(driver.findElement(By.xpath(annualAdjustment.balanceTaxAmount12.xpath)).getText().replace(",",""));
		
		int id180 = id140_1+id140_2+id140_3+id140_4+id140_5+id140_6+id140_7+id140_8+id140_9+id140_10+id140_11+id140_12;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.withholdTaxTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id180==actual){
			System.out.println("id180 Pass");
		}else{
			System.out.println("id180 Failed");
			throw new Exception("Error of calculation, expected: <["+id180+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id180 = "+ id180);
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
