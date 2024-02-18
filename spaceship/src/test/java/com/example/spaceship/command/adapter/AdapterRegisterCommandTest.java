package com.example.spaceship.command.adapter;

import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.mockStatic;

class AdapterRegisterCommandTest {

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
    void shouldRegisterAdapters() {
        List<Class<?>> adapters = List.of(Integer.class, Long.class);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            new AdapterRegisterCommand(adapters).execute();
            ioC.verify(() -> {
                IoC.resolve("Adapter.Register", adapters);
            });
        }
    }

}
