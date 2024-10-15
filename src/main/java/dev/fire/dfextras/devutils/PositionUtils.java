package dev.fire.dfextras.devutils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PositionUtils {
    public static BlockPos copyBlockPos(BlockPos blockPos) {
        return new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static boolean isequal(BlockPos b1, BlockPos b2) {
        return (b1 != null && b2 != null) && (b1.getX() == b2.getX()) && (b1.getY() == b2.getY()) && (b1.getZ() == b2.getZ());
    }
}
