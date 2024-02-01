package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuelTest {
    @Test
    void shouldDecreaseFuel() {
        var fuel = new Fuel(5);
        var expectedFuelAfterDecrease = 2;
        fuel.decrease(3);

        var actualFuelAmount = fuel.getAmount();

        assertThat(actualFuelAmount).isEqualTo(expectedFuelAfterDecrease);
    }

}
