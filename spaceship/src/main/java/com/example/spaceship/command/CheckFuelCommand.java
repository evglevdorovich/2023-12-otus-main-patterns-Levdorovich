package com.example.spaceship.command;

import com.example.spaceship.exception.CommandException;
import com.example.spaceship.model.FuelConsumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckFuelCommand implements Command {
    private final FuelConsumer fuelConsumer;
    private final int fuelAmountDemand;

    @Override
    public void execute() {
        if (fuelConsumer.getFuelAmount() < fuelAmountDemand) {
            throw new IllegalStateException("Fuel amount is not enough. Required: " + fuelAmountDemand + " but was: " + fuelConsumer.getFuelAmount());
        }
    }
}
