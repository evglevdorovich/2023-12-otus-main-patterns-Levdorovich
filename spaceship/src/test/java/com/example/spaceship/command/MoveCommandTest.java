package com.example.spaceship.command;

import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Position;
import com.example.spaceship.model.Velocity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {
    @Mock
    private Movable movable;

    @Test
    void shouldMoveInCorrectPosition() {
        var initialPosition = new Position(12, 5);
        var velocity = new Velocity(-7, 3);
        var expectedFinalPosition = new Position(5, 8);

        when(movable.getPosition()).thenReturn(initialPosition);
        when(movable.getVelocity()).thenReturn(velocity);

        var command = new MoveCommand(movable);
        command.execute();

        verify(movable).setPosition(expectedFinalPosition);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWithoutPosition() {
        when(movable.getVelocity()).thenReturn(new Velocity(1, 1));
        when(movable.getPosition()).thenReturn(null);

        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWithoutVelocity() {
        when(movable.getPosition()).thenReturn(new Position(1, 1));
        when(movable.getVelocity()).thenReturn(null);
        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

}
