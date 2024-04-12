package com.example.spaceship.command.state;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StateLoopCommandTest {
    @Test
    void shouldExecuteInLoop() {
        var state = Mockito.mock(State.class);
        when(state.handle()).thenReturn(null);

        var stateLoopCommand = new StateLoopCommand(state);
        stateLoopCommand.execute();
        verify(state).handle();
    }

    @Test
    void shouldExecuteInLoopTwice() {
        var state = Mockito.mock(State.class);
        when(state.handle()).thenReturn(state).thenReturn(null);

        var stateLoopCommand = new StateLoopCommand(state);
        stateLoopCommand.execute();
        verify(state, times(2)).handle();
    }

}
