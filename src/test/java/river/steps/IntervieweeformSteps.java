package river.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import river.pages.IntervieweeformPage;

public class IntervieweeformSteps {
	IntervieweeformPage intervieweeformPage = new IntervieweeformPage(); 
	
	@Given("User is on the Declaration for interpreted USCIS interview page") 
	public void user_is_on_the_declaration_for_interpreted_uscis_interview_page() throws Throwable {
		
	}

	@When("the user clicks {string}")
	public void the_user_clicks(String string) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//throw new io.cucumber.java.PendingException();
	}

	@Then("the user is redirected to the {string} page") 
	public void the_user_is_redirected_to_the_page(String string) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//throw new io.cucumber.java.PendingException();
	}

}
