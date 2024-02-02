package com.example.spaceship.factory;

import com.example.spaceship.command.BurnFuelCommand;
import com.example.spaceship.command.CheckFuelCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.model.FuelConsumer;
import com.example.spaceship.model.Movable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CommandMoveFactory {


    public MacroCommand buildMoveAndBurnFuelCommand(Movable movable, int fuelConsumption) {
        var commands = new ArrayList<Command>();

        if (movable instanceof FuelConsumer) {
            commands.add(new CheckFuelCommand((FuelConsumer) movable, fuelConsumption));
            commands.add(new BurnFuelCommand((FuelConsumer) movable, fuelConsumption));
        }
        commands.add(new MoveCommand(movable));

        return new MacroCommand(Collections.unmodifiableList(commands));
    }
}
