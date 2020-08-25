package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseLoginHandler {
	
	WebDriverWait wait;
	PropertiesProvider propertiesProvider;
	WebDriver driver;

	
	public BaseLoginHandler(WebDriver driver, String propertiesFileName) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 5);
		this.propertiesProvider = new PropertiesProvider(propertiesFileName);

	}
	
	public void selectLoginType()
	{
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("login_type_selector"))).click();
	}
	
	public void fillUsername() 
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.propertiesProvider.getProperty("login_username_selector"))));
	    this.driver.findElement(By.id(this.propertiesProvider.getProperty("login_username_selector"))).sendKeys(this.propertiesProvider.getProperty("username"));
	}
	
	public void clickLoginButton() 
	{
		WebElement loginButton = this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("login_button_selector")));
		this.wait.until(ExpectedConditions.visibilityOf(loginButton));
		loginButton.click();
	}
	
	public void confirmLogin()
	{
	    String generatedNumber = "11" + driver.findElement(By.className(this.propertiesProvider.getProperty("confirmation_code_selector"))).getText().replaceAll("\\D+", "");
	    this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("password_input_selector"))).sendKeys(generatedNumber);
	    this.driver.findElement(By.name(this.propertiesProvider.getProperty("login_confirm_button_selector"))).click();
	}
	
	
	public void fillPassword()
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.propertiesProvider.getProperty("login_password_selector"))));
	    this.driver.findElement(By.id(this.propertiesProvider.getProperty("login_password_selector"))).sendKeys(this.propertiesProvider.getProperty("password"));
	}
	
	public void clickBlisWelcome()
	{
		WebElement loginButton = this.driver.findElement(By.id(this.propertiesProvider.getProperty("welcome_button_selector")));
		this.wait.until(ExpectedConditions.visibilityOf(loginButton));
		loginButton.click();
	}
	
}
