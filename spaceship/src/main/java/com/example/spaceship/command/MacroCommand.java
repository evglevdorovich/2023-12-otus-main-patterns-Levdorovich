package com.example.spaceship.command;

import com.example.spaceship.exception.CommandException;

import java.util.List;

public record MacroCommand(List<Command> commands) implements Command {
    @Override
    public void execute() {
        try {
            commands.forEach(Command::execute);
        } catch (Exception e) {
            throw new CommandException("exception is thrown during macro command execution", e);
        }
    }
}
