package com.example.spaceship.command.scope;

import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class InitialisedIoCIT {

    @BeforeEach
    void init() {
        new InitCommand().execute();
    }

    @AfterEach
    void clean() {
        InitCommand.setCurrentScope(null);
        InitCommand.isAlreadyExecuted = false;
        IoC.clear();
    }

    @Test
    void shouldCorrectlySetScope() {
        var expectedScope = new Scope("Id", Map.of());

        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", expectedScope).execute();

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    void shouldCorrectlyClearScope() {
        InitCommand.setCurrentScope(InitCommand.ROOT_SCOPE);

        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isNull();
    }

    @Test
    void shouldReturnRootScopeWhenWithoutCurrent() {
        var expectedScope = InitCommand.ROOT_SCOPE;

        var actualScope = IoC.resolve("IoC.Scope.Current");

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    void shouldReturnCorrectScope() {
        var scope = IoC.<Scope>resolve("IoC.Scope.Create", "id", InitCommand.ROOT_SCOPE);
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();

        var actualScope = IoC.<Scope>resolve("IoC.Scope.Current");

        assertThat(actualScope).isEqualTo(scope);
    }


}
