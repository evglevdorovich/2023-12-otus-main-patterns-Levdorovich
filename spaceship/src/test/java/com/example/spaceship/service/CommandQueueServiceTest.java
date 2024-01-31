package com.example.spaceship.service;

import com.example.spaceship.command.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommandQueueServiceTest {

    @Mock
    private Command command;

    @Test
    void shouldHaveCorrectSizeAfterAddingElements() {
        var commandQueueService = new CommandQueueService(10);

        var expectedSize = 2;

        commandQueueService.add(command);
        commandQueueService.add(command);

        var actualSize = commandQueueService.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void shouldPollTheFirstElementAfterAdding() {
        var commandQueueService = new CommandQueueService(10);
        var expectedCommand = command;

        commandQueueService.add(command);

        var actualCommand = commandQueueService.poll();

        assertThat(actualCommand).isEqualTo(expectedCommand);
    }

}
