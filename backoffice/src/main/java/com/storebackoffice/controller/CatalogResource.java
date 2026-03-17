package com.storebackoffice.controller;

import com.storebackoffice.interfaces.CatalogApi;
import com.storebackoffice.api.model.ApiCatalog;
import com.storebackoffice.service.CatalogService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CatalogResource implements CatalogApi {

    private final CatalogService catalogService;

    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public ApiCatalog getCatalog() {
        ApiCatalog catalog = new ApiCatalog();
        catalog.setCategories(catalogService.buildCategories());
        return catalog;
    }
}
