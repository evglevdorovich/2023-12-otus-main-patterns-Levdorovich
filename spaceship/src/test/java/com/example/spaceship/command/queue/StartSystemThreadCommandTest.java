package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.event.DestroyEvent;
import com.example.spaceship.queue.SystemThread;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class StartSystemThreadCommandTest {
    @Test
    void shouldStartSystemThread() {
        var destroyEvent = new DestroyEvent();
        var queue = new ArrayBlockingQueue<Command>(100);

        var systemThread = new SystemThread(queue, destroyEvent::finish);

        new StartSystemThreadCommand(systemThread).execute();

        systemThread.stop();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());
    }

}
