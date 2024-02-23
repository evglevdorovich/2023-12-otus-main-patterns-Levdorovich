package com.example.spaceship.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.event.DestroyEvent;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueSystemThreadTest {
    @Mock
    private Command command;
    @Mock
    private Queue<Command> commands;
    @InjectMocks
    private QueueSystemThread queueSystemThread;

    @Test
    void shouldMadeReadyForStop() {
        queueSystemThread.stop();

        var actualReadyToStop = queueSystemThread.isReadyToStop();
        assertThat(actualReadyToStop).isTrue();
    }

    @Test
    void shouldStopStartedThread() {
        var destroyEvent = new DestroyEvent();
        var testee = new QueueSystemThread(commands, destroyEvent::finish);

        testee.start();
        // make sure it's executed at least once
        when(commands.poll()).thenAnswer((invocation) -> {
            testee.stop();
            return command;
        });

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());
    }

    @Test
    void shouldUpdateBehaviour() {
        var destroyEvent = new DestroyEvent();
        var testee = new QueueSystemThread(commands);

        testee.updateBehaviour(destroyEvent::finish);
        testee.start();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());
    }
}
