package com.example.spaceship.command;

import com.example.spaceship.model.FuelConsumer;

public record CheckFuelCommand(FuelConsumer fuelConsumer, int fuelAmountDemand) implements Command {
    @Override
    public void execute() {
        if (fuelConsumer.getFuelAmount() < fuelAmountDemand) {
            throw new IllegalStateException("Fuel amount is not enough. Required: " + fuelAmountDemand + " but was: " + fuelConsumer.getFuelAmount());
        }
    }
}
