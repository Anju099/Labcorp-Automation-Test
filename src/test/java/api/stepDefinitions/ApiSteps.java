package api.stepDefinitions;

import api.utils.ApiClient;
import io.cucumber.java.en.*;
import org.junit.Assert;
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
        System.out.println("Endpoint: " + endpoint);
        apiClient.prepareGetRequest(endpoint);
    }

    @Given("I prepare a POST request to {string}")
    public void preparePostRequest(String endpoint) {
        System.out.println("\n=== PREPARING POST REQUEST ===");
        System.out.println("Endpoint: " + endpoint);
        apiClient.preparePostRequest(endpoint);
    }

    @When("I send the GET request with query parameter {string} as {string}")
    public void sendGetRequestWithQueryParam(String paramKey, String paramValue) {
        System.out.println("Adding query parameter: " + paramKey + " = " + paramValue);
        apiClient.addQueryParameter(paramKey, paramValue);
        apiClient.sendGetRequest();
        System.out.println("✓ GET request sent successfully");
    }

    @When("I send the POST request with query parameter {string} as {string} and the following payload:")
    public void sendPostRequestWithPayload(String paramKey, String paramValue, io.cucumber.datatable.DataTable dataTable) {
        System.out.println("Adding query parameter: " + paramKey + " = " + paramValue);
        apiClient.addQueryParameter(paramKey, paramValue);
        
        // Build payload from DataTable
        dataTable.asMap().forEach((key, value) -> {
            System.out.println("Payload - " + key + ": " + value);
            
            // Try to convert to appropriate type
            if (isNumeric(value)) {
                payloadMap.put(convertKeyToCamelCase(key), Double.parseDouble(value));
            } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                payloadMap.put(convertKeyToCamelCase(key), Boolean.parseBoolean(value));
            } else {
                payloadMap.put(convertKeyToCamelCase(key), value);
            }
        });
        
        apiClient.sendPostRequestWithMap(payloadMap);
        System.out.println("✓ POST request sent successfully");
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = apiClient.getStatusCode();
        System.out.println("\nVerifying status code: " + actualStatusCode);
        Assert.assertEquals("Status code mismatch", expectedStatusCode, actualStatusCode);
        System.out.println("✓ Status code verification passed");
    }

    @Then("the response should contain the following fields:")
    public void verifyResponseFields(io.cucumber.datatable.DataTable dataTable) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("\nVerifying response contains expected fields:");

        List<String> fields = dataTable.asList();
        for (int i = 1; i < fields.size(); i++) { // i=1 se start kiya taaki 'field' skip ho jaye
            String field = fields.get(i);
            boolean contains = responseBody.contains(field);
            System.out.println("  - Field '" + field + "': " + (contains ? "✓ FOUND" : "✗ NOT FOUND"));
            Assert.assertTrue("Response should contain field: " + field, contains);
        };
        System.out.println("✓ All fields verification passed");
    }

    @Then("the response headers should be present")
    public void verifyResponseHeaders() {
        Map<String, String> headers = apiClient.getResponseHeaders();
        System.out.println("\nVerifying response headers:");
        System.out.println("Total headers found: " + headers.size());
        headers.forEach((key, value) -> System.out.println("  - " + key + ": " + value));
        Assert.assertTrue("Response should contain headers", !headers.isEmpty());
        System.out.println("✓ Headers verification passed");
    }

    @Then("the response should contain the order_id {string}")
    public void verifyOrderId(String expectedOrderId) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("\nVerifying order_id: " + expectedOrderId);
        Assert.assertTrue("Response should contain order_id: " + expectedOrderId, 
            responseBody.contains(expectedOrderId));
        System.out.println("✓ Order ID verification passed");
    }

    @Then("the response should contain customer email {string}")
    public void verifyCustomerEmail(String expectedEmail) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("Verifying customer email: " + expectedEmail);
        Assert.assertTrue("Response should contain customer email: " + expectedEmail, 
            responseBody.contains(expectedEmail));
        System.out.println("✓ Customer email verification passed");
    }

    @Then("the response should contain payment amount {string}")
    public void verifyPaymentAmount(String expectedAmount) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("Verifying payment amount: " + expectedAmount);
        Assert.assertTrue("Response should contain payment amount: " + expectedAmount, 
            responseBody.contains(expectedAmount));
        System.out.println("✓ Payment amount verification passed");
    }

    @Then("the response should contain shipping cost {string}")
    public void verifyShippingCost(String expectedCost) {
        String responseBody = apiClient.getResponseBody();
        System.out.println("Verifying shipping cost: " + expectedCost);
        Assert.assertTrue("Response should contain shipping cost: " + expectedCost, 
            responseBody.contains(expectedCost));
        System.out.println("✓ Shipping cost verification passed");
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
        // Convert snake_case to camelCase
        String[] parts = key.split("_");
        StringBuilder camelCase = new StringBuilder(parts[0]);
        
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase())
                    .append(parts[i].substring(1));
        }
        
        return camelCase.toString();
    }
}

