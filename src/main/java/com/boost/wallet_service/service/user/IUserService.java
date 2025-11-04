package com.boost.wallet_service.service.user;

import com.boost.wallet_service.dto.UserReqBean;
import com.boost.wallet_service.dto.UserRespBean;

public interface IUserService {

    UserRespBean create(UserReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

    UserRespBean update(UserReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

    UserRespBean retrieve(UserReqBean wsReqBean, String idempotencyKey) throws RuntimeException;

}
