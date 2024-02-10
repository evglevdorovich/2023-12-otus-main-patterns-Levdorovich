package com.example.spaceship.command.scope;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class InitCommandTest {
    @Test
    void shouldSetScope() {
        var expectedScope = new Scope("id", Collections.emptyMap());
        InitCommand.setCurrentScope(expectedScope);

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }



}
