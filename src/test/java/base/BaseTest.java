package base;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ReadProperties;
import java.time.Duration;
import java.util.ResourceBundle;

public class BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected static WebDriver driver;
    public static Actions actions;

    public static ResourceBundle FilterParam;
    public static ResourceBundle Jmeter;

    private static final String JAVA_VERSION = System.getProperty("java.version");
    private static final String PLATFORM = System.getProperty("os.name").toLowerCase();

    @Given("Setup Driver {string}")
    public void setUp(String driverType) {
        logger.info("************************************  BeforeScenario  ************************************");
        logger.info("Test environment: Platform: {}, Driver: {}", PLATFORM, driverType);
        logger.info("Java version: {}", JAVA_VERSION);

        FilterParam = ReadProperties.readProp("FilterParam.properties");
        Jmeter = ReadProperties.readProp("Jmeter.properties");

        try {
            driver = Drivers.getDriverType(driverType).getDriver();
            if (driver != null) {
                driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
                driver.manage().window().maximize();
                actions = new Actions(driver);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Failed to initialize the driver. Unsupported type: {}", driverType, e);
            throw e;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
            logger.info("WebDriver closed.");
        }
    }
    public static WebDriver getDriver() {
        return driver;
    }

}
