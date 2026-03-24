package com.storebackoffice.service;

import com.storebackoffice.api.model.ApiCategory;
import com.storebackoffice.api.model.ApiProduct;
import com.storebackoffice.entity.Category;
import com.storebackoffice.entity.Product;
import com.storebackoffice.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CatalogService {

    private final CategoryRepository categoryRepository;

    public CatalogService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public List<ApiCategory> findAllCategories() {
        return categoryRepository.findAllCategories().stream()
                .map(this::toApiCategory)
                .toList();
    }

    private ApiCategory toApiCategory(Category entity) {
        ApiCategory api = new ApiCategory()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription());
        api.setProducts(entity.getProducts().stream()
                .map(this::toApiProduct)
                .toList());
        return api;
    }

    private ApiProduct toApiProduct(Product entity) {
        return new ApiProduct()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .picture(entity.getPicture())
                .sku(entity.getSku());
    }
}
