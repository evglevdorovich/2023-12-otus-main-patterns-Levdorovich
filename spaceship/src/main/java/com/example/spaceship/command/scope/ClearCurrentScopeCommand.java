package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;

public record ClearCurrentScopeCommand() implements Command {

    @Override
    public void execute() {
        InitCommand.setCurrentScope(null);
    }
}
