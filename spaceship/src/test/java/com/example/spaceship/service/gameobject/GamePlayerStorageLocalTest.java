package com.example.spaceship.service.gameobject;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GamePlayerStorageLocalTest {
    private static final String PLAYER_ID = "playerId";
    private static final String GAME_ID = "gameId";
    @Test
    void shouldRetrieveCommand() {
        var expectedGameObject = new Object();
        var gameObjects = Map.of(GAME_ID, Map.of(PLAYER_ID, expectedGameObject));

        var gamePlayStore = new GamePlayerStorageLocal(gameObjects);

        var actualGameObject = gamePlayStore.get(GAME_ID, PLAYER_ID);
        assertThat(actualGameObject).isEqualTo(expectedGameObject);
    }

    @Test
    void shouldAddCommand() {
        var expectedGameObject = new Object();

        var gameObjects = new HashMap<String, Map<String, Object>>();

        var gamePlayStore = new GamePlayerStorageLocal(gameObjects);
        gamePlayStore.add(GAME_ID, PLAYER_ID, expectedGameObject);

        var actualAddedObject = gameObjects.get(GAME_ID).get(PLAYER_ID);

        assertThat(actualAddedObject).isEqualTo(expectedGameObject);
    }

    @Test
    void shouldAddInExistingGameCommand() {
        var expectedGameObject = new Object();
        var anotherPlayerId = "anotherPlayerId";

        var gameObjects = getGameObjectsWithAlreadyExistingPlayerInGame();

        var gamePlayStore = new GamePlayerStorageLocal(gameObjects);

        gamePlayStore.add(GAME_ID, anotherPlayerId, expectedGameObject);

        var actualAddedObject = gameObjects.get(GAME_ID).get(anotherPlayerId);

        assertThat(actualAddedObject).isEqualTo(expectedGameObject);
    }

    private static Map<String, Map<String, Object>> getGameObjectsWithAlreadyExistingPlayerInGame() {
        Map<String, Object> players = new HashMap<>();
        players.put(PLAYER_ID, new Object());
        Map<String, Map<String, Object>> gameObjects = new HashMap<>();

        gameObjects.put(GAME_ID, players);
        return gameObjects;
    }

}
