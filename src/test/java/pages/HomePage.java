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
    private By acceptCookiesButton = By.xpath("//button[contains(text(), 'Accept All Cookies')]");
    private By cookiePopup = By.xpath("//div[contains(@class, 'cookie') or contains(text(), 'Cookie Notice')]");


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickCareers() {
        // Handle cookie popup before clicking Careers link
        dismissCookiePopup();
        
        if (tryClickLocator(careersLink)) return;
        printAllLinks();
        throw new RuntimeException("Could not find and click Careers link. Check console for available links.");
    }

    public void dismissCookiePopup() {
        try {
            // Wait for cookie popup to appear and click "Accept All Cookies"
            List<WebElement> buttons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(acceptCookiesButton)
            );
            
            if (!buttons.isEmpty()) {
                WebElement acceptButton = buttons.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", acceptButton);
                Thread.sleep(500);
                acceptButton.click();
                System.out.println("Cookie popup dismissed successfully.");
                // Wait for popup to close
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Cookie popup not found or already dismissed: " + e.getMessage());
        }
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

