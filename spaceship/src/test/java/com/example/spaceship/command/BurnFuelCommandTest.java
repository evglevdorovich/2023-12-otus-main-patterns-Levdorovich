package com.example.spaceship.command;

import com.example.spaceship.model.FuelConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BurnFuelCommandTest {
    @Mock
    private FuelConsumer fuelConsumer;

    @Test
    void shouldBurnFuel() {
        var burnFuelCommand = new BurnFuelCommand(fuelConsumer);

        burnFuelCommand.execute();

        verify(fuelConsumer).consumeFuel();
    }

}
