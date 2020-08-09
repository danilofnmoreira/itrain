package com.itrain.exception.handler;

import java.util.NoSuchElementException;

import com.itrain.common.mapper.ErrorResponseMapper;
import com.itrain.common.payload.ErrorResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final var errorResponse = ErrorResponseMapper.createFrom(ex, status, getPath(request));
        logErrorResponse(errorResponse, ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { Exception.class })
    public ErrorResponse handleInternalServerErrorExceptions(final Exception ex, final WebRequest request) {

        final var errorResponse = ErrorResponseMapper.createFrom(HttpStatus.INTERNAL_SERVER_ERROR, getPath(request));
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { NoSuchElementException.class })
    public ErrorResponse handleNotFoundExceptions(final Exception ex, final WebRequest request) {

        final var errorResponse = ErrorResponseMapper.createFrom(ex, HttpStatus.NOT_FOUND, getPath(request));
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = { BadCredentialsException.class })
    public ErrorResponse handleUnauthorizedExceptions(final Exception ex, final WebRequest request) {

        final var errorResponse = ErrorResponseMapper.createFrom(ex, HttpStatus.UNAUTHORIZED, getPath(request));
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = { LockedException.class,
                                DisabledException.class })
    public ErrorResponse handleForbiddenExceptions(final Exception ex, final WebRequest request) {

        final var errorResponse = ErrorResponseMapper.createFrom(ex, HttpStatus.FORBIDDEN, getPath(request));
        logErrorResponse(errorResponse, ex);
        return errorResponse;
    }

    private void logErrorResponse(final ErrorResponse errorResponse, final Exception e) {

        log.error("Response for error\n---> {}", errorResponse, e);
    }

    private String getPath(final WebRequest request) {

        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

}