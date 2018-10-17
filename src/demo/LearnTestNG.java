package demo;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import demo.FunctionLibrary;
import demo.HelperScript;

public class LearnTestNG {
	
   public static FunctionLibrary action;
   public static String sActionKeyword;
   public static Method method;
   //public static Logger log=Logger.getLogger(clazz);
   
   String drive="Chrome";
   String project_dir=System.getProperty("user.dir");
   String scPath=project_dir+"\\DataSheets\\Scenario.xls";
   String tcPath=project_dir+"\\DataSheets\\TestCase.xls";
   String testFlowPath=project_dir+"\\DataSheets\\FlowSheet.xls";
   String[] TestDetails;
   String strDateTimeStamp;
   
   public WebDriver driver;
   
   ArrayList<String> ScList = new ArrayList<>();
   ArrayList<String> TcList = new ArrayList<>();
   ArrayList<String> TcStep = new ArrayList<>();
   Properties prop = new Properties();
   OutputStream output = null;
   
   SimpleDateFormat formatter;
   
   ExtentReports reports;
   ExtentTest testinfo;
   ExtentHtmlReporter reporter;
   
   File fileScreenshot, fileDestination;
   

   public LearnTestNG() throws NoSuchMethodException, SecurityException{
	   try{
		 //This will load all the methods of the class 'FunctionLibrary' in it.
	     //action = new FunctionLibrary();
         //It will be like array of method, use the break point here and do the watch
	     
	       }
	   catch(Exception ex)
	   {
		   System.out.println(ex.getCause());
		   System.out.println("TERMINATING EXECUTION!!!!");
		   System.exit(0);
	   }
	}
    
	@BeforeTest
	public void setup()
	{
		formatter = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");
    	strDateTimeStamp = formatter.format(new Date());
    	System.out.println("Date Time Formatting completed");
    	try
    	{
    		File TestResultFolder = new File(project_dir+"\\TestResults\\"+strDateTimeStamp);
    		TestResultFolder.mkdir();
    		System.out.println("Result Folder Created Successfully");
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Error in creating result folder");
    		ex.printStackTrace();
    	}
    	
		System.out.println("Execution Started......");
	}
	
