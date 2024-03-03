package com.example.spaceship.service.queue;

import com.example.spaceship.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@RequiredArgsConstructor
public class GameQueueStorageLocal implements GameQueueStorage {
    private static final int QUEUE_CAPACITY = 1000;
    private final Map<String, Queue<Command>> commandsByGameIds;

    @Override
    public void register(String gameId, Command command) {
        commandsByGameIds.computeIfAbsent(gameId, key -> new ArrayBlockingQueue<>(QUEUE_CAPACITY))
                .add(command);
    }

    @Override
    public Queue<Command> get(String gameId) {
        return commandsByGameIds.get(gameId);
    }
}
