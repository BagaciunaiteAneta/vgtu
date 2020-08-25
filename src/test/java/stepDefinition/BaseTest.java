package stepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.PropertiesProvider;

public abstract class BaseTest {
	
	WebDriver driver;
	WebDriverWait wait;
	PropertiesProvider propertiesProvider;
	
	
	public BaseTest(String propertiesFileName)
	{
		this.setPropertyProvider(propertiesFileName);
		this.setDriver();
		this.driver = new ChromeDriver();
		this.wait =  new WebDriverWait(this.driver, 12);
	}
	
	public void maximizeWindow()
	{
		this.driver.manage().window().maximize();
	}
	
	public void setPropertyProvider(String propertiesFileName)
	{
		this.propertiesProvider = new PropertiesProvider(propertiesFileName);
	}
	
	public void setDriver()
	{
		System.setProperty("webdriver.chrome.driver", this.propertiesProvider.getProperty("chrome_web_driver_path"));
	}

}
