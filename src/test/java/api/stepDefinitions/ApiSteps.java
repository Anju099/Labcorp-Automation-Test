package api.stepDefinitions;

import api.utils.ApiClient;
import io.cucumber.java.en.*;
import org.junit.Assert;
import com.fasterxml.jackson.databind.ObjectMapper; // Ye missing tha
import java.io.IOException; // Ye missing tha
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiSteps {
    private ApiClient apiClient;
    private Map<String, Object> payloadMap;

    public ApiSteps() {
        this.apiClient = new ApiClient();
        this.payloadMap = new HashMap<>();
    }

    @Given("I prepare a GET request to {string}")
    public void prepareGetRequest(String endpoint) {
        System.out.println("\n=== PREPARING GET REQUEST ===");
        apiClient.prepareGetRequest(endpoint);
    }

    @Given("I prepare a POST request to {string}")
    public void preparePostRequest(String endpoint) {
        System.out.println("\n=== PREPARING POST REQUEST ===");
        apiClient.preparePostRequest(endpoint);
    }

    @When("I send the GET request with query parameter {string} as {string}")
    public void sendGetRequestWithQueryParam(String paramKey, String paramValue) {
        apiClient.addQueryParameter(paramKey, paramValue);
        apiClient.sendGetRequest();
    }

    // New: Handle POST request with JSON doc string payload (Isse fix kiya hai)
    @When("I send the POST request with query parameter {string} as {string} and the following payload:")
    public void sendPostRequestWithJsonPayload(String paramKey, String paramValue, String docStringPayload) throws IOException {
        System.out.println("Adding query parameter: " + paramKey + " = " + paramValue);
        apiClient.addQueryParameter(paramKey, paramValue);

        // Parse JSON string to Map using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonPayload = objectMapper.readValue(docStringPayload, Map.class);

        System.out.println("Parsed JSON payload: " + jsonPayload);
        apiClient.sendPostRequestWithMap(jsonPayload);
        System.out.println("✓ POST request sent successfully (JSON doc string)");
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = apiClient.getStatusCode();
        Assert.assertEquals("Status code mismatch", expectedStatusCode, actualStatusCode);
    }

    @Then("the response should contain the following fields:")
    public void verifyResponseFields(io.cucumber.datatable.DataTable dataTable) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("\nVerifying response contains expected fields:");

        List<String> fields = dataTable.asList();
        for (int i = 1; i < fields.size(); i++) {
            String field = fields.get(i);
            boolean contains = responseBody.contains(field);
            System.out.println("  - Field '" + field + "': " + (contains ? "✓ FOUND" : "✗ NOT FOUND"));
            Assert.assertTrue("Response should contain field: " + field, contains);
        };
        System.out.println("✓ All fields verification passed");
    }


    @Then("the POST request's response should contain the following fields and values:")
    public void verifyResponseFieldsAndValues(io.cucumber.datatable.DataTable dataTable) {
        String responseString = apiClient.getResponseBody();
        // DataTable ko map mein convert karte hain
        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);

        System.out.println("--- Starting Validation for 15 Fields ---");

        expectedData.forEach((fieldPath, expectedValue) -> {

            String[] parts = fieldPath.split("\\.");
            String targetKey = parts[parts.length - 1];


            String pattern1 = targetKey + "=" + expectedValue;
            String pattern2 = "\"" + targetKey + "\":\"" + expectedValue + "\"";
            String pattern3 = "\"" + targetKey + "\":" + expectedValue; // For numbers

            boolean found = responseString.contains(targetKey) &&
                    (responseString.contains(expectedValue) || responseString.contains(pattern1));

            System.out.println("Checking: " + fieldPath + " [" + targetKey + "] -> " + (found ? "PASS" : "FAIL"));

            Assert.assertTrue("Field or Value mismatch! Field: " + fieldPath +
                    ", Expected Value: " + expectedValue, found);
        });

        System.out.println("--- All 15 Fields Verified Successfully ---");
    }

    @Then("the response headers should be present")
    public void verifyResponseHeaders() {
        Map<String, String> headers = apiClient.getResponseHeaders();
        Assert.assertTrue("Response should contain headers", !headers.isEmpty());
    }

    // Helper methods
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String convertKeyToCamelCase(String key) {
        String[] parts = key.split("_");
        StringBuilder camelCase = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
        }
        return camelCase.toString();
    }
}