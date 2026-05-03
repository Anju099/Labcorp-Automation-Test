package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class ApplyPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Job Title - Multiple fallback strategies
    private By applyJobTitle2 = By.xpath("//div//h3[text()='Lead Software QA Analyst']");
    
    // Location - Multiple fallback strategies
    private By applyJobLocation1 = By.xpath("//span[@class='apply-location']");
    private By applyJobLocation2 = By.xpath("//span[contains(@class, 'location')]");
    
    // Job ID - Multiple fallback strategies
    private By applyJobId1 = By.cssSelector(".apply-job-id");
    private By applyJobId2 = By.xpath("//*[contains(@class, 'job-id')]");
    
    // Automation Tool - Multiple fallback strategies
    private By automationTool1 = By.xpath("//div[@class='tools']//li[1]");
    private By automationTool2 = By.xpath("//li[contains(text(), 'Selenium') or contains(text(), 'automation')]");
    
    // Return to Search Button - Multiple fallback strategies
    private By returnToSearchButton2 = By.xpath("//button[text()='Careers Home']");


    public ApplyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    }

    public String getApplyJobTitle() {
        System.out.println("\n=== GETTING APPLY JOB TITLE ===");
        try {
            String title = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobTitle2)).getText();
            System.out.println("✓ Found title using applyJobTitle1: " + title);
            return title;
        } catch (Exception e1) {
            try {
                String title = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobTitle2)).getText();
                System.out.println("✓ Found title using applyJobTitle2: " + title);
                return title;
            } catch (Exception e2) {
                System.out.println("✗ Could not find job title");
                throw e2;
            }
        }
    }

    public String getApplyJobLocation() {
        System.out.println("\n=== GETTING APPLY JOB LOCATION ===");
        try {
            String location = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobLocation1)).getText();
            System.out.println("✓ Found location using applyJobLocation1: " + location);
            return location;
        } catch (Exception e1) {
            try {
                String location = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobLocation2)).getText();
                System.out.println("✓ Found location using applyJobLocation2: " + location);
                return location;
            } catch (Exception e2) {
                System.out.println("✗ Could not find job location");
                throw e2;
            }
        }
    }

    public String getApplyJobId() {
        System.out.println("\n=== GETTING APPLY JOB ID ===");
        try {
            String id = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobId1)).getText();
            System.out.println("✓ Found job ID using applyJobId1: " + id);
            return id;
        } catch (Exception e1) {
            try {
                String id = wait.until(ExpectedConditions.visibilityOfElementLocated(applyJobId2)).getText();
                System.out.println("✓ Found job ID using applyJobId2: " + id);
                return id;
            } catch (Exception e2) {
                System.out.println("✗ Could not find job ID");
                throw e2;
            }
        }
    }

    public String getAutomationTool() {
        System.out.println("\n=== GETTING AUTOMATION TOOL ===");
        try {
            String tool = wait.until(ExpectedConditions.visibilityOfElementLocated(automationTool1)).getText();
            System.out.println("✓ Found automation tool using automationTool1: " + tool);
            return tool;
        } catch (Exception e1) {
            try {
                String tool = wait.until(ExpectedConditions.visibilityOfElementLocated(automationTool2)).getText();
                System.out.println("✓ Found automation tool using automationTool2: " + tool);
                return tool;
            } catch (Exception e2) {
                System.out.println("✗ Could not find automation tool");
                throw e2;
            }
        }
    }

    public void clickReturnToSearch() {
        System.out.println("\n=== CLICKING RETURN TO SEARCH ===");
        if (tryClickElement(returnToSearchButton2)) {
            System.out.println("✓ Clicked using returnToSearchButton1");
            return;
        }
        if (tryClickElement(returnToSearchButton2)) {
            System.out.println("✓ Clicked using returnToSearchButton2");
            return;
        }
        if (tryClickElement(returnToSearchButton2)) {
            System.out.println("✓ Clicked using returnToSearchButton3");
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

