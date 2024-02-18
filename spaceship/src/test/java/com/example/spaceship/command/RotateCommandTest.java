package com.example.spaceship.command;

import com.example.spaceship.model.Rotatable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RotateCommandTest {

    @Mock
    private Rotatable rotatable;

    @Test
    void shouldCorrectlyRotate() {
        var initialDirection = 3;
        var angularVelocity = 2;
        var directionsNumber = 3;
        var expectedDirection = 2;

        when(rotatable.getDirection()).thenReturn(initialDirection);
        when(rotatable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotatable.getDirectionsNumber()).thenReturn(directionsNumber);

        var rotateCommand = new RotateCommand(rotatable);
        rotateCommand.execute();
        verify(rotatable).setDirection(expectedDirection);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfDirectionsNumberIsZero() {
        when(rotatable.getDirectionsNumber()).thenReturn(0);
        var rotateCommand = new RotateCommand(rotatable);
        assertThatThrownBy(rotateCommand::execute).isInstanceOf(IllegalArgumentException.class);
    }
}
