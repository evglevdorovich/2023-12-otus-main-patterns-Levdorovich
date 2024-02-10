package com.example.spaceship.listener;

import com.example.spaceship.service.CommandQueueService;
import com.example.spaceship.core.IoC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandListener {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
    private final CommandQueueService commandQueueService;
    private volatile boolean readyToStop = false;

    public void listen() {
        while (!isReadyToStop()) {
            var command = commandQueueService.poll();
            try {
                command.execute();
            } catch (Exception e) {
                commandQueueService.add(IoC.resolve(command.getClass() + EXCEPTION_HANDLER_POSTFIX, new Object[]{e}));
            }
        }
    }

    public void stop() {
        readyToStop = true;
    }

    boolean isReadyToStop() {
        return readyToStop;
    }
}
