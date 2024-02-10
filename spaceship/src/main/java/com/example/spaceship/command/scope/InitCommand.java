package com.example.spaceship.command.scope;

import com.example.spaceship.command.Command;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.ioc.UpdateIoCResolveDependencyStrategyCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.core.SimpleDependencyResolver;
import com.example.spaceship.model.core.Scope;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InitCommand implements Command {
    static final Scope ROOT_SCOPE = new Scope("root", new ConcurrentHashMap<>());
    private static final ThreadLocal<Scope> CURRENT_SCOPE = new ThreadLocal<>();
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final String SCOPE_PARENT = "IoC.Scope.Parent";
    static boolean isAlreadyExecuted = false;

    @Override
    @SuppressWarnings("unchecked")
    public void execute() {
        if (isAlreadyExecuted) {
            return;
        }

        LOCK.lock();
        try {
            if (isAlreadyExecuted) {
                return;
            }

            ROOT_SCOPE.put("IoC.Scope.Current.Set", args -> new SetCurrentScopeCommand((Scope) args[0]));
            ROOT_SCOPE.put("IoC.Scope.Current.Clear", args -> new ClearCurrentScopeCommand());
            ROOT_SCOPE.put("IoC.Scope.Current",
                    (args -> InitCommand.getCurrentScope() == null ? InitCommand.ROOT_SCOPE : InitCommand.getCurrentScope()));
            ROOT_SCOPE.put(SCOPE_PARENT, args -> {
                throw new IllegalStateException("root cannot have a parent");
            });
            ROOT_SCOPE.put("IoC.Scope.Create.Empty", args -> new Scope((String) args[0], new HashMap<>()));
            ROOT_SCOPE.put("IoC.Scope.Create", args -> {
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
            ROOT_SCOPE.put("IoC.Register", args -> new RegisterDependencyCommand((String) args[0],
                    (Function<Object[], Object>) args[1]));

            IoC.<Command>resolve(UpdateIoCResolveDependencyStrategyCommand.class.getSimpleName(),
                    (Function<BiFunction<String, Object[], Object>, BiFunction<String, Object[], Object>>)
                            oldStrategy -> (String dependency, Object[] args) -> {
                                var scope = getCurrentScope() == null ? ROOT_SCOPE : getCurrentScope();
                                return new SimpleDependencyResolver(scope).resolve(dependency, args);
                            }
            ).execute();
            isAlreadyExecuted = true;
        } finally {
            LOCK.unlock();
        }
    }

    static Scope getCurrentScope() {
        return CURRENT_SCOPE.get();
    }

    static void setCurrentScope(Scope scope) {
        CURRENT_SCOPE.set(scope);
    }
}
