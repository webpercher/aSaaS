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
 * Display the calculated total amount of each social insurance.
 **/
public class ID710 extends TestBase {
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
	 * #id710 = #id660(health insurance amount) + #id670(nursing insurance amount) + #id680(social
	 * security amount) + #id690(pension funds amount) + #id700(unemployment insurance
	 * amount)+#id890(adjustment amount of social insurance)
	 */
	@Test
	public void id710() throws Exception {
		// get somerandom numbers
		int socialInsurance = Math.abs(new Random().nextInt(100000));
		int nursingInsrance = Math.abs(new Random().nextInt(100000));
		int socialSecurity = Math.abs(new Random().nextInt(100000));
		int persionFunds = Math.abs(new Random().nextInt(100000));
		int healthInsrance = Math.abs(new Random().nextInt(100000));
		int unemploymentInsurance = Math.abs(new Random().nextInt(100000));

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
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
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
		Thread.sleep(2000);
		// edit
		for(int i = 0; i < 10; i++){
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		// input ID660 ID670 ID680 ID690 ID890 ID700
		driver.findElement(By.xpath(bonusPaymentStatements.healthInsrance.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.healthInsrance.xpath)).sendKeys(
				String.valueOf(healthInsrance));
		// id670
		driver.findElement(By.xpath(bonusPaymentStatements.nursingInsrance.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.nursingInsrance.xpath)).sendKeys(
				String.valueOf(nursingInsrance));
		// id680
		driver.findElement(By.xpath(bonusPaymentStatements.socialSecurity.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.socialSecurity.xpath)).sendKeys(
				String.valueOf(socialSecurity));
		// id690
		driver.findElement(By.xpath(bonusPaymentStatements.persionFunds.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.persionFunds.xpath)).sendKeys(
				String.valueOf(persionFunds));
		// id890
		driver.findElement(By.xpath(bonusPaymentStatements.socialInsurance.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.socialInsurance.xpath)).sendKeys(
				String.valueOf(socialInsurance));
		// id700
		driver.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance.xpath)).sendKeys(
				String.valueOf(unemploymentInsurance));
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);

		// check the result
		if (Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.socialInsranceSum2.xpath)).getText()
				.replace(",", "")) == (socialInsurance + nursingInsrance + socialSecurity
				+ persionFunds + healthInsrance + unemploymentInsurance)) {
			System.out.println("id710 Passed");
		} else {
			System.out.println("id710 Failed");
			System.out.println(healthInsrance);
			System.out.println(socialInsurance);
			System.out.println(nursingInsrance);
			System.out.println(socialSecurity);
			System.out.println(persionFunds);
			System.out.println(unemploymentInsurance);
			System.out
					.println("socialInsurance  nursingInsrance  socialSecurity  persionFunds healthInsrance = "
							+ (socialInsurance + nursingInsrance + socialSecurity + persionFunds
									+ healthInsrance + unemploymentInsurance));
			throw new Exception("Error of calculation, expected: <["
					+ (socialInsurance + nursingInsrance + socialSecurity + persionFunds
							+ healthInsrance + unemploymentInsurance)
					+ "]> but was: <["
					+ Integer.parseInt(driver
							.findElement(By.xpath(bonusPaymentStatements.socialInsranceSum2.xpath))
							.getText().replace(",", ""))
					+ "]> , "
					+ "Please refer to input and output values below: "
					+ "\r\n"
					+ "expected = "
					+ (socialInsurance + nursingInsrance + socialSecurity + persionFunds
							+ healthInsrance + unemploymentInsurance)
					+ "\r\n"
					+ "actual = "
					+ Integer.parseInt(driver
							.findElement(By.xpath(bonusPaymentStatements.socialInsranceSum2.xpath))
							.getText().replace(",", "")));

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