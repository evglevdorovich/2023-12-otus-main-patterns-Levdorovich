package com.example.spaceship.command;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.queue.RegisterCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import com.example.spaceship.model.OperationRequest;
import com.example.spaceship.model.Shootable;
import com.example.spaceship.model.Spaceship;
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
    void shouldPutShootCommandInQueue() {

        var operationRequest = new OperationRequest(OPERATION_ID, new Object[]{});
        var shootable = new Shootable(){};
        var command = new ShootCommand(shootable);
        var putInQueueCommand = Mockito.mock(RegisterCommand.class);
        var userContext = new UserContext(PLAYER_ID, GAME_ID);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("UserContext")).thenReturn(userContext);
            ioC.when(() -> IoC.resolve("Interpret.Commands.Resolution", operationRequest.getAction(), operationRequest.getArgs()))
                    .thenReturn(command);
            ioC.when(() -> IoC.resolve("Queue.Register", GAME_ID, command))
                    .thenReturn(putInQueueCommand);
            new InterpretCommand(operationRequest).execute();
        }
        verify(putInQueueCommand).execute();
    }

    @Test
    void shouldPutStopMoveCommandInQueue() {

        var operationRequest = new OperationRequest(OPERATION_ID, new Object[]{});
        var command = new StopMoveCommand(new Spaceship());
        var putInQueueCommand = Mockito.mock(RegisterCommand.class);
        var userContext = new UserContext(PLAYER_ID, GAME_ID);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("UserContext")).thenReturn(userContext);
            ioC.when(() -> IoC.resolve("Interpret.Commands.Resolution", operationRequest.getAction(), operationRequest.getArgs()))
                    .thenReturn(command);
            ioC.when(() -> IoC.resolve("Queue.Register", GAME_ID, command))
                    .thenReturn(putInQueueCommand);
            new InterpretCommand(operationRequest).execute();
        }
        verify(putInQueueCommand).execute();
    }

    @Test
    void shouldPutStartMoveCommandInQueue() {

        var operationRequest = new OperationRequest(OPERATION_ID, new Object[]{});
        var command = new StartMoveCommand(new Spaceship());
        var putInQueueCommand = Mockito.mock(RegisterCommand.class);
        var userContext = new UserContext(PLAYER_ID, GAME_ID);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("UserContext")).thenReturn(userContext);
            ioC.when(() -> IoC.resolve("Interpret.Commands.Resolution", operationRequest.getAction(), operationRequest.getArgs()))
                    .thenReturn(command);
            ioC.when(() -> IoC.resolve("Queue.Register", GAME_ID, command))
                    .thenReturn(putInQueueCommand);
            new InterpretCommand(operationRequest).execute();
        }
        verify(putInQueueCommand).execute();
    }


}
