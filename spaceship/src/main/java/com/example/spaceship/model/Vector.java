package com.example.spaceship.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Vector {
    private List<Integer> coordinates;

    public Vector plus(Vector vectorToPlus) {
        var resultCoordinates = new ArrayList<Integer>();

        var minSize = Math.min(this.size(), vectorToPlus.size());
        for (int i = 0; i < minSize; i++) {
            resultCoordinates.add(this.coordinates.get(i) + vectorToPlus.getCoordinates().get(i));
        }

        return new Vector(resultCoordinates);
    }


    public long getCoordinate(int index) {
        return coordinates.get(index);
    }

    public int size() {
        return coordinates.size();
    }
}
