package com.itrain.auth.domain;

public enum UserRole {

    ROLE_USER, ROLE_ADMIN, ROLE_STUDENT, ROLE_GYM, ROLE_PERSONAL_TRAINER;

    public static UserRole getByName(final String name) {
        for (final var value : values()) {
            if(value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("There are no %s with the given name, %s.", UserRole.class.getSimpleName(), name));
    }

}