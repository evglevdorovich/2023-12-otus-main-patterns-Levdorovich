package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.MoveToCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegularStateTest {
    @Mock
    private Queue<Command> commands;

    @Mock
    private Command command;

    @InjectMocks
    private RegularState regularState;

    @Test
    void shouldHandle() {
        when(commands.poll()).thenReturn(command);

        var actualState = regularState.handle();

        verify(command).execute();
        assertThat(actualState).isEqualTo(regularState);
    }

    @Test
    void shouldDoNothingWhenEmpty() {
        when(commands.poll()).thenReturn(null);

        var actualState = regularState.handle();

        assertThat(actualState).isEqualTo(regularState);
    }

    @Test
    void shouldReturnNullWhenHardStop() {
        var hardStopCommand = Mockito.mock(HardStopQueueCommand.class);
        when(commands.poll()).thenReturn(hardStopCommand);

        var actualState = regularState.handle();
        verify(hardStopCommand).execute();
        assertThat(actualState).isNull();
    }


    @Test
    void shouldReturnMoveToStateWhenMoveToCommand() {
        var movedFromCommand = Mockito.mock(MoveToCommand.class);
        var movedToCommands = new LinkedList<Command>();
        var moveToState = new MoveToState(commands, movedToCommands);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            when(commands.poll()).thenReturn(movedFromCommand);
            ioC.when(() -> IoC.resolve("IoC.State.MoveTo", commands, movedFromCommand))
                    .thenReturn(moveToState);

            var actualState = regularState.handle();
            assertThat(actualState).isEqualTo(moveToState);
        }

        verify(movedFromCommand).execute();
    }

}
