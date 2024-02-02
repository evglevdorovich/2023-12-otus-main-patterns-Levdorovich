package com.example.spaceship.command;

import com.example.spaceship.model.FuelConsumer;

public record BurnFuelCommand(FuelConsumer fuelConsumer, int fuelToBurn) implements Command {
    @Override
    public void execute() {
        fuelConsumer.decreaseFuelAmount(fuelToBurn);
    }
}
