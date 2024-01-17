package ru.otus;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class QuadraticsServiceTest {
    private final QuadraticsService quadraticsService = new QuadraticsService();

    @Test
    void shouldReturnNoSqrt() {
        var a = 1;
        var b = 0;
        var c = 1;

        var expectedResult = new double[0];

        var actualResult = quadraticsService.solve(a, b, c);

        assertThat(actualResult).containsExactlyInAnyOrder(expectedResult);
    }

    @Test
    void shouldReturn2Results() {
        var a = 1;
        var b = 0;
        var c = -1;

        var expectedResult = new double[]{1, -1};

        var actualResult = quadraticsService.solve(a, b, c);

        assertThat(actualResult).containsExactlyInAnyOrder(expectedResult);
    }

    @Test
    void shouldReturn2EqualResults() {
        var a = 1;
        var b = 2;
        var c = 1;

        var expectedResult = new double[]{-1, -1};

        var actualResult = quadraticsService.solve(a, b, c);

        assertThat(actualResult).containsExactlyInAnyOrder(expectedResult);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfAIsInErrorRange() {
        var a = 1e-6;
        var b = 2;
        var c = 1;

        assertThatThrownBy(() -> quadraticsService.solve(a, b, c)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfNaN() {
        var a = Double.NaN;
        var b = 2;
        var c = 1;

        assertThatThrownBy(() -> quadraticsService.solve(a, b, c)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfPositiveInfinity() {
        var a = Double.POSITIVE_INFINITY;
        var b = 2;
        var c = 1;

        assertThatThrownBy(() -> quadraticsService.solve(a, b, c)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfNegativeInfinity() {
        var a = Double.NEGATIVE_INFINITY;
        var b = 2;
        var c = 1;

        assertThatThrownBy(() -> quadraticsService.solve(a, b, c)).isInstanceOf(IllegalArgumentException.class);
    }

}
