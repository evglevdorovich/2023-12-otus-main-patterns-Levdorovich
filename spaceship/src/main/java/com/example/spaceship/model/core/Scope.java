package com.example.spaceship.model.core;

import java.util.Map;
import java.util.function.Function;

public record Scope(String id, Map<String, Function<Object[], Object>> dependencyResolutions) {
    public Function<Object[], Object> put(String dependencyName, Function<Object[], Object> expectedDependencyResolution) {
        return dependencyResolutions.put(dependencyName, expectedDependencyResolution);
    }

    public boolean containsDependencyResolution(String dependencyResolutionName) {
        return dependencyResolutions.containsKey(dependencyResolutionName);
    }

    public Function<Object[], Object> getDependency(String dependencyResolutionName) {
        return dependencyResolutions.get(dependencyResolutionName);
    }
}
