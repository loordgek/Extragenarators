package loordgek.extragenarators.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class TileUtil {
    @Nullable
    public static <T> T getTileEntity(IBlockAccess world, BlockPos pos, Class<T> tClass) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return tClass.isInstance(tileEntity) ? (T) tileEntity : null;
    }
}
