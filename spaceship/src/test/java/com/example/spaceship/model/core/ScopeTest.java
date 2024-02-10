package com.example.spaceship.model.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ScopeTest {
    private static final String EXISTING_DEPENDENCY_RESOLUTION_NAME = "existed";
    private static final Function<Object[], Object> DEPENDENCY_RESOLUTION = a -> a;
    private static final String SCOPE_ID = "id";

    @Test
    void shouldPutDependencyResolutionToScope() {
        var expectedDependencyResolutions = Map.of(EXISTING_DEPENDENCY_RESOLUTION_NAME, DEPENDENCY_RESOLUTION);

        var scope = new Scope(SCOPE_ID, new HashMap<>());
        scope.put(EXISTING_DEPENDENCY_RESOLUTION_NAME, DEPENDENCY_RESOLUTION);

        var actualDependencyResolutions = scope.getDependencyResolutions();

        assertThat(actualDependencyResolutions).containsExactlyInAnyOrderEntriesOf(expectedDependencyResolutions);
    }

    @Test
    void shouldReturnContainsIfContainsDependency() {
        var scope = new Scope(SCOPE_ID, Map.of(EXISTING_DEPENDENCY_RESOLUTION_NAME, DEPENDENCY_RESOLUTION));

        var actualResult = scope.containsDependencyResolution(EXISTING_DEPENDENCY_RESOLUTION_NAME);

        assertThat(actualResult).isTrue();

    }

    @Test
    void shouldReturnNotContainsIfContainsDependency() {
        var scope = new Scope(SCOPE_ID, Map.of(EXISTING_DEPENDENCY_RESOLUTION_NAME, DEPENDENCY_RESOLUTION));

        var actualResult = scope.containsDependencyResolution("not existing");

        assertThat(actualResult).isFalse();

    }

    @Test
    void shouldReturnCorrectResolutionDependency() {
        var scope = new Scope(SCOPE_ID, Map.of(EXISTING_DEPENDENCY_RESOLUTION_NAME, DEPENDENCY_RESOLUTION));

        var actualResult = scope.getDependency(EXISTING_DEPENDENCY_RESOLUTION_NAME);

        assertThat(actualResult).isEqualTo(DEPENDENCY_RESOLUTION);

    }

}
