package payroll_AnnualAdj_Calculation;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.asaas.test.util.ClientSettingPage;
import com.asaas.test.util.Common;
import com.asaas.test.util.DashboardPage;
import com.asaas.test.util.EmployeeSettingPage;
import com.asaas.test.util.PaymentStatements;
import com.asaas.test.util.AnnualAdjustment;
import com.asaas.test.util.SocialInsuranceCalculation;
import com.asaas.test.util.TestHelper;
import com.asaas.test.util.TopPage;


/**
 * Calculate total amount of #id750(Amount After Income Deduction) 
 **/
public class ID750_25 extends TestBase{
	public static TestHelper helper;
	public static ClientSettingPage clientSetting;
	public static DashboardPage dashboard;
	public static TopPage topPage;
	public static PaymentStatements paymentStatements;
	public static AnnualAdjustment annualAdjustment;
	public static EmployeeSettingPage employeeSetting;
	public static SocialInsuranceCalculation socialInsuranceCalculation;
	
	@BeforeClass
	public static void setUp() throws Exception {
		//initialize pages
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		paymentStatements = (PaymentStatements) helper.getPage("PaymentStatements");
		annualAdjustment = (AnnualAdjustment) helper.getPage("AnnualAdjustment");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//click A-SaaS menu
		driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
		//clean env
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		//precondition
		Common.createCompanyClient(driver,"25");
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
	}

	/**
	 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000, show Calculation when #id730(Total Payment Amount) is altered
	 * #id750=(WithholdingIncomeTaxLedger#totalAmount ×【10】)（※Round down to the nearest yen (i.e. 1000.1 gets rounded to 1000)） - 【20】 + 【30】
	 **/
	@Test
	public void id451_25_1() throws Exception {
		int totalAmount = (int) (Math.random()*20000000);
		int id90 = (int) (Math.random()*totalAmount);
		int id210 = totalAmount-id90;
		//【10】=SalaryAmountAfterIncomeDeduction#rateCoefficient of (SalaryAmountAfterIncomeDeduction#lowerPaymentAmountIncluded ≦ WithholdingIncomeTaxLedger#totalAmount ＜ SalaryAmountAfterIncomeDeduction#upperPaymentAmountNotIncluded)
		double rateCoefficient = Double.valueOf(getCSVData("rateCoefficient",totalAmount));
		//【20】=SalaryAmountAfterIncomeDeduction#deductionAmount of (SalaryAmountAfterIncomeDeduction#lowerPaymentAmountIncluded ≦ WithholdingIncomeTaxLedger#totalAmount ＜ SalaryAmountAfterIncomeDeduction#upperPaymentAmountNotIncluded)
		int deductionAmount = Integer.parseInt(getCSVData("deductionAmount",totalAmount));
		//【30】=SalaryAmountAfterIncomeDeduction#additionalAmount of (SalaryAmountAfterIncomeDeduction#lowerPaymentAmountIncluded ≦ WithholdingIncomeTaxLedger#totalAmount ＜ SalaryAmountAfterIncomeDeduction#upperPaymentAmountNotIncluded)
		int additionalAmount = Integer.parseInt(getCSVData("additionalAmount",totalAmount));
		//input the value
		String xpath = annualAdjustment.AmountAfterIncomeDeduction.xpath.replace("''","'"+annualAdjustment.AmountAfterIncomeDeduction.getDatas().get("title")+"'");
		driver.findElement(By.xpath(annualAdjustment.annualTaxablePayment.xpath)).sendKeys(String.valueOf(id90));
		driver.findElement(By.xpath(annualAdjustment.bonusPaymentAmount_1.xpath)).sendKeys(String.valueOf(id210));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).click();
		Thread.sleep(2000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",","").equals(String.valueOf(totalAmount))){
				driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).click();
				Thread.sleep(2000);
			}
		}
		//get the value
		int id730 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",",""));
		int actual = Integer.parseInt(driver.findElement(By.xpath(xpath)).getText().replace(",",""));
		//#id750=(WithholdingIncomeTaxLedger#totalAmount ×【10】)（※Round down to the nearest yen (i.e. 1000.1 gets rounded to 1000)） - 【20】 + 【30】
		BigDecimal bd = new BigDecimal(id730*rateCoefficient);
		int product = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_CEILING).toString().split("\\.")[0]);
		int id750 = Integer.parseInt(String.valueOf(product-deductionAmount+additionalAmount));
		//check the data
		if(id750==actual){
			System.out.println("id451_25_1 Pass");
		}else{
			System.out.println("id451_25_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id750+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id750 = "+ id750+"\r\n"+
					"totalAmount = "+ totalAmount+"\r\n"+
					"id90 = "+ id90+"\r\n"+
					"id210 = "+ id210+"\r\n"+
					"【10】 = "+ rateCoefficient+"\r\n"+
					"【20】 = "+ deductionAmount+"\r\n"+
					"【30】 = "+ additionalAmount+"\r\n"+
					"id730 = "+ id730);
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
	@SuppressWarnings("resource")
	public static String getCSVData(String key,int value) throws Exception
	{
		File file = new File(".");
		String filePath = file.getCanonicalPath()
				+ "\\resources\\pages\\testdata\\" + "SalaryAmountAfterIncomeDeduction25.csv";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "Shift-JIS"));
		String line = null;
		//get max account of one line 
		line = reader.readLine();
		String item[] = line.split(",");
		int num = 0;
		for(int i =0;i<item.length;i++){
			if(item[i].equals(key)){
				num = i;
			}
		}
		String result = "";
			line = reader.readLine();
			int amountOver;
			int amountUnder;
			while ((line) != null) {
				amountOver = Integer.parseInt(line.split(",")[2]);
				amountUnder = Integer.parseInt(line.split(",")[3]);
				if(amountOver<=value&&value<amountUnder){
					result = line.split(",")[num];
					return result;
				}
				line = reader.readLine();
			}
			return result;
	}
}