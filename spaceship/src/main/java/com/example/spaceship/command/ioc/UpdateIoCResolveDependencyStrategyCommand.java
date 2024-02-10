package com.example.spaceship.command.ioc;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.IoC;

import java.util.function.BiFunction;
import java.util.function.Function;

public record UpdateIoCResolveDependencyStrategyCommand(
        Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>> updateDependencyStrategy) implements Command {

    @Override
    public void execute() {
        var updatedDependencyStrategy = updateDependencyStrategy.apply(IoC.getDependencyStrategy());
        IoC.setDependencyStrategy(updatedDependencyStrategy);
    }
}
