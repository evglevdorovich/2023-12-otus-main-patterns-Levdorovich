package com.example.spaceship.repository;

import com.example.spaceship.model.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
}
