package com.example.spaceship.service.gameobject;

import com.example.spaceship.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GamePlayerCommandStorageLocalTest {

    private static final String GAME_ID = "gameId";
    private static final String PLAYER_ID = "playerId";
    private static final String COMMAND_NAME = "commandName";

    @Test
    void shouldThrowNotFoundException_WhenCannotFindGame() {
        var gamePlayerCommandStorage = new GamePlayerCommandStorageLocal(Map.of());
        assertThatThrownBy(() -> gamePlayerCommandStorage.validateCommand("unexisting", PLAYER_ID, "commandName"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldThrowNotFoundException_WhenCannotFindPlayer() {
        var gamePlayerCommandStorage = new GamePlayerCommandStorageLocal(Map.of(GAME_ID, Map.of()));
        assertThatThrownBy(() -> gamePlayerCommandStorage.validateCommand(GAME_ID, "unexistingPlayerId", "commandName"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldThrowNotFoundException_WhenCannotFindCommand() {
        var gamePlayerCommandStorage = new GamePlayerCommandStorageLocal(Map.of(GAME_ID, Map.of(PLAYER_ID, Set.of())));
        assertThatThrownBy(() -> gamePlayerCommandStorage.validateCommand(GAME_ID, PLAYER_ID, "commandName"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldNotThrow_WhenIsValid() {
        var gamePlayerCommandStorage = new GamePlayerCommandStorageLocal(Map.of(GAME_ID, Map.of(PLAYER_ID, Set.of(COMMAND_NAME))));
        assertThatNoException().isThrownBy(() -> gamePlayerCommandStorage.validateCommand(GAME_ID, PLAYER_ID, COMMAND_NAME));
    }

    @Test
    void shouldAddCommandForThePlayer() {
        Map<String, Map<String, Set<String>>> playerCommands = new HashMap<>();
        var gamePlayerCommandStorage = new GamePlayerCommandStorageLocal(playerCommands);
        gamePlayerCommandStorage.addCommands(GAME_ID, PLAYER_ID, Set.of(COMMAND_NAME));

        var containsCommand = playerCommands.get(GAME_ID).get(PLAYER_ID).contains(COMMAND_NAME);

        assertThat(containsCommand).isTrue();
    }
}
