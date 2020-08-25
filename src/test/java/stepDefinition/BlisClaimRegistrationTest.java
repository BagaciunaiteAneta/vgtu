package stepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.FieldsFiller;
import pageObjects.LoginHandler;
import pageObjects.WebActions;

public class BlisClaimRegistrationTest extends BaseTest {

	String propertiesFileName;
	WebActions webActions;
	FieldsFiller filler;
	String personalId;
	
	public BlisClaimRegistrationTest()
	{
		super("blisConfig.properties");
		this.propertiesFileName = "blisConfig.properties";
		this.maximizeWindow();
		this.webActions = new WebActions(this.driver);
		this.filler = new FieldsFiller(this.driver, this.propertiesFileName);
	}
	
	@Given("I'm logged-in in BLIS")
	public void i_m_logged_in_in_BLIS() {
		LoginHandler loginHandler = new LoginHandler(this.driver, this.propertiesProvider.getProperty("login_page"), this.propertiesFileName);
		loginHandler.loginToBlis();
	}
	
	@And("Open policy list")
	public void open_policy_list()
	{
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("poliecies_menu_selector"));
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("agreement_list_selector"));
	}

	@When("Choose policy with Trauma cover")
	public void choose_policy_with_Trauma_cover()
	{
		this.filler.fillBlisPolicyFilter();
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("filter_button_selector"));
		this.webActions.clickButtonByClassName(this.propertiesProvider.getProperty("policy_number_selector"));
	}

	@When("Press create New Claim application")
	public void press_create_New_Claim_application()
	{
		this.personalId = this.webActions.getTextByXpath(this.propertiesProvider.getProperty("personal_id_selector"));
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("new_claim_application_button_selector"));
	}

	@When("Fill all mandatory fields and press <Save>")
	public void fill_all_mandatory_fields_and_press_Save()
	{
		this.filler.fillMandatoryBlisClaimFields(this.personalId);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement submit = this.driver.findElement(By.id(this.propertiesProvider.getProperty("claim_save_selector")));
		Actions actions = new Actions(this.driver);
		actions.moveToElement(submit).click().perform();
	}

	@When("Press <Edit\\/Investigate>")
	public void press_Edit_Investigate()
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(this.propertiesProvider.getProperty("edit_claim_button_selector"))));
		WebElement submit = this.driver.findElement(By.name(this.propertiesProvider.getProperty("edit_claim_button_selector")));
		Actions actions = new Actions(this.driver);
		actions.moveToElement(submit).click().perform();
	}

	@Then("I fill additional information and press <Approve>")
	public void i_fill_additional_information_and_press_Approve()
	{
		webActions.setTextById(this.propertiesProvider.getProperty("diagnosis_field_selector"), "A");
		webActions.setTextById(this.propertiesProvider.getProperty("comments_field_selector"), "B");
		webActions.setTextById(this.propertiesProvider.getProperty("comments_on_corrections_selector"), "C");
	}

	@Then("Check if the claim was registered and is in correct status")
	public void check_if_the_claim_was_registered_and_is_in_correct_status()
	{
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(this.propertiesProvider.getProperty("approve_claim_button_selector"))));
		WebElement submit = this.driver.findElement(By.name(this.propertiesProvider.getProperty("approve_claim_button_selector")));
		Actions actions = new Actions(this.driver);
		actions.moveToElement(submit).click().perform();
	}
}
