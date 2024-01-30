package com.example.spaceship.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogCommand {
    private final Exception exception;
    public LogCommand(Exception exception) {
        this.exception = exception;
    }

    public void execute() {
        log.warn(exception.getMessage());
    }
}
