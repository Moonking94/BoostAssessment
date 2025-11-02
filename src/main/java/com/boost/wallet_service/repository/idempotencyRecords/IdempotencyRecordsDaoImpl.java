package com.boost.wallet_service.repository.idempotencyRecords;

import com.boost.wallet_service.repository.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdempotencyRecordsDaoImpl extends AbstractDao implements IdempotencyRecordsDaoCustom {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyRecordsDaoImpl.class);

    public IdempotencyRecordsDaoImpl() {}

}
