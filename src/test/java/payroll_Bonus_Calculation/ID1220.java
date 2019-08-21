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
 * Display the accumulated total of the general bonus amount to the previous time for each employee
 * during the period from 4/1 to 3/31 including this time.
 **/
public class ID1220 extends TestBase {
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
		Common.createCompanyClient(driver, "25");
		Common.addSimpleEmployeeInfo(driver);
	}

	// If PayrollBonus#paymentDate is 3/31 or before in the selected year
	@Test
	public void id1220_1() throws Exception {
		// number
		int bonusPayment = Math.abs(new Random().nextInt(1000000));

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit pay day
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		// click edit button
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		Common.isNotPending();
		// 1
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("TS586"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(
				clientSetting.paymentDateDay.getDatas().get("TS644_2"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 2
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth2.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("TS586"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay2.xpath)).sendKeys(
				clientSetting.paymentDateDay2.getDatas().get("TS1646"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 3
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth3.xpath)).sendKeys(
				clientSetting.paymentDateMonth3.getDatas().get("TS5403"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay3.xpath)).sendKeys(
				clientSetting.paymentDateDay3.getDatas().get("TS1646"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 4
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth4.xpath)).sendKeys(
				clientSetting.paymentDateMonth4.getDatas().get("TS5403"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay4.xpath)).sendKeys(
				clientSetting.paymentDateDay4.getDatas().get("TS5403"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// click save button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);

		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		Thread.sleep(1000);
		// click tab1
		driver.findElement(By.xpath(bonusPaymentStatements.onetime.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab1_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab1_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();

		// click tab2
		driver.findElement(By.xpath(bonusPaymentStatements.twotime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab2_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab2_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();
		// click tab3
		driver.findElement(By.xpath(bonusPaymentStatements.threetime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab3_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab3_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();
		// click tab4
		driver.findElement(By.xpath(bonusPaymentStatements.fourtime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab4_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab4_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();

		// check the result
		if ("".equals(tab1_id1220) && tab2_id1220.equals(tab1_id1200)) {
			System.out.println("id1220_1 Passed");
		} else {
			System.out.println("id1220_1 Failed");
			System.out.println("tab1_id1200=" + tab1_id1200 + "  tab1_id1220=" + tab2_id1220
					+ "  tab2_id1200=" + tab2_id1200 + "  tab2_id1220=" + tab2_id1220);
			throw new Exception("tab1_id1200=" + tab1_id1200 + "  tab1_id1220=" + tab2_id1220
					+ "  tab2_id1200=" + tab2_id1200 + "  tab2_id1220=" + tab2_id1220);
		}
		if ("".equals(tab3_id1220) && tab4_id1220.equals(tab3_id1200)) {
			System.out.println("id1220_2 Passed");
		} else {
			System.out.println("id1220_2 Failed");
			System.out.println("tab3_id1200=" + tab3_id1200 + "  tab31_id1220=" + tab4_id1220
					+ "  tab4_id1200=" + tab4_id1200 + "  tab4_id1220=" + tab4_id1220);
			throw new Exception("tab3_id1200=" + tab3_id1200 + "  tab31_id1220=" + tab4_id1220
					+ "  tab4_id1200=" + tab4_id1200 + "  tab4_id1220=" + tab4_id1220);
		}

	}

	// If PayrollBonus#paymentDate is 4/1 or later in the selected year
	@Test
	public void id1220_2() throws Exception {
		// number
		int bonusPayment = Math.abs(new Random().nextInt(1000000));

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit pay day
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		// click edit button
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		Common.isNotPending();
		// 1
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("TS586"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(
				clientSetting.paymentDateDay.getDatas().get("TS644_2"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 2
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth2.xpath)).sendKeys(
				clientSetting.paymentDateMonth.getDatas().get("TS586"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay2.xpath)).sendKeys(
				clientSetting.paymentDateDay2.getDatas().get("TS1646"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 3
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth3.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth3.xpath)).sendKeys(
				clientSetting.paymentDateMonth3.getDatas().get("TS5403"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay3.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay3.xpath)).sendKeys(
				clientSetting.paymentDateDay3.getDatas().get("TS1646"));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// 4
		// set month
		driver.findElement(By.xpath(clientSetting.paymentDateMonth4.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth4.xpath)).sendKeys(
				clientSetting.paymentDateMonth4.getDatas().get("TS5403"));
		Common.isNotPending();
		// set day
		driver.findElement(By.xpath(clientSetting.paymentDateDay4.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay4.xpath)).sendKeys(
				clientSetting.paymentDateDay4.getDatas().get("TS5403"));
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// click save button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);

		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		Thread.sleep(1000);
		// click tab1
		driver.findElement(By.xpath(bonusPaymentStatements.onetime.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab1_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab1_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();

		// click tab2
		driver.findElement(By.xpath(bonusPaymentStatements.twotime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab2_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab2_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();
		// click tab3
		driver.findElement(By.xpath(bonusPaymentStatements.threetime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab3_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab3_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();
		// click tab4
		driver.findElement(By.xpath(bonusPaymentStatements.fourtime.xpath)).click();
		// edit
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// input value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(3000);
		String tab4_id1200 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus.xpath)).getText();
		String tab4_id1220 = driver.findElement(
				By.xpath(bonusPaymentStatements.payGeneralBonus_dis.xpath)).getText();

		// check the result
		if ("".equals(tab1_id1220) && tab2_id1220.equals(tab1_id1200)) {
			System.out.println("id1220_1 Passed");
		} else {
			System.out.println("id1220_1 Failed");
			System.out.println("tab1_id1200=" + tab1_id1200 + "  tab1_id1220=" + tab2_id1220
					+ "  tab2_id1200=" + tab2_id1200 + "  tab2_id1220=" + tab2_id1220);
			throw new Exception("tab1_id1200=" + tab1_id1200 + "  tab1_id1220=" + tab2_id1220
					+ "  tab2_id1200=" + tab2_id1200 + "  tab2_id1220=" + tab2_id1220);
		}
		if ("".equals(tab3_id1220) && tab4_id1220.equals(tab3_id1200)) {
			System.out.println("id1220_2 Passed");
		} else {
			System.out.println("id1220_2 Failed");
			System.out.println("tab3_id1200=" + tab3_id1200 + "  tab31_id1220=" + tab4_id1220
					+ "  tab4_id1200=" + tab4_id1200 + "  tab4_id1220=" + tab4_id1220);
			throw new Exception("tab3_id1200=" + tab3_id1200 + "  tab31_id1220=" + tab4_id1220
					+ "  tab4_id1200=" + tab4_id1200 + "  tab4_id1220=" + tab4_id1220);
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