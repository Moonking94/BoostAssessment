package com.boost.wallet_service.dto;

import lombok.Getter;

import static com.boost.wallet_service.constant.Constants.*;

@Getter
public enum TransactionTypeEnum {
    CREDIT(ENDPOINT_WALLET_CREDIT),
    DEBIT(ENDPOINT_WALLET_DEBIT),
    TRANSFER(ENDPOINT_WALLET_TRANSFER);

    private final String endpoint;

    private TransactionTypeEnum(String endpoint) {
        this.endpoint = endpoint;
    }

}
