package com.example.spaceship.command;

import com.example.spaceship.model.Vector;
import com.example.spaceship.model.VelocityAdjustable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChangeVelocityCommandTest {
    @Mock
    private VelocityAdjustable velocityAdjustable;

    @Test
    void shouldChangeVelocity() {
        var expectedSetVelocity = new Vector(List.of(1, 2));
        var changeVelocityCommand = new ChangeVelocityCommand(velocityAdjustable, expectedSetVelocity);

        changeVelocityCommand.execute();

        verify(velocityAdjustable).setVelocity(expectedSetVelocity);
    }

}
