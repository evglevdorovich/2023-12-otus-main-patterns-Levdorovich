package com.example.spaceship.command;

import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Position;

public class MoveCommand {
    private final Movable movable;

    public MoveCommand(Movable movable) {
        this.movable = movable;
    }

    public void execute() {
        var initialPosition = movable.getPosition();
        var initialVelocity = movable.getVelocity();

        validateMovableToBeExecutable();

        var finalPosition = new Position(initialPosition.getX() + initialVelocity.getDx(),
                initialPosition.getY() + initialVelocity.getDy());
        movable.setPosition(finalPosition);
    }

    private void validateMovableToBeExecutable() {
        if (movable.getPosition() == null) {
            throw new IllegalArgumentException("position should not be empty");
        }
        if (movable.getVelocity() == null) {
            throw new IllegalArgumentException("velocity should not be empty");
        }
    }

}
