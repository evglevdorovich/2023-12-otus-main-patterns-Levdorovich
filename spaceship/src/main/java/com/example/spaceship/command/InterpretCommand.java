package com.example.spaceship.command;

import com.example.spaceship.command.queue.RegisterCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.PlayerActionRequest;

public record InterpretCommand(PlayerActionRequest playerActionRequest) implements Command {
    @Override
    public void execute() {
        var operationRequest = playerActionRequest.getOperationRequest();
        var gameObject = IoC.resolve("GameObject", playerActionRequest.getGameId(), playerActionRequest.getPlayerId());
        IoC.resolve("GameObject.Commands.Validate", playerActionRequest.getGameId(), playerActionRequest.getPlayerId(),
                operationRequest.getId());
        var commandToPerform = IoC.<Command>resolve(operationRequest.getId(), gameObject, operationRequest.getArgs());
        IoC.<RegisterCommand>resolve("Queue.Register", playerActionRequest.getGameId(), commandToPerform).execute();
    }

}
