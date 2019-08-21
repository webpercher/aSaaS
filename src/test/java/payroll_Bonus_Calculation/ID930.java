package payroll_Bonus_Calculation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.BonusPaymentStatements;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
/**
 * Display the payment amount from which the deduction amount is subtracted and to/from which the adjustment amount is added/subtracted.
 */
public class ID930 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static BonusPaymentStatements bonusPayment;
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		bonusPayment = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		Thread.sleep(4000);
		//clean environment
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));	
		//create company
		Common.createCompanyClient(driver, clientSetting.currentYear.getDatas().get("TS11265"));
		//add EmployeeInfo
		Common.addSimpleEmployeeInfo(driver);
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting);
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
	}
	/**
	 * #id930 =  #id650(payment amount) - #id880(deduction sum amount) + #id900(adjustment amount 1) 
	 * 				  + #id910(adjustment amount 2) + #id920(annual adjustment amount)
	 */
	@Test
	public void id930() throws Exception {
		int id390 = (int)(Math.random() * 10000000);
		int id900 = (int)(Math.random() * 10000000);
		int id910 = (int)(Math.random() * 10000000);
		int id920 = (int)(Math.random() * 10000000);
		//input value
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		driver.findElement(By.xpath(bonusPayment.adjustmentAmount.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.adjustmentAmount.xpath)).sendKeys(String.valueOf(id900));
		driver.findElement(By.xpath(bonusPayment.adjustmentAmount2.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.adjustmentAmount2.xpath)).sendKeys(String.valueOf(id910));
		driver.findElement(By.xpath(bonusPayment.annualAdjustment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.annualAdjustment.xpath)).sendKeys(String.valueOf(id920));
		driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
		for(int i = 0; i < 10; i++) {
			if(driver.findElement(By.xpath(bonusPayment.deductionSum.xpath)).getText().equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id650Str = driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText();
		int id650 = Integer.parseInt(id650Str.replace(",", ""));
		String id880Str = driver.findElement(By.xpath(bonusPayment.deductionSum.xpath)).getText();
		int id880 = Integer.parseInt(id880Str.replace(",", ""));
		//id930 = id650 - id880 + id900 + id910 + id920
		int id930 = id650 - id880 + id900 + id910 + id920;
		for(int i = 0; i < 10; i++){
			if(!driver.findElement(By.xpath(bonusPayment.lessAllowance_disp.xpath)).getText().equals(Common.formatNum(String.valueOf(id930)))){
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id930Str = driver.findElement(By.xpath(bonusPayment.lessAllowance_disp.xpath)).getText();
		if(Common.formatNum(String.valueOf(id930)).equals(id930Str)) {
			System.out.println("ID930 Pass");
		}else {
			System.out.println("ID930 Failed");
			System.out.println(id650 + " - " + id880 + " + " + id900 + " + " + id910 + " + " + id920 + " = "
					+ id930 + "\n" + "Actual value = " + id930Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id930))+"]> but was: <["+id930Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id930))+"\r\n"+
					"actual = "+ id930Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//clean environment
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);	
	}
}
