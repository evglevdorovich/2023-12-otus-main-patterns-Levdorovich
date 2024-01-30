package com.example.spaceship.configuration;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.LastRetryCommand;
import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.command.RetryCommand;
import com.example.spaceship.command.RotateCommand;
import com.example.spaceship.service.IoCResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiFunction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IoCConfigurationTest {
    @Mock
    private IoCResolver<Command, Exception, Command> ioCResolver;

    @Test
    void shouldRegisterAllNecessaryModules() {
        var ioCConfiguration = new IoCConfiguration(false);

        ioCConfiguration.registerHandlers(ioCResolver);
        verify(ioCResolver).registerHandler(eq(MoveCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver).registerHandler(eq(RotateCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver).registerHandler(eq(RetryCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver).registerHandler(eq(LastRetryCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
    }

    @Test
    void shouldRegisterAllNecessaryModulesWithSecondRetry() {
        var ioCConfiguration = new IoCConfiguration(true);

        ioCConfiguration.registerHandlers(ioCResolver);
        verify(ioCResolver).registerHandler(eq(MoveCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver).registerHandler(eq(RotateCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver, times(2)).registerHandler(eq(RetryCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
        verify(ioCResolver).registerHandler(eq(LastRetryCommand.class), eq(IllegalArgumentException.class), any(BiFunction.class));
    }
}
