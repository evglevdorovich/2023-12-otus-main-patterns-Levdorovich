package com.example.spaceship.command.state;


import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.MoveToCommand;
import lombok.RequiredArgsConstructor;

import java.util.Queue;

@RequiredArgsConstructor
public class RegularState implements State {
    private final Queue<Command> commands;

    @Override
    public State handle() {
        var command = commands.poll();

        if (command == null) {
            return this;
        }

        command.execute();

        if (command.getClass().equals(MoveToCommand.class)) {
            return new MoveToState(commands);
        }
        if (command.getClass().equals(HardStopQueueCommand.class)) {
            return null;
        }

        return this;
    }
}
