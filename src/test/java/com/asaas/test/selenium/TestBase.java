package com.asaas.test.selenium;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class TestBase
{
	//initilaze the property file
	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static WebDriver dr = null;
	public static EventFiringWebDriver driver = null;
	public static boolean isLoggedIn = false;

	@BeforeClass
	public static void beforeClass() throws Exception
	{
		initialize();
		login();
	}

	@AfterClass
	public static void afterClass() throws Exception
	{
		logout();
		Thread.sleep(3000);
		quit();
	}
	
	public static void initialize() throws Exception
	{
		if ( driver == null) {
			//config property file
			CONFIG = new Properties();
			FileInputStream fn =new FileInputStream(System.getProperty("user.dir")+"//src//config//CONFIG.properties");
			CONFIG.load(fn);
			
			//OR propperty
			OR = new Properties();
	        fn =new FileInputStream(System.getProperty("user.dir")+"//src//config//OR.properties");
			OR.load(fn);
			
			//Initalize web driver and event firing
			//WebDriver dr = null;
			if (CONFIG.getProperty("browser").equals("Firefox")) {
				FirefoxProfile profile = new FirefoxProfile();
		        profile.setPreference("network.websocket.enabled",false);
		        dr = new FirefoxDriver(profile); 
			} else if (CONFIG.getProperty("browser").equals("IE")) {
				dr = new InternetExplorerDriver();
			} 
			
			if (dr != null) {   
				 driver = new EventFiringWebDriver(dr);
				 driver.manage().timeouts().implicitlyWait(30l,TimeUnit.SECONDS);
				 driver.manage().window().maximize();
			}
		}
	}
	
	protected static void quit() throws Exception
	{
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		if (dr != null) {
			dr.quit();
			dr = null;
		}
		
		OR = null;
		CONFIG = null;
	}
	
	private static void login() throws Exception
	{
		if (!isLoggedIn) {
				driver.get(CONFIG.getProperty("testSiteName"));
				String username_1 = CONFIG.getProperty("UserName_1");
				String password_1 = CONFIG.getProperty("Password_1");
				getObject("username_input").sendKeys(username_1);
				getObject("password_input").sendKeys(password_1);
				getObject("login_button").click();
				Thread.sleep(3000);
			isLoggedIn = getObject("logout_button") != null;
			if (!isLoggedIn ){
				driver.get(CONFIG.getProperty("testSiteName"));
				String username_2 = CONFIG.getProperty("UserName_2");
				String password_2 = CONFIG.getProperty("Password_2");
				getObject("username_input").sendKeys(username_2);
				getObject("password_input").sendKeys(password_2);
				getObject("login_button").click();
				Thread.sleep(30000);
		        isLoggedIn = getObject("logout_button") != null;
				}
			if (!isLoggedIn ){
				driver.get(CONFIG.getProperty("testSiteName"));
				String username_3 = CONFIG.getProperty("UserName_3");
				String password_3 = CONFIG.getProperty("Password_3");
				getObject("username_input").sendKeys(username_3);
				getObject("password_input").sendKeys(password_3);
				getObject("login_button").click();
				Thread.sleep(30000);
		        isLoggedIn = getObject("logout_button") != null;
				}
		}     
	}

	public static void logout() throws Exception
	{
		if (isLoggedIn) 
		{
			if(getObject("Return_Dashboard_button") != null) 
			{
				getObject("Return_Dashboard_button").click(); 
				Thread.sleep(8000);
			}

			if(getObject("logout_button") != null)
			{
				getObject("logout_button").click();
				Thread.sleep(7000);
			}
			else
				driver.get(CONFIG.getProperty("testSiteName") + "/sessionExpire");
			
			isLoggedIn = false;
		}
	}
	
	public static WebElement getObject(String xpathvalKey)
	{
		try {
			return driver.findElement(By.xpath(OR.getProperty(xpathvalKey)));
		} catch (Throwable t) {
			//report an error 
			return null;
		}
	}
	public static void assertTrue(String string, boolean elementPresent)
	{
		// TODO Auto-generated method stub
		
	}
	
	protected boolean isElementPresent(By by) 
	{
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) 
		{
			return false;
	    }
	}	
}
