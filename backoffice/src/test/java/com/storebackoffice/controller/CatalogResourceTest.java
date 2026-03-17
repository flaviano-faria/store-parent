package com.storebackoffice.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
class CatalogResourceTest {

    @Test
    void getCatalog_returnsOkWithCategories() {
        given()
                .when().get("/catalog")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("categories", notNullValue())
                .body("categories.size()", greaterThanOrEqualTo(1))
                .body("categories[0].name", notNullValue())
                .body("categories[0].products", notNullValue());
    }

    @Test
    void getCatalog_returnsExpectedCategoryNames() {
        given()
                .when().get("/catalog")
                .then()
                .statusCode(200)
                .body("categories.name", hasItems("Electronics", "Clothing", "Home & Garden"));
    }

    @Test
    void getCatalog_returnsProductsWithRequiredFields() {
        given()
                .when().get("/catalog")
                .then()
                .statusCode(200)
                .body("categories[0].products[0].id", notNullValue())
                .body("categories[0].products[0].name", notNullValue())
                .body("categories[0].products[0].price", notNullValue());
    }
}
