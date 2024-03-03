package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.queue.GameQueueStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class RegisterCommandTest {

    @Test
    void shouldRegisterCommand() {
        var gameId = "gameId";
        var command = createCommand();
        var queueStorage = Mockito.mock(GameQueueStorage.class);

        new RegisterCommand(queueStorage, gameId, command).execute();

        verify(queueStorage).register(gameId, command);
    }

    private static Command createCommand() {
        return () -> {

        };
    }
}
