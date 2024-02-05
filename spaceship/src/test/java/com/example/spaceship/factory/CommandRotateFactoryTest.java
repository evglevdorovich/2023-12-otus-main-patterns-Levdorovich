package com.example.spaceship.factory;

import com.example.spaceship.command.ChangeVelocityCommand;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.RotateCommand;
import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Rotatable;
import com.example.spaceship.model.Vector;
import com.example.spaceship.model.VelocityAdjustable;
import com.example.spaceship.service.RotateVelocityCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandRotateFactoryTest {
    @Mock
    private RotatableAndNotMovable rotatableAndNotMovable;
    @Mock
    private RotatableMovableAndAdjustable rotatableMovableAndAdjustable;
    @Mock
    private RotateVelocityCalculator rotateVelocityCalculator;
    @InjectMocks
    private CommandRotateFactory commandRotateFactory;

    @Test
    void shouldOnlyReturnRotateCommand() {
        var expectedMacroCommand = new MacroCommand(List.of(new RotateCommand(rotatableAndNotMovable)));

        var actualMacroCommand = commandRotateFactory.createRotateAndChangeVelocity(rotatableAndNotMovable);

        assertThat(actualMacroCommand).isEqualTo(expectedMacroCommand);
    }

    @Test
    void shouldReturnRotateAndChangeVelocityCommand() {
        var newExpectedVelocity = new Vector(List.of(1, 2));
        when(rotateVelocityCalculator.calculateVelocity(rotatableMovableAndAdjustable)).thenReturn(newExpectedVelocity);

        var expectedMacroCommand = new MacroCommand(List.of(new RotateCommand(rotatableMovableAndAdjustable),
                new ChangeVelocityCommand(rotatableMovableAndAdjustable, newExpectedVelocity)));

        var actualMacroCommand = commandRotateFactory.createRotateAndChangeVelocity(rotatableMovableAndAdjustable);

        assertThat(actualMacroCommand).isEqualTo(expectedMacroCommand);
    }


    private interface RotatableAndNotMovable extends Rotatable {

    }

    private interface RotatableMovableAndAdjustable extends Rotatable, Movable, VelocityAdjustable {

    }

}
