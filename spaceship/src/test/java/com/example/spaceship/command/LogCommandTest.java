package com.example.spaceship.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.spaceship.utils.LogVerifier.verifyLogMessage;

@ExtendWith(MockitoExtension.class)
class LogCommandTest {
    @Test
    void shouldLogMessage() {
        Exception exceptionToBePrinted = new Exception("exception message");
        var expectedMessage = exceptionToBePrinted.getMessage();
        Runnable commandExecution = () -> new LogCommand(exceptionToBePrinted).execute();

        verifyLogMessage(LogCommand.class, commandExecution, expectedMessage);
    }

}
