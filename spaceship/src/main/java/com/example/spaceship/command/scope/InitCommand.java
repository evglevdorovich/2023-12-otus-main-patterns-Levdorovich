package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;
import com.example.spaceship.model.core.Scope;

public class InitCommand implements Command {
    private static final ThreadLocal<Scope> SCOPE = new ThreadLocal<>();
    private static boolean isAlreadyExecuted = false;

    @Override
    public void execute() {

    }

    static Scope getCurrentScope() {
        return SCOPE.get();
    }

    static void setCurrentScope(Scope scope) {
        SCOPE.set(scope);
    }
}
