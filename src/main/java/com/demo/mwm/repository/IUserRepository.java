package com.demo.mwm.repository;

import com.demo.mwm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findById(Integer id);
    Optional<UserEntity> findByUsername(String username);
}
