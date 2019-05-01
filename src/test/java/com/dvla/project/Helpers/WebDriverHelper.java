package com.dvla.project.Helpers;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.logging.Level;

public class WebDriverHelper extends EventFiringWebDriver {

    public static final WebDriver driver;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            driver.close();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        try {
            driver = buildChromeBrowser();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Error(throwable);
        }
    }

    public WebDriverHelper() {
        super(driver);
    }

    public static WebDriver buildChromeBrowser() throws Throwable {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\tools\\chromedriver\\chromedriver.exe");
        DesiredCapabilities capabilities = getChromeDesiredCapabilities();
        ChromeDriver chrome = new ChromeDriver(capabilities);
        chrome.manage().window().maximize();
        return chrome;
    }

    public static WebDriver buildFireFoxBrowser() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\tools\\geckodriver\\geckodriver.exe");
        DesiredCapabilities capabilities = getFireFoxDesiredCapabilities();
        FirefoxDriver firefox = new FirefoxDriver(capabilities);
        firefox.manage().window().setSize(new Dimension(1280, 1024));
        return firefox;
    }

    public static WebDriver buildIEBrowser() {
        System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\tools\\iedriver\\IEDriverServer.exe");
        DesiredCapabilities capabilities = getInternetExploreDesiredCapabilities();
        InternetExplorerDriver IE = new InternetExplorerDriver(capabilities);
        IE.manage().window().setSize(new Dimension(1280, 1024));
        return IE;
    }

    private static DesiredCapabilities getInternetExploreDesiredCapabilities() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        DesiredCapabilities capabilities = DesiredCapabilities
                .internetExplorer();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        capabilities
                .setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
        capabilities.setVersion("9");
        return capabilities;
    }

    private static DesiredCapabilities getFireFoxDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("marionette", false);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setBrowserName("firefox");

        capabilities.setCapability("disable-restore-session-state", true);
        return capabilities;
    }

    private static DesiredCapabilities getChromeDesiredCapabilities() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--test-type");
        capabilities.setCapability("chrome.verbose", false);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }

    public static WebDriver getWebDriver() {
        return driver;
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }
}
