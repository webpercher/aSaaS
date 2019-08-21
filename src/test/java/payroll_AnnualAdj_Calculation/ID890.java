package payroll_AnnualAdj_Calculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
 * Calculate total amount of #id890(Salary Income Balance Tax Amount)
 * If AnnualAdjustmentInformation#annualAdjestmentFlag is "TRUE" and WithholdingIncomeTaxLedger#totalAmount =< 20,000,000 , show Calculation when either #id750(Amount After Income Deduction) or #id830(Income Deduction Total Amount) is altered
 **/
public class ID890 extends TestBase{
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
		Common.createCompanyClient(driver,"24");
		Common.addSimpleEmployeeInfo(driver);
		Common.forwardToPage(topPage.projectID.getDatas().get("annualadjustment"));
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
	}

	/**
	 * #id890=(#id750(Amount After Income Deduction) - #id830(Income Deduction Total Amount))
	 * ※Round down to the nearest 1000yen (i.e. 123456 gets rounded to 123000)
	 **/
	@Test
	public void id890_1() throws Exception {
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
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
		Thread.sleep(2000);
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",","").equals(String.valueOf(totalAmount))){
				driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
				Thread.sleep(2000);
			}
		}
		//get the value
		int id730 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.TotalPaymentAmount.xpath)).getText().replace(",",""));
		//#id750=(WithholdingIncomeTaxLedger#totalAmount ×【10】)（※Round down to the nearest yen (i.e. 1000.1 gets rounded to 1000)） - 【20】 + 【30】
		BigDecimal bd = new BigDecimal(id730*rateCoefficient);
		int product = Integer.parseInt(bd.setScale(1,BigDecimal.ROUND_CEILING).toString().split("\\.")[0]);
		int id750 = Integer.parseInt(String.valueOf(product-deductionAmount+additionalAmount));
		
		//id760
		//id760 = id160+id280
		int id100 = (int) (Math.random()*1000000);
		int id220 = (int) (Math.random()*1000000);
		//input the data 
		driver.findElement(By.xpath(annualAdjustment.socialInsuranceRate.xpath)).sendKeys(String.valueOf(id100));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).click();
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_1.xpath)).sendKeys(String.valueOf(id220));
		driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
		for(int i=0;i<30;i++){
			if(!driver.findElement(By.xpath(annualAdjustment.socialInsuranceDeductionAmountPayroll.xpath)).getText().replace(",","").equals(String.valueOf(id100+id220))){
				driver.findElement(By.xpath(annualAdjustment.bonusSocialRate_2.xpath)).click();
				Thread.sleep(2000);
			}
		}
		int id160 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.socialInsuranceTotalAmount.xpath)).getText().replace(",","")); 
		int id280 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.bonusSocialInsuranceTotalAmount.xpath)).getText().replace(",",""));
		int id760 = id160+id280; 
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to deductionname
		driver.findElement(By.xpath(annualAdjustment.deductionname.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.generalLifeNew1.xpath)));		
		
		//id790
		//id790 = compareAmount
		int generalLifePaidRate1 = (int) (Math.random()*40000);
		driver.findElement(By.xpath(annualAdjustment.generalLifePaidRate1.xpath)).sendKeys(String.valueOf(generalLifePaidRate1));
		if(!driver.findElement(By.xpath(annualAdjustment.generalLifeNew1.xpath)).isSelected()){
			driver.findElement(By.xpath(annualAdjustment.generalLifeNew1.xpath)).click();			
		}
		Thread.sleep(2000);
		int compareAmount = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.compareAmount.xpath)).getText().replace(",",""));
		int id790 = compareAmount;
		
		//id800
		//id800 = earthquakePremiumsPaid1		
		int earthquakePremiumsPaid1 = (int) (Math.random()*40000);
		driver.findElement(By.xpath(annualAdjustment.earthquakePremiumsPaid1.xpath)).sendKeys(String.valueOf(earthquakePremiumsPaid1));
		if(!driver.findElement(By.xpath(annualAdjustment.earthquakeLongTerm1.xpath)).isSelected()){
			driver.findElement(By.xpath(annualAdjustment.earthquakeLongTerm1.xpath)).click();			
		}
		Thread.sleep(2000);
		int id800 = earthquakePremiumsPaid1;
		
		//id770
		//id770 = SocialPremiumsPaid1		
		int SocialPremiumsPaid1 = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.SocialPremiumsPaid1.xpath)).sendKeys(String.valueOf(SocialPremiumsPaid1));
		Thread.sleep(2000);
		int id770 = SocialPremiumsPaid1;
		
		//id780
		//id780 = SMEsRIAmount		
		int SMEsRIAmount = (int) (Math.random()*1000000);
		driver.findElement(By.xpath(annualAdjustment.SMEsRIAmount.xpath)).sendKeys(String.valueOf(SMEsRIAmount));
		Thread.sleep(2000);
		int id780 = SMEsRIAmount;
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to annualEmployeeDep
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.shortfallOverCheckoutType.xpath)));
		
		//id810
		//id810 =
		driver.findElement(By.xpath(annualAdjustment.spouse.xpath)).click();
		Thread.sleep(2000);
		Select sel = new Select(driver.findElement(By.xpath(annualAdjustment.dependenttype.xpath)));
		sel.selectByVisibleText(annualAdjustment.dependenttype.getDatas().get("TS1624"));
		Thread.sleep(2000);
		driver.findElement(By.xpath(annualAdjustment.birthdayyear.xpath)).click();
		Thread.sleep(2000);
		//input the data
		int businessEarningAmount = 759999;
		driver.findElement(By.xpath(annualAdjustment.businessEarningAmount.xpath)).sendKeys(String.valueOf(businessEarningAmount));
		driver.findElement(By.xpath(annualAdjustment.miscellaneousEarningAmount.xpath)).click();
		Thread.sleep(2000);
		//get value 
		int spouseSpecialDeductionAmount = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.spouseSpecialDeductionAmount.xpath)).getText().replace(",",""));
		int id810 = spouseSpecialDeductionAmount;
		//save
		driver.findElement(By.xpath(annualAdjustment.save.xpath)).click();
		Thread.sleep(2000);
		//forward to payTab page
		driver.findElement(By.xpath(annualAdjustment.payTab.xpath)).click();
		new WebDriverWait( driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(annualAdjustment.annualAdjPage.xpath)));
		int id820 = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.personalDeductionTotalAmount.xpath)).getText().replace(",",""));
		//#id830=(#id760 + #id770 + #id780 + #id790 + #id800 + #id810 + #id820)
		int id830 = id760+id770+id780+id790+id800+id810+id820;
		
		//get the value
		int actual = Integer.parseInt(driver.findElement(By.xpath(annualAdjustment.salaryIncomeBalanceTaxAmount.xpath)).getText().replace(",",""));
		//#id890=(#id750 - #id830)
		int id890 = id750-id830; 
		if(id890<1000){
			id890 = 0;
		}else{
			id890 = Integer.parseInt(String.valueOf(id890).substring(0,String.valueOf(id890).length()-3)+"000");
		}
		//check the data
		if(id890==actual){
			System.out.println("id890_1 Pass");
		}else{
			System.out.println("id890_1 Failed");
			throw new Exception("Error of calculation, expected: <["+id890+"]> but was: <["+actual+"]> , " +
					"Please refer to input and output values below: "+"\r\n"+
					"actual = "+ actual+"\r\n"+
					"id890 = "+ id890+"\r\n"+
					"id750 = "+ id750+"\r\n"+
					"id830 = "+ id830);
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
				+ "\\resources\\pages\\testdata\\" + "SalaryAmountAfterIncomeDeduction24.csv";
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