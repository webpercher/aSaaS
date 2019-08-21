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
 * Calculate total amount of #id150(Salary/Allowance > Taxable Payment Total Amount) 
 **/
public class ID150 extends TestBase{
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
	public void id150() throws Exception {
		//#id150=#id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=1) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=2) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=3) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=4) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=5) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=6) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=7月) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=8) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=9) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=10) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=11) + #id90(Salary/Allowance > Taxable Payment Amount of #id69(n)=12月)
		int id90_1 = (int) (Math.random()*100000000);
		int id90_2 = (int) (Math.random()*100000000);
		int id90_3 = (int) (Math.random()*100000000);
		int id90_4 = (int) (Math.random()*100000000);
		int id90_5 = (int) (Math.random()*100000000);
		int id90_6 = (int) (Math.random()*100000000);
		int id90_7 = (int) (Math.random()*100000000);
		int id90_8 = (int) (Math.random()*100000000);
		int id90_9 = (int) (Math.random()*100000000);
		int id90_10 = (int) (Math.random()*100000000);
		int id90_11 = (int) (Math.random()*100000000);
		int id90_12 = (int) (Math.random()*100000000);
		
		int id150 = id90_1+id90_2+id90_3+id90_4+id90_5+id90_6+id90_7+id90_8+id90_9+id90_10+id90_11+id90_12;
		//input the value			
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment.xpath)).sendKeys(String.valueOf(id90_1));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_2.xpath)).sendKeys(String.valueOf(id90_2));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_3.xpath)).sendKeys(String.valueOf(id90_3));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_4.xpath)).sendKeys(String.valueOf(id90_4));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_5.xpath)).sendKeys(String.valueOf(id90_5));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_6.xpath)).sendKeys(String.valueOf(id90_6));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_7.xpath)).sendKeys(String.valueOf(id90_7));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_8.xpath)).sendKeys(String.valueOf(id90_8));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_9.xpath)).sendKeys(String.valueOf(id90_9));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_10.xpath)).sendKeys(String.valueOf(id90_10));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_11.xpath)).sendKeys(String.valueOf(id90_11));
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment_12.xpath)).sendKeys(String.valueOf(id90_12));
		driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
		Thread.sleep(3000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.taxablePaymentTotalAmount.xpath)).getText().replace(",","").equals(String.valueOf(id150))){
				driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.taxablePaymentTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id150==actual){
			System.out.println("id150 Pass");
		}else{
			System.out.println("id150 Failed");
			throw new Exception("Error of calculation, expected: <["+id150+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id150 = "+ id150);
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
