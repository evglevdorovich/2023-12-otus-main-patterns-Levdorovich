package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StateLoopCommand implements Command {
    private State state;

    @Override
    public void execute() {
        while (state != null) {
            state = state.handle();
        }
    }
}
