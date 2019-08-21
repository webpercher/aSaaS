package com.asaas.test.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.asaas.test.selenium.TestBase;
import com.google.common.base.Function;

public class Common extends TestBase{
	private static TestHelper helper;
	private static ClientSettingPage clientSetting;
	private static TopPage topPage;
	private static EmployeeSettingPage employeeSetting;
	private static ClientCompanyDetailPage clientCompanyDetail;
	public static String paymentYear;
	private static DashboardPage dashboard; 
	private static AnnualAdjustment annualAdjustment; 
	private static CompanyClientLayout compClientLayout;
	private static IndivdualClientLayout indivClientLayout;
	private static AccountingStructureList accountingStructureList;
	private static AccountingTemplateAccount accountingTemplateAccount;
	private static ClientPersonDetailPage clientPersonDetailPage;

	public static boolean isElementPresent(WebDriver driver,By by) {
		try {
			driver.manage().timeouts().implicitlyWait(8l,TimeUnit.SECONDS);
			driver.findElement(by);
			driver.manage().timeouts().implicitlyWait(30l,TimeUnit.SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			driver.manage().timeouts().implicitlyWait(30l,TimeUnit.SECONDS);
			return false;
		}
	}
	
	public static boolean isElementPresent(WebDriver driver,By by,long time) {
		try {
			driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
			driver.findElement(by);
			driver.manage().timeouts().implicitlyWait(30l,TimeUnit.SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			driver.manage().timeouts().implicitlyWait(30l,TimeUnit.SECONDS);
			return false;
		}
	}
	
	public static void isElementNotPresent(final String xpath) throws Exception {
		new WebDriverWait(driver, 30).until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return !Common.isElementPresent(driver, By.xpath(xpath), 3l);
			}
		});
	}
	
	public static void clear(WebDriver driver,String xpath) throws InterruptedException {
		if(!driver.switchTo().activeElement().getLocation().equals(driver.findElement(By.xpath(xpath)).getLocation())){
			driver.findElement(By.xpath(xpath)).click();
		}
		driver.findElement(By.xpath(xpath)).sendKeys(Keys.DELETE);
		Thread.sleep(1000);
	}

	public static void removeCompany(WebDriver driver,String companyCode) throws Exception
	{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		clientCompanyDetail = (ClientCompanyDetailPage) helper.getPage("ClientCompanyDetailPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//remove company using companyCode
		forwardToTopPage();
		//click the '21 Client Management'  button
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientcompanydetailpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).sendKeys(companyCode);
		driver.findElement(By.xpath(topPage.popup_ClientSetName.xpath)).click();
		Thread.sleep(1000);
		if(isElementPresent(driver,By.xpath("//tr[td/div=" + companyCode + "]")))
		{
			//click the 'OK' button
			driver.findElement(By.xpath(topPage.popupOK.xpath)).click();
			//click the 'Delete' button
			driver.findElement(By.xpath(clientCompanyDetail.delButton.xpath)).click();
			Thread.sleep(1000);
			//click the 'OK' button on the "Do you want to delete the target adviser" form
			driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
			//click the 'OK' button on the "Been successfully removed" button
			driver.findElement(By.xpath(clientCompanyDetail.okForRemove.xpath)).click();
		}
		else
		{
			//if the created company to be deleted company is not existing in the list click the 'Cancel' button
			driver.findElement(By.xpath(topPage.popupCancel.xpath)).click();
		}
		if(!Common.isElementPresent(driver, By.xpath(topPage.returnDashboard.xpath))){
			//click menu-list button
			driver.findElement(By.xpath(topPage.menuList.xpath)).click();
		}
	}

	public static String formatNum(String numString){
		//format the number string to 'x,xxx'
		numString = numString.replace(",","");
		if(numString.contains(".")){
			String result =String.format("%1$,f",Double.parseDouble(numString)).substring(0,String.format("%1$,f",Double.parseDouble(numString)).indexOf('.')+3);
			if(numString.split("\\.")[1].length() ==1){
				return result.substring(0,result.length()-1);
			}else{
				return result;
			}
		}else{
			return String.format("%1$,d",Long.parseLong(numString));	
		}
	}
	
	public static String getRandomDataHalf(String type, int length) throws Exception {
		String data = "";
		String negativeData = "";
		String charData[] = { "!", "@", "#", "$", "%", "^", "&", "*" };
		if (type.equals("int")) {
			for (int i = 0; i < length-1; i++) {
				data += (int) (10 * Math.random());
			}
			data = (int) (9 * Math.random() +1) + data;
		} else if (type.equals("nint")) {
			for (int i = 0; i < length-1; i++) {
				data += (int) (10 * Math.random());
			}
			data = "-" + (int) (9 * Math.random() +1) + data;
		} else if (type.equals("double")) {
			for (int i = 0; i < length-3; i++) {
				data += (int) (10 * Math.random());
			}
			for (int i = 0; i < 2; i++) {
				negativeData += (int) (10 * Math.random());
			}
			data = (int) (9 * Math.random() +1) + data + "." + negativeData;
		} else if (type.equals("ndouble")) {
			for (int i = 0; i < length-3; i++) {
				data += (int) (10 * Math.random());
			}
			for (int i = 0; i < 2; i++) {
				negativeData += (int) (10 * Math.random());
			}
			data = "-" + (int) (9 * Math.random() +1) + data + "." + negativeData;
		} else if (type.equals("char")) {
			for (int i = 0; i < length; i++) {
				data += String.valueOf((char) ('a' + (int) (Math.random() * 26)));
			}
		} else if (type.equals("uchar")) {
			for (int i = 0; i < length; i++) {
				Random rnd = new Random();
				data += charData[rnd.nextInt(8)];
			}
		} else if (type.equals("kana")) {
			for (int i = 0; i < length; i++) {
				data += String.valueOf((char) ('ｱ' + (int) (Math.random() * 75)));
			}
		}
		return data;
	}

	public static String getRandomDataFull(String type, int length) throws Exception {
		String data = "";
		if (type.equals("kana")) {
			for (int i = 0; i < length; i++) {
				data += String.valueOf((char) ('ア' + (int) (Math.random() * 75)));
			}
			return data;
		}else if (type.equals("gana")) {
			for (int i = 0; i < length; i++) {
				data += String.valueOf((char) ('あ' + (int) (Math.random() * 75)));
			}
			return data;
		}
		char[] c = getRandomDataHalf(type, length).toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	
	public static String checkInputedTextSide(WebDriver driver,String epath){
		//check text side
		return driver.findElement(By.xpath(epath)).getCssValue("text-align");
	}

	public static boolean checkValidSelect(WebDriver driver,String epath,String[] values){	
		//check select options all exist
		WebElement ele = driver.findElement(By.xpath(epath));
		if(ele.getTagName().toLowerCase().equals("select"))
		{
			String[] opts = ele.getText().split("\\\n");			
			if(opts.length != values.length){
				return false;
			}
			for(int i =0;i < values.length;i++)
			{					
				boolean b = false;
				for(int j = 0;j<opts.length;j++)
				{
					if(values[j].equals(opts[i])){
						b = true;
						break;
					}
				}
				if(!b){
					return false;
				}
			}
		}
		
		return true;
	}

	public static boolean checkValidSelectInSequence(WebDriver driver,String epath,String[] values) throws Exception{
		// check select options all exist and they are in Sequence
		WebElement ele = driver.findElement(By.xpath(epath));
		if(ele.getTagName().toLowerCase().equals("select"))
		{
			String[] opts = ele.getText().split("\\\n");			
			if(opts.equals(values)){
				return true;
			}else{
				return false;
			}
		}else{
			throw new Exception("xpath format is wrong");
		}
	
	}
	
	public static boolean selectEachOne(WebDriver driver,String xpath) throws Exception{
		String[] opts = driver.findElement(By.xpath(xpath)).getText().split("\\\n");
		//check select options					
		for(int i=0;i < opts.length;i++)
		{
			Select sel = new Select (driver.findElement(By.xpath(xpath))); 
			sel.selectByVisibleText(opts[i]);
			Thread.sleep(2000);
			//verify value is correct
			org.junit.Assert.assertEquals(opts[i],driver.findElement(By.xpath(xpath)).getAttribute("value"));
		}
		return true;
	}

	public static void forwardToPage(String dest) throws Exception{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		forwardToTopPage();
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(dest + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		Thread.sleep(1000);
		//click OK button
		driver.findElement(By.xpath(topPage.popupOK.xpath)).click();
	}

	public static void forwardToPage(String dest,String SetCode) throws Exception{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the pointed  page
		forwardToTopPage();
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(dest + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_ClientSetCode.xpath)).sendKeys(SetCode);
		//click OK button
		driver.findElement(By.xpath(topPage.popupOK.xpath)).click();
	}
	
	public static void forwardToTopPage() throws Exception{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		dashboard = (DashboardPage) helper.getPage("DashboardPage");
		//forward to the pointed  page
		try{
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		}
		catch (NoSuchElementException e){
			driver.get(CONFIG.getProperty("testSiteName").split("/dashboard")[0] + "/dashboard/main.zul");
			Thread.sleep(2000);
			if(driver.getTitle().contains("Asaas DashBoard")&&!isElementPresent(driver,By.xpath(dashboard.menuOnDashboard.xpath))){
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_F5);
				r.keyRelease(KeyEvent.VK_F5);
			}
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dashboard.menuOnDashboard.xpath)));
			driver.findElement(By.xpath(dashboard.menuOnDashboard.xpath)).click();
			Thread.sleep(2000);
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		}
	}
		
	public static void getSelectValue(String xpath,String exceptValue) throws Exception{
		String value = driver.findElement(By.xpath(xpath)).getAttribute("value");
		String newXpath = xpath+"/option[@value='"+value+"']"; 
		org.junit.Assert.assertEquals(exceptValue,driver.findElement(By.xpath(newXpath)).getText());
	}
	
	public static void isNotPending() throws Exception {
		isElementNotPresent(topPage.pendingProgressBar.xpath);
	}
	
	public static String ConcatStr(String str,String path){
		int idx1 = path.indexOf("'");
		while(idx1 != -1){
			if(path.substring(idx1+1, idx1+2).equals("'")){
				return path.substring(0,idx1+1) + str + path.substring(idx1+1);
			}
			idx1 = path.indexOf("'", idx1+1);
		}
		return path;
	}	

	public static int calcAge(String era,String year,String mon,String day){
		int ageYear = 0;
		if(era.equalsIgnoreCase("heisei")||era.contentEquals("平成")){
			ageYear = Integer.parseInt(year)+ 1988;
		}
		else if(era.equalsIgnoreCase("showa")||era.contentEquals("昭和")){
			ageYear = Integer.parseInt(year)+ 1925;
		}
		else if(era.equalsIgnoreCase("taisho")||era.contentEquals("大正")){
			ageYear = Integer.parseInt(year)+ 1911;
		}
		Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int month = currentDate.get(Calendar.MONTH) + 1; 
		if(( month > Integer.valueOf(mon)) || ((month == Integer.valueOf(mon)) && (currentDate.get(Calendar.DATE) > Integer.valueOf(day)))){
			return currentDate.get(Calendar.YEAR) - ageYear;
		}
		if((month < Integer.valueOf(mon)) || ((month == Integer.valueOf(mon)) && (currentDate.get(Calendar.DATE) < Integer.valueOf(day)))){
			return currentDate.get(Calendar.YEAR) - ageYear - 1;
		}
		return -1;
		
	}

	public static int formatDecimalPart(String method,Double numString) throws Exception{
		//format the number decimal part
		double num = Double.valueOf(numString);
		int intPart = 0; 
		if(method.equals("切り捨て")){
			 intPart = (int) Math.floor(num);
		}else if(method.equals("切り上げ")){
			 intPart = (int) Math.ceil(num);
		}else if(method.equals("四捨五入")){
			 intPart = (int) Math.round(num);
		}else{
			 throw new Exception("the method value set wrong");
		}
		return intPart;
	}	
	
	public static int roundHalfDown(String baseValue,String rate){
		long product = Long.valueOf(baseValue)*Long.valueOf(rate.replace(".",""));
		double consult = Double.valueOf(product)/1000000;
		BigDecimal bd = new BigDecimal(consult);
		int result = Integer.valueOf((bd.setScale(0,BigDecimal.ROUND_HALF_DOWN).toString()));
		return result;
	}

	public static int convertToPSTYear(String era,String year){
		if(era.equalsIgnoreCase("heisei")||era.contentEquals("平成")){
			return Integer.parseInt(year)+ 1988;
		}
		else if(era.equalsIgnoreCase("showa")||era.contentEquals("昭和")){
			return Integer.parseInt(year)+ 1925;
		}
		else{
				return -1;
			}
	}

	public static String[] compareValuesDifferent(String[] compareValues) throws Exception {	
		if(compareValues[0] == null & compareValues[1] == null){
			throw new Exception("data are the same");
		}else if(compareValues[0] != null & compareValues[1] != null){
			if(compareValues[0].equals(compareValues[1])){
				throw new Exception("data are the same");
			}
		}
		return compareValues;
	}
	
	public static void compareValuesSame(String oneValue,String otherValue) throws Exception {	
		if(oneValue == null & otherValue == null){		
		}else if(oneValue != null & otherValue != null){
			if(!oneValue.equals(otherValue)){
				throw new Exception("data are different");
			}
		}else{
			throw new Exception("data are different");
		}
	}
		
	public static void createCompanyClientDetail(WebDriver driver) throws Exception 
	{
		helper = new TestHelper();
		clientCompanyDetail = (ClientCompanyDetailPage) helper.getPage("ClientCompanyDetailPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//click the '21 Client Management'  button
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientcompanydetailpage") + "\n");
		//click the 'click the Add Client' button
		driver.findElement(By.xpath(topPage.addMore.xpath)).click();
		//click the 'Add Company Client' button
		driver.findElement(By.xpath(topPage.corporateAdvisor.xpath)).click();
		Thread.sleep(2000);
		//set the 'Company Code' value
		driver.findElement(By.xpath(clientCompanyDetail.companyCode.xpath)).clear();
		driver.findElement(By.xpath(clientCompanyDetail.companyCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//set the 'Company Name' value 
		driver.findElement(By.xpath(clientCompanyDetail.companyName.xpath)).clear();
		driver.findElement(By.xpath(clientCompanyDetail.companyName.xpath)).sendKeys(clientSetting.companyName.getDatas().get("common"));
		//set the 'Company Kana' value
		driver.findElement(By.xpath(clientCompanyDetail.companyKana.xpath)).clear();
		driver.findElement(By.xpath(clientCompanyDetail.companyKana.xpath)).sendKeys(clientSetting.companyKana.getDatas().get("common"));
		driver.findElement(By.xpath(clientCompanyDetail.companyCode.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(clientCompanyDetail.save.xpath)).click();
		Thread.sleep(1000);
		//click OK button
		driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
		Thread.sleep(2000);
		if(isElementPresent(driver,By.xpath(clientCompanyDetail.okForDel.xpath),2)){
			driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Close' button
		driver.findElement(By.xpath(clientCompanyDetail.close.xpath)).click();
	}
	
	public static void createIndiClientDetail(WebDriver driver) throws Exception 
	{
		helper = new TestHelper();
		clientPersonDetailPage = (ClientPersonDetailPage) helper.getPage("ClientPersonDetailPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//click the '21 Client Management'  button
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientcompanydetailpage") + "\n");
		//click the 'click the Add Client' button
		driver.findElement(By.xpath(topPage.addMore.xpath)).click();
		//click the 'Add personal Client' button
		driver.findElement(By.xpath(topPage.personalAdvisor.xpath)).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientPersonDetailPage.clientCode.xpath)));
		//set the 'Company Code' value
		driver.findElement(By.xpath(clientPersonDetailPage.clientCode.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.clientCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//set the 'individualName_lastName' value 
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastName.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastName.xpath)).sendKeys(clientSetting.familyName.getDatas().get("common"));
		//set the 'individualName_firstName' value
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstName.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstName.xpath)).sendKeys(clientSetting.firstName.getDatas().get("common"));
		//set the 'individualName_lastNameKana' value
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastNameKana.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastNameKana.xpath)).sendKeys(clientSetting.familyNameKana.getDatas().get("common"));
		//set the 'individualName_firstNameKana' value 
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstNameKana.xpath)).sendKeys(clientSetting.firstNameKana.getDatas().get("common"));
		//set the statement type to General
		driver.findElement(By.xpath(clientPersonDetailPage.statementType_General.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(clientPersonDetailPage.save.xpath)).click();
		Thread.sleep(1000);
		//click OK button
		driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
		Thread.sleep(2000);
		if(isElementPresent(driver,By.xpath(clientCompanyDetail.okForDel.xpath),2)){
			driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Close' button
		driver.findElement(By.xpath(clientPersonDetailPage.close.xpath)).click();
	}
	
	public static void createIndiClientDetail(WebDriver driver,String code) throws Exception 
	{
		helper = new TestHelper();
		clientPersonDetailPage = (ClientPersonDetailPage) helper.getPage("ClientPersonDetailPage");
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//click the '21 Client Management'  button
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientcompanydetailpage") + "\n");
		//click the 'click the Add Client' button
		driver.findElement(By.xpath(topPage.addMore.xpath)).click();
		//click the 'Add personal Client' button
		driver.findElement(By.xpath(topPage.personalAdvisor.xpath)).click();
		//set the 'Company Code' value
		driver.findElement(By.xpath(clientPersonDetailPage.clientCode.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.clientCode.xpath)).sendKeys(code);
		//set the 'individualName_lastName' value 
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastName.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastName.xpath)).sendKeys(clientSetting.familyName.getDatas().get("common"));
		//set the 'individualName_firstName' value
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstName.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstName.xpath)).sendKeys(clientSetting.firstName.getDatas().get("common"));
		//set the 'individualName_lastNameKana' value
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastNameKana.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_lastNameKana.xpath)).sendKeys(clientSetting.familyNameKana.getDatas().get("common"));
		//set the 'individualName_firstNameKana' value 
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(clientPersonDetailPage.individualName_firstNameKana.xpath)).sendKeys(clientSetting.firstNameKana.getDatas().get("common"));
		//set the statement type to General
		driver.findElement(By.xpath(clientPersonDetailPage.statementType_General.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(clientPersonDetailPage.save.xpath)).click();
		Thread.sleep(1000);
		//click OK button
		driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
		Thread.sleep(2000);
		if(isElementPresent(driver,By.xpath(clientCompanyDetail.okForDel.xpath),2)){
			driver.findElement(By.xpath(clientCompanyDetail.okForDel.xpath)).click();
			Thread.sleep(2000);
		}
		//click the 'Close' button
		driver.findElement(By.xpath(clientPersonDetailPage.close.xpath)).click();
	}
	
	public static void createCompanyClient(WebDriver driver) throws Exception 
	{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'company Information'  page
		createCompanyClientDetail(driver);
		new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//click the 'click the Add payroll' button
		driver.findElement(By.xpath(topPage.popup_Payroll_AddPayroll.xpath)).click();
		Thread.sleep(2000);
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_Ok.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clientSetting.beforeButton.xpath)));
		//click the 'Before' button
		driver.findElement(By.xpath(clientSetting.beforeButton.xpath)).click();
		//set the front 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.zipCode1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.zipCode1.xpath)).sendKeys(clientSetting.zipCode1.getDatas().get("common"));
		//set the last 'Zip Code' value
		driver.findElement(By.xpath(clientSetting.zipCode2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.zipCode2.xpath)).sendKeys(clientSetting.zipCode2.getDatas().get("common"));
		//set the front 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber1.xpath)).sendKeys(clientSetting.telephoneNumber1.getDatas().get("common"));
		//set the middle 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber2.xpath)).sendKeys(clientSetting.telephoneNumber2.getDatas().get("common"));
		//set the last 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber3.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber3.xpath)).sendKeys(clientSetting.telephoneNumber3.getDatas().get("common"));
		//click the 'Next Month' button
		driver.findElement(By.xpath(clientSetting.nextMonth.xpath)).click();
		Thread.sleep(2000);
		//save the changes
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();		
		Thread.sleep(2000);
		//get year
		int year = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));
		String currentYear = String.valueOf(year-1988);
		//input the value to current year
		driver.findElement(By.xpath(clientSetting.currentYear.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.currentYear.xpath)).sendKeys(currentYear);		
		//set the 'Payment Day' value 
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).sendKeys(clientSetting.paymentDay.getDatas().get("common"));		
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).click();
		Thread.sleep(1000);
		//click the 'save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();		
		Thread.sleep(2000);
		//get the 'Payroll Period' value to paymentYear
		paymentYear = driver.findElement(By.xpath(clientSetting.payrollPeriod.xpath)).getText();
		//click the 'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}
	
	public static void createCompanyClient(WebDriver driver,String payrollPeriod) throws Exception 
	{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'company Information'  page
		createCompanyClientDetail(driver);
		new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//click the 'click the Add payroll' button
		driver.findElement(By.xpath(topPage.popup_Payroll_AddPayroll.xpath)).click();
		Thread.sleep(2000);
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_Ok.xpath)).click();
		Thread.sleep(3000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clientSetting.beforeButton.xpath)));
		//click the 'Before' button
		driver.findElement(By.xpath(clientSetting.beforeButton.xpath)).click();
		//set the front 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.zipCode1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.zipCode1.xpath)).sendKeys(clientSetting.zipCode1.getDatas().get("common"));
		//set the last 'Zip Code' value
		driver.findElement(By.xpath(clientSetting.zipCode2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.zipCode2.xpath)).sendKeys(clientSetting.zipCode2.getDatas().get("common"));
		//set the front 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber1.xpath)).sendKeys(clientSetting.telephoneNumber1.getDatas().get("common"));
		//set the middle 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber2.xpath)).sendKeys(clientSetting.telephoneNumber2.getDatas().get("common"));
		//set the last 'Telephone Number' value 
		driver.findElement(By.xpath(clientSetting.telephoneNumber3.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.telephoneNumber3.xpath)).sendKeys(clientSetting.telephoneNumber3.getDatas().get("common"));
		//click the 'Next Month' button
		driver.findElement(By.xpath(clientSetting.nextMonth.xpath)).click();
		Thread.sleep(2000);
		//save the changes
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();		
		Thread.sleep(2000);
		//get the 'Payroll Period' value to paymentYear
		driver.findElement(By.xpath(clientSetting.currentYear.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.currentYear.xpath)).sendKeys(payrollPeriod);
		Thread.sleep(2000);
		//set the 'Payment Day' value 
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).sendKeys(clientSetting.paymentDay.getDatas().get("common"));		
		driver.findElement(By.xpath(clientSetting.paymentDay.xpath)).click();
		Thread.sleep(1000);
		//click the 'save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();		
		Thread.sleep(2000);
		isNotPending();
		//click the 'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(3000);
	}

	public static void createIndividualClient(WebDriver driver) throws Exception 
	{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//
		createIndiClientDetail(driver);
		//forward to the 'Company Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//click the 'click the Add payroll' button
		driver.findElement(By.xpath(topPage.popup_Payroll_AddPayroll.xpath)).click();
		Thread.sleep(2000);
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_Ok.xpath)).click();	
		//set the 'Trade Name' value
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.tradeName.xpath)));
		driver.findElement(By.xpath(clientSetting.tradeName.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.tradeName.xpath)).sendKeys(clientSetting.tradeName.getDatas().get("common"));
		driver.findElement(By.xpath(clientSetting.firstNameKana.xpath)).click();
		Thread.sleep(2000);
		//click the 'Full name' radio
		driver.findElement(By.xpath(clientSetting.fullName.xpath)).click();
		//set the front 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.individualZipCode1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualZipCode1.xpath)).sendKeys(clientSetting.individualZipCode1.getDatas().get("common"));
		//set the last 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.individualZipCode2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualZipCode2.xpath)).sendKeys(clientSetting.individualZipCode2.getDatas().get("common"));
		//click the 'Next Month' button
		driver.findElement(By.xpath(clientSetting.individualNextMonth.xpath)).click();
		Thread.sleep(2000);
		//save the changes
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();		
		Thread.sleep(2000);
		//get the year
		int year = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));
		String currentYear = String.valueOf(year-1988);
		//input the value to current year
		driver.findElement(By.xpath(clientSetting.individualPaymentYear.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualPaymentYear.xpath)).sendKeys(currentYear);
		//set the 'Payment Day' value 
		driver.findElement(By.xpath(clientSetting.individualPaymentDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualPaymentDay.xpath)).sendKeys(clientSetting.individualPaymentDay.getDatas().get("common"));
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
//		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
//		Thread.sleep(2000);
		//click the 'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}
	
	public static void createIndividualClient(WebDriver driver,String paymentYear) throws Exception 
	{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//percondition
		createIndiClientDetail(driver);
		//forward to the 'Company Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//click the 'click the Add payroll' button
		driver.findElement(By.xpath(topPage.popup_Payroll_AddPayroll.xpath)).click();
		Thread.sleep(2000);
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Payroll_Client_Ok.xpath)).click();
		//set the 'Trade Name' value
		driver.findElement(By.xpath(clientSetting.tradeName.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.tradeName.xpath)).sendKeys(clientSetting.tradeName.getDatas().get("common"));
		driver.findElement(By.xpath(clientSetting.firstNameKana.xpath)).click();
		Thread.sleep(2000);
		//click the 'Full name' radio
		driver.findElement(By.xpath(clientSetting.fullName.xpath)).click();
		//set the front 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.individualZipCode1.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualZipCode1.xpath)).sendKeys(clientSetting.individualZipCode1.getDatas().get("common"));
		//set the last 'Zip Code' value 
		driver.findElement(By.xpath(clientSetting.individualZipCode2.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualZipCode2.xpath)).sendKeys(clientSetting.individualZipCode2.getDatas().get("common"));
		//click the 'Next Month' button
		driver.findElement(By.xpath(clientSetting.individualNextMonth.xpath)).click();
		Thread.sleep(2000);
		//save the changes
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();		
		Thread.sleep(2000);
		//set the 'paymentYear' value
		driver.findElement(By.xpath(clientSetting.individualPaymentYear.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualPaymentYear.xpath)).sendKeys(clientSetting.individualPaymentYear.getDatas().get("common"));
		//set the 'Payment Day' value 
		driver.findElement(By.xpath(clientSetting.individualPaymentDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.individualPaymentDay.xpath)).sendKeys(clientSetting.individualPaymentDay.getDatas().get("common"));
		//click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
