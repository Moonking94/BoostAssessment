package com.boost.wallet_service.model;

import com.boost.wallet_service.dto.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions", schema = "wallet", catalog = "wallet")
public class TransactionsEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID rowId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionTypeEnum transactionType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "source_user_id", nullable = false)
    private UsersEntity sourceUser;

    @ManyToOne
    @JoinColumn(name = "destination_user_id")
    private UsersEntity destinationUser;

    @Column(name = "trasaction_date", nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Version
    @Column(name = "version")
    private Long version;

}