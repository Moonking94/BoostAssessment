package com.boost.wallet_service.repository.transactions;

import com.boost.wallet_service.dto.TransactionBean;

import java.util.List;

public interface TransactionsDaoCustom {

    void clearEntityManagerCache();

    List<TransactionBean>  getTransactionHistory(String email);

}
