package com.example.spaceship.command.adapter;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;

import java.util.List;

public record AdapterRegisterCommand(List<Class<?>> adapterClasses) implements Command {
    @Override
    public void execute() {
        IoC.resolve("Adapter.Register", adapterClasses);
    }
}
