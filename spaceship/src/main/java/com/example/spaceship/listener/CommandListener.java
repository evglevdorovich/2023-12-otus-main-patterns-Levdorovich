package com.example.spaceship.listener;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.CommandQueueService;
import com.example.spaceship.service.IoCResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandListener {
    private final CommandQueueService commandQueueService;
    private final IoCResolver<Command, Exception, Command> ioCResolver;
    private volatile boolean readyToStop = false;

    public void listen() {
        while (!isReadyToStop()) {
            var command = commandQueueService.poll();
            try {
                command.execute();
            } catch (Exception e) {
                commandQueueService.add(ioCResolver.resolve(command, e));
            }
        }
    }

    boolean isReadyToStop() {
        return readyToStop;
    }

    public void stop() {
        readyToStop = true;
    }
}
