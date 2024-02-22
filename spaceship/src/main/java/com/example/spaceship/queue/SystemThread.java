package com.example.spaceship.queue;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SystemThread {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
    private Queue<Command> commands;
    @Getter
    private boolean readyToStop = false;
    private final Thread thread;

    private Runnable behaviour = () -> {
        var command = commands.poll();
        if (command != null) {
            try {
                command.execute();
            } catch (Exception e) {
                commands.add(IoC.resolve(command.getClass() + EXCEPTION_HANDLER_POSTFIX, e));
            }
        }
    };

    private Runnable onDestroy = () -> {
    };

    @Setter
    private Runnable onInit = () -> {
    };

    @Autowired
    public SystemThread(Queue<Command> commands) {
        this.commands = commands;
        thread = new Thread(() -> {
            onInit.run();
            while (!readyToStop) {
                behaviour.run();
            }
            onDestroy.run();
        });
    }

    public SystemThread(Queue<Command> commands, Runnable onDestroy) {
        this(commands);
        this.onDestroy = onDestroy;
    }
    public void start() {
        thread.start();
    }

    public void stop() {
        readyToStop = true;
    }
}
