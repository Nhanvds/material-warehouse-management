package com.vsii.mwm.repository;

import com.vsii.mwm.domain.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISupplierRepository extends JpaRepository<SupplierEntity,Integer> {

    Optional<SupplierEntity> findByIdAndIsActiveTrue(Integer id);

    List<SupplierEntity> getAllByIsActiveTrue();
}
