package com.vsii.mwm.repository;

import com.vsii.mwm.domain.MaterialEntity;
import com.vsii.mwm.service.utils.Constants;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepository extends JpaRepository<MaterialEntity, Integer>,
        JpaSpecificationExecutor<MaterialEntity>
{

    Optional<MaterialEntity> findByIdAndIsActiveTrue(Integer integer);

    List<MaterialEntity> findAllByIsActiveTrueAndSupplierId(Integer supplierId);

}
