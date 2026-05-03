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
   Scenario: Verify POST Request with full customer order data
    Given I prepare a POST request to "http://echo.free.beeceptor.com/sample-request"
    When I send the POST request with query parameter "author" as "beeceptor" and the following payload:
      """
      {
        "order_id": "12345",
        "customer": {
          "name": "Jane Smith",
          "email": "janesmith@example.com",
          "phone": "1-987-654-3210",
          "address": {
            "street": "456 Oak Street",
            "city": "Metropolis",
            "state": "NY",
            "zipcode": "10001",
            "country": "USA"
          }
        },
        "items": [
          {
            "product_id": "A101",
            "name": "Wireless Headphones",
            "quantity": 1,
            "price": 79.99
          },
          {
            "product_id": "B202",
            "name": "Smartphone Case",
            "quantity": 2,
            "price": 15.99
          }
        ],
        "payment": {
          "method": "credit_card",
          "transaction_id": "txn_67890",
          "amount": 111.97,
          "currency": "USD"
        },
        "shipping": {
          "method": "standard",
          "cost": 5.99,
          "estimated_delivery": "2024-11-15"
        },
        "order_status": "processing",
        "created_at": "2024-11-07T12:00:00Z"
      }
      """
    Then the response status code should be 200
    And the POST request's response should contain the following fields and values:
      | order_id              | 12345                     |
      | customer              | {name=Jane Smith          |
      | customer.name         | Jane Smith                |
      | customer.email        | janesmith@example.com     |
      | customer.phone        | 1-987-654-3210            |
      | customer.address      | street=456 Oak Street     |
      | customer.address.city | Metropolis                |
      | items                 | product_id=A101           |
      | payment               | method=credit_card        |
      | payment.method        | credit_card               |
      | payment.amount        | 111.97                    |
      | shipping              | method=standard           |
      | shipping.cost         | 5.99                      |
      | order_status          | processing                |
      | created_at            | 2024-11-07T12:00:00Z      |

