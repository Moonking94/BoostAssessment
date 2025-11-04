package com.boost.wallet_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TransactionBean {
    private UUID transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String fromEmail;
    private String toEmail;
    private LocalDateTime timestamp;
}
