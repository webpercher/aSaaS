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
/**
 * Display the calculated value of non-taxable payment amount.
 */
public class ID1120 extends TestBase {
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
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		//add subsidies type
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		ID650.addSubsidiesType(driver, bonusPayment);
		//select non-taxable
		selectNonTaxable(driver, bonusPayment);
		// forward to bonusSlipPage
		driver.findElement(By.xpath(bonusPayment.bonusSlipPage.xpath)).click();
		Common.isNotPending();
	}
	/**
	 * #id1120 = #id650 - #id1110
	 */
	@Test
	public void id1120_1() throws Exception {
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
		int id650 = id390 + id410 + id430 + id450 + id470 + id490 + id510 + id530 + id550 + id570 + id590 + id610;
		// click edit
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
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1110Str = driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText();
		int id1110 = Integer.parseInt(id1110Str.replace(",", ""));
		// id1120 = id650 - id1110
		int id1120 = id650 - id1110;
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.totalPaymentNonTax.xpath)).getText().equals(Common.formatNum(String.valueOf(id1120)))){
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1120Str = driver.findElement(By.xpath(bonusPayment.totalPaymentNonTax.xpath)).getText();
		if(Common.formatNum(String.valueOf(id1120)).equals(id1120Str)) {
			System.out.println("id1120_1 Pass");
		}else {
			System.out.println("id1120_1 Failed");
			System.out.println("id390 = " + id390 + "  id410 = " + id410 + "  id430 = " + id430 + "  id450 = " + id450 + "  id470 = " + id470 +"  id490 = "
					+ id490 + "  id510 = " + id510 + "  id530 = " + id530 + "  id550 = " + id550 + "  id570 = " + id570 + "  id590 = " + id590 + "  id610 = " + id610);
			System.out.println( "Theoretical value = " + id1120 + "\n" + "Actual value = " + id1120Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1120))+"]> but was: <["+id1120Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1120))+"\r\n"+
					"actual = "+ id1120Str);
		}
	}
	/**
	 * #id650 or #1110 is calculated or modified.
	 */
	@Test
	public void id1120_2() throws Exception {
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
		int id650 = id390 + id410 + id430 + id450 + id470 + id490 + id510 + id530 + id550 + id570 + id590 + id610;
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
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1110Str = driver.findElement(By.xpath(bonusPayment.totalPaymentTax.xpath)).getText();
		int id1110 = Integer.parseInt(id1110Str.replace(",", ""));
		// id1120 = id650 - id1110
		int id1120 = id650 - id1110;
		for(int a = 0;a<30;a++){
			if(!driver.findElement(By.xpath(bonusPayment.totalPaymentNonTax.xpath)).getText().equals(Common.formatNum(String.valueOf(id1120)))){
				driver.findElement(By.xpath(bonusPayment.balancePayment3.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1120Str = driver.findElement(By.xpath(bonusPayment.totalPaymentNonTax.xpath)).getText();
		if(Common.formatNum(String.valueOf(id1120)).equals(id1120Str)) {
			System.out.println("id1120_2 Pass");
		}else {
			System.out.println("id1120_2 Failed");
			System.out.println("id390 = " + id390 + "  id410 = " + id410 + "  id430 = " + id430 + "  id450 = " + id450 + "  id470 = " + id470 +"  id490 = "
					+ id490 + "  id510 = " + id510 + "  id530 = " + id530 + "  id550 = " + id550 + "  id570 = " + id570 + "  id590 = " + id590 + "  id610 = " + id610);
			System.out.println( id650 + " - " + id1110 + " = " + id1120 + "\n" + "totalPaymentTax = " + id1120Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id1120))+"]> but was: <["+id1120Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id1120))+"\r\n"+
					"actual = "+ id1120Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//clean environment
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);	
	}
	
	public static void selectNonTaxable(WebDriver driver, BonusPaymentStatements bonusPayment) throws Exception {
		Common.isNotPending();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.deductionButton.xpath)));
		driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		Thread.sleep(1000);
		selectCheckBox(bonusPayment.withholdingIncomeTax1.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax2.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax3.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax4.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax5.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax6.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax7.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax8.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax9.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax10.xpath);
		selectCheckBox(bonusPayment.withholdingIncomeTax11.xpath);
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.save.xpath)).click();
		Common.isNotPending();
	}
	
	private static void selectCheckBox(String xpath) {
		for(int i = 0; i < 5; i++) {
			if(! driver.findElement(By.xpath(xpath)).isSelected()) {
				driver.findElement(By.xpath(xpath)).click();
			}
		}
	}
}
