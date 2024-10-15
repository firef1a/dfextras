package dev.fire.dfextras.devutils;

import net.minecraft.util.math.Vec3d;

public class VectorUtils {
    public static boolean isVectorWithinVectorRangeExclusive(Vec3d position, Vec3d lower, Vec3d upper) {
        return ((position.x > lower.x) && (position.x < upper.x)) && ((position.y > lower.y) && (position.y < upper.y)) && ((position.z > lower.z) && (position.z < upper.z));
    }

    public static boolean isVectorWithinVectorRangeInclusive(Vec3d position, Vec3d lower, Vec3d upper) {
        return ((position.x >= lower.x) && (position.x <= upper.x)) && ((position.y >= lower.y) && (position.y <= upper.y)) && ((position.z >= lower.z) && (position.z <= upper.z));
    }

    public static Vec3d align(Vec3d vec) {
        return new Vec3d(Math.floor(vec.x), Math.floor(vec.y), Math.floor(vec.z));
    }

    public static boolean isEqual(Vec3d a, Vec3d b) {
        return a != null && b != null && a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    public static boolean withinHorizontalRangeInclusive(Vec3d a, Vec3d b, double range) {
        return a != null && b != null && range >= Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.z-b.z, 2));
    }
}
