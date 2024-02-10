package com.example.spaceship.command.ioc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InitCommandTest {
    @Test
    void shouldSetScope() {
        var expectedScope = new Object();
        InitCommand.setCurrentScope(expectedScope);

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }

}
