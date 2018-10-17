package demo;
import java.io.FileReader;
import java.io.Writer;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ddf.EscherColorRef.SysIndexSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

//import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;



public class FunctionLibrary {
	
	public FunctionLibrary()
	{
		//empty constructor to initialize null object
	}
	static String project_dir=System.getProperty("user.dir");
	static String dataConfigFile=project_dir+"\\DataConfigs\\TestData.properties";
	static String xpathConigFile=project_dir+"\\DataConfigs\\Xpath.properties";
	
	public static boolean LaunchUrl(WebDriver driver, String strData, String strXpath, ExtentTest testinfo){
		String strCurrentFunctionName = "LaunchUrl";
		strData=GetDataFromPropertiesFile(strData,dataConfigFile);
		if(strData!=null)
		{
		try{
		    driver.navigate().to(strData);
		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    //testinfo.log(Status.PASS,strCurrentFunctionName);
		    return true;
		   }
	    catch(Exception ex)
			{
	    	System.out.println(ex.getCause());
			}
		}
		else
		{
			System.out.println("No data retrieved");
		}
		//testinfo.log(Status.FAIL,strCurrentFunctionName);
		return false;

}
	public static boolean PauseExecution(WebDriver driver, String strData, String strXpath, ExtentTest testinfo){	
		String strCurrentFunctionName = "PauseExecution";
		if(strData!=null)
		{
		int timeOut=Integer.parseInt(strData);
		try{
		    driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		    //testinfo.log(Status.PASS,strCurrentFunctionName);
		    return true;
		   }
	    catch(Exception ex)
			{
	    	System.out.println(ex.getCause());
			}
		}
		else
		{
			System.out.println("No data retrieved");
		}
		//testinfo.log(Status.FAIL,strCurrentFunctionName);
		return false;
		
	}
	
	public static WebElement WaitForObjectToLoadAndReturnIfExist(WebDriver driver, String strXpath, int intTimeInMillis)throws Exception {
		long t0, t1;
		boolean blnObjectExistFlag=false;
		WebElement elementReturned=null;
		try{
			t0 = System.currentTimeMillis();
			do {
				try
				{
					elementReturned = driver.findElement(By.xpath(strXpath));
					blnObjectExistFlag=true;
				}
				catch(Exception ex)
				{}
				t1 = System.currentTimeMillis();
			} while (((t1 - t0) < intTimeInMillis) && !blnObjectExistFlag);
			
		}catch(Exception ex){
			
		}
		//Logger(objToWriteTxtFile,"I am here now" , elementReturned.toString());
		return elementReturned;
	}
	public static boolean ClickObject(WebDriver driver, String strData, String strXpath, ExtentTest testinfo)
	{
		String strCurrentFunctionName = "ClickObject";
		int intTimeInMillis=50000;
		strXpath=GetDataFromPropertiesFile(strXpath,xpathConigFile);
		if(strData!=null)
		{
		try
		{
			WebElement element=WaitForObjectToLoadAndReturnIfExist(driver,strXpath,intTimeInMillis);
			if(element!=null)
			{
				element.click();
				//testinfo.log(Status.PASS,strCurrentFunctionName);
				return true;
			}
			
		}
		catch(Exception ex)
		{
		System.out.println(ex.getCause());
		}
		}
		else
		{
			System.out.println("No data retrieved");
		}
		//testinfo.log(Status.FAIL,strCurrentFunctionName);
		return false;
	}
	public static String GetDataFromPropertiesFile(String keyName, String path)
	{
		String keyvalue=null;
		try
		{
			FileReader reader=new FileReader(path);
			Properties properties=new Properties();
			properties.load(reader);
			keyvalue=properties.getProperty(keyName);
			return keyvalue;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getCause());
		}
		return keyvalue;
	}
			
	
}
