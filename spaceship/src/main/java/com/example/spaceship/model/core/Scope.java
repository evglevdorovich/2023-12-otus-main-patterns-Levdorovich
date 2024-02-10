package com.example.spaceship.model.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class Scope {
    private final String id;
    @Getter
    private final Map<String, Function<Object[], Object>> dependencyResolutions;

    public Function<Object[], Object> put(String dependencyName, Function<Object[], Object> expectedDependencyResolution) {
        return dependencyResolutions.put(dependencyName, expectedDependencyResolution);
    }
}
