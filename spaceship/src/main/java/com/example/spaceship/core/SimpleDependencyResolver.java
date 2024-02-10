package com.example.spaceship.core;

import com.example.spaceship.model.core.Scope;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleDependencyResolver implements DependencyResolver {
    private final Scope scope;

    @Override
    public Object resolve(String dependencyName, Object[] args) {
        var currentScope = scope;

        while (true) {
            if (!currentScope.containsDependencyResolution(dependencyName)) {
                currentScope = (Scope) currentScope.getDependency("IoC.Scope.Parent").apply(new Object[]{});
            } else {
                return currentScope.getDependency(dependencyName).apply(args);
            }
        }

    }
}
