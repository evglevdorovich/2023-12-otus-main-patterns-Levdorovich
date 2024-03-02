package com.example.spaceship.service.queue;

import com.example.spaceship.command.Command;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class GameQueueStorageLocalTest {
    private static final String GAME_ID = "gameId";

    @Test
    void shouldGetQueue() {
        var expectedQueue = new ArrayBlockingQueue<Command>(100);

        Map<String, Queue<Command>> commands = Map.of(GAME_ID, expectedQueue);

        var actualQueue = new GameQueueStorageLocal(commands).get(GAME_ID);

        assertThat(actualQueue).isEqualTo(expectedQueue);
    }

    @Test
    void shouldRegisterCommandInQueue() {
        var queue = new ArrayBlockingQueue<Command>(100);
        var expectedCommand = createCommand();
        Map<String, Queue<Command>> commands = new HashMap<>();
        commands.put(GAME_ID, queue);
        var queueStorage = new GameQueueStorageLocal(commands);

        queueStorage.register(GAME_ID, expectedCommand);

        assertThat(queue).containsExactly(expectedCommand);
    }

    private static Command createCommand() {
        return () -> {

        };
    }

}
