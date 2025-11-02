package com.boost.wallet_service.repository.transactions;

import com.boost.wallet_service.model.TransactionsEntity;
import com.boost.wallet_service.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionsDao extends JpaRepository<TransactionsEntity, UUID>, TransactionsDaoCustom {
}
