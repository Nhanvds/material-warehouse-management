package com.demo.mwm.repository;

import com.demo.mwm.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface IRoleRepository extends JpaRepository<RoleEntity,Integer> {

    @Query("""
            select role from RoleEntity role
            where role.id in :ids
            """)
    Set<RoleEntity> findAllByIds(List<Integer> ids);

    RoleEntity findByName(String name);
}
