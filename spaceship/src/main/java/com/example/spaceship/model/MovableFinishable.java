package com.example.spaceship.model;

import com.example.spaceship.core.Adapted;

//Test class to reproduce creation of the adapter
@Adapted
public interface MovableFinishable extends Movable {
    void finish();
}
