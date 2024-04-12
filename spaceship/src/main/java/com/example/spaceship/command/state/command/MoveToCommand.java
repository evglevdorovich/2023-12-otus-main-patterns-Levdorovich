package com.example.spaceship.command.state.command;

import com.example.spaceship.command.Command;

import java.util.Queue;

public record MoveToCommand(Queue<Command> moveToCommands) implements Command {
    @Override
    public void execute() {

    }
}
