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
 * Calculate total amount of #id280(Bonus > Social Insurance Total Amount) 
 **/
public class ID280 extends TestBase{
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
	public void id280() throws Exception {
		//#id280=#id220(Bonus > Social Insurance Rate of #id189(n)=1) + #id220(Bonus > Social Insurance Rate of #id189(n)=2) + #id220(Bonus > Social Insurance Rate of #id189(n)=3) + #id220(Bonus > Social Insurance Rate of #id189(n)=4)
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
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.bonusSocialInsuranceTotalAmount.xpath)).getText().replace(",",""));
		//check the data
		if(id280==actual){
			System.out.println("id280 Pass");
		}else{
			System.out.println("id280 Failed");
			throw new Exception("Error of calculation, expected: <["+id280+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id280 = "+ id280);
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
