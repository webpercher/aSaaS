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
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
import com.google.common.base.Function;

/**
 * Display the total payment amount.
 **/
public class ID650 extends TestBase {
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
		//create precondition
		Common.createCompanyClient(driver);
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		addPayDate(driver, clientSetting);
		Common.forwardToTopPage();
		//add employees
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToTopPage();
		//add subsidies type
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		addSubsidiesType(driver, bonusPayment);
	}
	/**
	 * #id650=#id390(bonus payment amount) + #id410(allowance1 amount)+ #id430(allowance2 amount) 
	 * + #id450(allowance3 amount) + #id470(allowance4 amount) + #id490(allowance5 amount) 
	 * + #id510(allowance6 amount) + #id530(allowance7 amount) + #id550(allowance8 amount) 
	 * + #id570(allowance9 amount) + #id590(allowance10 amount) + #id610(allowance11 amount)
	 */
	@Test
	public void id650() throws Exception {
		int id390 = (int) (Math.random()*1000000);
		int id410 = (int) (Math.random()*1000000);
		int id430 = (int) (Math.random()*1000000);
		int id450 = (int) (Math.random()*1000000);
		int id470 = (int) (Math.random()*1000000);
		int id490 = (int) (Math.random()*1000000);
		int id510 = (int) (Math.random()*1000000);
		int id530 = (int) (Math.random()*1000000);
		int id550 = (int) (Math.random()*1000000);
		int id570 = (int) (Math.random()*1000000);
		int id590 = (int) (Math.random()*1000000);
		int id610 = (int) (Math.random()*1000000);
		int id650 = id390 + id410 + id430 + id450 + id470 + id490 
				+ id510 + id530 + id550 + id570 + id590 + id610;
		// forward to bonusSlipPage
		driver.findElement(By.xpath(bonusPayment.bonusSlipPage.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(2000);
		// input the value
		Common.clear(driver, bonusPayment.bonusPayment.xpath);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		Common.clear(driver, bonusPayment.deduction.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction.xpath)).sendKeys(String.valueOf(id410));
		Common.clear(driver, bonusPayment.deduction2.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction2.xpath)).sendKeys(String.valueOf(id430));
		Common.clear(driver, bonusPayment.deduction3.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction3.xpath)).sendKeys(String.valueOf(id450));
		Common.clear(driver, bonusPayment.deduction4.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction4.xpath)).sendKeys(String.valueOf(id470));
		Common.clear(driver, bonusPayment.deduction5.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction5.xpath)).sendKeys(String.valueOf(id490));
		Common.clear(driver, bonusPayment.deduction6.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction6.xpath)).sendKeys(String.valueOf(id510));
		Common.clear(driver, bonusPayment.deduction7.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction7.xpath)).sendKeys(String.valueOf(id530));
		Common.clear(driver, bonusPayment.deduction8.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction8.xpath)).sendKeys(String.valueOf(id550));
		Common.clear(driver, bonusPayment.deduction9.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction9.xpath)).sendKeys(String.valueOf(id570));
		Common.clear(driver, bonusPayment.deduction10.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction10.xpath)).sendKeys(String.valueOf(id590));
		Common.clear(driver, bonusPayment.deduction11.xpath);
		driver.findElement(By.xpath(bonusPayment.deduction11.xpath)).sendKeys(String.valueOf(id610));
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText().equals(Common.formatNum(String.valueOf(id650)))){
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String sumData = driver.findElement(By.xpath(bonusPayment.sumData1.xpath)).getText();	
		if(Common.formatNum(String.valueOf(id650)).equals(sumData)) {
			System.out.println("ID650 Pass");
		}else {
			System.out.println("ID650 Failed");
			System.out.println(id390 + " + " + id410 + " + " + id430 + " + " + id450 + " + " + id470 + " + " + id490 
					+ " + " + id510 + " + " + id530 + " + " + id550 + " + " + id570 + " + " + id590 + " + " + id610 + " = "
					+ id650 + "\n" + "sumData = " + sumData);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id650))+"]> but was: <["+sumData+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id650))+"\r\n"+
					"actual = "+ sumData);
		}
		//close
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
	
	public static void addSubsidiesType(WebDriver driver, BonusPaymentStatements bonusPayment) throws Exception {
		// add subsidies type
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.onetime.xpath)));	
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPayment.bonusAllowanceDeductionSetting.xpath)).click();
		Common.isNotPending();
		try{
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.deductionButton.xpath)));
		}catch(Exception ex) {
			driver.findElement(By.xpath(bonusPayment.bonusAllowanceDeductionSetting.xpath)).click();
			Common.isNotPending();
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.deductionButton.xpath)));
		}
		driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		// input subsidies
		setInput(bonusPayment.allowanceName.xpath, bonusPayment.allowanceName.getDatas().get("TS1812"));
		setInput(bonusPayment.allowanceName2.xpath, bonusPayment.allowanceName2.getDatas().get("TS1813"));
		setInput(bonusPayment.allowanceName3.xpath, bonusPayment.allowanceName3.getDatas().get("TS1815"));
		setInput(bonusPayment.allowanceName4.xpath, bonusPayment.allowanceName4.getDatas().get("TS1817"));
		setInput(bonusPayment.allowanceName5.xpath, bonusPayment.allowanceName5.getDatas().get("TS1818"));
		setInput(bonusPayment.allowanceName6.xpath, bonusPayment.allowanceName6.getDatas().get("TS1820"));
		setInput(bonusPayment.allowanceName7.xpath, bonusPayment.allowanceName7.getDatas().get("TS1821"));
		setInput(bonusPayment.allowanceName8.xpath, bonusPayment.allowanceName8.getDatas().get("TS1822"));
		setInput(bonusPayment.allowanceName9.xpath, bonusPayment.allowanceName9.getDatas().get("TS1823"));
		setInput(bonusPayment.allowanceName10.xpath, bonusPayment.allowanceName10.getDatas().get("TS1824"));
		setInput(bonusPayment.allowanceName11.xpath, bonusPayment.allowanceName11.getDatas().get("TS1825"));
		// save subsidies
		driver.findElement(By.xpath(bonusPayment.save.xpath)).click();
		Common.isNotPending();
	}

	public static void addPayDate(WebDriver driver, ClientSettingPage clientSetting) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.paymentDateSetting.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		try{
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.bonusPaymentDateSetting_page.xpath)));
		}catch(Exception ex) {
			driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
			Common.isNotPending();
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.bonusPaymentDateSetting_page.xpath)));
		}
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(clientSetting.paymentDateMonth.getDatas().get("TS11266"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(clientSetting.paymentDateDay.getDatas().get("TS11266"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
	}
	
	public static void addPayDate(WebDriver driver, ClientSettingPage clientSetting, String month, String date) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.paymentDateSetting.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		try{
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.bonusPaymentDateSetting_page.xpath)));
		}catch(Exception ex) {
			driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
			Common.isNotPending();
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.bonusPaymentDateSetting_page.xpath)));
		}
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(month);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(date);
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
	}
	
	private static void setInput(final String xpath, final String value) throws Exception{
		driver.findElement(By.xpath(xpath)).clear();
		driver.findElement(By.xpath(xpath)).sendKeys(value);
		new WebDriverWait(driver, 30).until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return value.equals(driver.findElement(By.xpath(xpath)).getAttribute("value"));
			  }
		});
	}
	
}
