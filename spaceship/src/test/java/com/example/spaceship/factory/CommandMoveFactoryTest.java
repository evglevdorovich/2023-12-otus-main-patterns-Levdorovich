package com.example.spaceship.factory;

import com.example.spaceship.command.BurnFuelCommand;
import com.example.spaceship.command.CheckFuelCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.model.FuelConsumer;
import com.example.spaceship.model.Movable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommandMoveFactoryTest {
    @Mock
    private MovableFuelConsumer movable;

    @Test
    void shouldReturnMoveAndBurnFuelCommand() {
        var commandMoveFactory = new CommandMoveFactory();
        var fuelConsumption = 5;
        FuelConsumer fuelConsumer = movable;
        List<Command> moveAndBurnFuelCommands = List.of(new CheckFuelCommand(fuelConsumer, fuelConsumption),
                new BurnFuelCommand(fuelConsumer, fuelConsumption), new MoveCommand(movable));

        var expectedMoveAndBurnFuelCommand = new MacroCommand(moveAndBurnFuelCommands);

        var actualMoveAndBurnFuelCommand = commandMoveFactory.buildMoveAndBurnFuelCommand(movable, fuelConsumption);

        assertThat(actualMoveAndBurnFuelCommand).isEqualTo(expectedMoveAndBurnFuelCommand);
    }

    private interface MovableFuelConsumer extends Movable, FuelConsumer {

    }

}
