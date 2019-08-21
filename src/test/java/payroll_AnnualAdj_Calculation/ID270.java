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
 * Calculate total amount of #id270(Bonus > Taxable Payment Total Amount) 
 **/
public class ID270 extends TestBase{
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
	public void id270() throws Exception {
		//#id150=#id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=1) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=2) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=3) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=4) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=5) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=6) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=7月) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=8) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=9) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=10) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=11) + #id210(Salary/Allowance > Taxable Payment Amount of #id69(n)=12月)
		int id210_1 = (int) (Math.random()*100000000);
		int id210_2 = (int) (Math.random()*100000000);
		int id210_3 = (int) (Math.random()*100000000);
		int id210_4 = (int) (Math.random()*100000000);
		
		int id270 = id210_1+id210_2+id210_3+id210_4;
		//input the value			
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_1.xpath)).sendKeys(String.valueOf(id210_1));
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_2.xpath)).sendKeys(String.valueOf(id210_2));
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_3.xpath)).sendKeys(String.valueOf(id210_3));
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_4.xpath)).sendKeys(String.valueOf(id210_4));
		driver.findElement(By.xpath(annualAdjustment.bonusDependentNumber_1.xpath)).click();
		Thread.sleep(3000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.bonusTaxablePaymentTotalAmount.xpath)).getText().replace(",","").equals(String.valueOf(id270))){
				driver.findElement(By.xpath(annualAdjustment.bonusDependentNumber_1.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.bonusTaxablePaymentTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id270==actual){
			System.out.println("id270 Pass");
		}else{
			System.out.println("id270 Failed");
			throw new Exception("Error of calculation, expected: <["+id270+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id270 = "+ id270);
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
