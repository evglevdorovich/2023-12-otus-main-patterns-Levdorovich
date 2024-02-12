package com.example.spaceship.core;

import com.example.spaceship.command.ioc.UpdateIoCResolveDependencyStrategyCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IoCTest {
    private static final String UPDATE_DEPENDENCY_STRATEGY_NAME = UpdateIoCResolveDependencyStrategyCommand.class.getSimpleName();

    @AfterEach
    void cleanUp() {
        IoC.clear();
    }

    @Test
    void shouldResolveHandlers() {
        Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>> updateDependencyStrategy = a -> a;
        var args = new Object[]{updateDependencyStrategy};
        var expectedResolvedObject = new UpdateIoCResolveDependencyStrategyCommand(updateDependencyStrategy);

        var actualResolvedObject = IoC.resolve(UPDATE_DEPENDENCY_STRATEGY_NAME, args);
        assertThat(actualResolvedObject).isEqualTo(expectedResolvedObject);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenHandlersNotFound() {
        var anotherDependencyName = "otherDependencyName";

        assertThatThrownBy(() -> IoC.resolve(anotherDependencyName)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldClearContext() {
        Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>> updateDependencyStrategy = a -> a;
        var args = new Object[]{updateDependencyStrategy};
        IoC.resolve(UPDATE_DEPENDENCY_STRATEGY_NAME, args);
        var expectedDependencyStrategy = IoC.getInitialDependencyStrategy();

        IoC.clear();
        var actualDependencyStrategy = IoC.getDependencyStrategy();

        assertThat(actualDependencyStrategy).isEqualTo(expectedDependencyStrategy);

    }

}
