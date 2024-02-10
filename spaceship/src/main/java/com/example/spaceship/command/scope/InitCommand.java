package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;

public record InitCommand() implements Command {
    private static final ThreadLocal<Object> SCOPE = new ThreadLocal<>();

    @Override
    public void execute() {

    }

    static Object getCurrentScope() {
        return SCOPE.get();
    }

    static void setCurrentScope(Object scope) {
        SCOPE.set(scope);
    }
}
