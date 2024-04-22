package com.example.spaceship.command;

import com.example.spaceship.model.Movable;

public record StopMoveCommand(Movable movable) implements Command{
    @Override
    public void execute() {
        //stop move
    }
}
