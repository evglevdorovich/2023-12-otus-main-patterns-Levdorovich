package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.queue.QueueSystemThread;

public record HardStopQueueSystemThreadCommand(QueueSystemThread queueSystemThread) implements Command {

    @Override
    public void execute() {
        queueSystemThread.stop();
    }
}
