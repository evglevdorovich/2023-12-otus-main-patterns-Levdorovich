package com.example.spaceship.service.gameobject;

import com.example.spaceship.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GamePlayerCommandStorageLocal implements GamePlayerCommandStorage {
    private final Map<String, Map<String, Set<String>>> playerCommands;

    @Override
    public void addCommands(String gameId, String playerId, Collection<String> commands) {
        playerCommands.computeIfAbsent(gameId, key -> new ConcurrentHashMap<>())
                .computeIfAbsent(playerId, key -> new HashSet<>()).addAll(commands);
    }

    @Override
    public void validateCommand(String gameId, String playerId, String commandName) {
        var gameCommands = playerCommands.get(gameId);

        if (gameCommands == null) {
            throw new NotFoundException("Cannot found game with ID:" + gameId);
        }

        var currentPlayerCommands = gameCommands.get(playerId);

        if (currentPlayerCommands == null) {
            throw new NotFoundException("Cannot find player for this game with id:" + playerId);
        }

        if (!currentPlayerCommands.contains(commandName)) {
            throw new NotFoundException(String.format("GameId=%s, PlayerId=%s, Cannot find allowed command for the player with name =%s",
                    gameId, playerId, commandName));
        }
    }
}
