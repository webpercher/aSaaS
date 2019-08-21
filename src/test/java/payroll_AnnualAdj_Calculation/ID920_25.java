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
 * Calculate total amount of #id920(Annual Adjustment Tax Amount)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000 , show Calculation when either #id900(TaxableIncomeAmount) or #id910(MortgageSpecialPossibleDeductionAmount) is altered
 **/
public class ID920_25 extends TestBase{
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
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
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
	 * (#id900 - #id910) ≧ 0 の場合
	 * #id920=(#id900 - #id910)
	 * (#id900 - #id910) ＜ 0 の場合
	 * #id920=0
	 **/
	@Test
	public void id920_25_1() throws Exception {
		//id830
		int id830 = 380000;
		//id750
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
		int id730 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",",""));
		//#id750=(WithholdingIncomeTaxLedger#totalAmount ×【10】)（※Round down to the nearest yen (i.e. 1000.1 gets rounded to 1000)） - 【20】 + 【30】
		BigDecimal bd = new BigDecimal(id730*rateCoefficient);
		int product = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_CEILING).toString().split("\\.")[0]);
		int id750 = Integer.parseInt(String.valueOf(product-deductionAmount+additionalAmount));
		//#id890=(#id750 - #id830)
		double id900_10 = 0;
		int id900_20 = 0;
		int id890 = id750-id830; 
		if(id890<1000){
			id890 = 0;
		}else{
			id890 = Integer.parseInt(String.valueOf(id890).substring(0,String.valueOf(id890).length()-3)+"000");
		}
		if(id890<=1950000){
			id900_10 = 0.05;
			id900_20 = 0;
		}else if(id890<=3300000){
			id900_10 = 0.1;
			id900_20 = 97500;
		}else if(id890<=6950000){
			id900_10 = 0.2;
			id900_20 = 427500;
		}else if(id890<=9000000){
			id900_10 = 0.23;
			id900_20 = 636000;
		}else if(id890<=16920000){
			id900_10 = 0.33;
			id900_20 = 1536000;
		}
		//#id900=(#id890 × 【10】) - 【20】
		BigDecimal bd1 = new BigDecimal(id890*id900_10);
		int product1 = Integer.parseInt(bd1.setScale(1,BigDecimal.ROUND_CEILING).toString().split("\\.")[0]);
		int id900 = Integer.parseInt(String.valueOf(product1-id900_20));
		if(id900<0){
			id900 = 0;
		}
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to deductionname
		driver.findElement(By.xpath(annualAdjustment.deductionname.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.generalLifeNew1.xpath)));
		//id910 = mortgageSpecialPossibleDeductionAmount
		int mortgageSpecialPossibleDeductionAmount = (int) (Math.random()*1000000);
		int id910 = mortgageSpecialPossibleDeductionAmount;
		//input value 
		driver.findElement(By.xpath(annualAdjustment.mortgageSpecialPossibleDeductionAmount.xpath)).sendKeys(String.valueOf(id910));
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.annualAdjustmentTaxAmount.xpath)).getText().replace(",",""));
		//id920 = (#id900 - #id910)
		int id920 = id900-id910;
		if(id920<0){
			id920 = 0;
		}
		//check the data
		if(id920==actual){
			System.out.println("id920_25_1 Pass");
		}else{
			System.out.println("id920_25_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id920+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id920 = "+ id920+"\r\n"+
					"id900 = "+ id900+"\r\n"+
					"id910 = "+ id910);
		}
	}

	@AfterClass
	public static void tearDown() throws Exception {
		//return to dashboard page and exit system
		Common.removeCompany(driver,clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.returnDashboard.xpath)).click();
		Thread.sleep(3000);
	}
	
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