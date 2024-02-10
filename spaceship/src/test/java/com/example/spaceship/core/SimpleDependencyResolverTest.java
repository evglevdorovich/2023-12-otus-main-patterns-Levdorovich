package com.example.spaceship.core;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleDependencyResolverTest {
    private static final String PARENT_SCOPE_KEY = "IoC.Scope.Parent";
    private static final String DEPENDENCY_NAME = "dependencyName";
    private static final String SCOPE_ID = "scope-id";
    private static final Function<Object[], Object> DEPENDENCY_STRATEGY = a -> a[0];

    @Test
    void shouldCorrectlyResolveDependency() {
        var expectedResolution = "dependency argument";
        var scope = new Scope(SCOPE_ID, Map.of(DEPENDENCY_NAME, DEPENDENCY_STRATEGY));

        var simpleDependencyResolverTest = new SimpleDependencyResolver(scope);

        var actualResolution = simpleDependencyResolverTest.resolve(DEPENDENCY_NAME, new Object[]{expectedResolution});

        assertThat(actualResolution).isEqualTo(expectedResolution);
    }

    @Test
    void shouldCorrectlyResolveDependencyFromParent() {
        var expectedResolution = "dependency argument";
        Scope currentScope = getCurrentScopeWithParents();

        var simpleDependencyResolverTest = new SimpleDependencyResolver(currentScope);

        var actualResolution = simpleDependencyResolverTest.resolve(DEPENDENCY_NAME, new Object[]{expectedResolution});

        assertThat(actualResolution).isEqualTo(expectedResolution);
    }

    private static Scope getCurrentScopeWithParents() {
        Scope lastParent = new Scope("lastParent", Map.of(DEPENDENCY_NAME, DEPENDENCY_STRATEGY));
        Scope firstParent = new Scope("firstParent", Map.of(PARENT_SCOPE_KEY, a -> lastParent));
        return new Scope("current", Map.of(PARENT_SCOPE_KEY, a -> firstParent));
    }

}
