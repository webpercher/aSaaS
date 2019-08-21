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
 * Display the general bonus amount for each employee at this time.
 **/
public class ID1200 extends TestBase {
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

	// If #id1220 >= 5400000
	@Test
	public void id1200_1() throws Exception {
		setAllowance();
		Thread.sleep(1000);
		// input id1220
		int id1220 = 5499999;
		int id390 = new Random().nextInt(1000000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(id390));
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).sendKeys(
				String.valueOf(id1220));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Thread.sleep(10000);
		// check the result
		if ("".equals(driver.findElement(
				By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath)).getText())) {
			System.out.println("id1200_1 Passed");
		} else {
			System.out.println("id1200_1 Failed");
			throw new Exception("Error of calculation, expected: <["
					+ ""
					+ "]> but was: <["
					+ driver.findElement(
							By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
							.getText()
					+ "]> , "
					+ "Please refer to input and output values below: "
					+ "\r\n"
					+ "expected = "
					+ "0"
					+ "\r\n"
					+ "actual = "
					+ driver.findElement(
							By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
							.getText());
		}
	}

	// If #id1220 < 5400000
	// If Z <= 5400000
	@Test
	public void id1200_2_1() throws Exception {
		setAllowance();
		Thread.sleep(1000);
		// input id1220
		int id1220 = 545555;
		int id390 = new Random().nextInt(1000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(id390));
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).sendKeys(
				String.valueOf(id1220));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(10000);
		// check the result
		if (Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
				.getText().replace(",", "")) == ((id390 + total) / 1000) * 1000) {
			System.out.println("id1200_2_1 Passed");
		} else {
			System.out.println("id1200_2_1 Failed");
			System.out.println(id1220);
			System.out.println(id390);
			System.out.println(total);
			System.out.println(driver.findElement(
					By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath)).getText());
			throw new Exception(
					"Error of calculation, expected: <["
							+ ((id390 + total) / 1000 * 1000)
							+ "]> but was: <["
							+ Integer.parseInt(driver
									.findElement(
											By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
									.getText().replace(",", ""))
							+ "]> , "
							+ "Please refer to input and output values below: "
							+ "\r\n"
							+ "expected = "
							+ ((id390 + total) / 1000 * 1000)
							+ "\r\n"
							+ "actual = "
							+ Integer.parseInt(driver
									.findElement(
											By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
									.getText().replace(",", "")));
		}
	}

	// If #id1220 < 5400000
	// If Z > 5400000
	@Test
	public void id1200_2_2() throws Exception {
		setAllowance();
		Thread.sleep(1000);
		// input id1220
		int id1220 = 5399990;
		int id390 = new Random().nextInt(1000000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(id390));
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.payGeneralBonus_input.xpath)).sendKeys(
				String.valueOf(id1220));
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(10000);
		// check the result
		if (Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
				.getText().replace(",", "")) == (5400000 - id1220)) {
			System.out.println("id1200_2_2 Passed");
		} else {
			System.out.println("id1200_2_2 Failed");
			System.out.println(id1220);
			System.out.println(id390);
			System.out.println(total);
			System.out.println(driver.findElement(
					By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath)).getText());
			throw new Exception(
					"Error of calculation, expected: <["
							+ (5400000 - id1220)
							+ "]> but was: <["
							+ Integer.parseInt(driver
									.findElement(
											By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
									.getText().replace(",", ""))
							+ "]> , "
							+ "Please refer to input and output values below: "
							+ "\r\n"
							+ "expected = "
							+ (5400000 - id1220)
							+ "\r\n"
							+ "actual = "
							+ Integer.parseInt(driver
									.findElement(
											By.xpath(bonusPaymentStatements.generalBonusAmountThisTime.xpath))
									.getText().replace(",", "")));
		}
	}

	// set id410~610
	public static void setAllowance() throws Exception {
		// random 11 itmes
		int allowanceValue1 = new Random().nextInt(100000);
		int allowanceValue2 = new Random().nextInt(100000);
		int allowanceValue3 = new Random().nextInt(100000);
		int allowanceValue4 = new Random().nextInt(100000);
		int allowanceValue5 = new Random().nextInt(100000);
		int allowanceValue6 = new Random().nextInt(100000);
		int allowanceValue7 = new Random().nextInt(100000);
		int allowanceValue8 = new Random().nextInt(100000);
		int allowanceValue9 = new Random().nextInt(100000);
		int allowanceValue10 = new Random().nextInt(100000);
		int allowanceValue11 = new Random().nextInt(100000);

		total = allowanceValue1 + allowanceValue2 + allowanceValue3 + allowanceValue4
				+ allowanceValue5 + allowanceValue6 + allowanceValue7 + allowanceValue8
				+ allowanceValue9 + allowanceValue10 + allowanceValue11;

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
		Thread.sleep(3000);
		// edit pay day
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		// click edit button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
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
		// click set button
		driver.findElement(By.xpath(bonusPaymentStatements.bonusAllowanceDeductionSetting.xpath))
				.click();
		Common.isNotPending();
		Thread.sleep(2000);
		// click edit button
		for(int i = 0; i < 10; i ++){
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		// input 11 items
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName.getDatas().get("TS1812"));
		// 2
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName2.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName2.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName2.getDatas().get("TS1813"));
		// 3
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName3.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName3.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName3.getDatas().get("TS1815"));
		// 4
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName4.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName4.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName4.getDatas().get("TS1817"));
		// 5
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName5.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName5.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName5.getDatas().get("TS1818"));
		// 6
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName6.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName6.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName6.getDatas().get("TS1820"));
		// 7
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName7.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName7.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName7.getDatas().get("TS1821"));
		// 8
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName8.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName8.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName8.getDatas().get("TS1822"));
		// 9
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName9.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName9.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName9.getDatas().get("TS1823"));
		// 10
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName10.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName10.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName10.getDatas().get("TS1824"));
		// 11
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName11.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName11.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName11.getDatas().get("TS1825"));
		Common.isNotPending();
		Thread.sleep(2000);
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		// if (driver.findElement(By.xpath(bonusPaymentStatements.definition.xpath)).isDisplayed())
		// {
		// driver.findElement(By.xpath(bonusPaymentStatements.definition.xpath)).click();
		// Common.isNotPending();
		// Thread.sleep(2000);
		// }
		// click bonusSlipPage
		driver.findElement(By.xpath(bonusPaymentStatements.bonusSlipPage.xpath)).click();
		// edit
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		}
		Common.isNotPending();
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
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
		// input 11 items
		// 1
		driver.findElement(By.xpath(bonusPaymentStatements.deduction.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction.xpath)).sendKeys(
				String.valueOf(allowanceValue1));
		// 2
		driver.findElement(By.xpath(bonusPaymentStatements.deduction2.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction2.xpath)).sendKeys(
				String.valueOf(allowanceValue2));
		// 3
		driver.findElement(By.xpath(bonusPaymentStatements.deduction3.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction3.xpath)).sendKeys(
				String.valueOf(allowanceValue3));
		// 4
		driver.findElement(By.xpath(bonusPaymentStatements.deduction4.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction4.xpath)).sendKeys(
				String.valueOf(allowanceValue4));
		// 5
		driver.findElement(By.xpath(bonusPaymentStatements.deduction5.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction5.xpath)).sendKeys(
				String.valueOf(allowanceValue5));
		// 6
		driver.findElement(By.xpath(bonusPaymentStatements.deduction6.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction6.xpath)).sendKeys(
				String.valueOf(allowanceValue6));
		// 7
		driver.findElement(By.xpath(bonusPaymentStatements.deduction7.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction7.xpath)).sendKeys(
				String.valueOf(allowanceValue7));
		// 8
		driver.findElement(By.xpath(bonusPaymentStatements.deduction8.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction8.xpath)).sendKeys(
				String.valueOf(allowanceValue8));
		// 9
		driver.findElement(By.xpath(bonusPaymentStatements.deduction9.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction9.xpath)).sendKeys(
				String.valueOf(allowanceValue9));
		// 10
		driver.findElement(By.xpath(bonusPaymentStatements.deduction10.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction10.xpath)).sendKeys(
				String.valueOf(allowanceValue10));
		// 11
		driver.findElement(By.xpath(bonusPaymentStatements.deduction11.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.deduction11.xpath)).sendKeys(
				String.valueOf(allowanceValue11));
		Thread.sleep(2000);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// return to dashboard page and exit system
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}

}