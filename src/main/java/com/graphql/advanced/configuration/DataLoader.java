package com.graphql.advanced.configuration;

import antlr.collections.impl.IntRange;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import com.graphql.advanced.entity.CustomerEntity;
import com.graphql.advanced.entity.PurchaseTransactionEntity;
import com.graphql.advanced.repository.CustomerRepository;
import com.graphql.advanced.repository.PurchaseTransactionRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public record DataLoader(CustomerRepository customerRepository,
                         PurchaseTransactionRepository purchaseTransactionRepository) {

    @Bean
    private InitializingBean sendDatabase() {
        Faker faker = new Faker();
        return () -> customerRepository.saveAll(generateCustomers(faker));
    }

    private List<CustomerEntity> generateCustomers(Faker faker) {
        return IntStream.range(0, 10)
                .mapToObj(i -> CustomerEntity.builder()
                        .createdAt(LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 10)))))
                        .fullName(faker.name().fullName())
                        .phoneNumber(faker.phoneNumber().cellPhone())
                        .address(faker.address().fullAddress())
                        .purchaseTransactions(generatePurchaseTransactions(faker))
                        .build())
                .collect(Collectors.toList());
    }

    private List<PurchaseTransactionEntity> generatePurchaseTransactions(Faker faker) {
        List<PurchaseTransactionEntity> transactionEntities = IntStream.range(0, 10)
                .mapToObj(i -> PurchaseTransactionEntity.builder()
                        .createdAt(LocalDate.now().minus(Period.ofDays(new Random().nextInt(365 * 10))))
                        .amount(new BigDecimal(faker.commerce().price().replaceAll(",", ".")))
                        .paymentType(
                                List.of(CreditCardType.values())
                                        .get(new Random().nextInt(CreditCardType.values().length))
                                        .toString()
                        )
                        .build())
                .collect(Collectors.toList());
        purchaseTransactionRepository.saveAll(transactionEntities);
        return transactionEntities;
    }


}
