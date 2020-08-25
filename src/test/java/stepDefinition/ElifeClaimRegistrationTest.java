package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.FieldsFiller;
import pageObjects.LoginHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ElifeClaimRegistrationTest extends BaseTest {
	
	String propertiesFileName;
	
	public ElifeClaimRegistrationTest()
	{
		super("config.properties");
		this.propertiesFileName = "config.properties";
		this.maximizeWindow();
	}
	

	@Given("I'm logged-in in IBS")
	public void i_m_logged_in_in_IBS() throws Throwable {	
		LoginHandler loginHandler = new LoginHandler(this.driver, this.propertiesProvider.getProperty("login_page"), this.propertiesFileName);
		loginHandler.loginToElife();
	}

	@And("Opened policy list")
	public void opened_policy_list() throws Throwable {
	
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(this.propertiesProvider.getProperty("policy_list_dropdown_selector"))));
		
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("policy_list_dropdown_selector"))).click();	
		this.driver.findElement(By.id(this.propertiesProvider.getProperty("policy_list_entry_selector"))).click();
	
	}

	@When("Choose policy")
	public void choose_policy() throws Throwable {
		
		this.driver.switchTo().frame(this.propertiesProvider.getProperty("policy_window_iframe_selector"));
		this.driver.findElement(By.linkText(this.propertiesProvider.getProperty("policy_number_link_text"))).click();
	}

	@And("Press create new claim")
	public void press_create_new_claim() throws Throwable {

		this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("new_claim_button_selector"))).click();
	}

	@And("Fill all mandatory fields")
	public void fill_all_mandatory_fields()  throws Throwable {
		
		FieldsFiller filler = new FieldsFiller(this.driver, this.propertiesFileName);
		filler.fillMandatoryClaimFields();
	}

	@Then("Press continue")
	public void press_continue() throws Throwable {
	   
	   this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("register_claim_step1_button"))).click();
	   this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("register_claim_step2_button"))).click();
	}

	@And("Check if the claim was registered")
	public void check_if_the_claim_was_registered() throws Throwable  {
		
	   this.driver.findElement(By.xpath(this.propertiesProvider.getProperty("check_if_claim_registered_selector"))).click();
	   
	}

}
