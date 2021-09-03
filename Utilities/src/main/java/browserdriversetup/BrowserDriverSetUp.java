package browserdriversetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserDriverSetUp {

    public static WebDriver driver = null;
    public static final String url = System.getProperty("url", "http://54.172.98.170:443/");
    public static String browserName = System.getProperty("browserName", "chrome");
    public static String os = System.getProperty("os", "mac");
    public static String platform = System.getProperty("platform", "local");
    public static String cloudPlatformName = System.getProperty("cloudPlatformName", "browserstack");
    public static final String AUTOMATE_USERNAME = System.getProperty("AUTOMATE_USERNAME","yourusername");
    public static final String AUTOMATE_ACCESS_KEY = System.getProperty("AUTOMATE_ACCESS_KEY", "xxnxn");

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        if (platform.equals("local")){
            if (browserName.equals("chrome")){
                getChromeDriver();
            }
        }
        else if (platform.equals("cloud")){
            if (cloudPlatformName.equals("browserstack")){
                getDriverForBrowserStack();
            }
        }
            driver.get(url);
    }

    public static WebDriver getChromeDriver(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");

        if (os.equals("mac")){
            System.setProperty("webdriver.chrome.driver", "/Users/hamza/WebAutomationFramework/Utilities/drivers/chromedriver");
            driver = new ChromeDriver(options);
        }
            return driver;
    }

    public static WebDriver getDriverForBrowserStack() throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os_version", "11.5.2");
        caps.setCapability("resolution", "2048x1536");
        caps.setCapability("browser", "chrome");
        caps.setCapability("browser_version", "latest-beta");
        caps.setCapability("os", "mac");

        driver = new RemoteWebDriver(new URL("http://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub"), caps);

        return driver;
    }

    @AfterMethod
    public void cleanUp() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();

    }

}
