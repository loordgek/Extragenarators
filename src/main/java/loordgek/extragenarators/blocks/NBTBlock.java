package loordgek.extragenarators.blocks;

import loordgek.extragenarators.tile.TileNBTTest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTBlock extends BlockMain {

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileNBTTest();
    }
//onBlockPlacedBy(worldIn, pos, state, placer, stack);

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileNBTTest){
            ((TileNBTTest) tileEntity).onBlockPlacedBy(worldIn, pos, state, placer, stack);
        }
    }
}
