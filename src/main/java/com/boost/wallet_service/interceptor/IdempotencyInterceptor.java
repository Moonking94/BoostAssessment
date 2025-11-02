package com.boost.wallet_service.interceptor;

import com.boost.wallet_service.annotation.Idempotent;
import com.boost.wallet_service.model.IdempotencyRecordsEntity;
import com.boost.wallet_service.repository.idempotencyRecords.IdempotencyRecordsDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;

@Component
public class IdempotencyInterceptor implements HandlerInterceptor {

    @Autowired private IdempotencyRecordsDao idempotencyRecordsDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod method)) {
            return true; // Skip if not a controller method
        }

        Idempotent annotation = method.getMethodAnnotation(Idempotent.class);
        if (annotation == null) {
            return true; // No idempotency on this method
        }

        String idempotencyKey = request.getHeader("Idempotency-Key");
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Idempotency-Key header");
            return false;
        }

        String endpoint = annotation.endpoint();

        Optional<IdempotencyRecordsEntity> existing = idempotencyRecordsDao
                .findByIdempotencyKeyAndEndpoint(idempotencyKey, endpoint);

        if (existing.isPresent()) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(existing.get().getResponsePayload());
            return false;
        }

        // Let request proceed
        return true;
    }

}
