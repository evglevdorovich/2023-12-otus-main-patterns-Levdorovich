package com.example.spaceship.service.gameobject;

public interface GamePlayerStorage {
    Object get(String gameId, String playerId);

    void add(String gameId, String playerId, Object player);
}
