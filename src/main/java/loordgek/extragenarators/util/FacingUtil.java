package loordgek.extragenarators.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class FacingUtil {

    public static EnumFacing getFacingFromPos(BlockPos pos, BlockPos neighbor) {
        BlockPos possubtract = pos.subtract(neighbor);
        if (possubtract.getX() == -1) return EnumFacing.EAST;
        if (possubtract.getX() == 1) return EnumFacing.WEST;
        if (possubtract.getY() == -1) return EnumFacing.UP;
        if (possubtract.getY() == 1) return EnumFacing.DOWN;
        if (possubtract.getZ() == -1) return EnumFacing.SOUTH;
        if (possubtract.getZ() == 1) return EnumFacing.NORTH;
        return null;
    }
}