//		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
//		Thread.sleep(2000);
		//click the 'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}

	public static void CompanyAddInsu(WebDriver driver) throws Exception {
		//open payroll company info
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");		
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		Thread.sleep(2000);
		//go to 'payroll Calculation Basic Information' page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Thread.sleep(2000);
		//click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//set the 'Closing Day' value 
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).sendKeys(clientSetting.closingDay.getDatas().get("common"));
		//click the 'Save' Button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the 'Social Insurance Rate Setting' button
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.perfectureType.xpath)));
		Thread.sleep(2000);
		//click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//click the 'Perfecture Type' button  to get a "sel" variable
		Select sel = new Select (driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		//set the 'Perfecture Type' value 
		sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get("common"));
		Thread.sleep(2000);
		//save the change
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(1000);
		//click the 'Save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the  'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}

	public static void createCompanyClientSocialInsu(WebDriver driver)throws Exception {
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		// create company and add societal insurance
		createCompanyClient(driver);
		Thread.sleep(2000);
		//forward to the 'Company Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		//go to 'payroll Calculation Basic Information' page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Thread.sleep(2000);
		// click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		// set the 'Closing Day' value
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).sendKeys(clientSetting.closingDay.getDatas().get("common"));
		Thread.sleep(2000);
		// click the 'Save' Button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		// click the 'Social Insurance Rate Setting' button
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.perfectureType.xpath)));
		Thread.sleep(2000);
		// click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		// click the 'Perfecture Type' button to get a "sel" variable
		Select sel = new Select (driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get("common"));
		Thread.sleep(2000);
		// save the change
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(1000);
		// click the 'Save' button
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		// click the 'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}
	
	public static void addEmployeeInfo(WebDriver driver) throws Exception
{
	helper = new TestHelper();
	employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
	topPage = (TopPage) helper.getPage("TopPage");
	//forward to the 'employee Information'  page
	Thread.sleep(2000);
	new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
	driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
	driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
	//type client code to open it
	driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
	driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
	//click the 'OK' button 
	Thread.sleep(1000);
	driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
	Thread.sleep(2000);
	isNotPending();
	//set the 'Employee Code *' value 
	driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).sendKeys(employeeSetting.employeeCode.getDatas().get("common"));
	//set the 'Family name Kana' value 
	driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).sendKeys(employeeSetting.familyNameKana.getDatas().get("common"));
	//set the 'First name kana' value
	driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).sendKeys(employeeSetting.firstNameKana.getDatas().get("common"));
	//set the 'Family name' value
	driver.findElement(By.xpath(employeeSetting.familyName.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.familyName.xpath)).sendKeys(employeeSetting.familyName.getDatas().get("common"));
	//set the 'First name' value
	driver.findElement(By.xpath(employeeSetting.firstName.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.firstName.xpath)).sendKeys(employeeSetting.firstName.getDatas().get("common"));
	//set the 'Employee Type *' value 
	Select selEmployeeType = new Select (driver.findElement(By.xpath(employeeSetting.selEmployeeBri.xpath)));
	selEmployeeType.selectByVisibleText(employeeSetting.selEmployeeBri.getDatas().get("common"));
	//set the 'SHOWA' value
	Select selEmployeeBri = new Select (driver.findElement(By.xpath(employeeSetting.era.xpath)));
	selEmployeeBri.selectByVisibleText(employeeSetting.era.getDatas().get("common"));	
	//set the 'Year' value
	driver.findElement(By.xpath(employeeSetting.year.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.year.xpath)).sendKeys(employeeSetting.year.getDatas().get("common"));
	//set the 'Month' value
	driver.findElement(By.xpath(employeeSetting.month.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.month.xpath)).sendKeys(employeeSetting.month.getDatas().get("common"));
	//set the 'Day' value
	driver.findElement(By.xpath(employeeSetting.day.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.day.xpath)).sendKeys(employeeSetting.day.getDatas().get("common"));
	//set Gender to male
	driver.findElement(By.xpath(employeeSetting.gender_male.xpath)).click();
	Thread.sleep(2000);
	//click the 'Calculate Income Tax Automatically' button twice to make sure it is selected 
	if(driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).getAttribute("checked") == null){
			driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).click();	
			Thread.sleep(2000);
	}
	//set the 'Monthly Table Type *' value 
	driver.findElement(By.xpath(employeeSetting.monthlyTableType.xpath)).click();
	Thread.sleep(2000);
	//set the 'Dependents Number *' value 
	driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(employeeSetting.dependentsNumber.getDatas().get("common"));
	//click the "make annual Adjustment"
	driver.findElement(By.xpath(employeeSetting.makeAnnualAdjustment.xpath)).click();
	Thread.sleep(2000);
	//set 'Payment Cycle Type *' value 
	driver.findElement(By.xpath(employeeSetting.paymentCycleM.xpath)).click();
	Thread.sleep(2000);
	//set the 'Monthly' value
	driver.findElement(By.xpath(employeeSetting.monthly.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.monthly.xpath)).sendKeys(employeeSetting.monthly.getDatas().get("common"));
	//payment unit value
	driver.findElement(By.xpath(employeeSetting.paymentUnit_Month.xpath)).click();
	Thread.sleep(2000);	
	//set the 'Commuter Allowance Amount' value
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(employeeSetting.commuterAllowanceAmount.getDatas().get("common"));
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).click();
	Thread.sleep(1000);
	//make insured is checked
	if(!driver.findElement(By.xpath(employeeSetting.insured.xpath)).isSelected()){
		driver.findElement(By.xpath(employeeSetting.insured.xpath)).click();
		Thread.sleep(1000);
	}
	//set the 'General Reward Monthly Amount' value
	driver.findElement(By.xpath(employeeSetting.generalRewardMonthlyAmount.xpath)).click();
	Thread.sleep(2000);
	org.junit.Assert.assertTrue(driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).isDisplayed());
	//select the 'Health InsuranceGrade' value
	driver.findElement(By.xpath(employeeSetting.healthInsuranceGrade.xpath)).click();
	//click the 'Select' button on "Index Monday Earning Table" form
	driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).click();
	Thread.sleep(2000);
	//set the 'Salary *' value
	driver.findElement(By.xpath(employeeSetting.salaryCash.xpath)).click();
	//set the 'Bonus *' value
	driver.findElement(By.xpath(employeeSetting.bonusCash.xpath)).click();
	//click the 'Save' button
	driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
	Thread.sleep(2000);
	//click the 'Close' button
	driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
	Thread.sleep(2000);
}
	
	public static void addSimpleEmployeeInfo(WebDriver driver) throws Exception
	{
		helper = new TestHelper();
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'employee Information'  page
		new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button 
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		Thread.sleep(2000);
		isNotPending();
		//set the 'Employee Code *' value 
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).sendKeys(employeeSetting.employeeCode.getDatas().get("common"));
		//set the 'Family name Kana' value 
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).sendKeys(employeeSetting.familyNameKana.getDatas().get("common"));
		//set the 'First name kana' value
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).sendKeys(employeeSetting.firstNameKana.getDatas().get("common"));
		//set the 'Family name' value
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).sendKeys(employeeSetting.familyName.getDatas().get("common"));
		//set the 'First name' value
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).sendKeys(employeeSetting.firstName.getDatas().get("common"));
		Thread.sleep(2000);
		//select the annual adj
		driver.findElement(By.xpath(employeeSetting.makeAnnualAdjustment.xpath)).click();
		Thread.sleep(2000);
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'Close' button
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
		Thread.sleep(2000);
	}
	
	public static void addEmployeeInfoByCode(WebDriver driver,String employeeCode) throws Exception
{
	helper = new TestHelper();
	employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
	topPage = (TopPage) helper.getPage("TopPage");
	//forward to the 'employee Information'  page
	org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
	driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
	driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
	//type client code to open it
	driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
	driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
	//click the 'OK' button 
	Thread.sleep(1000);
	driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
	//check button of 'add new'
	if(Common.isElementPresent(driver, By.xpath(employeeSetting.addNew.xpath))){
		driver.findElement(By.xpath(employeeSetting.addNew.xpath)).click();
		Thread.sleep(2000);
	}
	//set the 'Employee Code *' value 
	driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).sendKeys(employeeCode);
	//set the 'Family name Kana' value 
	driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).sendKeys(employeeSetting.familyNameKana.getDatas().get("common"));
	//set the 'First name kana' value
	driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).sendKeys(employeeSetting.firstNameKana.getDatas().get("common"));
	//set the 'Family name' value
	driver.findElement(By.xpath(employeeSetting.familyName.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.familyName.xpath)).sendKeys(employeeSetting.familyName.getDatas().get("common"));
	//set the 'First name' value
	driver.findElement(By.xpath(employeeSetting.firstName.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.firstName.xpath)).sendKeys(employeeSetting.firstName.getDatas().get("common"));
	//set the 'Employee Type *' value 
	Select selEmployeeType = new Select (driver.findElement(By.xpath(employeeSetting.selEmployeeBri.xpath)));
	selEmployeeType.selectByVisibleText(employeeSetting.selEmployeeBri.getDatas().get("common"));
	//set the 'SHOWA' value
	Select selEmployeeBri = new Select (driver.findElement(By.xpath(employeeSetting.era.xpath)));
	selEmployeeBri.selectByVisibleText(employeeSetting.era.getDatas().get("common"));	
	//set the 'Year' value
	driver.findElement(By.xpath(employeeSetting.year.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.year.xpath)).sendKeys(employeeSetting.year.getDatas().get("common"));
	//set the 'Month' value
	driver.findElement(By.xpath(employeeSetting.month.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.month.xpath)).sendKeys(employeeSetting.month.getDatas().get("common"));
	//set the 'Day' value
	driver.findElement(By.xpath(employeeSetting.day.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.day.xpath)).sendKeys(employeeSetting.day.getDatas().get("common"));
	//set Gender to male
	driver.findElement(By.xpath(employeeSetting.gender_male.xpath)).click();
	Thread.sleep(2000);
	//click the 'Calculate Income Tax Automatically' button twice to make sure it is selected 
	if(driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).getAttribute("checked") == null){
			driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).click();	
			Thread.sleep(2000);
	}
	//set the 'Monthly Table Type *' value 
	driver.findElement(By.xpath(employeeSetting.monthlyTableType.xpath)).click();
	Thread.sleep(2000);
	//set the 'Dependents Number *' value 
	driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(employeeSetting.dependentsNumber.getDatas().get("common"));
	//click the "make annual Adjustment"
	driver.findElement(By.xpath(employeeSetting.makeAnnualAdjustment.xpath)).click();
	Thread.sleep(2000);
	//set 'Payment Cycle Type *' value 
	driver.findElement(By.xpath(employeeSetting.paymentCycleM.xpath)).click();
	Thread.sleep(2000);
	//set the 'Monthly' value
	driver.findElement(By.xpath(employeeSetting.monthly.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.monthly.xpath)).sendKeys(employeeSetting.monthly.getDatas().get("common"));
	//payment unit value
	driver.findElement(By.xpath(employeeSetting.paymentUnit_Month.xpath)).click();
	Thread.sleep(2000);	
	//set the 'Commuter Allowance Amount' value
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).clear();
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(employeeSetting.commuterAllowanceAmount.getDatas().get("common"));
	driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).click();
	Thread.sleep(1000);
	if(driver.findElement(By.xpath(employeeSetting.generalRewardMonthlyAmount.xpath)).isEnabled()){
		//set the 'General Reward Monthly Amount' value
		driver.findElement(By.xpath(employeeSetting.generalRewardMonthlyAmount.xpath)).click();
		Thread.sleep(2000);
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).isDisplayed());
		//select the 'Health InsuranceGrade' value
		driver.findElement(By.xpath(employeeSetting.healthInsuranceGrade.xpath)).click();
		//click the 'Select' button on "Index Monday Earning Table" form
		driver.findElement(By.xpath(employeeSetting.selectButton.xpath)).click();
		Thread.sleep(2000);
	}	
	//set the 'Salary *' value
	driver.findElement(By.xpath(employeeSetting.salaryCash.xpath)).click();
	//set the 'Bonus *' value
	driver.findElement(By.xpath(employeeSetting.bonusCash.xpath)).click();
	//click the 'Save' button
	driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
	Thread.sleep(2000);
	//click the 'Close' button
	driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
	Thread.sleep(2000);
}
	
	public static void CompanyAddInsuEmpNoSocialInsu(WebDriver driver) throws Exception {
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//create company  
		createCompanyClient(driver);
		Thread.sleep(2000);
		//forward to the 'Company Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		//go to 'payroll Calculation Basic Information' page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Thread.sleep(2000);
		//set the 'Closing Day' value 
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).sendKeys(clientSetting.closingDay.getDatas().get("common"));
		Thread.sleep(2000);
		// cancel Social Insurance Join
		if(driver.findElement(By.xpath(clientSetting.socialInsuranceJoin.xpath)).getAttribute("checked") != null & driver.findElement(By.xpath(clientSetting.socialInsuranceJoin.xpath)).getAttribute("checked").equals("true")){
			driver.findElement(By.xpath(clientSetting.socialInsuranceJoin.xpath)).click();
			Thread.sleep(2000);				
		}
		// confirm cancel Social Insurance Join
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(2000);
		//save it
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the 'Social Insurance Rate Setting' button
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.perfectureType.xpath)));
		Thread.sleep(2000);
		// click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//set the 'Perfecture Type' value 
		Select sel = new Select (driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get("common"));
		Thread.sleep(2000);
		// click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(1000);
		//click the 'Save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the  'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		// add employee into selected company
		addEmployeeInfoNoInsurance(driver);
		Thread.sleep(3000);
	}

	public static void addEmployeeInfoNoInsurance(WebDriver driver)
			throws Exception {
		helper = new TestHelper();
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'enployee Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click ok button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		// set the 'Employee Code' value
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.employeeCode.xpath)).sendKeys(employeeSetting.employeeCode.getDatas().get("common"));
		// set the 'Family Name' value
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyNameKana.xpath)).sendKeys(employeeSetting.familyNameKana.getDatas().get("common"));
		// set the 'First Name' value	
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstNameKana.xpath)).sendKeys(employeeSetting.firstNameKana.getDatas().get("common"));
		// set the 'Family Name' value
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.familyName.xpath)).sendKeys(employeeSetting.familyName.getDatas().get("common"));
		// set the 'First Name' value
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.firstName.xpath)).sendKeys(employeeSetting.firstName.getDatas().get("common"));
		// set Employee Type 
		Select selEmployeeType = new Select (driver.findElement(By.xpath(employeeSetting.selEmployeeBri.xpath)));
		selEmployeeType.selectByVisibleText(employeeSetting.selEmployeeBri.getDatas().get("common"));
		//set 'SHOWA' 
		Select selEmployeeBri = new Select (driver.findElement(By.xpath(employeeSetting.era.xpath)));
		selEmployeeBri.selectByVisibleText(employeeSetting.era.getDatas().get("common"));	
		// set the 'Year' value
		driver.findElement(By.xpath(employeeSetting.year.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.year.xpath)).sendKeys(employeeSetting.year.getDatas().get("common"));
		// set the 'Month' value
		driver.findElement(By.xpath(employeeSetting.month.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.month.xpath)).sendKeys(employeeSetting.month.getDatas().get("common"));
		// set the 'Day' value
		driver.findElement(By.xpath(employeeSetting.day.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.day.xpath)).sendKeys(employeeSetting.day.getDatas().get("common"));
		//set gender to male
		driver.findElement(By.xpath(employeeSetting.gender_male.xpath)).click();
		// click calculate Income Tax Automatically
		driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).click();
		if(driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).getAttribute("checked") == null){
			driver.findElement(By.xpath(employeeSetting.calculateIncomeTaxAutomatically.xpath)).click();	
		}
		// select Monthly Table Type
		driver.findElement(By.xpath(employeeSetting.monthlyTableType.xpath)).click();
		// set Dependents Number
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.dependentsNumber.xpath)).sendKeys(employeeSetting.dependentsNumber.getDatas().get("common"));
		// click Make Annual Adjustment
		driver.findElement(By.xpath(employeeSetting.makeAnnualAdjustment.xpath)).click();
		// click Payment Cycle Day
		driver.findElement(By.xpath(employeeSetting.paymentCycleM.xpath)).click();
		Thread.sleep(3000);
		// set Monthly value
		driver.findElement(By.xpath(employeeSetting.monthly.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.monthly.xpath)).sendKeys(employeeSetting.monthly.getDatas().get("common"));
		//payment unit value
		driver.findElement(By.xpath(employeeSetting.paymentUnit_Month.xpath)).click();
		Thread.sleep(2000);	
		// set Commuter Allowance Amount value
		driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).clear();
		driver.findElement(By.xpath(employeeSetting.commuterAllowanceAmount.xpath)).sendKeys(employeeSetting.commuterAllowanceAmount.getDatas().get("common"));
		// click Salary Cash
		driver.findElement(By.xpath(employeeSetting.salaryCash.xpath)).click();
		// click Bonus Cash
		driver.findElement(By.xpath(employeeSetting.bonusCash.xpath)).click();
		// click save
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(2000);
		// click close
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
	}

	public static void CompanyAddInsuEmp(WebDriver driver) throws Exception {
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//create company and add societal insurance 
		createCompanyClient(driver);
		Thread.sleep(2000);
		//forward to the 'company Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		Thread.sleep(2000);
		//go to 'payroll Calculation Basic Information' page
		driver.findElement(By.xpath(clientSetting.payrollCaculationBasicInf.xpath)).click();
		Thread.sleep(2000);
		//set the 'closing Day' value 
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.closingDay.xpath)).sendKeys(clientSetting.closingDay.getDatas().get("common"));
		//click the 'Save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(1000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the payment date setting page
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Thread.sleep(2000);
		//set bonus payment Date Month value
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(clientSetting.paymentDateMonth.getDatas().get("common"));
		//set bonus payment Date day value
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(clientSetting.paymentDateDay.getDatas().get("common"));
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).click();
		Thread.sleep(2000);
		//save
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(1000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the 'Social Insurance Rate Setting' button
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(clientSetting.perfectureType.xpath)));
		//click the 'Perfecture Type' button  to get a "sel" variable
		Select sel = new Select (driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		//set the 'Perfecture Type' value 
		sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get("common"));
		Thread.sleep(2000);
		//save the change
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();
		Thread.sleep(1000);
		//click the 'Save' button	
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(3000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//click the  'Close' button
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		addEmployeeInfo(driver);
	}
	
	public static void setBonusPaymentDate2(WebDriver driver) throws Exception{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.serviceList.xpath)).isDisplayed());
		Thread.sleep(2000);
		//forward to the 'client setting page'  page
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		Thread.sleep(1000);
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click OK button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		//go to paymentDateSetting page
		driver.findElement(By.xpath(clientSetting.paymentDateSetting.xpath)).click();
		Thread.sleep(2000);
		//click the 'Edit' button
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		//set the month value
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateMonth.xpath)).sendKeys(clientSetting.paymentDateMonth.getDatas().get("TS1646"));
		//set the day value
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).clear();
		driver.findElement(By.xpath(clientSetting.paymentDateDay.xpath)).sendKeys(clientSetting.paymentDateDay.getDatas().get("TS1646"));
		//save the value
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//click the 'yes' button	
		driver.findElement(By.xpath(clientSetting.yes.xpath)).click();	
		Thread.sleep(2000);
		//click definition
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();	
		Thread.sleep(2000);
		//close it
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
	}

	public static void modifyPrefectureType(WebDriver driver,final String type) throws Exception{
		helper = new TestHelper();
		clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//open client info page
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		Thread.sleep(2000);
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("clientsettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click OK button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
        new WebDriverWait(driver, 30).until(new Function<WebDriver, Boolean>() {public Boolean apply(WebDriver driver){
        	return driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).isDisplayed();
        }});	
		driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
		Thread.sleep(2000);
		//forward to social insurance setting page with edit mode
		while(!Common.isElementPresent(driver, By.xpath(clientSetting.perfectureType.xpath))){
			driver.findElement(By.xpath(clientSetting.socialInsuranceRateSetting.xpath)).click();
			Thread.sleep(3000);
		}
		isNotPending();
		driver.findElement(By.xpath(clientSetting.edit.xpath)).click();
		Thread.sleep(2000);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(clientSetting.perfectureType.xpath)));
		//change prefecture type
		Select sel= new Select(driver.findElement(By.xpath(clientSetting.perfectureType.xpath)));
		sel.selectByVisibleText(clientSetting.perfectureType.getDatas().get(type));
		Thread.sleep(2000);
		//save the change
		driver.findElement(By.xpath(clientSetting.definition.xpath)).click();		
		Thread.sleep(1000);
		//save it
		driver.findElement(By.xpath(clientSetting.save.xpath)).click();
		Thread.sleep(2000);
		//check above type chould be changed correctly
        new WebDriverWait(driver, 30).until(new Function<WebDriver, Boolean>() {public Boolean apply(WebDriver driver){
        	return clientSetting.perfectureType.getDatas().get(type).equals(driver.findElement(By.xpath(clientSetting.perfectureType_dis.xpath)).getAttribute("innerHTML"));
        }});
		Thread.sleep(2000);
		isNotPending();
		//close it
		driver.findElement(By.xpath(clientSetting.close.xpath)).click();
		Thread.sleep(2000);
	}

	public static void modifyEmployeeToRegularEmployee(WebDriver driver) throws Exception{
		helper = new TestHelper();
		employeeSetting = (EmployeeSettingPage) helper.getPage("EmployeeSettingPage");
		topPage = (TopPage) helper.getPage("TopPage");
		//forward to the 'enployee Information'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("employeesettingpage") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click the 'OK' button 
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		//forward to edit mode
		driver.findElement(By.xpath(employeeSetting.edit.xpath)).click();
		Thread.sleep(1000);
		//change Employee Classification to regular employee
		Select sel = new Select (driver.findElement(By.xpath(employeeSetting.selEmployeeBri.xpath)));
		sel.selectByVisibleText(employeeSetting.selEmployeeBri.getDatas().get("regular"));
		Thread.sleep(2000);		
		//click the 'Save' button
		driver.findElement(By.xpath(employeeSetting.save.xpath)).click();
		Thread.sleep(1000);
		//click the 'Close' button
		driver.findElement(By.xpath(employeeSetting.close.xpath)).click();
	}
		
	public static void setAddressPostCode(WebDriver driver) throws Exception{
		helper = new TestHelper();
		annualAdjustment = (AnnualAdjustment) helper.getPage("AnnualAdjustment");
		//forward to the 'annual adjustment'  page
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("annualadjustment") + "\n");
		//type client code to open it
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Payroll_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//click OK button
		Thread.sleep(1000);
		driver.findElement(By.xpath(topPage.popup_Payroll_Ok.xpath)).click();
		driver.findElement(By.xpath(annualAdjustment.annualEmployeeDep.xpath)).click();
		Thread.sleep(3000);
		//check the page open correctly
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(annualAdjustment.postCode1InAnnual.xpath)).isDisplayed());
		//input normal value into post code
	   	driver.findElement(By.xpath(annualAdjustment.postCode1InAnnual.xpath)).clear();
	   	driver.findElement(By.xpath(annualAdjustment.postCode1InAnnual.xpath)).sendKeys(annualAdjustment.postCode1InAnnual.getDatas().get("TS2860_3"));
	   	driver.findElement(By.xpath(annualAdjustment.postCode2InAnnual.xpath)).clear();
	   	driver.findElement(By.xpath(annualAdjustment.postCode2InAnnual.xpath)).sendKeys(annualAdjustment.postCode2InAnnual.getDatas().get("TS2860_3"));
		Thread.sleep(1000);
		//click check button
	   	driver.findElement(By.xpath(annualAdjustment.check.xpath)).click();
	   	Thread.sleep(2000);
	   	//prepare to close current page
   		//click the close button
	   	driver.findElement(By.xpath(annualAdjustment.close.xpath)).click();   
	   	Thread.sleep(2000);
	   	driver.findElement(By.xpath(annualAdjustment.yes.xpath)).click();
		Thread.sleep(2000);
		if(Common.isElementPresent(driver, By.xpath(annualAdjustment.okForAnnual.xpath))){
	   		driver.findElement(By.xpath(annualAdjustment.okForAnnual.xpath)).click();
		   	Thread.sleep(3000);
	   	}
	   	if(Common.isElementPresent(driver, By.xpath(annualAdjustment.yes.xpath))){
	   		driver.findElement(By.xpath(annualAdjustment.yes.xpath)).click();
		   	Thread.sleep(3000);
	   	}

	   	}

	
	public static void createAccountStructure(WebDriver driver) throws Exception{
		helper = new TestHelper();
		topPage = (TopPage) helper.getPage("TopPage");
		accountingStructureList = (AccountingStructureList) helper.getPage("AccountingStructureList"); 
		accountingTemplateAccount = (AccountingTemplateAccount) helper.getPage("AccountingTemplateAccount");
		//forward to the 'Account Structure Maintenance'  page
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("accountStructureMaintenance") + "\n");
		Thread.sleep(3000);
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(accountingStructureList.menu_File.xpath)).isDisplayed());
		String tmp = accountingStructureList.accountStructureNameList.xpath + "[td[2]/div='" + accountingStructureList.accountStructureNameList.getDatas().get("indiStandard") + "']";
		String tmp1 =accountingStructureList.accountStructureNameList.xpath + "[td[2]/div='" + accountingStructureList.accountStructureNameList.getDatas().get("compStandard") + "']";
		boolean indi = false;
		boolean comp = false;
		if(!isElementPresent(driver,By.xpath(tmp))){indi = true;}
		if(!isElementPresent(driver,By.xpath(tmp1))){comp = true;}
		//create individual client or company client if no standard account structure of it
		while(indi||comp){
			//click Create Structure from file menu
			driver.findElement(By.xpath(accountingStructureList.menu_File.xpath)).click();
			driver.findElement(By.xpath(accountingStructureList.menu_File_CreateStructure.xpath)).click();
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(accountingStructureList.popup_Structure_System.xpath)).isDisplayed());
			//click system tab from pop up window
			driver.findElement(By.xpath(accountingStructureList.popup_Structure_System.xpath)).click();
			Thread.sleep(2000);
			//select standard account structure
			String type = "";
			if(indi){
				type = accountingStructureList.popup_Structure_System_List.xpath + "[td[2]/div='" + accountingStructureList.popup_Structure_System_List.getDatas().get("indiStandard") + "']";
			}	
			else if(comp){
				type = accountingStructureList.popup_Structure_System_List.xpath + "[td[2]/div='" + accountingStructureList.popup_Structure_System_List.getDatas().get("compStandard") + "']";
			}			
			driver.findElement(By.xpath(type)).click();
			//click select button
			driver.findElement(By.xpath(accountingStructureList.popup_Select.xpath)).click();
			Thread.sleep(2000);
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(accountingTemplateAccount.currentAssets.xpath)).isDisplayed());
			//click file menu from template account page
			driver.findElement(By.xpath(accountingTemplateAccount.menu_File.xpath)).click();
			//click publish option to save and close this page
			driver.findElement(By.xpath(accountingTemplateAccount.menu_File_StopEditModeAndPublish.xpath)).click();
			Thread.sleep(3000);
			//check create account correctly
			if(indi){
				org.junit.Assert.assertTrue(isElementPresent(driver,By.xpath(tmp)));
				indi = false;
			}
			else if(comp){
				org.junit.Assert.assertTrue(isElementPresent(driver,By.xpath(tmp1)));
				comp = false;
			}			
		}
		driver.findElement(By.xpath(accountingStructureList.close.xpath)).click();
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	
	public static void createCompAccount(WebDriver driver) throws Exception{
		helper = new TestHelper();
		compClientLayout = (CompanyClientLayout) helper.getPage("CompanyClientLayout");
		topPage = (TopPage) helper.getPage("TopPage");
		//check account structure
		createAccountStructure(driver);
		//create company client
		createCompanyClientDetail(driver);
		//forward to the 'Account Basic Information'  page
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("accountBasicInfo") + "\n");
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.popup_Acount_AddClientData.xpath)).isDisplayed());
		//click 'add account data' button
		driver.findElement(By.xpath(topPage.popup_Acount_AddClientData.xpath)).click();
		Thread.sleep(2000);
		//type company code to select it
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Acount_Client_Ok.xpath)).click();
		//check the account basic info page open correctly
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(compClientLayout.code.xpath)));
		//set code
		driver.findElement(By.xpath(compClientLayout.code.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.code.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//set company type		
		Select sel = new Select (driver.findElement(By.xpath(compClientLayout.companyType.xpath)));
		sel.selectByVisibleText(compClientLayout.companyType.getDatas().get("common"));
		Thread.sleep(1000);		   
		//set company name
		driver.findElement(By.xpath(compClientLayout.companyName.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.companyName.xpath)).sendKeys(compClientLayout.companyName.getDatas().get("common"));
		//set company name kana
		driver.findElement(By.xpath(compClientLayout.companyNameKana.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.companyNameKana.xpath)).sendKeys(compClientLayout.companyNameKana.getDatas().get("common"));
		//set short Name
		driver.findElement(By.xpath(compClientLayout.companyShortName.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.companyShortName.xpath)).sendKeys(compClientLayout.companyShortName.getDatas().get("common"));
		//set closing year
		driver.findElement(By.xpath(compClientLayout.closingDate_Year.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.closingDate_Year.xpath)).sendKeys(compClientLayout.closingDate_Year.getDatas().get("common"));
		//set closing month
		driver.findElement(By.xpath(compClientLayout.closingDate_Month.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.closingDate_Month.xpath)).sendKeys(compClientLayout.closingDate_Month.getDatas().get("common"));
		//set closing day
		driver.findElement(By.xpath(compClientLayout.closingDate_Day.xpath)).clear();
		driver.findElement(By.xpath(compClientLayout.closingDate_Day.xpath)).sendKeys(compClientLayout.closingDate_Day.getDatas().get("common"));
		driver.findElement(By.xpath(compClientLayout.closingDate_Month.xpath)).click();
		//set manufacturingCostType_Use
		driver.findElement(By.xpath(compClientLayout.manufacturingCostType_Use.xpath)).click();
		Thread.sleep(2000);
		//click save button
		driver.findElement(By.xpath(compClientLayout.save.xpath)).click();
		Thread.sleep(2000);
		//click ok button to close this page
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(compClientLayout.ok.xpath)));
		driver.findElement(By.xpath(compClientLayout.ok.xpath)).click();
		if(isElementPresent(driver,By.xpath(compClientLayout.ok.xpath),2)){
			driver.findElement(By.xpath(compClientLayout.ok.xpath)).click();
		}
