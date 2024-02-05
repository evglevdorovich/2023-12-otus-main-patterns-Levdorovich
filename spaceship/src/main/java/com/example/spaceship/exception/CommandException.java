package com.example.spaceship.exception;

public class CommandException extends RuntimeException {

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
