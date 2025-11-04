package com.boost.wallet_service.service.transaction;

import com.boost.wallet_service.dto.TransactionReqBean;
import com.boost.wallet_service.dto.TransactionRespBean;

public interface ITransactionService {

    TransactionRespBean getTransactionHistory(TransactionReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

}
