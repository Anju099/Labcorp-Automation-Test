package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.*;
import utils.BaseTest;

import java.util.List;

public class JobSearchSteps {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private JobDetailsPage jobDetailsPage;
    private ApplyPage applyPage;

    public JobSearchSteps() {
        this.driver = BaseTest.getDriver();
        this.homePage = new HomePage(driver);
        this.careersPage = new CareersPage(driver);
        this.jobDetailsPage = new JobDetailsPage(driver);
        this.applyPage = new ApplyPage(driver);
    }

    @Given("I open the browser to www.labcorp.com")
    public void openBrowser() {
        driver.get("https://www.labcorp.com");
        // Wait for page to fully load
        BaseTest.waitForPageLoadComplete(driver);
        try {
            Thread.sleep(1000); // Additional wait for dynamic content
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("I click on the Careers link")
    public void clickCareers() {
        homePage.clickCareers();
        // Wait for careers page to fully load
        BaseTest.waitForPageLoadComplete(driver);
        try {
            Thread.sleep(1000); // Additional wait for dynamic content
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("I search for the position {string}")
    public void searchPosition(String position) {
        careersPage.searchPosition(position);
    }

    @When("I select the job listing with expected job ID- 265600 Job Title- Lead Software QA Analyst and jobLocation- United States of America")
    public void selectJob() {
        // Use values from the feature file for job title and job id
        String jobTitle = "Lead Software QA Analyst";
        String jobId = "265600";
        String location = "Durham, North Carolina, United States of America";
        careersPage.selectJob(jobTitle, jobId, location);
    }

    @Then("I verify the job title is {string}")
    public void verifyJobTitle(String expected) {
        Assert.assertEquals(expected, jobDetailsPage.getJobTitle());
    }

    @Then("I verify the job location is {string} and {string}")
    public void verifyJobLocations(String expectedLoc1, String expectedLoc2) {
        jobDetailsPage.clickSeeAllLocationsIfPresent();
        List<String> actualLocations = jobDetailsPage.getAllJobLocations();
        Assert.assertTrue("Location 1 not found: " + expectedLoc1,
                actualLocations.stream().anyMatch(loc -> loc.contains(expectedLoc1)));
        Assert.assertTrue("Location 2 not found: " + expectedLoc2,
                actualLocations.stream().anyMatch(loc -> loc.contains(expectedLoc2)));
        jobDetailsPage.closeButton();
    }

    @Then("I verify the job ID is {string}")
    public void verifyJobId(String expectedJobId) {
        String actualJobId = jobDetailsPage.getJobId();
        Assert.assertTrue("Expected Job ID: " + expectedJobId + ", but found: " + actualJobId,
                actualJobId.contains(expectedJobId));
    }

    @And("I verify the first sentence of the JD under Responsibilities is {string}")
    public void verifyDescription(String expected) {
        Assert.assertEquals(expected, jobDetailsPage.getDescriptionText());
    }

    @And("I verify the second bullet point under Minimum Qualifications is {string}")
    public void verifyManagementSupport(String expected) {
        Assert.assertEquals(expected, jobDetailsPage.getManagementSupportBullet());
    }

    @And("I verify the third point under Preferred Qualifications is {string}")
    public void verifyRequirements(String expected) {
        Assert.assertEquals(expected, jobDetailsPage.getRequirementsBullet());
    }

    @When("I click Apply Now")
    public void clickApplyNow() {
        jobDetailsPage.clickApplyNow();
        jobDetailsPage.switchToNewWindow();
    }

    @Then("I verify the job title on the apply page is {string}")
    public void verifyApplyJobTitle(String expected) {
        Assert.assertEquals(expected, applyPage.getApplyJobTitle());
    }

    @And("I verify the job location on the apply page is {string}")
    public void verifyApplyJobLocation(String expected) {
        Assert.assertEquals(expected, applyPage.getApplyJobLocation());
    }

    @And("I verify the job ID on the apply page is {string}")
    public void verifyApplyJobId(String expected) {
        Assert.assertEquals(expected, applyPage.getApplyJobId());
    }

    @And("I verify the automation tool suggestion contains {string}")
    public void verifyAutomationTool(String expected) {
        Assert.assertTrue(applyPage.getAutomationTool().contains(expected));
    }

    @When("I click to Return to Job Search")
    public void clickReturnToSearch() {
        applyPage.clickReturnToSearch();
    }

    @Then("the job search page is displayed")
    public void verifyJobSearchPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("https://careers.labcorp.com/global/en"));
    }
}

