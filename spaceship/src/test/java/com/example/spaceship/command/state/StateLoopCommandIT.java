package com.example.spaceship.command.state;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.scope.ClearCurrentScopeCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.MoveToCommand;
import com.example.spaceship.command.state.command.RunCommand;
import com.example.spaceship.core.IoC;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
class StateLoopCommandIT {

    @BeforeAll
    static void init() {
        new InitCommand().execute();
        var scope = IoC.resolve("IoC.Scope.Create", "test");
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
        InitCommand.setAlreadyExecuted(false);
        IoC.clear();
    }

    @AfterAll
    static void cleanUp() {
        InitCommand.setAlreadyExecuted(false);
        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();
        IoC.clear();
    }

    @Test
    void shouldExecuteNormalCommandAndStop() {
        var command = Mockito.mock(Command.class);
        var hardStopCommand = new HardStopQueueCommand();
        var queue = new ArrayDeque<Command>();
        queue.add(command);
        queue.add(hardStopCommand);

        new StateLoopCommand(new RegularState(queue)).execute();

        verify(command).execute();
    }

    @Test
    void shouldPopulateReservedQueueAndStop() {
        var command = Mockito.mock(Command.class);
        var hardStopCommand = new HardStopQueueCommand();
        var queue = new ArrayDeque<Command>();
        var reservedQueue = new ArrayDeque<Command>();
        var moveToCommand = new MoveToCommand(reservedQueue);
        queue.add(moveToCommand);
        queue.add(command);
        queue.add(hardStopCommand);

        new StateLoopCommand(new RegularState(queue)).execute();

        assertThat(reservedQueue).containsExactly(command);
    }

    @Test
    void shouldPopulateReservedQueueAndThenExecuteRegular() {
        var command = Mockito.mock(Command.class);
        var executedInRegularCommand = Mockito.mock(Command.class);
        var hardStopCommand = new HardStopQueueCommand();
        var queue = new ArrayDeque<Command>();
        var reservedQueue = new ArrayDeque<Command>();
        var runCommand = new RunCommand();
        var moveToCommand = new MoveToCommand(reservedQueue);
        queue.add(moveToCommand);
        queue.add(command);
        queue.add(runCommand);
        queue.add(executedInRegularCommand);
        queue.add(hardStopCommand);

        new StateLoopCommand(new RegularState(queue)).execute();

        assertThat(reservedQueue).containsExactly(command);
        verify(executedInRegularCommand).execute();
    }
}
