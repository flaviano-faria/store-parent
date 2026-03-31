package com.storebackoffice.exception;

public class ProductNotFoundException extends ProductException {

    public ProductNotFoundException(Long id) {
        super(404, "PRODUCT_NOT_FOUND", "Product not found: " + id);
    }
}
