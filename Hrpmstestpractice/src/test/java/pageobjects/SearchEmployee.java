package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchEmployee {
    @FindBy(how = How.ID, using = "exampleInputEmail")
    WebElement email;
    @FindBy(how = How.ID, using = "exampleInputPassword")
    WebElement password;
    @FindBy(how = How.CSS, using = "body > div > div > div > div > div > div > div > div > form > button")
    WebElement login;
    @FindBy(how = How.CSS, using = "#accordionSidebar_PIMS > a")
    WebElement dropdown;
    @FindBy(how = How.CSS, using = "#collapse_PIMS")
    WebElement allemployees;
    @FindBy(how = How.CSS, using = "#allEmployeeListTable_filter > label > input")
    WebElement search;

    public void logIn(){
        email.sendKeys("admin");
        password.sendKeys("123456");
        login.click();

    }

    public void search(){
        dropdown.click();
        allemployees.click();
        search.sendKeys("fiaz");

    }
}
