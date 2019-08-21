package payroll_Bonus_Calculation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
 * Display the amount paid by bank transfer.
 */
public class ID940 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static EmployeeSettingPage employeeSetting;
	private static BonusPaymentStatements bonusPayment;
	
	private static String id930;
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
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		//add EmployeeInfo
		Common.addSimpleEmployeeInfo(driver);	
	}
	/**
	 * If bonus time is unlocked and the stored #id930 is not same with the calculated #id930, or when [recalculate] is clicked in locked month.
	 * If Employee#bonusPaymentType is "cash" 
	 */
	@Test
	public void id940_1_1() throws Exception {
		//If Employee#bonusPaymentType is "cash" 
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));	
		selectBonusPaymentType(driver, employeeSetting, employeeSetting.bonusCashPayment.xpath);
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
		//#id940 = 0
		int id940 = 0;
		//input value
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText().equals("")) {
					driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).click();
					Thread.sleep(1000);
			}
		}
		id930 = driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText();
		String id940Str = driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).getAttribute("value");
		if(id940Str.equals("")) {
			id940Str = "0";
		}
		if(Common.formatNum(String.valueOf(id940)).equals(id940Str)) {
			System.out.println("id940_1_1 Pass");
		}else {
			System.out.println("id940_1_1 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id930 = " +id930);
			System.out.println("Theoretical value = " + id940 + "\n" + "Actual value = " + id940Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id940))+"]> but was: <["+id940Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id940))+"\r\n"+
					"actual = "+ id940Str);
		}
		driver.findElement(By.xpath(bonusPayment.save.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.lockMonth.xpath)).click();
		Thread.sleep(1000);
		if(Common.isElementPresent(driver, By.xpath(bonusPayment.dateSettingYesButton.xpath), 2)) {
			driver.findElement(By.xpath(bonusPayment.dateSettingYesButton.xpath)).click();
			Thread.sleep(1000);
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
	}
	/**
	 * If Employee#bonusPaymentType is "bankTranfer" 
	 */
	@Test
	public void id940_1_2() throws Exception {
		//for to employeeSettingPage
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		selectBonusPaymentType(driver, employeeSetting, employeeSetting.bonusBankTransfer.xpath);
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		//for to bonusPayment page
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.lockMonth.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPayment.lockMonth.xpath)).click();
		Thread.sleep(1000);
		if(Common.isElementPresent(driver, By.xpath(bonusPayment.dateSettingYesButton.xpath), 2)) {
			driver.findElement(By.xpath(bonusPayment.dateSettingYesButton.xpath)).click();
			Thread.sleep(1000);
		}
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		Thread.sleep(2000);
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPayment.recaculate.xpath)).click();
		Thread.sleep(2000);
		if(Common.isElementPresent(driver, By.xpath(bonusPayment.dateSettingYesButton.xpath), 2)) {
			driver.findElement(By.xpath(bonusPayment.dateSettingYesButton.xpath)).click();
			Thread.sleep(2000);
		}
		Common.isNotPending();
		for(int i = 0; i < 30; i++){
			if(!driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).getAttribute("value").equals(id930)){
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id940Str = driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).getAttribute("value");
		if(id940Str.equals("")) {
			id940Str = "0";
		}
		if(id940Str.equals(id930)) {
			System.out.println("id940_1_2 Pass");
		}else {
			System.out.println("id940_1_2 Failed");
			System.out.println("id930 = " + id930);
			System.out.println("Theoretical value = " + id930 + "\n" + "Actual value = " + id940Str);
			throw new Exception("Error of calculation, expected: <["+id930+"]> but was: <["+id940Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ id930 +"\r\n"+
					"actual = "+ id940Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
	}
	/**
	 * If bonus time is unlocked and #id950 is altered manually
	 * #id940 = #id930(balance payment amount) - #id950(balance payment amount by bank transfer)
	 */
	@Test
	public void id940_2() throws Exception {
		int id390 = (int) (Math.random() * 10000000);

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
		for(int i = 0; i < 5; i++) {
			if(driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText().equals(Common.formatNum(String.valueOf(id930)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);	
			}
		}
		String id930Str = driver.findElement(By.xpath(bonusPayment.balancePayment.xpath)).getText();
		int id930 = Integer.parseInt(id930Str.replace(",", ""));
		int id950 = (int) (Math.random() * id930);
		driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).sendKeys(String.valueOf(id950));
		// id940 = id930 - id950
		int id940 = id930 - id950;
		for(int i = 0; i < 30; i++) {
			if(!driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).getAttribute("value").equals(Common.formatNum(String.valueOf(id940)))) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);	
			}
		}
		String id940Str = driver.findElement(By.xpath(bonusPayment.balancePayment2.xpath)).getAttribute("value");
		if(id940Str.equals(Common.formatNum(String.valueOf(id940)))) {
			System.out.println("ID940_2 Pass");
		}else {
			System.out.println("ID940_2 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id930 = " + id930 + "\n" + "id950 = " + id950);
			System.out.println("Theoretical value = " + id940 + "\n" + "Actual value = " + id940Str);
			throw new Exception("Error of calculation, expected: <["+id940+"]> but was: <["+id940Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ id940 +"\r\n"+
					"actual = "+ id940Str);
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
	
	public static void selectBonusPaymentType(WebDriver driver, EmployeeSettingPage employeeSetting, String xpath) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(employeeSetting.employeeCode_disp.xpath)));
		Common.isNotPending();
		//click edit
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		}
		Thread.sleep(1000);
		//click payment type
		for(int i = 0; i < 5; i++) {
			if(! driver.findElement(By.xpath(xpath)).isSelected()) {
				driver.findElement(By.xpath(xpath)).click();
				Thread.sleep(1000);
			}
		}
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		if(Common.isElementPresent(driver, By.xpath(employeeSetting.YES.xpath), 2)) {
			driver.findElement(By.xpath(employeeSetting.YES.xpath)).click();
		}
		Thread.sleep(2000);			
	}
}