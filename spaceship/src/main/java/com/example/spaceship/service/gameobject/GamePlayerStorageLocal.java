package com.example.spaceship.service.gameobject;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GamePlayerStorageLocal implements GamePlayerStorage {
    private final Map<String, Map<String, Object>> gameObjects;

    @Override
    public Object get(String gameId, String playerId) {
        return gameObjects.get(gameId).get(playerId);
    }

    @Override
    public void add(String gameId, String playerId, Object player) {
        gameObjects.computeIfAbsent(gameId, key -> new ConcurrentHashMap<>())
                .put(playerId, player);
    }
}
