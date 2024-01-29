package com.example.spaceship.service;

import com.example.spaceship.model.Spaceship;
import com.example.spaceship.model.Vector;
import com.example.spaceship.repository.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceTest {
    private static final Long SPACESHIP_ID = 1L;
    @Mock
    private SpaceshipRepository spaceshipRepository;
    @InjectMocks
    private SpaceshipService spaceshipService;

    @Test
    void shouldThrowEntityNotFoundExceptionIfNotEntityPersisted_WhenMove() {
        when(spaceshipRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> spaceshipService.move(anyLong())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldMoveSpaceship() {
        var initialPosition = new Vector(new ArrayList<>(List.of(1, 2)));
        var initialSpaceship = getSpaceship(initialPosition);

        val expectedMovedPosition = new Vector(List.of(2, 4));
        var savedSpaceship = getSpaceship(expectedMovedPosition);

        when(spaceshipRepository.findById(SPACESHIP_ID)).thenReturn(Optional.of(initialSpaceship));
        when(spaceshipRepository.save(initialSpaceship)).thenReturn(savedSpaceship);
        val actualPosition = spaceshipService.move(SPACESHIP_ID);

        assertThat(actualPosition).isEqualTo(expectedMovedPosition);
    }

    @Test
    void shouldThrowExceptionWhenSpaceshipIsNotSaved() {
        var initialPosition = new Vector(new ArrayList<>(List.of(1, 2)));

        when(spaceshipRepository.findById(SPACESHIP_ID)).thenReturn(Optional.of(getSpaceship(initialPosition)));
        when(spaceshipRepository.save(getSpaceship(initialPosition))).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> spaceshipService.move(SPACESHIP_ID)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRotateSpaceship() {
        var initialDirection = 3;

        var expectedDirection = 2;
        var initialSpaceship = getSpaceship(initialDirection);

        when(spaceshipRepository.findById(SPACESHIP_ID)).thenReturn(Optional.of(initialSpaceship));
        when(spaceshipRepository.save(initialSpaceship)).thenReturn(getSpaceship(expectedDirection));
        val actualDirection = spaceshipService.rotate(SPACESHIP_ID);

        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfNotEntityPersisted_WhenRotate() {
        when(spaceshipRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> spaceshipService.rotate(anyLong())).isInstanceOf(EntityNotFoundException.class);
    }

    private static Spaceship getSpaceship(Vector position) {
        return Spaceship.builder()
                .id(SPACESHIP_ID)
                .position(position)
                .velocity(position)
                .build();
    }

    private static Spaceship getSpaceship(int direction) {
        return Spaceship.builder()
                .id(SPACESHIP_ID)
                .angularVelocity(2)
                .direction(direction)
                .directionsNumber(3)
                .build();
    }

}
