package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.MoveToCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
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
        var moveToCommand = Mockito.mock(MoveToCommand.class);
        var moveToState = new MoveToState(commands);
        when(commands.poll()).thenReturn(moveToCommand);

        var actualState = regularState.handle();
        verify(moveToCommand).execute();
        assertThat(actualState).isEqualTo(moveToState);
    }

}
