package com.graphql.advanced.repository;

import com.graphql.advanced.entity.PurchaseTransactionEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseTransactionRepository extends CrudRepository<PurchaseTransactionEntity, String>,
        QuerydslPredicateExecutor<PurchaseTransactionEntity> {

}
