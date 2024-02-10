package com.example.spaceship.core;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class SimpleDependencyResolver implements DependencyResolver {
    private final Map<String, Function<Object[], Object>> scope;

    @Override
    @SuppressWarnings("unchecked")
    public Object resolve(String dependencyName, Object[] args) {
        var currentScope = scope;

        while (true) {
            if (!currentScope.containsKey(dependencyName)) {
                currentScope = (Map<String, Function<Object[], Object>>) currentScope.get("IoC.Scope.Parent").apply(new Object[]{});
            } else {
                return currentScope.get(dependencyName).apply(args);
            }
        }

    }
}
