package dev.fire.sitemod.devutils;

import net.minecraft.util.math.Vec3d;

public class MathUtils {
    public static double lerp(double a, double b, double f) {
        return a * (1.0 - f) + (b * f);
    }

    public static boolean inRange(int v, int b, int u) {
        return v >= b && v <= u;
    }
}
