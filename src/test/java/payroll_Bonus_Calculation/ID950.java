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
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
/**
 * Display the amount paid by cash.
 */
public class ID950 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static EmployeeSettingPage employeeSetting;
	private static BonusPaymentStatements bonusPayment;
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		bonusPayment = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		Thread.sleep(4000);			
		//clean environment
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		//create company
		Common.createCompanyClient(driver, clientSetting.currentYear.getDatas().get("TS11265"));	
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting);
		Common.forwardToTopPage();
		//add EmployeeInfo
		Common.addSimpleEmployeeInfo(driver);	
	}
	/**
	 * If bonus time is unlocked and the stored #id930 is not same with the calculated #id930, or when [recalculate] is clicked in locked month.
	 * If Employee#bonusPaymentType is "cash" 
	 * #id950 = #id930(balance payment amount)
	 */
	@Test
	public void id950_1_1() throws Exception {
		//If Employee#bonusPaymentType is "cash" 
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));	
		ID940.selectBonusPaymentType(driver, employeeSetting, employeeSetting.bonusCashPayment.xpath);
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		//for to bonusPayment page
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		int id390 = (int) (Math.random() * 10000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 10; i++) {
			if(driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).getAttribute("value").equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		// #id950 = #id930(balance payment amount)
		String id950 = driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText();
		String id950Str = driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).getAttribute("value");
		if(id950.equals(id950Str)) {
			System.out.println("id950_1_1 Pass");
		}else {
			System.out.println("id950_1_1 Failed");
			System.out.println("id390 = " + id390);
			System.out.println("Theoretical value = " + id950 + "\n" + "Actual value = " + id950Str);
			throw new Exception("Error of calculation, expected: <["+id950+"]> but was: <["+id950Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ id950 +"\r\n"+
					"actual = "+ id950Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If bonus time is unlocked and the stored #id930 is not same with the calculated #id930, or when [recalculate] is clicked in locked month.
	 * If Employee#bonusPaymentType is "bankTranfer" 
	 * #id950 = 0
	 */
	@Test
	public void id950_1_2() throws Exception {
		//If Employee#bonusPaymentType is "bankTranfer" 
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));	
		ID940.selectBonusPaymentType(driver, employeeSetting, employeeSetting.bonusBankTransfer.xpath);
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		//for to bonusPayment page
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		int id390 = (int) (Math.random() * 10000000);
		// #id950 = 0
		int id950 = 0;
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 10; i++) {
			if(driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText().equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id950Str = driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).getAttribute("value");
		if(id950Str.equals("")) {
			id950Str = "0";
		}
		if(Common.formatNum(String.valueOf(id950)).equals(id950Str)) {
			System.out.println("id950_1_2 Pass");
		}else {
			System.out.println("id950_1_2 Failed");
			System.out.println("id390 = " + id390);
			System.out.println("Theoretical value = " + id950 + "\n" + "Actual value = " + id950Str);
			throw new Exception("Error of calculation, expected: <["+id950+"]> but was: <["+id950Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ id950 +"\r\n"+
					"actual = "+ id950Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	/**
	 * If bonus time is unlocked and #id940 is altered manually
	 * #id950 = #id930(balance payment amount) - #id940(balance payment amount by bank transfer)
	 */
	@Test
	public void id950_2() throws Exception {
		int id390 = (int) (Math.random() * 10000000);
		int id940 = (int) (Math.random() * 100000);
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		//input value
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).sendKeys(String.valueOf(id940));
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
			driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
			Thread.sleep(1000);	
		}
		String id930Str = driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText();
		int id930 = Integer.parseInt(id930Str.replace(",", ""));
		//id950 = id930 - id940
		int id950 = id930 - id940;
		String id950Str = driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id950)).equals(id950Str)) {
			System.out.println("id950_2 Pass");
		}else {
			System.out.println("id950_2 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id930 = " + id930 + "\n" + "id940 = " + id940);
			System.out.println("Theoretical value = " + id950 + "\n" + "Actual value = " + id950Str);
			throw new Exception("Error of calculation, expected: <["+id950+"]> but was: <["+id950Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ id950 +"\r\n"+
					"actual = "+ id950Str);
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
