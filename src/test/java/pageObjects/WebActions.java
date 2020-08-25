package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebActions {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public WebActions(WebDriver driver)
	{
		this.driver = driver;
		this.wait =  new WebDriverWait(this.driver, 25);
	}
	
	public void clickButtonById(String selector)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector)));
		this.driver.findElement(By.id(selector)).click();
	}
	
	public void clickButtonByName(String selector)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(selector)));
		this.driver.findElement(By.name(selector)).click();
	}
	
	public void clickButtonByClassName(String selector)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(selector)));
		this.driver.findElement(By.className(selector)).click();
	}
	
	public String getTextByXpath(String selector)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
		return this.driver.findElement(By.xpath(selector)).getText();
	}
	
	public void selectFromDropdown(String selector, String selectText)
	{
	    WebElement dropdown = driver.findElement(By.id(selector));  
	    Select dropdownSelector = new Select(dropdown); 
	    dropdownSelector.selectByVisibleText(selectText);
	}
	
	public void setTextById(String selector, String value)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector)));
		this.driver.findElement(By.id(selector)).sendKeys(value);
	}
	
	public void clickInputButtonById(String selector)
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector)));
		WebElement submit = this.driver.findElement(By.id(selector));
		Actions actions = new Actions(this.driver);
		actions.moveToElement(submit).click().perform();
	}

}
