package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;
import com.example.spaceship.model.core.Scope;

public record SetCurrentScopeCommand(Scope scope) implements Command {
    @Override
    public void execute() {
        InitCommand.setCurrentScope(scope);
    }
}
