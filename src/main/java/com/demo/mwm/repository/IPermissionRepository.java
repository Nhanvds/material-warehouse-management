package com.demo.mwm.repository;

import com.demo.mwm.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepository extends JpaRepository<PermissionEntity,Integer> {
}
