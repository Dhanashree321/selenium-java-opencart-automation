package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = { "Sanity", "Regression", "Master" })
    @Parameters({ "os", "browser" })
    public void setup(@Optional("Windows") String os, String br) throws IOException, InterruptedException {

        // Load config.properties
        FileReader file = new FileReader(".//src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        String execEnv = p.getProperty("execution_env");

        if (execEnv.equalsIgnoreCase("remote")) {

            // ---------- SELENIUM 4 STYLE OPTIONS (Docker Grid) ----------
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled"); // IMPORTANT
            EdgeOptions edgeOptions = new EdgeOptions();

            // Common Docker-safe arguments
            String[] dockerArgs = {
                    "--remote-allow-origins=*",
                    "--disable-dev-shm-usage",
                    "--no-sandbox"
            };

            // OS Handling (Grid metadata only)
            if (os.equalsIgnoreCase("windows")) {
                chromeOptions.setPlatformName("Windows 10");
                edgeOptions.setPlatformName("Windows 10");
            } else if (os.equalsIgnoreCase("mac")) {
                chromeOptions.setPlatformName("MAC");
                edgeOptions.setPlatformName("MAC");
            }

            WebDriver remoteDriver = null;

            switch (br.toLowerCase()) {

                case "chrome":
                    chromeOptions.addArguments(dockerArgs);
                    remoteDriver = new RemoteWebDriver(
                            new URL("http://192.168.0.121:4444/wd/hub"),
                            chromeOptions);
                    break;

                case "edge":
                    edgeOptions.addArguments(dockerArgs);
                    remoteDriver = new RemoteWebDriver(
                            new URL("http://192.168.0.121:4444/wd/hub"),
                            edgeOptions);
                    break;

                default:
                    System.out.println("Invalid browser for remote execution");
                    return;
            }

            driver = remoteDriver;

        } else {   // --------------- LOCAL EXECUTION ----------------

            switch (br.toLowerCase()) {

                case "chrome":
                    driver = new ChromeDriver();
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    break;

                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                default:
                    System.out.println("Invalid browser name");
                    return;
            }
        }

        // Common settings
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        Thread.sleep(3000);

        driver.get(p.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = { "Sanity", "Regression", "Master" })
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        String alpha = RandomStringUtils.randomAlphabetic(3);
        String num = RandomStringUtils.randomNumeric(3);
        return alpha + "@" + num;
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        String targetFilePath =
                System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

        File targetFile = new File(targetFilePath);
        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }
}
