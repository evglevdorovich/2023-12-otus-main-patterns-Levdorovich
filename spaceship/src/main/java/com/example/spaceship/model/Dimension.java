package com.example.spaceship.model;

import java.util.List;

public record Dimension(List<Long> sizes) {
    public int getDimensionCount() {
        return sizes.size();
    }

    public long getDimensionSize(int dimensionIndex) {
        return sizes.get(dimensionIndex);
    }
}
