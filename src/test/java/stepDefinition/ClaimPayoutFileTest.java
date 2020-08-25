package stepDefinition;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.FileParser;
import pageObjects.LoginHandler;
import pageObjects.WebActions;

public class ClaimPayoutFileTest extends BaseTest {
	
	String propertiesFileName;
	WebActions webActions;
	
	String payoutSum;
	
	public ClaimPayoutFileTest()
	{
		super("blisConfig.properties");
		this.propertiesFileName = "blisConfig.properties";
		this.maximizeWindow();
		this.webActions = new WebActions(this.driver);
	}

	@Given("I'm logged in BLIS system")
	public void i_m_logged_in_BLIS()
	{
		LoginHandler loginHandler = new LoginHandler(this.driver, this.propertiesProvider.getProperty("login_page"), this.propertiesFileName);
		loginHandler.loginToBlis();
	}
	
	@And("Open claim list")
	public void open_claim_list()
	{
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("claims_menu_selector"));
		this.webActions.clickButtonById(this.propertiesProvider.getProperty("claims_list_button_selector"));
	}

	@When("I filter claim in AP Approved status")
	public void i_filter_claim_in_AP_Approved_status()
	{
	    this.webActions.selectFromDropdown(this.propertiesProvider.getProperty("claim_status_dropdown_selector"), this.propertiesProvider.getProperty("claim_status"));
	    this.webActions.clickInputButtonById(this.propertiesProvider.getProperty("filer_claims_button_selector"));
	   
	    String claimId = this.webActions.getTextByXpath(this.propertiesProvider.getProperty("claim_id_selector"));
	    this.webActions.setTextById(this.propertiesProvider.getProperty("claim_number_imput_selector"), claimId);
	    this.webActions.clickInputButtonById(this.propertiesProvider.getProperty("filer_claims_button_selector"));
	}

	@And("Check pay out sum and press <Export for pay out>")
	public void check_pay_out_sum_and_press_Export_for_pay_out()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.payoutSum = this.webActions.getTextByXpath(this.propertiesProvider.getProperty("claim_payout_sum_selector"));
		this.webActions.clickInputButtonById(this.propertiesProvider.getProperty("export_claims_button_selector"));
	}

	@Then("In csv file compare sum with {string} cell")
	public void in_csv_file_compare_sum_with_cell(String string) {

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String fileName = "claimsForPayOut" + (new SimpleDateFormat("yyyyMMdd").format(new Date())) + "_" + (new SimpleDateFormat("HHmm").format(new Date())) + "\\d{2}\\_\\d{1,}\\.zip";
		
		File[] files = FileParser.listFilesMatching(new File(this.propertiesProvider.getProperty("download_file_path")), fileName);

		FileParser.extractFolder("" + files[0], this.propertiesProvider.getProperty("download_file_path") + "\\extracted");
						
		String extractedFileName = (new SimpleDateFormat("yyyyMMdd").format(new Date())) + "_CL_TRANSFER_\\d{1,}\\.csv";
		
		File[] extractedFiles = FileParser.listFilesMatching(new File(this.propertiesProvider.getProperty("download_file_path")+ "\\extracted"), extractedFileName);
		
		
		List<String[]> lines = FileParser.readCSVdata("" + extractedFiles[0]);
		
        int lineNo = 1;
        for(String[] line: lines) {
            int columnNo = 1;
            for (String value: line) {                
                if(lineNo == 2 && columnNo == 1) {
                	String[] array = value.split(";");
                	String declaredFilePrice = array[9].replace(" EUR", "");

                	System.out.println("WEBSITE SUM: " + Float.parseFloat(declaredFilePrice));
                	System.out.println("FILE SUM: " + Float.parseFloat(this.payoutSum));

                	if(Float.parseFloat(declaredFilePrice) == Float.parseFloat(this.payoutSum)) {
                    	System.out.println("APPROVED");
                		this.webActions.clickInputButtonById(this.propertiesProvider.getProperty("approve_file_selector"));
                	} else {
                    	System.out.println("DECLINED");
                		this.webActions.clickInputButtonById(this.propertiesProvider.getProperty("decline_file_selector"));
                	}
                }
                
                columnNo++;
            }
            lineNo++;
        }
        
//        FileParser.clearDirectory(this.propertiesProvider.getProperty("download_file_path") + "\\extracted");

	}
	
}
