package pageobjectstests;

import browserdriversetup.BrowserDriverSetUp;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.ActivityPage;
import readexcel.ExcelReader;

public class ActivityPageTest extends BrowserDriverSetUp {

    ActivityPage activityPage = null;

    @BeforeMethod
    public void initializeElements(){
        activityPage = PageFactory.initElements(driver, ActivityPage.class);
    }

    @DataProvider
    public Object[][] dataProvider() throws Exception {
        ExcelReader excelReader = new ExcelReader();
        //Where is the excel file
        excelReader.setExcelFile("/Users/hamza/WebAutomationFramework/Hrpmstestpractice/excelreader/data.xlsx");
        Object[][] data = excelReader.getExcelSheetData("Sheet1");
        return data;
    }

    @Test(dataProvider = "dataProvider")
    public void masterPmsTest(String status, String name)  {
        activityPage.logIn();
        activityPage.activityStatus(status);
        activityPage.shortName(name);
        activityPage.save();
        String actual = activityPage.num();
        Assert.assertEquals(actual, "1");

    }


}
