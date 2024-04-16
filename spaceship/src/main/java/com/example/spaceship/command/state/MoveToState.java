package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.RunCommand;
import com.example.spaceship.core.IoC;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Queue;

@RequiredArgsConstructor
@Value
public class MoveToState implements State {
    Queue<Command> movedFromCommands;
    Queue<Command> moveToCommands;

    @Override
    public State handle() {
        var movedCommand = movedFromCommands.poll();

        if (movedCommand == null) {
            return this;
        }

        if (movedCommand.getClass() == HardStopQueueCommand.class) {
            return null;
        }

        if (movedCommand.getClass() == RunCommand.class) {
            return IoC.resolve("IoC.State.Regular", movedFromCommands);
        }

        moveToCommands.add(movedCommand);

        return this;
    }
}
