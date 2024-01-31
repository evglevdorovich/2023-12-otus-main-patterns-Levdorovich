package com.example.spaceship.command;

public class RetryCommand implements Command {
    private final Command command;

    public RetryCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        command.execute();
    }
}
