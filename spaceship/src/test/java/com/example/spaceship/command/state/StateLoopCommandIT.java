package com.example.spaceship.command.state;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.state.command.HardStopQueueCommand;
import com.example.spaceship.command.state.command.MoveToCommand;
import com.example.spaceship.command.state.command.RunCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StateLoopCommandIT extends IoCSetUpTest {

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
