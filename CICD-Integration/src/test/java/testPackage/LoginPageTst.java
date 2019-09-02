package testPackage;

import org.testng.annotations.Test;

import Pacs2Go.P2GWeb.LoginPage;

public class LoginPageTst {
	
	public static LoginPage Lp;
	
	@Test(groups= {"BPositive","PositiveEndtoEnd"})
	public void Neg_login()
	{
		Lp = new LoginPage();
		Lp.login("admin", "admin");
	}
	
	@Test(groups="PositiveEndtoEnd", priority=3)
	public void base_logout()
	{
		Lp.logout();
	}
	
}
