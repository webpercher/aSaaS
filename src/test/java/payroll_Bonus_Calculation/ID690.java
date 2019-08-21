package payroll_Bonus_Calculation;

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
 * Display the calculated pension funds amount.
 **/
public class ID690 extends TestBase {
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static BonusPaymentStatements bonusPaymentStatements;

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
		// click pensionFundsAmount_input-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		// clean env
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		// precondition
		Common.createCompanyClient(driver, "24");
		Common.addSimpleEmployeeInfo(driver);
	}

	/**
	 * If Employee#fixedAmountPensionFundsFlag is false s = #insuredPersonPaymentProportion which
	 * ClientPensionInsurance#insuranceType='pensionFunds' and ClientPensionInsurance#startDate <=
	 * {PayrollBonus#paymentDate for the selected time}, and also #endDate is null or #endDate >=
	 * {PayrollBonus#paymentDate for the selected time} S = #id1210 × s #id690= S rounded to the
	 * nearest integer according to
	 * PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
	 **/
	@Test
	public void id690() throws Exception {
		// get somerandom numbers
		int pensionFundsAmount_input = Math.abs(new Random().nextInt(100));
		String rate = String.valueOf(pensionFundsAmount_input);
		// get pensionFundsAmount_input random number
		int bonusPayment = Math.abs(new Random().nextInt(1000000));

		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		// edit and let the client has joined social insurance
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		// check whether employeesPension is checked
		if (driver.findElement(By.xpath(employeeSetting.employeesPension.xpath)).isSelected()) {
		} else {
			driver.findElement(By.xpath(employeeSetting.employeesPension.xpath)).click();
			Common.isNotPending();
		}
		Thread.sleep(1000);
		// check Employee#fixedAmountSocialSecurityFlag is false
		if (driver.findElement(By.xpath(employeeSetting.fixedAmountSocialSecurity.xpath))
				.isSelected()) {
			driver.findElement(By.xpath(employeeSetting.fixedAmountSocialSecurity.xpath)).click();
		}
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Common.isNotPending();

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit pay day
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		// click edit button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("common"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(
				clientSetting.paymentDateDay.getDatas().get("common"));
		Common.isNotPending();
		// click save button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		// change foundation
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// click edit
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// click foundation radio
		driver.findElement(By.xpath(clientSetting.pensionFoundAmount.xpath)).click();
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		// click ratesetting
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		// edit the rate
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		// input the random rate
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_input.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_input.xpath)).sendKeys(rate);
		// input date
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_year.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_year.xpath)).sendKeys(
				clientSetting.currentYear.getDatas().get("TS11346"));
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_month.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.pensionFundsAmount_month.xpath)).sendKeys(
				clientSetting.paymentDateDay3.getDatas().get("TS5403"));
		Thread.sleep(2000);
		// save
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Common.isNotPending();

		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		// input pensionFundsAmount_input random value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);

		// check the payGeneralBonus
		int payGeneralBonus = Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText()
				.replace(",", ""));

		int persionFunds_dis = Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.persionFunds_dis.xpath)).getText()
				.replace(",", ""));
		System.out.println(Common.roundHalfDown(String.valueOf(payGeneralBonus),
				(rate + "." + "0000"))
				+ Common.roundHalfDown(String.valueOf(payGeneralBonus), (rate + "." + "0000")));
		if (Common.roundHalfDown(String.valueOf(payGeneralBonus), (rate + "." + "0000")) == persionFunds_dis) {
			System.out.println("id690 Passed");
		} else {
			System.out.println("id690 Failed");
			throw new Exception("Error of calculation, expected: <[" + payGeneralBonus
					* pensionFundsAmount_input / 100 + "]> but was: <[" + persionFunds_dis
					+ "]> , " + "Please refer to input and output values below: " + "\r\n"
					+ "expected = " +payGeneralBonus * pensionFundsAmount_input
					/ 100 + "\r\n" + "actual = " + persionFunds_dis);
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// return to dashboard page and exit system
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}

}