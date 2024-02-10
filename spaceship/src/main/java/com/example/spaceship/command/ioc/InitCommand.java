package com.example.spaceship.command.ioc;

import com.example.spaceship.command.Command;

public record InitCommand() implements Command {
    private static final ThreadLocal<Object> SCOPE = new ThreadLocal<>();

    public static Object getCurrentScope() {
        return SCOPE.get();
    }

    public static void setCurrentScope(Object scope) {
        SCOPE.set(scope);
    }

    @Override
    public void execute() {

    }
}
