package com.example.spaceship.core;

import com.example.spaceship.model.core.Scope;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleDependencyResolver implements DependencyResolver {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
    private final Scope scope;

    @Override
    public Object resolve(String dependencyName, Object[] args) {
        var currentScope = scope;

        while (true) {
            if (!currentScope.containsDependencyResolution(dependencyName)) {
                try {
                    currentScope = (Scope) currentScope.getDependency("IoC.Scope.Parent").apply(new Object[]{});
                } catch (Exception e) {
                    IoC.resolve(SimpleDependencyResolver.class.getSimpleName() + EXCEPTION_HANDLER_POSTFIX, e);
                }
            } else {
                return currentScope.getDependency(dependencyName).apply(args);
            }
        }
    }
}
