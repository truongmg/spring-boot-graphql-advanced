package com.graphql.advanced.repository;

import com.graphql.advanced.entity.CustomerEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface CustomerRepository extends CrudRepository<CustomerEntity, String>, QuerydslPredicateExecutor<CustomerEntity> {

}
