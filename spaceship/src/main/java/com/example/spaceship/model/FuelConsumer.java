package com.example.spaceship.model;

import com.example.spaceship.core.Adapted;

@Adapted
public interface FuelConsumer {
    int getFuelAmount();
    int getFuelConsumption();
    void consumeFuel();
    boolean isEnoughToConsume();
}
