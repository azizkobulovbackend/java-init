package com.example.my_project.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("Asia/Tashkent"));

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        Map<String, Object> errorResponse = new HashMap<>();
        String formattedTimestamp = formatter.format(Instant.now());

        errorResponse.put("timestamp", formattedTimestamp);
        errorResponse.put("status", ex.getStatusCode().value());
        errorResponse.put("error", ex.getReason());
        errorResponse.put("path", request.getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
