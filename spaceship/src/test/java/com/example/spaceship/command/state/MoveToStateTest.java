package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

@ExtendWith(MockitoExtension.class)
class MoveToStateTest {
    @Mock
    private Queue<Command> commands;

    @Test
    void shouldStopWhenHardStopCommand() {
        var moveState = new MoveToState(commands, null);
    }
}
