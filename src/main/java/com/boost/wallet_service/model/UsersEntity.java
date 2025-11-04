package com.boost.wallet_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "wallet", catalog = "wallet")
public class UsersEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Version
    @Column(name = "version")
    private Long version;

}
