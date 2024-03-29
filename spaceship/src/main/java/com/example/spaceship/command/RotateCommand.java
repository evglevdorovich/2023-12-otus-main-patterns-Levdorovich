package com.example.spaceship.command;

import com.example.spaceship.model.Rotatable;

public record RotateCommand(Rotatable rotatable) implements Command {

    public void execute() {
        if (rotatable.getDirectionsNumber() <= 0) {
            throw new IllegalArgumentException("directions number could not be less than 1");
        }
        var directionAfterRotate = (rotatable.getDirection() + rotatable.getAngularVelocity()) % rotatable.getDirectionsNumber();
        rotatable.setDirection(directionAfterRotate);
    }
}
