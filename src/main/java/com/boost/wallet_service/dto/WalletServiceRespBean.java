package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WalletServiceRespBean {

    private String status;
    private BigDecimal balance;
    private String errorMsg;

}
