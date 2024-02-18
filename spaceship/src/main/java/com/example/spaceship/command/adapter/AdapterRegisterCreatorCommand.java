package com.example.spaceship.command.adapter;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.core.Scope;

import java.util.Arrays;
import java.util.function.Function;

public record AdapterRegisterCreatorCommand() implements Command {
    @Override
    public void execute() {
        Function<Class<?>[], Object> adapterRegister = (classes) -> {
            Arrays.stream(classes)
                    .forEach(adapter -> IoC.resolve("IoC.Register", "Adapter." + adapter.getName(),
                            (Function<Object[], Object>) (args) -> {
                                try {
                                    return adapter.getConstructor(Object.class).newInstance(args[0]);
                                } catch (Exception e) {
                                    throw new IllegalArgumentException("Cannot find a constructor with size = " + args.length + "for Adapter = " +
                                            adapter.getName());
                                }
                            }));
            return classes;
        };

        IoC.resolve("IoC.Register", "Adapter.Register", adapterRegister);
    }
}
