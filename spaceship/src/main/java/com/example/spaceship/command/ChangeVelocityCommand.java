package com.example.spaceship.command;

import com.example.spaceship.model.Vector;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangeVelocityCommand implements Command {
    private final VelocityAdjustable velocityAdjustable;
    private final Vector newVelocity;

    @Override
    public void execute() {
        velocityAdjustable.setVelocity(newVelocity);
    }
}
