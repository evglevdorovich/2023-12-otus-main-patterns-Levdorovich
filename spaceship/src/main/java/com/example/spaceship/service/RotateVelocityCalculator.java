package com.example.spaceship.service;

import com.example.spaceship.model.Rotatable;
import com.example.spaceship.model.Vector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RotateVelocityCalculator {
    // stub
    public Vector calculateVelocity(Rotatable rotatable) {
        return new Vector(List.of(1, 2));
    }
}
