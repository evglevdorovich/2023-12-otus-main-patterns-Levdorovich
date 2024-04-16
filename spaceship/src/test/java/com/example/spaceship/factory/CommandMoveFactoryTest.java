package com.example.spaceship.factory;

import com.example.spaceship.command.BurnFuelCommand;
import com.example.spaceship.command.CheckFuelCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.model.Dimension;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.FuelConsumer;
import com.example.spaceship.model.Movable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandMoveFactoryTest {
    @Mock
    private MovableFuelConsumer movable;
    @Mock
    private CommandCollisionFactory commandCollisionFactory;
    @InjectMocks
    private CommandMoveFactory commandMoveFactory;

    @Test
    void shouldReturnMoveAndBurnFuelCommand() {
        FuelConsumer fuelConsumer = movable;
        var field = new Field(new Dimension(List.of()), Map.of());
        var queue = new ArrayDeque<Command>();
        var maxGameObjectDiameter = 4;
        var commandCollisionMacroCommand = new MacroCommand(List.of());

        List<Command> moveAndBurnFuelCommands = List.of(new CheckFuelCommand(fuelConsumer), new MoveCommand(movable),
        new BurnFuelCommand(fuelConsumer), commandCollisionMacroCommand);

        when(commandCollisionFactory.createCollisionCommand(queue, field, movable, maxGameObjectDiameter)).thenReturn(commandCollisionMacroCommand);

        var expectedMoveAndBurnFuelCommand = new MacroCommand(moveAndBurnFuelCommands);

        var actualMoveAndBurnFuelCommand = commandMoveFactory.buildMoveAndBurnFuelCommand(queue, field, movable, maxGameObjectDiameter);

        assertThat(actualMoveAndBurnFuelCommand).isEqualTo(expectedMoveAndBurnFuelCommand);
    }

    private interface MovableFuelConsumer extends Movable, FuelConsumer {

    }

}
