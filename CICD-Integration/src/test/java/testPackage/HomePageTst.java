package testPackage;
/* Change on Github */
import org.testng.annotations.Test;

import Pacs2Go.P2GWeb.HomePage;
import ParentClassPkg.BaseClass;

public class HomePageTst {
	public static HomePage hmp;
	
	@Test(groups="PositiveEndtoEnd", priority=1)
	public void pop_info()
	{
		hmp = new HomePage(BaseClass.driver);
		hmp.populate_info("abcd");
	}
	
	@Test(groups="PositiveEndtoEnd", priority=2)
	public void pop_info2()
	{
		hmp.validate_Setting_Page("DVD200");
		System.out.println("Value is given as :" + LoginPageTst.Lp.username);
	}
	@Test(groups= {"BPositive"}, priority=1)
	public void neg_logout()
	{
		LoginPageTst.Lp.logout();
	}
	
		
}
