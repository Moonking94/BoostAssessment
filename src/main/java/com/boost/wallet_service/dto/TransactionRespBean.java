package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRespBean {
    private List<TransactionBean> transactions;
    private int total;

    private String errorMsg;

}
