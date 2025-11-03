package com.boost.wallet_service.service.user;

import com.boost.wallet_service.dto.UserServiceReqBean;
import com.boost.wallet_service.dto.UserServiceRespBean;
import com.boost.wallet_service.dto.WalletServiceRespBean;
import com.boost.wallet_service.model.IdempotencyRecordsEntity;
import com.boost.wallet_service.model.UsersEntity;
import com.boost.wallet_service.repository.idempotencyRecords.IdempotencyRecordsDao;
import com.boost.wallet_service.repository.users.UsersDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.boost.wallet_service.constant.Constants.ENDPOINT_USER_CREATE;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Autowired private UsersDao usersDao;
    @Autowired private IdempotencyRecordsDao idempotencyRecordsDao;
    @Autowired private ObjectMapper objectMapper; // For storing response as JSON
    @Autowired private MessageSource messageSource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserServiceRespBean create(UserServiceReqBean wsReqBean, String idempotencyKey) throws RuntimeException {
        try {
            String endpoint = ENDPOINT_USER_CREATE;

            // 1. Try inserting idempotency record (will fail if already exists)
            IdempotencyRecordsEntity record = IdempotencyRecordsEntity.builder()
                    .idempotencyKey(idempotencyKey)
                    .endpoint(ENDPOINT_USER_CREATE)
                    .createdTimestamp(LocalDateTime.now())
                    .responsePayload(null)
                    .build();

            try {
                idempotencyRecordsDao.save(record);
            } catch (DataIntegrityViolationException e) {
                return idempotencyRecordsDao
                        .findByIdempotencyKeyAndEndpoint(idempotencyKey, endpoint)
                        .map(r -> {
                            try {
                                return objectMapper.readValue(r.getResponsePayload(), UserServiceRespBean.class);
                            } catch (JsonProcessingException ex) {
                                throw new RuntimeException("Failed to parse cached response", ex);
                            }
                        })
                        .orElseThrow(() -> new RuntimeException("Duplicate request, no cached response"));
            }

            // 2. Validate if user already exists by email
            if (usersDao.findByEmail(wsReqBean.getEmail()).isPresent()) {
                throw new RuntimeException("User with this email already exists");
            }

            // 3. Save user
            UsersEntity user = new UsersEntity();
            user.setName(wsReqBean.getName());
            user.setEmail(wsReqBean.getEmail());
            user.setBalance(wsReqBean.getBalance());
            user = usersDao.saveAndFlush(user);

            // 4. Prepare response
            UserServiceRespBean wsRespBean = new UserServiceRespBean();
            wsRespBean.setName(user.getName());
            wsRespBean.setEmail(user.getEmail());
            wsRespBean.setBalance(user.getBalance());

            // 5. Cache response in idempotency table
            try {
                record.setResponsePayload(objectMapper.writeValueAsString(wsRespBean));
                idempotencyRecordsDao.save(record);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to cache response payload", e);
            }

            return wsRespBean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
