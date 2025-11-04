package com.boost.wallet_service.service.transaction;

import com.boost.wallet_service.dto.TransactionBean;
import com.boost.wallet_service.dto.TransactionReqBean;
import com.boost.wallet_service.dto.TransactionRespBean;
import com.boost.wallet_service.repository.idempotencyRecords.IdempotencyRecordsDao;
import com.boost.wallet_service.repository.transactions.TransactionsDao;
import com.boost.wallet_service.repository.users.UsersDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    @Autowired private UsersDao usersDao;
    @Autowired private TransactionsDao transactionsDao;
    @Autowired private IdempotencyRecordsDao idempotencyRecordsDao;
    @Autowired private ObjectMapper objectMapper; // For storing response as JSON
    @Autowired private MessageSource messageSource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionRespBean getTransactionHistory(TransactionReqBean wsReqBean, String idempotencyKey) throws RuntimeException {
        try {
            if (StringUtils.isEmpty(wsReqBean.getEmail())) {
                throw new RuntimeException("Email is empty !");
            }

            List<TransactionBean> results = transactionsDao.getTransactionHistory(wsReqBean.getEmail());

            TransactionRespBean wsRespBean = new TransactionRespBean();
            wsRespBean.setTransactions(results);
            wsRespBean.setTotal(results.size());

            return wsRespBean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
