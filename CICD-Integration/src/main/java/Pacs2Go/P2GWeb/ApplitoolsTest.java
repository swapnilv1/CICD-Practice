package Pacs2Go.P2GWeb;

	import com.applitools.eyes.selenium.Eyes;
	import com.applitools.eyes.RectangleSize;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
	import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
	 
	public class ApplitoolsTest {
	 
	    public static void main(String[] args) throws URISyntaxException, InterruptedException {
	 
	    	System.setProperty("webdriver.chrome.driver", "C:\\Selenium Cases\\chromedriver_win32\\chromedriver.exe");	
	 	    WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 
	        Eyes eyes = new Eyes();
	        // This is your api key, make sure you use it in all your tests.
	        eyes.setApiKey("hJ8100CKdVORSeuW111OGsZ5C5oQQwLwrRlgU1ZxLhZD9fM110");
	 
	        try {
	            // Start visual testing with browser viewport set to 1024x768.
	            // Make sure to use the returned driver from this point on.
	            driver = eyes.open(driver, "Applitools", "Test Web Page", new RectangleSize(800, 600));
	 
	            driver.get("http://applitools.com");
	 
	            // Visual validation point #1
	            eyes.checkWindow("Main Page");
	 
	            //driver.findElement(By.cssSelector(".features>a")).click();
	 
	            // Visual validation point #2
	            //eyes.checkWindow("Features page");
	 
	            // End visual testing. Validate visual correctness.
	            eyes.close();
	        } finally {
	            // Abort test in case of an unexpected error.
	            eyes.abortIfNotClosed();
	            driver.close();
	        }
	    }
}
