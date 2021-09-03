package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {

    @FindBy(how = How.ID, using = "exampleInputEmail")
    WebElement email;
    @FindBy(how = How.ID, using = "exampleInputPassword")
    WebElement password;
    @FindBy(how = How.CSS, using = "body > div > div > div > div > div > div > div > div > form > button")
    WebElement login;
    @FindBy(how = How.XPATH, using = "/html/body/nav/span")
    WebElement banner;

        public void logIn(){
            email.sendKeys("admin");
            password.sendKeys("123456");
            login.click();
        }

        public String findBanner(){
            String actval = banner.getText();
            System.out.println(actval);
            return actval;
        }

}
