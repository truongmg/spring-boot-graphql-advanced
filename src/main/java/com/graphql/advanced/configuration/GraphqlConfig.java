package com.graphql.advanced.configuration;

import com.graphql.advanced.projection.PurchaseTransactionProjection;
import com.graphql.advanced.repository.PurchaseTransactionRepository;
import graphql.scalars.ExtendedScalars;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
@RequiredArgsConstructor
public class GraphqlConfig {

    private final PurchaseTransactionRepository purchaseTransactionRepository;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.Date)
                .type("Query", builder -> builder.dataFetcher("findAllPurchaseTransactions",
                        manyDataFetcherForFindAllPurchaseTransactions()))
                .type("Query", builder -> builder.dataFetcher("findPurchaseTransactionById",
                        singleDataFetcherForFindPurchaseTransactionById()))
                .build();
    }

    private DataFetcher<PurchaseTransactionProjection> singleDataFetcherForFindPurchaseTransactionById() {
        return QuerydslDataFetcher.builder(purchaseTransactionRepository)
                .projectAs(PurchaseTransactionProjection.class)
                .single();
    }

    private DataFetcher<Iterable<PurchaseTransactionProjection>> manyDataFetcherForFindAllPurchaseTransactions() {
        return QuerydslDataFetcher.builder(purchaseTransactionRepository)
                .projectAs(PurchaseTransactionProjection.class)
                .many();
    }

}
