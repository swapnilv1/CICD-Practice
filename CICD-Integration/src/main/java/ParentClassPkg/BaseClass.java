package ParentClassPkg;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

	public static WebDriver driver;
	public static Properties properties;
		
	public static String browser_Type;
	public static String dB_Connection_String;
	public static String dB_User;
	public static String dB_Password;
	public static String input_File_Dir;
	public static String input_File_Name;
	public static String success_Snapshot_File_Dir;
	public static String failure_Snapshot_File_Dir;
	public static String report_File_Dir;
	public static String report_File_Name;
	public static String driver_File_Path;
	public static String chrome_Driver_File_Name;
	public static String application_URL;
	
	public static Connection db_connection =null;
	public BaseClass() 
	{
		
	}
	public void initialize()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\\\Selenium Cases\\\\chromedriver.exe");	
	 	driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://127.0.0.1:1860/PacsToGoWeb/#No-back");
		driver.manage().window().maximize();
	}
	public WebElement findElement(String ele_key)
	{
		WebElement W_el = driver.findElement(By.xpath(properties.getProperty(ele_key)));
		return W_el;
	}
	
}
