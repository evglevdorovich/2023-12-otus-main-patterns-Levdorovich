package com.example.spaceship.service.gameobject;

public interface GamePlayerStore {
    Object retrieve(String gameId, String playerId);
}
