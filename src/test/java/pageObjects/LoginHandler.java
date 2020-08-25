package pageObjects;

import org.openqa.selenium.WebDriver;

public class LoginHandler extends BaseLoginHandler{
		
	public LoginHandler(WebDriver driver, String loginPage, String propertiesFileName) 
	{
		super(driver, propertiesFileName);
		driver.get(loginPage);
	}
	
	public void loginToElife ()
	{
		this.fillUsername();
		this.selectLoginType();
		this.clickLoginButton();
		this.confirmLogin();
	}
	
	public void loginToBlis()
	{
		this.clickBlisWelcome();
		this.fillUsername();
		this.fillPassword();
		this.clickLoginButton();
	}

}
