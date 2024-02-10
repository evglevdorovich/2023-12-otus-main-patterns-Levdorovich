package com.example.spaceship.command.scope;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class SetCurrentScopeCommandTest {
    @Test
    void shouldSetCurrentScope() {
        var expectedScopeToSet = new Scope("id", Collections.emptyMap());
        var setCurrentScopeCommand = new SetCurrentScopeCommand(expectedScopeToSet);

        setCurrentScopeCommand.execute();
        var actualCurrentScope = InitCommand.getCurrentScope();

        assertThat(actualCurrentScope).isEqualTo(expectedScopeToSet);
    }

}
