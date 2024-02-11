package com.example.spaceship.core;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class SimpleDependencyResolverTest {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
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

    @Test
    void shouldResolveExceptionWhenCaughtExceptionWhileGettingParent() {
        var unExistingDependency = "dependency argument";
        Scope scopeWithoutParent = new Scope(SCOPE_ID, Map.of());

        var simpleDependencyResolverTest = new SimpleDependencyResolver(scopeWithoutParent);
        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(IoC.resolve(eq(SimpleDependencyResolver.class.getSimpleName() + EXCEPTION_HANDLER_POSTFIX), any(Exception.class)))
                    .thenThrow(RuntimeException.class);
            assertThatThrownBy(() -> simpleDependencyResolverTest.resolve(unExistingDependency, new Object[]{})).isInstanceOf(RuntimeException.class);
        }
    }


    private static Scope getCurrentScopeWithParents() {
        Scope lastParent = new Scope("lastParent", Map.of(DEPENDENCY_NAME, DEPENDENCY_STRATEGY));
        Scope firstParent = new Scope("firstParent", Map.of(PARENT_SCOPE_KEY, a -> lastParent));
        return new Scope("current", Map.of(PARENT_SCOPE_KEY, a -> firstParent));
    }

}
