package com.example.spaceship.command;

import com.example.spaceship.model.Vector;
import com.example.spaceship.model.VelocityAdjustable;

public record ChangeVelocityCommand(VelocityAdjustable velocityAdjustable, Vector newVelocity) implements Command {
    @Override
    public void execute() {
        velocityAdjustable.setVelocity(newVelocity);
    }
}
