package com.boost.wallet_service.service.user;

import com.boost.wallet_service.dto.UserServiceReqBean;
import com.boost.wallet_service.dto.UserServiceRespBean;

public interface IUserService {

    UserServiceRespBean create(UserServiceReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

}
