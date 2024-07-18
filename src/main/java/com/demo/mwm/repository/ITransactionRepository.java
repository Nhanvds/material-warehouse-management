package com.demo.mwm.repository;

import com.demo.mwm.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<TransactionEntity,Integer> {

}
