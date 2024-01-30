package com.example.spaceship.listener;

import com.example.spaceship.service.CommandQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandListener {
    private final CommandQueueService commandQueueService;
    private volatile boolean readyToStop = false;

    public void listen() {
        while (!readyToStop) {

        }
    }

    public void stop() {
        readyToStop = true;
    }
}
