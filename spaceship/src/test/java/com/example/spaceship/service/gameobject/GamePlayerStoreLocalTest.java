package com.example.spaceship.service.gameobject;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GamePlayerStoreLocalTest {
    @Test
    void shouldRetrieveCommand() {
        var playerId = "playerId";
        var gameId = "gameId";
        var expectedGameObject = new Object();
        var gameObjects = Map.of(gameId, Map.of(playerId, expectedGameObject));

        var gamePlayStore = new GamePlayerStoreLocal(gameObjects);

        var actualGameObject = gamePlayStore.retrieve(gameId, playerId);
        assertThat(actualGameObject).isEqualTo(expectedGameObject);
    }

}
