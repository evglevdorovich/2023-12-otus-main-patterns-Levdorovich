package com.example.spaceship.model.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ScopeTest {
    @Test
    void shouldPutDependencyResolutionToScope() {
        Function<Object[], Object> expectedDependencyResolution = a -> a;
        var dependencyName = "dependencyName";
        var expectedDependencyResolutions = Map.of(dependencyName, expectedDependencyResolution);

        var scope = new Scope("id", new HashMap<>());
        scope.put(dependencyName, expectedDependencyResolution);

        var actualDependencyResolutions = scope.getDependencyResolutions();

        assertThat(actualDependencyResolutions).containsExactlyInAnyOrderEntriesOf(expectedDependencyResolutions);
    }

}
