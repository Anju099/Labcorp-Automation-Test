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

    // Multiple locator strategies
    private By careersLink1 = By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'careers')]");
    private By careersLink2 = By.xpath("//a[normalize-space()='Careers']");
    private By careersLink3 = By.linkText("Careers");
    private By careersLink4 = By.xpath("//a[@href and contains(@href, 'career')]");
    private By careersLink5 = By.xpath("//*[contains(text(), 'Careers')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCareers() {
        // Strategy 1: Try finding and clicking with visibility wait
        if (tryClickLocator(careersLink1)) return;
        if (tryClickLocator(careersLink2)) return;
        if (tryClickLocator(careersLink3)) return;
        if (tryClickLocator(careersLink4)) return;
        if (tryClickLocator(careersLink5)) return;
        
        // Strategy 2: If all else fails, print all links for debugging
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

