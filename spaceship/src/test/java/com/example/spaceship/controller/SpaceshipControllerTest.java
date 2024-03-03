package com.example.spaceship.controller;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.OperationRequest;
import com.example.spaceship.model.PlayerActionRequest;
import com.example.spaceship.model.Vector;
import com.example.spaceship.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipControllerTest {
    @InjectMocks
    private SpaceshipController spaceshipController;
    @Mock
    private SpaceshipService spaceshipService;

    @Test
    void shouldMoveSpaceship() {
        var spaceshipId = 1L;
        var expectedPosition = new Vector(List.of(1, 1));
        when(spaceshipService.move(spaceshipId)).thenReturn(expectedPosition);

        var actualPosition = spaceshipController.move(spaceshipId);

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }

    @Test
    void shouldRotateSpaceship() {
        var spaceshipId = 1L;
        var expectedDirection = 1;
        when(spaceshipService.rotate(spaceshipId)).thenReturn(expectedDirection);

        var actualDirection = spaceshipController.rotate(spaceshipId);

        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    @Test
    void shouldExecuteInterpretCommand() {
        var gameId = "gameId";
        var playerId = "playerId";
        var operationRequest = new OperationRequest("operationId", new Object[]{});
        var playerActionRequest = new PlayerActionRequest(gameId, playerId, operationRequest);
        var command = Mockito.mock(Command.class);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve("PlayerActionRequest", gameId, playerId, operationRequest))
                    .thenReturn(playerActionRequest);
            ioC.when(() -> IoC.<Command>resolve("Commands.Interpret", playerActionRequest))
                            .thenReturn(command);
            spaceshipController.operate(gameId, playerId, operationRequest);
        }
        verify(command).execute();
    }


}
