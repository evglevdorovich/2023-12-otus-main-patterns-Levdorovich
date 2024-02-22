package com.example.spaceship.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DestroyEventTest {
    @Test
    void shouldFinish() {
        var destroyEvent = new DestroyEvent();

        destroyEvent.finish();
        var actualFinished = destroyEvent.isFinished();

        assertThat(actualFinished).isTrue();
    }
}
