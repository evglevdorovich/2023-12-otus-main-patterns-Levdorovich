package com.example.spaceship.command;

import com.example.spaceship.model.FuelConsumer;

public record CheckFuelCommand(FuelConsumer fuelConsumer) implements Command {
    @Override
    public void execute() {
        if (!fuelConsumer.isEnoughToConsume()) {
            throw new IllegalStateException("Fuel amount is not enough. Required: " + fuelConsumer.getFuelConsumption() + " but was: " +
                    fuelConsumer.getFuelAmount());
        }
    }
}
