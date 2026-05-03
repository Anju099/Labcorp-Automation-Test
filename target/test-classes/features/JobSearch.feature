Feature: LabCorp Job Search Automation

  Scenario: Browse to a job listing and verify details
    Given I open the browser to www.labcorp.com
    When I click on the Careers link
    And I search for the position "Lead Software QA Analyst"
    And I select the job listing
    Then I verify the job title is "Lead Software QA Analyst"
   #And I verify the job location is "United States of America"
   # And I verify the job ID is "265600"
    And I verify the first sentence of the JD under Responsibilities is "Senior QA resource for project teams providing solid technical leadership and support"
    And I verify the second bullet point under Minimum Qualifications is "6 or more years’ experience in Software Quality Assurance"
    And I verify the third point under Preferred Qualifications is "1 or more years' Clinical background experience."
    When I click Apply Now
    Then I verify the job title on the apply page is "Lead Software QA Analyst"
    #And I verify the job location on the apply page is "Burlington, NC"
    #And I verify the job ID on the apply page is "LAB00012345"
    #And I verify the automation tool suggestion contains "Selenium"
    When I click to Return to Job Search
    Then the job search page is displayed

