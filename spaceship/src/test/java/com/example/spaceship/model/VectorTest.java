package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VectorTest {

    @Test
    void shouldPlusVector() {
        var firstVector = new Vector(new ArrayList<>(List.of(1, 1, 1)));
        var secondVector = new Vector(List.of(2, 2, 2, 2));
        var expectedVector = new Vector(List.of(3, 3, 3));

        var actualSummedVector = firstVector.plus(secondVector);

        assertThat(actualSummedVector).isEqualTo(expectedVector);
    }

    @Test
    void shouldReturnCorrectSize() {
        var vector = new Vector(List.of(1, 2, 3));
        var expectedSize = 3;
        var actualSize = vector.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }
}
