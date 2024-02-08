package com.example.spaceship.command;

import java.util.function.BiFunction;
import java.util.function.Function;

public record UpdateIoCResolveDependencyStrategyCommand(
        Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>> updateDependencyStrategy) implements Command {

    @Override
    public void execute() {

    }
}
