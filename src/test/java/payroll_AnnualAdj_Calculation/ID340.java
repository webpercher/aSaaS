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
 * Calculate total amount of #id340(Salary/Bonus > Present Job Payment Total Amount) 
 **/
public class ID340 extends TestBase{
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
	public void id340() throws Exception {
		//#id340=#id150 + #id270
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
		
		int id340 = id150+id270;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.presentJobPaymentTotalAmount.xpath)).getAttribute("value").replace(",",""));
		//check the data
		if(id340==actual){
			System.out.println("id340 Pass");
		}else{
			System.out.println("id340 Failed");
			throw new Exception("Error of calculation, expected: <["+id340+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id150 = "+ id150+"\r\n"+
					"id270 = "+ id270+"\r\n"+
					"id340 = "+ id340);
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
