package com.boost.wallet_service.repository.idempotencyRecords;

import com.boost.wallet_service.model.IdempotencyRecordsEntity;
import com.boost.wallet_service.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdempotencyRecordsDao extends JpaRepository<IdempotencyRecordsEntity, UUID>, IdempotencyRecordsDaoCustom {

    Optional<IdempotencyRecordsEntity> findByIdempotencyKeyAndEndpoint(String idempotencyKey, String endpoint);

}
