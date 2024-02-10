package com.example.spaceship.command.scope;

import com.example.spaceship.command.ioc.InitCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetCurrentScopeCommandTest {
    @Test
    void shouldSetCurrentScope() {
        var expectedScopeToSet = new Object();
        var setCurrentScopeCommand = new SetCurrentScopeCommand(expectedScopeToSet);

        setCurrentScopeCommand.execute();
        var actualCurrentScope = InitCommand.getCurrentScope();

        assertThat(actualCurrentScope).isEqualTo(expectedScopeToSet);
    }

}
