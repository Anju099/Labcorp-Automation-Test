Feature: REST API Testing for Beeceptor Endpoints

  @API @GET
  Scenario: Verify GET Request and validate response fields
    Given I prepare a GET request to "https://echo.free.beeceptor.com/sample-request"
    When I send the GET request with query parameter "author" as "beeceptor"
    Then the response status code should be 200
    And the response should contain the following fields:
      | path   |
      | ip     |
      | method |
    And the response headers should be present

  @API @POST
  Scenario: Verify POST Request with customer order data
    Given I prepare a POST request to "http://echo.free.beeceptor.com/sample-request"
    When I send the POST request with query parameter "author" as "beeceptor" and the following payload:
      | order_id  | 12345                           |
      | customer_name  | Jane Smith              |
      | customer_email | janesmith@example.com   |
      | customer_phone | 1-987-654-3210          |
      | address_street | 456 Oak Street          |
      | address_city   | Metropolis              |
      | address_state  | NY                      |
      | address_zipcode| 10001                   |
      | address_country| USA                     |
      | payment_method | credit_card             |
      | payment_amount | 111.97                  |
      | shipping_cost  | 5.99                    |
      | order_status   | processing              |
    Then the response status code should be 200
    And the response should contain the order_id "12345"
    And the response should contain customer email "janesmith@example.com"
    And the response should contain payment amount "111.97"
    And the response should contain shipping cost "5.99"

