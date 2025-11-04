package com.boost.wallet_service.service.wallet;

import com.boost.wallet_service.dto.WalletReqBean;
import com.boost.wallet_service.dto.WalletRespBean;

public interface IWalletService {

    WalletRespBean credit(WalletReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

    WalletRespBean debit(WalletReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

    WalletRespBean transfer(WalletReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

}
