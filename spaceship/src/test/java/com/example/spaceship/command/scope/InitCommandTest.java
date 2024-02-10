package com.example.spaceship.command.scope;

import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class InitCommandTest {
    private static final String ROOT_ID = "root";
    private static final String SCOPE_PARENT = "IoC.Scope.Parent";

    @Test
    void shouldSetScope() {
        var expectedScope = new Scope("id", Collections.emptyMap());
        InitCommand.setCurrentScope(expectedScope);

        var actualScope = InitCommand.getCurrentScope();

        assertThat(actualScope).isEqualTo(expectedScope);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCorrectlyInitTest() {
        var resolutionStrategy = new ConcurrentHashMap<String, Function<Object[], Object>>();
        var expectedRootScope = new Scope(ROOT_ID, resolutionStrategy);

        expectedRootScope.put("IoC.Scope.Current.Set", args -> new SetCurrentScopeCommand((Scope) args[0]));
        expectedRootScope.put("IoC.Scope.Current.Clear", args -> new ClearCurrentScopeCommand());
        expectedRootScope.put("IoC.Scope.Current",
                (args -> InitCommand.getCurrentScope() == null ? InitCommand.ROOT_SCOPE : InitCommand.getCurrentScope()));
        expectedRootScope.put(SCOPE_PARENT, args -> {
            throw new IllegalStateException("root cannot have a parent");
        });
        expectedRootScope.put("IoC.Scope.Create.Empty", args -> new Scope((String) args[0], new HashMap<>()));
        expectedRootScope.put("IoC.Scope.Create", args -> {
            var creatingScope = IoC.<Scope>resolve("IoC.Scope.Create.Empty", args[0]);
            if (args.length > 1) {
                var parentScope = args[1];
                creatingScope.put(SCOPE_PARENT, arg -> parentScope);
            } else {
                var parentScope = IoC.<Scope>resolve("IoC.Scope.Current");
                creatingScope.put(SCOPE_PARENT, arg -> parentScope);
            }
            return creatingScope;
        });
        expectedRootScope.put("IoC.Register", args -> new RegisterDependencyCommand((String) args[0],
                (Function<Object[], Object>) args[1]));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldCorrectlyInit() {
        var resolutionStrategy = new ConcurrentHashMap<String, Function<Object[], Object>>();
        var expectedRootScopeDependencyResolutionNames = List.of("IoC.Scope.Current.Set", "IoC.Scope.Current.Clear", "IoC.Scope.Current",
                "IoC.Scope.Create.Empty", "IoC.Scope.Create", "IoC.Register");

    }


}
