package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.queue.SystemThread;

public record StartSystemThreadCommand(SystemThread systemThread) implements Command {

    @Override
    public void execute() {
        systemThread.start();
    }
}
