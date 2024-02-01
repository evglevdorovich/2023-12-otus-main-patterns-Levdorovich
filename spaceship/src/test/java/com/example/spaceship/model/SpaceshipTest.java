package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceshipTest {
    @Test
    void shouldReturnCorrectFuelAmount() {
        var initialFuelAmount = 3;
        var amountToDecrease = 1;
        var expectedFuelAmount = 2;

        var spaceship = Spaceship.builder()
                .fuel(new Fuel(initialFuelAmount))
                .build();

        spaceship.decreaseFuelAmount(amountToDecrease);
        var actualFuelAmount = spaceship.getFuelAmount();

        assertThat(actualFuelAmount).isEqualTo(expectedFuelAmount);
    }
}
