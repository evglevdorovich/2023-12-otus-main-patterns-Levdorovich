package com.example.spaceship.listener;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.CommandQueueService;
import com.example.spaceship.service.IoC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandListener {
    private final CommandQueueService commandQueueService;
    private final IoC<Command, Exception, Command> ioC;
    private volatile boolean readyToStop = false;

    public void listen() {
        while (!isReadyToStop()) {
            var command = commandQueueService.poll();
            try {
                command.execute();
            } catch (Exception e) {
                commandQueueService.add(ioC.resolve(command, e));
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
