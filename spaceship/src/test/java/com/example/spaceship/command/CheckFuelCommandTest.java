package com.example.spaceship.command;

import com.example.spaceship.exception.CommandException;
import com.example.spaceship.model.FuelConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckFuelCommandTest {
    @Mock
    private FuelConsumer fuelConsumer;

    @Test
    void shouldThrowNothingWhenFuelIsEnough() {
        var fuelDemand = 5;
        var enoughFuelAmount = 5;
        var checkFuelCommand = new CheckFuelCommand(fuelConsumer, fuelDemand);

        when(fuelConsumer.getFuelAmount()).thenReturn(enoughFuelAmount);
        assertThatNoException().isThrownBy(checkFuelCommand::execute);
    }

    @Test
    void shouldThrowCommandExceptionWhenFuelIsNotEnough() {
        var fuelDemand = 5;
        var notEnoughFuelAmount = 4;
        var checkFuelCommand = new CheckFuelCommand(fuelConsumer, fuelDemand);

        when(fuelConsumer.getFuelAmount()).thenReturn(notEnoughFuelAmount);

        assertThatThrownBy(checkFuelCommand::execute).isInstanceOf(IllegalStateException.class);
    }

}
