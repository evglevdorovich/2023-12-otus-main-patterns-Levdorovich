package com.example.spaceship.service;

import com.example.spaceship.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class CommandQueueService {
    private final Queue<Command> commands;

    public CommandQueueService(@Value("${queue.init-capacity}") int initCapacity) {
        commands = new ArrayBlockingQueue<>(initCapacity, true);
    }

    public void add(Command command) {
        commands.add(command);
    }

    int size() {
        return commands.size();
    }

    public Command poll() {
        return commands.poll();
    }
}
