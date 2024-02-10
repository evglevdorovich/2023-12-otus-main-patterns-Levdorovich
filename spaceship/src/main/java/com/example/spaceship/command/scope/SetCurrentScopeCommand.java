package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.ioc.InitCommand;

public record SetCurrentScopeCommand(Object scope) implements Command {
    @Override
    public void execute() {
        InitCommand.setCurrentScope(scope);
    }
}
