package com.example.spaceship.command;

import com.example.spaceship.model.Collidable;

public record CheckCollisionCommand(Collidable collidable, Collidable otherCollidable) implements Command {
    @Override
    public void execute() {
        //check collisions between 2 objects
    }
}
