package pageobjectstests;

import browserdriversetup.BrowserDriverSetUp;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;

public class HomePageTest extends BrowserDriverSetUp {

    HomePage homePage = null;

    @BeforeMethod
    public void initializeElements() {
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test
    public void LogInTest(){
        homePage.logIn();
    }

    @Test
    public void bannerTest(){
        homePage.logIn();
        String actualVal = homePage.findBanner();
        String expectedValue = "Izaan Human Resource Management System";
        Assert.assertEquals(actualVal, expectedValue);
    }

}
