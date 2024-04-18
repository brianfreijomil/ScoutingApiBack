package com.microservice.user.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String message;

    public NotFoundException(String entity, String attribute, String value) {
        this.message = String.format("The entity %s is not found with the %s %s", entity, attribute, value);
    }
}
