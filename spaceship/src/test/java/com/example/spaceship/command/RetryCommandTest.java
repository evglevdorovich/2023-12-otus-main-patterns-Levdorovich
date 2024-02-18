package com.example.spaceship.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RetryCommandTest {
    @Mock
    private Command command;

    @Test
    void shouldRetryCommand() {
        var retryCommand = new RetryCommand(command);
        retryCommand.execute();
        verify(command).execute();
    }
}
