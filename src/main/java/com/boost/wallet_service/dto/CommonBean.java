package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonBean {

    private String email;
    private String idempotencyKey;

}
