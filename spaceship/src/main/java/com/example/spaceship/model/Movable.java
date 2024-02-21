package com.example.spaceship.model;

import com.example.spaceship.core.Adapted;

@Adapted
public interface Movable {
    Vector getPosition();
    void setPosition(Vector position);
    Vector getVelocity();
}
