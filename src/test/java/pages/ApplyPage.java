package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class ApplyPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By applyJobTitle = By.xpath("//div//h3[text()='Lead Software QA Analyst']");
    private By applyJobLocation = By.xpath("//span[@class='apply-location']");
    private By applyJobId = By.cssSelector(".apply-job-id");
    private By returnToSearchButton = By.xpath("//button[text()='Careers Home']");

    public ApplyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public String getApplyJobTitle() {
        System.out.println("\n=== GETTING APPLY JOB TITLE ===");
        try {
            String title = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobTitle)).getText();
            System.out.println("✓ Found title using applyJobTitle1: " + title);
            return title;
        } catch (Exception e1) {
                System.out.println("✗ Could not find job title");
                throw e1;
        }
    }

    public String getApplyJobLocation() {
        System.out.println("\n=== GETTING APPLY JOB LOCATION ===");
        try {
            String location = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobLocation)).getText();
            System.out.println("✓ Found location using applyJobLocation1: " + location);
            return location;
        } catch (Exception e1) {
                System.out.println("✗ Could not find job location");
                throw e1;
        }
    }

    public String getApplyJobId() {
        System.out.println("\n=== GETTING APPLY JOB ID ===");
        try {
            String id = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobId)).getText();
            System.out.println("✓ Found job ID using applyJobId1: " + id);
            return id;
        } catch (Exception e1) {
                System.out.println("✗ Could not find job ID");
                throw e1;
            }
    }

    public void clickReturnToSearch() {
        System.out.println("\n=== CLICKING RETURN TO SEARCH ===");
        if (tryClickElement(returnToSearchButton)){
            System.out.println("✓ Clicked using returnToSearchButton1");
            return;
        }
        System.out.println("✗ Could not find return to search button");
        throw new RuntimeException("Could not find return to search button");
    }
    
    private boolean tryClickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (element.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                Thread.sleep(500);
                element.click();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}

