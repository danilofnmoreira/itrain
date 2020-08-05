package com.itrain.client.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings(value = { "serial" })
@NoArgsConstructor
public class ContactOwnershipConflictException extends RuntimeException {

    public ContactOwnershipConflictException(final String msg) {
        super(msg);
    }
}