package com.example.spaceship.controller;

import com.example.spaceship.model.Position;
import com.example.spaceship.service.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpaceshipController {
    private final SpaceshipService spaceshipService;

    @PostMapping("/spaceships/{id}/move")
    public Position move(@PathVariable Long id) {
        return spaceshipService.move(id);
    }

    @PostMapping("/spaceships/{id}/rotate")
    public int rotate(@PathVariable Long id) {
        return spaceshipService.rotate(id);
    }
}
