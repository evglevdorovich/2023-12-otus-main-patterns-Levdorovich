package com.example.spaceship.factory;

import com.example.spaceship.command.BurnFuelCommand;
import com.example.spaceship.command.CheckFuelCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.FuelConsumer;
import com.example.spaceship.model.Movable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class CommandMoveFactory {
    private final CommandCollisionFactory commandCollisionFactory;

    public MacroCommand buildMoveAndBurnFuelCommand(Queue<Command> commandQueue, Field field, Movable movable, int maxGameObjectDiameter) {
        var commands = new ArrayList<Command>();

        if (movable instanceof FuelConsumer fuelConsumer) {
            commands.add(new CheckFuelCommand(fuelConsumer));
            commands.add(new MoveCommand(movable));
            commands.add(new BurnFuelCommand(fuelConsumer));
        } else {
            commands.add(new MoveCommand(movable));
        }
        commands.add(commandCollisionFactory.createCollisionCommand(commandQueue, field, movable, maxGameObjectDiameter));
        return new MacroCommand(Collections.unmodifiableList(commands));
    }
}
