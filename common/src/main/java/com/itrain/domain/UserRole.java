package com.itrain.domain;

public enum UserRole {

    USER, ADMIN, CLIENT, GYM, PERSONAL_TRAINER;

    public static UserRole getByName(String name) {
        for(var value : values()) {
            if(value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("There are no %s with the given name, %s.", UserRole.class.getSimpleName(), name));
    }

}