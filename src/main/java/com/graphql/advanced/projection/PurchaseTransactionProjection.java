package com.graphql.advanced.projection;

import java.time.LocalDate;

public interface PurchaseTransactionProjection {

    String getId();

    String getPaymentType();

    LocalDate getCreatedAt();

}
