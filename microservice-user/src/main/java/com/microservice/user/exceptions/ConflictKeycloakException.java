package com.microservice.user.exceptions;

import lombok.Getter;

@Getter
public class ConflictKeycloakException extends RuntimeException {

    private String message;

    public ConflictKeycloakException(String message) {
        this.message = String.format("Sorry, an unexpected error occurred, Contact your system admin. Error: %s", message);
    }

}
