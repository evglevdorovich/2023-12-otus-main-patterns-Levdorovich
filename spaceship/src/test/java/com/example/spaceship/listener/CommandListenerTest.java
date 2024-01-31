package com.example.spaceship.listener;

import com.example.spaceship.command.Command;
import com.example.spaceship.service.CommandQueueService;
import com.example.spaceship.service.IoCResolver;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandListenerTest {
    @Mock
    private CommandQueueService queueService;
    @Mock
    private Command command;
    @Mock
    private IoCResolver<Command, Exception, Command> ioCResolver;
    @InjectMocks
    private CommandListener commandListener;

    @Test
    @SneakyThrows
    void shouldTakeAndExecuteCommand() {
        when(queueService.poll()).thenReturn(command);
        CommandListener spyListener = spy(commandListener);

        when(spyListener.isReadyToStop()).thenReturn(false, true);

        spyListener.listen();

        verify(command).execute();
    }

    @Test
    @SneakyThrows
    void shouldAddCommandWhenSomeException() {
        var secondCommand = mock(Command.class);
        CommandListener spyListener = spy(commandListener);
        when(queueService.poll()).thenReturn(command);
        doThrow(RuntimeException.class).when(command).execute();
        when(ioCResolver.resolve(eq(command), isA(RuntimeException.class))).thenReturn(secondCommand);

        when(spyListener.isReadyToStop()).thenReturn(false, true);

        spyListener.listen();

        verify(command).execute();
        verify(queueService).add(secondCommand);
    }

}
