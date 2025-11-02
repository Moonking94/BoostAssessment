package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletServiceRespBean {

    private String status;
    private BigDecimal newBalance;
    private String errorMsg;

}
