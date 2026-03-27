package com.storebackoffice.repository;

import com.storebackoffice.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> listOrdered(int limit) {
        return getEntityManager()
                .createQuery("FROM Product p ORDER BY p.id", Product.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Product> listByCategoryNameOrdered(String categoryName, int limit) {
        return getEntityManager()
                .createQuery(
                        "FROM Product p JOIN p.category c WHERE LOWER(c.name) = LOWER(:name) ORDER BY p.id",
                        Product.class)
                .setParameter("name", categoryName)
                .setMaxResults(limit)
                .getResultList();
    }

    public Optional<Product> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }
}
