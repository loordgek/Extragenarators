package loordgek.extragenarators.tile;

import loordgek.extragenarators.container.container.IContainerGuiSync;
import loordgek.extragenarators.nbt.INBTSaver;
import loordgek.extragenarators.util.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.List;

public class TileMain extends TileEntity implements ITickable, IContainerGuiSync, INBTSaver {
    private List<Field> nbtfieldlist;
    private int timer;
    private boolean firsttick = true;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fromNBT(compound);
        LogHelper.info(compound);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        toNBT(compound);
        return compound;
    }

    @Override
    public void update() {
        if (firsttick){
            firstTick();
            firsttick = false;
            return;
        }
        timer++;
        if (timer == 40) {
            timer = 0;
            if (!worldObj.isRemote){
                update2secSeverSide();
            }
            else update2secClientSide();
        }
        if (!worldObj.isRemote) {
            updateServerSide();
        }
        else updateClientSide();
    }
    protected void firstTick(){}

    protected void updateClientSide(){}

    protected void updateServerSide(){}

    protected void update2secSeverSide() {}

    protected void update2secClientSide() {}

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){}

    public void onNeighborChange(BlockPos neighbor) {}

    public void breakBlock(){}

    @Override
    public void onGuiUpdate() {}

    @Override
    public List<Field> getFieldList() {
        return nbtfieldlist;
    }

    @Override
    public void setFieldList(List<Field> fieldList) {
        this.nbtfieldlist = fieldList;

    }
}
