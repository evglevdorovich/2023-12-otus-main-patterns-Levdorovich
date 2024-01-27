package com.example.spaceship.controller;

import com.example.spaceship.model.Position;
import com.example.spaceship.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipControllerTest {
    @InjectMocks
    private SpaceshipController spaceshipController;
    @Mock
    private SpaceshipService spaceshipService;

    @Test
    void shouldMoveSpaceship() {
        var spaceshipId = 1L;
        var expectedPosition = new Position(1, 1);
        when(spaceshipService.move(spaceshipId)).thenReturn(expectedPosition);

        var actualPosition = spaceshipController.move(spaceshipId);

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }

    @Test
    void shouldRotateSpaceship() {
        var spaceshipId = 1L;
        var expectedDirection = 1;
        when(spaceshipService.rotate(spaceshipId)).thenReturn(expectedDirection);

        var actualDirection = spaceshipController.rotate(spaceshipId);

        assertThat(actualDirection).isEqualTo(expectedDirection);
    }


}
