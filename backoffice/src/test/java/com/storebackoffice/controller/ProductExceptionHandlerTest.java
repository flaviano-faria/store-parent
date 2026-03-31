package com.storebackoffice.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class ProductExceptionHandlerTest {

    @Test
    void getProduct_whenMissing_returnsMappedNotFound() {
        given()
                .when().get("/products/{id}", 999999)
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("status", equalTo(404))
                .body("code", equalTo("PRODUCT_NOT_FOUND"))
                .body("message", equalTo("Product not found: 999999"))
                .body("path", equalTo("/products/999999"))
                .body("timestamp", notNullValue());
    }

    @Test
    void updateProduct_whenPayloadIdMismatch_returnsMappedBadRequest() {
        String body = """
                {
                  "id": 999,
                  "name": "Updated Name",
                  "description": "Updated Description",
                  "price": 12.34,
                  "picture": "picture.png",
                  "sku": "SKU-UPDATED-001"
                }
                """;

        given()
                .contentType("application/json")
                .body(body)
                .when().put("/products/{id}", 101)
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("status", equalTo(400))
                .body("code", equalTo("PRODUCT_BAD_REQUEST"))
                .body("message", equalTo("Payload id does not match path id"))
                .body("path", equalTo("/products/101"))
                .body("timestamp", notNullValue());
    }
}
