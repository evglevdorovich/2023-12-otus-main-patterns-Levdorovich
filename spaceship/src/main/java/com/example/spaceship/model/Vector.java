package com.example.spaceship.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Vector {
    private List<Integer> coordinates;

    public Vector plus(Vector vectorToPlus) {
        var minSize = Math.min(this.size(), vectorToPlus.size());
        for (int i = 0; i < minSize; i++) {
            this.coordinates.set(i, this.coordinates.get(i) + vectorToPlus.getCoordinates().get(i));
        }
        return this;
    }

    public int size() {
        return coordinates.size();
    }
}
