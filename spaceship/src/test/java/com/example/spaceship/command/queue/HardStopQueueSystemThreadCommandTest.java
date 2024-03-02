package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.event.DestroyEvent;
import com.example.spaceship.queue.QueueSystemThread;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class HardStopQueueSystemThreadCommandTest {
    @Test
    void shouldStopSystemThreadCommand() {
        var destroyEvent = new DestroyEvent();
        var queue = new ArrayBlockingQueue<Command>(100);

        var systemThread = new QueueSystemThread(queue, destroyEvent::finish);

        systemThread.start();

        new HardStopQueueSystemThreadCommand(systemThread).execute();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());
    }

}
