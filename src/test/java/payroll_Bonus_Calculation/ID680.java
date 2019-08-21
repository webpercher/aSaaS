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
 * Display the calculated social security amount.
 **/
public class ID680 extends TestBase {
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
		// click pensionFundApplying_old_insuredPersonPaymentProportion-SaaS
		// menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		// clean env
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		// precondition
		Common.createCompanyClient(driver, "25");
		Common.addSimpleEmployeeInfo(driver);
	}

	/**
	 * If Employee#fixedAmountSocialSecurityFlag is false r = #insuredPersonPaymentProportion which
	 * ClientPensionInsurance#insuranceType='socialSecurity' and ClientPensionInsurance#startDate <=
	 * {PayrollBonus#paymentDate for the selected time}, and also #endDate is null or #endDate >=
	 * {PayrollBonus#paymentDate for the selected time} R = #id1210 × r #id680= R rounded to the
	 * nearest integer according to
	 * PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
	 **/
	@Test
	public void id680() throws Exception {
		// get somerandom numbers
		int pensionFundApplying_old_insuredPersonPaymentProportion = Math.abs(new Random()
				.nextInt(100));
		int pensionFundApplying_new_insuredPersonPaymentProportion = Math.abs(new Random()
				.nextInt(100));
		String rate = String.valueOf(pensionFundApplying_old_insuredPersonPaymentProportion);
		String rate2 = String.valueOf(pensionFundApplying_new_insuredPersonPaymentProportion);
		// get pensionFundApplying_old_insuredPersonPaymentProportion random
		// number
		int bonusPayment = Math.abs(new Random().nextInt(1000000));

		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		// edit and let the client has joined social insurance
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
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
		Thread.sleep(2000);
		// click ratesetting
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		// edit the rate
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		Common.isNotPending();
		// input the random rate
		driver.findElement(
				By.xpath(clientSetting.pensionFundApplying_old_insuredPersonPaymentProportion.xpath))
				.clear();
		driver.findElement(
				By.xpath(clientSetting.pensionFundApplying_old_insuredPersonPaymentProportion.xpath))
				.sendKeys(rate);
		Thread.sleep(1000);
		// click another tab
		driver.findElement(By.xpath(clientSetting.pensionFundApplying_new.xpath)).click();
		// input the random rate
		driver.findElement(
				By.xpath(clientSetting.pensionFundApplying_new_insuredPersonPaymentProportion.xpath))
				.clear();
		driver.findElement(
				By.xpath(clientSetting.pensionFundApplying_new_insuredPersonPaymentProportion.xpath))
				.sendKeys(rate2);
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
		Thread.sleep(1000);
		// edit
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		Common.isNotPending();
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);

		// check the result
		// 24.9.1~25.9.1
		if (Integer.parseInt(clientSetting.currentYear.getDatas().get("TS11346")) == 24
				|| ((Integer.parseInt(clientSetting.currentYear.getDatas().get("TS11346")) == 25 && Integer
						.parseInt(clientSetting.paymentDateDay.getDatas().get("TS11266")) < 9))) {
			int result = Integer.parseInt(driver
					.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText()
					.replace(",", ""));
			int result2 = Integer.parseInt(driver
					.findElement(By.xpath(bonusPaymentStatements.foundAmount_dis.xpath)).getText()
					.replace(",", ""));

			System.out.println("result=" + result);
			System.out.println("pensionFundApplying_old_insuredPersonPaymentProportion="
					+ pensionFundApplying_old_insuredPersonPaymentProportion);
			System.out.println("pensionFundApplying_new_insuredPersonPaymentProportion="
					+ pensionFundApplying_new_insuredPersonPaymentProportion);
			System.out.println("result * pensionFundApplying_old_insuredPersonPaymentProportion ="
					+ (result * pensionFundApplying_old_insuredPersonPaymentProportion / 100));
			System.out.println("result2=" + result2);
			System.out.println("Common.roundHalfDown(String.valueOf(result), (rate + \".\" + \"0000\")    "
					+ Common.roundHalfDown(String.valueOf(result), (rate + "." + "0000")));
			// (result * pensionFundApplying_old_insuredPersonPaymentProportion / 100)
			if ((Common.roundHalfDown(String.valueOf(result), (rate + "." + "0000")) == result2)) {
				System.out.println("id680_1 Passed");
			} else {
				System.out.println("id680_1 Failed");
				throw new Exception("Error of calculation, expected: <[" + result
						* Integer.parseInt(rate) / 100 + "]> but was: <[" + result2 + "]> , "
						+ "Please refer to input and output values below: " + "\r\n"
						+ "expected = " + result * Integer.parseInt(rate) / 100 + "\r\n"
						+ "actual = " + result2);
			}
		} else {// 25.9.1~~
			int result = Integer.parseInt(driver
					.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText()
					.replace(",", ""));
			int result2 = Integer.parseInt(driver
					.findElement(By.xpath(bonusPaymentStatements.foundAmount_dis.xpath)).getText()
					.replace(",", ""));

			System.out.println("result=" + result);
			System.out.println("pensionFundApplying_old_insuredPersonPaymentProportion="
					+ pensionFundApplying_old_insuredPersonPaymentProportion);
			System.out.println("pensionFundApplying_new_insuredPersonPaymentProportion="
					+ pensionFundApplying_new_insuredPersonPaymentProportion);
			System.out.println("result * pensionFundApplying_old_insuredPersonPaymentProportion ="
					+ (result * pensionFundApplying_new_insuredPersonPaymentProportion / 100));
			System.out.println("result2=" + result2);
			System.out.println("Common.roundHalfDown(String.valueOf(result), (rate2 + \".\" + \"0000\")    "
					+ Common.roundHalfDown(String.valueOf(result), (rate2 + "." + "0000")));
			
			if ((Common.roundHalfDown(String.valueOf(result), (rate2 + "." + "0000")) == result2)) {
				System.out.println("id680_2 Passed");
			} else {
				System.out.println("id680_2 Failed");
				throw new Exception("Error of calculation, expected: <[" + result
						* Integer.parseInt(rate) / 100 + "]> but was: <[" + result2 + "]> , "
						+ "Please refer to input and output values below: " + "\r\n"
						+ "expected = " + result * Integer.parseInt(rate) / 100 + "\r\n"
						+ "actual = " + result2);
			}
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