package com.storebackoffice.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

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
}
