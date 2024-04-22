package com.example.spaceship.command;

import com.example.spaceship.model.Movable;

public record StartMoveCommand(Movable movable) implements Command {
    @Override
    public void execute() {
        //start move
    }
}
