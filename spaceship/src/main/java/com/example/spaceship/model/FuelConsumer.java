package com.example.spaceship.model;

public interface FuelConsumer {
    int getFuelAmount();
    int getFuelConsumption();
    void consumeFuel();
    boolean isEnoughToConsume();
}
