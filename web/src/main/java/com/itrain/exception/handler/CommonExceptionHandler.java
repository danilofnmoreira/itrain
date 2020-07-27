package com.itrain.exception.handler;

import java.util.NoSuchElementException;

import com.itrain.mapper.ErrorResponseMapper;
import com.itrain.payload.api.v1.response.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errorResponse = ErrorResponseMapper.createFrom(ex, status, ((ServletWebRequest) request).getRequest().getRequestURI());
        logErrorResponse(errorResponse, ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { Exception.class })
    public ErrorResponse handleInternalServerErrorExceptions(Exception ex, WebRequest request) {

        var errorResponse = ErrorResponseMapper.createFrom(HttpStatus.INTERNAL_SERVER_ERROR, ((ServletWebRequest) request).getRequest().getRequestURI());
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { NoSuchElementException.class })
    public ErrorResponse handleNotFoundExceptions(Exception ex, WebRequest request) {

        var errorResponse = ErrorResponseMapper.createFrom(HttpStatus.NOT_FOUND, ((ServletWebRequest) request).getRequest().getRequestURI());
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    private void logErrorResponse(ErrorResponse errorResponse, Exception e) {
        log.error("Response for error\n---> {}", errorResponse, e);
    }
}