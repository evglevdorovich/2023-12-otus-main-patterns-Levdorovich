package com.example.spaceship.model;

public interface FuelConsumer {
    int getFuelAmount();
    void decreaseFuelAmount(int amountToDecrease);
}
