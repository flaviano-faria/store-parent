package com.storebackoffice.service;

import com.storebackoffice.api.model.ApiProduct;
import com.storebackoffice.entity.Product;
import com.storebackoffice.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ApiProduct> listProducts(String category, Integer limit) {
        int lim = (limit != null && limit > 0) ? limit : 20;
        List<Product> products;
        if (category == null || category.isBlank()) {
            products = productRepository.listOrdered(lim);
        } else {
            products = productRepository.listByCategoryNameOrdered(category.strip(), lim);
        }
        return products.stream().map(this::toApi).toList();
    }

    @Transactional
    public ApiProduct createProduct(ApiProduct body) {
        Product entity = fromApiForCreate(body);
        productRepository.persist(entity);
        productRepository.getEntityManager().flush();
        return toApi(entity);
    }

    public ApiProduct getProduct(Long id) {
        return productRepository.findByIdOptional(id)
                .map(this::toApi)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
    }

    @Transactional
    public ApiProduct updateProduct(Long id, ApiProduct body) {
        Product entity = productRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        applyApiToEntity(body, entity, false);
        return toApi(entity);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product entity = productRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        productRepository.delete(entity);
    }

    private Product fromApiForCreate(ApiProduct body) {
        Product entity = new Product();
        applyApiToEntity(body, entity, true);
        return entity;
    }

    /**
     * @param forCreate when true, ignore incoming id (DB generates it)
     */
    private void applyApiToEntity(ApiProduct body, Product entity, boolean forCreate) {
        if (!forCreate && body.getId() != null && !body.getId().equals(entity.getId())) {
            throw new BadRequestException("Payload id does not match path id");
        }
        entity.setName(body.getName());
        entity.setDescription(body.getDescription());
        entity.setPrice(body.getPrice());
        entity.setPicture(body.getPicture());
        entity.setSku(body.getSku());
    }

    private ApiProduct toApi(Product entity) {
        return new ApiProduct()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .picture(entity.getPicture())
                .sku(entity.getSku());
    }
}
