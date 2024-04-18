package com.microservice.user.exceptions;

import lombok.Getter;

@Getter
public class ConflictExistException extends RuntimeException {

    private String message;

    public ConflictExistException(String entity, String attribute, String value) {
        this.message = String.format("There is already a %s entity with %s %s.", entity, attribute, value);
    }

}
