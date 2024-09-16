package river.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import river.pages.IntervieweeFormPage;

public class IntervieweeFormSteps {

	private IntervieweeFormPage intervieweeFormPage;

	// Constructor
	public IntervieweeFormSteps() {

		this.intervieweeFormPage = new IntervieweeFormPage();
	}

	@Given("I open the {string} form")
	public void iOpenTheForm(String formLabel) throws Throwable {
		intervieweeFormPage.openForm(formLabel);
	}

	@When("I start the form")
	public void iStartTheForm() throws Throwable {
		intervieweeFormPage.startForm();
	}

	@When("I click the {string} button")
	public void iClickTheButton(String buttonText) throws Throwable {
		intervieweeFormPage.clickButton(buttonText);
	}

	@Then("I scroll to the top of the page")
	public void iScrollToTheTopOfThePage() throws Throwable {
		intervieweeFormPage.scrollToTop();
	}

	@Then("I enter {string} into the Alien Registration Number field")
	public void iEnterIntoTheAlienRegistrationNumberField(String registrationNumber) throws Throwable {
		intervieweeFormPage.enterAlienRegistrationNumber(registrationNumber);
	}

	@Then("I select {string} from the Language combo box")
	public void iSelectFromTheLanguageComboBox(String language) throws Throwable {
		intervieweeFormPage.selectLanguage(language);
	}

}
