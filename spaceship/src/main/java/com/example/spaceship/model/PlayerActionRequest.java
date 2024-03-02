package com.example.spaceship.model;

import lombok.Value;

@Value
public class PlayerActionRequest {
    String gameId;
    String playerId;
    String operationId;
    String[] args;
}
