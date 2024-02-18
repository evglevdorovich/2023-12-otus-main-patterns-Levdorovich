package com.example.spaceship.core;

import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IoCIT {
    @BeforeAll
    static void init() {
        new InitCommand().execute();
        var scope = IoC.resolve("IoC.Scope.Create", "test");
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
        InitCommand.setAlreadyExecuted(false);
        IoC.clear();
    }

    @AfterEach
    void cleanUp() {
        InitCommand.setAlreadyExecuted(false);
        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();
        IoC.clear();
    }

    @Test
    void shouldLoadAdapters() {

    }
}
