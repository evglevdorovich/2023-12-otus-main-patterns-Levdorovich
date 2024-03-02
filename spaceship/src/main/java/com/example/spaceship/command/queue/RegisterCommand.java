package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.queue.GameQueueStorage;

public record RegisterCommand(GameQueueStorage queueStorage, String gameId, Command command) implements Command {

    @Override
    public void execute() {
        queueStorage.register(gameId, command);
    }

}
