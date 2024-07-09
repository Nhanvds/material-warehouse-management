package com.demo.mwm.repository;

import com.demo.mwm.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepository extends JpaRepository<MaterialEntity, Integer>,
        JpaSpecificationExecutor<MaterialEntity> {

    Optional<MaterialEntity> findByIdAndIsActiveTrue(Integer integer);

    List<MaterialEntity> findAllByIsActiveTrueAndSupplierId(Integer supplierId);

}
