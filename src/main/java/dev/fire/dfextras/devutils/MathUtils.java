package dev.fire.dfextras.devutils;

public class MathUtils {
    public static double lerp(double a, double b, double f) {
        return a * (1.0 - f) + (b * f);
    }

    public static boolean inRange(int v, int b, int u) {
        return v >= b && v <= u;
    }
}
