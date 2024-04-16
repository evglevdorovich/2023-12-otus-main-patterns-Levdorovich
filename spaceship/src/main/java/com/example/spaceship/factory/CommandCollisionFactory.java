package com.example.spaceship.factory;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.Movable;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@RequiredArgsConstructor
@Value
@Service
public class CommandCollisionFactory {

    public MacroCommand createCollisionCommand(Queue<Command> commands, Field field, Movable movable, int maxGameObjectDiameter) {
        var collisionHandlerCommands = new ArrayList<Command>();
        var collisionHandlerCommand =
                IoC.<Command>resolve("Command.CollisionHandler.Regular", commands, field, movable);
        var collisionSecondOffsetHandlerCommands =
                IoC.<List<Command>>resolve("Command.CollisionHandler.Offset", collisionHandlerCommand, maxGameObjectDiameter);

        collisionHandlerCommands.add(collisionHandlerCommand);
        collisionHandlerCommands.addAll(collisionSecondOffsetHandlerCommands);

        return new MacroCommand(collisionHandlerCommands);
    }
}
