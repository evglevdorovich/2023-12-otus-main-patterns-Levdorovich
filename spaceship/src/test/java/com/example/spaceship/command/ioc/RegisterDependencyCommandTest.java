package com.example.spaceship.command.ioc;

import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class RegisterDependencyCommandTest {
    @Test
    void shouldRegisterDependency() {
        var currentScope = new HashMap<String, Function<Object[], Object>>();
        String dependency = "dependencyName";
        Function<Object[], Object> dependencyResolution = a -> a;
        var expectedCurrentScope = Map.of(dependency, dependencyResolution);

        var registerDependencyCommand = new RegisterDependencyCommand(dependency, dependencyResolution);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(IoC.resolve("IoC.Scope.Current", new Object[]{})).thenReturn(currentScope);
            registerDependencyCommand.execute();
        }

        assertThat(currentScope).containsExactlyInAnyOrderEntriesOf(expectedCurrentScope);

    }

}
