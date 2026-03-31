package com.storebackoffice.exception;

public class ProductBadRequestException extends ProductException {

    public ProductBadRequestException(String message) {
        super(400, "PRODUCT_BAD_REQUEST", message);
    }
}
