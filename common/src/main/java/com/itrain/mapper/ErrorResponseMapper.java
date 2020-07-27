package com.itrain.mapper;

import java.time.LocalDateTime;
import java.util.HashMap;
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

        return ErrorResponse
            .builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .statusError(status.getReasonPhrase())
            .message("There are fields errors.")
            .path(path)
            .errors(ex.getBindingResult()
                      .getFieldErrors()
                      .stream()
                      .map(e -> {
                          var map = new HashMap<String, Object>();
                          map.put("error_message", e.getDefaultMessage());
                          map.put("rejected_value", e.getRejectedValue());
                          map.put("field",  e.getField());
                          return map;
                      })
                      .collect(Collectors.toSet()))
            .build();
    }
    //@formatter:on

    //@formatter:off
    public static ErrorResponse createFrom(HttpStatus status, String path) {

        return ErrorResponse
            .builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .statusError(status.getReasonPhrase())
            .message("Unexpected error")
            .path(path)
            .build();
    }
    //@formatter:on

}