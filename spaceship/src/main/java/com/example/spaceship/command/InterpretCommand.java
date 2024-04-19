package com.example.spaceship.command;

import com.example.spaceship.command.queue.RegisterCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import com.example.spaceship.model.OperationRequest;

public record InterpretCommand(OperationRequest operationRequest) implements Command {
    @Override
    public void execute() {
        var context = IoC.<UserContext>resolve("UserContext");
        var commandToPerform = IoC.<Command>resolve("Interpret.Commands.Resolution", operationRequest.getAction(), operationRequest.getArgs());
        IoC.<RegisterCommand>resolve("Queue.Register", context.gameId(), commandToPerform).execute();
    }
}
