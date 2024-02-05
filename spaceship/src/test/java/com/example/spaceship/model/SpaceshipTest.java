package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceshipTest {
    @Test
    void shouldReturnCorrectFuelAmountAfterConsumption() {
        var initialFuelAmount = 3;
        var fuelConsumption = 1;
        var expectedFuelAmount = 2;

        var spaceship = Spaceship.builder()
                .fuel(new Fuel(initialFuelAmount))
                .fuelConsumption(fuelConsumption)
                .build();

        spaceship.consumeFuel();
        var actualFuelAmount = spaceship.getFuelAmount();

        assertThat(actualFuelAmount).isEqualTo(expectedFuelAmount);
    }

    @Test
    void shouldReturnCorrectFuelAmount(){
        var expectedFuelAmount = 2;
        var spaceship = Spaceship.builder()
                .fuel(new Fuel(expectedFuelAmount))
                .build();

        var actualFuelAmount = spaceship.getFuelAmount();

        assertThat(actualFuelAmount).isEqualTo(expectedFuelAmount);
    }

    @Test
    void shouldReturnFalseWhenNotEnoughToConsume(){
        var fuelConsumption = 100;
        var currentFuel = 2;
        var expectedIsEnoughToConsume = false;

        var spaceship = Spaceship.builder()
                .fuelConsumption(fuelConsumption)
                .fuel(new Fuel(currentFuel))
                .build();

        var actualIsEnoughToConsume = spaceship.isEnoughToConsume();

        assertThat(actualIsEnoughToConsume).isEqualTo(expectedIsEnoughToConsume);
    }

    @Test
    void shouldReturnTrueWhenEnoughToConsume(){
        var fuelConsumption = 2;
        var currentFuel = 100;
        var expectedIsEnoughToConsume = true;

        var spaceship = Spaceship.builder()
                .fuelConsumption(fuelConsumption)
                .fuel(new Fuel(currentFuel))
                .build();

        var actualIsEnoughToConsume = spaceship.isEnoughToConsume();

        assertThat(actualIsEnoughToConsume).isEqualTo(expectedIsEnoughToConsume);
    }
}
