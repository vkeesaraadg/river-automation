package river.steps;

import io.cucumber.java.en.Given;
import river.pages.LoginPage;



public class LoginSteps{
	
	LoginPage loginPage = new LoginPage();
	
	
	@Given("^On Login Screen I logged in to River$")
	public void on_Login_Screen_i_logged_in_to_RiverApp() throws Throwable {
		loginPage.loginIntoRiverApp();
	}
	
}
