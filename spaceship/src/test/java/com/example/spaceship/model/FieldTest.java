package com.example.spaceship.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FieldTest {
    @Test
    void shouldReturnCorrectArea() {
        var expectedIndex = "3:2:3:";
        var dimension = new Dimension(List.of(1L, 2L, 3L));
        var position = new Vector(List.of(3, 5, 9));
        var area = new Area(Set.of());
        var field = new Field(dimension, Map.of(expectedIndex, area));

        var actualArea = field.getArea(position);

        assertThat(actualArea).isSameAs(area);
    }

    @Test
    void shouldReturnCorrectAreaWithOffset() {
        var expectedIndex = "1:1:3:";
        var offset = 2;
        var dimension = new Dimension(List.of(3L, 4L, 4L));
        var position = new Vector(List.of(3, 5, 10));
        var area = new Area(Set.of());
        var field = new Field(dimension, Map.of(expectedIndex, area));

        var actualArea = field.getArea(position, offset);

        assertThat(actualArea).isSameAs(area);
    }

}
