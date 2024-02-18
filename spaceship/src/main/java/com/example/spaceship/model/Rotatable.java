package com.example.spaceship.model;

import com.example.spaceship.core.Adapted;

@Adapted
public interface Rotatable {
    int getDirection();
    void setDirection(int direction);
    int getAngularVelocity();
    int getDirectionsNumber();
}
