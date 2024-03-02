package com.example.spaceship.event;

import lombok.Getter;

@Getter
public class DestroyEvent {
    private boolean finished;

    public void finish() {
        finished = true;
    }
}
