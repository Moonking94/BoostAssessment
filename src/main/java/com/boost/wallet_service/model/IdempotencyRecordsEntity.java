package com.boost.wallet_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "idempotency_records", schema = "wallet", catalog = "wallet")
public class IdempotencyRecordsEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Column(name = "created_timestamp", nullable = false)
    private LocalDateTime createdTimestamp;

    @Lob
    @Column(name = "response_payload", nullable = false)
    private String responsePayload;

}
