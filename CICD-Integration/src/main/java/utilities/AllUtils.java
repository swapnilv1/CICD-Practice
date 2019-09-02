package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;

import ParentClassPkg.BaseClass;

public class AllUtils 
{
    WebDriver driver;
    
    FileInputStream inp_File;
    XSSFWorkbook work_Book;
    int first_Call=0;
    
    public AllUtils(WebDriver driver)
	{
		this.driver = driver;
	}
    
    public void initialize()
    {
    	inp_File = null;
				
		try
		{
			inp_File = new FileInputStream(new File(BaseClass.input_File_Dir+BaseClass.input_File_Name));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		 
	    //Create Workbook instance holding reference to .xlsx file
	    work_Book = null;
		try
		{
			work_Book = new XSSFWorkbook(inp_File);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
    }
		
    public List<String> read_input_file (String tc_Name,String sheet_Name,List<String> inp_Lst)
    {
	   int no_Of_Params=0,no_Of_Itr =0;
	   boolean tc_Found=false;
	   
	   inp_Lst = new ArrayList<String>();
       XSSFSheet sheet = work_Book.getSheet(sheet_Name);
       
       //Iterate through each rows one by one
       int totalRow = sheet.getLastRowNum() + 1;
       // count number of active columns in row
       int totalColumn = sheet.getRow(0).getLastCellNum();
       // Create array of rows and column
       // This for loop will run on rows
       boolean end_Of_File = true;
       
       
       for (int i = 0; (i < totalRow && end_Of_File); i++) 
       {
         XSSFRow rows = sheet.getRow(i);
         // This for loop will run on columns of that row
         if( (rows.getCell(0)).getStringCellValue().equals(tc_Name) && !(tc_Found))
         {
        	 tc_Found = true;
         }
         // This for loop will run on columns of that row
         if( tc_Found)
         {
             for (int j = 0; (j < totalColumn && end_Of_File); j++) 
             {
               // get Cell method will get cell
                XSSFCell cell = rows.getCell(j);
                switch (cell.getCellType())
                {
                case NUMERIC:
                	if(no_Of_Params==0)
                	{
                		no_Of_Params =(int)cell.getNumericCellValue();
                		totalColumn = no_Of_Params + 1;
                	}
                	else if (no_Of_Itr==0)
                	{
                	   no_Of_Itr =(int)cell.getNumericCellValue();
                	   totalRow = i + no_Of_Itr + 1;    		   
                	}
                	else
                	    inp_Lst.add(Integer.toString((int) (cell.getNumericCellValue())));
                    break;
                    
                case STRING:
                	if(no_Of_Itr!=0)
                	inp_Lst.add((cell.getStringCellValue()));
                    break;
                    
                case BLANK:
                	if(i +1 != totalRow && j==1)
                	{
                	  end_Of_File=false;
                	}
                    break;
              }
            }
        }
       }
       System.out.println("Size of List : "+ inp_Lst.size()+ "\n Printing List of params :");
      
       System.out.println(inp_Lst); 
       
       return inp_Lst;
  }	
	
    @SuppressWarnings("unchecked")
    public void Fluent_Wait(long timeout)
    {
        Wait<WebDriver> wait = new FluentWait(driver)  
                    .withTimeout(Duration.ofSeconds(timeout))    
                    .pollingEvery(Duration.ofSeconds(2))   
                    .ignoring(NoSuchElementException.class);
    }

    public boolean verify_page_title(String expected_title)
    {
        return (BaseClass.driver.getTitle().equalsIgnoreCase(expected_title));
    }

    public void getScreenShotOnSucess(ITestResult result) 
     {
	  Calendar calendar = Calendar.getInstance();
	  SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

	  String methodName = result.getName();

	  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  try {
	    String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/automation/success_screenshot/";
	    File destFile = new File((String) reportDirectory + methodName + "_" + formater.format(calendar.getTime()) + ".png");

	    FileUtils.copyFile(scrFile, destFile);
	  } catch (IOException e) {
	    e.printStackTrace();
	  }
	}

    public void getScreenShotOnFailure(ITestResult result) 
    {
	  Calendar calendar = Calendar.getInstance();
	  SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

	  String methodName = result.getName();

	  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	  try {
	    String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/automation/faliure_screenshot/";
	    File destFile = new File((String) reportDirectory + methodName + "_" + formater.format(calendar.getTime()) + ".png");

	    FileUtils.copyFile(scrFile, destFile);

	  } catch (IOException e) {
	    e.printStackTrace();
	  }
	}

    public void closure_Activities()
    {
	   try 
       {
		   inp_File.close();
	   } catch (IOException e) 
       {
		 // TODO Auto-generated catch block
	     e.printStackTrace();
	   }
	   try {
		BaseClass.db_connection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
     }
    
    public WebElement locate_Frame_For_Element(String x_Path)
    {
    	List<WebElement> all_Frames = driver.findElements(By.xpath("//iframe"));
    	Iterator<WebElement>itr = all_Frames.iterator();
    	WebElement op_Frame = null;
    	
    	while(itr.hasNext())
    	{
    		op_Frame = itr.next();
    		driver.switchTo().frame(op_Frame);
    		
    		if( (driver.findElements(By.xpath(x_Path)).size()!=0))
    		{	
    			driver.switchTo().defaultContent();
    			return op_Frame;
    		}
    		else
    		{
    			op_Frame=null;
    		}
    	}
    	
   	  driver.switchTo().defaultContent();
	  return op_Frame;
    }
    
    public boolean load_properties()
    {
    	String Base_Path = System.getProperty("user.dir").replace("\\","\\\\");
    	FileInputStream fi = null;
		try {
			fi = new  FileInputStream(Base_Path + "\\src\\main\\java\\PropertiesPkg\\Config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	Properties properties = new Properties();
		try {
			properties.load(fi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		BaseClass.browser_Type = properties.getProperty("Browser_Type");
		BaseClass.dB_Connection_String = properties.getProperty("DB_Connection_String");
		BaseClass.dB_User = properties.getProperty("DB_User");
		BaseClass.dB_Password = properties.getProperty("DB_Password");
		BaseClass.input_File_Dir = Base_Path + (properties.getProperty("Input_File_Dir").replace("\\","\\\\"));
		BaseClass.input_File_Name = properties.getProperty("Input_File_Name");
		BaseClass.success_Snapshot_File_Dir = Base_Path + properties.getProperty("Success_Snapshot_File_Dir").replace("\\","\\\\");
		BaseClass.failure_Snapshot_File_Dir = Base_Path + properties.getProperty("Failure_Snapshot_File_Dir").replace("\\","\\\\");
		BaseClass.report_File_Dir = Base_Path + properties.getProperty("Report_File_Dir").replace("\\","\\\\");
		BaseClass.report_File_Name = properties.getProperty("Report_File_Name");
		BaseClass.driver_File_Path = Base_Path + properties.getProperty("Driver_File_Path").replace("\\","\\\\");
		BaseClass.chrome_Driver_File_Name = properties.getProperty("Chrome_Driver_File_Name");
		BaseClass.application_URL = properties.getProperty("Application_URL");
		return true;
    }
    
    public boolean connect_To_DB()
    {
       try
      { 
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
          
        // Establishing Connection 
         BaseClass.db_connection = DriverManager.getConnection(BaseClass.dB_Connection_String 
         , BaseClass.dB_User, BaseClass.dB_Password); 

        if (BaseClass.db_connection != null)              
            return true;             
        else            
            return false; 
       } 
       catch(Exception e) 
       { 
          System.out.println(e);
          return false;
       } 
      
    }
       
    public ResultSet fetch_Query_Result(String query,ResultSet query_Result)
    {
    	query_Result=null;
    	if (BaseClass.db_connection != null)	 
    	{
    		try {
    			Statement stmt = BaseClass.db_connection.createStatement();
    			query_Result = stmt.executeQuery(query);
    			return query_Result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return query_Result;
			}
    	}
    	return query_Result;
     }
    
    public boolean[] field_Validations(WebElement element,WebElement val_button,boolean[] checks_Needed)
    {
       	String []inp_String = {"123Abc","Abc123","1234","1234#$","*&1234","abc!@#","!$Abc","@#%","ABCD"};
    	int cnt = 0;
    	boolean op_Result[]= {false,false,false,false,false,false,false,false,false};
    	for(boolean inp : checks_Needed)
    	{
    		if (inp==true)
    		{
    			element.sendKeys(inp_String[cnt]);
    	    	val_button.click();
    	    	//Validate Message
    	    	if(/*message is correct*/)
    	    	op_Result[cnt]=true;	
    		}
    		cnt++;
    	}
    	return op_Result;
    		
    }
    
}
