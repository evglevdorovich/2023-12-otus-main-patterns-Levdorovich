package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.RunCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveToStateTest {
    @Mock
    private Queue<Command> movedFromCommands;

    @Test
    void shouldStopWhenHardStopCommand() {
        var hardStopCommand = Mockito.mock(HardStopQueueCommand.class);
        var moveToCommands = new LinkedList<Command>();
        var moveState = new MoveToState(movedFromCommands, moveToCommands);

        when(movedFromCommands.poll()).thenReturn(hardStopCommand);

        var result = moveState.handle();
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnSameStateWhenEmptyQueue() {
        var moveToCommands = new LinkedList<Command>();
        var moveState = new MoveToState(movedFromCommands, moveToCommands);

        assertThat(moveState.handle()).isSameAs(moveState);
    }

    @Test
    void shouldPutInAnotherQueue() {
        var moveToCommands = new LinkedList<Command>();
        var moveState = new MoveToState(movedFromCommands, moveToCommands);
        var command = Mockito.mock(Command.class);
        when(movedFromCommands.poll()).thenReturn(command);

        moveState.handle();

        assertThat(moveToCommands).containsExactly(command);
    }

    @Test
    void shouldReturnTheSameState() {
        var moveToCommands = new LinkedList<Command>();
        var moveState = new MoveToState(movedFromCommands, moveToCommands);
        var command = Mockito.mock(Command.class);
        when(movedFromCommands.poll()).thenReturn(command);

        var result = moveState.handle();

        assertThat(result).isSameAs(moveState);
    }

    @Test
    void shouldReturnRegularStateWhenRunCommand() {
        var runCommand = new RunCommand();
        var moveToCommands = new LinkedList<Command>();
        var regularState = new RegularState(movedFromCommands);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            when(movedFromCommands.poll()).thenReturn(runCommand);
            ioC.when(() -> IoC.resolve("IoC.State.Regular", movedFromCommands))
                    .thenReturn(regularState);

            var actualState = regularState.handle();
            assertThat(actualState).isEqualTo(regularState);
        }
    }
}
