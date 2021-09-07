package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ActivityPage {

    @FindBy(how = How.ID, using = "exampleInputEmail")
    WebElement email;
    @FindBy(how = How.ID, using = "exampleInputPassword")
    WebElement password;
    @FindBy(how = How.CSS, using = "body > div > div > div > div > div > div > div > div > form > button")
    WebElement login;
    @FindBy(how = How.ID, using = "Setting_MasterData")
    WebElement masterdata;
    @FindBy(how = How.CSS, using = "#collapse_MasterData > a > span")
    WebElement masterpims;
    @FindBy(how = How.ID, using = "statusName")
    WebElement activitystatus;
    @FindBy(how = How.ID, using = "shortName")
    WebElement shortname;
    @FindBy(how = How.XPATH, using = "//*[@id=\"content\"]/div/div[2]/div/div/form/button")
    WebElement done;
    @FindBy(how = How.XPATH, using = "//*[@id=\"departmentTable_paginate\"]/ul/li[2]/a")
    WebElement digit;

    public void logIn(){
        email.sendKeys("admin");
        password.sendKeys("123456");
        login.click();
        masterdata.click();
        masterpims.click();

    }

    public void activityStatus(String status) {
        activitystatus.sendKeys(status);

    }

    public void shortName(String name){
        shortname.sendKeys(name);

    }

    public void save(){
        done.click();
    }

    public String num(){
        String actval = digit.getText();
        return actval;
    }

}
