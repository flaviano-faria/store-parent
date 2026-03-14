package com.storebackoffice.controller;

import com.storebackoffice.api.CatalogApi;
import com.storebackoffice.api.model.ApiCatalog;
import com.storebackoffice.api.model.ApiCategory;
import com.storebackoffice.api.model.ApiProduct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CatalogResource implements CatalogApi {

    @Override
    public ApiCatalog getCatalog() {
        ApiCatalog catalog = new ApiCatalog();
        catalog.setCategories(buildCategories());
        return catalog;
    }

    private List<ApiCategory> buildCategories() {
        ApiCategory electronics = new ApiCategory()
                .id(1L)
                .name("Electronics")
                .description("Electronic devices and accessories");
        electronics.setProducts(List.of(
                createProduct(101L, "Wireless Mouse", "Ergonomic wireless mouse", 29.99, "mouse.png", "ELEC-MOU-001"),
                createProduct(102L, "USB-C Hub", "7-in-1 USB-C hub", 49.99, "hub.png", "ELEC-HUB-002")
        ));

        ApiCategory clothing = new ApiCategory()
                .id(2L)
                .name("Clothing")
                .description("Apparel and fashion");
        clothing.setProducts(List.of(
                createProduct(201L, "Cotton T-Shirt", "Organic cotton tee", 24.99, "tshirt.png", "CLTH-TSH-001"),
                createProduct(202L, "Denim Jeans", "Classic fit jeans", 79.99, "jeans.png", "CLTH-JNS-002")
        ));

        ApiCategory home = new ApiCategory()
                .id(3L)
                .name("Home & Garden")
                .description("Home improvement and garden supplies");
        home.setProducts(List.of(
                createProduct(301L, "LED Lamp", "Adjustable desk lamp", 39.99, "lamp.png", "HOME-LMP-001")
        ));

        return List.of(electronics, clothing, home);
    }

    private ApiProduct createProduct(Long id, String name, String description, Double price, String picture, String sku) {
        return new ApiProduct()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .picture(picture)
                .sku(sku);
    }
}
