package com.example.spaceship.command;

import com.example.spaceship.model.Shootable;

public record ShootCommand(Shootable shootable) implements Command{
    @Override
    public void execute() {
        //shoot
    }
}
