package com.example.spaceship.command.ioc;

import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateIoCResolveDependencyStrategyCommandTest {
    @AfterEach
    void cleanUp() {
        IoC.clear();
    }

    @Test
    void shouldUpdateIoCDependencyStrategy() {
        BiFunction<String, Object[], Object> expectedDependencyStrategy = (str, args) -> new Object();
        Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>> updateDependencyStrategy =
                a -> expectedDependencyStrategy;
        var updateIoCDependencyStrategyCommand = new UpdateIoCResolveDependencyStrategyCommand(updateDependencyStrategy);

        updateIoCDependencyStrategyCommand.execute();
        var actualDependencyStrategy = IoC.getDependencyStrategy();

        assertThat(actualDependencyStrategy).isEqualTo(expectedDependencyStrategy);
    }

}
