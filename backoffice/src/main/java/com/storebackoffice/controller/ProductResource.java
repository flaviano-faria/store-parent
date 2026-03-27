package com.storebackoffice.controller;

import com.storebackoffice.api.model.ApiProduct;
import com.storebackoffice.interfaces.ProductsApi;
import com.storebackoffice.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductResource implements ProductsApi {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ApiProduct> listProducts(String category, Integer limit) {
        return productService.listProducts(category, limit);
    }

    @Override
    public ApiProduct createProduct(ApiProduct apiProduct) {
        return productService.createProduct(apiProduct);
    }

    @Override
    public ApiProduct getProduct(Long id) {
        return productService.getProduct(id);
    }

    @Override
    public ApiProduct updateProduct(Long id, ApiProduct apiProduct) {
        return productService.updateProduct(id, apiProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }
}
