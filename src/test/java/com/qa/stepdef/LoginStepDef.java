package com.qa.stepdef;

import com.qa.pages.LoginPage;   
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDef {

    @Then("^I Verify user navigate to Login screen$")
    public void userNavigatesToLoginScreen() throws InterruptedException {
        new LoginPage().waitForLoginScreen();
    }

    @Then("^I Click on Sign In With Email option to login$")
    public void clickOnSignInWithEmailLink() throws InterruptedException {
        new LoginPage().clickOnSignInWithEmail();
    }

    @When("^I Enter Email id \"([^\"]*)\" and click on Proceed")
    public void iEnterEmailId(String email) throws InterruptedException {
        new LoginPage().enterEmailID(email);
    }

    @Then("^I Receive Otp from \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"and enter it into OTP Field$")
    public void iReceiveOTPAndEnterIt(String email, String password, String subject) throws Exception {
      new LoginPage().enterReceivedOTPFromEmail(email, password, subject);
    }

    @Then("^I Verify User is registered successfully$")
    public void iVerifyUserIsRegistered() throws InterruptedException {
        new LoginPage().verifyUserIsRegistered();
    }
    
    @Then("^I Enter Mobile country Code \"([^\"]*)\" and Mobile number \"([^\"]*)\" and click on Proceed$")
    public void iSelectCountryCodeAndEnterMobileNum(String countrycode, String mobnumber) throws InterruptedException {
        new LoginPage().enterMobileNumber(countrycode, mobnumber);
    }
    
    @Then("^I Receive Otp from Mobile and enter it$")
    public void iReceiveOTPAndEnterTheSame() throws Exception {
        new LoginPage().enterReceivedOTPFromMobile();
    }
    
    @Then("^I verify Koo logo and \"([^\"]*)\" text should show on top of the enter phone number screen")
    public void iVerifyLogoAndHeadingText(String headingText) {
    	new LoginPage().verifyLogoAndHeading(headingText);
    }
}
