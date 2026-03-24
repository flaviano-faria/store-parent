package com.storebackoffice.repository;

import com.storebackoffice.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    /**
     * Returns every category in the database, whether it has products or not.
     * <p>
     * First query loads all category ids (so empty categories are never dropped).
     * Second query uses {@code LEFT JOIN FETCH} so categories without products still appear with an empty list.
     */
    public List<Category> findAllCategories() {
        List<Long> ids = getEntityManager()
                .createQuery("SELECT c.id FROM Category c ORDER BY c.id", Long.class)
                .getResultList();
        if (ids.isEmpty()) {
            return List.of();
        }

        List<Category> rows = getEntityManager()
                .createQuery(
                        "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id IN :ids ORDER BY c.id",
                        Category.class)
                .setParameter("ids", ids)
                .getResultList();

        LinkedHashMap<Long, Category> byId = new LinkedHashMap<>();
        for (Category c : rows) {
            byId.putIfAbsent(c.getId(), c);
        }

        List<Category> ordered = new ArrayList<>(ids.size());
        for (Long id : ids) {
            Category c = byId.get(id);
            if (c != null) {
                ordered.add(c);
            }
        }
        return ordered;
    }
}
