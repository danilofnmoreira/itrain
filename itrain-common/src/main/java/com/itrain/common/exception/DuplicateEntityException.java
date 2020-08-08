package com.itrain.common.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings(value = { "serial" })
@NoArgsConstructor
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(final String msg) {
        super(msg);
    }
}