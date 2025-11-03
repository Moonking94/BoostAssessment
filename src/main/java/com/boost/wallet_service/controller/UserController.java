package com.boost.wallet_service.controller;

import com.boost.wallet_service.annotation.Idempotent;
import com.boost.wallet_service.constant.Constants;
import com.boost.wallet_service.dto.UserServiceReqBean;
import com.boost.wallet_service.dto.UserServiceRespBean;
import com.boost.wallet_service.service.user.IUserService;
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

import static com.boost.wallet_service.constant.Constants.ENDPOINT_USER_CREATE;

@RestController
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("/api/userController")
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired private IUserService service;

    @Autowired private Gson gson = new Gson();

    public UserController() {}

    @PostMapping(value = "create", headers = "accept=application/json")
    @Idempotent(endpoint = ENDPOINT_USER_CREATE)
    public ResponseEntity<?> create(@RequestBody UserServiceReqBean wsReqBean, @RequestHeader("Idempotency-Key") String idempotencyKey
            , HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        UUID uuid = UUID.randomUUID();
        UserServiceRespBean wsRespBean;
        try {
            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_REQUEST.replace("[string]", gson.toJson(wsReqBean)));

            wsRespBean = service.create(wsReqBean, idempotencyKey);

            log.info((servletRequest.getServletPath() + " : [ID]").replace("ID", uuid.toString()) + " | " + Constants.FLAG_RESPONSE.replace("[string]", gson.toJson(wsRespBean)));
        } catch (Exception e) {
            wsRespBean = new UserServiceRespBean();
            wsRespBean.setErrorMsg(e.getMessage());
        }

        return new ResponseEntity<UserServiceRespBean>(wsRespBean, null, HttpStatus.OK);
    }
}
