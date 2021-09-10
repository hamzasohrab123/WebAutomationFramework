package pageobjectstests;

import browserdriversetup.BrowserDriverSetUp;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.EnterBook;

public class EnterBookTest extends BrowserDriverSetUp {

    EnterBook enterBook = null;

    @BeforeMethod
    public void initializeElements(){
        enterBook = PageFactory.initElements(driver, EnterBook.class);
    }

    @Test
    public void bookTest(){
        enterBook.enterBook();
        String actual = enterBook.bookName();
        Assert.assertEquals(actual, "Sherlock Holmes");

    }
}
