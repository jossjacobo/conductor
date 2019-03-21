package io.ddavison.conductor.util;

import io.ddavison.conductor.ConductorConfig;
import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.pmw.tinylog.Logger;

public class DriverUtil {

    public static WebDriver getDriver(ConductorConfig config, DesiredCapabilities desiredCapabilities) {
        WebDriver driver = null;
        Capabilities capabilities = new DesiredCapabilities();
        boolean isLocal = config.getHub() == null;
        try {
            switch (config.getBrowser()) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions()
                            .merge(buildCustomCapabilities(config, desiredCapabilities));
                    if (isLocal) {
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver(chromeOptions);
                    } else {
                        capabilities = chromeOptions;
                    }
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions()
                            .merge(buildCustomCapabilities(config, desiredCapabilities));
                    if (isLocal) {
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver(firefoxOptions);
                    } else {
                        capabilities = firefoxOptions;
                    }
                    break;
                case INTERNET_EXPLORER:
                    InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions()
                            .merge(buildCustomCapabilities(config, desiredCapabilities));
                    if (isLocal) {
                        WebDriverManager.iedriver().setup();
                        driver = new InternetExplorerDriver(internetExplorerOptions);
                    } else {
                        capabilities = internetExplorerOptions;
                    }
                    break;
                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions()
                            .merge(buildCustomCapabilities(config, desiredCapabilities));
                    if (isLocal) {
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver(edgeOptions);
                    } else {
                        capabilities = edgeOptions;
                    }
                    break;
                case SAFARI:
                    SafariOptions safariOptions = new SafariOptions()
                            .merge(buildCustomCapabilities(config, desiredCapabilities));
                    if (isLocal) {
                        driver = new SafariDriver(safariOptions);
                    } else {
                        capabilities = safariOptions;
                    }
                    break;
                default:
                    Logger.error("Unknown browser: " + config.getBrowser());
                    return null;
            }

            if (!isLocal) {
                try {
                    driver = new RemoteWebDriver(config.getHub(), capabilities);
                } catch (Exception e) {
                    Logger.error(e, "Couldn't connect to hub: " + config.getHub().toString());
                }
            }
        } catch (Exception e) {
            Logger.error(e, "Probably a WebDriver issue, see https://github.com/bonigarcia/webdrivermanager");
            System.exit(1);
        }
        return driver;
    }

    public static MutableCapabilities buildCustomCapabilities(ConductorConfig conductorConfig,
                                                              DesiredCapabilities customDesiredCapabilities) {
        MutableCapabilities newCapabilities = new MutableCapabilities();
        if (!conductorConfig.getCustomCapabilities().isEmpty()) {
            for (String key : conductorConfig.getCustomCapabilities().keySet()) {
                newCapabilities.setCapability(key, conductorConfig.getCustomCapabilities().get(key));
            }
        }
        if (customDesiredCapabilities != null && !customDesiredCapabilities.asMap().isEmpty()) {
            newCapabilities.merge(customDesiredCapabilities);
        }
        return newCapabilities;
    }

}
