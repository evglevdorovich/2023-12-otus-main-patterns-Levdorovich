package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;

public record SetCurrentScopeCommand(Object scope) implements Command {
    @Override
    public void execute() {
        InitCommand.setCurrentScope(scope);
    }
}
