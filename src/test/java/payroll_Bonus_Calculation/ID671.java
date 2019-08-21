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
 * Display the sum of health insurnace amount and nursing insurance amount.
 */
public class ID671 extends TestBase {
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
		//add employees
		Common.addSimpleEmployeeInfo(driver);
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(4000);
	}
	/**
	 * #id671 = #id660 + #id670
	 */
	@Test
	public void id671() throws Exception {
		int id660 = (int) (Math.random() * 10000000);
		int id670 = (int) (Math.random() * 10000000);
		//#id671 = #id660 + #id670
		int id671 = id660 + id670;
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		//input value
		driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).sendKeys(String.valueOf(id660));
		driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).sendKeys(String.valueOf(id670));
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.socialSecurity_dis.xpath)).getText().equals(Common.formatNum(String.valueOf(id671)))){
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get value
		String id671Str = driver.findElement(By.xpath(bonusPayment.socialSecurity_dis.xpath)).getText();
		//check the data
		if(Common.formatNum(String.valueOf(id671)).equals(id671Str)){
			System.out.println("ID671 Pass");
		}else{
			System.out.println("ID671 Failed");
			System.out.println("id660 = " + id660 + "\n" + "id670 = " + id670);
			System.out.println("Theoretical value = " + id671 + "\n" + "Actual value = " + id671Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id671))+"]> but was: <["+id671Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id671))+"\r\n"+
					"actual = "+ id671Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//clean environment
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
}
