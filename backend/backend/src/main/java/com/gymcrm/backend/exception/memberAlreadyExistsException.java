package com.gymcrm.backend.exception;

public class memberAlreadyExistsException extends RuntimeException{
    public memberAlreadyExistsException(String message) {
        super(message);
    }
}
