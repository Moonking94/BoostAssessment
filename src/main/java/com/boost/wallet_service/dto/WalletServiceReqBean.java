package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WalletServiceReqBean extends CommonBean {

    private String type;
    private BigDecimal amount;
    private String user;
    private String recepient;

}
