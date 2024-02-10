package com.example.spaceship.service;

import com.example.spaceship.command.ioc.UpdateIoCResolveDependencyStrategyCommand;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class IoC<T> {

    @SuppressWarnings("unchecked")
    @Getter
    @Setter
    private static BiFunction<String, Object[], Object> dependencyStrategy = (dependencyName, args) -> {

        if (!Objects.equals(dependencyName, UpdateIoCResolveDependencyStrategyCommand.class.getSimpleName())) {
            throw new IllegalArgumentException("cannot find dependency with name: " + dependencyName);
        }

        return new UpdateIoCResolveDependencyStrategyCommand(
                (Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>>) args[0]);
    };

    @SuppressWarnings("unchecked")
    public T resolve(String dependency, Object[] args) {
        return (T) dependencyStrategy.apply(dependency, args);
    }
}
