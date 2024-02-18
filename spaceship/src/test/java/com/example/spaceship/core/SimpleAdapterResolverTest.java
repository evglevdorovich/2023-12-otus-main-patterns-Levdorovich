package com.example.spaceship.core;

import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class SimpleAdapterResolverTest {
    @BeforeEach
    void init() {
        new InitCommand().execute();
        var scope = IoC.resolve("IoC.Scope.Create", "test");
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
    }

    @AfterEach
    void cleanUp() {
        InitCommand.setAlreadyExecuted(false);
        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();
        IoC.clear();
    }

    @Test
    void shouldResolveObject() {
        var simpleAdapterResolver = new SimpleAdapterResolver();
        var interfaceName = String.class.getName();
        var object = new Object();
        var adapteredObject = new Object();

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> {
                IoC.resolve("Adapter." + interfaceName + "Adapter", object);
            }).thenReturn(adapteredObject);
            var actualResolvedAdapteredObject = simpleAdapterResolver.resolve(interfaceName, object);
            assertThat(actualResolvedAdapteredObject).isEqualTo(adapteredObject);
        }
    }
}
