package com.example.spaceship.command;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.queue.RegisterCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.OperationRequest;
import com.example.spaceship.model.PlayerActionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class InterpretCommandTest extends IoCSetUpTest {
    private static final String GAME_ID = "gameId";
    private static final String PLAYER_ID = "playerId";
    private static final String OPERATION_ID = "operationId";

    @Test
    void shouldPutCommandInQueue() {

        var operationRequest = new OperationRequest(OPERATION_ID, new Object[]{});
        var playerActionRequest = new PlayerActionRequest(GAME_ID, PLAYER_ID, operationRequest);
        var gameObject = new Object();
        var command = createCommand();
        var putInQueueCommand = Mockito.mock(RegisterCommand.class);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("GameObject", playerActionRequest.getGameId(), playerActionRequest.getPlayerId()))
                    .thenReturn(gameObject);
            ioC.when(() -> IoC.resolve(operationRequest.getId(), gameObject, operationRequest.getArgs()))
                    .thenReturn(command);
            ioC.when(() -> IoC.resolve("Queue.Register", playerActionRequest.getGameId(), command))
                    .thenReturn(putInQueueCommand);

            new InterpretCommand(playerActionRequest).execute();

            ioC.verify(() -> IoC.resolve("GameObject.Commands.Validate", playerActionRequest.getGameId(),
                    playerActionRequest.getPlayerId(), operationRequest.getId()));
        }
        verify(putInQueueCommand).execute();
    }

    private static Command createCommand() {
        return () -> {

        };
    }

}
