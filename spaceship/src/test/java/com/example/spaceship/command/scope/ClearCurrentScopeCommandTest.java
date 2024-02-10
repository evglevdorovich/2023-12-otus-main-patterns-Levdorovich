package com.example.spaceship.command.scope;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClearCurrentScopeCommandTest {

    @Test
    void shouldClearCurrentScope() {
        InitCommand.setCurrentScope(new Object());
        var clearCurrentScopeCommand = new ClearCurrentScopeCommand();

        clearCurrentScopeCommand.execute();
        var actualCurrentScope = InitCommand.getCurrentScope();

        assertThat(actualCurrentScope).isNull();
    }
}
