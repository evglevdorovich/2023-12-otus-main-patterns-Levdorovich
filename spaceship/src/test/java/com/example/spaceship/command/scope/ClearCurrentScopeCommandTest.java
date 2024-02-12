package com.example.spaceship.command.scope;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ClearCurrentScopeCommandTest {

    @Test
    void shouldClearCurrentScope() {
        InitCommand.setCurrentScope(new Scope("current", Collections.emptyMap()));
        var clearCurrentScopeCommand = new ClearCurrentScopeCommand();

        clearCurrentScopeCommand.execute();
        var actualCurrentScope = InitCommand.getCurrentScope();

        assertThat(actualCurrentScope).isNull();
    }
}
