package com.example.spaceship.controller;

import com.example.spaceship.command.Command;
import com.example.spaceship.core.IoC;
import com.example.spaceship.model.OperationRequest;
import com.example.spaceship.model.Vector;
import com.example.spaceship.service.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpaceshipController {
    private final SpaceshipService spaceshipService;

    @PostMapping("/spaceships/{id}/move")
    public Vector move(@PathVariable Long id) {
        return spaceshipService.move(id);
    }

    @PostMapping("/spaceships/{id}/rotate")
    public int rotate(@PathVariable Long id) {
        return spaceshipService.rotate(id);
    }

    @PatchMapping("/game/{gameId}/player/{playerId}/operation")
    public ResponseEntity<String> operate(@PathVariable String gameId, @PathVariable String playerId,
                                          @RequestBody OperationRequest operationRequest) {
        var playerActionRequest = IoC.resolve("PlayerActionRequest", gameId, playerId, operationRequest);
        IoC.<Command>resolve("Commands.Interpret", playerActionRequest).execute();
        return ResponseEntity.noContent().build();
    }
}
