package com.example.spaceship.command.scope;

import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class InitCommandTest {
    private static final String ROOT_ID = "root";

    @Test
    void shouldSetScope() {
        var expectedScope = new Scope("id", Collections.emptyMap());
        InitCommand.setCurrentScope(expectedScope);

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    void shouldCorrectlyInit() {
        var initCommand = new InitCommand();
        initCommand.execute();
        var expectedRootScopeDependencyResolutionNames = List.of("IoC.Scope.Current.Set", "IoC.Scope.Current.Clear", "IoC.Scope.Current",
                "IoC.Scope.Create.Empty", "IoC.Scope.Create", "IoC.Register", "IoC.Scope.Parent");
        assertThat(InitCommand.ROOT_SCOPE.dependencyResolutions()).containsOnlyKeys(expectedRootScopeDependencyResolutionNames);

    }

    //imitate multiple threads trying to execute init command - checks that double lock checking is working
    @Test
    void shouldNotThrowAnyExceptionWhenMultipleThreadsTryToInitAndUpdateDependencyResolveStrategy() throws InterruptedException {
        AtomicInteger exceptionsThrown = new AtomicInteger(0);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> {
                try {
                    new InitCommand().execute();
                    InitCommand.setCurrentScope(null);
                } catch (Exception e) {
                    exceptionsThrown.incrementAndGet();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }


        for (Thread thread : threads) {
            thread.join();
        }

        assertThat(exceptionsThrown.get()).isZero();
    }


}
