package browserdriversetup;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reporting.ExtentManager;
import reporting.ExtentTestManager;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BrowserDriverSetUp {

    public static WebDriver driver = null;
    public static final String url = System.getProperty("url", "http://54.172.98.170:443/");
    public static String browserName = System.getProperty("browserName", "chrome");
    public static String os = System.getProperty("os", "mac");

    //To go into cloud you just have to change the platform to cloud
    public static String platform = System.getProperty("platform", "local");
    public static String cloudPlatformName = System.getProperty("cloudPlatformName", "browserstack");
    public static final String AUTOMATE_USERNAME = System.getProperty("AUTOMATE_USERNAME","hamzasohrab_oLJxIR");
    public static final String AUTOMATE_ACCESS_KEY = System.getProperty("AUTOMATE_ACCESS_KEY", "YBUHyHbqwRr55YGkdiCq");

    //Start Of Reporting Utilities

    //ExtentReport

    public static ExtentReports extent;
    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }
    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }
    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }
        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(driver, result.getName());
        }
        driver.quit();
    }

    public static void captureScreenshot(WebDriver driver, String screenshotName){

        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir")+ "/pictures/" + screenshotName + " " + df.format(date) + ".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());;
        }

    }
    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
     //End Of Reporting Utilities


    @BeforeMethod
    public void setUp() throws MalformedURLException {
        if (platform.equals("local")){
            if (browserName.equals("chrome")){
                getChromeDriver();
            }
            else if (browserName.equals("firefox")){
                getFireFoxDriver();
            }

        }
        else if (platform.equals("cloud")){
            if (cloudPlatformName.equals("browserstack")){
                getDriverForBrowserStack();
            }
        }
            else if (cloudPlatformName.equals("saucelab")){
                getDriverForSauceLab();
            }

            driver.get(url);
    }

    public static WebDriver getChromeDriver(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");

        if (os.equals("mac")){
            System.setProperty("webdriver.chrome.driver", "../Utilities/drivers/chromedriver");
            driver = new ChromeDriver(options);
        }
        else if (os.equals("windows")){
            System.setProperty("webdriver.chrome.driver", "../Utilities/drivers/chromedriver.exe");
            driver = new ChromeDriver(options);
        }
            return driver;
    }

    public static WebDriver getFireFoxDriver(){

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");

        if (os.equals("mac")){
            System.setProperty("webdriver.firefox.driver", "../Utilities/drivers/geckodriver");
            driver = new FirefoxDriver(options);
        }
        else if (os.equals("windows")){
            System.setProperty("webdriver.firefox.driver", "../Utilities/drivers/geckodriver.exe");
            driver = new FirefoxDriver(options);
        }
            return driver;
    }

    public static WebDriver getDriverForBrowserStack() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os_version", "Big Sur");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("browser", "chrome");
        caps.setCapability("browser_version", "93.0");
        caps.setCapability("os", "OS X");

        driver = new RemoteWebDriver(new URL("http://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub"), caps);

        return driver;
    }

    public WebDriver getDriverForSauceLab() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os_version", "big Sur");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "93.0");
        caps.setCapability("os", "OS X");

        driver = new RemoteWebDriver(new URL("http://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub"), caps);

        return driver;
    }

    @AfterMethod
    public void cleanUp() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }

}
