package com.example.spaceship.service.gameobject;

import java.util.Map;

public class GamePlayerStoreLocal implements GamePlayerStore {
    // imitation of local storage
    private final Map<String, Map<String, Object>> gameObjects;

    public GamePlayerStoreLocal(Map<String, Map<String, Object>> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Object retrieve(String gameId, String playerId) {
        return gameObjects.get(gameId).get(playerId);
    }
}
