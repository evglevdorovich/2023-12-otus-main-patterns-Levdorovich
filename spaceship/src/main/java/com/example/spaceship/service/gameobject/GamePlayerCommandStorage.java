package com.example.spaceship.service.gameobject;

import java.util.Collection;

public interface GamePlayerCommandStorage {
    void addCommands(String gameId, String playerId, Collection<String> commands);

    void validateCommand(String gameId, String playerId, String commandName);
}
