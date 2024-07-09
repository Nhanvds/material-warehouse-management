package com.demo.mwm.repository;

import com.demo.mwm.entity.SupplierEntity;
import com.demo.mwm.utils.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISupplierRepository extends JpaRepository<SupplierEntity,Integer> {

    Optional<SupplierEntity> findByIdAndIsActiveTrue(Integer id);

    @Procedure(name = Constants.Procedure.FIND_SUPPLIER_BY_ID_AND_IS_ACTIVE_TRUE)
    Optional<SupplierEntity> findSupplierByIdAndIsActiveTrue(@Param("supplier_id") Integer supplierId);

    List<SupplierEntity> getAllByIsActiveTrue();
}
