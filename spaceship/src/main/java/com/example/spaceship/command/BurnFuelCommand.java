package com.example.spaceship.command;

import com.example.spaceship.model.FuelConsumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BurnFuelCommand implements Command{
    private final FuelConsumer fuelConsumer;
    private final int fuelToBurn;
    @Override
    public void execute() {
        fuelConsumer.decreaseFuelAmount(fuelToBurn);
    }
}
