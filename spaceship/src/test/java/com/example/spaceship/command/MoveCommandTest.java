package com.example.spaceship.command;

import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Vector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {
    @Mock
    private Movable movable;

    @Test
    void shouldMoveInCorrectPosition() {

        var initialPosition = new Vector(new ArrayList<>(List.of(12, 5)));
        var velocity = new Vector(List.of(-7, 3));
        var expectedFinalPosition = new Vector(List.of(5, 8));

        when(movable.getPosition()).thenReturn(initialPosition);
        when(movable.getPosition()).thenReturn(initialPosition);
        when(movable.getVelocity()).thenReturn(velocity);
        when(movable.getVelocity()).thenReturn(velocity);

        var command = new MoveCommand(movable);
        command.execute();
        assertThat(initialPosition).isEqualTo(expectedFinalPosition);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWithoutPosition() {
        when(movable.getPosition()).thenReturn(null);

        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPositionSizeZero() {
        when(movable.getPosition()).thenReturn(new Vector(Collections.emptyList()));

        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWithoutVelocity() {
        when(movable.getPosition()).thenReturn(new Vector(List.of(1, 1)));
        when(movable.getVelocity()).thenReturn(null);
        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenVelocitySizeZero() {
        when(movable.getPosition()).thenReturn(new Vector(List.of(1, 1)));
        when(movable.getVelocity()).thenReturn(new Vector(Collections.emptyList()));
        var command = new MoveCommand(movable);
        assertThatThrownBy(command::execute).isInstanceOf(IllegalArgumentException.class);
    }

}
