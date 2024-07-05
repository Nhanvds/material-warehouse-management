package com.demo.mwm.repository.specification;

import com.demo.mwm.service.utils.Constants;
import com.demo.mwm.domain.MaterialEntity;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class MaterialSpecification {

    public static Specification<MaterialEntity> isActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                active == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("isActive"),active);
    }

    public static Specification<MaterialEntity> hasMaterialNameLike(String materialName) {
        return (root, query, criteriaBuilder) ->
                materialName == null || materialName.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get("materialName"), "%" + materialName + "%");
    }

    public static Specification<MaterialEntity> hasMaterialCodeLike(String materialCode) {
        return (root, query, criteriaBuilder) ->
                materialCode == null || materialCode.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get("materialCode"), "%" + materialCode + "%");
    }

    public static Specification<MaterialEntity> sort(String sortProperty, String sortOrder) {
        return (root, query, criteriaBuilder) -> {
            if (!sortOrder.isEmpty() && !sortProperty.isEmpty()) {
                Path<MaterialEntity> orderPath = root.get(sortProperty);
                Order order = sortOrder.equalsIgnoreCase(Constants.Paging.ASC)
                        ? criteriaBuilder.asc(orderPath)
                        : criteriaBuilder.desc(orderPath);
                query.orderBy(order);
            }
            return criteriaBuilder.conjunction();
        };
    }

}
