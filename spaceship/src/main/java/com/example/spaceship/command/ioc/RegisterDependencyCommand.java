package com.example.spaceship.command.ioc;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;

import java.util.function.Function;

public record RegisterDependencyCommand(String dependency, Function<Object[], Object> dependencyResolution) implements Command {

    @Override
    public void execute() {
        Scope scope = IoC.resolve("IoC.Scope.Current");
        scope.put(dependency, dependencyResolution);
    }
}
