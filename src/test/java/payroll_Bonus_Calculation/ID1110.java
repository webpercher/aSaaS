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
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
/**
 * Display the taxable payment amount.
 */
public class ID1110 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static BonusPaymentStatements bonusPayment;
	private static PaymentStatements paymentStatement;
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		bonusPayment = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		paymentStatement = (PaymentStatements) helper.getPage("PaymentStatements");
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
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		//add subsidies type
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		ID650.addSubsidiesType(driver, bonusPayment);
		// forward to bonusSlipPage
		driver.findElement(By.xpath(bonusPayment.bonusSlipPage.xpath)).click();
	}
	/**
	 * ELSE
	 */
	@Test
	public void id1110_2() throws Exception {
		int id390 = (int) (Math.random() * 10000000);
		int id410 = (int) (Math.random() * 1000000);
		int id430 = (int) (Math.random() * 1000000);
		int id450 = (int) (Math.random() * 1000000);
		int id470 = (int) (Math.random() * 1000000);
		int id490 = (int) (Math.random() * 1000000);
		int id510 = (int) (Math.random() * 1000000);
		int id530 = (int) (Math.random() * 1000000);
		int id550 = (int) (Math.random() * 1000000);
		int id570 = (int) (Math.random() * 1000000);
		int id590 = (int) (Math.random() * 1000000);
		int id610 = (int) (Math.random() * 1000000);
		// Y(the total allowance amount) = the total allowance amount which "withholding income tax is Off in
		//	BonusVoluntaryItemAmount#allowanceSetting out of #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
		final int Y = id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610;
		// #id1110 = #id390(bonus payment amount) + Y
		int id1110 = id390+Y;
		// click edit
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
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
		Thread.sleep(1000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText().equals(Common.formatNum(String.valueOf(id1110)))){
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1110Str = driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText();
		if(Common.formatNum(String.valueOf(id1110)).equals(id1110Str)) {
			System.out.println("ID1110_2 Pass");
		}else {
			System.out.println("ID1110_2 Failed");
			System.out.println(id390 + " + " + id410 + " + " + id430 + " + " + id450 + " + " + id470 + " + " + id490 
					+ " + " + id510 + " + " + id530 + " + " + id550 + " + " + id570 + " + " + id590 + " + " + id610 + " = "
					+ id1110 + "\n" + "totalPaymentTax = " + id1110Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1110))+"]> but was: <["+id1110Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1110))+"\r\n"+
					"actual = "+ id1110Str);
		}
		driver.findElement(By.xpath(bonusPayment.save.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(5000);
	}
	/**
	 * If the selected payroll month is locked and in edit mode(not in recalculation) 
	 */
	@Test
	public void id1110_1() throws Exception {
		// selected payroll month is locked
		Common.forwardToPage(topPage.projectID.getDatas().get("paymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(paymentStatement.lockMonth.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(paymentStatement.lockMonth.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatement.definition.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(paymentStatement.close.xpath)).click();
		Thread.sleep(5000);
		int id390 = (int) (Math.random() * 10000000);
		int id410 = (int) (Math.random() * 1000000);
		int id430 = (int) (Math.random() * 1000000);
		int id450 = (int) (Math.random() * 1000000);
		int id470 = (int) (Math.random() * 1000000);
		int id490 = (int) (Math.random() * 1000000);
		int id510 = (int) (Math.random() * 1000000);
		int id530 = (int) (Math.random() * 1000000);
		int id550 = (int) (Math.random() * 1000000);
		int id570 = (int) (Math.random() * 1000000);
		int id590 = (int) (Math.random() * 1000000);
		int id610 = (int) (Math.random() * 1000000);
		// Y(the total allowance amount) = the total allowance amount which "withholding income tax is Off in
		//	BonusVoluntaryItemAmount#allowanceSetting out of #id410,#id430,#id450,#id470,#id490,#id510,#id530,#id550,#id570,#id590,#id610.
		final int Y = id410+id430+id450+id470+id490+id510+id530+id550+id570+id590+id610;
		// #id1110 = #id390(bonus payment amount) + Y
		int id1110 = id390+Y;
		// forward to bonusSlipPage
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		// click edit
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
		Thread.sleep(3000);
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText().equals(Common.formatNum(String.valueOf(id1110)))){
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1110Str = driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText();
		if(Common.formatNum(String.valueOf(id1110)).equals(id1110Str)) {
			System.out.println("ID1110_1 Pass");
		}else {
			System.out.println("ID1110_1 Failed");
			System.out.println(id390 + " + " + id410 + " + " + id430 + " + " + id450 + " + " + id470 + " + " + id490 
					+ " + " + id510 + " + " + id530 + " + " + id550 + " + " + id570 + " + " + id590 + " + " + id610 + " = "
					+ id1110 + "\n" + "totalPaymentTax = " + id1110Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1110))+"]> but was: <["+id1110Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1110))+"\r\n"+
					"actual = "+ id1110Str);
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//clean environment
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);	
	}
}
