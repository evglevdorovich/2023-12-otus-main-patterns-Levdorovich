package com.example.spaceship.service.queue;

import com.example.spaceship.command.Command;

import java.util.Queue;

public interface GameQueueStorage {
    void register(String gameId, Command command);
    Queue<Command> get(String gameId);
}
