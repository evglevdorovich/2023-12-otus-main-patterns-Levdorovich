package com.example.spaceship.factory;

import com.example.spaceship.command.ChangeVelocityCommand;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.MacroCommand;
import com.example.spaceship.command.RotateCommand;
import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Rotatable;
import com.example.spaceship.model.VelocityAdjustable;
import com.example.spaceship.service.RotateVelocityCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CommandRotateFactory {
    private final RotateVelocityCalculator rotateVelocityCalculator;

    public MacroCommand createRotateAndChangeVelocity(Rotatable rotatable) {
        var commands = new ArrayList<Command>();
        commands.add(new RotateCommand(rotatable));

        if (rotatable instanceof Movable && rotatable instanceof VelocityAdjustable) {
            var newVector = rotateVelocityCalculator.calculateVelocity(rotatable);
            commands.add(new ChangeVelocityCommand((VelocityAdjustable) rotatable, newVector));
        }

        return new MacroCommand(Collections.unmodifiableList(commands));
    }
}
