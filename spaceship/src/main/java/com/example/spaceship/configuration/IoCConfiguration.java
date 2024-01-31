package com.example.spaceship.configuration;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.LastRetryCommand;
import com.example.spaceship.command.LogCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.command.RetryCommand;
import com.example.spaceship.command.RotateCommand;
import com.example.spaceship.service.IoCResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCConfiguration {
    private final boolean enableSecondRetry;

    public IoCConfiguration(@Value("${second-retry.enable}") boolean enableSecondRetry) {
        this.enableSecondRetry = enableSecondRetry;
    }

    @Autowired
    public void registerHandlers(IoCResolver<Command, Exception, Command> ioCResolver) {
        ioCResolver.registerHandler(MoveCommand.class, IllegalArgumentException.class, (key, exc) -> new RetryCommand(key));
        ioCResolver.registerHandler(RotateCommand.class, IllegalArgumentException.class, (key, exc) -> new RetryCommand(key));
        ioCResolver.registerHandler(RetryCommand.class, IllegalArgumentException.class, (key, exc) -> new LogCommand(exc));
        ioCResolver.registerHandler(LastRetryCommand.class, IllegalArgumentException.class, (key, exc) -> new LogCommand(exc));

        if (enableSecondRetry) {
            ioCResolver.registerHandler(RetryCommand.class, IllegalArgumentException.class, (key, exc) -> new LastRetryCommand(key));
        }
    }
}
