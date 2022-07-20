package com.qa.pages;

import com.qa.utils.DriverManager; 
import com.qa.utils.GlobalParams;
import com.qa.utils.TestUtils;
import java.time.Duration;

import io.appium.java_client.*;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import java.util.Base64;
import java.util.HashMap;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BasePage {
    private AppiumDriver<?> driver;
    TestUtils utils = new TestUtils();

    public BasePage(){
        this.driver = new DriverManager().getDriver();
       // PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        PageFactory.initElements(this.driver, this);
    }

    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    
    public Boolean isElementPresent(WebElement element) {
    	waitForVisibility(element);
    	
		return element.isDisplayed();
	}
    
    
    public Boolean isEnabled(WebElement element) {
    	Boolean status = false;
    	if(element.isEnabled()) {
    		status =true;
    	}
    	return status;
    }
    
    
    
    
    public Boolean isKeyPadShown() {
    	Boolean status = false;
    	if(driver.getKeyboard()!=null) {
    		status =true;
    	}
    	return status;
	}

@SuppressWarnings({})
    public void waitForVisibility(By e) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.click();
    }

    public void click(By e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        driver.findElement(e).click();
    }

    public void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.sendKeys(txt);
    }

    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }
    
    public WebElement getDynamicVariable(String xpath) {
      //  waitForVisibility(e);
        return driver.findElement(By.xpath(xpath));
    }

    public String getText(WebElement e, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + new GlobalParams().getPlatformName());
        }
        utils.log().info(msg + txt);
        return txt;
    }

    public String getText(By e, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + new GlobalParams().getPlatformName());
        }
        utils.log().info(msg + txt);
        return txt;
    }

    public void closeApp() {
        ((InteractsWithApps) driver).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) driver).launchApp();
    }
    
    public void clickAndroidDeviceBackBttn() {
    	driver.navigate().back();
    }

    public WebElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return (WebElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector()."+ childLocAttr +"(\"" + childLocValue + "\"));");
    }

    public WebElement iOSScrollToElementUsingMobileScroll(WebElement e) {
        RemoteWebElement element = ((RemoteWebElement) e);
        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
     //	  scrollObject.put("direction", "down");
     //	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
     //	  scrollObject.put("name", "test-ADD TO CART");
        scrollObject.put("toVisible", "sdfnjksdnfkld");
        driver.executeScript("mobile:scroll", scrollObject);
        return e;
    }

    public By iOSScrollToElementUsingMobileScrollParent(WebElement parentE, String predicateString) {
        RemoteWebElement parent = (RemoteWebElement)parentE;
        String parentID = parent.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", parentID);
//	  scrollObject.put("direction", "down");
	  scrollObject.put("predicateString", predicateString);
//	  scrollObject.put("name", "test-ADD TO CART");
//        scrollObject.put("toVisible", "sdfnjksdnfkld");
        driver.executeScript("mobile:scroll", scrollObject);
        By m = MobileBy.iOSNsPredicateString(predicateString);
        System.out.println("Mobilelement is " + m);
        return m;
    }

    public WebElement scrollToElement(WebElement element, String direction) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        boolean isFound = false;

        switch (direction) {
            case "up":
                endY = (int) (size.height * 0.4);
                startY = (int) (size.height * 0.6);
                break;

            case "down":
                endY = (int) (size.height * 0.6);
                startY = (int) (size.height * 0.4);
                break;
        }

        for (int i = 0; i < 5; i++) {
            if (find(element, 1)) {
                isFound = true;
                break;
            } else {
                swipe(startX, startY, endX, endY, 1000);
            }
        }
        if(!isFound){
            throw new Exception("Element not found");
        }
        return element;
    }

    public By scrollToElement(By element, String direction) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        boolean isFound = false;

        switch (direction) {
            case "up":
                endY = (int) (size.height * 0.4);
                startY = (int) (size.height * 0.6);
                break;

            case "down":
                endY = (int) (size.height * 0.6);
                startY = (int) (size.height * 0.4);
                break;
        }

        for (int i = 0; i < 3; i++) {
            if (find(element, 1)) {
                isFound = true;
                break;
            } else {
                swipe(startX, startY, endX, endY, 1000);
            }
        }
        if(!isFound){
            throw new Exception("Element not found");
        }
        return element;
    }

    public boolean find(final WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(final By element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver.findElement(element).isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public void swipe(int startX, int startY, int endX, int endY, int millis)
            throws InterruptedException {
        TouchAction t = new TouchAction(driver);
        t.press(point(startX, startY)).waitAction(waitOptions(ofMillis(millis))).moveTo(point(endX, endY)).release()
                .perform();
    }
    
    //Code to Swipe UP
    public void swipeFromUpToBottom()
    {
    try {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    HashMap<String, String> scrollObject = new HashMap<String, String>();
	    scrollObject.put("direction", "up");
	    js.executeScript("mobile: scroll", scrollObject);
	    System.out.println("Swipe up was Successfully done.");
    }
    catch (Exception e){
    	System.out.println("swipe up was not successfull");
    }
    }
   //Code to Swipe DOWN
    public void swipeFromBottomToUp()
    {
    try {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    HashMap<String, String> scrollObject = new HashMap<String, String>();
	    scrollObject.put("direction", "down");
	    js.executeScript("mobile: scroll", scrollObject);
	    System.out.println("Swipe down was Successfully done");
    }
    catch (Exception e)
    {
    System.out.println("swipe down was not successfull");
    }
    }
    
    public void swipeToSeeDownsideElementsOfScreen(int howManySwipes) {
    	Dimension size = driver.manage().window().getSize();
    	// calculate coordinates for vertical swipe
    	int startVerticalY = (int) (size.height * 0.8);
    	int endVerticalY = (int) (size.height * 0.21);
    	int startVerticalX = (int) (size.width / 2.1);
    			try {
    				for (int i = 1; i <= howManySwipes; i++) {
    				new TouchAction<>(driver).press(point(startVerticalX, startVerticalY))
    						.waitAction(waitOptions(java.time.Duration.ofSeconds(2))).moveTo(point(startVerticalX, endVerticalY))
    						.release().perform();
    			}
    		} catch (Exception e) {
    			    				//print error or something
    		}
    		}
    
    public void swipeToSeeUpsideElementsOfScreen(int howManySwipes) {
    	Dimension size = driver.manage().window().getSize();
    	// calculate coordinates for vertical swipe
    	int startVerticalY = (int) (size.height * 0.8);
    	int endVerticalY = (int) (size.height * 0.21);
    	int startVerticalX = (int) (size.width / 2.1);
    			try {
    				for (int i = 1; i <= howManySwipes; i++) {
    					new TouchAction<>(driver).press(point(startVerticalX, endVerticalY))
    							.waitAction(waitOptions(java.time.Duration.ofSeconds(2))).moveTo(point(startVerticalX, startVerticalY))
    							.release().perform();
    			}
    		} catch (Exception e) {
    			    				//print error or something
    		}
    		}
    
    protected String getReferenceImageB64(String filename) throws URISyntaxException, IOException {
      //  URL refImgUrl = getClass().getClassLoader().getResource(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
        //        + File.separator + "resources" + File.separator + "images" + File.separator +filename);
       // URL refImgUrl = getClass().getClassLoader().getResource("/src/test/resources/images/"+filename);
        //utils.log().info("Image Path is" + refImgUrl);
       // File refImgFile = Paths.get(refImgUrl.toURI()).toFile();
        File refImgFile =new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                        + File.separator + "resources" + File.separator + "images" + File.separator +filename);
               return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
    }
    
    public void minimzeAndReopenAppForDuration(int sec) {
    	driver.runAppInBackground(Duration.ofSeconds(sec));
    }
    
    public Boolean isElementVisible(WebElement element) {
    	Boolean status = false;
    	if( element!=null) {
    		status =true;
    	}
    	return status;
    }
    
}