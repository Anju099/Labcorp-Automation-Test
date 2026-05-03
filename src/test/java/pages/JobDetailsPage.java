package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JobDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for "See all" link
    private By seeAllLocationsLink = By.xpath("//button[contains(text(),'See all')]");
    // Locator for job locations in the popup
    private By locationList = By.xpath("//div[contains(@class,'modal')]//li");



    // Job Title - Multiple fallback strategies
    private By jobTitle1 = By.id("job-title");
    private By jobTitle2 = By.xpath("//h1");
    private By jobTitle3 = By.xpath("//*[contains(@class, 'title')]");
    
    // Location - Multiple fallback strategies
    private By jobLocation1 = By.xpath("//span[@class='location']");
    private By jobLocation2 = By.xpath("//span[contains(@class, 'location')]");
    
    // Job ID - Multiple fallback strategies
    private By jobId1 = By.cssSelector(".job-id");
    private By jobId2 = By.xpath("//*[contains(@class, 'job-id')]");
    
    // Description - Multiple fallback strategies
    private By descriptionText1 = By.xpath("//ul/li//p[text()='Senior QA resource for project teams providing solid technical leadership and support.']");

    
    // Management Support - Multiple fallback strategies
    private By managementSupportBullet1 = By.xpath("//li[contains(., '6 or more years') and contains(., 'experience')]");

    
    // Requirements - Multiple fallback strategies
    private By requirementsBullet1 = By.xpath("//li[contains(., '1 or more years') and contains(., 'Clinical background experience')]");

    
    // Apply Now Button - Multiple fallback strategies
    private By applyNowButton1 = By.cssSelector("button.apply-now");
    private By applyNowButton2 = By.xpath("//ppc-content[contains(text(), \"Apply Now\")]");
    private By applyNowButton3 = By.xpath("//button[contains(@class, 'apply')]");

    private By applyJobTitle2 = By.xpath("//div//h3[text()='Lead Software QA Analyst']");

    private By closeButton = By.xpath("//div[@class='popup-content-block']//button/i[@class='icon icon-cancel']");




    public void clickSeeAllLocationsIfPresent() {
        List<WebElement> seeAll = driver.findElements(seeAllLocationsLink);
        if (!seeAll.isEmpty()) {
            seeAll.get(0).click();
        }
    }

    public List<String> getAllJobLocations() {
        // Wait for modal if needed (add explicit wait if your framework supports)
        List<WebElement> locations = driver.findElements(locationList);
        return locations.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getJobId() {

        WebElement jobIdElement = driver.findElement(By.className("jobId"));


        String fullText = jobIdElement.getText();


        String cleanId = fullText.replace("Job ID :", "").trim();

        System.out.println("Final Job ID: " + cleanId);
        return cleanId;
    }

    public JobDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getJobTitle() {
        System.out.println("\n=== GETTING JOB TITLE ===");
        return trySafeGetText("Job Title", jobTitle1, jobTitle2, jobTitle3);
    }

    public String getJobLocation() {
        System.out.println("\n=== GETTING JOB LOCATION ===");
        return trySafeGetText("Job Location", jobLocation1, jobLocation2);
    }

    public void closeButton() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(closeButton));
        element.click();
    }

    public String getDescriptionText() {
        System.out.println("\n=== GETTING DESCRIPTION TEXT ===");
        String fullText = trySafeGetText("Description", descriptionText1);
        return fullText.split("\\.")[0];
    }

    public String getManagementSupportBullet() {
        System.out.println("\n=== GETTING MANAGEMENT SUPPORT BULLET ===");
        return trySafeGetText("Management Support", managementSupportBullet1);
    }

    public String getRequirementsBullet() {
        System.out.println("\n=== GETTING REQUIREMENTS BULLET ===");
        return trySafeGetText("Requirements", requirementsBullet1);
    }

    public void clickApplyNow() {
        System.out.println("\n=== CLICKING APPLY NOW ===");
        if (tryClickElement(applyNowButton1)) {
            System.out.println("✓ Clicked using applyNowButton1");
            return;
        }
        if (tryClickElement(applyNowButton2)) {
            System.out.println("✓ Clicked using applyNowButton2");
            return;
        }
        if (tryClickElement(applyNowButton3)) {
            System.out.println("✓ Clicked using applyNowButton3");
            return;
        }
        System.out.println("✗ Could not find Apply Now button");
        throw new RuntimeException("Could not find Apply Now button");
    }

    public void switchToNewWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }
    
    private String trySafeGetText(String fieldName, By... locators) {
        for (int i = 0; i < locators.length; i++) {
            try {
                String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locators[i])).getText();
                System.out.println("✓ Found " + fieldName + " using locator #" + (i+1) + ": " + text);
                return text;
            } catch (Exception e) {
                continue;
            }
        }
        System.out.println("✗ Could not find " + fieldName);
        throw new RuntimeException("Could not find " + fieldName);
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

