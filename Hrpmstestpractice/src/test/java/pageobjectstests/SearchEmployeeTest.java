package pageobjectstests;

import browserdriversetup.BrowserDriverSetUp;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.SearchEmployee;

public class SearchEmployeeTest extends BrowserDriverSetUp {

    SearchEmployee searchemployee = null;

    @BeforeMethod
    public void initializeElements(){
        searchemployee = PageFactory.initElements(driver, SearchEmployee.class);
    }

    @Test
    public void searchEmployeeTest(){
        searchemployee.logIn();
        searchemployee.search();

    }
}
