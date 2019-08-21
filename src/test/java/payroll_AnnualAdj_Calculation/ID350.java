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
 * Calculate total amount of #id350(Salary/Bonus > Present Job Social Insurance Total Amount) 
 **/
public class ID350 extends TestBase{
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
	public void id350() throws Exception {
		//#id350=#id160 + #id280
		int id100_1 = (int) (Math.random()*100000000);
		int id100_2 = (int) (Math.random()*100000000);
		int id100_3 = (int) (Math.random()*100000000);
		int id100_4 = (int) (Math.random()*100000000);
		int id100_5 = (int) (Math.random()*100000000);
		int id100_6 = (int) (Math.random()*100000000);
		int id100_7 = (int) (Math.random()*100000000);
		int id100_8 = (int) (Math.random()*100000000);
		int id100_9 = (int) (Math.random()*100000000);
		int id100_10 = (int) (Math.random()*100000000);
		int id100_11 = (int) (Math.random()*100000000);
		int id100_12 = (int) (Math.random()*100000000);

		int id160 = id100_1+id100_2+id100_3+id100_4+id100_5+id100_6+id100_7+id100_8+id100_9+id100_10+id100_11+id100_12;
		//input the value			
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate.xpath)).sendKeys(String.valueOf(id100_1));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate2.xpath)).sendKeys(String.valueOf(id100_2));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate3.xpath)).sendKeys(String.valueOf(id100_3));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate4.xpath)).sendKeys(String.valueOf(id100_4));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate5.xpath)).sendKeys(String.valueOf(id100_5));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate6.xpath)).sendKeys(String.valueOf(id100_6));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate7.xpath)).sendKeys(String.valueOf(id100_7));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate8.xpath)).sendKeys(String.valueOf(id100_8));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate9.xpath)).sendKeys(String.valueOf(id100_9));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate10.xpath)).sendKeys(String.valueOf(id100_10));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate11.xpath)).sendKeys(String.valueOf(id100_11));
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate12.xpath)).sendKeys(String.valueOf(id100_12));
		driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
		Thread.sleep(3000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.socialInsuranceTotalAmount.xpath)).getText().replace(",","").equals(String.valueOf(id160))){
				driver.findElement(By.xpath(annualAdjustment.dependentNumber12.xpath)).click();
				Thread.sleep(1000);
			}
		}
		
		int id220_1 = (int) (Math.random()*100000000);
		int id220_2 = (int) (Math.random()*100000000);
		int id220_3 = (int) (Math.random()*100000000);
		int id220_4 = (int) (Math.random()*100000000);
		
		int id280 = id220_1+id220_2+id220_3+id220_4;
		//input the value			
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).sendKeys(String.valueOf(id220_1));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).sendKeys(String.valueOf(id220_2));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_3.xpath)).sendKeys(String.valueOf(id220_3));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_4.xpath)).sendKeys(String.valueOf(id220_4));
		driver.findElement(By.xpath(annualAdjustment.bonusDependentNumber_1.xpath)).click();
		Thread.sleep(3000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.bonusSocialInsuranceTotalAmount.xpath)).getText().replace(",","").equals(String.valueOf(id280))){
				driver.findElement(By.xpath(annualAdjustment.bonusDependentNumber_1.xpath)).click();
				Thread.sleep(1000);
			}
		}
		
		int id350 = id160+id280;
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.presentJobSocialInsuranceTotalAmount.xpath)).getAttribute("value").replace(",",""));
		//check the data
		if(id350==actual){
			System.out.println("id350 Pass");
		}else{
			System.out.println("id350 Failed");
			throw new Exception("Error of calculation, expected: <["+id350+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id160 = "+ id160+"\r\n"+
					"id280 = "+ id280+"\r\n"+
					"id350 = "+ id350);
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
