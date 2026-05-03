## LabCorp Automation Framework

A comprehensive BDD-based test automation framework using Selenium WebDriver and REST Assured with Cucumber.

### Project Structure

```
labcorp-automation/
├── src/test/java
│ ├── ui/
│ │ ├── pages/                          # Page Object Model classes
│ │ │ ├── HomePage.java
│ │ │ ├── CareersPage.java
│ │ │ ├── JobDetailsPage.java
│ │ │ └── ApplyPage.java
│ │ ├── stepDefinitions/                # UI Step Definitions
│ │ │ ├── JobSearchSteps.java
│ │ │ └── Hooks.java
│ │ └── runners/                        # Test Runners
│ │ └── UiTestRunner.java
│ ├── api/
│ │ ├── stepDefinitions/                # API Step Definitions
│ │ │ └── ApiSteps.java
│ │ ├── utils/                          # API Utilities
│ │ │ └── ApiClient.java
│ │ └── runners/                        # API Test Runners
│ │ └── ApiTestRunner.java
│ ├── common/
│ │ ├── hooks/                          # Common Hooks
│ │ ├── utils/                          # Common Utilities
│ │ └── BaseTest.java
│ └── stepDefinitions/                  # Shared Step Definitions
│ └── Hooks.java
├── src/test/resources
│ ├── features/
│ │ ├── ui/
│ │ │ └── JobSearch.feature
│ │ └── api/
│ │ └── api.feature
├── pom.xml                             # Maven Configuration
└── README.md                           # This file

```

### Features

#### UI Testing (Selenium + Cucumber)
- Page Object Model architecture
- Multiple fallback locators for robustness
- Auto-retry mechanisms
- Comprehensive debugging output
- Browser automation for LabCorp careers portal

#### API Testing (REST Assured + Cucumber)
- GET and POST request validation
- Response field and header verification
- JSON payload handling
- Status code validation
- Clean, readable BDD format

### Setup Instructions

1. **Prerequisites:**
   - Java 11+
   - Maven 3.8.1+
   - Chrome/Chromium browser

2. **Install Dependencies:**
   ```bash
   mvn clean install
   ```

3. **Run All Tests:**
   ```bash
   mvn test
   ```

4. **Run UI Tests Only:**
   ```bash
   mvn test -Dgroups="@LabcorpTestUi"
   ```

5. **Run API Tests Only:**
   ```bash
   mvn test -Dgroups="@API"
   ```

### Test Scenarios

#### UI Tests (@LabcorpTestUi)
- **Scenario:** Browse to a job listing and verify details
  - Search for "Lead Software QA Analyst"
  - Verify job title, location, and ID
  - Check job description, qualifications, and preferences
  - Apply for the job
  - Verify application page details

#### API Tests (@API)
1. **GET Request Test (@GET)**
   - Endpoint: `https://echo.free.beeceptor.com/sample-request?author=beeceptor`
   - Validates: path, ip, method fields
   - Validates: response headers

2. **POST Request Test (@POST)**
   - Endpoint: `http://echo.free.beeceptor.com/sample-request`
   - Payload: Complex customer order data
   - Validates: order_id, customer email, payment amount, shipping cost

### Key Classes

#### UI Page Objects
- `HomePage.java` - Main landing page
- `CareersPage.java` - Careers search page
- `JobDetailsPage.java` - Job details view
- `ApplyPage.java` - Job application page

#### UI Step Definitions
- `JobSearchSteps.java` - Test step implementations
- `Hooks.java` - Setup/Teardown logic

#### API Classes
- `ApiClient.java` - REST Assured wrapper for API calls
- `ApiSteps.java` - Cucumber step definitions for API tests

#### Utilities
- `BaseTest.java` - WebDriver initialization and management
- `ApiClient.java` - API request/response handling

### Dependencies

```xml
<!-- Selenium -->
<selenium-java>4.15.0</selenium-java>

<!-- Cucumber -->
<cucumber-java>7.11.0</cucumber-java>
<cucumber-junit>7.11.0</cucumber-junit>

<!-- REST Assured -->
<rest-assured>5.3.1</rest-assured>

<!-- WebDriver Manager -->
<webdrivermanager>5.5.3</webdrivermanager>

<!-- JSON Processing -->
<jackson-databind>2.15.2</jackson-databind>

<!-- Unit Testing -->
<junit>4.13.2</junit>
```

### Running Tests

**Maven Command:**
```bash
mvn clean test
```

**With Specific Tag:**
```bash
mvn clean test -Dgroups="@API"
```

**With Specific Runner:**
```bash
mvn test -Dtest=ApiTestRunner
```

### Test Reports

After test execution, reports are generated:
- **UI Reports:** `target/cucumber-reports/index.html`
- **API Reports:** `target/cucumber-reports/api-report.html`

### Browser Closure

- Browser automatically closes after each test via Hooks
- Graceful handling of browser closure errors
- Proper cleanup of WebDriver instance

### Locator Strategy

All page objects use:
1. **Primary Locators** - Most specific (ID, exact class)
2. **Secondary Locators** - More flexible (contains, xpath)
3. **Tertiary Locators** - Most permissive (generic matching)
4. **Fallback Strategies** - Auto-retry with alternative locators

If all fail, detailed debug info is printed:
- All matching elements
- Element attributes
- Helpful console output

### Example Test Execution

```
===== TEST STARTED =====
✓ Found Careers link using locator #2
=== SEARCHING FOR POSITION: Lead Software QA Analyst ===
✓ Found input using searchInput1 (type text)
✓ Clicked button using searchButton1 (id)
=== CLOSING BROWSER =====
Closing browser...
✓ Browser closed successfully
===== TEST COMPLETED =====
```

### Troubleshooting

1. **Browser not closing:**
   - Check Hooks.java is properly configured
   - Verify BaseTest.tearDown() is called

2. **Locator not found:**
   - Check console output for element debug info
   - Update locators based on printed element list

3. **API tests failing:**
   - Verify endpoint URLs are accessible
   - Check request/response payloads match expected format
   - Review REST Assured logs in console

### Future Enhancements

- Parallel test execution
- Custom reporting framework
- Database integration
- Performance testing
- Load testing with JMeter
- Visual regression testing
- Accessibility testing
- Mobile testing with Appium

### Support

For issues or questions:
1. Check console logs for detailed error messages
2. Review feature files for test case definitions
3. Check page objects for locator updates
4. Verify dependencies in pom.xml

---

**Last Updated:** May 3, 2026

