package com.demo.mwm.repository;

import com.demo.mwm.entity.MaterialEntity;
import com.demo.mwm.utils.Constants;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepository extends JpaRepository<MaterialEntity, Integer>,
        JpaSpecificationExecutor<MaterialEntity> {

    Optional<MaterialEntity> findByIdAndIsActiveTrue(Integer integer);

    List<MaterialEntity> findAllByIsActiveTrueAndSupplierId(Integer supplierId);


    public static Specification<MaterialEntity> isActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                active == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get(Constants.MaterialField.IS_ACTIVE), active);
    }

    public static Specification<MaterialEntity> hasMaterialNameLike(String materialName) {
        return (root, query, criteriaBuilder) ->
                materialName == null || materialName.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get(Constants.MaterialField.MATERIAL_NAME), Constants.Query.PERCENT_SIGN + materialName + Constants.Query.PERCENT_SIGN);
    }

    public static Specification<MaterialEntity> hasMaterialCodeLike(String materialCode) {
        return (root, query, criteriaBuilder) ->
                materialCode == null || materialCode.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get(Constants.MaterialField.MATERIAL_CODE), Constants.Query.PERCENT_SIGN + materialCode + Constants.Query.PERCENT_SIGN);
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
