package com.boost.wallet_service.service.wallet;

import com.boost.wallet_service.constant.Constants;
import com.boost.wallet_service.dto.TransactionTypeEnum;
import com.boost.wallet_service.dto.WalletServiceReqBean;
import com.boost.wallet_service.dto.WalletServiceRespBean;
import com.boost.wallet_service.model.IdempotencyRecordsEntity;
import com.boost.wallet_service.model.TransactionsEntity;
import com.boost.wallet_service.model.UsersEntity;
import com.boost.wallet_service.repository.idempotencyRecords.IdempotencyRecordsDao;
import com.boost.wallet_service.repository.transactions.TransactionsDao;
import com.boost.wallet_service.repository.users.UsersDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    @Autowired private UsersDao usersDao;
    @Autowired private TransactionsDao transactionsDao;
    @Autowired private IdempotencyRecordsDao idempotencyRecordsDao;
    @Autowired private ObjectMapper objectMapper; // For storing response as JSON
    @Autowired private MessageSource messageSource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletServiceRespBean credit(WalletServiceReqBean wsReqBean, String idempotencyKey) throws RuntimeException {
        try {

            // 1. Try inserting idempotency record first
            IdempotencyRecordsEntity record = IdempotencyRecordsEntity.builder()
                    .idempotencyKey(idempotencyKey)
                    .endpoint(TransactionTypeEnum.CREDIT.getEndpoint())
                    .createdTimestamp(LocalDateTime.now())
                    .responsePayload(null)
                    .build();

            try {
                idempotencyRecordsDao.save(record);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicateRequestException("Duplicate idempotency key: already processed");
            }

            // 2. Fetch user with optimistic locking
            UsersEntity user = usersDao.findByEmail(wsReqBean.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 3. Apply credit
            user.setBalance(user.getBalance().add(wsReqBean.getAmount()));
            usersDao.saveAndFlush(user); // triggers version increment

            // 4. Log transaction
            TransactionsEntity tx = TransactionsEntity.builder()
                    .transactionType(TransactionTypeEnum.CREDIT)
                    .amount(wsReqBean.getAmount())
                    .sourceUser(user)
                    .transactionDate(LocalDateTime.now())
                    .build();
            transactionsDao.saveAndFlush(tx);

            // 5. Prepare response
            WalletServiceRespBean wsRespBean = new WalletServiceRespBean();
            wsRespBean.setNewBalance(user.getBalance());
            wsRespBean.setStatus(messageSource.getMessage(Constants.RESPONSE_CODE_SUCCESS, null, LocaleContextHolder.getLocale()));

            // 6. Store response payload in idempotency record
            try {
                record.setResponsePayload(objectMapper.writeValueAsString(wsRespBean));
                idempotencyRecordsDao.save(record);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize response", e);
            }

            return wsRespBean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
