package com.demo.mwm.repository;

import com.demo.mwm.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermissionRepository extends JpaRepository<PermissionEntity,Integer> {
    @Override
    List<PermissionEntity> findAll();
}
