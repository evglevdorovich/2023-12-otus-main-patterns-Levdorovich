package com.example.spaceship.command.scope;

import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class InitialisedIoCIT {

    @BeforeEach
    void init() {
        new InitCommand().execute();
    }

    @AfterEach
    void clean() {
        InitCommand.setCurrentScope(null);
        InitCommand.setAlreadyExecuted(false);
        IoC.clear();
    }

    @Test
    void shouldCorrectlySetScope() {
        var expectedScope = new Scope("Id", Map.of());

        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", expectedScope).execute();

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    void shouldCorrectlyClearScope() {
        InitCommand.setCurrentScope(InitCommand.ROOT_SCOPE);

        IoC.<ClearCurrentScopeCommand>resolve("IoC.Scope.Current.Clear").execute();

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isNull();
    }

    @Test
    void shouldReturnRootScopeWhenWithoutCurrent() {
        var expectedScope = InitCommand.ROOT_SCOPE;

        var actualScope = IoC.resolve("IoC.Scope.Current");

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    void shouldReturnCorrectScopeAfterCreation() {
        var scope = IoC.<Scope>resolve("IoC.Scope.Create", "id", InitCommand.ROOT_SCOPE);
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();

        var actualScope = IoC.<Scope>resolve("IoC.Scope.Current");

        assertThat(actualScope).isEqualTo(scope);
    }

    @Test
    void shouldReturnParentWhenSetWithoutParentAfterCreation() {
        var scope = IoC.<Scope>resolve("IoC.Scope.Create", "id");
        IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
        var expectedParent = InitCommand.ROOT_SCOPE;

        var actualScope = IoC.<Scope>resolve("IoC.Scope.Parent");

        assertThat(actualScope).isEqualTo(expectedParent);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenWithoutParent() {
        assertThatThrownBy(() -> IoC.resolve("IoC.Scope.Parent")).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @SneakyThrows
    void eachThreadShouldResolveStrategyInOwnScope() {
        String dependencyName = "dependencyName";
        String initialString = "abcdf";
        String expectedResultThread2AfterResolution = "abcdf";
        String expectedResultThread1AfterResolution = "ABCDF";

        Function<Object[], Object> parentDependencyResolution = a -> expectedResultThread2AfterResolution;
        Function<Object[], Object> thread1DependencyResolution = a -> expectedResultThread1AfterResolution;

        IoC.<RegisterDependencyCommand>resolve("IoC.Register", dependencyName, parentDependencyResolution).execute();


        var thread1 = new Thread(() -> {
            var scope = IoC.<Scope>resolve("IoC.Scope.Create", "thread1", InitCommand.ROOT_SCOPE);
            IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
            IoC.<RegisterDependencyCommand>resolve("IoC.Register", dependencyName, thread1DependencyResolution).execute();
            var actualResult = IoC.resolve(dependencyName, initialString);

            assertThat(actualResult).isEqualTo(expectedResultThread1AfterResolution);
        });

        var thread2 = new Thread(() -> {
            var scope = IoC.<Scope>resolve("IoC.Scope.Create", "thread2", InitCommand.ROOT_SCOPE);
            IoC.<SetCurrentScopeCommand>resolve("IoC.Scope.Current.Set", scope).execute();
            var actualResult = IoC.resolve(dependencyName, initialString);

            assertThat(actualResult).isEqualTo(expectedResultThread2AfterResolution);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
