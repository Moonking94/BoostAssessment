package com.boost.wallet_service.controller;

import com.boost.wallet_service.annotation.Idempotent;
import com.boost.wallet_service.constant.Constants;
import com.boost.wallet_service.dto.TransactionReqBean;
import com.boost.wallet_service.dto.TransactionRespBean;
import com.boost.wallet_service.service.transaction.ITransactionService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.boost.wallet_service.constant.Constants.ENDPOINT_USER_TRANSACTION_HISTORY;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("/api/transactionController")
public class TransactionController {

    private Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired private ITransactionService service;

    @Autowired private Gson gson = new Gson();

    public TransactionController() {}

    @PostMapping(value = "getTransactionHistory", headers = "accept=application/json")
    @Idempotent(endpoint = ENDPOINT_USER_TRANSACTION_HISTORY)
    public ResponseEntity<?> getTransactionHistory(@RequestBody TransactionReqBean wsReqBean, @RequestHeader("Idempotency-Key") String idempotencyKey
            , HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        UUID uuid = UUID.randomUUID();
        TransactionRespBean wsRespBean;
        try {
            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_REQUEST.replace("[string]", gson.toJson(wsReqBean)));

            wsRespBean = service.getTransactionHistory(wsReqBean, idempotencyKey);

            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_RESPONSE.replace("[string]", gson.toJson(wsRespBean)));
        } catch (Exception e) {
            wsRespBean = new TransactionRespBean();
            wsRespBean.setErrorMsg(e.getMessage());
        }

        return new ResponseEntity<TransactionRespBean>(wsRespBean, null, HttpStatus.OK);
    }

}