	@Test(priority=1)
	public void execution() throws Exception
	{
		
		try {
			switch(drive)
			{
				case "Chrome":
					System.setProperty("webdriver.chrome.driver", project_dir+"\\WebDrivers\\chromedriver.exe");
					driver= new ChromeDriver();
					driver.manage().window().maximize();
					break;
					
				case "Firefox":
					System.setProperty("webdriver.gecko.driver",project_dir+"\\WebDrivers\\geckodriver.exe");
			        FirefoxOptions options = new FirefoxOptions();
					options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe"); //Location where Firefox is installed
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
					capabilities.setCapability("moz:firefoxOptions", options);
					driver=new FirefoxDriver();
					driver.manage().window().maximize();
					break;
					
				case "IE":
					System.setProperty("webdriver.ie.driver", project_dir+"\\WebDrivers\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
					break;
					}
        } 
        catch (Exception e)
		{
        	System.out.println(e.getMessage());
        	System.out.println("Unable to launch browser..");
        	System.out.println("TERMENATING NOW!!!");
        	System.exit(0);
        } 
		action = new FunctionLibrary();
		ScList=HelperScript.GetScenarioList(scPath, "Scenario");
		System.out.println("Number of Scenarios detected:: "+ScList.size());
		
		if(ScList.size()>0)
		{   
			
			for (String ScName : ScList)  
			{  
				String ScstrDateTimeStamp=formatter.format(new Date());
				File SCResultFolder = new File(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp);
				SCResultFolder.mkdir();
	    		System.out.println("Result Folder Created Successfully for scenario:: "+ScName);
				System.out.println("Executing Scenario:: "+ScName);
				TcList=HelperScript.GetTestCaseList(tcPath, "TestCase", ScName);
				if(TcList.size()>0)
				{
					for(String TcName : TcList)
					{
						System.out.println("Executing TestCase:: "+TcName+" for Scenario:: "+ScName);
						String TcstrDateTimeStamp=formatter.format(new Date());
						File TCResultFolder = new File(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp+"\\"+"TestCase_"+TcName+"_"+TcstrDateTimeStamp);
						TCResultFolder.mkdir();
						//System.out.println(TcName);
						File TCScreenshotFolder = new File(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp+"\\"+"TestCase_"+TcName+"_"+TcstrDateTimeStamp+"\\"+"Screenshots");
						TCScreenshotFolder.mkdir();
						reporter=new ExtentHtmlReporter(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp+"\\"+"TestCase_"+TcName+"_"+TcstrDateTimeStamp+"\\"+"AutomationReports_"+TcName+TcstrDateTimeStamp+".html");
						reporter.loadXMLConfig(new File(project_dir+"/extent-config.xml"));
						reports=new ExtentReports();
						reports.setSystemInfo("Environment", "QA");
						reports.attachReporter(reporter);
						testinfo=reports.createTest(strDateTimeStamp);

						System.out.println("Result Folder Created Successfully for Test Case:: "+TcName);
						
						TcStep=HelperScript.GetTestCaseSteps(testFlowPath,"TestFlow",TcName);
						if(TcStep.size()>0)
						{
							int stpCount=1;
							for(String Step : TcStep)
							{
								
								TestDetails=Step.split(Pattern.quote("||"));
								try
								{
									System.out.println("Trying to execute step"+stpCount+":: "+TestDetails[0]);
									System.out.println("Step Description:: "+TestDetails[1]);
									Method execute= FunctionLibrary.class.getMethod(TestDetails[0], WebDriver.class, String.class, String.class, ExtentTest.class);
									boolean flag=(boolean) execute.invoke(action, driver, TestDetails[3], TestDetails[2], testinfo);
									if(flag==true)
									{
										System.out.println("EXECUTED SUCCESSFULLY!!");
										testinfo.log(Status.PASS,TestDetails[0]);
										stpCount=stpCount+1;
									}
									else
									{
										System.out.println("COULD NOT EXECUTE!!");
										String FailstrDateTimeStamp=formatter.format(new Date());
										fileScreenshot = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
										fileDestination=new File(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp+"\\"+"TestCase_"+TcName+"_"+TcstrDateTimeStamp+"\\"+"Screenshots"+"\\"+"Step"+stpCount+"_"+FailstrDateTimeStamp+".png");
										FileUtils.copyFile(fileScreenshot, fileDestination);
										//testinfo.log(Status.FAIL,TestDetails[0]);
										//testinfo.addScreenCaptureFromPath(fileDestination.getAbsolutePath()).log(Status.FAIL,TestDetails[0]);
										//String img=testinfo.addScreenCaptureFromPath(fileDestination.getAbsolutePath());
										//testinfo.log(Status.FAIL,TestDetails[0],);
										//testinfo.log(Status.FAIL,testinfo.addScreenCaptureFromPath(fileDestination.getAbsolutePath()));
										MediaEntityModelProvider mediaModel=MediaEntityBuilder.createScreenCaptureFromPath(project_dir+"\\TestResults\\"+strDateTimeStamp+"\\"+"Scenario_"+ScName+"_"+ScstrDateTimeStamp+"\\"+"TestCase_"+TcName+"_"+TcstrDateTimeStamp+"\\"+"Screenshots"+"\\"+"Step"+stpCount+"_"+FailstrDateTimeStamp+".png").build();
										testinfo.fail(TestDetails[0], mediaModel);
										stpCount=stpCount+1;
									}
								}
								catch(Exception ex)
								{
									ex.printStackTrace();
								}
							}
							reports.flush();
							//reports.
						}
						else
						{
							System.out.println("No Test Step found to execute for TestCase:: "+TcName);
						}
					}
				}
				else
				{
					System.out.println("No Test Case found to execute for Scenario:: "+ScName);
				}
			} 
			reports.flush();
			
		}
		else
		{
			System.out.println("No Scenarios found to execute...");
			System.exit(0);
		}
		
        
	}
	@AfterTest
	public void cleanUp()
	{
		driver.quit();
		
	}

	
}
