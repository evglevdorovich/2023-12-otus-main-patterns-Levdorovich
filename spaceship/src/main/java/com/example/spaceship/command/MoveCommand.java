package com.example.spaceship.command;

import com.example.spaceship.model.Movable;

public class MoveCommand implements Command {
    private final Movable movable;

    public MoveCommand(Movable movable) {
        this.movable = movable;
    }

    public void execute() {
        validateMovableToBeExecutable();
        movable.setPosition(movable.getPosition().plus(movable.getVelocity()));
    }

    private void validateMovableToBeExecutable() {
        if (movable.getPosition() == null || movable.getPosition().size() == 0) {
            throw new IllegalArgumentException("position should not be empty");
        }
        if (movable.getVelocity() == null || movable.getVelocity().size() == 0) {
            throw new IllegalArgumentException("velocity should not be empty");
        }
    }

}
