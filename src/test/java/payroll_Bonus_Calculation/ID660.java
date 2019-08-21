package payroll_Bonus_Calculation;

import java.text.DecimalFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.BonusPaymentStatements;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;
import com.google.common.base.Function;
/**
 * Display the calculated health insurance amount.
 */
public class ID660 extends TestBase {
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
		joinedSocialInsuranceFlag(driver, clientSetting, topPage);
		//add employees
		Common.addSimpleEmployeeInfo(driver);
		//create precondition
		Common.forwardToPage(topPage.projectID.getDatas().get("employeesettingpage"));
		selectInsuredHealthInsurance(driver, employeeSetting);
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		//create pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		Common.isNotPending();
		ID650.addPayDate(driver, clientSetting);
	}
	/**
	 * If ClientSocialInsurance#healthInsuranceType is 'kyokai'
	 */
	@Test
	public void id660_1() throws Exception {
		// Set conditions
		Common.isNotPending();
		selectHealthInsurance(driver, clientSetting ,clientSetting.healthInsuranceType1.xpath);
		// p = #insuredPersonPaymentProportion which ClientHealthInsurance#insuranceType='unionHealth' 
		// and ClientHealthInsurance#startDate <= PayrollBonus#paymentDate for the selected time,
		// and also #endDate is null or #endDate >= PayrollBonus#paymentDate for the selected time
		String p = getP(driver, clientSetting);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		// input the value
		int id390 = (int) (Math.random()*10000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).getAttribute("value").equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).click();
				Thread.sleep(1000);
			}else{
				break;
			}
		}
		String id1200Str = driver.findElement(By.xpath(bonusPayment.generalBonusAmountThisTime.xpath)).getText();
		Thread.sleep(1000);
		String id1200 = id1200Str.replace(",", "");
		//	P = #id1200 × p
		// #id660= P rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		int id660 = Common.roundHalfDown(id1200, p);
		String id660Str = driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id660)).equals(id660Str)) {
			System.out.println("ID660_1 Pass");
		}else {
			System.out.println("ID660_1 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id1200 = " + id1200 + "\n" + "p = " + p);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id660))+"]> but was: <["+id660Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id660))+"\r\n"+
					"actual = "+ id660Str);
		}
		driver.findElement(By.xpath(bonusPayment.close.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.dateSettingNoButton.xpath)).click();
		Thread.sleep(5000);
	}
	/**
	 * If ClientSocialInsurance#healthInsuranceType is 'union'
	 */
	@Test
	public void id660_2() throws Exception {
		// Set conditions
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)));
		Common.isNotPending();
		selectHealthInsurance(driver, clientSetting ,clientSetting.healthInsuranceType.xpath);
		// p = #insuredPersonPaymentProportion which ClientHealthInsurance#insuranceType='unionHealth' 
		// and ClientHealthInsurance#startDate <= PayrollBonus#paymentDate for the selected time,
		// and also #endDate is null or #endDate >= PayrollBonus#paymentDate for the selected time
		String p = setP(driver, clientSetting);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(1000);
		// input the value
		int id390 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).getAttribute("value").equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.nursingInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id1200Str = driver.findElement(By.xpath(bonusPayment.generalBonusAmountThisTime.xpath)).getText();
		String id1200 = id1200Str.replace(",", "");
		//	P = #id1200 × p
		// #id660= P rounded to the nearest integer according to PayrollCalculationSetting#healthPensionInsuranceCalcRoundingMethod (*1)
		int id660 = Common.roundHalfDown(id1200, p);
		String id660Str = driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).getAttribute("value");
		if(Common.formatNum(String.valueOf(id660)).equals(id660Str)) {
			System.out.println("ID660_2 Pass");
		}else {
			System.out.println("ID660_2 Failed");
			System.out.println("id390 = " + id390 + "\n" + "id1200 = " + id1200 + "\n" + "p = " + p);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id660))+"]> but was: <["+id660Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id660))+"\r\n"+
					"actual = "+ id660Str);
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
	
	public static void selectHealthInsurance(WebDriver driver, ClientSettingPage clientSetting, String xpath) throws Exception {
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		//click edit
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.healthInsuranceType1.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(1000);
		if(! driver.findElement(By.xpath(xpath)).isSelected()) {
			driver.findElement(By.xpath(xpath)).click();
		}
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeSelected(By.xpath(xpath)));
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
	}
	
	public static String setP(WebDriver driver, ClientSettingPage clientSetting) throws Exception {
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.careInsStartApplying.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		}
		Thread.sleep(2000);
		//input value
		driver.findElement(By.xpath(clientSetting.healthInsOldAmountMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.healthInsOldAmountMonth.xpath)).sendKeys(clientSetting.healthInsOldAmountMonth.getDatas().get("TS462"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.healthInsOldAmountDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.healthInsOldAmountDay.xpath)).sendKeys(clientSetting.healthInsOldAmountDay.getDatas().get("TS462"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.healthInsStartApplying.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.healthInsNewAmountMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.healthInsNewAmountMonth.xpath)).sendKeys(clientSetting.healthInsNewAmountMonth.getDatas().get("TS463"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.healthInsNewAmountDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.healthInsNewAmountDay.xpath)).sendKeys(clientSetting.healthInsNewAmountDay.getDatas().get("TS463"));
		Thread.sleep(1000);
		//get 4 decimal places
		DecimalFormat df = new DecimalFormat("#.0000");
		driver.findElement(By.xpath(clientSetting.insuredPersonPaymentPr2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.insuredPersonPaymentPr2.xpath)).sendKeys(df.format(Math.random() * 10));
		driver.findElement(By.xpath(clientSetting.careInsranceOldAmountMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.careInsranceOldAmountMonth.xpath)).sendKeys(clientSetting.careInsranceOldAmountMonth.getDatas().get("TS462"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.careInsranceOldAmountDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.careInsranceOldAmountDay.xpath)).sendKeys(clientSetting.careInsranceOldAmountDay.getDatas().get("TS462"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.careInsStartApplying.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.careInsranceNewAmountMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.careInsranceNewAmountMonth.xpath)).sendKeys(clientSetting.careInsranceNewAmountMonth.getDatas().get("TS475"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.careInsranceNewAmountDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.careInsranceNewAmountDay.xpath)).sendKeys(clientSetting.careInsranceNewAmountDay.getDatas().get("TS475"));
		Thread.sleep(2000);
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		}
		Thread.sleep(1000);

		return driver.findElement(By.xpath(clientSetting.insuredPersonPaymentPr2_dis.xpath)).getText();
	}
	
	public static String getP(WebDriver driver, final ClientSettingPage clientSetting) throws Exception {
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Common.isNotPending();
		//click edit
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.careInsStartApplying.xpath)));
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(1000);
		//get select
		Select select = new Select(driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		select.selectByIndex((int)(Math.random()*47)+1);
		Thread.sleep(1000);
		if(Common.isElementPresent(driver, By.xpath(clientSetting.definition.xpath), 2)) {
			driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
			Thread.sleep(1000);
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.healthInsStartApplying.xpath)).click();
		Thread.sleep(2000);
		for(int i = 0; i < 5; i++) {
			driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		}
		Common.isNotPending();
		//get insuredPersonPaymentPr2 text
		new WebDriverWait(driver, 30).until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ! driver.findElement(By.xpath(clientSetting.insuredPersonPaymentPr2_dis.xpath)).getText().equals("0.0000");
			  }
			});
		return driver.findElement(By.xpath(clientSetting.insuredPersonPaymentPr2_dis.xpath)).getText();
	}
	
	public static void selectInsuredHealthInsurance(WebDriver driver, EmployeeSettingPage employeeSetting) throws Exception {
		Thread.sleep(2000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(employeeSetting.employeeCode_disp.xpath)));
		Common.isNotPending();
		//click edit
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(1000);
		for(int i = 0; i < 5; i++) {
			if(! driver.findElement(By.xpath(employeeSetting.insured.xpath)).isSelected()) {
				driver.findElement(By.xpath(employeeSetting.insured.xpath)).click();
				Thread.sleep(3000);
			}
		}
		if(driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).isSelected()) {
			driver.findElement(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath)).click();
			Thread.sleep(2000);
		}
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(employeeSetting.insured.xpath),true));
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(employeeSetting.fixedAmountHealthInsurance.xpath),false));
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Common.isNotPending();
	}
	
	public static void joinedSocialInsuranceFlag(WebDriver driver, ClientSettingPage clientSetting, TopPage topPage) throws Exception {
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.edit.xpath)));
		Common.isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(1000);
		if(!driver.findElement(By.xpath(clientSetting.socialInsuranceJoin.xpath)).isSelected()) {
			driver.findElement(By.xpath(clientSetting.socialInsuranceJoin.xpath)).click();
			Thread.sleep(1000);
		}
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementSelectionStateToBe(By.xpath(clientSetting.socialInsuranceJoin.xpath),true));
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
	}
	
}
