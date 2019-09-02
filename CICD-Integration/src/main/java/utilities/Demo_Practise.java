package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import ParentClassPkg.BaseClass;

public class Demo_Practise {

	  static FileInputStream inp_File;
	  //static XSSFWorkbook work_Book;
	  static int first_Call=0;
	  
	public static void read_input_file ()
    {
		FileInputStream file=null;
		try {
			file = new FileInputStream(new File("C:\\\\Users\\\\Swapnil\\\\eclipse-workspace\\\\P2GWebFramework\\\\src\\\\main\\\\java\\\\PropertiesPkg\\\\Report_Format.xlsx"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		XSSFWorkbook workbook=null;
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        XSSFSheet sheet = workbook.getSheet("Project Report");
         
        Object[][] bookData = 
        	{
                {"Head First Java", "Kathy Serria", 40},
                {"Effective Java", "Joshua Bloch", 30},
                {"Clean Code", "Robert martin", 20},
                {"Thinking in Java", "Bruce Eckel", 10},
        	};
 
        int rowCount = 4;
         
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
             
            int columnCount = 3;
             
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
             
        }
         
        FileOutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream("C:\\Users\\Swapnil\\eclipse-workspace\\P2GWebFramework\\src\\main\\java\\PropertiesPkg\\Report_Format.xlsx");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			workbook.write(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        					
		
/*	   XSSFSheet sheet = work_Book.getSheet(sheet_Name);
       
       XSSFRow rows = sheet.getRow(4);
       Cell cell = rows.createCell(3);
       cell.setCellValue((String) "Project Jetblue REsponsive Web Design Modified");
       // This for loop will run on columns of that row
            
       FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx"));
       work_Book.write(outputStream);*/
  }	
	
	
	public static boolean load_properties()
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
		System.out.println(BaseClass.input_File_Dir + "\n" + BaseClass.success_Snapshot_File_Dir);
		return true;
    }
	
	public static boolean[] field_Validations(WebElement element,WebElement val_button,boolean[] checks_Needed)
    {
    	
    	String []inp_String = {"123Abc","Abc123","1234","1234#$","*&1234","abc!@#","!$Abc","@#%","ABCD"};
    	int cnt = 0;
    	boolean op_Result[]= {false,false,false,false,false,false,false,false,false};
    	for(boolean inp : checks_Needed)
    	{
    		if (inp==true)
    		{
    			/*element.sendKeys(inp_String[cnt]);
    	    	val_button.click();
    	    	//Validate Message
    	    	if(message is correct)*/
    	    	op_Result[cnt]=true;	
    		}
    		cnt++;
    	}
    	return op_Result;
    		
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		read_input_file();
	//	Demo_Practise.load_properties();
		
		/*List<String> inp_Lst = new ArrayList<String>();
		//System.out.println("User Dir :"+ System.getProperty("user.dir"));
		//String temp_Path = System.getProperty("user.dir");
		//System.out.println(temp_Path.replace("\\","\\\\"));
		boolean[] checks_Needed={true,true,true,true,true,true,true,true,true};
		WebElement element=null,val_button=null;
		field_Validations(element,val_button,checks_Needed);
		
		
		ArrayList<String[]> inp1_Lst=new ArrayList<String[]>();
		String ar[] = {"Swapnil","Sushil"};
		inp1_Lst.add(0,ar);
		String ar2[]= inp1_Lst.get(0);
		System.out.println(ar2[0]);
		inp_Lst = Demo_Practise.read_input_file ("Search_Flights","Search_Flights",inp_Lst);
		inp_Lst.clear();
		Demo_Practise.read_input_file ("Dont_Search_Flights","Search_Flights",inp_Lst); */
		
		SoftAssert asset = new SoftAssert();
		asset.assertTrue(true);
		asset.assertFalse(true);
		
		
		
		
	}

}
