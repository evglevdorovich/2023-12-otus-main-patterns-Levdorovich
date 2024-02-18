package com.example.spaceship.command.adapter;

import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.function.BiFunction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class AdapterRegisterCreatorCommandTest {
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
        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            new AdapterRegisterCreatorCommand().execute();
            ioC.verify(() -> {
                IoC.resolve("IoC.Scope.Current");
                IoC.resolve(eq("IoC.Register"), eq("Adapter.Register"), any(BiFunction.class));
            });
        }
    }

}
