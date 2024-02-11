package com.example.spaceship.model.core;

import java.util.Map;
import java.util.function.Function;

public record Scope(String id, Map<String, Function<Object[], Object>> dependencyResolutions) {
    public void put(String dependencyName, Function<Object[], Object> expectedDependencyResolution) {
        dependencyResolutions.put(dependencyName, expectedDependencyResolution);
    }

    public boolean containsDependencyResolution(String dependencyResolutionName) {
        return dependencyResolutions.containsKey(dependencyResolutionName);
    }

    public Function<Object[], Object> getDependency(String dependencyResolutionName) {
        return dependencyResolutions.get(dependencyResolutionName);
    }
}
