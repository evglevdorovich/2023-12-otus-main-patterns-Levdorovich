package com.example.spaceship.command.ioc;

import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
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
        var currentScope = new Scope("id", new HashMap<>());
        String dependency = "dependencyName";
        Function<Object[], Object> dependencyResolution = a -> a;
        var expectedCurrentDependencyResolutions = Map.of(dependency, dependencyResolution);

        var registerDependencyCommand = new RegisterDependencyCommand(dependency, dependencyResolution);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(IoC.resolve("IoC.Scope.Current")).thenReturn(currentScope);
            registerDependencyCommand.execute();
        }

        assertThat(currentScope.dependencyResolutions()).containsExactlyInAnyOrderEntriesOf(expectedCurrentDependencyResolutions);

    }

}
