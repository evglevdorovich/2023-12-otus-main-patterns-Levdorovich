package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Queue;

@RequiredArgsConstructor
@Value
public class MoveToState implements State {
    Queue<Command> moveFromCommands;
    Queue<Command> moveToCommands;
    @Override
    public State handle() {
        return null;
    }
}
