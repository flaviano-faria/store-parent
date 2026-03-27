package com.storebackoffice.controller;

import com.storebackoffice.api.model.ApiProduct;
import com.storebackoffice.interfaces.ProductsApi;

import java.util.List;

public class ProductResource implements ProductsApi {
    @Override
    public ApiProduct createProduct(ApiProduct apiProduct) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public ApiProduct getProduct(Long id) {
        return null;
    }

    @Override
    public List<ApiProduct> listProducts(String category, Integer limit) {
        return List.of();
    }

    @Override
    public ApiProduct updateProduct(Long id, ApiProduct apiProduct) {
        return null;
    }
}
