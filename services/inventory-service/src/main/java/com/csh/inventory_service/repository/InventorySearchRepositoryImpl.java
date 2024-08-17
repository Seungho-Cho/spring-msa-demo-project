package com.csh.inventory_service.repository;

import com.csh.inventory_service.entity.Inventory;
import com.csh.inventory_service.graphql.InventoryFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InventorySearchRepositoryImpl implements InventorySearchRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Inventory> searchInventories(InventoryFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> query = criteriaBuilder.createQuery(Inventory.class);
        Root<Inventory> inventoryRoot = query.from(Inventory.class);

        List<Predicate> predicates = new ArrayList<>();
        Field[] fields = InventoryFilter.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(filter);
                if (value != null) {
                    if (value instanceof String strValue) {
                        predicates.add(criteriaBuilder.like(inventoryRoot.get(field.getName()), "%"+strValue+"%"));
                    } else {
                        predicates.add(criteriaBuilder.equal(inventoryRoot.get(field.getName()), value));
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Failed to access filter fields: " + field.getName(), e);
            }
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
