package com.itrain.mapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.itrain.payload.api.v1.response.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseMapper {

    //@formatter:off
    public static ErrorResponse createFrom(MethodArgumentNotValidException ex, HttpStatus status, String path) {

        var errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> {
                Map<String, Object> map = new HashMap<>();
                map.put("error_message", e.getDefaultMessage());
                map.put("rejected_value", e.getRejectedValue());
                map.put("field",  e.getField());
                return map;
            })
            .collect(Collectors.toSet());

        return createErrorResponse(status, path, "There are fields errors.", errors);
    }
    //@formatter:on

    public static ErrorResponse createFrom(HttpStatus status, String path) {

        return createErrorResponse(status, path, "Unexpected error. Try it later.");
    }

    public static ErrorResponse createFrom(Throwable ex, HttpStatus status, String path) {

        return createErrorResponse(status, path, ex.getMessage());
    }

    //@formatter:off
    private static ErrorResponse createErrorResponse(HttpStatus status, String path, String message, Set<Map<String, Object>> errors) {

        return ErrorResponse
            .builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .statusError(status.getReasonPhrase())
            .path(path)
            .message(message)
            .errors(errors)
            .build();
    }
    //@formatter:on

    private static ErrorResponse createErrorResponse(HttpStatus status, String path, String message) {

        return createErrorResponse(status, path, message, null);
    }

}