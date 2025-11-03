package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceReqBean {

    private String name;
    private String email;
    private BigDecimal balance;

}
