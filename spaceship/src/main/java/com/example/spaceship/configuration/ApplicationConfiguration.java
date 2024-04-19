package com.example.spaceship.configuration;

import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.command.Command;
import com.example.spaceship.command.adapter.AdapterRegisterCommand;
import com.example.spaceship.command.adapter.AdapterRegisterCreatorCommand;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.queue.RegisterCommand;
import com.example.spaceship.command.scope.InitCommand;
import com.example.spaceship.command.state.MoveToState;
import com.example.spaceship.command.state.RegularState;
import com.example.spaceship.command.state.command.MoveToCommand;
import com.example.spaceship.core.Adapted;
import com.example.spaceship.core.AdapterResolver;
import com.example.spaceship.core.IoC;
import com.example.spaceship.service.adapter.AdapterCreator;
import com.example.spaceship.service.classes.ClassFinder;
import com.example.spaceship.service.gameobject.GamePlayerCommandStorageLocal;
import com.example.spaceship.service.gameobject.GamePlayerStorageLocal;
import com.example.spaceship.service.queue.GameQueueStorageLocal;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private static final String IOC_REGISTER = "IoC.Register";
    private final ClassFinder classFinder;
    private final AdapterCreator adapterCreator;
    private final AdapterResolver adapterResolver;

    @PostConstruct
    public void configure() {
        new InitCommand().execute();
        new AdapterRegisterCreatorCommand().execute();
        var classes = classFinder.search("../", Adapted.class);
        var adapters = adapterCreator.createAdapters(classes, IoCAdapterInterceptor.class, "Adapter").toArray();
        new AdapterRegisterCommand(adapters).execute();
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "Adapter",
                (Function<Object[], Object>) args -> adapterResolver.resolve((String) args[0], args[1])).execute();

        var gamePlayerStorageLocal = new GamePlayerStorageLocal(new ConcurrentHashMap<>());
        var playerCommands = new GamePlayerCommandStorageLocal(new HashMap<>());
        var gameQueueStorageLocal = new GameQueueStorageLocal(new ConcurrentHashMap<>());
        var interpretCommandsResolutions = new HashMap<String, Function<Object[], Object>>();

        prepareGameObjectStorage(gamePlayerStorageLocal);
        prepareCommandsStorage(playerCommands);
        prepareInterpretCommands(interpretCommandsResolutions);
        prepareQueueStorage(gameQueueStorageLocal);
        registerStates();
    }

    private static void prepareInterpretCommands(HashMap<String, Function<Object[], Object>> interpretCommandsResolutions) {
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "Interpret.Commands.Resolution.Register", (Function<Object[], Object>)
                args -> {
                    interpretCommandsResolutions.put((String) args[0], (Function<Object[], Object>) args[1]);
                    return null;
                }).execute();

        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "Interpret.Commands.Resolution", (Function<Object[], Object>)
                args -> interpretCommandsResolutions.get(args[0]).apply((Object[]) args[1])).execute();
    }

    @SuppressWarnings("unchecked")
    private static void registerStates() {
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "IoC.State.Regular",
                (Function<Object[], Object>) args -> new RegularState((Queue<Command>) args[0])).execute();

        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "IoC.State.MoveTo",
                (Function<Object[], Object>) args -> new MoveToState((Queue<Command>) args[0],
                        ((MoveToCommand) args[1]).moveToCommands())).execute();
    }

    private static void prepareQueueStorage(GameQueueStorageLocal gameQueueStorageLocal) {
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "Queue.Register", (Function<Object[], Object>)
                args -> new RegisterCommand(gameQueueStorageLocal, (String) args[0], (Command) args[1])).execute();

        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "Queue", (Function<Object[], Object>)
                args -> gameQueueStorageLocal.get((String) args[0])).execute();
    }

    @SuppressWarnings("unchecked")
    private static void prepareCommandsStorage(GamePlayerCommandStorageLocal playerCommands) {
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "GameObject.Commands.Register", (Function<Object[], Object>)
                args -> {
                    playerCommands.addCommands((String) args[0], (String) args[1], (Collection<String>) args[2]);
                    return null;
                }).execute();

        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "GameObject.Commands.Validate", (Function<Object[], Object>)
                args -> {
                    playerCommands.validateCommand((String) args[0], (String) args[1], (String) args[2]);
                    return null;
                }).execute();
    }

    private static void prepareGameObjectStorage(GamePlayerStorageLocal gamePlayerStorageLocal) {
        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "GameObject", (Function<Object[], Object>)
                args -> gamePlayerStorageLocal.get((String) args[0], (String) args[1])).execute();

        IoC.<RegisterDependencyCommand>resolve(IOC_REGISTER, "GameObject.Register", (Function<Object[], Object>)
                args -> {
                    gamePlayerStorageLocal.add((String) args[0], (String) args[1], args[2]);
                    return null;
                }).execute();
    }
}
