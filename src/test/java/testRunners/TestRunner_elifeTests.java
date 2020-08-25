package testRunners;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
 
	@RunWith(Cucumber.class)
	@CucumberOptions(features="src//test//java//fetures", glue="stepDefinition")
	
public class TestRunner_elifeTests {
	
}
