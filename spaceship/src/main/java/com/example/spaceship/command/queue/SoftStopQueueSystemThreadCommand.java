package com.example.spaceship.command.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import com.example.spaceship.queue.QueueSystemThread;

public class SoftStopQueueSystemThreadCommand implements Command {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
    private final QueueSystemThread queueSystemThread;
    private final Runnable softStopBehaviour;

    public SoftStopQueueSystemThreadCommand(QueueSystemThread queueSystemThread) {
        this.queueSystemThread = queueSystemThread;

        softStopBehaviour = () -> {
            var commands = queueSystemThread.getCommands();
            IoC.<HardStopQueueSystemThreadCommand>resolve("SystemThread.HardStop", queueSystemThread).execute();
            while (!commands.isEmpty()) {
                var command = commands.poll();
                if (command != null) {
                    try {
                        command.execute();
                    } catch (Exception e) {
                        commands.add(IoC.resolve(command.getClass() + EXCEPTION_HANDLER_POSTFIX, e));
                    }
                }
            }
        };
    }

    @Override
    public void execute() {
        queueSystemThread.updateBehaviour(softStopBehaviour);
    }
}
