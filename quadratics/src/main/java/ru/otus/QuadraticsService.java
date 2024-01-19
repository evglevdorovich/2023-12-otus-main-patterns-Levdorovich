package ru.otus;

public class QuadraticsService {
    private static final double EPS = 1e-5;

    public double[] solve(double a, double b, double c) {

        validateNumbers(a, b, c);

        var discriminant = calculateDiscriminant(a, b, c);

        if (Math.abs(discriminant) < EPS) {
            var result = -b / (2 * a);
            return new double[]{result, result};
        }
        if (discriminant < 0) {
            return new double[0];
        } else {
            return new double[]{(-b + Math.sqrt(discriminant)) / (2 * a), (-b - Math.sqrt(discriminant)) / (2 * a)};
        }
    }

    private static void validateNumbers(double a, double b, double c) {
        if (Math.abs(a) < EPS) {
            throw new IllegalArgumentException("a cannot be less then zero, a = " + a);
        }

        if (Double.isInfinite(a) || Double.isInfinite(b) || Double.isInfinite(c)) {
            throw new IllegalArgumentException("1 or more of the arguments are infinite");

        }

        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)) {
            throw new IllegalArgumentException("1 or more of the arguments are NaN");
        }
    }

    private static double calculateDiscriminant(double a, double b, double c) {
        return b * b - (4.0 * a * c);
    }
}
