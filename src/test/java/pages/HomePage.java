package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By careersLink = By.linkText("Careers");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCareers() {
        if (tryClickLocator(careersLink)) return;
        printAllLinks();
        throw new RuntimeException("Could not find and click Careers link. Check console for available links.");
    }

    private boolean tryClickLocator(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            // Scroll element into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500); // Small delay for animation
            element.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void printAllLinks() {
        try {
            List<WebElement> allLinks = driver.findElements(By.xpath("//a[@href]"));
            System.out.println("\n=== ALL LINKS ON PAGE ===");
            for (int i = 0; i < allLinks.size(); i++) {
                String text = allLinks.get(i).getText().trim();
                String href = allLinks.get(i).getAttribute("href");
                System.out.println((i+1) + ". TEXT: '" + text + "' | HREF: '" + href + "'");
            }
            System.out.println("========================\n");
        } catch (Exception e) {
            System.out.println("Error printing links: " + e.getMessage());
        }
    }
}

