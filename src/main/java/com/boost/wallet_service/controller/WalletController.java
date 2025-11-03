package com.boost.wallet_service.controller;

import com.boost.wallet_service.annotation.Idempotent;
import com.boost.wallet_service.constant.Constants;
import com.boost.wallet_service.dto.WalletServiceReqBean;
import com.boost.wallet_service.dto.WalletServiceRespBean;
import com.boost.wallet_service.service.wallet.IWalletService;
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

import static com.boost.wallet_service.constant.Constants.*;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("api/walletController")
public class WalletController {

    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired private IWalletService service;

    @Autowired private Gson gson = new Gson();

    public WalletController() {}

    @PostMapping(value = "credit", headers = "accept=application/json")
    @Idempotent(endpoint = ENDPOINT_WALLET_CREDIT)
    public ResponseEntity<?> credit(@RequestBody WalletServiceReqBean wsReqBean, @RequestHeader("Idempotency-Key") String idempotencyKey
            , HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        UUID uuid = UUID.randomUUID();
        WalletServiceRespBean wsRespBean;
        try {
            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_REQUEST.replace("[string]", gson.toJson(wsReqBean)));

            wsRespBean = service.credit(wsReqBean, idempotencyKey);

            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_RESPONSE.replace("[string]", gson.toJson(wsRespBean)));
        } catch (Exception e) {
            wsRespBean = new WalletServiceRespBean();
            wsRespBean.setErrorMsg(e.getMessage());
        }

        return new ResponseEntity<WalletServiceRespBean>(wsRespBean, null, HttpStatus.OK);
    }

    @PostMapping(value = "debit", headers = "accept=application/json")
    @Idempotent(endpoint = ENDPOINT_WALLET_DEBIT)
    public ResponseEntity<?> debit(@RequestBody WalletServiceReqBean wsReqBean, @RequestHeader("Idempotency-Key") String idempotencyKey
            , HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        UUID uuid = UUID.randomUUID();
        WalletServiceRespBean wsRespBean;
        try {
            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_REQUEST.replace("[string]", gson.toJson(wsReqBean)));

            wsRespBean = service.debit(wsReqBean, idempotencyKey);

            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_RESPONSE.replace("[string]", gson.toJson(wsRespBean)));
        } catch (Exception e) {
            wsRespBean = new WalletServiceRespBean();
            wsRespBean.setErrorMsg(e.getMessage());
        }

        return new ResponseEntity<WalletServiceRespBean>(wsRespBean, null, HttpStatus.OK);
    }

    @PostMapping(value = "transfer", headers = "accept=application/json")
    @Idempotent(endpoint = ENDPOINT_WALLET_TRANSFER)
    public ResponseEntity<?> transfer(@RequestBody WalletServiceReqBean wsReqBean, @RequestHeader("Idempotency-Key") String idempotencyKey
            , HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        UUID uuid = UUID.randomUUID();
        WalletServiceRespBean wsRespBean;
        try {
            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_REQUEST.replace("[string]", gson.toJson(wsReqBean)));

            wsRespBean = service.transfer(wsReqBean, idempotencyKey);

            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_RESPONSE.replace("[string]", gson.toJson(wsRespBean)));
        } catch (Exception e) {
            wsRespBean = new WalletServiceRespBean();
            wsRespBean.setErrorMsg(e.getMessage());
        }

        return new ResponseEntity<WalletServiceRespBean>(wsRespBean, null, HttpStatus.OK);
    }

}
