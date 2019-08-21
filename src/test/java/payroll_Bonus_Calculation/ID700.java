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
import org.openqa.selenium.support.ui.Select;

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
 * Display the calculated unemployment insurance amount.
 **/
public class ID700 extends TestBase {
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	public static BonusPaymentStatements bonusPaymentStatements;
	public static int total1;
	public static int total2;
	public static int unemploymentInsuranceRate;

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

	// PayrollCalculationSetting#unemploymentInsuranceType is Employee#unemploymentInsuranceType
	@Test
	public void id700_1_1() throws Exception {
		setAllowance();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
			if (!driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).isEnabled()) {
				break;
			}
		}
		Common.isNotPending();
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		int bonusPayment = new Random().nextInt(100000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Thread.sleep(10000);
		int id700_1 = 0;
		while (true) {
			id700_1 = Integer.parseInt(driver
					.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance2.xpath))
					.getText().replace(",", ""));
			if (!"".equals(driver
					.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance2.xpath))
					.getText().replace(",", ""))) {
				break;
			}
		}
		// check the result
		// int result = (bonusPayment + total2) * unemploymentInsuranceRate / 100;
		// 4/5
		// if (result % 10 <= 4) {
		// result = result / 10;
		// } else {
		// result = result / 10 + 1;
		// }
		System.out.println(id700_1);
		System.out.println(Common.roundHalfDown(String.valueOf((bonusPayment + total2)),
				String.valueOf(unemploymentInsuranceRate + "." + "000")));
		if (id700_1 == Common.roundHalfDown(String.valueOf((bonusPayment + total2)),
				String.valueOf(unemploymentInsuranceRate + "." + "000"))) {
			System.out.println("id700_1_1 Passed");
		} else {
			System.out.println("id700_1_1 Failed");
			throw new Exception(" bonusPayment=" + bonusPayment + " total2=" + total2
					+ " unemploymentInsuranceRate=" + unemploymentInsuranceRate + " id700_1="
					+ id700_1);
		}

	}

	// If Employee#unemploymentInsuranceType is "general"
	@Test
	public void id700_2_1() throws Exception {
		driver.findElement(By.xpath(bonusPaymentStatements.close.xpath)).click();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit Flag
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// switch flag
		driver.findElement(By.xpath(clientSetting.constructionPeriodFlag.xpath)).click();
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		// edit
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
			if (!driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).isEnabled()) {
				break;
			}
		}
		Common.isNotPending();
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		int bonusPayment = new Random().nextInt(100000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Thread.sleep(10000);
		int id700_2 = Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance2.xpath))
				.getText().replace(",", ""));
		// check the result
		// int result = (bonusPayment + total2)
		// * Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "2").replace(".",
		// "")) / 100;
		// 4/5
		// if (result % 10 <= 4) {
		// result = result / 10;
		// } else {
		// result = result / 10 + 1;
		// }
		System.out.println(id700_2);
		System.out.println(Common.roundHalfDown(String.valueOf((bonusPayment + total2)),
				(getCSVData("insuredPersonPaymentProportion", "2") + "." + "000")));
		if (id700_2 == Common.roundHalfDown(String.valueOf((bonusPayment + total2)),
				(getCSVData("insuredPersonPaymentProportion", "2") + "." + "000"))) {
			System.out.println("id700_2_1 Passed");
		} else {
			System.out.println("id700_2_1 Failed");
			throw new Exception(" bonusPayment="
					+ bonusPayment
					+ " total2="
					+ total2
					+ " insuredPersonPaymentProportion="
					+ Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "2").replace(
							".", "")) + " id700_2=" + id700_2);
		}
	}

	// If Employee#unemploymentInsuranceType is "construction"
	@Test
	public void id700_2_2() throws Exception {
		driver.findElement(By.xpath(bonusPaymentStatements.close.xpath)).click();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(employeeSetting.bussnissType_other.xpath)).click();
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Common.isNotPending();

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit Flag
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// switch flag
		driver.findElement(By.xpath(clientSetting.otherPeriodFlag.xpath)).click();
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Common.isNotPending();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
			if (!driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).isEnabled()) {
				break;
			}
		}
		Common.isNotPending();
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		int bonusPayment = new Random().nextInt(100000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Thread.sleep(10000);
		int id700_3 = Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance2.xpath))
				.getText().replace(",", ""));
		// check the result
		// int result = (bonusPayment + total2)
		// * Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "1").replace(".",
		// "")) / 100;
		// // 4/5
		// if (result % 10 <= 4) {
		// result = result / 10;
		// } else {
		// result = result / 10 + 1;
		// }
		if (id700_3 == Common.roundHalfDown(String.valueOf(bonusPayment + total2),
				(getCSVData("insuredPersonPaymentProportion", "1") + "." + "000"))) {
			System.out.println("id700_2_2 Passed");
		} else {
			System.out.println("id700_2_2 Failed");
			throw new Exception(" bonusPayment="
					+ bonusPayment
					+ " total2="
					+ total2
					+ " insuredPersonPaymentProportion="
					+ Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "1").replace(
							".", "")) + " id700_3=" + id700_3);
		}
	}

	// If Employee#unemploymentInsuranceType is "other"
	@Test
	public void id700_2_3() throws Exception {
		driver.findElement(By.xpath(bonusPaymentStatements.close.xpath)).click();
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		// forward to 0511
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		Common.isNotPending();
		Thread.sleep(1000);
		driver.findElement(By.xpath(employeeSetting.bussnissType_other.xpath)).click();
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Common.isNotPending();

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit Flag
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// switch flag
		driver.findElement(By.xpath(clientSetting.specialPaymentPeriodFlag.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		}
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		// forward to 0501
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
			if (!driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).isEnabled()) {
				break;
			}
		}
		Common.isNotPending();
		Thread.sleep(1000);
		// input pensionFundApplying_old_insuredPersonPaymentProportion random
		// value
		int bonusPayment = new Random().nextInt(100000);
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusPayment.xpath)).sendKeys(
				String.valueOf(bonusPayment));
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Thread.sleep(10000);
		int id700_4 = Integer.parseInt(driver
				.findElement(By.xpath(bonusPaymentStatements.unemploymentInsurance2.xpath))
				.getText().replace(",", ""));
		// check the result
		int result = (bonusPayment + total2)
				* Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "0").replace(".",
						"")) / 100;
		// 4/5
		if (result % 10 <= 4) {
			result = result / 10;
		} else {
			result = result / 10 + 1;
		}
		if (id700_4 == result) {
			System.out.println("id700_2_3 Passed");
		} else {
			System.out.println("id700_2_3 Failed");
			throw new Exception(" bonusPayment="
					+ bonusPayment
					+ " total2="
					+ total2
					+ " insuredPersonPaymentProportion="
					+ Integer.parseInt(getCSVData("insuredPersonPaymentProportion", "0").replace(
							".", "")) + " id700_4=" + id700_4);
		}
	}

	public void setAllowance() throws Exception {
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

		total1 = allowanceValue1 + allowanceValue2 + allowanceValue3 + allowanceValue4
				+ allowanceValue5 + allowanceValue6 + allowanceValue7 + allowanceValue8
				+ allowanceValue9 + allowanceValue10 + allowanceValue11;
		total2 = allowanceValue1 + allowanceValue2 + allowanceValue3 + allowanceValue4
				+ allowanceValue5 + allowanceValue6 + allowanceValue7 + allowanceValue8;

		// forward to 0510
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		Thread.sleep(2000);
		// edit Flag
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// switch flag
		driver.findElement(By.xpath(clientSetting.specialPaymentPeriodFlag.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		// driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
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
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.perfectureType.xpath)).click();
		// choose zone
		Select select = new Select(driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		driver.findElement(By.xpath(clientSetting.perfectureType.xpath)).click();
		Thread.sleep(1000);
		select.selectByIndex(4);
		Thread.sleep(2000);
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Common.isNotPending();
		for (int i = 0; i < 10; i++) {
			unemploymentInsuranceRate = Integer.parseInt(driver
					.findElement(By.xpath(clientSetting.insuredPersonPaymentProportion.xpath))
					.getText().replace(".", "")) / 10000;
		}
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
		Thread.sleep(1000);
		// click edit button
		driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
		Thread.sleep(1000);
		// input 11 items
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName.getDatas().get("TS1812"));
		// 2
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName2.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName2.getDatas().get("TS1813"));
		// 3
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName3.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName3.getDatas().get("TS1815"));
		// 4
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName4.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName4.getDatas().get("TS1817"));
		// 5
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName5.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName5.getDatas().get("TS1818"));
		// 6
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName6.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName6.getDatas().get("TS1820"));
		// 7
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName7.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName7.getDatas().get("TS1821"));
		// 8
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName8.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName8.getDatas().get("TS1822"));
		// 9
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName9.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName9.getDatas().get("TS1823"));
		if (!driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit9.xpath))
				.isSelected()) {
			driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit9.xpath)).click();
		}
		// 10
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName10.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName10.getDatas().get("TS1824"));
		if (!driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit10.xpath))
				.isSelected()) {
			driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit10.xpath)).click();
		}

		// 11
		driver.findElement(By.xpath(bonusPaymentStatements.allowanceName11.xpath)).sendKeys(
				bonusPaymentStatements.allowanceName11.getDatas().get("TS1825"));
		if (!driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit11.xpath))
				.isSelected()) {
			driver.findElement(By.xpath(bonusPaymentStatements.unemployeeBenefit11.xpath)).click();
		}

		Common.isNotPending();
		Thread.sleep(2000);
		// save
		driver.findElement(By.xpath(bonusPaymentStatements.save.xpath)).click();
		Common.isNotPending();
		driver.findElement(By.xpath(bonusPaymentStatements.bonusSlipPage.xpath)).click();
		// edit
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).click();
			if (!driver.findElement(By.xpath(bonusPaymentStatements.edit.xpath)).isEnabled()) {
				break;
			}
		}
		Common.isNotPending();
		Thread.sleep(1000);
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
	}

	// getCSVData
	public static String getCSVData(String key, String value) throws Exception {
		File file = new File(".");
		String filePath = file.getCanonicalPath() + "\\resources\\pages\\testdata\\"
				+ "2013UnemploymentInsurance.csv";
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				filePath), "Shift-JIS"));
		String line = null;
		// get max account of one line
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for (int i = 0; i < item.length; i++) {
			if (item[i].equals("\"" + key + "\"")) {
				num = i;
			}
		}

		String result = "";
		for (int i = 0; i < 4; i++) {
			line = reader.readLine();
		}
		while ((line) != null) {
			if (line.split(",")[2].equals(value)) {
				result = line.split(",")[num];
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