package pageObjects;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class FieldsFiller {
	
	PropertiesProvider propertiesProvider;
	WebDriver driver;
	WebDriverWait wait;
	
	public FieldsFiller(WebDriver driver, String propertiesFileName)
	{
		this.propertiesProvider = new PropertiesProvider(propertiesFileName);
		this.driver = driver;
		this.wait =  new WebDriverWait(this.driver, 15);
	}
	
	public void fillMandatoryClaimFields()
	{
	    this.driver.findElement(By.id(this.propertiesProvider.getProperty("event_dropdown_selector"))).click();
	    // checking values in the dropdown
		
	    String[] claimType = {"","Mirtis","Laikinas nedarbingumas","Kritinës ligos","Trauma","Priverstinis nedarbas","Neágalumas","Sutarties pasibaigimas"};
	    WebElement dropdownClaim = driver.findElement(By.id(this.propertiesProvider.getProperty("event_dropdown_selector")));  
	    Select select = new Select(dropdownClaim);  

	    List<WebElement> options = select.getOptions();  
	    for(WebElement we:options)  
	    {  
	     boolean match = false;
	     for (int i=0; i<claimType.length; i++){
	         if (we.getText().equals(claimType[i])){
	           match = true;
	         }
	       }
	     Assert.assertTrue( we.getText(),match);
	    }  
	    
	    select.selectByVisibleText(this.propertiesProvider.getProperty("injury"));
		this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_date_selector"))).sendKeys(this.propertiesProvider.getProperty("injury_event_date_data"));
		this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_name_selector"))).sendKeys(this.propertiesProvider.getProperty("injury_event_name_data"));
		this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_document_button_selector"))).click();
		this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_hospital_input_selector"))).sendKeys(this.propertiesProvider.getProperty("injury_event_hospital_input_data"));
	
	   WebElement dropdownBankAcc  = this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_bank_location_dropdown_selector")));  
	    Select selectBank = new Select(dropdownBankAcc);  
	    selectBank.selectByVisibleText(this.propertiesProvider.getProperty("injury_event_bank_location_data"));
	    this.driver.findElement(By.name(this.propertiesProvider.getProperty("injury_event_bank_account_numbers_selector"))).sendKeys(this.propertiesProvider.getProperty("injury_event_bank_account_numbers_data"));
	}
	
	public void fillBlisPolicyFilter()
	{
		this.selectBlisPolicyProductCode()
				.setBlisFilterStartDate()
				.setBlisFilterPolicyStatus()
				.resetFiltSubStatus()
				.selectFilterSubStatuses()
				.saveFilterSelection();
	}
	
	private FieldsFiller selectBlisPolicyProductCode()
	{
		this.selectFromDropdown(this.propertiesProvider.getProperty("product_code_dropdown_selector"), this.propertiesProvider.getProperty("product_code"));
	    return this;
	}

	private FieldsFiller setBlisFilterStartDate()
	{
	    WebElement startDateInput = driver.findElement(By.id(this.propertiesProvider.getProperty("start_date_input_selector")));
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH, -8);
	    
	    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String timeString = formatter.format(calendar.getTime());
	    
	    startDateInput.sendKeys(timeString);
	    return this;
	}
	
	private FieldsFiller setBlisFilterPolicyStatus()
	{
		this.selectFromDropdown(this.propertiesProvider.getProperty("policy_status_dropdown_selector"), this.propertiesProvider.getProperty("policy_status"));
	    return this;
	}
	
	private FieldsFiller resetFiltSubStatus()
	{
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("open_selection_button_selector"))).click();
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.propertiesProvider.getProperty("reset_selections_button_selector"))));
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("reset_selections_button_selector"))).click();
		return this;
	}
	
	private FieldsFiller selectFilterSubStatuses()
	{
		List<String> selectionArray = this.propertiesProvider.getPropertiesAsArray("selection");
		
        for (String id : selectionArray) {
    		WebElement checkbox = this.driver.findElement(By.id(id));
    		Actions actions = new Actions(this.driver);
    		actions.moveToElement(checkbox).click().perform();
        }

		return this;
	}
	
	private void saveFilterSelection()
	{
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("save_selection_button_selector"))).click();
	}
	
	private void selectFromDropdown(String selector, String selectText)
	{
	    WebElement dropdown = driver.findElement(By.id(selector));  
	    Select dropdownSelector = new Select(dropdown); 
	    dropdownSelector.selectByVisibleText(selectText);
	}
	
	public void fillMandatoryBlisClaimFields(String personalId)
	{
		WebActions webActions = new WebActions(this.driver);
		
		webActions.selectFromDropdown(this.propertiesProvider.getProperty("claim_event_type_selector"), this.propertiesProvider.getProperty("claim_event"));
		webActions.selectFromDropdown(this.propertiesProvider.getProperty("insurance_event_type_selector"), this.propertiesProvider.getProperty("claim_event"));

	    Calendar calendar = Calendar.getInstance();
	    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
	    
	    String now = formatter.format(calendar.getTime());

		webActions.setTextById(this.propertiesProvider.getProperty("customer_registration_date_selector"), now);
		webActions.setTextById(this.propertiesProvider.getProperty("notification_date_selector"), now);
		
	    calendar.add(Calendar.MONTH, -1);	    
	    String timeString = formatter.format(calendar.getTime());
		
		webActions.setTextById(this.propertiesProvider.getProperty("insurance_event_date_selector"), timeString);
		webActions.setTextById(this.propertiesProvider.getProperty("event_date_selector"), timeString);
		
		webActions.setTextById(this.propertiesProvider.getProperty("receiver_selector"), personalId);
		webActions.clickButtonById(this.propertiesProvider.getProperty("add_receiver_selector"));
	}
	
	
}
