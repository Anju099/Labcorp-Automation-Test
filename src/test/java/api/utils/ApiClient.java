package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private RequestSpecification requestSpec;
    private Response response;
    private String contentType = "application/json";

    public ApiClient() {
        RestAssured.baseURI = "";
    }

    public void prepareGetRequest(String endpoint) {
        requestSpec = RestAssured.given();
        requestSpec.baseUri(endpoint);
    }

    public void preparePostRequest(String endpoint) {
        requestSpec = RestAssured.given()
                .contentType(contentType)
                .accept(contentType);
        requestSpec.baseUri(endpoint);
    }

    public void addQueryParameter(String key, String value) {
        requestSpec.queryParam(key, value);
    }

    public void sendGetRequest() {
        response = requestSpec.when().get();
        logResponse();
    }

    public void sendPostRequest(String jsonPayload) {
        response = requestSpec.when().body(jsonPayload).post();
        logResponse();
    }

    public void sendPostRequestWithMap(Map<String, Object> payloadMap) {
        String jsonPayload = convertMapToJson(payloadMap);
        response = requestSpec.when().body(jsonPayload).post();
        logResponse();
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getResponseBody() {
        return response.getBody().asString();
    }

    public String getResponseAsJsonPath(String path) {
        return response.jsonPath().getString(path);
    }

    public Object getResponseAsObject(String path) {
        return response.jsonPath().get(path);
    }

    public boolean responseBodyContains(String text) {
        return getResponseBody().contains(text);
    }

    public Map<String, String> getResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        response.getHeaders().forEach(header -> headers.put(header.getName(), header.getValue()));
        return headers;
    }

    public boolean headerExists(String headerName) {
        return response.getHeaders().hasHeaderWithName(headerName);
    }

    private String convertMapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        int counter = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (counter > 0) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":");
            
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else if (entry.getValue() instanceof Number) {
                json.append(entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                json.append(entry.getValue());
            } else {
                json.append("\"").append(entry.getValue()).append("\"");
            }
            counter++;
        }
        json.append("}");
        return json.toString();
    }

    private void logResponse() {
        System.out.println("\n=== API RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + getResponseBody());
        System.out.println("Response Headers: " + getResponseHeaders());
        System.out.println("==================\n");
    }
}

