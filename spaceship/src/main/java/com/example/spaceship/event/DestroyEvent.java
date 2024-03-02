package com.example.spaceship.event;

import lombok.Getter;

public class DestroyEvent {
    @Getter
    private boolean finished;

    public void finish() {
        finished = true;
    }
}
