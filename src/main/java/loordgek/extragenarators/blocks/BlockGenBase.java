package loordgek.extragenarators.blocks;

import loordgek.extragenarators.Extragenarators;
import loordgek.extragenarators.GuiHander;
import loordgek.extragenarators.enums.EnumGenarator;
import loordgek.extragenarators.tile.TileFurnaceGen;
import loordgek.extragenarators.util.IVariantLookup;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGenBase extends BlockMain implements IVariantLookup {
    private final static PropertyEnum<EnumGenarator> genmeta = PropertyEnum.create("genmeta", EnumGenarator.class);

    public BlockGenBase() {
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(genmeta, EnumGenarator.byMeta(0)));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        int meta = getMetaFromState(state);
        switch (meta) {
            case 0:
                return new TileFurnaceGen();
        }
        return null;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            int meta = getMetaFromState(state);
            if (!playerIn.isSneaking()) {
                switch (meta) {
                    case 0:
                        playerIn.openGui(Extragenarators.instance, GuiHander.GuiIDS.furnacegengui.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
                    case 1:
                        playerIn.openGui(Extragenarators.instance, GuiHander.GuiIDS.lavagengui.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
                    case 2:
                        playerIn.openGui(Extragenarators.instance, GuiHander.GuiIDS.endergengui.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            } else
                playerIn.openGui(Extragenarators.instance, GuiHander.GuiIDS.upgradegui.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
//        TileEntity tileEntity = worldIn.getTileEntity(pos);
//        assert (tileEntity) != null;
//        ((TileMain) tileEntity).onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < EnumGenarator.getlenth(); i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(genmeta, EnumGenarator.byMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(genmeta)).getMeta();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, genmeta);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack((Blocks.GenITEM), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public String[] variantnames() {
        String[] strings = new String[EnumGenarator.getlenth()];
        for (int i = 0; i < EnumGenarator.getlenth(); i++) {
            strings[i] = EnumGenarator.byMeta(i).getName();
        }
        return strings;
    }
}