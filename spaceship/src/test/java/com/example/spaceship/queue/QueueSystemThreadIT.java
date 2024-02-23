package com.example.spaceship.queue;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.event.DestroyEvent;
import com.example.spaceship.model.core.Scope;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Queue;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueSystemThreadIT extends IoCSetUpTest {
    private static final String EXCEPTION_HANDLER_POSTFIX = ".exception.handler";
    @Mock
    private Command command;
    @Mock
    private Queue<Command> commands;

    @Test
    void shouldHandleException() {
        var destroyEvent = new DestroyEvent();
        var testee = new QueueSystemThread(commands, destroyEvent::finish);

        testee.setOnInit(getIoCInitialisationWithStoppingLogic(testee));

        when(commands.poll()).thenReturn(command);
        doThrow(RuntimeException.class).when(command).execute();

        testee.start();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());
    }

    //Setting test scope inside Thread in System thread
    private Runnable getIoCInitialisationWithStoppingLogic(QueueSystemThread testee) {
        return () -> {
            var scope = IoC.<Scope>resolve("IoC.Scope.Create", "testSystemThread1");
            IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
            IoC.<RegisterDependencyCommand>resolve("IoC.Register", command.getClass() + EXCEPTION_HANDLER_POSTFIX,
                    (Function<Object[], Object>) exc -> {
                        testee.stop();
                        return command;
                    }).execute();
        };
    }

}
