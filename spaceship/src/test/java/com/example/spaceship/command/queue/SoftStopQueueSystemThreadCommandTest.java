package com.example.spaceship.command.queue;

import com.example.spaceship.IoCSetUpTest;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.event.DestroyEvent;
import com.example.spaceship.model.core.Scope;
import com.example.spaceship.queue.QueueSystemThread;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class SoftStopQueueSystemThreadCommandTest extends IoCSetUpTest {

    public static final int CAPACITY = 1000;
    @Mock
    private Command command;

    @Test
    void shouldStopAfterQueueIsEmpty() {
        var destroyEvent = new DestroyEvent();
        var queue = new ArrayBlockingQueue<Command>(CAPACITY);
        doNothing().when(command).execute();

        for (int i = 0; i < CAPACITY - 1; i++) {
            queue.add(new DoNothingCommand());
        }
        queue.add(command);

        var systemThread = new QueueSystemThread(queue, destroyEvent::finish);
        systemThread.setOnInit(getIoCInitialisationWithStoppingLogic());

        systemThread.start();

        new SoftStopQueueSystemThreadCommand(systemThread).execute();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertThat(destroyEvent.isFinished()).isTrue());

        assertThat(queue).isEmpty();
        verify(command).execute();
    }

    private Runnable getIoCInitialisationWithStoppingLogic() {
        return () -> {
            var scope = IoC.<Scope>resolve("IoC.Scope.Create", "testSystemThread1");
            IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
            IoC.<RegisterDependencyCommand>resolve("IoC.Register", "SystemThread.HardStop",
                    (Function<Object[], Object>) (args) -> new HardStopQueueSystemThreadCommand((QueueSystemThread) args[0])).execute();
        };
    }

    private static class DoNothingCommand implements Command {

        @Override
        public void execute() {

        }
    }

}
