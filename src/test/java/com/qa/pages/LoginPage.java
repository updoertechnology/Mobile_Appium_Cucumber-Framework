package com.qa.pages;

import org.junit.Assert; 
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.utils.TestUtils;
import com.qa.utils.EmailHelper;
import com.qa.utils.GetMobileOTP;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BasePage {
	TestUtils utils = new TestUtils();

	@FindBy (id = "test-Username") 
	@iOSXCUITFindBy (id = "test-Username")
	private WebElement usernameTxtFld;

	@FindBy (id = "test-Password") 
	@iOSXCUITFindBy (id = "test-Password")
	private WebElement passwordTxtFld;
	
	@FindBy(id = "com.koo.app:id/sign_with_email")
	public WebElement lnk_signInWithEmail;
	
	@FindBy(id = "com.koo.app:id/email_edittext")
	public WebElement txtfield_EnterEmailId;
	
	@FindBy(id="com.koo.app:id/phone_no_edittext")
	public WebElement txtfield_EnterMobileNumber;
	
	@FindBy(id="com.koo.app:id/code_tv")
	public WebElement txtfield_MobileCodeDrpDown;
	
	@FindBy(id="com.koo.app:id/search_et")
	public WebElement txtfield_SearchCountry;
	
	@FindBy(id="com.koo.app:id/name_tv")
	public WebElement select_CountryCanada;
	
	@FindBy(id = "com.koo.app:id/email_continue_button")
	public WebElement bttn_Proceed;
	
	@FindBy(id = "com.koo.app:id/phone_no_next_button")
	public WebElement bttn_MobileProceed;
	
	@FindBy(id = "com.koo.app:id/email_otp_editext")
	public WebElement txtfield_EnterOTP;
	
	@FindBy(id ="com.koo.app:id/otp_edit_text")
	public WebElement txtFieldMobileOTP;
	
	@FindBy(id="com.koo.app:id/bt_continue")
	public WebElement bttn_skipContinue;
	
	@FindBy(id = "com.koo.app:id/button_verify")
	public WebElement bttn_Verify;
	
	@FindBy(id = "com.koo.app:id/select_lang_popup")
	public WebElement bttn_LanguageSelection;
	
	@FindBy(id = "com.koo.app:id/vokalLogo_a")
	public WebElement kooLogo;
	
	@FindBy(xpath = "//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='2']")
	public WebElement txt_heading;
	

	public LoginPage(){
	}

	public LoginPage enterUserName(String username) throws InterruptedException {
		clear(usernameTxtFld);	
		sendKeys(usernameTxtFld, username, "login with " + username);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		clear(passwordTxtFld);
		sendKeys(passwordTxtFld, password, "password is " + password);
		return this;
	}
	
	
	 public void waitForLoginScreen() throws InterruptedException {
		 Thread.sleep(6000);
		 waitForVisibility(lnk_signInWithEmail);
	    }
	    
	    public void enterMobileNumber(String coundtryCode, String mobileNum) throws InterruptedException {
	    	Thread.sleep(15000);
	       // clickAndroidDeviceBackBttn();
	    	click(txtfield_MobileCodeDrpDown);
	    	//txtfield_MobileCodeDrpDown.click();
	    	Thread.sleep(6000);
	    	sendKeys(txtfield_SearchCountry, coundtryCode, "Searching Canada Country");
	    	click(select_CountryCanada);
	    	//driver.findElement(By.xpath("//android.widget.TextView[contains(@text(), '"+coundtryCode+"')]")).click();
	    	Thread.sleep(1000);
	    	txtfield_EnterMobileNumber.sendKeys(mobileNum);
	    	bttn_MobileProceed.click();
	    	Thread.sleep(15000);
	    }
	    /*
	    *   Search for images using keywords
	    */
	    public void clickOnSignInWithEmail() throws InterruptedException {
	    	Thread.sleep(15000);
	        //clickAndroidDeviceBackBttn();
	        Thread.sleep(2000);
	    	waitForLoginScreen();
	    	Thread.sleep(5000);
	    	click(lnk_signInWithEmail);
	    	Thread.sleep(5000);
	       
	    }
		
	    public void enterEmailID(String emailuser) throws InterruptedException {
	    	//txtfield_EnterEmailId.clear(); 
//	    	txtfield_EnterEmailId.sendKeys(emailuser+utils.getRandomNumber(1000)+"@gmail.com");
	    	txtfield_EnterEmailId.sendKeys(emailuser+"@gmail.com");
	    	bttn_Proceed.click();
	    	Thread.sleep(8000);
	    	
	    }
	    public void enterReceivedOTPFromEmail(String email, String password, String sub) throws Exception {
	    	String otp = new EmailHelper().getOTPFromEmailText(email, password, sub);
	    	Thread.sleep(15000);
	    	txtfield_EnterOTP.sendKeys(otp);
	    	bttn_Proceed.click();
	    	Thread.sleep(4000);
	    }
	    
	    public void enterReceivedOTPFromMobile() throws Exception {
	    	String otp = new GetMobileOTP().getOTP();
	    	txtFieldMobileOTP.sendKeys(otp);
	    	bttn_Verify.click();
	    	Thread.sleep(6000);
	    }
	    
	    public void verifyUserIsRegistered() throws InterruptedException {
	    	Thread.sleep(3000);
	    	Assert.assertTrue(isElementPresent(bttn_skipContinue));
	    	Thread.sleep(5000);
	    }
	    
	    public void clickOnLanguageSelectionButton() throws InterruptedException {
	    	click(bttn_LanguageSelection);
	    	Thread.sleep(1000);
	    	
	    }
	    
	    public void waitForLoginBttnInPopUp() {
	    	waitForVisibility(bttn_MobileProceed);
	    }
	    
	    public void verifyLogoAndHeading(String headingtext) {
	    	Assert.assertTrue(isElementPresent(kooLogo));
	    	Assert.assertTrue(txt_heading.getText().equalsIgnoreCase(headingtext));
	    }
	    
	    
}
