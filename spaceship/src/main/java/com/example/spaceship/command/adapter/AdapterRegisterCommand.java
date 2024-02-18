package com.example.spaceship.command.adapter;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;

public record AdapterRegisterCommand(Object[] adapterClasses) implements Command {
    @Override
    public void execute() {
        IoC.resolve("Adapter.Register", adapterClasses);
    }
}
