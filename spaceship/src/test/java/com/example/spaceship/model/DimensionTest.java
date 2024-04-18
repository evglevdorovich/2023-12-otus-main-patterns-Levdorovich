package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DimensionTest {
    @Test
    void shouldReturnCorrectDimensionCounts() {
        var expectedDimensionCount = 3;
        var dimension = new Dimension(List.of(1L, 2L, 3L));

        var actualDimensionCount = dimension.getDimensionCount();
        assertThat(actualDimensionCount).isEqualTo(expectedDimensionCount);
    }

    @Test
    void shouldReturnCorrectDimensionSize() {
        var expectedDimensionSize = 2;
        var dimension = new Dimension(List.of(1L, 2L, 3L));

        var actualDimensionCount = dimension.getDimensionSize(1);
        assertThat(actualDimensionCount).isEqualTo(expectedDimensionSize);
    }

}
