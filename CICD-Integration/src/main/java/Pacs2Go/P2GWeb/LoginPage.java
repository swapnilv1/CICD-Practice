package Pacs2Go.P2GWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ParentClassPkg.BaseClass;

public class LoginPage extends BaseClass 
{
	
	@FindBy(id="txtUserName")
	WebElement usname;
	
	@FindBy(id="txtPassword")
	WebElement passwd;
	
	@FindBy(id="btnlogin")
	WebElement Login;
	
	@FindBy(xpath="//a[text()='Logout']")
	WebElement Logout;
	
	
	public String username;
	
	public WebDriver driver ;
    public LoginPage() {
    	
	
		// TODO Auto-generated constructor stub
	}

    public void login(String uname, String paswd)
    {
    	BaseClass b = new LoginPage();
    	b.initialize();
    	driver = BaseClass.driver;
    	PageFactory.initElements(driver, this);
    	usname.sendKeys(uname);
    	passwd.sendKeys(paswd);
    	Login.click();
    	
    	username = uname;
    }
    public void logout()
    {
    	System.out.println("Logging out ...");
    	Logout.click();
    }
    
    
    
}