//		driver.findElement(By.xpath(compClientLayout.close.xpath)).click();
//		Thread.sleep(3000);
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
	}
	
	public static void createIndivAccount(WebDriver driver) throws Exception{
		helper = new TestHelper();
		indivClientLayout = (IndivdualClientLayout) helper.getPage("IndivdualClientLayout");
		topPage = (TopPage) helper.getPage("TopPage");
		//check account structure
		createAccountStructure(driver);
		//creat individual client
		createIndiClientDetail(driver);
		//forward to the 'Account Basic Information'  page
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));
		driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
		driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("accountBasicInfo") + "\n");
		org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.popup_Acount_AddClientData.xpath)).isDisplayed());
		//click 'add account data' button
		driver.findElement(By.xpath(topPage.popup_Acount_AddClientData.xpath)).click();
		Thread.sleep(2000);
		//type company code to select it
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetCode.xpath)).clear();
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		driver.findElement(By.xpath(topPage.popup_Acount_Client_SetName.xpath)).click();
		Thread.sleep(2000);
		//click the 'OK' button
		driver.findElement(By.xpath(topPage.popup_Acount_Client_Ok.xpath)).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(indivClientLayout.code.xpath)));
		//set code
		driver.findElement(By.xpath(indivClientLayout.code.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.code.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		//set last name
		driver.findElement(By.xpath(indivClientLayout.lastName.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.lastName.xpath)).sendKeys(indivClientLayout.lastName.getDatas().get("common"));
		//set first name
		driver.findElement(By.xpath(indivClientLayout.firstName.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.firstName.xpath)).sendKeys(indivClientLayout.firstName.getDatas().get("common"));
		//set last name kana
		driver.findElement(By.xpath(indivClientLayout.lastNameKana.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.lastNameKana.xpath)).sendKeys(indivClientLayout.lastNameKana.getDatas().get("common"));
		//set first name kana
		driver.findElement(By.xpath(indivClientLayout.firstNameKana.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.firstNameKana.xpath)).sendKeys(indivClientLayout.firstNameKana.getDatas().get("common"));
		//set trade Name
		driver.findElement(By.xpath(indivClientLayout.tradeName.xpath)).clear();
		driver.findElement(By.xpath(indivClientLayout.tradeName.xpath)).sendKeys(indivClientLayout.tradeName.getDatas().get("common"));
		Thread.sleep(2000);
		//click save button
		driver.findElement(By.xpath(indivClientLayout.save.xpath)).click();
		Thread.sleep(2000);
		//click ok button to close this page
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(indivClientLayout.ok.xpath)));
		driver.findElement(By.xpath(indivClientLayout.ok.xpath)).click();
		Thread.sleep(2000);
		if(isElementPresent(driver,By.xpath(indivClientLayout.ok.xpath),2)){
			driver.findElement(By.xpath(indivClientLayout.ok.xpath)).click();
		}
