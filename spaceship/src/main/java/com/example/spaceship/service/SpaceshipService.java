package com.example.spaceship.service;

import com.example.spaceship.command.MoveCommand;
import com.example.spaceship.command.RotateCommand;
import com.example.spaceship.model.Spaceship;
import com.example.spaceship.model.Vector;
import com.example.spaceship.repository.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceshipService {
    private final SpaceshipRepository spaceshipRepository;

    public Vector move(Long id) {
        var spaceship = spaceshipRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        new MoveCommand(spaceship).execute();

        return save(spaceship).getPosition();
    }

    public int rotate(Long id) {
        var spaceship = spaceshipRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        new RotateCommand(spaceship).execute();
        return save(spaceship).getDirection();
    }

    private Spaceship save(Spaceship spaceship) {
        try {
            spaceship = spaceshipRepository.save(spaceship);
        } catch (RuntimeException runtimeException) {
            throw new IllegalArgumentException("error in saving the spaceship: " + spaceship);
        }
        return spaceship;
    }
}
