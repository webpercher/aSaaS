package payroll_Bonus_Calculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.BonusPaymentStatements;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;

/**
 * Display the tax rate of the withholding income tax to be collected from each employee.
 **/
public class ID1310_24 extends TestBase {
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static BonusPaymentStatements bonusPaymentStatements;
	public static int total;

	@BeforeClass
	public static void setUp() throws Exception {
		// initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		bonusPaymentStatements = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		// click pensionFundApplying_old_insuredPersonPaymentProportion-SaaS
		// menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		// clean env
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		// precondition
		Common.createCompanyClient(driver, "24");
		Common.addSimpleEmployeeInfo(driver);
	}

	// If the selected bonus time's payment date < 2013/01/01
	// If Employee#monthlyTableType is "KO"
	@Test
	public void id1310_24_1() throws Exception {
		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		for(int i = 0; i < 10; i ++){
			driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		}
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.koMonthlyTable.xpath)).click();
		// set people
		int dependentsNumber = new Random().nextInt(10);
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(
				String.valueOf(dependentsNumber));
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(1000);
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		// edit pay day
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// click edit button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Common.isNotPending();
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("TS11266"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(
				clientSetting.paymentDateDay.getDatas().get("TS11266"));
		Common.isNotPending();
		// click save button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		// close
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);

		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		// input id1300
		driver.findElement(By.xpath(bonusPaymentStatements.paymentAmountEtc.xpath)).clear();
		int paymentAmountEtc = new Random().nextInt(2000000);
		driver.findElement(By.xpath(bonusPaymentStatements.paymentAmountEtc.xpath)).sendKeys(
				String.valueOf(paymentAmountEtc));
		// clear id720
		driver.findElement(By.xpath(bonusPaymentStatements.incomeTax.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		// check the result
		int taxRate = Integer.parseInt((driver
				.findElement(By.xpath(bonusPaymentStatements.taxRate.xpath)).getText()
				.replace(".", "").replace("%", "").replace(".", "")));
		int realTaxRate = getCSVData_24((paymentAmountEtc / 1000) , dependentsNumber);
		if (taxRate == realTaxRate) {
			System.out.println("id1310_24_1 Passed");
		} else {
			System.out.println("id1310_24_1 Failed");
			throw new Exception("taxRate=" + taxRate + " realTaxRate=" + realTaxRate);
		}

	}

	// If Employee#monthlyTableType is "OTU"
	@Test
	public void id1310_24_2() throws Exception {
		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		// edit and let the client has joined social insurance
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// set monthlyTableType
		driver.findElement(By.xpath(employeeSetting.monthlyTableType.xpath)).click();
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		driver.findElement(By.xpath(employeeSetting.YES.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Common.isNotPending();

		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		// input id1300
		driver.findElement(By.xpath(bonusPaymentStatements.paymentAmountEtc.xpath)).clear();
		int paymentAmountEtc = new Random().nextInt(2000000);
		driver.findElement(By.xpath(bonusPaymentStatements.paymentAmountEtc.xpath)).sendKeys(
				String.valueOf(paymentAmountEtc));
		// clear id720
		driver.findElement(By.xpath(bonusPaymentStatements.incomeTax.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		// check the result
		int taxRate = Integer.parseInt((driver
				.findElement(By.xpath(bonusPaymentStatements.taxRate.xpath)).getText()
				.replace(".", "").replace("%", "").replace(".", "")));
		int realTaxRate = 0;
		if (paymentAmountEtc < 241000) {
			realTaxRate = 10;
		} else if (paymentAmountEtc >= 241000 && paymentAmountEtc < 305000) {
			realTaxRate = 20;
		} else if (paymentAmountEtc >= 305000 && paymentAmountEtc < 563000) {
			realTaxRate = 30;
		} else if (paymentAmountEtc >= 563000) {
			realTaxRate = 38;
		}
		if (taxRate == realTaxRate) {
			System.out.println("id1310_24_2 Passed");
		} else {
			System.out.println("id1310_24_2 Failed");
			throw new Exception("taxRate=" + taxRate + " realTaxRate=" + realTaxRate);
		}

	}

	// getCSVData 24
	public static int getCSVData_24(int value, int people) throws Exception {
		File file = new File(".");
		String filePath = file.getCanonicalPath() + "\\resources\\pages\\testdata\\"
				+ "WithholdingTaxRateForBonus2013_24.csv";
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				filePath), "Shift-JIS"));
		String line = null;
		if (people > 7) {
			people = 7;
		}
		String key = "lower" + String.valueOf(people);
		// get max account of one line
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for (int i = 0; i < item.length; i++) {
			if (item[i].equals(key)) {
				num = i;
			}
		}

		int result = 0;
		line = reader.readLine();
		while ((line) != null) {
			// line.split(",")[num]
			// value
			// line.split(",")[num+1]
			// line.split(",")[0]
			if ((Integer.parseInt((line.split(",")[num]).replace(".", "").trim()) < value)
					&& (Integer.parseInt((line.split(",")[num + 1]).replace(".", "").trim()) > value)) {
				result = Integer.valueOf(line.split(",")[0].replace(".", ""));
				return result;
			}
			line = reader.readLine();
		}
		return result;
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// return to dashboard page and exit system
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}

}