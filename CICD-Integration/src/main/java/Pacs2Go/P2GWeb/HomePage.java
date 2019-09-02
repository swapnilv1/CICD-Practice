package Pacs2Go.P2GWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	 WebDriver driver ;
	 
	 @FindBy(xpath="//a[Text()='Logout']")
	 WebElement Logout;
	 
	 @FindBy(id="PatientId")
	 WebElement Patientid;
	 
	 @FindBy(xpath="//a[text()='Setting']")
	 WebElement settingLink;
	 
	 @FindBy(id="Title")
	 WebElement titletxt;
	 
	 public static String patient_id;
	 
	 public HomePage(WebDriver driver){
		
		this.driver = driver;
		// TODO Auto-generated constructor stub
		PageFactory.initElements(this.driver, this);
	}
	
	//Local AE Operations
	
	public void populate_info(String patid)
	{
		///Logout.click();
		
		Patientid.sendKeys(patid);
		patient_id = patid;
	}
	
	public void validate_Setting_Page(String title)
	{
		settingLink.click();
		PageFactory.initElements(driver, this);
		titletxt.sendKeys(title);
		
	}
	
	
}
