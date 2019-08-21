package payroll_Bonus_Calculation;

import java.text.DecimalFormat;

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
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
/**
 * Display the sum of health insurnace amount and nursing insurance amount.
 */
public class ID670 extends TestBase {
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static DashboardPage dashboard;
	private static EmployeeSettingPage employeeSetting;
	private static BonusPaymentStatements bonusPayment;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		bonusPayment = (BonusPaymentStatements) helper.getPage("BonusPaymentStatements");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		Thread.sleep(4000);
		//clean environment
		Common.removeCompany(driver, clientSetting.companyCode.getDatas().get("common"));
		//create company
		Common.createCompanyClient(driver, clientSetting.currentYear.getDatas().get("TS11265"));
		//select joinedSocialInsuranceFlag is true
		ID660.joinedSocialInsuranceFlag(driver, clientSetting, topPage);
		//add employees
		Common.addSimpleEmployeeInfo(driver);
		//create precondition
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		ID660.selectInsuredHealthInsurance(driver, employeeSetting);
		selectnursingInsured(driver, employeeSetting);
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting);
		Common.isNotPending();
	}
	/**
	 * If ClientSocialInsurance#healthInsuranceType is 'kyokai'
	 */
	@Test
	public void id670_1() throws Exception {
		// Set conditions
		ID660.selectHealthInsurance(driver, clientSetting, clientSetting.healthInsuranceType1.xpath);
		String p = ID660.getP(driver, clientSetting);
		String q = getQ(driver, clientSetting);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.onetime.xpath)));
		//click edit
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		//input the value
		int id390 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).getAttribute("value").equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		//get the value
		String id1200Str = driver.findElement(By.xpath(bonusPayment.generalBonusAmountThisTime.xpath)).getText();
		String id1200 = id1200Str.replace(",", "");
		//Q10 = #id1200 × (q + p)
		//	Q11 = Q10 rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		String rate = String.valueOf(Integer.parseInt(p.replace(".", "")) + Integer.parseInt(q.replace(".", "")));
		int Q11 = Common.roundHalfDown(id1200, rate);
		//	Q20 = #id1200 × p
		//	Q21 = Q20 rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		int Q21 = Common.roundHalfDown(id1200, p);
		//	#id670 = Q11 - Q21
		int id670 = Q11 - Q21;
		String id670Str = driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id670)).equals(id670Str)) {
			System.out.println("ID670_1 Pass");
		}else {
			System.out.println("ID670_1 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id1200 = " + id1200 + "\n" + "p = " + p + "\n" + "q = " + q);
			System.out.println("Theoretical value = " + id670 + "\n" + "Actual value = " + id670Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id670))+"]> but was: <["+id670Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id670))+"\r\n"+
					"actual = "+ id670Str);
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
	}
	/**
	 * If ClientSocialInsurance#healthInsuranceType is 'union'
	 */
	@Test
	public void id670_2() throws Exception {
		// Set conditions
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)));
		Common.isNotPending();
		ID660.selectHealthInsurance(driver, clientSetting, clientSetting.healthInsuranceType.xpath);
		String p = ID660.setP(driver, clientSetting);
		String q = setQ(driver, clientSetting);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		//click edit
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		//input the value
		int id390 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).getAttribute("value").equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		// get the value
		String id1200Str = driver.findElement(By.xpath(bonusPayment.generalBonusAmountThisTime.xpath)).getText();
		String id1200 = id1200Str.replace(",", "");
		//Q10 = #id1200 × (q + p)
		//	Q11 = Q10 rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		String rate = String.valueOf(Integer.parseInt(p.replace(".", "")) + Integer.parseInt(q.replace(".", "")));
		int Q11 = Common.roundHalfDown(id1200, rate);
		//	Q20 = #id1200 × p
		//	Q21 = Q20 rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		int Q21 = Common.roundHalfDown(id1200, p);
		// #id670 = Q11 - Q21
		int id670 = Q11 - Q21;
		String id670Str = driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).getAttribute("value");
		if (Common.formatNum(String.valueOf(id670)).equals(id670Str)) {
			System.out.println("ID670_2 Pass");
		}else {
			System.out.println("ID670_2 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id1200 = " + id1200 + "\n" + "p = " + p + "\n" + "q = " + q);
			System.out.println("Theoretical value = " + id670 + "\n" + "Actual value = " + id670Str);
			throw new Exception("Error of calculation, expected: <["+ Common.formatNum(String.valueOf(id670))+ "]> but was: <[" + id670Str + "]> , "
					+ "Please refer to input and output values below: "+ "\r\n" + "expected = "+ Common.formatNum(String.valueOf(id670)) + "\r\n"
					+ "actual = " + id670Str);
		}
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
	
	public static String setQ(WebDriver driver, ClientSettingPage clientSetting) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.careInsStartApplying.xpath)));
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(1000);
		//get 4 decimal places
		DecimalFormat df = new DecimalFormat("#.0000");
		driver.findElement(By.xpath(clientSetting.careNewPersonDependency.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.careNewPersonDependency.xpath)).sendKeys(df.format(Math.random() * 10));
		Thread.sleep(1000);
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		}
		Thread.sleep(2000);

		return driver.findElement(By.xpath(clientSetting.careNewPersonDependency_dis.xpath)).getText();
	}
		
	public static String getQ(WebDriver driver, ClientSettingPage clientSetting) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.careInsStartApplying.xpath)));
		driver.findElement(By.xpath(clientSetting.careInsStartApplying.xpath)).click();
		Thread.sleep(2000);
		return driver.findElement(By.xpath(clientSetting.careNewPersonDependency_dis.xpath)).getText();
	}
	
	public static void selectnursingInsured(WebDriver driver, EmployeeSettingPage employeeSetting) throws Exception {
		// create nursingInsuredPersonFlag
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(employeeSetting.employeeCode_disp.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(1000);
		for(int i = 0; i < 5; i++) {
			if(! driver.findElement(By.xpath(employeeSetting.between40_65.xpath)).isSelected()) {
				driver.findElement(By.xpath(employeeSetting.between40_65.xpath)).click();
				Thread.sleep(3000);
			}
		}
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(employeeSetting.between40_65.xpath),true));
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Common.isNotPending();
	}

}
