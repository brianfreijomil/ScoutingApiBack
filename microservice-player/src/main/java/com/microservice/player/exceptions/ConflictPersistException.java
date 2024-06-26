package com.microservice.player.exceptions;

import lombok.Getter;

@Getter
public class ConflictPersistException extends RuntimeException{

    private String message;

    public ConflictPersistException(String action, String entity, String attribute, String value, String errorMSG) {
        this.message = String.format("Cannot %s entity with %s %s, due to error: %s", action, entity, attribute, value, errorMSG);
    }
}