//		//close it
//		driver.findElement(By.xpath(indivClientLayout.close.xpath)).click();
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(topPage.projectID.xpath)));

	}

	public static void forwardToDepreciationClient(WebDriver driver,String clientTypeXpath)throws Exception{
			helper = new TestHelper();
			clientSetting = (ClientSettingPage) helper.getPage("ClientSettingPage");
			topPage = (TopPage) helper.getPage("TopPage");
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(topPage.projectID.xpath)).isDisplayed());
			driver.findElement(By.xpath(topPage.projectID.xpath)).clear();
			driver.findElement(By.xpath(topPage.projectID.xpath)).sendKeys(topPage.projectID.getDatas().get("dep_basicInfo") + "\n");
			//select created client
		    Thread.sleep(1000);
		    driver.findElement(By.xpath(topPage.popup_Asset_AddAccountData.xpath)).click();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath(clientTypeXpath)).click();
		    Thread.sleep(2000);
		    //select created client
		    driver.findElement(By.xpath(topPage.popup_Asset_Account_SetCode.xpath)).clear();
		    driver.findElement(By.xpath(topPage.popup_Asset_Account_SetCode.xpath)).sendKeys(clientSetting.companyCode.getDatas().get("common"));
		    driver.findElement(By.xpath(topPage.popup_Asset_Account_SetName.xpath)).click();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath(topPage.popup_Asset_Account_Ok.xpath)).click();
		    //check forward to depreciation company client newly register
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(clientSetting.dprcBaseSet.xpath)).isDisplayed());
			org.junit.Assert.assertTrue(driver.findElement(By.xpath(clientSetting.dprcBaseSet.xpath)).getText().contains(clientSetting.dprcBaseSet.getDatas().get("disp")));
		}

	public static int checkCodeRange(String[] selectValue,String firstPath,String inputPath) throws Exception{
		int count = 0;
		int i=0;
		//check all line and compare the code with selectValue
		while(true){
			//click input field to show the drop down list
			driver.findElement(By.xpath(inputPath)).click();
			Thread.sleep(1000);
			i++;
			//check current line is existed in drop down list
			if(Common.isElementPresent(driver, By.xpath(firstPath + "["+ i +"]"))){
				//get the code from current line then compare it with selectValue
				String code = driver.findElement(By.xpath(firstPath + "["+ i +"]")).getText().split("\\\n")[0];
				String name = driver.findElement(By.xpath(firstPath + "["+ i +"]")).getText().split("\\\n")[1];
				for(int j=0;j < selectValue.length;j++){
					if(selectValue[j].equals(code)){
						count ++;
						break;
					}
				}
				//select current line
				driver.findElement(By.xpath(firstPath + "["+ i +"]")).click();
				Thread.sleep(1000);
				//verify current line could be selected
				org.junit.Assert.assertTrue(driver.findElement(By.xpath(inputPath)).getAttribute("value").equals(code + " " + name));
			}
			else{
				i--;
				break;
			}
		}
		if(count == selectValue.length && count == i){
			//drop down list is same with the parameter array
			return 1;
		}
		else if(count < selectValue.length){
			//drop down list do not contain all the parameter array
			return -1;
		}
		else{
			//drop down list is bigger than the parameter array and contain the array
			return 0;
		}		
	}
	
	public static void selectAppointCode(String code,String firstPath,String inputPath,String seldClassName) throws Exception{
	  	//show the drop down list
	  	driver.findElement(By.xpath(inputPath)).click();
	  	Thread.sleep(2000);
	  	//input code
	  	driver.findElement(By.xpath(inputPath)).clear();
	  	driver.findElement(By.xpath(inputPath)).sendKeys(code.substring(0,1));
	  	Thread.sleep(2000);
	  	driver.findElement(By.xpath(inputPath)).sendKeys(code.substring(1));
	  	Thread.sleep(2000);
	  	driver.findElement(By.xpath(inputPath)).click();
	  	//check the code is existed in the drop down list
	  	org.junit.Assert.assertTrue(driver.findElement(By.xpath(firstPath + "[contains(@class,'" + seldClassName + "')]")).getText().contains(code));
	  	driver.findElement(By.xpath(firstPath + "[contains(@class,'" + seldClassName + "')]")).click();
	  	Thread.sleep(2000);
   }
	
	public static void selectAppointCode(String code,String inputPath) throws Exception{
	  	//show the drop down list
	  	driver.findElement(By.xpath(inputPath)).click();
	  	Thread.sleep(2000);
	  	//input code
	  	driver.findElement(By.xpath(inputPath)).clear();
	  	driver.findElement(By.xpath(inputPath)).sendKeys(code.substring(0,1));
	  	Thread.sleep(2000);
	  	driver.findElement(By.xpath(inputPath)).sendKeys(Keys.END);
	  	Thread.sleep(1000);
	  	driver.findElement(By.xpath(inputPath)).sendKeys(code.substring(1));
	  	Thread.sleep(2000);
	  	driver.findElement(By.xpath(inputPath)).click();
	  	driver.findElement(By.xpath(inputPath)).sendKeys(Keys.ENTER);
	    Thread.sleep(2000);
	  	//check select success
	    org.junit.Assert.assertEquals(code,driver.findElement(By.xpath(inputPath)).getAttribute("value").split(" ")[0]);
   }
	
	public static void selectAppointCode2(String code,String inputPath) throws Exception{
	  	//show the drop down list
	  	driver.findElement(By.xpath(inputPath)).click();
	  	Thread.sleep(2000);
	  	//input code
	  	driver.findElement(By.xpath(inputPath)).clear();
	  	driver.findElement(By.xpath(inputPath)).sendKeys(code);
	  	Thread.sleep(2000);
	  	driver.findElement(By.xpath(inputPath)).sendKeys(Keys.ENTER);
	    Thread.sleep(2000);
	  	//check select success
	    org.junit.Assert.assertEquals(code,driver.findElement(By.xpath(inputPath)).getAttribute("value").split(" ")[0]);
   }
			
}