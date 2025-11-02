package com.boost.wallet_service.service.wallet;

import com.boost.wallet_service.dto.WalletServiceReqBean;
import com.boost.wallet_service.dto.WalletServiceRespBean;

public interface IWalletService {

    WalletServiceRespBean credit(WalletServiceReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

}
