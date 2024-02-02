package com.example.spaceship.command;

import com.example.spaceship.model.Movable;

public record MoveCommand(Movable movable) implements Command {

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
