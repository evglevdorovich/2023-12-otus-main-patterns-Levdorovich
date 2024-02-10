package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.ioc.InitCommand;

public record ClearCurrentScopeCommand() implements Command {

    @Override
    public void execute() {
        InitCommand.setCurrentScope(null);
    }
}
