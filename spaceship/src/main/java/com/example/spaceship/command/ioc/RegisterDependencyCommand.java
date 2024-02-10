package com.example.spaceship.command.ioc;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.IoC;

import java.util.Map;
import java.util.function.Function;

public record RegisterDependencyCommand(String dependency, Function<Object[], Object> dependencyResolution) implements Command {

    @Override
    public void execute() {
        Map<String, Function<Object[], Object>> currentScope = IoC.resolve("IoC.Scope.Current", new Object[]{});
        currentScope.put(dependency, dependencyResolution);
    }
}
