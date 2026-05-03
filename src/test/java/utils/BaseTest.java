package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class BaseTest {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }
        return driver;
    }

    public static void waitForPageLoadComplete(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
        // Additional wait for jQuery if it's being used
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                webDriver -> {
                    try {
                        return (Boolean) ((JavascriptExecutor) webDriver).executeScript("return typeof jQuery === 'undefined' || jQuery.active === 0");
                    } catch (Exception e) {
                        return true;
                    }
                }
            );
        } catch (Exception e) {
            // jQuery might not be present, that's fine
        }
    }

    public static void tearDown() {
        if (driver != null) {
            try {
                System.out.println("Closing browser...");
                driver.quit();
                System.out.println("✓ Browser closed successfully");
            } catch (Exception e) {
                System.out.println("✗ Error closing browser: " + e.getMessage());
                try {
                    driver = null;
                } catch (Exception e2) {
                    // Ignore
                }
            } finally {
                driver = null;
            }
        }
    }
}

