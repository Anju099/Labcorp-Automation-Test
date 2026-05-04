package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Search input by placeholder
    private By searchInput = By.id("typehead");

    // Job card by title and job id
    public By jobCard(String jobTitle, String jobId, String jobLocation) {
        return By.xpath("//a[@aria-label='Lead Software QA Analyst Job ID is 265600' and 'Durham, North Carolina, United States of America']");
    }

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchPosition(String position) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(position);
        input.sendKeys(org.openqa.selenium.Keys.ENTER);
    }
    
    private boolean tryFillInput(By locator, String text) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (input.isDisplayed()) {
                input.sendKeys(text);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private boolean tryClickButton(By locator) {
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (button.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                Thread.sleep(500);
                button.click();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private void printAllInputs() {
        try {
            List<WebElement> allInputs = driver.findElements(By.xpath("//input"));
            System.out.println("\n=== ALL INPUT FIELDS ON PAGE ===");
            for (int i = 0; i < allInputs.size(); i++) {
                String type = allInputs.get(i).getAttribute("type");
                String id = allInputs.get(i).getAttribute("id");
                String name = allInputs.get(i).getAttribute("name");
                String placeholder = allInputs.get(i).getAttribute("placeholder");
                System.out.println((i+1) + ". TYPE: " + type + " | ID: " + id + " | NAME: " + name + " | PLACEHOLDER: " + placeholder);
            }
            System.out.println("================================\n");
        } catch (Exception e) {
            System.out.println("Error printing inputs: " + e.getMessage());
        }
    }
    
    private void printAllButtons() {
        try {
            List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
            System.out.println("\n=== ALL BUTTONS ON PAGE ===");
            for (int i = 0; i < allButtons.size(); i++) {
                String text = allButtons.get(i).getText().trim();
                String type = allButtons.get(i).getAttribute("type");
                String id = allButtons.get(i).getAttribute("id");
                String ariaLabel = allButtons.get(i).getAttribute("aria-label");
                System.out.println((i+1) + ". TEXT: '" + text + "' | TYPE: " + type + " | ID: " + id + " | ARIA-LABEL: " + ariaLabel);
            }
            System.out.println("============================\n");
        } catch (Exception e) {
            System.out.println("Error printing buttons: " + e.getMessage());
        }
    }

    public void selectJob(String jobTitle, String jobId, String jobLocation) {
        WebElement job = wait.until(ExpectedConditions.elementToBeClickable(jobCard(jobTitle, jobId, jobLocation)));
        job.click();
    }
    
    private boolean tryClickLink(By locator) {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (link.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
                Thread.sleep(500);
                link.click();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private void printAllJobLinks() {
        try {
            List<WebElement> allLinks = driver.findElements(By.xpath("//a[@href]"));
            System.out.println("\n=== ALL LINKS CONTAINING 'JOB' OR 'AUTOMATION' ===");
            for (int i = 0; i < allLinks.size(); i++) {
                String text = allLinks.get(i).getText().trim();
                String href = allLinks.get(i).getAttribute("href");
                if (text.toLowerCase().contains("job") || text.toLowerCase().contains("automation") || 
                    href.toLowerCase().contains("job") || href.toLowerCase().contains("automation")) {
                    System.out.println((i+1) + ". TEXT: '" + text + "' | HREF: '" + href + "'");
                }
            }
            System.out.println("=============================================\n");
        } catch (Exception e) {
            System.out.println("Error printing links: " + e.getMessage());
        }
    }
}



