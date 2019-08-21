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
 * Display the calculated total amount of each deduction amount recorded in the Bonus-slip.
 */
public class ID880 extends TestBase {
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
		//add emploeeInfo
		Common.addSimpleEmployeeInfo(driver);
		//add pay date
		Common.forwardToPage(topPage.projectID.getDatas().get("clientsettingpage"));
		ID650.addPayDate(driver, clientSetting);
		//add deduction
		Common.forwardToPage(topPage.projectID.getDatas().get("bonuspaymentstatements"));
		Common.isNotPending();
		addDeduction(driver, bonusPayment);
	}
	/**
	 * #id880 = #id710(social insurance sum amount)+#id720(income tax amount)+#id750(deduction1 name)+#id770(deduction2 name)
	 * 		+#id790(deduction3 name)+#id810(deduction4 name)+#id830(deduction5 name)+#id850(deduction6 name)+#id870(deduction7 name)
	 */
	@Test
	public void id880() throws Exception {
		//get social insurance sum amount
		driver.findElement(By.xpath(bonusPayment.bonusSlipPage.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.bonusPayment_disp.xpath)));
		Common.isNotPending();
		for(int i = 0; i < 10; i++) {
			driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		}
		Thread.sleep(2000);
		int id390 = (int) (Math.random() * 10000000);
		int id750 = (int) (Math.random() * 10000000);
		int id770 = (int) (Math.random() * 10000000);
		int id790 = (int) (Math.random() * 10000000);
		int id810 = (int) (Math.random() * 10000000);
		int id830 = (int) (Math.random() * 10000000);
		int id850 = (int) (Math.random() * 10000000);
		int id870 = (int) (Math.random() * 10000000);
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).sendKeys(String.valueOf(id390));
		driver.findElement(By.xpath(bonusPayment.deduction12.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction12.xpath)).sendKeys(String.valueOf(id750));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction13.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction13.xpath)).sendKeys(String.valueOf(id770));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction14.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction14.xpath)).sendKeys(String.valueOf(id790));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction15.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction15.xpath)).sendKeys(String.valueOf(id810));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction16.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction16.xpath)).sendKeys(String.valueOf(id830));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction17.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction17.xpath)).sendKeys(String.valueOf(id850));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deduction18.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deduction18.xpath)).sendKeys(String.valueOf(id870));
		Thread.sleep(1000);
		for(int i = 0; i < 30; i++) {
			if(driver.findElement(By.xpath(bonusPayment.socialInsranceSum2.xpath)).getText().equals("")) {
				driver.findElement(By.xpath(bonusPayment.bonusPayment.xpath)).click();
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id710Str = driver.findElement(By.xpath(bonusPayment.socialInsranceSum2.xpath)).getText();
		int id710 = Integer.parseInt(id710Str.replace(",", ""));
		String id720Str = driver.findElement(By.xpath(bonusPayment.incomeTax.xpath)).getAttribute("value");
		int id720 = Integer.parseInt(id720Str.replace(",", ""));
		//id880 = #id710+#id720+#id750+#id770+#id790+#id810+#id830+#id850+#id870
		int id880 = id710 + id720 + id750 + id770 + id790 + id810 + id830 + id850 + id870;
		for(int i = 0; i < 30; i++){
			if(!driver.findElement(By.xpath(bonusPayment.deductionSum.xpath)).getText().equals(Common.formatNum(String.valueOf(id880)))){
				driver.findElement(By.xpath(bonusPayment.healthInsrance.xpath)).click();
				Thread.sleep(1000);
			}
		}
		String id880Str = driver.findElement(By.xpath(bonusPayment.deductionSum.xpath)).getText();
		if(Common.formatNum(String.valueOf(id880)).equals(id880Str)) {
			System.out.println("ID880 Pass");
		}else {
			System.out.println("ID880 Failed");
			System.out.println(id710 + " + " + id720 + " + " + id750 + " + " + id770 + " + " + id790 + " + " + id810
					+ " + " + id830 + " + " + id850 + " + " + id870 + " = "
					+ id880 + "\n" + "sumData = " + id880Str);
			throw new Exception("Error of calculation, expected: <["+Common.formatNum(String.valueOf(id880))+"]> but was: <["+id880Str+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"expected = "+ Common.formatNum(String.valueOf(id880))+"\r\n"+
					"actual = "+ id880Str);
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
	
	public static void addDeduction(WebDriver driver, BonusPaymentStatements bonusPayment) throws Exception {
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.bonusAllowanceDeductionSetting.xpath)).click();
		Common.isNotPending();
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(bonusPayment.deductionButton.xpath)));
		driver.findElement(By.xpath(bonusPayment.deductionButton.xpath)).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.edit.xpath)).click();
		Thread.sleep(2000);
		//input value
		driver.findElement(By.xpath(bonusPayment.deductionName.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName.xpath)).sendKeys(bonusPayment.deductionName.getDatas().get("TS1827"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName2.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName2.xpath)).sendKeys(bonusPayment.deductionName2.getDatas().get("TS1828"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName3.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName3.xpath)).sendKeys(bonusPayment.deductionName3.getDatas().get("TS1829"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName4.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName4.xpath)).sendKeys(bonusPayment.deductionName4.getDatas().get("TS1830"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName5.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName5.xpath)).sendKeys(bonusPayment.deductionName5.getDatas().get("TS1833"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName6.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName6.xpath)).sendKeys(bonusPayment.deductionName6.getDatas().get("TS1834"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.deductionName7.xpath)).clear();
		driver.findElement(By.xpath(bonusPayment.deductionName7.xpath)).sendKeys(bonusPayment.deductionName7.getDatas().get("TS1835"));
		Thread.sleep(1000);
		driver.findElement(By.xpath(bonusPayment.save.xpath)).click();
		Common.isNotPending();
		Thread.sleep(1000);
	}
}
